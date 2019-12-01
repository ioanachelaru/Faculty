package Repo;

import Model.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        logger.traceEntry("finding angajat with id {}",user);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from angajat where userAngajat=?")) {
            preStmt.setString(1,user);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Angajat a = new Angajat(result.getString("userAngajat"), result.getString("passwordAngajat"));
                    logger.traceExit(a);
                    return a;
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No angajat found with id{}",user);
        return null;
    }

    @Override
    public Iterable<Angajat> findAll() {
        logger.traceEntry();
        List<Angajat> lst = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from angajat")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    lst.add(new Angajat(result.getString("userAngajat"), result.getString("passwordAngajat")));
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(lst);
        return lst;
    }

    public void save(Angajat angajat) {
        logger.traceEntry("saving anagajat{}",angajat);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into angajat values (?,?)")){
            preStmt.setString(1,angajat.getId());
            preStmt.setString(2,angajat.getPasswordAngajat());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }


    public void delete(String idEntity) {
        logger.traceEntry("deleting angajat with {}",idEntity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from angajat where id=?")){
            preStmt.setString(1,idEntity);
            preStmt.executeUpdate();
        }catch (SQLException ex){
        logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }


    public void update(String idAngajat,Angajat angajat) {
        logger.traceEntry("updating angajat with {}",idAngajat);
        Connection con = dbUtils.getConnection();
        Angajat a=findOne(idAngajat);
        if(a==null){ return; }
        String newId=angajat.getId();
        String newPassword=angajat.getPasswordAngajat();

        try(PreparedStatement preStmt=con.prepareStatement("update Users"+
                " set password = ? "+
                " where user = ? ")){
            preStmt.setString(1,newPassword);
        }catch(SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB angajat update " + ex);
        }
        logger.traceExit();
    }
}