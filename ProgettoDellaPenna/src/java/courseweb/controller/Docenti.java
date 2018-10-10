package courseweb.controller;

import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Docenti extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet", "Docenti?");
        request.setAttribute("change", "y");
        if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Docenti");
                res.activate("docenti.ftl.html", request, response); 
            }
            else{
                request.setAttribute("lingua","en");
                request.setAttribute("page_title", "Teachers");
                res.activate("docenti_en.ftl.html", request, response);
            }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
        try {
            if(request.getParameter("lin")==null)
                lin="it";
            else
                lin=request.getParameter("lin");
            action_default(request, response,lin);

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
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
        return "Main Corso servlet";
    }// </editor-fold>

}
