package tech.itpark.proggerhub.exception;

public class TypeRestoreIssueNotMatchException extends RuntimeException {
    public TypeRestoreIssueNotMatchException() {
        super();
    }

    public TypeRestoreIssueNotMatchException(String message) {
        super(message);
    }

    public TypeRestoreIssueNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeRestoreIssueNotMatchException(Throwable cause) {
        super(cause);
    }

    protected TypeRestoreIssueNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
