package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AllCorsi extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","Corsianno?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                
                
                List<CDL> cdl= ((IgwDataLayer)request.getAttribute("datalayer")).getCDLNoMag();
                List<CDL> cdlm= ((IgwDataLayer)request.getAttribute("datalayer")).getCDLMag();
                
                request.setAttribute("cdl", cdl);
                request.setAttribute("cdlm", cdlm);
                
                
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("modificacorsi.ftl.html", request, response);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"Corsianno")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            
            if (request.getParameter("n") != null) {
            int n;
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_special(request, response, n, lin);
            }
            else {
                action_default(request, response,lin);
            }
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

   

    private void action_special(HttpServletRequest request, HttpServletResponse response, int id, String lingua) throws TemplateManagerException {
        
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("servlet","Corsianno?lin=it");
                if(lingua.equals("it")||lingua.equals("")){
                try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                
                List<CDL> cdl1= ((IgwDataLayer)request.getAttribute("datalayer")).getCDLNoMag();
                List<CDL> cdlm= ((IgwDataLayer)request.getAttribute("datalayer")).getCDLMag();
                
                CDL cdl =((IgwDataLayer)request.getAttribute("datalayer")).getCDL(id);
                
                List<Corso> corso = ((IgwDataLayer)request.getAttribute("datalayer")).getCorsiInCdlNoAnno(cdl);
                
                Map<Integer, List<Corso>> b= new LinkedHashMap();
                
                List<Corso> c;
                for(Corso corso1: corso){
                    int anno=corso1.getAnno();
                    if(b.containsKey(anno)){
                        c=b.get(anno);
                        c.add(corso1);
                        b.put(anno,c);
                    }
                    else{
                        c=new ArrayList();
                        c.add(corso1);
                        b.put(anno,c);
                    
                    }
 
                    }
                
                
                request.setAttribute("corso",b);
                request.setAttribute("cdl",cdl1);
                request.setAttribute("cdlm",cdlm);
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("corsianno.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE,"ciaooo", ex);
            }
       

    }
    }


}
 
