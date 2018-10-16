/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.*;
import courseweb.controller.data.DataLayerException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Toni & Tony
 */
public class DocenteImpl implements Docente {
    
    private int id_docente;
    
    private String immagine;
    
    private String nome;
    
    private String cognome;
    
    private String email;
    
    private String ufficio;
    
    private String telefono;
    
    private String specializzazione;
    
    private String ricerche;
    
    private String pubblicazione;
    
    private String curriculum;
    
    private String ricevimento;
    
    private List<Corso> corsi;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    
    public DocenteImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id_docente=0;
        this.nome=null;
        this.cognome=null;
        this.email=null;
        this.ufficio=null;
        this.telefono=null;
        this.specializzazione=null;
        this.ricerche=null;
        this.pubblicazione=null;
        this.curriculum=null;
        this.ricevimento=null;
        this.corsi=null;
        this.dirty=false;
    }
    
    @Override
    public void setIDDocente(int id){
        this.id_docente=id;
        this.dirty=true;
    }
    
    @Override
    public int getIDDocente(){
        return this.id_docente;
    }

    @Override
    public String getImmagine() {
        return this.immagine;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUfficio() {
        return this.ufficio;
    }

    @Override
    public String getTelefono() {
        return this.telefono;
    }

    @Override
    public String getSpecializzazione() {
        return this.specializzazione;
    }

    @Override
    public String getRicerche() {
        return this.ricerche;
    }

    @Override
    public String getPubblicazione() {
       return this.pubblicazione;
    }

    @Override
    public String getCurriculum() {
        return this.curriculum;
    }

    @Override
    public String getRicevimento() {
        return this.ricevimento;
    }

    @Override
    public void setImmagine(String Immagine) {
        this.immagine=Immagine;
        this.dirty=true;
    }
    

    @Override
    public void setNome(String nome) {
        this.nome=nome;
        this.dirty=true;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome=cognome;
        this.dirty=true;
    }

    @Override
    public void setEmail(String email) {
        this.email=email;
        this.dirty=true;
    }

    @Override
    public void setUfficio(String ufficio) {
       this.ufficio=ufficio;
       this.dirty=true;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono=telefono;
        this.dirty=true;
    }

    @Override
    public void setSpecializzazione(String specializzazione) {
        this.specializzazione=specializzazione;  
        this.dirty=true;
    }

    @Override
    public void setRicerche(String ricerche) {
        this.ricerche=ricerche;
        this.dirty=true;
    }

    @Override
    public void setPubblicazioni(String pubblicazioni) {
        this.pubblicazione=pubblicazioni;
        this.dirty=true;
    
    }

    @Override
    public void setCurriculum(String curriculum) {
        this.curriculum=curriculum;
        this.dirty=true;
    }

    @Override
    public void setRicevimento(String ricevimento) {
        this.ricevimento=ricevimento;
        this.dirty=true;
    }

    @Override
    public List<Corso> getCorsi() throws DataLayerException {
        if(corsi==null)
            corsi=ownerdatalayer.getCorsiDelDocente(this);
        return corsi;
    }

    @Override
    public void setCorsi(List<Corso> corsi){
        this.corsi=corsi;
        this.dirty=true;
    }
    
    @Override
    public void addCorso(Corso corso){
        this.corsi.add(corso);
        this.dirty=true;
    }

     @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void copyFrom(Docente docente) {
        
        id_docente = docente.getIDDocente();
        immagine = docente.getImmagine();
        nome = docente.getNome();
        cognome = docente.getCognome();
        email = docente.getEmail();
        ufficio = docente.getUfficio();
        telefono = docente.getTelefono();
        specializzazione = docente.getSpecializzazione();
        ricerche = docente.getRicerche();
        pubblicazione = docente.getPubblicazione();
        curriculum = docente.getCurriculum();
        ricevimento = docente.getRicevimento();
        try{
            corsi=docente.getCorsi();           
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
    


    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    public static boolean contains(List<Docente> list,Docente docente){
        for(Docente doc:list)
            if(doc.getIDDocente()==docente.getIDDocente())
                return true;
        return false;
    }
   
}
