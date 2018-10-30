package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
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


public class CreateMateriale extends BaseController {
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,String lingua) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("servlet","CreateMateriale?");
            if(lingua.equals("it")||lingua.equals("")){
            try {
                request.setAttribute("lingua","it");
                request.setAttribute("page_title", "Backoffice");
                
                request.setAttribute("corso",((IgwDataLayer)request.getAttribute("datalayer")).getCorsiByAnno());
                

                HttpSession s = request.getSession(false);
                String a = (String) s.getAttribute("username");
                request.setAttribute("nome",a);
                 
                res.activate("createmateriale.ftl.html", request, response);
            } catch (DataLayerException ex) {
                Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, "CIAOOOO", ex);
            }
       

    }
    }
    
    public void action_crea(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        String nome=request.getParameter("nomem");
        String descrizioneit=request.getParameter("descrizioneitm");
        String descrizioneen=request.getParameter("descrizioneenm");
        int id=Integer.parseInt(request.getParameter("corso"));
        String filePath=null;
        String fileName;
        String context=request.getServletContext().getRealPath("");
        Part file=request.getPart("linkm");
        if(file.getSize()!=0){
            fileName=nome;
            filePath=Upload.Up(context,file,"Materiale",fileName,null);
        }
        Materiale materiale=((IgwDataLayer)request.getAttribute("datalayer")).createMateriale();
        materiale.setIDCorso(id);
        materiale.setDescrizione_it(descrizioneit);
        materiale.setDescrizione_en(descrizioneen);
        materiale.setNome(nome);
        materiale.setLink(filePath);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeMateriale(materiale);
        
        HttpSession session= request.getSession(false);
        int id1 = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");
        
        courseweb.model.interfacce.Log log=((IgwDataLayer)request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id1);
        log.setDescrizione("Ha aggiunto il materiale "+ nome);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer)request.getAttribute("datalayer")).storeLog(log);
        
    }
     
    
    
    
   @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String lin;
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            String username=(String)s.getAttribute("username");   
        try {
            if (((IgwDataLayer)request.getAttribute("datalayer")).getAccessUtente(username,"CreateMateriale")) {
            if(request.getParameter("lin")==null){
                lin="it";}
            else{
                lin=request.getParameter("lin");
            }
            if(request.getParameter("crea")!=null)
                action_crea(request,response);
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

}