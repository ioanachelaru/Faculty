package repos;

import domain.Bibliotecar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class RepoBibliotecar implements CRUDRepo<String, Bibliotecar> {

    @Override
    public Bibliotecar findOne(String username) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Bibliotecar bibliotecar= session.find(Bibliotecar.class,username);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return bibliotecar;
    }

    @Override
    public Iterable<Bibliotecar> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Bibliotecar> bibliotecari = session.createQuery("select b from Bibliotecar b ",Bibliotecar.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return bibliotecari;
    }

    public void save(Bibliotecar bibliotecar) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(bibliotecar);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void delete(String idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Bibliotecar bibliotecar = this.findOne(idEntity);

        session.delete(bibliotecar);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void update(Bibliotecar bibliotecar) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(bibliotecar);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
