package tech.itpark.proggerhub.exception;

public class WrongTypeRestoreIssueException extends RuntimeException {

    public WrongTypeRestoreIssueException() {
        super();
    }

    public WrongTypeRestoreIssueException(String message) {
        super(message);
    }

    public WrongTypeRestoreIssueException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongTypeRestoreIssueException(Throwable cause) {
        super(cause);
    }

    protected WrongTypeRestoreIssueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
