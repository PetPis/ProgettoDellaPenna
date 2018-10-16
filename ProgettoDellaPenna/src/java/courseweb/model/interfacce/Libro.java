/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.time.Year;
import java.util.List;

/**
 *
 * @author Toni & Tony
 */
public interface Libro {
    
    int getIDLibro();
    
    String getAutore();
    
    String getTitolo();
    
    int getVolume();
    
    int getAnno();
    
    String getEditore();
    
    String getLink();
    
    void setIDLibro(int id);
    
    void setAutore(String autore);
    
    void setTitolo(String titolo);
    
    void setVolume(int volume);
    
    void setAnno(int anno);
    
    void setEditore(String editore);
    
    void setLink(String link);
    
    List<Corso> getCorsi() throws DataLayerException;
    
    void setCorsi(List<Corso> corsi);
    
    void addCorsi(Corso corso);
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Libro libro);
    
}
