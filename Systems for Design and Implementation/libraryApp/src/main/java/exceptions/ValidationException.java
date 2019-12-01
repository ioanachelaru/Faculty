package exceptions;

public class ValidationException extends RuntimeException {
    /**
     * Constructor
     * @param msg mesajul care contine erorile
     */
    public ValidationException(String msg) {
        super(msg);
    }
}
