package courseweb.controller;

import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Login extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet", "Login?");
        if (lingua.equals("it") || lingua.equals("")) {
            request.setAttribute("lingua", "it");

            request.setAttribute("page_title", "Login");
            res.activate("login.ftl.html", request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password= request.getParameter("password");
        
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {

                if (request.getParameter("lin") == null) {
                    lin = "it";
                } else {
                    lin = request.getParameter("lin");
                }
                action_default(request, response, lin);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
