package tech.itpark.proggerhub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.proggerhub.converter.BodyConverter;
import tech.itpark.proggerhub.dto.RestoreDto;
import tech.itpark.proggerhub.dto.UserDto;
import tech.itpark.proggerhub.dto.UserIdDto;
import tech.itpark.proggerhub.dto.UserTokenDto;
import tech.itpark.proggerhub.security.Authentication;
import tech.itpark.proggerhub.service.AuthService;
import tech.itpark.proggerhub.service.model.RestoreModel;
import tech.itpark.proggerhub.service.model.UserModel;
import tech.itpark.servlet.ContentTypes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String KEY = "key";
    private static final String AUTH = "AUTH";
    private static final String MEDIA_TYPE_NOT_SUPPORTED = "media type not supported";

    private final AuthService service;
    private final BodyConverter converter;

    public void register(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!converter.canRead(request.getHeader(CONTENT_TYPE), UserDto.class)) {
                response.sendError(415, MEDIA_TYPE_NOT_SUPPORTED);
                return;
            }

            final var dto = converter.read(request.getReader(), UserDto.class);
            final var id = service.register(new UserModel(dto.getLogin(), dto.getPassword(),
                    dto.getTypeRestoreIssue(), dto.getValueOnRestoreIssue()));

            response.addHeader(CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
            converter.write(response.getWriter(), new UserIdDto(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!converter.canRead(request.getHeader(CONTENT_TYPE), UserDto.class)) {
                response.sendError(415, MEDIA_TYPE_NOT_SUPPORTED);
                return;
            }

            final var dto = converter.read(request.getReader(), UserDto.class);
            final var token = service.login(new UserModel(dto.getLogin(), dto.getPassword(),
                    dto.getTypeRestoreIssue(), dto.getValueOnRestoreIssue()));

            response.addHeader(CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
            converter.write(response.getWriter(), new UserTokenDto(token));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeById(HttpServletRequest request, HttpServletResponse response) {
        final var auth = (Authentication) request.getAttribute(AUTH);
        service.removeById(auth);
    }

    public void replacePassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!converter.canRead(request.getHeader(CONTENT_TYPE), UserDto.class)) {
                response.sendError(415, MEDIA_TYPE_NOT_SUPPORTED);
                return;
            }

            final var key = request.getParameter(KEY);
            final var dto = converter.read(request.getReader(), UserDto.class);
            final var id = service.replacePassword(dto.getLogin(), dto.getPassword(), key);

            response.addHeader(CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
            converter.write(response.getWriter(), new UserIdDto(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreKey(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!converter.canRead(request.getHeader(CONTENT_TYPE), RestoreDto.class)) {
                response.sendError(415, MEDIA_TYPE_NOT_SUPPORTED);
                return;
            }

            final var restore = converter.read(request.getReader(), RestoreDto.class);
            final var key = service.getRestoreKey(new RestoreModel(restore.getLogin(),
                    restore.getTypeRestoreIssue(), restore.getValueOnTypeRestoreIssue()));

            converter.write(response.getWriter(), key);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
