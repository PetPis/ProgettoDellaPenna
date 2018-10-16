/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.sql.Timestamp;

/**
 *
 * @author Toni & Tony
 */
public interface Log {
    
    void setIDUtente(int id_utente);
    
    void setIDLog(int id);
    
    int getIDLog();
    
    Timestamp getData();
    
    String getDescrizione();
    
    Utente getUtente() throws DataLayerException;
    
    void setUtente(Utente utente);
    
    void setData(Timestamp data);
    
    void setDescrizione(String Descrizione);
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Log log);

}
