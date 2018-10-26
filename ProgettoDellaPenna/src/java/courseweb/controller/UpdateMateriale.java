package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.Corso;
import courseweb.model.interfacce.IgwDataLayer;
import courseweb.model.interfacce.Materiale;
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

@MultipartConfig

public class UpdateMateriale extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","UpdateMateriale?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("corsi",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());

                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                 
                res.activate("updatemateriale.ftl.html", request, response);
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
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"ModificaDocente")) {
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
            action_selcorso(request, response, n, lin);
            }
            action_default(request, response,lin);
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

    private void action_selcorso(HttpServletRequest request, HttpServletResponse response,int id ,String lin) throws IOException, TemplateManagerException {
        
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","UpdateMateriale?");
            if(lin.equals("it")||lin.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("corsi",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());
                
                Corso corso = ((IgwDataLayer)request.getAttribute("datalayer")).getCorso(id);
                request.setAttribute("materiale" , corso.getMateriale());
                
                
                
               
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException{
        String nome=request.getParameter("nomem");
        String descrizioneit=request.getParameter("descrizioneitm");
        String descrizioneen=request.getParameter("descrizioneenm");
        int id=Integer.parseInt(request.getParameter("id"));
        Materiale materiale=((IgwDataLayer)request.getAttribute("datalayer")).getMateriale(id);
        String filePath=materiale.getLink();
        String fileName;
        String context=request.getServletContext().getRealPath("");
        Part file=request.getPart("linkm");
        if(file.getSize()!=0){
            fileName=nome;
            filePath=Upload.Up(context,file,"Materiale",fileName,filePath);
        }
        if(!materiale.getNome().equals(nome))
            materiale.setNome(nome);
        if(!materiale.getDescrizione_it().equals(descrizioneit))
            materiale.setDescrizione_it(descrizioneit);
        if(!materiale.getDescrizione_en().equals(descrizioneen))
            materiale.setDescrizione_en(descrizioneen);
        if(!materiale.getLink().equals(filePath))
            materiale.setLink(nome);
        
        ((IgwDataLayer)request.getAttribute("datalayer")).storeMateriale(materiale);
        
        
    }
    
    
    public void action_elimina(HttpServletRequest request, HttpServletResponse response) throws IOException,DataLayerException{
        int id=Integer.parseInt(request.getParameter("id"));
        Materiale materiale=((IgwDataLayer)request.getAttribute("datalayer")).getMateriale(id);
        String context=request.getServletContext().getRealPath("");
        Upload.delete(context, materiale.getLink());
        
        String nomelog=materiale.getNome();
       HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha cancellato il materiale"+""+ nomelog);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        
        ((IgwDataLayer)request.getAttribute("datalayer")).deleteMateriale(materiale);
    
    }
    
}

 
