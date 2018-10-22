/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;

public interface Dublino_en {
    
    void setIDCorso(int id_corso);
    
    Corso getCorso() throws DataLayerException;
    
    void setCorso(Corso corso);
    
    String getKnowledge();
    
    void setKnowledge(String knowledge);
    
    String getApplication();
    
    void setApplication(String application);
    
    String getEvaluation();
    
    void setEvaluation(String evaluation);
    
    String getCommunication();
    
    void setCommunication(String communication);
    
    String getLifelong();
    
    void setLifelong(String lifelong);
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Dublino_en dublino);
    
}
