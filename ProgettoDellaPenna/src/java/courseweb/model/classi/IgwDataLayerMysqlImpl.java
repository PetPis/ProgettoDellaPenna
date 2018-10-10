/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.data.DataLayerMysqlImpl;
import courseweb.model.interfacce.Gruppo;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Servizio;
import courseweb.model.interfacce.Utente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import all classes and interfaces
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IgwDataLayerMysqlImpl extends DataLayerMysqlImpl implements IgwDataLayer {

    private PreparedStatement sUtentiByGruppo, sUtenteByID,Login,sGruppoByID,sServiziByGruppo,sServizioByID;

    @Override
    public void init() throws DataLayerException {
        try {
            super.init();
            sGruppoByID=connection.prepareStatement("SELECT * FROM Gruppo WHERE IDGruppo=?");
            Login=connection.prepareStatement("SELECT * FROM Utente WHERE BINARY Utente.Username=? AND BINARY Utente.Password=?");
            sUtentiByGruppo = connection.prepareStatement("SELECT IDUtente FROM Utente WHERE Gruppo=?");
            sUtenteByID = connection.prepareStatement("SELECT * FROM Utente WHERE IDUtente=?");
            sServiziByGruppo=connection.prepareStatement("SELECT Servizio FROM Group_Services WHERE Gruppo=?");
            sServizioByID=connection.prepareStatement("SELECT * FROM Servizio WHERE IDServizio=?");
            
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load UtentiInGruppo by Gruppo", ex);
        }
    }

    public IgwDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    @Override
    public Utente createUtente() {
        return (Utente) new UtenteImpl(this);
    }
    public Utente createUtente(ResultSet rs) throws DataLayerException {
        try {
            Utente de = new UtenteImpl(this);

            de.setID(rs.getInt("IDUtente"));
            de.setUsername(rs.getString("Username"));
            de.setPassword(rs.getString("Password"));
            de.setDocente(rs.getInt("Docente"));
            de.setIDGruppo(rs.getInt("Gruppo"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Utente object form ResultSet", ex);
        }
    }
    
    @Override //LOGIN QUERY
    public Utente getUtenti(String username, String password) throws DataLayerException {
    try{
            Login.setString(1,username);
            Login.setString(2,password);
            try (ResultSet rs=Login.executeQuery()){
                if(rs.next())
                    return createUtente(rs);
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load Login",ex);
        }
        return null;
    }
    
    @Override
    public Gruppo createGruppo() {
        return (Gruppo) new GruppoImpl(this);
    } 
    public Gruppo createGruppo(ResultSet rs) throws DataLayerException {
        try {
            GruppoImpl de = new GruppoImpl(this);
            
            de.setIDGruppo(rs.getInt("IDGruppo")); 
            de.setNome(rs.getString("Nome"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Gruppo object form ResultSet", ex);
        }
   }
    
    @Override
    public Gruppo getGruppo(int IDGruppo) throws DataLayerException {
        try {
            sGruppoByID.setInt(1,IDGruppo);
            try (ResultSet rs=sGruppoByID.executeQuery()) {
                if(rs.next())
                    return createGruppo(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Gruppo by ID",ex);
        }
        return null;
    }
    
    @Override
    public Servizio createServizio() {
        return new ServizioImpl(this);
    }    
    public Servizio createServizio(ResultSet rs) throws DataLayerException {
        try {
            Servizio de = new ServizioImpl(this);
            
            de.setIDServizio(rs.getInt("IDServizio")); 
            de.setScript(rs.getString("Script"));
            de.setDescrizione(rs.getString("Descrizione"));  
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Servizio object form ResultSet", ex);
        }
   }
    
    @Override
    public Servizio getServizio(int IDServizio) throws DataLayerException {
        try {
            sServizioByID.setInt(1,IDServizio);
            try (ResultSet rs=sServizioByID.executeQuery()) {
                if(rs.next())
                    return createServizio(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Servizio by ID",ex);
        }
        return null;
    }
    
    @Override
    public List<Servizio> getServiziPerGruppo(Gruppo gruppo) throws DataLayerException {
        List<Servizio> result = new ArrayList();
        try{
            sServiziByGruppo.setInt(1, gruppo.getIDGruppo());
            try (ResultSet rs=sServiziByGruppo.executeQuery()){
                while(rs.next())
                    result.add(getServizio(rs.getInt("Servizio")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load ServiziPerGruppo by Gruppo",ex);
        }
        return result;
    }
    
    @Override
    public void destroy() {
        try {
            sUtentiByGruppo.close();
            sUtenteByID.close();
            Login.close();
            sGruppoByID.close();
            sServiziByGruppo.close();
            sServizioByID.close();
            
        }catch (SQLException ex) {
            //
        }
        super.destroy();
    }

}
