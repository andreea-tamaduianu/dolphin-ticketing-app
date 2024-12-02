package ro.ase.dolphin.obj;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author ana.maria.tamaduianu
 */
public class AutentificareObj implements Serializable{

    private  long id;
    private  String content;
    private  String cod_utilizator = null;
    private  String parola_utilizator = null;
    private  String rol_utilizator = null;
    private  String nume_utilizator = null;
    private  String utilizator_autentificat = null;
    private  String err_autentificare = null;
    
    
    public AutentificareObj() {        
    }
    
    public AutentificareObj(String cod_utiilzator) {
      this.cod_utilizator = cod_utilizator;
    }

    public AutentificareObj(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

  public String getParola_utilizator() {
    return parola_utilizator;
  }

  public void setParola_utilizator(String parola_utilizator) {
    this.parola_utilizator = parola_utilizator;
  }

  public String getRol_utilizator() {
    return rol_utilizator;
  }

  public void setRol_utilizator(String rol_utilizator) {
    this.rol_utilizator = rol_utilizator;
  }

  public String getNume_utilizator() {
    return nume_utilizator;
  }

  public void setNume_utilizator(String nume_utilizator) {
    this.nume_utilizator = nume_utilizator;
  }

  public String getUtilizator_autentificat() {
    return utilizator_autentificat;
  }

  public void setUtilizator_autentificat(String utilizator_autentificat) {
    this.utilizator_autentificat = utilizator_autentificat;
  }

  public String getErr_autentificare() {
    return err_autentificare;
  }

  public void setErr_autentificare(String err_autentificare) {
    this.err_autentificare = err_autentificare;
  }

  public String getCod_utilizator() {
    return cod_utilizator;
  }

  public void setCod_utilizator(String cod_utilizator) {
    this.cod_utilizator = cod_utilizator;
  }
    
    
    
}
