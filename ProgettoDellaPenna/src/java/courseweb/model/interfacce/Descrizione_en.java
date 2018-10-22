/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;


public interface Descrizione_en {
    
    void setIDCorso(int id_corso);
    
    Corso getCorso() throws DataLayerException;
    
    void setCorso(Corso corso);
    
    String getPrerequisiti();
    
    void setPrerequisiti(String prerequisiti);
    
    String getObiettivi();
    
    void setObiettivi(String obietivi);
    
    String getMod_Esame();
    
    void setMod_Esame(String mod_esame);
    
    String getMod_Insegnamento();
    
    void setMod_Insegnamento(String mod_insegnamento);
    
    String getSillabo();
    
    void setSillabo(String sillabo);
    
    String getNote();
    
    void setNote(String note);
    
    String getHomepage();
    
    void setHomepage(String Homepage);
    
    String getForum();
    
    void setForum(String forum);
    
    String getRisorse_Ext();
    
    void setRisorse_Ext(String risorse);
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Descrizione_en descrizione);
}
