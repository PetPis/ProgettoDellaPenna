/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseweb.model.interfacce;

import courseweb.controller.data.DataLayerException;
import java.util.List;
/**
 *
 * @author Toni & Tony
 */
public interface Docenti_Corso {
    
    Corso getCorso(int id_corso) throws DataLayerException;
    
    int getIDCorso(int id_corso);
    
    void setIDCorso(int id_corso);
    
    Docente getDocente(int id_docente) throws DataLayerException;
    
    int getIDDocente(int id_docente);
    
    void setIDDocente(int id_docente);
    
    List<Docente> getDocenti(int corso) throws DataLayerException;
    
    List<Corso> getCorsi(int docente) throws DataLayerException;
    
}
