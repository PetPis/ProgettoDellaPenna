package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Docenti extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());

            request.setAttribute("docenti", ((IgwDataLayer) request.getAttribute("datalayer")).getDocente());
            request.setAttribute("servlet", "listdocenti?");
            request.setAttribute("change", "y");
            HttpSession session = request.getSession(false);
            if (session != null && request.isRequestedSessionIdValid()) {
                String a = (String) session.getAttribute("username");
                request.setAttribute("nome", a);
                boolean doc = (boolean) session.getAttribute("docente");
                if (doc == true) {
                    int id = (int) session.getAttribute("docenteid");
                    request.setAttribute("docente", id);
                }
            }
            if (lingua.equals("it") || lingua.equals("")) {
                request.setAttribute("lingua", "it");
                request.setAttribute("page_title", "Lista Docenti");
                res.activate("docenti.ftl.html", request, response);
            } else {
                request.setAttribute("lingua", "en");
                request.setAttribute("page_title", "Teachers List");
                res.activate("docenti_en.ftl.html", request, response);
            }
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
        try {
            if (request.getParameter("lin") == null) {
                lin = "it";
            } else {
                lin = request.getParameter("lin");
            }
            action_default(request, response, lin);

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
