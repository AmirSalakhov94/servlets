package tech.itpark.proggerhub.exception;

public class RestoreValueNotMatchException extends RuntimeException {
    public RestoreValueNotMatchException() {
        super();
    }

    public RestoreValueNotMatchException(String message) {
        super(message);
    }

    public RestoreValueNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestoreValueNotMatchException(Throwable cause) {
        super(cause);
    }

    protected RestoreValueNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}