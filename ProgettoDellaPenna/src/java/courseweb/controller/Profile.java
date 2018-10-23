package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Profile extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    
    
    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws DataLayerException, IOException, ServletException, TemplateManagerException {
        String nome=request.getParameter("nome");
        String old=request.getParameter("old");
        String password=request.getParameter("password");
        int id=Integer.parseInt(request.getParameter("id"));
        Utente utente=((IgwDataLayer)request.getAttribute("datalayer")).getUtente(id);
        if(!nome.trim().isEmpty()&&((IgwDataLayer)request.getAttribute("datalayer")).existUtente(nome)&&!utente.getUsername().equals(nome)){
            request.setAttribute("message", "Nome già esistente");
            action_default(request,response,"");
        }
        if(((IgwDataLayer)request.getAttribute("datalayer")).getUtenti(nome, old)==null){
            request.setAttribute("message", "Password errata");
            action_default(request,response,"");
        }
        
        utente.setUsername(nome);
        if(!utente.getPassword().equals(password))
            utente.setPassword(password);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeUtente(utente);
        HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha modifica il suo profilo");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        response.sendRedirect("Login");
    }
     
     
     
     
     
     
    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","Profile?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Profilo");
                
                
                
                HttpSession session= request.getSession(false);
                
                String a = (String) session.getAttribute("username");
                request.setAttribute("nome",a);
                
                int id = (int) session.getAttribute("userid");
                
                request.setAttribute("utente", ((IgwDataLayer)request.getAttribute("datalayer")).getUtente(id));
                
                res.activate("profile.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
            }
       

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
                if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"Profile")) {
                if (request.getParameter("profilo") != null) {
                    try {
                        action_modifica(request, response);
                    } catch (TemplateManagerException ex) {
                        Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
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
