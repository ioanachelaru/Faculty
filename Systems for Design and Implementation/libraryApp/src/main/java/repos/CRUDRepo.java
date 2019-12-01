package repos;

public interface CRUDRepo<ID,T> {
    void save(T entity);
    void delete(ID id);
    void update(T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}