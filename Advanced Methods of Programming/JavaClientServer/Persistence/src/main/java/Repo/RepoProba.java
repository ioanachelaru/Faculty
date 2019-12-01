package Repo;

import Model.Proba;
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

public class RepoProba implements IRepository<Integer, Proba> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RepoProba(Properties props) {
        logger.info("Initializing RepoProba with properties: {} ",props);
        this.dbUtils = new JdbcUtils(props);
    }

    public RepoProba() throws IOException {
        this.dbUtils = new JdbcUtils();
    }

    public void save(Proba proba) {
        logger.traceEntry("saving proba{}",proba);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into proba values (?,?,?)")){
            preStmt.setInt(1,proba.getId());
            preStmt.setString(2,proba.getStil().toString());
            preStmt.setInt(3,proba.getDistanta().getValueInt(proba.getDistanta().toString()));
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
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from proba")) {
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
    public Proba findOne(Integer id) {
        Connection con=dbUtils.getConnection();
        logger.traceEntry("finding proba with id {}",id);
        try(PreparedStatement preStmt=con.prepareStatement("select * from proba where idProba=?")) {
            preStmt.setInt(1,id);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Proba p = new Proba(result.getInt("idProba"),
                            result.getInt("distanta"),
                            result.getString("stil"));
                    logger.traceExit(p);
                    return p;
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No proba found with id{}",id);
        return null;
    }

    @Override
    public Iterable<Proba> findAll() {
        logger.traceEntry();
        List<Proba> lst = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from proba")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    lst.add(new Proba(result.getInt("idProba"),
                                    result.getInt("distanta"),
                            result.getString("stil")));
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(lst);
        return lst;
    }

    public Integer numarInscrieri(Integer idProba){
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as NR from inscriere where idProba=?")) {
            preStmt.setInt(1,idProba);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("NR"));
                    return result.getInt("NR");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }
}
