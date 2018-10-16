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

public interface Corso {
    
    void setID(int id);
    
    int getID();
    
    List<CDL> getCDL() throws DataLayerException;
    
    void setCDLInCorso(List<CDL> cdl);
    
    void addCDLInCorso(CDL cdl);
    
    String getNome_it();
    
    String getNome_en();
    
    void setNome_it(String nome_it);
    
    void setNome_en(String nome_en);
    
    String getSSD();
    
    void setSSD(String ssd);
    
    String getLingua();
    
    void setLingua(String lingua);
    
    int getSemestre();
    
    void setSemestre(int semestre);
    
    int getCfu();
    
    void setCfu(int cfu);
    
    int getAnno();
    
    void setAnno(int anno);
    
    char getTipologia();
    
    void setTipologia(char tipologia);
    
    List<Corso> getCorsiMutuati() throws DataLayerException;
    
    void setCorsiMutuati(List<Corso> mutuati);
    
    Corso getCorsoMutua() throws DataLayerException;
    
    void setCorsoMutua(Corso mutua);
    
    List<Corso> getCorsiPrerequisiti() throws DataLayerException;
    
    void setCorsiPrerequisiti(List<Corso> prerequisiti);
    
    List<Corso> getCorsiModulo() throws DataLayerException;
    
    void setCorsiModulo(List<Corso> modulo);
    
    void addCorsoMutuato(Corso corso);
    
    void addCorsoPrerequisiti(Corso corso);
    
    void addCorsoModulo(Corso corso);
    
    List<Docente> getDocenti() throws DataLayerException;
    
    void setDocenti(List<Docente> docenti);
    
    void addDocente(Docente docente);
    
    List<Libro> getLibri() throws DataLayerException;
    
    void setLibri(List<Libro> libri);
    
    void addLibro(Libro libro);
    
    List<Materiale> getMateriale() throws DataLayerException;
    
    void setMateriale(List<Materiale> materiale);
    
    void addMateriale(Materiale materiale);
    
    public void setDirty(boolean dirty);
    
    public boolean isDirty();
    
    public void copyFrom(Corso corso);
    
    public int getOldID();
    
    public void setOldID(int id);
    
    public List<Corso> getAnniPrecedenti() throws DataLayerException;
    
    public void setAnniPrecedenti(List<Corso> corso);
}
