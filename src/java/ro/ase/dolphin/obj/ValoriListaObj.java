/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.obj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ana t
 */
public class ValoriListaObj {
  
  List<Liste_defObj> valoriLista = null;
  String valoare_lista_adaugata=null;
  String err_message=null;

  public ValoriListaObj() {
    
     valoriLista = new ArrayList<Liste_defObj>() ;
  }

  public List<Liste_defObj> getValoriLista() {
    return valoriLista;
  }

  public void setValoriLista(List<Liste_defObj> valoriLista) {
    this.valoriLista = valoriLista;
  }
  
  public void addValoriLista( Liste_defObj liste_defObj) {
    this.valoriLista.add(liste_defObj);
  }
  
 
  public String getValoare_lista_adaugata() {
        return valoare_lista_adaugata;
    }

    public void setValoare_lista_adaugata(String valoare_lista_adaugata) {
        this.valoare_lista_adaugata = valoare_lista_adaugata;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }
  
  
  
}
