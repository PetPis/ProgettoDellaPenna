/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.util.List;

public interface Gruppo {
    
    void setIDGruppo(int id);
    
    int getIDGruppo();
    
    String getNome();
    
    void setNome(String nome);
    
    List<Servizio> getServizi() throws DataLayerException;
}
