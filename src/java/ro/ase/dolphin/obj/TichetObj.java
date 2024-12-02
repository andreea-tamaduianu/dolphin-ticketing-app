package ro.ase.dolphin.obj;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author ana.maria.tamaduianu
 */
public class TichetObj implements Serializable {
  
  private  long id;
  private  BigDecimal nr_tichet;
  private  String numne_alarma = null;
  
  private  BigDecimal id_prioritate = null;
  private  String prioritate = null;
  private  String den_prioritate = null;
  
  private  BigDecimal id_entitate_activa = null;
  private  String entitate_activa = null;
  private  String den_entitate_activa = null;
  
  private  String descriere = null;
  
  private  BigDecimal id_serviciu = null;
  private  String serviciu = null;
  private  String den_serviciu = null;
  
  private  BigDecimal id_componenta = null;
  private  String componenta = null;
  private  String den_componenta = null;
  
  private  BigDecimal id_stare = null;
  private  String stare = null;
  private  String den_stare = null;
  
  private  String tichet_adaugat = null;
  private  String err_message = null;

  
  public TichetObj() {
    
  }
          
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public BigDecimal getNr_tichet() {
    return nr_tichet;
  }

  public void setNr_tichet(BigDecimal nr_tichet) {
    this.nr_tichet = nr_tichet;
  }

  public String getNumne_alarma() {
    return numne_alarma;
  }

  public void setNumne_alarma(String numne_alarma) {
    this.numne_alarma = numne_alarma;
  }

  public BigDecimal getId_prioritate() {
    return id_prioritate;
  }

  public void setId_prioritate(BigDecimal id_prioritate) {
    this.id_prioritate = id_prioritate;
  }

  public String getPrioritate() {
    return prioritate;
  }

  public void setPrioritate(String prioritate) {
    this.prioritate = prioritate;
  }

  public String getDen_prioritate() {
    return den_prioritate;
  }

  public void setDen_prioritate(String den_prioritate) {
    this.den_prioritate = den_prioritate;
  }

  public BigDecimal getId_entitate_activa() {
    return id_entitate_activa;
  }

  public void setId_entitate_activa(BigDecimal id_entitate_activa) {
    this.id_entitate_activa = id_entitate_activa;
  }

  public String getEntitate_activa() {
    return entitate_activa;
  }

  public void setEntitate_activa(String entitate_activa) {
    this.entitate_activa = entitate_activa;
  }

  public String getDen_entitate_activa() {
    return den_entitate_activa;
  }

  public void setDen_entitate_activa(String den_entitate_activa) {
    this.den_entitate_activa = den_entitate_activa;
  }

  public String getDescriere() {
    return descriere;
  }

  public void setDescriere(String descriere) {
    this.descriere = descriere;
  }

  public BigDecimal getId_serviciu() {
    return id_serviciu;
  }

  public void setId_serviciu(BigDecimal id_serviciu) {
    this.id_serviciu = id_serviciu;
  }

  public String getServiciu() {
    return serviciu;
  }

  public void setServiciu(String serviciu) {
    this.serviciu = serviciu;
  }

  public String getDen_serviciu() {
    return den_serviciu;
  }

  public void setDen_serviciu(String den_serviciu) {
    this.den_serviciu = den_serviciu;
  }

  public BigDecimal getId_componenta() {
    return id_componenta;
  }

  public void setId_componenta(BigDecimal id_componenta) {
    this.id_componenta = id_componenta;
  }

  public String getComponenta() {
    return componenta;
  }

  public void setComponenta(String componenta) {
    this.componenta = componenta;
  }

  public String getDen_componenta() {
    return den_componenta;
  }

  public void setDen_componenta(String den_componenta) {
    this.den_componenta = den_componenta;
  }

  public BigDecimal getId_stare() {
    return id_stare;
  }

  public void setId_stare(BigDecimal id_stare) {
    this.id_stare = id_stare;
  }

  public String getStare() {
    return stare;
  }

  public void setStare(String stare) {
    this.stare = stare;
  }

  public String getDen_stare() {
    return den_stare;
  }

  public void setDen_stare(String den_stare) {
    this.den_stare = den_stare;
  }

  public String getTichet_adaugat() {
    return tichet_adaugat;
  }

  public void setTichet_adaugat(String tichet_adaugat) {
    this.tichet_adaugat = tichet_adaugat;
  }

  public String getErr_message() {
    return err_message;
  }

  public void setErr_message(String err_message) {
    this.err_message = err_message;
  }
  
 
  
  
    
}
