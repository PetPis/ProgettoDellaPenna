package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Utente;
import courseweb.model.interfacce.Log;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

@MultipartConfig

public class ModificaDocente extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","ModificaCorso?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("docenti",((IgwDataLayer)request.getAttribute("datalayer")).getDocente());

                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("modificadocente.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            String nome= request.getParameter("nome");
            String cognome= request.getParameter("cognome");
            Docente doc=((IgwDataLayer)request.getAttribute("datalayer")).getDocente(id);
                String imgPath=doc.getImmagine();
                String fileName;
                String context=request.getServletContext().getRealPath("");
                Part immagine=request.getPart("immagine");
                if(immagine.getSize()!=0){
                    fileName=nome+cognome;
                    imgPath=Upload.Up(context,immagine,"imgDocenti",fileName,imgPath);
                }
                String email= request.getParameter("email");
                String ufficio= request.getParameter("ufficio");
                String telefono= request.getParameter("telefono");
                String specializzazione= request.getParameter("specializzazione");
                String ricerche= request.getParameter("ricerche");
                String pubblicazioni= request.getParameter("pubblicazioni");
                
                Part curriculum=request.getPart("curriculum");
                String currPath=doc.getCurriculum();
                if(curriculum.getSize()!=0){
                    fileName=nome+cognome;
                    currPath=Upload.Up(context,curriculum,"curriculum",fileName,currPath);
                }
                String ricevimento= request.getParameter("ricevimento");
                
                if(!doc.getNome().equals(nome))
                    doc.setNome(nome);
                if(!doc.getCognome().equals(cognome))
                    doc.setCognome(cognome);
                if(!doc.getEmail().equals(email))
                    doc.setEmail(email);
                if(!doc.getUfficio().equals(ufficio))
                    doc.setUfficio(ufficio);
                if(!doc.getTelefono().equals(telefono))
                    doc.setTelefono(telefono);
                if(!doc.getSpecializzazione().equals(specializzazione))
                    doc.setSpecializzazione(specializzazione);
                if(!doc.getRicerche().equals(ricerche))
                    doc.setRicerche(ricerche);
                if(!doc.getPubblicazione().equals(pubblicazioni))
                    doc.setPubblicazioni(pubblicazioni);
                if(!doc.getRicevimento().equals(ricevimento))
                    doc.setRicevimento(ricevimento);
                if(!doc.getImmagine().equals(imgPath))
                    doc.setImmagine(imgPath);
                if(!doc.getCurriculum().equals(currPath))
                    doc.setCurriculum(currPath);
                
                ((IgwDataLayer)request.getAttribute("datalayer")).storeDocente(doc);
                
                HttpSession session= request.getSession(false);
                int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
            Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
            log.setIDUtente(id1);
            log.setDescrizione("Ha modificato il docente "+" "+ nome +" "+cognome);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"ModificaDocente")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            
            if(request.getParameter("cancella")!=null)
                try {
                    action_elimina(request,response);
            } catch (SQLException ex) {
                Logger.getLogger(ModificaDocente.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(request.getParameter("modifica")!=null){
                action_modifica(request,response);}
            
            if (request.getParameter("n") != null) {
            int n;
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_seldocente(request, response, n, lin);
            }
            action_default(request, response,lin);
            }
            else {
                SecurityLayer.disposeSession(request);
                    response.sendRedirect("Login?referrer=" + URLEncoder.encode(request.getRequestURI(), "UTF-8"));
            }
            } catch (DataLayerException ex) {
                Logger.getLogger(AllCorsi.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    private void action_seldocente(HttpServletRequest request, HttpServletResponse response,int id ,String lin) throws IOException, TemplateManagerException {
        
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","ModificaDocente?");
            if(lin.equals("it")||lin.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                
                request.setAttribute("docente",((IgwDataLayer)request.getAttribute("datalayer")).getDocente(id));
             
                request.setAttribute("docenti",((IgwDataLayer)request.getAttribute("datalayer")).getDocente());
               
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    private void action_elimina(HttpServletRequest request, HttpServletResponse response) throws IOException,DataLayerException,SQLException {
        int id=Integer.parseInt(request.getParameter("id"));
        Docente docente=((IgwDataLayer)request.getAttribute("datalayer")).getDocente(id);
        Utente utente=((IgwDataLayer)request.getAttribute("datalayer")).getUtenteByDocente(docente);
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteUtente(utente);
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteDocente(docente);
        
    
        
    }

}

 
