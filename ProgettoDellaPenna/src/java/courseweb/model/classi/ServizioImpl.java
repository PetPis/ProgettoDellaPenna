/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;
import courseweb.model.interfacce.Servizio;
import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.Gruppo;
import courseweb.model.interfacce.IgwDataLayer;
import java.util.List;

public class ServizioImpl implements Servizio {

    private int IDServizio;
    private String Script;
    private String Descrizione;
    private List<Gruppo> Gruppi;
    protected IgwDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public ServizioImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.IDServizio=0;
        this.Script=null;
        this.Descrizione=null;
        this.Gruppi=null;
        this.dirty=false;
    }
    
    @Override
    public int getIDServizio() {
        return this.IDServizio;
    }

    @Override
    public String getScript() {
        return this.Script;
    }

    @Override
    public String getDescrizione() {
        return this.Descrizione;
    }

    @Override
    public void setIDServizio(int idservizio) {
        this.IDServizio=idservizio;
        this.dirty=true;
    }

    @Override
    public void setScript(String script) {
        this.Script=script;
        this.dirty=true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.Descrizione=descrizione;
        this.dirty=true;
    }

    @Override
    public List<Gruppo> getGruppi() throws DataLayerException {
      if(Gruppi==null)
          Gruppi=ownerdatalayer.getGruppiDelServizio(this);
      return Gruppi;
    }

    @Override
    public void setGruppi(List<Gruppo> gruppi) {
        this.Gruppi=gruppi;
        this.dirty=true;
    }

    @Override
    public void addGruppo(Gruppo gruppo) {
        this.Gruppi.add(gruppo);
        this.dirty=true;
    }

    @Override
    public void setIDGruppo(int id_gruppo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
