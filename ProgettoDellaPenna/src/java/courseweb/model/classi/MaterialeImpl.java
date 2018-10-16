/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Materiale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toni & Tony
 */
public class MaterialeImpl implements Materiale{
    
    private int id;
    
    private int id_corso;
    
    private Corso corso;
    
    private String nome;
    
    private String link;
    
    private String descrizione_it;
    
    private String descrizione_en;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    public MaterialeImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id=0;
        this.corso=null;
        this.nome=null;
        this.descrizione_en=null;
        this.descrizione_it=null;
        this.link=null;
        this.dirty=false;
        this.id_corso=-1;
    }
    
    @Override
    public int getID(){
        return this.id;
    }
    
    @Override
    public void setID(int id){
        this.id=id;
        this.dirty=true;
    }
    
    @Override
    public Corso getCorso() throws DataLayerException{
        if(corso==null&&id_corso>0)
            corso=ownerdatalayer.getCorso(id_corso);
        return corso;
    }
    
    @Override
    public String getNome(){
        return this.nome;
    }
    
    @Override
    public void setNome(String nome){
        this.nome=nome;
        this.dirty=true;
    }
    
    @Override
    public String getLink(){
        return this.link;
    }
    
    @Override
    public void setLink(String link){
        this.link=link;
        this.dirty=true;
    }
    
    @Override
    public String getDescrizione_it(){
        return this.descrizione_it;
    }
    
    @Override
    public void setDescrizione_it(String descrizione_it){
        this.descrizione_it=descrizione_it;
        this.dirty=true;
    }
    
    @Override
    public String getDescrizione_en(){
        return this.descrizione_en;
    }
    
    @Override
    public void setDescrizione_en(String descrizione_en){
        this.descrizione_en=descrizione_en;
        this.dirty=true;
    }

    
    @Override
    public void setIDCorso(int id_corso) {
        this.id_corso=id_corso;
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
    public void copyFrom(Materiale materiale) {
        id=materiale.getID();
        nome=materiale.getNome();
        link=materiale.getLink();
        descrizione_it=materiale.getDescrizione_it();
        descrizione_en=materiale.getDescrizione_en();
        
        try{
            corso=materiale.getCorso();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }

    
        
}
