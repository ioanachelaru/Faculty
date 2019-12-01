package repos;

import domain.Abonat;
import domain.Bibliotecar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class RepoAbonat implements CRUDRepo<String, Abonat> {

    @Override
    public Abonat findOne(String cui) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Abonat abonat= session.find(Abonat.class,cui);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return abonat;
    }

    @Override
    public Iterable<Abonat> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Abonat> abonati= session.createQuery("select a from Abonat a ",Abonat.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return abonati;
    }

    public void save(Abonat abonat) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(abonat);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void delete(String idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Abonat abonat = this.findOne(idEntity);

        session.delete(abonat);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void update(Abonat abonat) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(abonat);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
