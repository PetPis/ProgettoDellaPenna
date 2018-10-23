package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Log extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","Log?");
            if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("log",((IgwDataLayer)request.getAttribute("datalayer")).getLog());
                
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                res.activate("log.ftl.html", request, response);
       

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
    
}
 
