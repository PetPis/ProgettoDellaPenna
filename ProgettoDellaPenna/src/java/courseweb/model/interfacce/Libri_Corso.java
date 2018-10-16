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
public interface Libri_Corso {
    
    Corso getCorso(int id_corso) throws DataLayerException;
    
    int getIDCorso();
    
    void setIDCorso(int id_corso);
    
    Libro getLibro(int id_libro) throws DataLayerException;
    
    int getIDLibro();
    
    void setIDLibro(int id_libro);
    
    List<Libro> getLibroCollection(int id_corso);
    
}
