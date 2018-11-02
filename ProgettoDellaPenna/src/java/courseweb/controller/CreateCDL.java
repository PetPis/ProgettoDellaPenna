package courseweb.controller;

import courseweb.controller.data.DataLayerException;
import courseweb.controller.security.SecurityLayer;
import courseweb.model.interfacce.CDL;
import courseweb.model.interfacce.Log;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

@MultipartConfig

public class CreateCDL extends BaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, String lingua) throws IOException, ServletException, TemplateManagerException {
        HttpSession session = request.getSession(false);
        String a = (String) session.getAttribute("username");
        request.setAttribute("nome", a);
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Backoffice");

        if (lingua.equals("it") || lingua.equals("")) {
            request.setAttribute("lingua", "it");
            res.activate("createcdl.ftl.html", request, response);
        }

    }

    private void action_creaCDL(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        String nome = request.getParameter("nome");
        String nomeen = request.getParameter("nome_en");
        String abbr_it = request.getParameter("abbr_it");
        String abbr_en = request.getParameter("abbr_en");
        int cfu = 0;
        if (request.getParameter("cfu").length() != 0) {
            cfu = Integer.parseInt(request.getParameter("cfu"));
        }
        String mag = request.getParameter("mag");
        int magistrale = 0;
        if (mag != null) {
            magistrale = 1;
        }
        String descrizione_it = request.getParameter("descrizione_it");
        String descrizione_en = request.getParameter("descrizione_en");
        String imgPath = "";
        String fileName;
        String context = request.getServletContext().getRealPath("");
        Part immagine = request.getPart("immagine");
        if (immagine.getSize() != 0) {
            fileName = nome;
            imgPath = Upload.Up(context, immagine, "imgCDL", fileName, null);
        }
        else{
            imgPath="imgCDL\\default.png";
        }

        CDL cdl = ((IgwDataLayer) request.getAttribute("datalayer")).createCDL();
        cdl.setNome_it(nome);
        cdl.setNome_en(nomeen);
        cdl.setAbbr_it(abbr_it);
        cdl.setAbbr_en(abbr_en);
        cdl.setCfu(cfu);
        cdl.setMagistrale(magistrale);
        cdl.setDescrizione_it(descrizione_it);
        cdl.setDescrizione_en(descrizione_en);
        cdl.setImmagine(imgPath);

        ((IgwDataLayer) request.getAttribute("datalayer")).storeCDL(cdl);

        HttpSession session = request.getSession(false);
        int id = (int) session.getAttribute("userid");
        //int id = (int) session.getAttribute("docenteid");

        Log log = ((IgwDataLayer) request.getAttribute("datalayer")).CreateLog();
        log.setIDUtente(id);
        log.setDescrizione("Ha creato il cdl " + "" + nome);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setData(timestamp);
        ((IgwDataLayer) request.getAttribute("datalayer")).storeLog(log);

        request.setAttribute("message", "Upload has been done successfully!");
        response.sendRedirect("Backoffice");

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String lin;
        HttpSession s = SecurityLayer.checkSession(request);
        String username = (String) s.getAttribute("username");
        try {
            if (((IgwDataLayer) request.getAttribute("datalayer")).getAccessUtente(username, "CreateCDL")) {
                if (request.getParameter("crea") != null) {
                    try {
                        action_creaCDL(request, response);
                    } catch (IOException | TemplateManagerException ex) {
                        Logger.getLogger(CreateCDL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        if (request.getParameter("lin") == null) {
                            lin = "it";
                        } else {
                            lin = request.getParameter("lin");
                        }
                        action_default(request, response, lin);

                    } catch (IOException | TemplateManagerException ex) {
                        request.setAttribute("exception", ex);
                        action_error(request, response);

                    }
                }
            } else {
                SecurityLayer.disposeSession(request);
                try {
                    response.sendRedirect("Login?referrer=" + URLEncoder.encode(request.getRequestURI(), "UTF-8"));
                } catch (IOException ex) {
                    Logger.getLogger(CreateCDL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(CreateCDL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Login servlet";
    }// </editor-fold>

}
