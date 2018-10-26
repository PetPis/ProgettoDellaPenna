package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.Descrizione_en;
import courseweb.model.interfacce.Descrizione_it;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.Dublino_en;
import courseweb.model.interfacce.Dublino_it;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
import courseweb.model.interfacce.Materiale;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ModificaCorso extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int id, String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","ModificaCorso?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("cdl",((IgwDataLayer)request.getAttribute("datalayer")).getCDLNoMag());
                request.setAttribute("cdlm",((IgwDataLayer)request.getAttribute("datalayer")).getCDLMag());
                
                
                
                Corso corso = ((IgwDataLayer)request.getAttribute("datalayer")).getCorso(id);
                request.setAttribute("corso", corso);
                request.setAttribute("propedeudici",corso.getCorsiPrerequisiti());
                request.setAttribute("mutuati",corso.getCorsiMutuati());
                request.setAttribute("moduli",corso.getCorsiModulo());
                request.setAttribute("corsi", ((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());
                
                Map<Integer,String> corsicdl=new HashMap();
                for(CDL cdl:corso.getCDL()){
                    for(Corso cs:((IgwDataLayer)request.getAttribute("datalayer")).getCorsiInCdl(cdl)){
                        corsicdl.put(cs.getID(), cs.getNome_it());
                    }
                }
                request.setAttribute("corsicdl", corsicdl);
                
                
                request.setAttribute("docente", corso.getDocenti());
                request.setAttribute("docenti", ((IgwDataLayer)request.getAttribute("datalayer")).getDocente());
                
                 
                request.setAttribute("cdl", corso.getCDL());
                request.setAttribute("cdl1", ((IgwDataLayer)request.getAttribute("datalayer")).getCDL());
                
                Descrizione_it descrizioneit= (corso.getDescrizione_it());
                Descrizione_en descrizioneen= (corso.getDescrizione_en());
                Dublino_it dublinoit= (corso.getDublino_it());
                Dublino_en dublinoen= (corso.getDublino_en());
                
 
                request.setAttribute("descrizioneit", descrizioneit);
                request.setAttribute("descrizioneen", descrizioneen);
                request.setAttribute("dublinoit",dublinoit);
                request.setAttribute("dublinoen",dublinoen);
               
                List<Libro> libri=(corso.getLibri());
                List<Materiale> materiali=(corso.getMateriale());
                
                request.setAttribute("materiali",materiali);
                request.setAttribute("libro",libri);
                
                
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("modificacorso.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
@Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
            String lin;
            try{
            HttpSession s = SecurityLayer.checkSession(request);
            String username=(String)s.getAttribute("username");
            try {
                if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"ModificaCorso")) {
                    int n;
                    if(request.getParameter("cancella")!=null)
                        action_elimina(request,response);
                    if (request.getParameter("modifica") != null) {
                         action_modifica(request, response);
                    } else {
                try {
                    if(request.getParameter("lin")==null)
                        lin="it";
                    else{
                        lin=request.getParameter("lin");
                    n = SecurityLayer.checkNumeric(request.getParameter("n"));
                    action_default(request, response, n, lin);
                    }

                } catch (IOException | TemplateManagerException ex) {
                    request.setAttribute("exception", ex);
                    action_error(request, response);

                }
            }
                }else {
                    SecurityLayer.disposeSession(request);
                    response.sendRedirect("Login?referrer=" + URLEncoder.encode(request.getRequestURI(), "UTF-8"));
                }
                } catch (DataLayerException ex) {
                Logger.getLogger(ModificaCorso.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            }
}


    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
                    
                int key=Integer.parseInt(request.getParameter("key"));
                String nome= request.getParameter("nome");
                String nomeEN= request.getParameter("nome_en");
                String ssd= request.getParameter("ssd");
                String linguac= request.getParameter("linguac");
                int semestre=0;
                if(request.getParameter("semestre").length()!=0)
                    semestre= Integer.parseInt(request.getParameter("semestre"));
                int cfu=0;
                if(request.getParameter("cfu").length()!=0)
                    cfu=Integer.parseInt( request.getParameter("cfu"));
                String tipologia= request.getParameter("tipologia");
                
                String[] docente=request.getParameterValues("docenti");
                List<Docente> docenti=new ArrayList();
                if(docente!=null)
                    for(int i=0,a=0; i<docente.length; i++){
                       a=Integer.parseInt(docente[i]);
                       docenti.add(((IgwDataLayer)request.getAttribute("datalayer")).getDocente(a));  
                    }
                
                String[] c=request.getParameterValues("cdl");
                List<CDL> cdl=new ArrayList();
                for(int i=0,a=0; i<c.length; i++){
                   a=Integer.parseInt(c[i]);
                   cdl.add(((IgwDataLayer)request.getAttribute("datalayer")).getCDL(a));  
                }
                
                String[] mutuati=request.getParameterValues("mutuati");
                List<Corso> mutuaList=new ArrayList();
                if(mutuati!=null)
                    for(int i=0,a=0; i<mutuati.length; i++){
                        a=Integer.parseInt(mutuati[i]);
                        mutuaList.add(((IgwDataLayer)request.getAttribute("datalayer")).getCorso(a));
                    }
                
                
                String[] propedeudici=request.getParameterValues("propedeudici");
                List<Corso> propList=new ArrayList();
                if(propedeudici!=null)
                    for(int i=0,a=0; i<propedeudici.length; i++){
                        a=Integer.parseInt(propedeudici[i]);
                        propList.add(((IgwDataLayer)request.getAttribute("datalayer")).getCorso(a));
                    }
                
                String[] modulo=request.getParameterValues("modulo");
                List<Corso> moduloList=new ArrayList();
                if(modulo!=null)
                    for(int i=0,a=0; i<modulo.length; i++){
                        a=Integer.parseInt(modulo[i]);
                        moduloList.add(((IgwDataLayer)request.getAttribute("datalayer")).getCorso(a));
                    }

                Corso corso=((IgwDataLayer)request.getAttribute("datalayer")).getCorso(key);
                

                if(!corso.getNome_it().equals(nome))
                    corso.setNome_it(nome);
                if(!corso.getNome_en().equals(nomeEN))
                    corso.setNome_en(nomeEN);
                if(!corso.getSSD().equals(ssd))
                    corso.setSSD(ssd);
                if(!corso.getLingua().equals(linguac))
                    corso.setLingua(linguac);
                if(corso.getSemestre()!=semestre)
                    corso.setSemestre(semestre);
                if(corso.getCfu()!=cfu)
                    corso.setCfu(cfu);
                if(corso.getTipologia()!=tipologia.charAt(0))
                    corso.setTipologia(tipologia.charAt(0));
                corso.setDocenti(docenti);
                corso.setCDLInCorso(cdl);
                corso.setCorsiModulo(moduloList);
                corso.setCorsiMutuati(mutuaList);
                corso.setCorsiPrerequisiti(propList);
                
                
                //DESCRIZIONE
                Descrizione_it descrizioneit=corso.getDescrizione_it();
                
                String prerequisiti= request.getParameter("prerequisiti");
                String obiettivi= request.getParameter("obiettivi");
                String mod_esame= request.getParameter("modesa");
                String mod_insegnamento= request.getParameter("modins");
                String sillabo= request.getParameter("sillabo");
                String note= request.getParameter("note");
                String homepage= request.getParameter("homepage");
                String forum= request.getParameter("forum");
                String risorse_ext= request.getParameter("risorse");
                
                if(!descrizioneit.getPrerequisiti().equals(prerequisiti))
                    descrizioneit.setPrerequisiti(prerequisiti);
                if(!descrizioneit.getObiettivi().equals(obiettivi))
                    descrizioneit.setObiettivi(obiettivi);
                if(!descrizioneit.getMod_Esame().equals(mod_esame))
                    descrizioneit.setMod_Esame(mod_esame);
                if(!descrizioneit.getMod_Insegnamento().equals(note))
                    descrizioneit.setMod_Insegnamento(mod_insegnamento);
                if(!descrizioneit.getSillabo().equals(sillabo))
                    descrizioneit.setSillabo(sillabo);
                if(!descrizioneit.getNote().equals(note))
                    descrizioneit.setNote(note);
                if(!descrizioneit.getHomepage().equals(homepage))
                    descrizioneit.setHomepage(homepage);
                if(!descrizioneit.getForum().equals(forum))
                    descrizioneit.setForum(forum);
                if(!descrizioneit.getRisorse_Ext().equals(risorse_ext))
                    descrizioneit.setRisorse_Ext(risorse_ext);
                
                
                //Sezione descrizione EN
                Descrizione_en descrizioneen=corso.getDescrizione_en();
                
                String prerequisitien= request.getParameter("prerequisitien");
                String obiettivien= request.getParameter("obiettivien");
                String mod_esameen= request.getParameter("modesaen");
                String mod_insegnamentoen= request.getParameter("modinsen");
                String sillaboen= request.getParameter("sillaboen");
                String noteen= request.getParameter("noteen");
                String homepageen= request.getParameter("homepageen");
                String forumen= request.getParameter("forumen");
                String risorse_exten= request.getParameter("risorseen");
                
                if(!descrizioneen.getPrerequisiti().equals(prerequisitien))
                    descrizioneen.setPrerequisiti(prerequisitien);
                if(!descrizioneen.getObiettivi().equals(obiettivien))
                    descrizioneen.setObiettivi(obiettivien);
                if(!descrizioneen.getMod_Esame().equals(mod_esameen))
                    descrizioneen.setMod_Esame(mod_esameen);
                if(!descrizioneen.getMod_Insegnamento().equals(noteen))
                    descrizioneen.setMod_Insegnamento(mod_insegnamentoen);
                if(!descrizioneen.getSillabo().equals(sillaboen))
                    descrizioneen.setSillabo(sillaboen);
                if(!descrizioneen.getNote().equals(noteen))
                    descrizioneen.setNote(noteen);
                if(!descrizioneen.getHomepage().equals(homepageen))
                    descrizioneen.setHomepage(homepageen);
                if(!descrizioneen.getForum().equals(forumen))
                    descrizioneen.setForum(forumen);
                if(!descrizioneen.getRisorse_Ext().equals(risorse_exten))
                    descrizioneen.setRisorse_Ext(risorse_exten);
                
                
                //DUBLINO IT
                Dublino_it dublinoit=corso.getDublino_it();
                
                String knowledge = request.getParameter("knowledge");
                String application  = request.getParameter("application");
                String evaluation = request.getParameter("evaluation");
                String communication = request.getParameter("communication");
                String lifelong = request.getParameter("lifelong");
                
                if(!dublinoit.getKnowledge().equals(knowledge))
                    dublinoit.setKnowledge(knowledge);
                if(!dublinoit.getApplication().equals(application))
                    dublinoit.setApplication(application);
                if(!dublinoit.getEvaluation().equals(evaluation))
                    dublinoit.setEvaluation(evaluation);
                if(!dublinoit.getCommunication().equals(communication))
                    dublinoit.setCommunication(communication);
                if(!dublinoit.getLifelong().equals(lifelong))
                    dublinoit.setLifelong(lifelong);
                
                //DUBLINO EN
                Dublino_en dublinoen=corso.getDublino_en();
                
                String knowledgeen = request.getParameter("knowledgeen");
                String applicationen  = request.getParameter("applicationen");
                String evaluationen = request.getParameter("evaluationen");
                String communicationen = request.getParameter("communicationen");
                String lifelongen = request.getParameter("lifelongen");
                
                if(!dublinoen.getKnowledge().equals(knowledgeen))
                    dublinoen.setKnowledge(knowledgeen);
                if(!dublinoen.getApplication().equals(applicationen))
                    dublinoen.setApplication(applicationen);
                if(!dublinoen.getEvaluation().equals(evaluationen))
                    dublinoen.setEvaluation(evaluationen);
                if(!dublinoen.getCommunication().equals(communicationen))
                    dublinoen.setCommunication(communicationen);
                if(!dublinoen.getLifelong().equals(lifelongen))
                    dublinoen.setLifelong(lifelongen);

            String nomelog=corso.getNome_it();
            ((IgwDataLayer)request.getAttribute("datalayer")).storeCorso(corso);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDescrizione_it(descrizioneit);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDescrizione_en(descrizioneen);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDublino_it(dublinoit);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDublino_en(dublinoen);
            
            HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha modificato il corso di"+ nomelog);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        
                response.sendRedirect("Backoffice");
            
            } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    
    private void action_elimina(HttpServletRequest request, HttpServletResponse response) throws IOException,DataLayerException {
        int id=Integer.parseInt(request.getParameter("key"));
        Corso corso=((IgwDataLayer)request.getAttribute("datalayer")).getCorso(id);
        String nome=corso.getNome_it();
        List<CDL> cdl=corso.getCDL();
        
        List<Materiale> mat=corso.getMateriale();
            String context=request.getServletContext().getRealPath("");
            for(Materiale materiale:mat)
                Upload.delete(context,materiale.getLink());
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteCorso(corso);
        
        HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha cancellato il corso di "+ nome + " appartenente al cdl di "+ cdl );
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        response.sendRedirect("Backoffice");
        
        
    }
   
}

 
