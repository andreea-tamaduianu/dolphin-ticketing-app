/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.obj;

import java.math.BigDecimal;

/**
 *
 * @author felix
 */
public class DetaliuTichetObj {
  
  
      private BigDecimal id_tichet  = null;
      private BigDecimal nr_tichet  = null;
      private BigDecimal id_tichet_det  = null;
      private BigDecimal id_tip_det  = null;
      private  String tip_det  = null;
      private  String den_tip_det  = null;
      private  String descriere  = null;
      private  String data_act  = null;
      private  String ora_act  = null;
      private BigDecimal id_utiliz  = null;
      private  String nume_utiliz  = null;
      
      public DetaliuTichetObj() {
        
      } 

  public BigDecimal getId_tichet() {
    return id_tichet;
  }

  public void setId_tichet(BigDecimal id_tichet) {
    this.id_tichet = id_tichet;
  }

  public BigDecimal getNr_tichet() {
    return nr_tichet;
  }

  public void setNr_tichet(BigDecimal nr_tichet) {
    this.nr_tichet = nr_tichet;
  }

  public BigDecimal getId_tichet_det() {
    return id_tichet_det;
  }

  public void setId_tichet_det(BigDecimal id_tichet_det) {
    this.id_tichet_det = id_tichet_det;
  }

  public BigDecimal getId_tip_det() {
    return id_tip_det;
  }

  public void setId_tip_det(BigDecimal id_tip_det) {
    this.id_tip_det = id_tip_det;
  }

  public String getTip_det() {
    return tip_det;
  }

  public void setTip_det(String tip_det) {
    this.tip_det = tip_det;
  }

  public String getDen_tip_det() {
    return den_tip_det;
  }

  public void setDen_tip_det(String den_tip_det) {
    this.den_tip_det = den_tip_det;
  }

  public String getDescriere() {
    return descriere;
  }

  public void setDescriere(String descriere) {
    this.descriere = descriere;
  }

  public String getData_act() {
    return data_act;
  }

  public void setData_act(String data_act) {
    this.data_act = data_act;
  }

  public String getOra_act() {
    return ora_act;
  }

  public void setOra_act(String ora_act) {
    this.ora_act = ora_act;
  }

  public BigDecimal getId_utiliz() {
    return id_utiliz;
  }

  public void setId_utiliz(BigDecimal id_utiliz) {
    this.id_utiliz = id_utiliz;
  }

  public String getNume_utiliz() {
    return nume_utiliz;
  }

  public void setNume_utiliz(String nume_utiliz) {
    this.nume_utiliz = nume_utiliz;
  }
    
      
}
