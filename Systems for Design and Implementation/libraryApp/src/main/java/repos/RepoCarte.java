package repos;

import domain.Carte;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class RepoCarte implements CRUDRepo<Integer, Carte> {

    public int getId(){
        List<Carte> carti = (List<Carte>) this.findAll();
        int maxId = 1;
        for (Carte c:carti) {
            if(c.getId() > maxId)
                maxId = c.getId();

        }
        return maxId + 1;
    }

    @Override
    public Carte findOne(Integer idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Carte carte= session.find(Carte.class,idEntity);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return carte;
    }

    @Override
    public Iterable<Carte> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Carte> carte= session.createQuery("select a from Carte a ",Carte.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return carte;
    }

    public void save(Carte carte) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(carte);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void delete(Integer idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Carte carte = this.findOne(idEntity);

        session.delete(carte);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void update(Carte carte) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(carte);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
