package chat.services.rest;

import Model.HasId;
import java.util.List;

public interface Repository <E,T extends HasId<E>>{
    void save(T entity);

    void delete(Integer idEntity);

    void update(T entity);

    T find(Integer idEntity);

    List<T> findAll();

    int size();
}
