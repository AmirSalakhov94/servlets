package tech.itpark.proggerhub.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.itpark.proggerhub.exception.InvalidPasswordFormatException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordHasher {
    private static final int DEFAULT_SALT_LENGTH = 64;
    private static final String SEPARATOR = ":";

    private final MessageDigest digest;
    private final SecureRandom random = new SecureRandom();

    public String hash(String raw, int saltLength) {
        final var saltBytes = new byte[saltLength];
        random.nextBytes(saltBytes);
        final var saltHex = Hex.encode(saltBytes);
        return saltHex + SEPARATOR + Hex.encode(digest.digest(
                (saltHex + SEPARATOR + raw).getBytes(StandardCharsets.UTF_8)
        ));
    }

    public String hash(String raw) {
        return hash(raw, DEFAULT_SALT_LENGTH);
    }

    public boolean match(String hash, String raw) {
        final var parts = hash.split(SEPARATOR);
        if (parts.length != 2)
            throw new InvalidPasswordFormatException();

        final var saltHex = parts[0];
        final var hashedPasswordHex = parts[1];
        final var calculatedPasswordHex = Hex.encode(digest.digest(
                (saltHex + SEPARATOR + raw).getBytes(StandardCharsets.UTF_8)
        ));

        return hashedPasswordHex.equals(calculatedPasswordHex);
    }
}
