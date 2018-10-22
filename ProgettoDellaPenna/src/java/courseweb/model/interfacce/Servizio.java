/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.util.List;

public interface Servizio {
    
    int getIDServizio();
    
    String getScript();
    
    String getDescrizione();
    
    void setIDServizio(int idservizio);
    
    void setScript(String script);
    
    void setDescrizione(String descrizione);
    
    List<Gruppo> getGruppi() throws DataLayerException;
    
    void setGruppi(List<Gruppo> gruppi);
    
    void addGruppo(Gruppo gruppo);

    public void setIDGruppo(int aInt);
    
}
