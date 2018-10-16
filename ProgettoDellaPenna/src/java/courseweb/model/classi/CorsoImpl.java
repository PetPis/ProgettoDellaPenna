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
public class CorsoImpl implements Corso{
    
    private int id;
    
    private List<CDL> cdl;
    
    private String nome_it;
    
    private String nome_en;
    
    private String ssd;
    
    private String lingua;
    
    private int semestre;
    
    private int cfu;
    
    private int anno;
    
    private char tipologia;
    
    private int oldID;
    
    private List<Corso> precedenti;
    
    
    //assumiamo che un corso possa mutuare(sostituire) più corsi, e che lo stesso possa essere mutuato (sostituito) da un solo corso
    private List<Corso> mutuati;
    
    private Corso mutuato;
    
    
    private List<Corso> prerequisiti;
    
    private List<Corso> modulo;
    
    private List<Docente> docenti;
    
    private List<Libro> libri;
    
    private List<Materiale> materiale;
    
    protected IgwDataLayer ownerdatalayer;
    
    protected boolean dirty;
    
    
    public CorsoImpl(IgwDataLayer ownerdatalayer){
        this.ownerdatalayer=ownerdatalayer;
        this.id=0;
        this.cdl=null;
        this.nome_it=null;
        this.nome_en=null;
        this.ssd=null;
        this.lingua=null;
        this.semestre=0;
        this.cfu=0;
        this.anno=0;
        this.tipologia=0;
        this.mutuati=null;
        this.mutuato=null;
        this.prerequisiti=null;
        this.modulo=null;
        this.docenti=null;
        this.libri=null;
        this.materiale=null;
        this.dirty=false;
        this.oldID=0;
        this.precedenti=null;
    }  
    
    @Override
    public void setID(int id){
        this.id=id;
        this.dirty=true;
    }
    
    @Override
    public int getID(){
        return this.id;
    }
    
    @Override
    public List<CDL> getCDL() throws DataLayerException{
        if(cdl==null)
            cdl=ownerdatalayer.getCDLInCorso(this);
        return cdl;
    }
    
    @Override
    public void setCDLInCorso(List<CDL> cdl){
        this.cdl=cdl;
        this.dirty=true;
    }
    
    @Override
    public void addCDLInCorso(CDL cdl){
        this.cdl.add(cdl);
        this.dirty=true;
    }
    
    @Override
    public String getNome_it(){
        return this.nome_it;
    }
    
    @Override
    public String getNome_en(){
        return this.nome_en;
    }
    
    @Override
    public void setNome_it(String nome){
        this.nome_it=nome;
        this.dirty=true;
    }
    
    @Override
    public void setNome_en(String nome){
        this.nome_en=nome;
        this.dirty=true;
    }
    
    @Override
    public String getSSD(){
        return this.ssd;
    }
    
    @Override
    public void setSSD(String ssd){
        this.ssd=ssd;
        this.dirty=true;
    }
    
    @Override
    public String getLingua(){
        return this.lingua;
    }
    
    @Override
    public void setLingua(String lingua){
        this.lingua=lingua;
        this.dirty=true;
    }
    
    @Override
    public int getSemestre(){
        return this.semestre;
    }
    
    @Override
    public void setSemestre(int semestre){
        this.semestre=semestre;
        this.dirty=true;
    }
    
    @Override
    public int getCfu(){
        return this.cfu;
    }
    
    @Override
    public void setCfu(int cfu){
        this.cfu=cfu;
        this.dirty=true;
    }
    
    @Override
    public int getAnno(){
        return this.anno;
    }
    
    @Override
    public void setAnno(int anno){
        this.anno=anno;
        this.dirty=true;
    }
    
    @Override
    public char getTipologia(){
        return this.tipologia;
    }
    
    @Override
    public void setTipologia(char tipologia){
        this.tipologia=tipologia;
        this.dirty=true;
    }
    
    @Override
    public List<Corso> getCorsiMutuati() throws DataLayerException{
        if(mutuati==null)
            mutuati=ownerdatalayer.getCorsiMutuati(this);
        return mutuati;
    }
    
    @Override
    public void setCorsiMutuati(List<Corso> mutuati){
        this.mutuati=mutuati;
        this.dirty=true;
    }
    
    @Override
    public Corso getCorsoMutua() throws DataLayerException{
        if(mutuato==null)
            mutuato=ownerdatalayer.getCorsoMutua(this);
        return mutuato;
    }
    
    @Override
    public void setCorsoMutua(Corso mutuato){
        this.mutuato=mutuato;
        this.dirty=true;
    }
    
    @Override
    public List<Corso> getCorsiPrerequisiti() throws DataLayerException{
        if(prerequisiti==null)
            prerequisiti=ownerdatalayer.getCorsiPrerequisiti(this);
        return prerequisiti;
    }
    
    @Override
    public void setCorsiPrerequisiti(List<Corso> prerequisiti){
        this.prerequisiti=prerequisiti;
        this.dirty=true;
    }
    
    @Override
    public List<Corso> getCorsiModulo() throws DataLayerException{
        if(modulo==null)
            modulo=ownerdatalayer.getCorsiModulo(this);
        return modulo;
    }
    
    @Override
    public void setCorsiModulo(List<Corso> corsi){
        this.modulo=corsi;
        this.dirty=true;
    }
    
    @Override
    public void addCorsoModulo(Corso corso){
        this.modulo.add(corso);
        this.dirty=true;
    }
    
    @Override
    public void addCorsoMutuato(Corso corso){
        this.mutuati.add(corso);
        this.dirty=true;
    }
    
    @Override
    public void addCorsoPrerequisiti(Corso corso){
        this.prerequisiti.add(corso);
        this.dirty=true;
    }
    
    @Override
    public List<Docente> getDocenti() throws DataLayerException{
        if(docenti==null)
            docenti=ownerdatalayer.getDocentiCorso(this);
        return docenti;
    }
    
    @Override
    public void setDocenti(List<Docente> docenti){
        this.docenti=docenti;
        this.dirty=true;
    }
    
    @Override
    public void addDocente(Docente docente){
        this.docenti.add(docente);
        this.dirty=true;
    }
    
    @Override
    public List<Libro> getLibri() throws DataLayerException{
        if(libri==null)
            libri=ownerdatalayer.getLibriCorso(this);
        return libri;
    }
    
    @Override
    public void setLibri(List<Libro> libri){
        this.libri=libri;
        this.dirty=true;
    }
    
    @Override
    public void addLibro(Libro libro){
        this.libri.add(libro);
        this.dirty=true;
    }
    
    @Override
    public List<Materiale> getMateriale() throws DataLayerException{
        if(materiale==null)
            materiale=ownerdatalayer.getMaterialeCorso(this.id);
        return materiale;
    }
    
    @Override
    public void setMateriale(List<Materiale> materiale){
        this.materiale=materiale;
    }
    
    @Override
    public void addMateriale(Materiale materiale){
        this.materiale.add(materiale);
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
    public void copyFrom(Corso corso) {
        id=corso.getID();
        nome_it=corso.getNome_it();
        nome_en=corso.getNome_en();
        ssd=corso.getSSD();
        lingua=corso.getLingua();
        semestre=corso.getSemestre();
        cfu=corso.getCfu();
        anno=corso.getAnno();
        tipologia=corso.getTipologia();
        
        
        
        try{
            cdl=corso.getCDL();
            mutuati=corso.getCorsiMutuati();
            prerequisiti=corso.getCorsiPrerequisiti();
            modulo=corso.getCorsiModulo();
            docenti=corso.getDocenti();
            libri=corso.getLibri();
            materiale=corso.getMateriale();
            
        }   
        catch (DataLayerException ex) {
            Logger.getLogger(DocenteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dirty = true;
    }
    
    @Override
    public int getOldID(){
        return this.oldID;
    }
    
    @Override
    public void setOldID(int id){
        this.oldID=id;
        this.dirty=true;
    }
    
    @Override
    public List<Corso> getAnniPrecedenti() throws DataLayerException{
        if(precedenti==null && oldID>0)
            precedenti=ownerdatalayer.getAnniPrecedenti(this);
        return precedenti;
    }
    
    @Override
    public void setAnniPrecedenti(List<Corso> precedenti){
        this.precedenti=precedenti;
        this.dirty=true;
    }
    
    public static boolean contains(List<Corso> list,Corso corso){
        for(Corso c:list)
            if(c.getID()==corso.getID())
                return true;
        return false;
    }
}
