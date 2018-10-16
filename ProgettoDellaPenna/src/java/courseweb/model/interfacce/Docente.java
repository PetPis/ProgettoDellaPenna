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
 * @author Toni & Tony
 */
public interface Docente{
    
    void setIDDocente(int id);
 
    int getIDDocente();
    
    String getImmagine();
    
    String getNome();
    
    String getCognome();
    
    String getEmail();
    
    String getUfficio();
    
    String getTelefono();
    
    String getSpecializzazione();
    
    String getRicerche();
    
    String getPubblicazione();
    
    String getCurriculum();
    
    String getRicevimento();
    
    void setImmagine(String Immagine);
    
    void setNome(String nome);
    
    void setCognome(String cognome);
    
    void setEmail(String email);
    
    void setUfficio(String ufficio);
    
    void setTelefono(String telefono);

    void setSpecializzazione(String specializzazione);
    
    void setRicerche(String ricerche);
    
    void setPubblicazioni(String pubblicazioni);
    
    void setCurriculum(String curriculum);
    
    void setRicevimento(String ricevimento);
    
    List<Corso> getCorsi() throws DataLayerException;
    
    void setCorsi(List<Corso> corsi);
    
    void addCorso(Corso corso);

    boolean isDirty();

    public void copyFrom(Docente docente);

    public void setDirty(boolean dirty);
}
