/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;

/**
 *
 * @author Tony
 */
public interface Group_Services {
    
    Servizio getServizio() throws DataLayerException;
       
    Gruppo getGruppo() throws DataLayerException;
    
    
    void setServizio(Servizio servizio);
    
    void setGruppo(Gruppo gruppo);
}
