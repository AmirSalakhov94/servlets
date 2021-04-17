package tech.itpark.proggerhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.proggerhub.crypto.PasswordHasher;
import tech.itpark.proggerhub.crypto.TokenGenerator;
import tech.itpark.proggerhub.exception.*;
import tech.itpark.proggerhub.repository.AuthRepository;
import tech.itpark.proggerhub.repository.model.UserTokenModel;
import tech.itpark.proggerhub.security.AuthProvider;
import tech.itpark.proggerhub.security.Authentication;
import tech.itpark.proggerhub.service.model.RestoreModel;
import tech.itpark.proggerhub.service.model.UserAuthModel;
import tech.itpark.proggerhub.service.model.UserModel;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthProvider {

    private final AuthRepository repository;
    private final PasswordHasher hasher;
    private final TokenGenerator tokenGenerator;

    public long register(UserModel model) {
        if (model.getLogin() == null)
            throw new BadLoginException();

        if (!model.getLogin().matches("^[a-zA-Z0-9]{3,15}$"))
            throw new BadLoginException();

        if (model.getPassword().length() < 8)
            throw new PasswordPolicyViolationException("must be longer than 8");

        if (model.getTypeRestoreIssue() == null)
            throw new WrongTypeRestoreIssueException("type restore issue is null");

        if (model.getValueOnRestoreIssue() == null || model.getValueOnRestoreIssue().isEmpty())
            throw new WrongRestoreValueException("restore value can't is null or empty");

        return repository.save(new tech.itpark.proggerhub.repository.model.UserModel(model.getLogin().trim().toLowerCase(),
                hasher.hash(model.getPassword()), model.getTypeRestoreIssue(), model.getValueOnRestoreIssue()
        ));
    }

    public String login(UserModel model) {
        final var user = repository.findByLogin(model.getLogin()).orElseThrow(UserNotFoundException::new);

        if (!hasher.match(user.getHash(), model.getPassword()))
            throw new PasswordsNotMatchedException();

        final var token = tokenGenerator.generate();
        repository.save(new UserTokenModel(user.getId(), token));
        return token;
    }

    public long replacePassword(String login, String password, String key) {
        final var exists = repository.restoreKeyExists(login, key);
        if (!exists)
            throw new BadRestoreKeyException("restore key is not active or not exists");

        if (password.length() < 8)
            throw new PasswordPolicyViolationException("must be longer than 8");

        repository.deleteOldRestoreKeyAndExpired(key);
        return repository.replacePassword(login, hasher.hash(password));
    }

    public String getRestoreKey(RestoreModel restore) {
        final var typeRestoreIssue = restore.getTypeRestoreIssue();
        if (typeRestoreIssue == null)
            throw new IncorrectRequestParametersException("Type restore issue is null");

        final var valueRestoreIssue = restore.getValueOnTypeRestoreIssue();
        if (valueRestoreIssue == null || valueRestoreIssue.isEmpty())
            throw new IncorrectRequestParametersException("Value restore issue is null or empty");

        final var user = repository.findUserWithRestoreByLogin(restore.getLogin());
        return user.filter(u -> typeRestoreIssue == u.getTypeRestoreIssue())
                .map(u -> {
                    final var currentValueRestoreIssue = u.getValueOnRestoreIssue();
                    if (!valueRestoreIssue.equals(currentValueRestoreIssue))
                        throw new RestoreValueNotMatchException(String.format("restore value %s not match", valueRestoreIssue));

                    final var key = UUID.randomUUID().toString();
                    repository.saveRestoreKey(u.getLogin(), key);

                    return key;
                })
                .orElseThrow(() -> new TypeRestoreIssueNotMatchException(String.format("type restore issue %s not match", typeRestoreIssue)));
    }

    public Authentication authenticate(String token) {
        return repository.findByToken(token)
                .map(o -> (Authentication) new UserAuthModel(o.getId(), o.getRoles()))
                .orElse(Authentication.anonymous())
                ;
    }

    public void removeById(Authentication auth) {
        if (!auth.hasAnyRoles("ROLE_ADMIN")) {
            throw new PermissionDeniedException();
        }
    }
}
