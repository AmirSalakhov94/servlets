package tech.itpark.proggerhub.exception;

public class LimitNumberReplacePasswordException extends RuntimeException {
    public LimitNumberReplacePasswordException() {
        super();
    }

    public LimitNumberReplacePasswordException(String message) {
        super(message);
    }

    public LimitNumberReplacePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitNumberReplacePasswordException(Throwable cause) {
        super(cause);
    }

    protected LimitNumberReplacePasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
