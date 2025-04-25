package exception;

public class NotExistingEntityException extends RuntimeException {
    public NotExistingEntityException(String message) {
        super(message);
    }
}
