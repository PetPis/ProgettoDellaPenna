/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.data.DataLayerMysqlImpl;
import courseweb.model.interfacce.IgwDataLayer;
//import all classes and interfaces
import static java.lang.Character.toUpperCase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IgwDataLayerMysqlImpl extends DataLayerMysqlImpl implements IgwDataLayer {

    //private prepareStatement
    
    @Override
    public void init() throws DataLayerException {
        //try {
            super.init();
            
        
            
            //queryName = connection.prepareStatement("query");
            
            
        /*} catch (SQLException ex) {
            throw new DataLayerException("Error initializing igw data layer", ex);
        }*/
    }
    
    
    public IgwDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

}