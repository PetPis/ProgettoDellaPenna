/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.Gruppo;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Servizio;
import courseweb.model.interfacce.Utente;
import java.util.List;

public class GruppoImpl implements Gruppo{
    
    private int id;
    
    private String nome;
    
    private List<Utente> utenti;
    
    private List<Servizio> servizi;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    public GruppoImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id=0;
        this.nome=null;
        this.utenti=null;
        this.servizi=null;
        this.dirty=false;
    }
    
    @Override
    public int getIDGruppo(){
        return this.id;
    }
    
    @Override
    public void setIDGruppo(int id){
        this.id=id;
        this.dirty=true;
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
    public List<Servizio> getServizi() throws DataLayerException{
        if(servizi==null)
            servizi=ownerdatalayer.getServiziPerGruppo(this);
        return servizi;
    }
}
