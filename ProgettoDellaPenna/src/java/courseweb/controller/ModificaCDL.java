package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.view.FailureResult;
import courseweb.view.TemplateManagerException;
import courseweb.view.TemplateResult;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import courseweb.model.interfacce.Log;

@MultipartConfig
public class ModificaCDL extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","ModificaCDL?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("cdl",((IgwDataLayer)request.getAttribute("datalayer")).getCDLNoMag());
                request.setAttribute("cdlm",((IgwDataLayer)request.getAttribute("datalayer")).getCDLMag());

                
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("modificacdl.ftl.html", request, response);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"Allcorsi")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            
            if(request.getParameter("modifica")!=null)
                action_modifica(request,response);
            if(request.getParameter("cancella")!=null)
                action_elimina(request,response);
            
            
            
            
            if (request.getParameter("n") != null) {
            int n;
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            action_selcdl(request, response, n, lin);
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

    private void action_selcdl(HttpServletRequest request, HttpServletResponse response,int id ,String lin) throws IOException, TemplateManagerException {
        
                TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","ModificaCDL?");
            if(lin.equals("it")||lin.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                request.setAttribute("cdl",((IgwDataLayer)request.getAttribute("datalayer")).getCDLNoMag());
                request.setAttribute("cdlm",((IgwDataLayer)request.getAttribute("datalayer")).getCDLMag());
                request.setAttribute("cdls",((IgwDataLayer)request.getAttribute("datalayer")).getCDL(id));
                

                
                
                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                
                
                res.activate("modificacdl.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    public void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException,DataLayerException{
        String nome=request.getParameter("nome");
        String nome_en=request.getParameter("nome_en");
        String abbr_it=request.getParameter("abbr_it");
        String abbr_en=request.getParameter("abbr_en");
        int cfu=0;
        if(request.getParameter("cfu")!=null)
            cfu=Integer.parseInt(request.getParameter("cfu"));
        int magistrale=0;
        if(request.getParameter("mag")!=null)
            magistrale=1;
        String descrizione_it=request.getParameter("descrizione_it");
        String descrizione_en=request.getParameter("descrizione_en");
        int id=Integer.parseInt(request.getParameter("id"));
        CDL cdl=((IgwDataLayer)request.getAttribute("datalayer")).getCDL(id);
        String imgPath=cdl.getImmagine();
        String fileName;
        String context=request.getServletContext().getRealPath("");
        Part immagine=request.getPart("immagine");
        if(immagine.getSize()!=0){
            fileName=nome;
            imgPath=Upload.Up(context,immagine,"imgCDL",fileName,imgPath);
        }
        if(!cdl.getNome_it().equals(nome))
            cdl.setNome_it(nome);
        if(!cdl.getNome_en().equals(nome_en))
            cdl.setNome_en(nome_en);
        if(!cdl.getAbbr_it().equals(abbr_it))
            cdl.setAbbr_it(abbr_it);
        if(!cdl.getAbbr_en().equals(abbr_en))
            cdl.setAbbr_en(abbr_en);
        if(cdl.getCfu()!=cfu)
            cdl.setCfu(cfu);
        if(cdl.getMagistrale()!=magistrale)
            cdl.setMagistrale(magistrale);
        if(!cdl.getDescrizione_it().equals(descrizione_it))
            cdl.setDescrizione_it(descrizione_it);
        if(!cdl.getDescrizione_en().equals(descrizione_en))
            cdl.setDescrizione_en(descrizione_en);
        if(!cdl.getImmagine().equals(imgPath))
            cdl.setImmagine(imgPath);
        
        ((IgwDataLayer)request.getAttribute("datalayer")).storeCDL(cdl);
        
        HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha modificato il cdl "+" "+ nome);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        response.sendRedirect("Backoffice");
    }
    
    public void action_elimina(HttpServletRequest request, HttpServletResponse response) throws IOException,DataLayerException{
        int id=Integer.parseInt(request.getParameter("id"));
        CDL cdl=((IgwDataLayer)request.getAttribute("datalayer")).getCDL(id);
        String context=request.getServletContext().getRealPath("");
        Upload.delete(context, cdl.getImmagine());
        
        String nomelog=cdl.getNome_it();
        HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        
        Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha cancellato il cdl " + " "+ nomelog);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteCDL(cdl);
        
        response.sendRedirect("Backoffice");
        
    }
    
    
    
}

 
