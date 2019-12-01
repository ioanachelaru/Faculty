package repo;

import domain.Proba;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoProbaTest {
    RepoProba repoProba;

    @Before
    public void setUp()throws Exception{
        Properties prop = new Properties();
        prop.load(new FileReader("D:\\MPP\\Laborator1-MPP\\src\\main\\resources\\bd.properties"));
        this.repoProba=new RepoProba(prop);
    }

    @Test
    public void save() {
        Proba p=new Proba(5,200,"mixt");

        this.repoProba.save(p);
        int after=this.repoProba.size();

        assertEquals(5,this.repoProba.size());
    }

    @Test
    public void findOne() {
        Integer id=1;
        Proba p=this.repoProba.findOne(id);

        assertNotNull(p);
    }

    @Test
    public void findAll() {
        Iterable<Proba> l=this.repoProba.findAll();
        assertEquals(5,this.repoProba.size());
    }
}
