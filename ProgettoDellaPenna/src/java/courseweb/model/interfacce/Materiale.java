/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.util.List;

/**
 *
 * @author Tony & Tony
 */
public interface Materiale {
    
    void setID(int id);
    
    int getID();
    
    String getNome();
    
    void setNome(String nome);
    
    String getLink();
    
    void setLink(String link);
    
    String getDescrizione_it();
    
    void setDescrizione_it(String descrizione_it);
    
    String getDescrizione_en();
    
    void setIDCorso(int id_corso);
    
    void setDescrizione_en(String descrizione_en);
    
    public Corso getCorso() throws DataLayerException;
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Materiale materiale);
    
    
}
