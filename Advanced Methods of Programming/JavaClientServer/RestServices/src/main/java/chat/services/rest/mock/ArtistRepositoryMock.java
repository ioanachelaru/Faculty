package chat.services.rest.mock;

import chat.services.rest.Proba;
import chat.services.rest.ProbaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class ArtistRepositoryMock implements ProbaRepository {

    private Map<String, Proba> allProbe;

    public ArtistRepositoryMock() {
        allProbe = new TreeMap<String, Proba>();
        populateUsers();
    }

    private void populateUsers() {
        Proba Proba1 = new Proba(1,50,"fluture");
        Proba Proba2 = new Proba(2,200,"mixt");
        Proba Proba3 = new Proba(3,800,"mixt");
        Proba Proba4 = new Proba(4,1500,"fluture");
        Proba Proba5 = new Proba(5,200,"fluture");
        Proba Proba6 = new Proba(6,800,"fluture");
        Proba Proba7 = new Proba(7,50,"mixt");
        Proba Proba8 = new Proba(8,50,"mixt");
        Proba Proba9 = new Proba(9,200,"mixt");
        Proba Proba10 = new Proba(10,1500,"mixt");

        allProbe.put(String.valueOf(Proba1.getId()),Proba1);
        allProbe.put(String.valueOf(Proba2.getId()),Proba2);
        allProbe.put(String.valueOf(Proba3.getId()),Proba3);
        allProbe.put(String.valueOf(Proba4.getId()),Proba4);
        allProbe.put(String.valueOf(Proba5.getId()),Proba5);
        allProbe.put(String.valueOf(Proba6.getId()),Proba6);
        allProbe.put(String.valueOf(Proba7.getId()),Proba7);
        allProbe.put(String.valueOf(Proba8.getId()),Proba8);
        allProbe.put(String.valueOf(Proba9.getId()),Proba9);
        allProbe.put(String.valueOf(Proba10.getId()),Proba10);


    }

    @Override
    public Proba findBy(Integer id) {
        Proba Proba=allProbe.get(String.valueOf(id));
        if (Proba==null)
            return null;
        return Proba;
    }

    @Override
    public Iterable<Proba> getProbe() {
        return allProbe.values();
    }

    @Override
    public void update(Integer id, Proba Proba) {
        if (allProbe.containsKey(String.valueOf(id))){
            if (id.equals(Proba.getId())) {
                allProbe.put(String.valueOf(id), Proba);
                return;
            }

        }
    }

    @Override
    public void save(Proba entity) {
        System.out.println("[UserRepositoryMock] save user - entering");
        if(allProbe.containsKey(String.valueOf(entity.getId())))
            try {
                throw new Exception("Username already exists: "+entity.getId());
            } catch (Exception e) {
                System.out.println("Exista deja un user cu acest id!!!");
                e.printStackTrace();
            }
        allProbe.put(String.valueOf(entity.getId()),entity);
        System.out.println(entity.getId()+" "+entity.getDistanta()+" "+entity.getStil());
        System.out.println("[UserRepositoryMock] save user - exiting ok");
    }

    @Override
    public void delete(Integer idEntity) {
        if (allProbe.containsKey(String.valueOf(idEntity)))
            allProbe.remove(String.valueOf(idEntity));
    }

    @Override
    public void update(Proba entity) {

    }

    @Override
    public Proba find(Integer idEntity) {
        return findBy(idEntity);
    }

    @Override
    public List<Proba> findAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
