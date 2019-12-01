package repos;

import domain.Exemplar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class RepoExemplar implements CRUDRepo<Integer, Exemplar> {

    public int getId(){
        int maxId = 1;
        List<Exemplar> exemplarList = (List<Exemplar>) this.findAll();
        for (Exemplar e: exemplarList) {
            if(e.getId() > maxId)
                maxId = e.getId();
        }
        return maxId + 1;
    }

    @Override
    public void save(Exemplar entity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public void delete(Integer integer) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Exemplar exemplar = this.findOne(integer);

        session.delete(exemplar);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteByBook(Integer id){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        final Iterable<Exemplar> instances = this.findAll();
        for (Exemplar obj : instances) {
            if(obj.getIdCarte() == id)
                session.delete(obj);
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public void update(Exemplar entity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(entity);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public Exemplar findOne(Integer integer) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Exemplar exemplar = session.find(Exemplar.class,integer);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return exemplar;
    }

    @Override
    public Iterable<Exemplar> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Exemplar> exemplare = session.createQuery("select e from Exemplar e ",Exemplar.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return exemplare;
    }
}
