/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.obj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felix
 */
public class DetaliiTichet {
  
  private  BigDecimal rowsNoPerSet = null;
  private  BigDecimal curentSet = null;
  private  BigDecimal setsNo = null;
  private  BigDecimal rowsNo = null;
  private  String filterScript = null;
  private  String orderScript = null;
  private  String mesaj = null;
  
  List<DetaliuTichetObj> detaliiTichet = null;
  
  public DetaliiTichet() {
    
    detaliiTichet = new ArrayList<DetaliuTichetObj>() ;
    
    curentSet = BigDecimal.ONE;
    filterScript = "";
    orderScript = "";
    
  }
  
  public void addDetaliu(DetaliuTichetObj detaliu) {
    detaliiTichet.add(detaliu);
  }

  public BigDecimal getRowsNoPerSet() {
    return rowsNoPerSet;
  }

  public void setRowsNoPerSet(BigDecimal rowsNoPerSet) {
    this.rowsNoPerSet = rowsNoPerSet;
  }

  public BigDecimal getCurentSet() {
    return curentSet;
  }

  public void setCurentSet(BigDecimal curentSet) {
    this.curentSet = curentSet;
  }

  public BigDecimal getSetsNo() {
    return setsNo;
  }

  public void setSetsNo(BigDecimal setsNo) {
    this.setsNo = setsNo;
  }

  public BigDecimal getRowsNo() {
    return rowsNo;
  }

  public void setRowsNo(BigDecimal rowsNo) {
    this.rowsNo = rowsNo;
  }

  public String getFilterScript() {
    return filterScript;
  }

  public void setFilterScript(String filterScript) {
    this.filterScript = filterScript;
  }

  public String getOrderScript() {
    return orderScript;
  }

  public void setOrderScript(String orderScript) {
    this.orderScript = orderScript;
  }

  public String getMesaj() {
    return mesaj;
  }

  public void setMesaj(String mesaj) {
    this.mesaj = mesaj;
  }

  public List<DetaliuTichetObj> getDetaliiTichet() {
    return detaliiTichet;
  }

  public void setDetaliiTichet(List<DetaliuTichetObj> detaliiTichet) {
    this.detaliiTichet = detaliiTichet;
  }
  
  
  
}
