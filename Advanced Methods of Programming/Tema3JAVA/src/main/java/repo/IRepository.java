package repo;

public interface IRepository<ID, T> {
    int size();
    void save(T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}