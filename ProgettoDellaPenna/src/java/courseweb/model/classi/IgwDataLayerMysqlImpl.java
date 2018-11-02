/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.classi;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.data.DataLayerMysqlImpl;
import courseweb.model.classi.CDLImpl;
import courseweb.model.classi.CorsoImpl;
import courseweb.model.classi.Descrizione_enImpl;
import courseweb.model.classi.Descrizione_itImpl;
import courseweb.model.classi.DocenteImpl;
import courseweb.model.classi.Dublino_enImpl;
import courseweb.model.classi.Dublino_itImpl;
import courseweb.model.classi.GruppoImpl;
import courseweb.model.classi.LibroImpl;
import courseweb.model.classi.LogImpl;
import courseweb.model.classi.MaterialeImpl;
import courseweb.model.classi.ServizioImpl;
import courseweb.model.classi.UtenteImpl;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.Descrizione_en;
import courseweb.model.interfacce.Descrizione_it;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.Dublino_en;
import courseweb.model.interfacce.Dublino_it;
import courseweb.model.interfacce.Gruppo;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
import courseweb.model.interfacce.Log;
import courseweb.model.interfacce.Materiale;
import courseweb.model.interfacce.Servizio;
import courseweb.model.interfacce.Utente;
import static java.lang.Character.toUpperCase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IgwDataLayerMysqlImpl extends DataLayerMysqlImpl implements IgwDataLayer {

    private PreparedStatement sCorsiMutuatiByCorso,sCorsiPrerequisitiByCorso,sCorsiModuloByCorso,sDocentiByCorso,sLibriByCorso,sAllCorsi,sMaterialeByCorso,sCorsiByCDL,sUtentiByGruppo,sServiziByGruppo,sCorsiByDocente,sCorsiByLibro,sGruppiByServizio,sCorsi,sDocenti,sCDL,sCdlByMagistrale,sCdlByTriennale,sUtenteByDocente;
    private PreparedStatement sCDLByID,sCorsoByID,sDocenteByID,sDescrizione_itByCorso,sDescrizione_enByCorso,sDublino_itByCorso,sDublino_enByCorso,sMaterialeByID,sLibroByID,sGruppoByID,sUtenteByID,sServizioByID,sLogByID,sCorsiByAnno,sCDLByCorso,Login,sCorsoMutuaByCorso,sCorsiByCDLNoAnno,sAccess;
    
    private PreparedStatement iDocente, iUtente,iCorso,iDocentiCorso,iCDL,iCDLCorso,iColleg_Corso,iDescrizione_it,iDescrizione_en,iDublino_it,iDublino_en,iMateriale,iLibro,iLibri_Corso;
    private PreparedStatement uDocente, uUtente,uCorso,uCDL,uMateriale,uLibro;

    private PreparedStatement dDocente,dDocentiCorso,dCDLCorso,dColleg_Corso,dCorso,dDescrizione_it,dDescrizione_en,dDublino_it,dDublino_en,dMaterialeCorso,dAllLibriCorso,dLibro,dAllDocCorso,dAllCDLCorso,dThis_Corso,dOther_Corso,dCDLinColl,dCDL,dUtente,dCorsiDocente,dMateriale,dLibriCorso;
    
    private PreparedStatement iLog;
    

    private PreparedStatement checkUtente,sLog;
    
    @Override
    public void init() throws DataLayerException {
        try {
            super.init();
            
            //CARO BABBO NATALE VOGLIO UN LIST DI TUTTI GLI UTENTI E UN SELECT CORSI CDL BY ANNO, GRAZIE <3
            
            
            sLog=connection.prepareStatement("SELECT * FROM Log");
            sCDLByID=connection.prepareStatement("SELECT * FROM CDL WHERE IDCDL=?");
            sCorsoByID=connection.prepareStatement("SELECT * FROM Corso WHERE IDCorso=?");
            sDocenteByID=connection.prepareStatement("SELECT * FROM Docente WHERE IDDocente=?");
            sDescrizione_itByCorso=connection.prepareStatement("SELECT * FROM Descrizione_it WHERE Corso=?");
            sDescrizione_enByCorso=connection.prepareStatement("SELECT * FROM Descrizione_en WHERE Corso=?");
            sDublino_itByCorso=connection.prepareStatement("SELECT * FROM Dublino_it WHERE Corso=?");
            sDublino_enByCorso=connection.prepareStatement("SELECT * FROM Dublino_en WHERE Corso=?");
            sMaterialeByID=connection.prepareStatement("SELECT * FROM Materiale WHERE IDMateriale=?");
            sLibroByID=connection.prepareStatement("SELECT * FROM Libro WHERE IDLibro=?");
            sGruppoByID=connection.prepareStatement("SELECT * FROM Gruppo WHERE IDGruppo=?");
            sUtenteByID=connection.prepareStatement("SELECT * FROM Utente WHERE IDUtente=?");
            sServizioByID=connection.prepareStatement("SELECT * FROM Servizio WHERE IDServizio=?");
            sLogByID=connection.prepareStatement("SELECT * FROM Log WHERE IDLog=?");
            sCorsiByAnno=connection.prepareStatement("SELECT * FROM Corso WHERE Anno=?");
            sAllCorsi=connection.prepareStatement("SELECT * FROM Corso");
            sCDLByCorso=connection.prepareStatement("SELECT * FROM CDL,Corsi_CDL WHERE CDL.IDCDL=Corsi_CDL.CDL AND Corso=?");
            sCorsoMutuaByCorso=connection.prepareStatement("SELECT * FROM Colleg_Corsi,Corso WHERE This_Corso=IDCorso AND Other_Corso=? AND Tipo='Mutuato'");
           
            sCdlByMagistrale = connection.prepareStatement("SELECT * FROM CDL WHERE Magistrale=1");
            sCdlByTriennale = connection.prepareStatement("SELECT * FROM CDL WHERE Magistrale=0");
            
            Login=connection.prepareStatement("SELECT * FROM Utente WHERE BINARY Utente.Username=? AND BINARY Utente.Password=?");
            
            PreparedStatement LoginD=connection.prepareStatement("SELECT * FROM Utente,Gruppo,Docente INNER JOIN Utente.gruppo=Gruppo.IDGruppo AND Utente.Docente=Docente.IDDocente WHERE BINARY Utente.Username=? AND BINARY Utente.Password=? AND Gruppo.IDgruppo=?;");

            sCorsiByCDLNoAnno=connection.prepareStatement("SELECT Corso FROM Corsi_CDL,Corso WHERE Corso=IDCorso AND CDL=? ORDER BY Anno DESC");       
            
            
            sDocenti=connection.prepareStatement("SELECT IDDocente FROM Docente");
            sCorsi=connection.prepareStatement("SELECT IDCorso FROM Corso WHERE Anno<?");   /*LOOK*/
            sCDL = connection.prepareStatement("SELECT IDCDL FROM CDL");
            sCorsiMutuatiByCorso=connection.prepareStatement("SELECT Other_Corso FROM Colleg_Corsi WHERE This_Corso=? AND Tipo='Mutuato'");
            sCorsiPrerequisitiByCorso=connection.prepareStatement("SELECT Other_Corso FROM Colleg_Corsi WHERE This_Corso=? AND Tipo='Propedeudico'");
            sCorsiModuloByCorso=connection.prepareStatement("SELECT Other_Corso FROM Colleg_Corsi WHERE This_Corso=? AND Tipo='Modulo'");
            sDocentiByCorso=connection.prepareStatement("SELECT Docente FROM Docenti_Corso WHERE Corso=?");
            sLibriByCorso=connection.prepareStatement("SELECT Libro FROM Libri_Corso WHERE Corso=?");
            sMaterialeByCorso=connection.prepareStatement("SELECT IDMateriale FROM Materiale WHERE Corso=?");
            
            sCorsiByCDL=connection.prepareStatement("SELECT Corso FROM Corsi_CDL,Corso WHERE Corso=IDCorso AND CDL=? AND Anno=?");   /*LOOK*/
            
         /*  sCorsiByCDLAnno=connection.prepareStatement("SELECT * FROM Corsi_CDL,Corsi WHERE CDL=? AND Corsi.Anno<?"); */
            
            sUtentiByGruppo=connection.prepareStatement("SELECT IDUtente FROM Utente WHERE Gruppo=?");
            sServiziByGruppo=connection.prepareStatement("SELECT Servizio FROM Group_Services WHERE Gruppo=?");
            sCorsiByDocente=connection.prepareStatement("SELECT Corso FROM Docenti_Corso,Corso WHERE Docenti_Corso.Corso=Corso.IDCorso AND Docente=? AND Anno=?");
            sCorsiByLibro=connection.prepareStatement("SELECT Corso FROM Libri_Corso WHERE Libro=?");
            sGruppiByServizio=connection.prepareStatement("SELECT Gruppo FROM Group_Services WHERE Servizio=?");
            sUtenteByDocente=connection.prepareStatement("SELECT * FROM Utente WHERE Docente=?");
            
            PreparedStatement sCDLByAnno=connection.prepareStatement("SELECT * FROM CDL");
            
            sAccess=connection.prepareStatement("SELECT Script FROM Servizio,Utente,Group_Services WHERE Utente.Gruppo=Group_Services.Gruppo AND Servizio.IDServizio=Group_Services.Servizio AND Script=? AND Utente.Username=?");
            
            checkUtente=connection.prepareStatement("SELECT * FROM Utente WHERE Username=?");
            
            //insert
            iDocente = connection.prepareStatement("INSERT INTO Docente (Immagine,Nome,Cognome,Email,Ufficio,Telefono,Specializzazione,Ricerche,Pubblicazioni,Curriculum,Ricevimento) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDocente =  connection.prepareStatement("UPDATE Docente SET Nome=?,Cognome=?,Immagine=?,Email=?,Ufficio=?,Telefono=?,Specializzazione=?,Ricerche=?,Pubblicazioni=?,Curriculum=?,Ricevimento=? WHERE IDDocente=?");
            dDocente = connection.prepareStatement("DELETE FROM Docente WHERE IDDocente=?");
            
            iUtente = connection.prepareStatement("INSERT INTO Utente (Username,Password,Docente,Gruppo) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente =  connection.prepareStatement("UPDATE Utente Set Username=?,Password=? WHERE IDUtente=?");
            dUtente=connection.prepareStatement("DELETE FROM Utente WHERE IDUtente=?");
            
            iCorso=connection.prepareStatement("INSERT INTO Corso (Nome_it,Nome_en,SSD,Lingua,Semestre,CFU,Anno,Tipologia) VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            uCorso=connection.prepareStatement("UPDATE Corso SET Nome_it=?,Nome_en=?,SSD=?,Lingua=?,Semestre=?,CFU=?,Tipologia=? WHERE IDCorso=?");
            iDocentiCorso=connection.prepareStatement("REPLACE INTO Docenti_Corso(Corso,Docente) VALUES (?,?)");
            iCDLCorso=connection.prepareStatement("REPLACE INTO Corsi_CDL(Corso,CDL) VALUES(?,?)");
            iColleg_Corso=connection.prepareStatement("REPLACE INTO Colleg_Corsi(This_Corso,Other_Corso,Tipo) VALUES(?,?,?)");
            
            iCDL=connection.prepareStatement("INSERT INTO CDL(Nome_it,Nome_en,Anno,CFU,Magistrale,Immagine,Descrizione_it,Descrizione_en,Abbr_it,Abbr_en) VALUES (?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            
            iDescrizione_it=connection.prepareStatement("REPLACE INTO Descrizione_it(Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_Ext) VALUES(?,?,?,?,?,?,?,?,?,?)");
            iDescrizione_en=connection.prepareStatement("REPLACE INTO Descrizione_en(Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_Ext) VALUES(?,?,?,?,?,?,?,?,?,?)");
            iDublino_it=connection.prepareStatement("REPLACE INTO Dublino_it(Corso,Knowledge,Application,Evaluation,Communication,Lifelong) VALUES(?,?,?,?,?,?)");
            iDublino_en=connection.prepareStatement("REPLACE INTO Dublino_en(Corso,Knowledge,Application,Evaluation,Communication,Lifelong) VALUES(?,?,?,?,?,?)");
            iMateriale=connection.prepareStatement("INSERT INTO Materiale(Corso,Nome,Link,Descrizione_it,Descrizione_en) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            iLibro=connection.prepareStatement("INSERT INTO Libro(Autore,Titolo,Volume,Anno,Editore,Link) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            iLibri_Corso=connection.prepareStatement("REPLACE INTO Libri_Corso(Corso,Libro) VALUES(?,?)");
            
            dDocentiCorso=connection.prepareStatement("DELETE FROM Docenti_Corso WHERE Corso=? AND Docente=?");
            dCDLCorso=connection.prepareStatement("DELETE FROM Corsi_CDL WHERE Corso=? AND CDL=?");
            dColleg_Corso=connection.prepareStatement("DELETE FROM Colleg_Corsi WHERE This_Corso=? AND Other_Corso=?");
            dCorso=connection.prepareStatement("DELETE FROM Corso WHERE IDCorso=?");
            dDescrizione_it=connection.prepareStatement("DELETE FROM Descrizione_it WHERE Corso=?");
            dDescrizione_en=connection.prepareStatement("DELETE FROM Descrizione_en WHERE Corso=?");
            dDublino_it=connection.prepareStatement("DELETE FROM Dublino_it WHERE Corso=?");
            dDublino_en=connection.prepareStatement("DELETE FROM Dublino_en WHERE Corso=?");
            dMaterialeCorso=connection.prepareStatement("DELETE FROM Materiale WHERE Corso=?");
            dAllLibriCorso=connection.prepareStatement("DELETE Libri_Corso FROM Libri_Corso LEFT JOIN Corso ON Corso.IDCorso=Libri_Corso.Corso LEFT JOIN Libro ON Libro.IDLibro=Libri_Corso.Libro WHERE Corso=?");
            dLibro=connection.prepareStatement("DELETE IGNORE FROM Libro WHERE IDLibro=?");
            dAllDocCorso=connection.prepareStatement("DELETE Docenti_Corso FROM Docenti_Corso LEFT JOIN Corso ON Corso.IDCorso=Docenti_Corso.Corso LEFT JOIN Docente ON Docente.IDDocente=Docenti_Corso.Docente WHERE Corso=?");
            dAllCDLCorso=connection.prepareStatement("DELETE Corsi_CDL FROM Corsi_CDL LEFT JOIN Corso ON Corso.IDCorso=Corsi_CDL.Corso LEFT JOIN CDL ON CDL.IDCDL=Corsi_CDL.CDL WHERE Corso=?");
            dThis_Corso=connection.prepareStatement("DELETE FROM Colleg_Corsi WHERE This_Corso=?");
            dOther_Corso=connection.prepareStatement("DELETE FROM Colleg_Corsi WHERE Other_Corso=?");
            
            dLibriCorso=connection.prepareStatement("DELETE Libri_Corso FROM Libri_Corso LEFT JOIN Corso ON Corso.IDCorso=Libri_Corso.Corso LEFT JOIN Libro ON Libro.IDLibro=Libri_Corso.Libro WHERE Libro=?");
            
            dCDLinColl=connection.prepareStatement("DELETE Corsi_CDL FROM Corsi_CDL LEFT JOIN CDL ON CDL.IDCDL=Corsi_CDL.CDL LEFT JOIN Corso ON Corso.IDCorso=Corsi_CDL.Corso WHERE CDL=?");
            dCDL=connection.prepareStatement("DELETE FROM CDL WHERE IDCDL=?");
            
            uMateriale=connection.prepareStatement("UPDATE Materiale SET Nome=?,Link=?,Descrizione_it=?,Descrizione_en=? WHERE IDMateriale=?");
            dMateriale=connection.prepareStatement("DELETE FROM Materiale WHERE IDMateriale=?");
            
            dCorsiDocente=connection.prepareStatement("DELETE Docenti_Corso FROM Docenti_Corso LEFT JOIN Docente ON Docente.IDDocente=Docenti_Corso.Docente LEFT JOIN Corso ON Corso.IDCorso=Docenti_Corso.Corso WHERE Docente=?");
            uCDL=connection.prepareStatement("UPDATE CDL SET Nome_it=?,Nome_en=?,CFU=?,Magistrale=?,Immagine=?,Descrizione_it=?,Descrizione_en=?,Abbr_it=?,Abbr_en=? WHERE IDCDL=?");
            uLibro=connection.prepareStatement("UPDATE Libro SET Autore=?,Titolo=?,Volume=?,Anno=?,Editore=?,Link=? WHERE IDLibro=?");
            
            iLog=connection.prepareStatement("INSERT INTO LOG(Utente,Data,Descrizione) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            
            
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing igw data layer", ex);
        }
    }
    
    
    public IgwDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    @Override
    public CDL createCDL() {
        return new CDLImpl(this);
    }

    public CDL createCDL(ResultSet rs) throws DataLayerException {
        try {
            CDLImpl c = new CDLImpl(this);
            c.setIDCDL(rs.getInt("IDCDL")); //dobbiamo mettere tutti i metodi setid puttana
            c.setNome_it(rs.getString("Nome_it").toUpperCase());
            c.setNome_en(rs.getString("Nome_en").toUpperCase());
            c.setAnno(rs.getInt("Anno"));  //il metodo getYear è stato deprecato https://docs.oracle.com/javase/7/docs/api/java/sql/Time.html probabile che dobbiamo suare caldendar
            c.setCfu(rs.getInt("Cfu"));
            c.setMagistrale(rs.getInt("Magistrale"));
            c.setImmagine(rs.getString("Immagine"));
            c.setDescrizione_it(rs.getString("Descrizione_it"));
            c.setDescrizione_en(rs.getString("Descrizione_en"));
            c.setAbbr_it(rs.getString("Abbr_it").toUpperCase());
            c.setAbbr_en(rs.getString("Abbr_en").toUpperCase());
            return c;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create CDL object form ResultSet", ex);
        }
    }
    
    @Override
    public Corso createCorso() {
        return new CorsoImpl(this);
    }
    
    

    public Corso createCorso(ResultSet rs) throws DataLayerException {
        try {
            CorsoImpl co = new CorsoImpl(this);
            
            co.setID(rs.getInt("IDCorso")); 
            co.setNome_it(rs.getString("Nome_it"));
            co.setNome_en(rs.getString("Nome_en"));
            co.setSSD(rs.getString("SSD").toUpperCase());  
            co.setLingua(rs.getString("Lingua"));
            co.setSemestre(rs.getInt("Semestre"));
            co.setCfu(rs.getInt("CFU"));
            co.setAnno(rs.getInt("Anno"));
            co.setOldID(rs.getInt("OldID"));
            if(rs.getString("Tipologia").length()!=0)
                co.setTipologia(toUpperCase(rs.getString("Tipologia").charAt(0)));
            return co;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Corso object form ResultSet", ex);
        }
    }
    
    @Override
    public Docente createDocente() {
        return new DocenteImpl(this);
    }
    
        public Docente createDocente(ResultSet rs) throws DataLayerException {
        try {
            DocenteImpl d = new DocenteImpl(this);
            
            d.setIDDocente(rs.getInt("IDDocente")); 
            d.setImmagine(rs.getString("Immagine"));
            d.setNome(rs.getString("Nome"));
            d.setCognome(rs.getString("Cognome"));  
            d.setEmail(rs.getString("Email"));
            d.setUfficio(rs.getString("Ufficio"));
            d.setTelefono(rs.getString("Telefono"));
            d.setSpecializzazione(rs.getString("Specializzazione"));
            d.setRicerche(rs.getString("Ricerche"));
            d.setPubblicazioni(rs.getString("Pubblicazioni"));
            d.setCurriculum(rs.getString("Curriculum"));
            d.setRicevimento(rs.getString("Ricevimento"));
            return d;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Docente object form ResultSet", ex);
        }
    }
    
    @Override
    public Descrizione_it createDescrizione_it() {
        return new Descrizione_itImpl(this);
    }
    
            public Descrizione_it createDescrizione_it(ResultSet rs) throws DataLayerException {
        try {
            Descrizione_itImpl de = new Descrizione_itImpl(this);
            
            de.setIDCorso(rs.getInt("Corso")); 
            de.setPrerequisiti(rs.getString("Prerequisiti"));
            de.setObiettivi(rs.getString("Obiettivi"));
            de.setMod_Esame(rs.getString("Mod_Esame"));  
            de.setMod_Insegnamento(rs.getString("Mod_Insegnamento"));
            de.setSillabo(rs.getString("Sillabo"));
            de.setNote(rs.getString("Note"));
            de.setHomepage(rs.getString("Homepage"));
            de.setForum(rs.getString("Forum"));
            de.setRisorse_Ext(rs.getString("Risorse_ext"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Descrizione_it object form ResultSet", ex);
        }
   }
    
   
    

    @Override
    public Descrizione_en createDescrizione_en() {
        return new Descrizione_enImpl(this);
    }
    
    public Descrizione_en createDescrizione_en(ResultSet rs) throws DataLayerException {
        try {
            Descrizione_enImpl de = new Descrizione_enImpl(this);
            
            de.setIDCorso(rs.getInt("Corso")); 
            de.setPrerequisiti(rs.getString("Prerequisiti"));
            de.setObiettivi(rs.getString("Obiettivi"));
            de.setMod_Esame(rs.getString("Mod_Esame"));  
            de.setMod_Insegnamento(rs.getString("Mod_Insegnamento"));
            de.setSillabo(rs.getString("Sillabo"));
            de.setNote(rs.getString("Note"));
            de.setHomepage(rs.getString("Homepage"));
            de.setForum(rs.getString("Forum"));
            de.setRisorse_Ext(rs.getString("Risorse_ext"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Descrizione_en object form ResultSet", ex);
        }
   }
    
    

    @Override
    public Dublino_it createDublino_it() {
        return new Dublino_itImpl(this);
    }
    
    
    
    public Dublino_it createDublino_it(ResultSet rs) throws DataLayerException {
        try {
            Dublino_itImpl de = new Dublino_itImpl(this);
            
            de.setIDCorso(rs.getInt("Corso")); 
            de.setKnowledge(rs.getString("Knowledge"));
            de.setApplication(rs.getString("Application"));
            de.setEvaluation(rs.getString("Evaluation"));  
            de.setCommunication(rs.getString("Communication"));
            de.setLifelong(rs.getString("LifeLong"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Dublino_it object form ResultSet", ex);
        }
   }

    @Override
    public Dublino_en createDublino_en() {
        return new Dublino_enImpl(this);
    }
    
        public Dublino_enImpl createDublino_en(ResultSet rs) throws DataLayerException {
        try {
            Dublino_enImpl de = new Dublino_enImpl(this);
            
            de.setIDCorso(rs.getInt("Corso")); 
            de.setKnowledge(rs.getString("Knowledge"));
            de.setApplication(rs.getString("Application"));
            de.setEvaluation(rs.getString("Evaluation"));  
            de.setCommunication(rs.getString("Communication"));
            de.setLifelong(rs.getString("LifeLong"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Dublino_en object form ResultSet", ex);
        }
   }
    

    @Override
    public Materiale createMateriale() {
        return (Materiale) new MaterialeImpl(this);
    }

    
            public Materiale createMateriale(ResultSet rs) throws DataLayerException {
        try {
            MaterialeImpl de = new MaterialeImpl(this);
            
            de.setIDCorso(rs.getInt("Corso")); 
            de.setID(rs.getInt("IDMateriale"));
            de.setNome(rs.getString("Nome"));
            de.setLink(rs.getString("Link"));  
            de.setDescrizione_it(rs.getString("Descrizione_it"));
            de.setDescrizione_en(rs.getString("Descrizione_en"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Materiale object form ResultSet", ex);
        }
   }
    
    @Override
    public Libro createLibro() {
        return new LibroImpl(this);
    }

    
            public Libro createLibro(ResultSet rs) throws DataLayerException {
        try {
            LibroImpl de = new LibroImpl(this);
            
            de.setIDLibro(rs.getInt("IDLibro")); 
            de.setAutore(rs.getString("Autore"));
            de.setTitolo(rs.getString("Titolo"));
            de.setVolume(rs.getInt("Volume"));  
            de.setAnno(rs.getInt("Anno"));
            de.setEditore(rs.getString("Editore"));
            de.setLink(rs.getString("Link"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Libro object form ResultSet", ex);
        }
   }
    
    @Override
    public Gruppo createGruppo() {
        return (Gruppo) new GruppoImpl(this);
    }
    
    
    public Gruppo createGruppo(ResultSet rs) throws DataLayerException {
        try {
            GruppoImpl de = new GruppoImpl(this);
            
            de.setIDGruppo(rs.getInt("IDGruppo")); 
            de.setNome(rs.getString("Nome"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Gruppo object form ResultSet", ex);
        }
   }
    

    @Override
    public Utente createUtente() {
        return (Utente) new UtenteImpl(this);
    }
    
    
    public Utente createUtente(ResultSet rs) throws DataLayerException {
        try {
            Utente de = new UtenteImpl(this);
            
            de.setID(rs.getInt("IDUtente")); 
            de.setUsername(rs.getString("Username"));
            de.setPassword(rs.getString("Password"));
            de.setDocente(rs.getInt("Docente"));  
            de.setIDGruppo(rs.getInt("Gruppo"));
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Utente object form ResultSet", ex);
        }
   }

    @Override
    public Servizio createServizio() {
        return new ServizioImpl(this);
    }
    
    
    
    public Servizio createServizio(ResultSet rs) throws DataLayerException {
        try {
            Servizio de = new ServizioImpl(this);
            
            de.setIDServizio(rs.getInt("IDServizio")); 
            de.setScript(rs.getString("Script"));
            de.setDescrizione(rs.getString("Descrizione"));  
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Servizio object form ResultSet", ex);
        }
   }
    
    
    
    @Override
    public Log CreateLog() {
        return (Log) new LogImpl(this);
    }

    
    
    public Log CreateLog(ResultSet rs) throws DataLayerException {
        try {
            Log de = new LogImpl(this);
            de.setIDLog(rs.getInt("IDLog")); 
            de.setData(rs.getTimestamp("Data"));
            de.setDescrizione(rs.getString("Descrizione"));
            de.setIDUtente(rs.getInt("Utente"));  
            return de;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Log object form ResultSet", ex);
        }
   }
 
    
    @Override
    public CDL getCDL(int IDCdl) throws DataLayerException {
        try {
            sCDLByID.setInt(1,IDCdl);
            try (ResultSet rs=sCDLByID.executeQuery()) {
                if(rs.next())
                    return createCDL(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load CDL by ID",ex);
        }
        return null;
    }

    @Override
    public Corso getCorso(int IDCorso) throws DataLayerException {
        try {
            sCorsoByID.setInt(1,IDCorso);
            try (ResultSet rs=sCorsoByID.executeQuery()) {
                if(rs.next())
                    return createCorso(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Corso by ID",ex);
        }
        return null;
    }

    @Override
    public Docente getDocente(int IDDocente) throws DataLayerException {
        try {
            sDocenteByID.setInt(1,IDDocente);
            try (ResultSet rs=sDocenteByID.executeQuery()) {
                if(rs.next())
                    return createDocente(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Docente by ID",ex);
        }
        return null;
    }

    @Override
    public Descrizione_it getDescrizione_it(Corso corso) throws DataLayerException {
        try {
            sDescrizione_itByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sDescrizione_itByCorso.executeQuery()) {
                if(rs.next())
                    return createDescrizione_it(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Descrizione_it by Corso",ex);
        }
        return null;
    }

    @Override
    public Descrizione_en getDescrizione_en(Corso corso) throws DataLayerException {
        try {
            sDescrizione_enByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sDescrizione_enByCorso.executeQuery()) {
                if(rs.next())
                    return createDescrizione_en(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Descrizione_en by Corso",ex);
        }
        return null;
    }

    @Override
    public Dublino_it getDublino_it(Corso corso) throws DataLayerException {
        try {
            sDublino_itByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sDublino_itByCorso.executeQuery()) {
                if(rs.next())
                    return createDublino_it(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Dublino_it by Corso",ex);
        }
        return null;
    }

    @Override
    public Dublino_en getDublino_en(Corso corso) throws DataLayerException {
        try {
            sDublino_enByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sDublino_enByCorso.executeQuery()) {
                if(rs.next())
                    return createDublino_en(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Dublino_en by Corso",ex);
        }
        return null;
    }

    @Override
    public Materiale getMateriale(int IDMateriale) throws DataLayerException {
        try {
            sMaterialeByID.setInt(1,IDMateriale);
            try (ResultSet rs=sMaterialeByID.executeQuery()) {
                if(rs.next())
                    return createMateriale(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Materiale by ID",ex);
        }
        return null;
    }

    @Override
    public Libro getLibro(int IDLibro) throws DataLayerException {
        try {
            sLibroByID.setInt(1,IDLibro);
            try (ResultSet rs=sLibroByID.executeQuery()) {
                if(rs.next())
                    return createLibro(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Libro by ID",ex);
        }
        return null;
    }

    @Override
    public Gruppo getGruppo(int IDGruppo) throws DataLayerException {
        try {
            sGruppoByID.setInt(1,IDGruppo);
            try (ResultSet rs=sGruppoByID.executeQuery()) {
                if(rs.next())
                    return createGruppo(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Gruppo by ID",ex);
        }
        return null;
    }

    @Override
    public Utente getUtente(int IDUtente) throws DataLayerException {
        try {
            sUtenteByID.setInt(1,IDUtente);
            try (ResultSet rs=sUtenteByID.executeQuery()) {
                if(rs.next())
                    return createUtente(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Utente by ID",ex);
        }
        return null;
    }

    
     
    
    @Override
    public Servizio getServizio(int IDServizio) throws DataLayerException {
        try {
            sServizioByID.setInt(1,IDServizio);
            try (ResultSet rs=sServizioByID.executeQuery()) {
                if(rs.next())
                    return createServizio(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Servizio by ID",ex);
        }
        return null;
    }

    @Override
    public Log getLog(int IDLog) throws DataLayerException {
        try {
            sLogByID.setInt(1,IDLog);
            try (ResultSet rs=sLogByID.executeQuery()) {
                if(rs.next())
                    return CreateLog(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Log by ID",ex);
        }
        return null;
    }

    @Override
    public List<Corso> getCorsiMutuati(Corso corso) throws DataLayerException {
        List<Corso> result = new ArrayList();
        try {
            sCorsiMutuatiByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sCorsiMutuatiByCorso.executeQuery()) {
                while (rs.next()){
                    result.add(getCorso(rs.getInt("Other_Corso")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load CorsiMutuati by Corso", ex);
        }
        return result;
    }

    @Override
    public Corso getCorsoMutua(Corso corso) throws DataLayerException{
        try {
            sCorsoMutuaByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sCorsoMutuaByCorso.executeQuery()) {
                if(rs.next())
                    return createCorso(rs);
            }
        }
        catch (SQLException ex){
            throw new DataLayerException("Unable to load Corso Che Mutua by Corso",ex);
        }
        return null;
    }
    
    @Override
    public List<Corso> getCorsiPrerequisiti(Corso corso) throws DataLayerException {
        List<Corso> result = new ArrayList();
        try {
            sCorsiPrerequisitiByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sCorsiPrerequisitiByCorso.executeQuery()) {
                while (rs.next()){
                    result.add(getCorso(rs.getInt("Other_Corso")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load CorsiPrerequisiti by Corso", ex);
        }
        return result;
    }

    @Override
    public List<Corso> getCorsiModulo(Corso corso) throws DataLayerException {
        List<Corso> result = new ArrayList();
        try {
            sCorsiModuloByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sCorsiModuloByCorso.executeQuery()) {
                while (rs.next()){
                    result.add(getCorso(rs.getInt("Other_Corso")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load CorsiModulo by Corso", ex);
        }
        return result;
    }

    @Override
    public List<Docente> getDocentiCorso(Corso corso) throws DataLayerException {
        List<Docente> result = new ArrayList();
        try {
            sDocentiByCorso.setInt(1,corso.getID());
            try (ResultSet rs=sDocentiByCorso.executeQuery()) {
                while (rs.next()){
                    result.add(getDocente(rs.getInt("Docente")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load DocentiCorso by Corso", ex);
        }
        return result;
    }

    @Override
    public List<Libro> getLibriCorso(Corso corso) throws DataLayerException {
        List<Libro> result=new ArrayList();
        try{
            sLibriByCorso.setInt(1, corso.getID());
            try (ResultSet rs=sLibriByCorso.executeQuery()){
                while(rs.next())
                    result.add(getLibro(rs.getInt("Libro")));
            }
        } catch (SQLException ex){
            throw new DataLayerException("Unable to load LibriCorso by Corso",ex);
        }
        return result;
    }

    @Override
    public List<Materiale> getMaterialeCorso(int corso) throws DataLayerException {
        List<Materiale> result = new ArrayList();
        try{
            sMaterialeByCorso.setInt(1, corso);
            try (ResultSet rs=sMaterialeByCorso.executeQuery()){
                while(rs.next())
                    result.add(getMateriale(rs.getInt("IDMateriale")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load MaterialeCorso by Corso",ex);
        }
        return result;
    }

    @Override
    public List<Corso> getCorsiInCdl(CDL cdl) throws DataLayerException {
        List<Corso> result = new ArrayList();
        LocalDate date=LocalDate.now();
        int month=date.getMonthValue();
        int year=date.getYear();
        if(month<=8)
            year=year-1;
        try{
            sCorsiByCDL.setInt(1, cdl.getIDCDL());
            sCorsiByCDL.setInt(2,year);
            try (ResultSet rs=sCorsiByCDL.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("Corso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CorsiInCdl by CDL",ex);
        }
        return result;
    }
    
    
     @Override
    public List<Corso> getCorsiInCdlNoAnno(CDL cdl) throws DataLayerException {
        List<Corso> result = new ArrayList();
        try{
            sCorsiByCDLNoAnno.setInt(1, cdl.getIDCDL());
            
            try (ResultSet rs=sCorsiByCDLNoAnno.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("Corso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CorsiInCdl by CDL",ex);
        }
        return result;
    }
    
    
    
    
    @Override
    public List<Utente> getUtentiInGruppo(Gruppo gruppo) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try{
            sUtentiByGruppo.setInt(1, gruppo.getIDGruppo());
            try (ResultSet rs=sUtentiByGruppo.executeQuery()){
                while(rs.next())
                    result.add(getUtente(rs.getInt("IDUtente")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load UtentiInGruppo by Gruppo",ex);
        }
        return result;
    }

    @Override
    public List<Servizio> getServiziPerGruppo(Gruppo gruppo) throws DataLayerException {
        List<Servizio> result = new ArrayList();
        try{
            sServiziByGruppo.setInt(1, gruppo.getIDGruppo());
            try (ResultSet rs=sServiziByGruppo.executeQuery()){
                while(rs.next())
                    result.add(getServizio(rs.getInt("Servizio")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load ServiziPerGruppo by Gruppo",ex);
        }
        return result;
    }

    @Override
    public List<Corso> getCorsiDelDocente(Docente docente) throws DataLayerException {
        List<Corso> result = new ArrayList();
        LocalDate date=LocalDate.now();
        int month=date.getMonthValue();
        int year=date.getYear();
        if(month<=8)
            year=year-1;
        try{
            sCorsiByDocente.setInt(1, docente.getIDDocente());
            sCorsiByDocente.setInt(2, year);
            try (ResultSet rs=sCorsiByDocente.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("Corso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CorsiDelDocente by Docente",ex);
        }
        return result;
    }
    
    @Override
    public List<Corso> getCorsi() throws DataLayerException {
        List<Corso> result = new ArrayList();
        try{
            try (ResultSet rs=sAllCorsi.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("IDCorso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CorsiDelLibro by Docente",ex);
        }
        return result;
    }

    @Override
    public List<Corso> getCorsiDelLibro(Libro libro) throws DataLayerException {
        List<Corso> result = new ArrayList();
        try{
            sCorsiByLibro.setInt(1, libro.getIDLibro());
            try (ResultSet rs=sCorsiByLibro.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("Corso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CorsiDelLibro by Docente",ex);
        }
        return result;
    }
    
    @Override
    public List<Gruppo> getGruppiDelServizio(Servizio servizio) throws DataLayerException {
        List<Gruppo> result = new ArrayList();
        try{
            sGruppiByServizio.setInt(1, servizio.getIDServizio());
            try (ResultSet rs=sGruppiByServizio.executeQuery()){
                while(rs.next())
                    result.add(getGruppo(rs.getInt("Gruppo")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load GruppiDelServizio by Servizio",ex);
        }
        return result;
    }

    @Override
    public List<Corso> getCorso() throws DataLayerException {
       List<Corso> result = new ArrayList();
        LocalDate date=LocalDate.now();
        int month=date.getMonthValue();
        int year=date.getYear();
        if(month<=8)
            year=year-1;
        try{
            sCorsi.setInt(1, year);
            try (ResultSet rs=sCorsi.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("IDCorso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sCorso",ex);
        }
        return result;
    }
    
    @Override
    public List<Corso> getCorsiByAnno() throws DataLayerException {
        List<Corso> result = new ArrayList();
        LocalDate date=LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year=year-1;
        try{
            sCorsiByAnno.setInt(1, year);
            try (ResultSet rs=sCorsiByAnno.executeQuery()){
                while(rs.next())
                    result.add(getCorso(rs.getInt("IDCorso")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sCorsoByAnno",ex);
        }
        return result;
    }

    @Override
    public List<Docente> getDocente() throws DataLayerException {
        List<Docente> result = new ArrayList();
        try{
            try (ResultSet rs=sDocenti.executeQuery()){
                while(rs.next())
                    result.add(getDocente(rs.getInt("IDDocente")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sDocenti",ex);
        }
        return result;
    }
    
    @Override
    public List<CDL> getCDL() throws DataLayerException {
        List<CDL> result = new ArrayList();    
        try{
            try (ResultSet rs=sCDL.executeQuery()){
                while(rs.next())
                    result.add(getCDL(rs.getInt("IDCDL")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sCDL",ex);
        }
        return result;
    }
    
    @Override
    public List<CDL> getCDLMag() throws DataLayerException {
        List<CDL> result = new ArrayList();
        try{
            try (ResultSet rs=sCdlByMagistrale.executeQuery()){
                while(rs.next())
                    result.add(getCDL(rs.getInt("IDCDL")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sCDLByMagistrale",ex);
        }
        return result;
    }

    
    @Override
    public List<CDL> getCDLNoMag() throws DataLayerException {
        List<CDL> result = new ArrayList();
        try{
            try (ResultSet rs=sCdlByTriennale.executeQuery()){
                while(rs.next())
                    result.add(getCDL(rs.getInt("IDCDL")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load sCDLTriennale",ex);
        }
        return result;
    }

    
    
    @Override
    public List<CDL> getCDLInCorso(Corso corso) throws DataLayerException {
        List<CDL> result = new ArrayList();
        try{
            sCDLByCorso.setInt(1, corso.getID());
            try (ResultSet rs=sCDLByCorso.executeQuery()){
                while(rs.next())
                    result.add(getCDL(rs.getInt("IDCDL")));
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load CDLInCorso by Corso",ex);
        }
        return result;
    }
    
    
    @Override
    public List<Corso> getAnniPrecedenti(Corso corso) throws DataLayerException{
        List<Corso> result=new ArrayList();
        Corso tmp;
        try{
            sCorsoByID.setInt(1,corso.getOldID());
            try(ResultSet rs=sCorsoByID.executeQuery()){
                if(rs.next()){
                    tmp=createCorso(rs);
                    result.add(tmp);
                    if(tmp.getOldID()>0)
                        result.addAll(getAnniPrecedenti(tmp));
                }
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load Corsi Anni Precedenti",ex);
        }
        return result;
    }
                    
    
    
    @Override //LOGIN QUERY
    public Utente getUtenti(String username, String password) throws DataLayerException {
    try{
            Login.setString(1,username);
            Login.setString(2,password);
            try (ResultSet rs=Login.executeQuery()){
                if(rs.next())
                    return createUtente(rs);
            }
        }catch (SQLException ex){
            throw new DataLayerException("Unable to load Login",ex);
        }
        return null;
    }

    @Override
    public boolean getAccessUtente(String username,String script) throws DataLayerException{
            try {
                sAccess.setString(1, script);
                sAccess.setString(2, username);
                    try (ResultSet rs=sAccess.executeQuery()){
                        return rs.next();
                    }
            } catch (SQLException ex) {
                Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }
    
    
    @Override
    public Utente getUtenteByDocente(Docente docente) throws DataLayerException, SQLException {
        sUtenteByDocente.setInt(1, docente.getIDDocente());
        ResultSet rs=sUtenteByDocente.executeQuery();
        if(rs.next())
            return createUtente(rs);
        return null;
    }
    
    
    @Override
    public void storeDocente(Docente docente) throws DataLayerException{
        int key = docente.getIDDocente();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!docente.isDirty()) {
                    return;
                }
                
                uDocente.setString(1, docente.getNome());
                uDocente.setString(2, docente.getCognome());
                uDocente.setString(3, docente.getImmagine());
                uDocente.setString(4, docente.getEmail());
                uDocente.setString(5, docente.getUfficio());
                uDocente.setString(6, docente.getTelefono());
                uDocente.setString(7, docente.getSpecializzazione());
                uDocente.setString(8, docente.getRicerche());
                uDocente.setString(9, docente.getPubblicazione());
                uDocente.setString(10, docente.getCurriculum());
                uDocente.setString(11, docente.getRicevimento());
                uDocente.setInt(12, docente.getIDDocente());
                
                
                uDocente.executeUpdate();
                
            } else { //insert
                iDocente.setString(1, docente.getImmagine());
                iDocente.setString(2, docente.getNome());
                iDocente.setString(3, docente.getCognome());
                iDocente.setString(4, docente.getEmail());
                iDocente.setString(5, docente.getUfficio());
                iDocente.setString(6, docente.getTelefono());
                iDocente.setString(7, docente.getSpecializzazione());
                iDocente.setString(8, docente.getRicerche());
                iDocente.setString(9, docente.getPubblicazione());
                iDocente.setString(10, docente.getCurriculum());
                iDocente.setString(11, docente.getRicevimento());
                
                      
                if (iDocente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iDocente.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                docente.copyFrom(getDocente(key));
            }
            docente.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store docente", ex);
        }
    }
        
        
    
    
    @Override
    public void storeUtente(Utente utente) throws DataLayerException {
        int key = utente.getID();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!utente.isDirty()) {
                    return;
                }
                
                uUtente.setString(1, utente.getUsername());
                uUtente.setString(2, utente.getPassword());
                uUtente.setInt(3, utente.getID());
                uUtente.executeUpdate();
                
            } else { //insert
                
                iUtente.setString(1, utente.getUsername());
                iUtente.setString(2, utente.getPassword());
                
                iUtente.setInt(4, utente.getGruppo().getIDGruppo());
                if(utente.getDocente()!=0)
                    iUtente.setInt(3, utente.getDocente());
                else
                    iUtente.setNull(3, java.sql.Types.INTEGER);

                
                
                
                
                
                if (iUtente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                utente.copyFrom(getUtente(key));
            }
            utente.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store utente", ex);
        }
    }

     @Override
    public void storeCorso(Corso corso) throws DataLayerException {
        int key = corso.getID();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!corso.isDirty()) {
                    return;
                }
                
                uCorso.setString(1, corso.getNome_it());
                uCorso.setString(2, corso.getNome_en());
                uCorso.setString(3, corso.getSSD());
                uCorso.setString(4, corso.getLingua());
                uCorso.setInt(5,corso.getSemestre());
                uCorso.setInt(6, corso.getCfu());
                if(!String.valueOf(corso.getTipologia()).equals("n"))
                    uCorso.setString(7, String.valueOf(corso.getTipologia()));
                else
                    uCorso.setString(7,"");
                uCorso.setInt(8,corso.getID());
                
                if(uCorso.executeUpdate()==1){
                    List<Docente> docx=getDocentiCorso(corso);
                    List<Docente> doci=corso.getDocenti();
                    for(Docente doc:docx)
                        if(!DocenteImpl.contains(corso.getDocenti(), doc)){
                            dDocentiCorso.setInt(1, corso.getID());
                            dDocentiCorso.setInt(2, doc.getIDDocente());
                            dDocentiCorso.executeUpdate();
                        }
                        /*else{
                            doci.remove(doc);
                        }*/
                    for(Docente doc:doci){
                        iDocentiCorso.setInt(1, corso.getID());
                        iDocentiCorso.setInt(2, doc.getIDDocente());
                        iDocentiCorso.executeUpdate();
                    }
                    
                    List<CDL> cdlx=getCDLInCorso(corso);
                    List<CDL> cdli=corso.getCDL();
                    for(CDL cdl:cdlx)
                        if(!CDLImpl.contains(corso.getCDL(), cdl)){
                            dCDLCorso.setInt(1, corso.getID());
                            dCDLCorso.setInt(2, cdl.getIDCDL());
                            dCDLCorso.executeUpdate();
                        }
                        /*else{
                            cdli.remove(cdl);
                        }*/
                    for(CDL cdl:cdli){
                        iCDLCorso.setInt(1, corso.getID());
                        iCDLCorso.setInt(2, cdl.getIDCDL());
                        iCDLCorso.executeUpdate();
                    }
                    
                    List<Corso> mdb=getCorsiMutuati(corso);
                    List<Corso> mutuati=corso.getCorsiMutuati();
                    for(Corso c:mdb)
                        if(!CorsoImpl.contains(corso.getCorsiMutuati(), c)){
                            dColleg_Corso.setInt(1, corso.getID());
                            dColleg_Corso.setInt(2, c.getID());
                            dColleg_Corso.executeUpdate();
                        }
                    for(Corso c:mutuati){
                        iColleg_Corso.setInt(1, corso.getID());
                        iColleg_Corso.setInt(2, c.getID());
                        iColleg_Corso.setString(3, "mutuato");
                        iColleg_Corso.executeUpdate();
                    }
                    
                    List<Corso> mddb=getCorsiModulo(corso);
                    List<Corso> modulo=corso.getCorsiModulo();
                    for(Corso c:mddb)
                        if(!CorsoImpl.contains(corso.getCorsiModulo(), c)){
                            dColleg_Corso.setInt(1, corso.getID());
                            dColleg_Corso.setInt(2, c.getID());
                            dColleg_Corso.executeUpdate();
                        }
                    for(Corso c:modulo){
                        iColleg_Corso.setInt(1, corso.getID());
                        iColleg_Corso.setInt(2, c.getID());
                        iColleg_Corso.setString(3, "modulo");
                        iColleg_Corso.executeUpdate();
                    }
                    
                    List<Corso> pdb=getCorsiPrerequisiti(corso);
                    List<Corso> propedeudici=corso.getCorsiPrerequisiti();
                    for(Corso c:pdb)
                        if(!CorsoImpl.contains(corso.getCorsiPrerequisiti(), c)){
                            dColleg_Corso.setInt(1, corso.getID());
                            dColleg_Corso.setInt(2, c.getID());
                            dColleg_Corso.executeUpdate();
                        }
                    for(Corso c:propedeudici){
                        iColleg_Corso.setInt(1, corso.getID());
                        iColleg_Corso.setInt(2, c.getID());
                        iColleg_Corso.setString(3, "propedeudico");
                        iColleg_Corso.executeUpdate();
                    }
                    
                    /*
                    for(Docente doc:docx) 
                        if(!corso.getDocenti().contains(doc)){                                                                                                              
                            dDocentiCorso.setInt(1, corso.getID());
                            dDocentiCorso.setInt(2, doc.getIDDocente());
                            dDocentiCorso.executeUpdate();
                            docx.remove(doc);
                        }
                    for(Docente doc:corso.getDocenti())
                        if(!docx.contains(doc)){
                            iDocentiCorso.setInt(1, corso.getID());
                            iDocentiCorso.setInt(2, doc.getIDDocente());
                            iDocentiCorso.executeUpdate();
                        }
                    List<CDL> cdl=getCDLInCorso(corso);
                    for(CDL cd:cdl)
                        if(!corso.getCDL().contains(cd)){
                           dCDLCorso.setInt(1, corso.getID());
                           dCDLCorso.setInt(2,cd.getIDCDL());
                           dCDLCorso.executeUpdate();
                           cdl.remove(cd);
                        }
                    for(CDL cd:corso.getCDL())
                        if(!cdl.contains(cd)){
                            iCDLCorso.setInt(1, corso.getID());
                            iCDLCorso.setInt(2, cd.getIDCDL());
                            iCDLCorso.executeUpdate();
                        }*/
                    corso.copyFrom(corso);
                
                corso.setDirty(false);
                }
            } else { //insert
                iCorso.setString(1, corso.getNome_it());
                iCorso.setString(2, corso.getNome_en());
                iCorso.setString(3, corso.getSSD());
                iCorso.setString(4,corso.getLingua());
                iCorso.setInt(5, corso.getSemestre());
                iCorso.setInt(6, corso.getCfu());
                LocalDate date=LocalDate.now();
                int month=date.getMonthValue();
                int year=date.getYear();
                if(month>=9||month<=1)
                    if(corso.getSemestre()!=1)
                        year=year-1;
                iCorso.setInt(7, year);
                if(!String.valueOf(corso.getTipologia()).equals("n"))
                    iCorso.setString(8, String.valueOf(corso.getTipologia()));
                else
                    iCorso.setString(8,"");
                
                
                      
                if (iCorso.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iCorso.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
            
            for(Docente doc : corso.getDocenti()){
                iDocentiCorso.setInt(1, key);
                iDocentiCorso.setInt(2, doc.getIDDocente());
                iDocentiCorso.executeUpdate();
            }
            
            for(CDL cdl : corso.getCDL()){
                iCDLCorso.setInt(1, key);
                iCDLCorso.setInt(2, cdl.getIDCDL());
                iCDLCorso.executeUpdate();
            }
            
            for(Corso c:corso.getCorsiMutuati()){
                iColleg_Corso.setInt(1, key);
                iColleg_Corso.setInt(2,c.getID());
                iColleg_Corso.setString(3, "mutuato");
                iColleg_Corso.executeUpdate();
            }
            
            for(Corso c:corso.getCorsiModulo()){
                iColleg_Corso.setInt(1, key);
                iColleg_Corso.setInt(2,c.getID());
                iColleg_Corso.setString(3, "modulo");
                iColleg_Corso.executeUpdate();
            }
            
            for(Corso c:corso.getCorsiPrerequisiti()){
                iColleg_Corso.setInt(1, key);
                iColleg_Corso.setInt(2,c.getID());
                iColleg_Corso.setString(3, "propedeudico");
                iColleg_Corso.executeUpdate();
            }
            
                corso.copyFrom(getCorso(key));
            }
            corso.setDirty(false);
            }
            /*Iterator doc;
            if(!corso.getDocenti().isEmpty()){
                doc=corso.getDocenti().iterator();
                Docente d = null;
                while(doc.hasNext()){
                    d=(Docente)doc.next();
                    iDocentiCorso.setInt(1, corso.getID());
                    iDocentiCorso.setInt(2,d.getIDDocente());
                }
            }*/
            
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store Corso", ex);
        }
    }
    

    
    @Override
    public void destroy() {
        //anche chiudere i PreparedStamenent è una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {
            sCorsoByID.close();
            sLog.close();
            sCDLByID.close();
            sCorsoByID.close();
            sDocenteByID.close();
            sDescrizione_itByCorso.close();
            sDescrizione_enByCorso.close();
            sDublino_itByCorso.close();
            sDublino_enByCorso.close();
            sMaterialeByID.close();
            sLibroByID.close();
            sGruppoByID.close();
            sUtenteByID.close();
            sServizioByID.close();
            sLogByID.close();
            sCorsiByAnno.close();
            sCDLByCorso.close();
            sCorsoMutuaByCorso.close();
            sCdlByMagistrale.close();
            sCdlByTriennale.close();
            Login.close();
            sCorsiByCDLNoAnno.close();
            sDocenti.close();
            sCorsi.close();
            sCDL.close();
            sCorsiMutuatiByCorso.close();
            sCorsiPrerequisitiByCorso.close();
            sCorsiModuloByCorso.close();
            sDocentiByCorso.close();
            sMaterialeByCorso.close();
            sCorsiByCDL.close();
            sUtentiByGruppo.close();
            sServiziByGruppo.close();
            sCorsiByDocente.close();
            sCorsiByLibro.close();
            sGruppiByServizio.close();
            sUtenteByDocente.close();
            sAccess.close();
            checkUtente.close();
            iDocente.close();
            uDocente.close();
            dDocente.close();
            iUtente.close();
            uUtente.close();
            dUtente.close();
            iCorso.close();
            uCorso.close();
            iDocentiCorso.close();
            iCDLCorso.close();
            iColleg_Corso.close();
            iCDL.close();
            iDescrizione_it.close();
            iDescrizione_en.close();
            iDublino_it.close();
            iDublino_en.close();
            iMateriale.close();
            iLibro.close();
            iLibri_Corso.close();
            dDocentiCorso.close();
            dCDLCorso.close();
            iColleg_Corso.close();
            dCorso.close();
            dDescrizione_it.close();
            dDescrizione_en.close();
            dDublino_it.close();
            dDublino_en.close();
            dMaterialeCorso.close();
            
            
            
            
            
            
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public void storeCDL(CDL cdl) {
        int key = cdl.getIDCDL();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!cdl.isDirty()) {
                    return;
                }
                uCDL.setString(1, cdl.getNome_it());
                uCDL.setString(2, cdl.getNome_en());
                uCDL.setInt(3, cdl.getCfu());
                uCDL.setInt(4, cdl.getMagistrale());
                uCDL.setString(5, cdl.getImmagine());
                uCDL.setString(6, cdl.getDescrizione_it());
                uCDL.setString(7, cdl.getDescrizione_en());
                uCDL.setString(8, cdl.getAbbr_it());
                uCDL.setString(9, cdl.getAbbr_en());
                uCDL.setInt(10, cdl.getIDCDL());
                uCDL.executeUpdate();
            
            } else { //insert
                
               iCDL.setString(1, cdl.getNome_it());
               iCDL.setString(2, cdl.getNome_en());
               iCDL.setInt(3, LocalDate.now().getYear());
               iCDL.setInt(4, cdl.getCfu());
               iCDL.setInt(5, cdl.getMagistrale());
               iCDL.setString(6, cdl.getImmagine());
               iCDL.setString(7, cdl.getDescrizione_it());
               iCDL.setString(8, cdl.getDescrizione_en());
               iCDL.setString(9, cdl.getAbbr_it());
               iCDL.setString(10, cdl.getAbbr_en());
                
                
                
                
                
                if (iCDL.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iCDL.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                try {
                    cdl.copyFrom(getCDL(key));
                } catch (DataLayerException ex) {
                    Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cdl.setDirty(false);
        } catch (SQLException ex) {
            try {
                throw new DataLayerException("Unable to store CDL", ex);
            } catch (DataLayerException ex1) {
                Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    
    @Override
    public void storeDescrizione_it(Descrizione_it descrizione) throws DataLayerException{
         try {
                if (!descrizione.isDirty()) {
                    return;
                }
               iDescrizione_it.setInt(1, descrizione.getCorso().getID());
               iDescrizione_it.setString(2, descrizione.getPrerequisiti());
               iDescrizione_it.setString(3, descrizione.getObiettivi());
               iDescrizione_it.setString(4, descrizione.getMod_Esame());
               iDescrizione_it.setString(5, descrizione.getMod_Insegnamento());
               iDescrizione_it.setString(6, descrizione.getSillabo());
               iDescrizione_it.setString(7, descrizione.getNote()); 
               iDescrizione_it.setString(8, descrizione.getHomepage());
               iDescrizione_it.setString(9, descrizione.getForum());
               iDescrizione_it.setString(10, descrizione.getRisorse_Ext());
                
                
                if (iDescrizione_it.executeUpdate() == 1) {
                    try {
                    descrizione.copyFrom(getDescrizione_it(descrizione.getCorso()));
                    } catch (DataLayerException ex) {
                        Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean existUtente(String username) {
        try{
            checkUtente.setString(1, username);
            try (ResultSet rs=checkUtente.executeQuery()){
                if(rs.next())
                    return true;
            }
        }catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Log> getLog() throws DataLayerException{
        List<Log> result = new ArrayList();

            try(ResultSet rs=sLog.executeQuery()){
                while(rs.next())
                    result.add(getLog(rs.getInt("IDLog")));
        } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void storeDescrizione_en(Descrizione_en descrizione) throws DataLayerException {
        try {
                if (!descrizione.isDirty()) {
                    return;
                }
               iDescrizione_en.setInt(1, descrizione.getCorso().getID());
               iDescrizione_en.setString(2, descrizione.getPrerequisiti());
               iDescrizione_en.setString(3, descrizione.getObiettivi());
               iDescrizione_en.setString(4, descrizione.getMod_Esame());
               iDescrizione_en.setString(5, descrizione.getMod_Insegnamento());
               iDescrizione_en.setString(6, descrizione.getSillabo());
               iDescrizione_en.setString(7, descrizione.getNote()); 
               iDescrizione_en.setString(8, descrizione.getHomepage());
               iDescrizione_en.setString(9, descrizione.getForum());
               iDescrizione_en.setString(10, descrizione.getRisorse_Ext());
                
                
                if (iDescrizione_en.executeUpdate() == 1) {
                    try {
                    descrizione.copyFrom(getDescrizione_en(descrizione.getCorso()));
                    } catch (DataLayerException ex) {
                        Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void storeDublino_it(Dublino_it dublino) throws DataLayerException {
        try {
                if (!dublino.isDirty()) {
                    return;
                }
               iDublino_it.setInt(1, dublino.getCorso().getID());
               iDublino_it.setString(2, dublino.getKnowledge());
               iDublino_it.setString(3, dublino.getApplication());
               iDublino_it.setString(4, dublino.getEvaluation());
               iDublino_it.setString(5, dublino.getCommunication());
               iDublino_it.setString(6, dublino.getLifelong());
                
                
                if (iDublino_it.executeUpdate() == 1) {
                    try {
                    dublino.copyFrom(getDublino_it(dublino.getCorso()));
                    } catch (DataLayerException ex) {
                        Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void storeDublino_en(Dublino_en dublino) throws DataLayerException {
        try {
                if (!dublino.isDirty()) {
                    return;
                }
               iDublino_en.setInt(1, dublino.getCorso().getID());
               iDublino_en.setString(2, dublino.getKnowledge());
               iDublino_en.setString(3, dublino.getApplication());
               iDublino_en.setString(4, dublino.getEvaluation());
               iDublino_en.setString(5, dublino.getCommunication());
               iDublino_en.setString(6, dublino.getLifelong());
                
                
                if (iDublino_en.executeUpdate() == 1) {
                    try {
                    dublino.copyFrom(getDublino_en(dublino.getCorso()));
                    } catch (DataLayerException ex) {
                        Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void storeMateriale(Materiale materiale) throws DataLayerException {
        int key = materiale.getID();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!materiale.isDirty()) {
                    return;
                }
                
                uMateriale.setString(1, materiale.getNome());
                uMateriale.setString(2, materiale.getLink());
                uMateriale.setString(3, materiale.getDescrizione_it());
                uMateriale.setString(4, materiale.getDescrizione_en());
                uMateriale.setInt(5, materiale.getID());
                uMateriale.executeUpdate();
            
            } else { //insert
                
               iMateriale.setInt(1, materiale.getCorso().getID());
               iMateriale.setString(2, materiale.getNome());
               iMateriale.setString(3, materiale.getLink());
               iMateriale.setString(4, materiale.getDescrizione_it());
               iMateriale.setString(5, materiale.getDescrizione_en());
                
                if (iMateriale.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iMateriale.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                try {
                    materiale.copyFrom(getMateriale(key));
                } catch (DataLayerException ex) {
                    Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            materiale.setDirty(false);
        } catch (SQLException ex) {
            try {
                throw new DataLayerException("Unable to store Materiale", ex);
            } catch (DataLayerException ex1) {
                Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @Override
    public void storeLibro(Libro libro, int corso) throws DataLayerException{
        int key = libro.getIDLibro();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!libro.isDirty()) {
                    return;
                }
                uLibro.setString(1, libro.getAutore());
                uLibro.setString(2, libro.getTitolo());
                uLibro.setInt(3, libro.getVolume());
                uLibro.setInt(4, libro.getAnno());
                uLibro.setString(5, libro.getEditore());
                uLibro.setString(6, libro.getLink());
                uLibro.setInt(7, libro.getIDLibro());
                uLibro.executeUpdate();
            
            } else { //insert
                
               iLibro.setString(1, libro.getAutore());
               iLibro.setString(2, libro.getTitolo());
               iLibro.setInt(3, libro.getVolume());
               iLibro.setInt(4, libro.getAnno());
               iLibro.setString(5, libro.getEditore());
               iLibro.setString(6, libro.getLink());
                
                if (iLibro.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iLibro.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                iLibri_Corso.setInt(1, corso);
                iLibri_Corso.setInt(2, key);
                iLibri_Corso.executeUpdate();
                try {
                    libro.copyFrom(getLibro(key));
                } catch (DataLayerException ex) {
                    Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            libro.setDirty(false);
        } catch (SQLException ex) {
            try {
                throw new DataLayerException("Unable to store Libro", ex);
            } catch (DataLayerException ex1) {
                Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }


    @Override
    public void deleteCorso(Corso corso) throws DataLayerException{
        try{
            int id=corso.getID();
            List<Libro> libri=corso.getLibri();
            
            dAllDocCorso.setInt(1, id);
            dAllDocCorso.executeUpdate();

            dAllCDLCorso.setInt(1, id);
            dAllCDLCorso.executeUpdate();
            
            
            dAllLibriCorso.setInt(1, id);
            dAllLibriCorso.executeUpdate();

            dThis_Corso.setInt(1, id);
            dThis_Corso.executeUpdate();
            
            dOther_Corso.setInt(1, id);
            dOther_Corso.executeUpdate();
            
            for(Libro libro:libri){
                dLibro.setInt(1, libro.getIDLibro());
                dLibro.executeUpdate();
            }
            
            dMaterialeCorso.setInt(1, id);
            dMaterialeCorso.executeUpdate();
            
            dDublino_it.setInt(1, id);
            dDublino_it.executeUpdate();
            
            dDublino_en.setInt(1, id);
            dDublino_en.executeUpdate();
            
            dDescrizione_it.setInt(1, id);
            dDescrizione_it.executeUpdate();
            
            dDescrizione_en.setInt(1, id);
            dDescrizione_en.executeUpdate();
            
            dCorso.setInt(1, id);
            dCorso.executeUpdate();
       
            
        }catch(SQLException ex){
            
        }
    }


    @Override
    public void storeLog(Log log) throws DataLayerException {
        
        int key = log.getIDLog();
         try {
            if (key > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!log.isDirty()) {
                    return;
                }
                
  
            } else { //insert
                
               iLog.setInt(1, log.getUtente().getID());
               iLog.setTimestamp(2,log.getData());
               iLog.setString(3,log.getDescrizione());             
                
                if (iLog.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iLog.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            if (key > 0) {
                try {
                    log.copyFrom(getLog(key));
                } catch (DataLayerException ex) {
                    Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            log.setDirty(false);
       
            } catch (SQLException ex) {
            Logger.getLogger(IgwDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    @Override
    public void deleteCDL(CDL cdl) throws DataLayerException{
        try{
            List<Corso> corsi=cdl.getCorsiInCdl();
            dCDLinColl.setInt(1, cdl.getIDCDL());
            dCDLinColl.executeUpdate();
            
            for(Corso corso:corsi)
                deleteCorso(corso);
            
            dCDL.setInt(1, cdl.getIDCDL());
            dCDL.executeUpdate();
        }catch(SQLException ex){
        }
    }

    @Override
    public void deleteDocente(Docente docente) throws DataLayerException {
        try{
            dCorsiDocente.setInt(1, docente.getIDDocente());
            dCorsiDocente.executeUpdate();
            
            dDocente.setInt(1, docente.getIDDocente());
            dDocente.executeUpdate();
        }catch(SQLException ex){
            throw new DataLayerException("Unable to delete Docente", ex);
        }
    }
    
    @Override
    public void deleteUtente(Utente utente) throws DataLayerException{
        try{
            dUtente.setInt(1, utente.getID());
            dUtente.executeUpdate();
        }catch(SQLException ex){
            throw new DataLayerException("Unable to delete Utente", ex);
        }
    }
    
    @Override
    public void deleteMateriale(Materiale materiale) throws DataLayerException{
        try{
            dMateriale.setInt(1, materiale.getID());
            dMateriale.executeUpdate();
            
        }catch(SQLException ex){
            throw new DataLayerException("Unable to delete Materiale", ex);
        }
    }

    @Override
    public void deleteLibro(Libro libro) throws DataLayerException,SQLException {
        dLibriCorso.setInt(1, libro.getIDLibro());
        dLibriCorso.executeUpdate();
        
        dLibro.setInt(1, libro.getIDLibro());
        dLibro.executeUpdate();
    }
        
}
        
        

  