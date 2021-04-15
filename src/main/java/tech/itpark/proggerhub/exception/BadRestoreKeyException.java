package tech.itpark.proggerhub.exception;

public class BadRestoreKeyException extends RuntimeException {
    public BadRestoreKeyException() {
        super();
    }

    public BadRestoreKeyException(String message) {
        super(message);
    }

    public BadRestoreKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRestoreKeyException(Throwable cause) {
        super(cause);
    }

    protected BadRestoreKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
