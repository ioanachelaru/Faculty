package repo;

import domain.Participant;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

public class RepoParticipantTest {
    RepoParticipant repoParticipant;

    @Before
    public void setUp() throws Exception{
        Properties prop = new Properties();
        prop.load(new FileReader("D:\\MPP\\Laborator1-MPP\\src\\main\\resources\\bd.properties"));
        this.repoParticipant=new RepoParticipant(prop);
    }

    @Test
    public void save() {
        Participant p=new Participant(1,"Ioana",20);

        this.repoParticipant.save(p);
        int after=this.repoParticipant.size();

        assertEquals(1,after);
    }

    @Test
    public void findOne() {
        Integer id=1;
        Participant p=repoParticipant.findOne(id);

        assertNotNull(p);
    }

    @Test
    public void findAll() {
        Iterable<Participant> l=repoParticipant.findAll();
        assertEquals(1,repoParticipant.size());
    }
}
