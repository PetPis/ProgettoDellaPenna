package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
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



public class UpdateLibroD extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","UpdateLibroD?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                String b=request.getSession().getId();
                request.setAttribute("session_id",b);
                
                int id=(int) s.getAttribute("docenteid");
                
                Docente docente=((IgwDataLayer)request.getAttribute("datalayer")).getDocente(id);
                
                request.setAttribute("corso",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiDelDocente(docente));

                 
                res.activate("updatelibroD.ftl.html", request, response);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"BackofficeD")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            
            if(request.getParameter("modifica")!=null)
                action_modifica(request,response);
            if(request.getParameter("cancella")!=null)
                try {
                    action_elimina(request,response);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateLibro.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (request.getParameter("n") != null) {
            int n;
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_selcorso(request, response, n, lin);
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

    private void action_selcorso(HttpServletRequest request, HttpServletResponse response, int id ,String lin) throws IOException, TemplateManagerException {
        
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","UpdateLibroD?");
            if(lin.equals("it")||lin.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                String b=request.getSession().getId();
                request.setAttribute("session_id",b);
                
                int i=(int) s.getAttribute("docenteid");
                
                Docente docente=((IgwDataLayer)request.getAttribute("datalayer")).getDocente(i);
                
                request.setAttribute("corsi",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiDelDocente(docente));
                
                Corso corso = ((IgwDataLayer)request.getAttribute("datalayer")).getCorso(id);
                
                request.setAttribute("libri" , corso.getLibri());
                
                
                
               
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    
    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException{
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
        int id=Integer.parseInt(request.getParameter("id"));
        
        Libro libro=((IgwDataLayer)request.getAttribute("datalayer")).getLibro(id);
        if(!libro.getAutore().equals(autore))
            libro.setAutore(autore);
        if(libro.getAnno()!=anno)
            libro.setAnno(anno);
        if(!libro.getTitolo().equals(titolo))
            libro.setTitolo(titolo);
        if(libro.getVolume()!=volume)
            libro.setVolume(volume);
        if(!libro.getEditore().equals(editore))
            libro.setEditore(editore);
        if(libro.getLink().equals(link))
            libro.setLink(link);
        
        String nomelog=libro.getTitolo();
       HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha modificato il libro "+ nomelog);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLibro(libro,0);
        response.sendRedirect("BackofficeD");
    }
    
    
    public void action_elimina(HttpServletRequest request, HttpServletResponse response) throws IOException,DataLayerException, SQLException{
        int id=Integer.parseInt(request.getParameter("id"));
        Libro libro=((IgwDataLayer)request.getAttribute("datalayer")).getLibro(id);
        
        String nomelog=libro.getTitolo();
       HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha cancellato il libro "+ nomelog);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        
        
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteLibro(libro);
        response.sendRedirect("BackofficeD");
    } 
}

 
