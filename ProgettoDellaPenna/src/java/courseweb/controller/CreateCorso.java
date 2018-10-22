package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.Log;
import courseweb.model.interfacce.Descrizione_en;
import courseweb.model.interfacce.Descrizione_it;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.Dublino_en;
import courseweb.model.interfacce.Dublino_it;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateCorso extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        try {
            HttpSession session= request.getSession(false);
            String a = (String) session.getAttribute("username");
            request.setAttribute("nome",a);
            TemplateResult res = new TemplateResult(getServletContext());
            
            request.setAttribute("docenti", ((IgwDataLayer)request.getAttribute("datalayer")).getDocente());
            request.setAttribute("cdl", ((IgwDataLayer)request.getAttribute("datalayer")).getCDL());
            
            
             
            request.setAttribute("corsi",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());
            
            request.setAttribute("page_title", "Backoffice");
              
            if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                res.activate("create_corso.ftl.html", request, response); 
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(CreateCorso.class.getName()).log(Level.SEVERE, "CIAONE", ex);
        }
  
    }
    
    
    private void action_crea(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
                try{
                    
                         
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
                
                Corso corso=((IgwDataLayer)request.getAttribute("datalayer")).createCorso();
 
                corso.setNome_it(nome);
                corso.setNome_en(nomeEN);
                corso.setSSD(ssd);
                corso.setLingua(linguac);
                corso.setSemestre(semestre);
                corso.setCfu(cfu);
                corso.setTipologia(tipologia.charAt(0));
                
                corso.setDocenti(docenti);
                corso.setCDLInCorso(cdl);
                corso.setCorsiModulo(moduloList);
                corso.setCorsiMutuati(mutuaList);
                corso.setCorsiPrerequisiti(propList);
                
                
             //Sezione descrizione it
                String prerequisiti= request.getParameter("prerequisiti");
                String obiettivi= request.getParameter("obiettivi");
                String mod_esame= request.getParameter("modesa");
                String mod_insegnamento= request.getParameter("modins");
                String sillabo= request.getParameter("sillabo");
                String note= request.getParameter("note");
                String homepage= request.getParameter("homepage");
                String forum= request.getParameter("forum");
                String risorse_ext= request.getParameter("risorse");
 
                Descrizione_it descrizioneit=((IgwDataLayer)request.getAttribute("datalayer")).createDescrizione_it();
                        
                descrizioneit.setCorso(corso);
                descrizioneit.setPrerequisiti(prerequisiti);
                descrizioneit.setObiettivi(obiettivi);
                descrizioneit.setMod_Esame(mod_esame);
                descrizioneit.setMod_Insegnamento(mod_insegnamento);
                descrizioneit.setSillabo(sillabo);
                descrizioneit.setNote(note);
                descrizioneit.setHomepage(homepage);
                descrizioneit.setForum(forum);
                descrizioneit.setRisorse_Ext(risorse_ext);
                
        
                
                //Sezione descrizione EN
                String prerequisitien= request.getParameter("prerequisitien");
                String obiettivien= request.getParameter("obiettivien");
                String mod_esameen= request.getParameter("modesaen");
                String mod_insegnamentoen= request.getParameter("modinsen");
                String sillaboen= request.getParameter("sillaboen");
                String noteen= request.getParameter("noteen");
                String homepageen= request.getParameter("homepageen");
                String forumen= request.getParameter("forumen");
                String risorse_exten= request.getParameter("risorseen");
 
                Descrizione_en descrizioneen=((IgwDataLayer)request.getAttribute("datalayer")).createDescrizione_en();
                        
                descrizioneen.setCorso(corso);
                descrizioneen.setPrerequisiti(prerequisitien);
                descrizioneen.setObiettivi(obiettivien);
                descrizioneen.setMod_Esame(mod_esameen);
                descrizioneen.setMod_Insegnamento(mod_insegnamentoen);
                descrizioneen.setSillabo(sillaboen);
                descrizioneen.setNote(noteen);
                descrizioneen.setHomepage(homepageen);
                descrizioneen.setForum(forumen);
                descrizioneen.setRisorse_Ext(risorse_exten);
                
                
                
                //DUBLINO IT
                String knowledge = request.getParameter("knowledge");
                String application  = request.getParameter("application");
                String evaluation = request.getParameter("evaluation");
                String communication = request.getParameter("communication");
                String lifelong = request.getParameter("lifelong");
                
                Dublino_it dublinoit=((IgwDataLayer)request.getAttribute("datalayer")).createDublino_it();
                
                dublinoit.setCorso(corso);
                dublinoit.setKnowledge(knowledge);
                dublinoit.setApplication(application);
                dublinoit.setEvaluation(evaluation);
                dublinoit.setCommunication(communication);
                dublinoit.setLifelong(lifelong);
                
                //Dublinoen
                
                String knowledgeen = request.getParameter("knowledgeen");
                String applicationen  = request.getParameter("applicationen");
                String evaluationen = request.getParameter("evaluationen");
                String communicationen = request.getParameter("communicationen");
                String lifelongen = request.getParameter("lifelongen");
                
                Dublino_en dublinoen=((IgwDataLayer)request.getAttribute("datalayer")).createDublino_en();
                
                dublinoen.setCorso(corso);
                dublinoen.setKnowledge(knowledgeen);
                dublinoen.setApplication(applicationen);
                dublinoen.setEvaluation(evaluationen);
                dublinoen.setCommunication(communicationen);
                dublinoen.setLifelong(lifelongen);
                
                    
            //partenza store
            ((IgwDataLayer)request.getAttribute("datalayer")).storeCorso(corso);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDescrizione_it(descrizioneit);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDescrizione_en(descrizioneen);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDublino_it(dublinoit);
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDublino_en(dublinoen);
            
        HttpSession session= request.getSession(false);
        int id = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id);
        log.setDescrizione("Ha creato il corso "+""+nome);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        
        response.sendRedirect("Backoffice");
            
            } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
                
        
    }


    
@Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
            String lin;
            try{
            HttpSession s = SecurityLayer.checkSession(request);
            String username=(String)s.getAttribute("username");
            try {
                if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"CreateCorso")) {
             if (request.getParameter("crea") != null) {
                try {
                    action_crea(request, response);
                } catch (IOException | TemplateManagerException ex) {
                    Logger.getLogger(CreateCorso.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
        try {
            if(request.getParameter("lin")==null)
                lin="it";
            else
                lin=request.getParameter("lin");
            action_default(request, response,lin);

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
                Logger.getLogger(CreateCorso.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
            
            
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Login servlet";
    }// </editor-fold>

}
