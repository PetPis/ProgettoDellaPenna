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

public class Backoffice extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet", "Backend?");
        if (lingua.equals("it") || lingua.equals("")) {

            request.setAttribute("lingua", "it");
            request.setAttribute("page_title", "Backoffice");

            HttpSession s = request.getSession(false);
            String a = (String) s.getAttribute("username");
            request.setAttribute("nome", a);

            String b = request.getSession().getId();
            request.setAttribute("session_id", b);

            res.activate("backoffice.ftl.html", request, response);

        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            String username = (String) s.getAttribute("username");
            if (((IgwDataLayer) request.getAttribute("datalayer")).getAccessUtente(username, "Backoffice")) {
                if (request.getParameter("lin") == null) {
                    lin = "it";
                } else {
                    lin = request.getParameter("lin");
                }

                action_default(request, response, lin);
            } else {
                //se la pagina non è accessibile come utente anonimo, ridirigiamo a quella di login
                //if this page cannot be accessed as anonymous user, redirect to the login page
                //notare come passiamo alla servlet di login la nostra URL come referrer
                //note how we pass to the login servlet our URL as the referrer
                SecurityLayer.disposeSession(request);
                response.sendRedirect("Login?referrer=" + URLEncoder.encode(request.getRequestURI(), "UTF-8"));
                //...oppure dichiariamo che è richiesta la login, ma lasciamo all'utente la scelta
                //...or declare that a login is required and let the user choose
                //action_accessdenied(request, response);
                //...o generiamo un errore
                //...or output an error message
                //request.setAttribute("exception", new Exception("Access not allowed"));
                //action_error(request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Corso servlet";
    }// </editor-fold>

}
