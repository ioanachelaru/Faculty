package utils;

public interface Observable<E> {
    void addObserver(Observer<E> e);
    void notifyObservers(E s);
}
