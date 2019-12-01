package Repo;

public class RepoException extends RuntimeException {
    public RepoException(String message) {
        super(message);
    }

    public RepoException(Exception ex) {
        super(ex);
    }
}