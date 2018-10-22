/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Descrizione_itImpl implements Descrizione_it{
    
    private Corso corso;
    
    private int id_corso;
    
    private String prerequisiti;
    
    private String obiettivi;
    
    private String mod_esame;
    
    private String mod_insegnamento;
    
    private String sillabo;
    
    private String note;
    
    private String homepage;
    
    private String forum;
    
    private String risorse_ext;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    
    public Descrizione_itImpl(IgwDataLayer ownerdatalayer){
    this.ownerdatalayer=ownerdatalayer;
    this.corso=null;
    this.prerequisiti=null;
    this.obiettivi=null;
    this.mod_esame=null;
    this.mod_insegnamento=null;
    this.sillabo=null;
    this.note=null;
    this.homepage=null;
    this.forum=null;
    this.risorse_ext=null;
    this.id_corso=-1;
    this.dirty=false;
}

    
    public void setIDCorso(int id_corso) {
        this.id_corso=id_corso;
        this.dirty=true;
    }
    
    
    
    
    @Override
    public Corso getCorso() throws DataLayerException{
        if(corso==null)
            corso=ownerdatalayer.getCorso(id_corso);
        return corso;
    }

    @Override
    public void setCorso(Corso corso) {
        this.corso=corso;
        this.dirty=true;
    }

    @Override
    public String getPrerequisiti() {
        return this.prerequisiti;
    }

    @Override
    public void setPrerequisiti(String prerequisiti) {
        this.prerequisiti=prerequisiti;
        this.dirty=true;
    }

    @Override
    public String getObiettivi() {
        return this.obiettivi;
    }

    @Override
    public void setObiettivi(String obiettivi) {
        this.obiettivi=obiettivi;
        this.dirty=true;
    }

    @Override
    public String getMod_Esame() {
        return this.mod_esame;
    }

    @Override
    public void setMod_Esame(String mod_esame) {
        this.mod_esame=mod_esame;
        this.dirty=true;
    }

    @Override
    public String getMod_Insegnamento() {
        return this.mod_insegnamento;
    }

    @Override
    public void setMod_Insegnamento(String mod_insegnamento) {
        this.mod_insegnamento=mod_insegnamento;
        this.dirty=true;
    }

    @Override
    public String getSillabo() {
        return this.sillabo;
    }

    @Override
    public void setSillabo(String sillabo) {
        this.sillabo=sillabo;
        this.dirty=true;
    }

    @Override
    public String getNote() {
        return this.note;
    }

    @Override
    public void setNote(String note) {
        this.note=note;
        this.dirty=true;
    }

    @Override
    public String getHomepage() {
        return this.homepage;
    }

    @Override
    public void setHomepage(String Homepage) {
        this.homepage=Homepage;
        this.dirty=true;
    }

    @Override
    public String getForum() {
        return this.forum;
    }

    @Override
    public void setForum(String forum) {
        this.forum=forum;
        this.dirty=true;
    }

    @Override
    public String getRisorse_Ext() {
        return this.risorse_ext;
    }

    @Override
    public void setRisorse_Ext(String risorse) {
        this.risorse_ext=risorse;
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
    public void copyFrom(Descrizione_it descrizione) {
        prerequisiti=descrizione.getPrerequisiti();
        obiettivi=descrizione.getObiettivi();
        mod_esame=descrizione.getMod_Esame();
        mod_insegnamento=descrizione.getMod_Insegnamento();
        sillabo=descrizione.getSillabo();
        note=descrizione.getNote();
        homepage=descrizione.getHomepage();
        forum=descrizione.getForum();
        risorse_ext=descrizione.getRisorse_Ext();
        
        try{
            corso=descrizione.getCorso();
            id_corso=descrizione.getCorso().getID();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
}