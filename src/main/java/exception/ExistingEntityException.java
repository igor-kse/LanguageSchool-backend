package exception;

public class ExistingEntityException extends RuntimeException {

    public ExistingEntityException(String message) {
        super(message);
    }
}
