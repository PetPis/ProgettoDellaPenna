/*
 * N.B Attenzione la liste vanno messe in caso di relazioni n-n ed 1-n
 *     dalla parte dell'1 (1-n), o ad entrambe dalla parte dell'n (n-n)
 */
package courseweb.model.interfacce;
import courseweb.controller.data.DataLayerException;
import java.util.Calendar;
import java.util.List;
/**
 *
 @author Toni $ Tony
 */
public interface CDL {
    
    void setIDCDL(int id);
    
    int getIDCDL();
    
    String getNome_it();
    
    void setNome_it(String nome_it);
    
    String getAbbr_it();
    
    void setAbbr_it(String abbr);
    
    String getNome_en();
    
    void setNome_en(String nome_en);
    
    String getAbbr_en();
    
    void setAbbr_en(String abbr);
    
    int getAnno();

    void setAnno(int anno);
    
    int getCfu();
    
    void setCfu(int cfu);
    
    int getMagistrale();
    
    void setMagistrale(int magistrale);
    
    String getImmagine();
    
    void setImmagine(String immagine);
    
    String getDescrizione_it();
    
    void setDescrizione_it(String descrizione_it);
    
    String getDescrizione_en();
    
    void setDescrizione_en(String descrizione_en);
    
    
    List<Corso> getCorsiInCdl() throws DataLayerException;
    
    void setCorsiInCdl(List<Corso> corsi);
    
    void addCorsoInCdl(Corso corso);

    public boolean isDirty();
    
    public void copyFrom(CDL cdl);
    
    public void setDirty(boolean dirty);
}
