package tech.itpark.proggerhub.crypto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Hex {
    public static final char[] CHARS = "0123456789abcdef".toCharArray();

    public static String encode(byte[] bytes) {
        final var result = new char[2 * bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            result[i * 2] = CHARS[(b & 0b1111_0000) >>> 4];
            result[i * 2 + 1] = CHARS[b & 0b0000_1111];
        }
        return new String(result);
    }
}
