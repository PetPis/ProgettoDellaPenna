package courseweb.controller;

import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Home extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        
        request.setAttribute("page_title", "Home");
        request.setAttribute("change","y");
            request.setAttribute("servlet","Home?");
        if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                res.activate("homepage.ftl.html", request, response); 
            }
            else{
                request.setAttribute("lingua","en");
                res.activate("homepage_en.ftl.html", request, response);
            }   
    }

    
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
            //boolean b=false;
        try {
            /*Enumeration list=request.getParameterNames();
            while(list.hasMoreElements())
                if(list.nextElement().toString().equals("lin")){
                    b=true;
                    break;
                }
            if(b)
                lin=request.getParameter("lin");
            lin="it";
            */
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