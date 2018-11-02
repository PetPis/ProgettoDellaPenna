package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Utente;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig

public class CreateDocente extends BaseController {


    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        HttpSession session= request.getSession(false);
        String a = (String) session.getAttribute("username");
        request.setAttribute("nome",a);
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Backoffice");
    
        if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                res.activate("createdocente.ftl.html", request, response); 
            }
  
    }
    
    
    private void action_registraD(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
                try{
                
                    
                
                GeneratePassword random = new GeneratePassword();
                
                String password=random.nextString();

                String username1=null ,username2=null;
                
                String nome= request.getParameter("nome");
                String cognome= request.getParameter("cognome");
                
                String imgPath="";
                String fileName;
                String context=request.getServletContext().getRealPath("");
                Part immagine=request.getPart("immagine");
                if(immagine.getSize()!=0){
                    fileName=nome+cognome;
                    imgPath=Upload.Up(context,immagine,"imgDocenti",fileName,null);
                }
                else{
                    
                    imgPath="imgDocenti\\default.jpg";
                }
                
                //String immagine= request.getParameter("immagine");
                String email= request.getParameter("email");
                String ufficio= request.getParameter("ufficio");
                String telefono= request.getParameter("telefono");
                String specializzazione= request.getParameter("specializzazione");
                String ricerche= request.getParameter("ricerche");
                String pubblicazioni= request.getParameter("pubblicazioni");
                
                Part curriculum=request.getPart("curriculum");
                String currPath="";
                if(curriculum.getSize()!=0){
                    fileName=nome+cognome;
                    currPath=Upload.Up(context,curriculum,"curriculum",fileName,null);
                }
                //String curriculum= request.getParameter("curriculum");
                String ricevimento= request.getParameter("ricevimento");
                
                   
                  if(nome.length()>=3){
                  username1=nome.toLowerCase().substring(0, 2);}
                  else{
                      username1=nome.toLowerCase();
                    }
                  
                  if(cognome.length()>=3){
                  username2=cognome.toLowerCase().substring(0, 2);
                  }else{
                      username2=nome.toLowerCase();
                    }
                  
                  String username=username1.concat(username2);
                  int x=1;
                  while(((IgwDataLayer)request.getAttribute("datalayer")).existUtente(username)){
                      username+=x;
                      x++;
                  }
                  
                Docente docente=((IgwDataLayer)request.getAttribute("datalayer")).createDocente();
                Utente utente=((IgwDataLayer)request.getAttribute("datalayer")).createUtente();
                
                docente.setImmagine(imgPath);
                docente.setNome(nome);
                docente.setCognome(cognome);
                docente.setEmail(email);
                docente.setUfficio(ufficio);
                docente.setTelefono(telefono);
                docente.setSpecializzazione(specializzazione);
                
                docente.setRicerche(ricerche);
                docente.setPubblicazioni(pubblicazioni);
                
                docente.setCurriculum(currPath);
                docente.setRicevimento(ricevimento);
  
                
            ((IgwDataLayer)request.getAttribute("datalayer")).storeDocente(docente);
            
            int id=docente.getIDDocente();
            
            utente.setDocente(id);
            utente.setUsername(username);
            utente.setPassword(password);
            utente.setIDGruppo(2);
            
            ((IgwDataLayer)request.getAttribute("datalayer")).storeUtente(utente);
            HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha registrato il docente "+ nome +""+""+cognome);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
            
                request.setAttribute("message", "Upload has been done successfully!");
                response.sendRedirect("Backoffice");
            
            } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
                
        
    }

 
    
    
    
@Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
            String lin;
            try{
            HttpSession s = SecurityLayer.checkSession(request);
            String username=(String)s.getAttribute("username");
            try {
                if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"RegisterDocente")) {
                     if (request.getParameter("registra") != null) {
                        try {
                            action_registraD(request, response);
                        } catch (IOException | TemplateManagerException ex) {
                            Logger.getLogger(CreateDocente.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CreateDocente.class.getName()).log(Level.SEVERE, null, ex);
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
