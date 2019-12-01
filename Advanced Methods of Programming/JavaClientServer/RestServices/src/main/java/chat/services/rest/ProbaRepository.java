package chat.services.rest;

public interface ProbaRepository extends Repository<Integer, Proba> {
    Proba findBy(Integer id);
    Iterable<Proba> getProbe();
    void update(Integer id, Proba proba);
}
