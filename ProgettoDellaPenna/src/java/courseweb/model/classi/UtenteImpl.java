/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.Gruppo;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Utente;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteImpl implements Utente{
    
    private int id;
    
    private String username;
    
    private String password;
    
    private int docente;
    
    private Gruppo gruppo;
    
    private int id_gruppo;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    public UtenteImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id=0;
        this.username=null;
        this.password=null;
        this.docente=0;
        this.gruppo=null;
        this.dirty=false;
        this.id_gruppo=-1;
    }
    
    public void setIDGruppo(int id_gruppo){
        this.id_gruppo=id_gruppo;
    }
    
    public int getID(){
        return this.id;
    }
    
    @Override
    public void setID(int id){
        this.id=id;
        this.dirty=true;
    }
    
    @Override
    public String getUsername(){
        return this.username;
    }
    
    @Override
    public String getPassword(){
        return this.password;
    }
    
    @Override
    public int getDocente(){
        return this.docente;
    }
    
    @Override
    public void setGruppo(Gruppo gruppo){
        this.gruppo=gruppo;
        this.dirty=true;
    }
    
    @Override
    public void setUsername(String username){
        this.username=username;
        this.dirty=true;
    }
    
    @Override
    public void setPassword(String password){
        this.password=password;
        this.dirty=true;
    }
    
    @Override
    public void setDocente(int docente){
        this.docente=docente;
        this.dirty=true;
    }
    
    @Override
    public Gruppo getGruppo() throws DataLayerException{
        if(gruppo==null&&id_gruppo>=0)
            gruppo=ownerdatalayer.getGruppo(id_gruppo);
        return this.gruppo;
    }
    
    @Override
    public boolean isDirty() {
        return dirty;
    }   


    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void copyFrom(Utente utente) {
        
        id = utente.getID();
        username = utente.getUsername();
        password = utente.getPassword();
        
        try{
           gruppo = utente.getGruppo();
           docente = utente.getDocente();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
}
