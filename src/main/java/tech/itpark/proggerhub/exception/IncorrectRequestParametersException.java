package tech.itpark.proggerhub.exception;

public class IncorrectRequestParametersException extends RuntimeException {
    public IncorrectRequestParametersException() {
    }

    public IncorrectRequestParametersException(String message) {
        super(message);
    }

    public IncorrectRequestParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectRequestParametersException(Throwable cause) {
        super(cause);
    }

    protected IncorrectRequestParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
