package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CreateLibro extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","LibroNew?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("corso",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());
                

                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                 
                res.activate("libronew.ftl.html", request, response);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"LibroNew")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            if(request.getParameter("aggiungi")!=null)
                action_aggiungi(request,response);
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
    
    public void action_aggiungi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        String autore=request.getParameter("autore");
        String titolo=request.getParameter("titolo");
        int volume=0;
        if(request.getParameter("volume").length()!=0)
            volume=Integer.parseInt(request.getParameter("volume"));
        int anno=0;
        if(request.getParameter("anno").length()!=0)
            anno=Integer.parseInt(request.getParameter("anno"));
        String editore=request.getParameter("editore");
        String link=request.getParameter("link");
        int corso=Integer.parseInt(request.getParameter("corso"));
        Libro libro=((IgwDataLayer)request.getAttribute("datalayer")).createLibro();
        libro.setAutore(autore);
        libro.setAnno(anno);
        libro.setTitolo(titolo);
        libro.setVolume(volume);
        libro.setEditore(editore);
        libro.setLink(link);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLibro(libro,corso);
        
        
        HttpSession session= request.getSession(false);
        int id = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id);
        log.setDescrizione("Ha aggiunto il libro"+ titolo +"corso"+ corso);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
    }    
}