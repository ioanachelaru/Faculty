package repos;

import domain.Imprumut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class RepoImprumut implements CRUDRepo<Integer, Imprumut> {

    public int getId(){
        List<Imprumut> imprumutList = (List<Imprumut>) this.findAll();
        return imprumutList.size()+1;
    }

    @Override
    public Imprumut findOne(Integer idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Imprumut imprumut= session.find(Imprumut.class,idEntity);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return imprumut;
    }

    @Override
    public Iterable<Imprumut> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Imprumut> imprumuturi= session.createQuery("select i from Imprumut i ",Imprumut.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return imprumuturi;
    }

    public void save(Imprumut imprumut) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(imprumut);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void delete(Integer idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Imprumut imprumut = this.findOne(idEntity);

        session.delete(imprumut);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void update(Imprumut imprumut) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(imprumut);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
