package courseweb.model.classi;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Dublino_enImpl implements Dublino_en{
    
    private Corso corso;
    
    private int id_corso;
    
    private String knowledge;
    
    private String application;
    
    private String evaluation;
    
    private String communication;
    
    private String lifelong;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    public Dublino_enImpl(IgwDataLayer ownerdatalayer){
    this.ownerdatalayer=ownerdatalayer;
    this.corso=null;
    this.knowledge=null;
    this.application=null;
    this.evaluation=null;
    this.communication=null;
    this.lifelong=null;
    this.id_corso=-1;
    this.dirty=false;
}
    
    @Override
    public void setIDCorso(int id_corso){
        this.id_corso=id_corso;
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
    public String getKnowledge() {
        return this.knowledge;
    }

    @Override
    public void setKnowledge(String knowledge) {
        this.knowledge=knowledge;
        this.dirty=true;
    }

    @Override
    public String getApplication() {
        return this.application;
    }

    @Override
    public void setApplication(String application) {
        this.application=application;
        this.dirty=true;
    }

    @Override
    public String getEvaluation() {
        return this.evaluation;
    }

    @Override
    public void setEvaluation(String evaluation) {
       this.evaluation=evaluation;
       this.dirty=true;
    }

    @Override
    public String getCommunication() {
        return this.communication;
    }

    @Override
    public void setCommunication(String communication) {
        this.communication=communication;
        this.dirty=true;
    }

    @Override
    public String getLifelong() {
        return this.lifelong;
    }

    @Override
    public void setLifelong(String lifelong) {
        this.lifelong=lifelong;
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
    public void copyFrom(Dublino_en dublino) {
        knowledge=dublino.getKnowledge();
        application=dublino.getApplication();
        evaluation=dublino.getEvaluation();
        communication=dublino.getCommunication();
        lifelong=dublino.getLifelong();
        
        try{
            corso=dublino.getCorso();
            id_corso=dublino.getCorso().getID();
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
    
    
    
}