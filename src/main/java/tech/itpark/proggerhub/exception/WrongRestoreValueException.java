package tech.itpark.proggerhub.exception;

public class WrongRestoreValueException extends RuntimeException {

    public WrongRestoreValueException() {
        super();
    }

    public WrongRestoreValueException(String message) {
        super(message);
    }

    public WrongRestoreValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongRestoreValueException(Throwable cause) {
        super(cause);
    }

    protected WrongRestoreValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
