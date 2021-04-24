package tech.itpark.proggerhub.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Header {

    AUTHORIZATION("Authorization"),
    CONTENT_TYPE("Content-Type");

    private final String value;
}
