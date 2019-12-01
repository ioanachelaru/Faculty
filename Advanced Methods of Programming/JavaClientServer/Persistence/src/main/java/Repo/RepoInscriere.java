package Repo;

import Model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoInscriere implements IRepository<Integer, Inscriere> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoInscriere(Properties props) {
        logger.info("Initializing RepoInscriere with properties: {} ",props);
        this.dbUtils = new JdbcUtils(props);
    }

    public RepoInscriere() throws IOException {
        this.dbUtils = new JdbcUtils();
    }

    public void save(Inscriere inscriere) {
        logger.traceEntry("saving inscriere{}",inscriere);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into inscriere values (?,?,?)")){
            preStmt.setInt(1,inscriere.getIdParticipant());
            preStmt.setInt(2,inscriere.getIdProba());
            preStmt.setInt(3,inscriere.getId());
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
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from inscriere")) {
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
    public Inscriere findOne(Integer id) {
        logger.traceEntry("finding inscriere with id {}",id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from inscriere where idInscriere=?")) {
            preStmt.setInt(1,id);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Inscriere i= new Inscriere(result.getInt("idParticipant"),
                            result.getInt("idProba"));
                    logger.traceExit(i);
                    return i;
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No inscriere found with id{}",id);
        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        List<Inscriere> lst = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from inscriere")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    lst.add(new Inscriere(result.getInt("idParticipant"),
                            result.getInt("idProba")));
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(lst);
        return lst;
    }
}
