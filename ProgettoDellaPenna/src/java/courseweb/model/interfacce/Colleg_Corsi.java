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
public interface Colleg_Corsi {
    
    Corso getCorso(int id_corso)throws DataLayerException;
    
    int getThis_Corso();
    
    void setThis_Corso(int this_corso);
    
    int getOther_Corso();
    
    void setOther_Corso(int other_corso);
    
    String getTipo();
    
    void setTipo(String tipo);
    
    List<Corso> getCorsiMutuati(int corso);
    
    List<Corso> getCorsiPrerequisiti(int corso);
    
    List<Corso> getCorsiModulo(int corso);
    
}
