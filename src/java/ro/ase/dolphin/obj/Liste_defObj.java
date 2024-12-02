/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.obj;

import java.math.BigDecimal;

/**
 *
 * @author ana t
 */
public class Liste_defObj {
  BigDecimal id_lista_def = null;
  String cod_lista = null;        
  String den_lista = null;
  BigDecimal order_no = null;
  String lista_adaugata=null;
  String err_message=null;
  
  
  public Liste_defObj() {
    
  }

  public BigDecimal getId_lista_def() {
    return id_lista_def;
  }

  public void setId_lista_def(BigDecimal id_lista_def) {
    this.id_lista_def = id_lista_def;
  }

  public String getCod_lista() {
    return cod_lista;
  }

  public void setCod_lista(String cod_lista) {
    this.cod_lista = cod_lista;
  }

  public String getDen_lista() {
    return den_lista;
  }

  public void setDen_lista(String den_lista) {
    this.den_lista = den_lista;
  }

  public BigDecimal getOrder_no() {
    return order_no;
  }

  public void setOrder_no(BigDecimal order_no) {
    this.order_no = order_no;
  }

    public String getLista_adaugata() {
        return lista_adaugata;
    }

    public void setLista_adaugata(String lista_adaugata) {
        this.lista_adaugata = lista_adaugata;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }
  
  
  
}
