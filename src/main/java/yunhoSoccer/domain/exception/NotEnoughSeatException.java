package yunhoSoccer.domain.exception;

public class NotEnoughSeatException extends RuntimeException {

    public NotEnoughSeatException() {
        super();
    }

    public NotEnoughSeatException(String message) {
        super(message);
    }

    public NotEnoughSeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSeatException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughSeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
