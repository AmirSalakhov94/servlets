package tech.itpark.proggerhub.crypto;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TokenGenerator {
    public static final int DEFAULT_LENGTH = 128;
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        return generate(DEFAULT_LENGTH);
    }

    public String generate(int bytesLength) {
        final var bytes = new byte[bytesLength];
        random.nextBytes(bytes);
        return Hex.encode(bytes);
    }
}
