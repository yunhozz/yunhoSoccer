package yunhoSoccer.domain.exception;

public class NotEnoughCapitalException extends RuntimeException {

    public NotEnoughCapitalException() {
        super();
    }

    public NotEnoughCapitalException(String message) {
        super(message);
    }

    public NotEnoughCapitalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCapitalException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughCapitalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
