package ro.ase.dolphin.rest;

import java.math.BigDecimal;
import ro.ase.dolphin.obj.AutentificareObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.dolphin.obj.Liste_defObj;
import ro.ase.dolphin.obj.ValoriListaObj;


/**
 *
 * @author ana.maria.tamaduianu
 */
@RestController
public class RestAppController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/restLogare", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AutentificareObj autentificare(@RequestParam(value = "name", defaultValue = "World") String name,
                                          @RequestParam(value = "parola", defaultValue = "0000") String parola) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        AutentificareObj autentificareObj = new AutentificareObj(name);
        
        BigDecimal id_utilizator = null;
        BigDecimal blocat = BigDecimal.ZERO;
        String parola_utilizator = null;
        String rol_utilizator = null;
        String echipa = null;
        String den_echipa = null;
        String nume_utilizator = null;
        String prenume_utilizator = null;
        String utilizator_autentificat = null;
        String err_autentificare = null;
        
        try {
            /*
          TestObj rez1=  jdbcTemplate.query("select * from utiliz where cod_utiliz = 'ADMIN'", new ResultSetExtractor<TestObj>() {
            @Override
            public TestObj extractData(ResultSet rs) throws SQLException, DataAccessException {
               
                if (rs.next()) {
                    TestObj obj = new TestObj(0, "aa");
                   
                    obj.setId(rs.getLong("id_utiliz"));
                    obj.setContent(rs.getString("cod_utiliz"));
                  
                    return obj;

                }
                return null;
            }
        });
          System.out.println("rez1="+rez1);
          */
            
            System.out.println("-> name = " + name);
            System.out.println("-> parola = " + parola);
            
            conn = dataSource.getConnection();
            
            String sql_autentificare = " select " +
                                        " UTILIZATORI.ID_UTILIZ , " +
                                        " UTILIZATORI.COD_UTILIZ , " +
                                        " UTILIZATORI.NUME_UTILIZ , " +
                                        " UTILIZATORI.PRENUME_UTILIZ , " +
                                        " UTILIZATORI.id_rol_utiliz, " +
                                        " ROL.cod_lista ROL_utiliz, " +
                                        " ROL.den_lista den_rol, " +
                                        
                                        " UTILIZATORI.PAROLA, " +
                                        " UTILIZATORI.id_stare, " +
                                        " STARE.cod_lista stare, " +
                                        " STARE.den_lista den_stare, " +
                                        
                                        " UTILIZATORI.id_echipa, " +
                                        " ECHIPA.cod_lista ECHIPA, " +
                                        " ECHIPA.den_lista den_echipa " +
                                        
                                        " from " +
                                        " DOLPHIN.UTILIZATORI, " +
                                        " DOLPHIN.liste_def STARE, " +
                                        " DOLPHIN.liste_def ROL, " +
                                        " DOLPHIN.liste_def ECHIPA " +
                                        
                                        " where " +
                                        " UTILIZATORI.COD_UTILIZ = ? " +
                                        " and STARE.id_lista_def = UTILIZATORI.id_stare " +
                                        " and ROL.id_lista_def = UTILIZATORI.id_rol_utiliz " +
                                        " and ECHIPA.id_lista_def = UTILIZATORI.id_echipa ";            
            
            System.out.println("--> sql_autentificare =" + sql_autentificare);
            
            pstmt = conn.prepareStatement( sql_autentificare);
            pstmt.clearParameters();
            pstmt.setString(1, name);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {              
                
                id_utilizator = rs.getBigDecimal("ID_UTILIZ");
                //blocat = rs.getBigDecimal("BLOCAT");
                parola_utilizator = rs.getString("PAROLA");
                rol_utilizator = rs.getString("ROL_UTILIZ");
                nume_utilizator = rs.getString("NUME_UTILIZ");
                prenume_utilizator = rs.getString("PRENUME_UTILIZ");
                
                echipa = prenume_utilizator = rs.getString("echipa");
                den_echipa = prenume_utilizator = rs.getString("den_echipa");
                
                System.out.println("--> Autentificare utilizator ->>>>>" + name );
                System.out.println("--> id_utilizator = " + id_utilizator );
                System.out.println("--> nume_utilizator = " + nume_utilizator );
                System.out.println("--> rol_utilizator = " + rol_utilizator );
                System.out.println("--> parola_utilizator = " + parola_utilizator );
                                
                if(parola_utilizator != null && parola_utilizator.equals(parola) ) {
                  utilizator_autentificat = "DA";
                  
                  if(BigDecimal.ONE.compareTo(blocat) == 0 ) {
                    utilizator_autentificat = "NU";
                    err_autentificare = "Utilizatorul este blocat!";
                  }
                  else {
                    autentificareObj.setUtilizator_autentificat(utilizator_autentificat);
                    autentificareObj.setId(id_utilizator.longValue());
                    autentificareObj.setRol_utilizator(rol_utilizator);
                    autentificareObj.setNume_utilizator(nume_utilizator);
                  }
                  
                }
                else {
                  utilizator_autentificat = "NU";
                  err_autentificare = "Datele pentru autentificare sunt incorecte!";
                  
                  autentificareObj.setUtilizator_autentificat(utilizator_autentificat);
                  autentificareObj.setErr_autentificare(err_autentificare);
                }                
                
            }
            else {
              utilizator_autentificat = "NU";
              err_autentificare = "Nu exista utilizatorul cu acest cod!";
              
              autentificareObj.setUtilizator_autentificat(utilizator_autentificat);
              autentificareObj.setErr_autentificare(err_autentificare);
                  
            }
            
            System.out.println("--> utilizator_autentificat = " + utilizator_autentificat );
            System.out.println("--> err_autentificare = " + err_autentificare );
                            

        } catch (SQLException ex) {
            Logger.getLogger(RestAppController.class.getName()).log(Level.SEVERE, null, ex);
            
            utilizator_autentificat = "NU";
            err_autentificare = ex.getMessage();
              
            autentificareObj.setUtilizator_autentificat(utilizator_autentificat);
            autentificareObj.setErr_autentificare(err_autentificare);
              
        } finally {
            try {rs.close();} catch (Exception ex) {}
            try {pstmt.close();} catch (Exception ex) {}
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return autentificareObj;
    }
    
    @RequestMapping(value = "/restValoriLista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ValoriListaObj valoriLista( @RequestParam(value = "tip_lista") String tip_lista ) {
      
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Liste_defObj  liste_defObj  = null;
        ValoriListaObj valoriListaObj = new ValoriListaObj();
        String sql_liste = " select " +
                                  "  LISTE.id_lista, " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.id_lista_def, " +
                                  "  LISTE_DEF.cod_lista, " +
                                  "  LISTE_DEF.den_lista, " +
                                  "  LISTE_DEF.ORDER_NO " +
                                  " from " +
                                  "  DOLPHIN.LISTE, " +
                                  "  DOLPHIN.LISTE_DEF " +
                                  " where " +
                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +
                                  //"  and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' ) " +
                                  "  and LISTE.tip_lista like ? " +
                                  " order by " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.cod_lista ";
        try {
          
        
          System.out.println("-> Apel valoriLista" );
          System.out.println("-> tip_lista = " + tip_lista);          
          System.out.println("-> sql_liste = " + sql_liste);
            
          conn = dataSource.getConnection();
          pstmt = conn.prepareStatement( sql_liste );
          
          pstmt.clearParameters();
          pstmt.setString(1, tip_lista);
// restValoriLista                  
//          pstmt.setString(1, "ECHIPE");
//          pstmt.setString(2, "ROLURI");
//          pstmt.setString(3, "STARI_U");

//          pstmt.setString(1, "PRIORITATI");
//          pstmt.setString(2, "ENTITATI");
//          pstmt.setString(3, "SERVICII");
//          pstmt.setString(4, "COMPONENTE");
//          pstmt.setString(5, "STARI");
          
            liste_defObj  = new Liste_defObj();
            liste_defObj.setCod_lista("-");
            liste_defObj.setDen_lista("-");
            valoriListaObj.addValoriLista(liste_defObj);
            
          rs = pstmt.executeQuery();
          while(rs.next()) {
            
            
            liste_defObj  = new Liste_defObj();
            liste_defObj.setId_lista_def( rs.getBigDecimal("id_lista_def") );
            liste_defObj.setCod_lista(rs.getString("cod_lista") );
            liste_defObj.setDen_lista(rs.getString("den_lista") );
            liste_defObj.setOrder_no(rs.getBigDecimal("ORDER_NO") );
            
            System.out.println( "TIP_LISTA: " + rs.getString("tip_lista") + 
                     " -  ID = " + liste_defObj.getId_lista_def()+ 
                     " COD = " + liste_defObj.getCod_lista() + 
                     " DEN = " + liste_defObj.getDen_lista() + 
                     " ORDER_NO = " + liste_defObj.getOrder_no() );
                     
            valoriListaObj.addValoriLista(liste_defObj);            
          }
          
        } catch (SQLException ex) {
            Logger.getLogger(RestUtilizatorController.class.getName()).log(Level.SEVERE, null, ex);
            
              
        } finally {
            try {rs.close();} catch (Exception ex) {}
            try {pstmt.close();} catch (Exception ex) {}
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestUtilizatorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return valoriListaObj;
    }
    
}
