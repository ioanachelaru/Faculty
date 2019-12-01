package Repo;

import Model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoParticipant implements IRepository<Integer, Participant> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoParticipant(Properties props){
        logger.info("Initializing RepoParticipant with properties: {} ",props);
        this.dbUtils=new JdbcUtils(props);
    }


    public void save(Participant participant) {
        logger.traceEntry("saving participant{}",participant);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into participant values (?,?,?)")){
            preStmt.setInt(1,participant.getId());
            preStmt.setString(2,participant.getNumeParticipant());
            preStmt.setInt(3,participant.getVarstaParticipant());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from participant")) {
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
    public Participant findOne(Integer idParticipant) {
        logger.traceEntry("finding participant with id {}",idParticipant);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from participant where idParticipant=?")) {
            preStmt.setInt(1,idParticipant);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Participant p= new Participant(result.getInt("idParticipant"),
                            result.getString("numeParticipant"), result.getInt("varstaParticipant"));
                    logger.traceExit(p);
                    return p;
                }
                else return null;
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No participant found with id{}",idParticipant);
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        List<Participant> lst = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from participant")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    lst.add(new Participant(result.getInt("idParticipant"), result.getString("numePArticipant"),
                            result.getInt("varstaParticipant")));
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(lst);
        return lst;
    }

    public Participant findByNameAndAge(String name, int age){
        logger.traceEntry("finding participant with name {} and age {}",name,age);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from participant where numeParticipant like ? and varstaParticipant=?")) {
            preStmt.setString(1,name);
            preStmt.setInt(2,age);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Participant participant= new Participant(result.getInt("idParticipant"),
                            result.getString("numeParticipant"), result.getInt("varstaParticipant"));
                    logger.traceExit(participant);
                    return participant;
                }
                else return null;
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No participant found with given name and age");
        return null;
    }

}
