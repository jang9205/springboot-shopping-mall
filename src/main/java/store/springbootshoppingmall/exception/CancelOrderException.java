package store.springbootshoppingmall.exception;

public class CancelOrderException extends IllegalStateException {

    public CancelOrderException(String message) {
        super(message);
    }

    public CancelOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
