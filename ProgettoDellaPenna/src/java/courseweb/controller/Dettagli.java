package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.Descrizione_it;
import courseweb.model.interfacce.Docente;
import courseweb.model.interfacce.Dublino_it;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Libro;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Dettagli extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int id, String lingua) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            
            Corso corso = ((IgwDataLayer)request.getAttribute("datalayer")).getCorso(id);
            
            request.setAttribute("propedeudici",corso.getCorsiPrerequisiti());
            request.setAttribute("mutuati",corso.getCorsiMutuati());
            request.setAttribute("mutuato",corso.getCorsoMutua());
            request.setAttribute("moduli",corso.getCorsiModulo());
            request.setAttribute("corso", corso);
            request.setAttribute("docenti", corso.getDocenti());
            request.setAttribute("libri", corso.getLibri());
            request.setAttribute("change","y");
            request.setAttribute("servlet","Dettagli?n="+id+"&");
            request.setAttribute("cdl",((IgwDataLayer)request.getAttribute("datalayer")).getCDLInCorso(corso));
            
            HttpSession session= request.getSession(false);
            if(session!=null && request.isRequestedSessionIdValid()){
            String a = (String) session.getAttribute("username");
            request.setAttribute("nome",a);
            boolean doc = (boolean) session.getAttribute("docente");
            if(doc==true){
            int id1=(int) session.getAttribute("docenteid");
            request.setAttribute("docente",id1);}
            }
            
            request.setAttribute("descrizione_it", corso.getDescrizione_it());
            request.setAttribute("dublino_it", corso.getDublino_it());
            request.setAttribute("descrizione_en", corso.getDescrizione_en());
            request.setAttribute("dublino_en", corso.getDublino_en());
            
            if(request.getParameter("new")!=null){
                int n=SecurityLayer.checkNumeric(request.getParameter("new"));
                request.setAttribute("precedenti", ((IgwDataLayer)request.getAttribute("datalayer")).getCorso(n).getAnniPrecedenti());
                request.setAttribute("nn", n);
            }
            else{
                if(corso.getAnniPrecedenti()!=null){
                    request.setAttribute("precedenti", corso.getAnniPrecedenti());
                    request.setAttribute("nn", corso.getID());
                }
            }
            if(lingua.equals("it")||lingua.equals("")){
                request.setAttribute("lingua","it");
                String title;
                if(corso.getNome_it().trim().isEmpty())
                    title=corso.getNome_en();
                else
                    title=corso.getNome_it();
                request.setAttribute("page_title", title);                
                res.activate("corsi.ftl.html", request, response); 
            }
            else{
                request.setAttribute("lingua","en");
                String title;
                if(corso.getNome_en().trim().isEmpty())
                    title=corso.getNome_it();
                else
                    title=corso.getNome_en();
                request.setAttribute("page_title", title);
                res.activate("corsi_en.ftl.html", request, response);
            }
            
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        int n;
        String lin;
        try {
            if(request.getParameter("lin")==null)
                lin="it";
            else
                lin=request.getParameter("lin");
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_default(request, response, n, lin);
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Chiave corso non specificata");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }
    
    @Override
    public String getServletInfo() {
        return "Main Login servlet";
    }

}
