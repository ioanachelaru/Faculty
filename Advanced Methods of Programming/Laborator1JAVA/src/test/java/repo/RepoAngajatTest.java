package repo;

import domain.Angajat;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import utils.ConcursConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static org.junit.Assert.*;

public class RepoAngajatTest {
    RepoAngajat repoAngajat;

    @Before
    public void setUp() throws Exception {
        //ApplicationContext context=new AnnotationConfigApplicationContext(ConcursConfig.class);
        //repoAngajat = context.getBean(RepoAngajat.class);
        ApplicationContext context=new ClassPathXmlApplicationContext("concurs.xml");
        Properties prop = new Properties();
        //prop.load(new FileReader("D:\\MPP\\Laborator1-MPP\\src\\main\\resources\\bd.properties"));

        this.repoAngajat = context.getBean(RepoAngajat.class);
    }

    @Test
    public void save() {
        Angajat a = new Angajat("ioana09","i998");

        this.repoAngajat.save(a);
        int after = this.repoAngajat.size();

        assertEquals(6,after);
    }

    @Test
    public void findOne() {
        String user="maria05";
        Angajat a=repoAngajat.findOne(user);

        assertNotNull(a);
    }

    @Test
    public void findAll() {
        Iterable<Angajat> l=repoAngajat.findAll();
        assertEquals(2,repoAngajat.size());
    }
}
