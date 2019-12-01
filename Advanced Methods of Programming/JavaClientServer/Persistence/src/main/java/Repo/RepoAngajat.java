package Repo;

import Model.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoAngajat implements IRepository<String, Angajat> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoAngajat(Properties props) {
        logger.info("Initializing RepoAngajat with properties: {} ",props);
        this.dbUtils = new JdbcUtils(props);
    }

    public RepoAngajat() throws IOException {
        this.dbUtils = new JdbcUtils();
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from angajat")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public Angajat findOne(String user) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Angajat angajat = session.find(Angajat.class,user);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return angajat;
    }

    @Override
    public Iterable<Angajat> findAll() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Angajat> angajatList = session.createQuery("select a from Angajat a ",Angajat.class).list();

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        return angajatList;
    }

    public void save(Angajat angajat) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(angajat);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void delete(String idEntity) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(idEntity);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    public void update(String idAngajat,Angajat angajat) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(angajat);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}