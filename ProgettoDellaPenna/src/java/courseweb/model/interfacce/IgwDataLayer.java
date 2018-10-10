/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayer;
import courseweb.controller.data.DataLayerException;
import java.util.List;


public interface IgwDataLayer extends DataLayer {

    Utente createUtente();
    public Utente getUtenti(String username, String password) throws DataLayerException;
    Gruppo createGruppo();
    Gruppo getGruppo(int IDGruppo) throws DataLayerException;
    Servizio createServizio();
    Servizio getServizio(int IDServizio) throws DataLayerException;
    public List<Servizio> getServiziPerGruppo(Gruppo gruppo) throws DataLayerException;
}
