package exceptions;

public class RepositoryException extends RuntimeException {
    /**
     * Constructor
     * @param msg mesajul care contine erorile
     */
    public RepositoryException(String msg) {
        super(msg);
    }
}
