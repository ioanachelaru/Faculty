package repo;

import domain.Inscriere;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoInscriereTest {
    RepoInscriere repoInscriere;

    @Before
    public void setUp()throws Exception{
        Properties prop = new Properties();
        prop.load(new FileReader("D:\\MPP\\Laborator1-MPP\\src\\main\\resources\\bd.properties"));
        this.repoInscriere=new RepoInscriere(prop);
    }

    @Test
    public void save() {
        Inscriere i=new Inscriere(1,1);

        this.repoInscriere.save(i);
        int after=this.repoInscriere.size();

        assertEquals(1,after);
    }

    @Test
    public void findOne() {
        Integer id=11;
        Inscriere i=this.repoInscriere.findOne(id);

        assertNotNull(i);
    }

    @Test
    public void findAll() {
        Iterable<Inscriere> l=this.repoInscriere.findAll();
        assertEquals(1,this.repoInscriere.size());
    }
}
