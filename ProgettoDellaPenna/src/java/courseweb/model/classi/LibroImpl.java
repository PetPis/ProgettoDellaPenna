/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;
import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Toni & Tony
 */
public class LibroImpl implements Libro {
    
    private int IDLibro;
    private String Autore;
    private String Titolo;
    private int Volume;
    private int Anno;
    private String Editore;
    private String Link;
    private List<Corso> Corso;
    protected IgwDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public LibroImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.Corso=null;
        this.IDLibro=0;
        this.Autore=null;
        this.Titolo=null;
        this.Volume=0;
        this.Anno=0;
        this.Editore=null;
        this.Link=null;
        this.dirty=false;
    }
    
    @Override
    public void setIDLibro(int id){
        this.IDLibro=id;
        this.dirty=true;
    }
    
    @Override
    public int getIDLibro() {
        return this.IDLibro;
    }

    @Override
    public String getAutore() {
        return this.Autore;
    }
    

    @Override
    public String getTitolo() {
        return this.Titolo;
    }

    @Override
    public int getVolume() {
        return this.Volume;
    }

    @Override
    public int getAnno() {
        return this.Anno;
    }

    @Override
    public String getEditore() {
        return this.Editore;
    }

    @Override
    public String getLink() {
        return this.Link;
    }

    @Override
    public void setAutore(String autore) {
        this.Autore=autore;
        this.dirty=true;
    }

    @Override
    public void setTitolo(String titolo) {
        this.Titolo=titolo;
        this.dirty=true;
    }

    @Override
    public void setVolume(int volume) {
        this.Volume=volume;
        this.dirty=true;
    }

    @Override
    public void setAnno(int anno) {
        this.Anno=anno;
        this.dirty=true;
    }

    @Override
    public void setEditore(String editore) {
        this.Editore=editore;
        this.dirty=true;
    }

    @Override
    public void setLink(String link) {
        this.Link=link;
        this.dirty=true;
    }

    @Override
    public List<Corso> getCorsi() throws DataLayerException {
        if(Corso==null)
            Corso=ownerdatalayer.getCorsiDelLibro(this);
        return Corso;
    }

    @Override
    public void setCorsi(List<Corso> corsi) {
        this.Corso=corsi;
        this.dirty=true;
    }

    @Override
    public void addCorsi(Corso corso) {
        this.Corso.add(corso);
        this.dirty=true;
    }
    
    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void copyFrom(Libro libro) {
        IDLibro=libro.getIDLibro();
        Autore=libro.getAutore();
        Titolo=libro.getTitolo();
        Volume=libro.getVolume();
        Anno=libro.getAnno();
        Editore=libro.getEditore();
        Link=libro.getLink();
        
        try{
            Corso=libro.getCorsi();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }

}
