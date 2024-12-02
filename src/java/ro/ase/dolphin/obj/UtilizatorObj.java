/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.obj;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Ana
 */
public class UtilizatorObj implements Serializable{

  private  long id_utiliz;
  private  String cod_utiliz;
  private  String nume_utiliz = null;
  private  String prenume_utiliz = null;
  
  private  String parola = null;
  
  private  BigDecimal id_rol_utiliz = null;  
  private  String rol_utiliz = null;  
  private  String den_rol_utiliz = null;  
  
  private  BigDecimal id_stare;
  private  String stare;
  private  String den_stare;
  
  private  BigDecimal id_echipa=null;
  private  String echipa=null;
  private  String den_echipa=null;
  
  private  String email=null;
  
  private  String utilizator_adaugat = null;
  private  String err_message = null;
  
  
  
  
    public UtilizatorObj() {
        
    }

    public long getId_utiliz() {
        return id_utiliz;
    }

    public void setId_utiliz(long id_utiliz) {
        this.id_utiliz = id_utiliz;
    }

    public String getCod_utiliz() {
        return cod_utiliz;
    }

    public void setCod_utiliz(String cod_utiliz) {
        this.cod_utiliz = cod_utiliz;
    }

    public String getNumne_utiliz() {
        return nume_utiliz;
    }

    public void setNumne_utiliz(String numne_utiliz) {
        this.nume_utiliz = numne_utiliz;
    }

    

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    

    public BigDecimal getId_echipa() {
        return id_echipa;
    }

    public void setId_echipa(BigDecimal id_echipa) {
        this.id_echipa = id_echipa;
    }

    

    public String getUtilizator_adaugat() {
        return utilizator_adaugat;
    }

    public void setUtilizator_adaugat(String utilizator_adaugat) {
        this.utilizator_adaugat = utilizator_adaugat;
    }

    public String getNume_utiliz() {
        return nume_utiliz;
    }

    public void setNume_utiliz(String nume_utiliz) {
        this.nume_utiliz = nume_utiliz;
    }
    
    
  
  public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

  public String getPrenume_utiliz() {
    return prenume_utiliz;
  }

  public void setPrenume_utiliz(String prenume_utiliz) {
    this.prenume_utiliz = prenume_utiliz;
  }

  public BigDecimal getId_rol_utiliz() {
    return id_rol_utiliz;
  }

  public void setId_rol_utiliz(BigDecimal id_rol_utiliz) {
    this.id_rol_utiliz = id_rol_utiliz;
  }

  public String getRol_utiliz() {
    return rol_utiliz;
  }

  public void setRol_utiliz(String rol_utiliz) {
    this.rol_utiliz = rol_utiliz;
  }

  public String getDen_rol_utiliz() {
    return den_rol_utiliz;
  }

  public void setDen_rol_utiliz(String den_rol_utiliz) {
    this.den_rol_utiliz = den_rol_utiliz;
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

  public String getEchipa() {
    return echipa;
  }

  public void setEchipa(String echipa) {
    this.echipa = echipa;
  }

  public String getDen_echipa() {
    return den_echipa;
  }

  public void setDen_echipa(String den_echipa) {
    this.den_echipa = den_echipa;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
    
 
}
