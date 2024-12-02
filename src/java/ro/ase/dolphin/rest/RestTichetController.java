package ro.ase.dolphin.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import ro.ase.dolphin.obj.TichetObj;
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
import ro.ase.dolphin.obj.DetaliiTichet;
import ro.ase.dolphin.obj.DetaliuTichetObj;
import ro.ase.dolphin.obj.ListaTichete;
import ro.ase.dolphin.obj.SchimbareStareObj;


/**
 *
 * @author ana.maria.tamaduianu
 */
@RestController
public class RestTichetController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/restAdaugaTichet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public TichetObj adaugaTichet(@RequestParam(value = "nume_alarma") String nume_alarma,
                                  @RequestParam(value = "prioritate") String prioritate,
                                  @RequestParam(value = "serviciu") String serviciu,
                                  @RequestParam(value = "entitate_activa") String entitate_activa,
                                  @RequestParam(value = "componenta") String componenta,
                                  @RequestParam(value = "descriere") String descriere) {
      
                       
        Connection conn = null;
        PreparedStatement pstmt_insertTichet = null;
        PreparedStatement pstmt_validareLista = null;
        PreparedStatement pstmt_generarePK = null;
        ResultSet rs = null;
        
        BigDecimal id_prioritate = null;
        BigDecimal id_serviciu = null;
        BigDecimal id_entitate_activa = null;
        BigDecimal id_componenta = null;
        BigDecimal id_tichet = null;
        BigDecimal nr_tichet = null;
        BigDecimal id_stare = null;
                                             
        String mesaj_ERR = "";
        boolean isErrors = false;
        
        TichetObj tichetObj = new TichetObj();
        
        String sqlValidareLista = " select " +
                                  "  LISTE.id_lista, " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.id_lista_def, " +
                                  "  LISTE_DEF.cod_lista, " +
                                  "  LISTE_DEF.den_lista " +
                                  " from " +
                                  "  DOLPHIN.LISTE, " +
                                  "  DOLPHIN.LISTE_DEF " +
                                  " where " +
                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +
                                  //"  and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' ) " +
                                  "  and LISTE.tip_lista like ? " +
                                  "  and LISTE_DEF.cod_lista like ? " ;
        
        
        String sqlInsertTichet = " insert into DOLPHIN.TICHETE (" +
                                  "  ID_TICHET, " +
                                  "  NR_TICHET, " +
                                  "  NUME_ALARMA, " +
                                  "  ID_PRIORITATE, " +
                                  "  ID_ENTITATE_ACTIVA, " +
                                  "  DESCRIERE, " +
                                  "  ID_SERVICIU, " +
                                  "  ID_COMPONENTA, " +
                                  "  ID_STARE ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sqlPK_Tichet = " select DOLPHIN.SEQ_TICHET.nextval PK_VAL from dual ";
        
        try {
          
            System.out.println("-> ApelrestAdaugaTichet " );
            System.out.println("-> nume_alarma = " + nume_alarma);
            System.out.println("-> prioritate = " + prioritate);
            System.out.println("-> serviciu = " + serviciu);
            System.out.println("-> entitate_activa = " + entitate_activa);
            System.out.println("-> componenta = " + componenta);
            System.out.println("-> descriere = " + descriere);            
            
            System.out.println("-> sqlInsertTichet = " + sqlInsertTichet);
            System.out.println("-> sqlValidareLista = " + sqlValidareLista);
            System.out.println("-> sqlPK_Tichet = " + sqlPK_Tichet);
            
            conn = dataSource.getConnection();
            
            pstmt_generarePK = conn.prepareStatement(sqlPK_Tichet);
            pstmt_insertTichet = conn.prepareStatement(sqlInsertTichet);
            pstmt_validareLista = conn.prepareStatement(sqlValidareLista);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "PRIORITATI");
            pstmt_validareLista.setString(2, prioritate);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_prioritate = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de prioritate "+prioritate+" nu este definit in lista PRIORITATI!" ;
            }
            System.out.println(" --> id_prioritate = " + id_prioritate);
            
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "ENTITATI");
            pstmt_validareLista.setString(2, entitate_activa);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_entitate_activa = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de entitate activa "+entitate_activa+" nu este definit in lista ENTITATI!" ;
            }
            System.out.println(" --> id_entitate_activa = " + id_entitate_activa);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "COMPONENTE");
            pstmt_validareLista.setString(2, componenta);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_componenta = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de componenta "+componenta+" nu este definit in lista COMPONENTE!" ;
            }
            System.out.println(" --> id_componenta = " + id_componenta);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "SERVICII");
            pstmt_validareLista.setString(2, serviciu);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();            
            if(rs.next()) {
              id_serviciu = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de serviciu "+serviciu+" nu este definit in lista SERVICII!" ;
            }
            System.out.println(" --> id_serviciu = " + id_serviciu);
            
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "STARI");
            pstmt_validareLista.setString(2, "DESCHIS");
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();            
            if(rs.next()) {
              id_stare = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de stare DESCHIS nu este definit in lista STARI!" ;
            }
            System.out.println(" --> id_serviciu = " + id_serviciu);
            
            
            if(isErrors) {
              tichetObj.setTichet_adaugat("NU"); 
              tichetObj.setErr_message( mesaj_ERR );
              System.out.println(" --> mesaj_ERR  = " + mesaj_ERR);
              
            }
            else {
              
              try {rs.close();} catch (Exception ex) {}
              rs = pstmt_generarePK.executeQuery(); 
              if(rs.next()) {
                id_tichet = rs.getBigDecimal("PK_VAL");
                nr_tichet = id_tichet;
              }        
              System.out.println(" --> id_tichet = " + id_tichet);
              
                      
              tichetObj.setId(id_tichet.longValue());
              tichetObj.setNr_tichet(nr_tichet );
              
              pstmt_insertTichet.clearParameters(); 
              pstmt_insertTichet.setBigDecimal(1, id_tichet);
              pstmt_insertTichet.setBigDecimal(2, nr_tichet);
              pstmt_insertTichet.setString(3, nume_alarma);
              pstmt_insertTichet.setBigDecimal(4, id_prioritate);
              pstmt_insertTichet.setBigDecimal(5, id_entitate_activa);
              pstmt_insertTichet.setString(6, descriere);
              pstmt_insertTichet.setBigDecimal(7, id_serviciu);
              pstmt_insertTichet.setBigDecimal(8, id_componenta);
              pstmt_insertTichet.setBigDecimal(9, id_stare);
              
              int insert_row = pstmt_insertTichet.executeUpdate();
              
              System.out.println(" --> insert_row = " + insert_row);
              tichetObj.setTichet_adaugat("DA"); 
              tichetObj.setErr_message( null );
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) {}
            try {pstmt_insertTichet.close();} catch (Exception ex) {}
            try {pstmt_validareLista.close();} catch (Exception ex) {}
            try {pstmt_generarePK.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tichetObj;
    }
    
    @RequestMapping(value = "/restSeteazaStareTichet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public SchimbareStareObj setareStareTichet( @RequestParam(value = "stare_tichet") String stare_tichet,
                                                @RequestParam(value = "stare_tichet_ant") String stare_tichet_ant,
                                                @RequestParam(value = "id_tichet") BigDecimal id_tichet,
                                                @RequestParam(value = "nr_tichet") BigDecimal nr_tichet,
                                                @RequestParam(value = "id_utiliz") BigDecimal id_utiliz,
                                                @RequestParam(value = "tip_oper") String tip_oper,
                                                @RequestParam(value = "overtime") BigDecimal overtime,
                                                @RequestParam(value = "descriere") String descriere
                                                ) {
  
        Connection conn = null;
        SchimbareStareObj schimbareStareObj = new SchimbareStareObj();        
        
        PreparedStatement pstmt_updateTichet = null;
        PreparedStatement pstmt_selectTichet = null;
        PreparedStatement pstmt_insertLog = null;
        PreparedStatement pstmt_validareLista = null;
        
        ResultSet rs = null;
        ResultSet rs_validare = null;
        
        boolean isErrors = false;                                                
        String mesaj_ERR = "";
        BigDecimal id_stare_tichet = null;
        BigDecimal id_stare_ant = null;
        BigDecimal id_stare = null;
        BigDecimal id_tip_oper = null;
        
        System.out.println("-> consultareTichete " );
        
        System.out.println("-> stare_tichet = " + stare_tichet);
        System.out.println("-> stare_tichet_ant = " + stare_tichet_ant);
        System.out.println("-> id_tichet = " + id_tichet);
        System.out.println("-> nr_tichet = " + nr_tichet);
        System.out.println("-> id_utiliz = " + id_utiliz);
        System.out.println("-> tip_oper = " + tip_oper);
        
        String sqlValidareLista = " select " +
                                  "  LISTE.id_lista, " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.id_lista_def, " +
                                  "  LISTE_DEF.cod_lista, " +
                                  "  LISTE_DEF.den_lista " +
                                  " from " +
                                  "  DOLPHIN.LISTE, " +
                                  "  DOLPHIN.LISTE_DEF " +
                                  " where " +
                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +                                  
                                  "  and LISTE.tip_lista like ? " +
                                  "  and LISTE_DEF.cod_lista like ? " ;
        
        String sqlValidareTichet = " select " +
                                   "  TICHETE.ID_TICHET, "  + 
                                   "  TICHETE.NR_TICHET, " + 
                                   "  TICHETE.ID_STARE " + 
                                   
                                   " FROM " +                
                                   "  DOLPHIN.TICHETE  " + 
                                   //"  DOLPHIN.LISTE_DEF STARE_TICHET " + 
                                   " WHERE " +
                                   "  TICHETE.ID_TICHET = ? "  + 
                                   //"  AND STARE_TICHET.ID_LISTA_DEF = TICHETE.ID_STARE " + 
                                   // Blocare inregistrare pana la Update 
                                   " FOR UPDATE OF   " +
                                   "  TICHETE.ID_TICHET  " +
                                   " NOWAIT  ";
        
        String sqlUpdateTichet = " update  " +
                                 "  DOLPHIN.TICHETE  " + 
                                 " set ID_STARE = ? " +  
                                 " WHERE " +
                                 "  TICHETE.ID_TICHET = ? "  + 
                                 "";                
        
        String sqlInsertLog = " insert into " +
                              "  DOLPHIN.TICHETE_LOG ( " +                                               
                              "  ID_TICHET_LOG, " + //DOLPHIN.SEQ_LOG_TICHETE.NEXTVAL 
                              "  ID_TICHET, " + //?
                              "  ID_UTILIZ, " + //?
                              "  ID_TIP_OPER, " + //?
                              "  ID_STARE, " + //?
                              "  DATA_START, " + //SYSDATE                
                              "  DATA_END, " + //null
                              "  OVERTIME, " + //?
                              "  DESCRIERE " + //?
                              " ) VALUES " +
                              " ( DOLPHIN.SEQ_LOG_TICHETE.NEXTVAL, ?, ?, ?, ?, SYSDATE, null, ?, ? ) " +
                              "";
        try {
            
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
                    
            System.out.println(" --> sqlValidareTichet = " + sqlValidareTichet);
            pstmt_selectTichet = conn.prepareStatement(sqlValidareTichet);
            try {rs.close();} catch (Exception ex) {}
            pstmt_selectTichet.clearParameters(); 
            pstmt_selectTichet.setBigDecimal(1, id_tichet);
            rs = pstmt_selectTichet.executeQuery(); 
            if(rs.next()) {
              id_stare_tichet = rs.getBigDecimal("id_stare");
              System.out.println(" --> id_stare_tichet = " + id_stare_tichet);
              
              System.out.println(" --> sqlValidareLista = " + sqlValidareLista);
              pstmt_validareLista = conn.prepareStatement(sqlValidareLista);
              
              pstmt_validareLista.clearParameters();
              pstmt_validareLista.setString(1, "STARI");
              pstmt_validareLista.setString(2, stare_tichet_ant);
              
              try {rs_validare.close();} catch (Exception ex) {}
              rs_validare = pstmt_validareLista.executeQuery();            
              if(rs_validare.next()) {
                id_stare_ant = rs_validare.getBigDecimal("id_lista_def");
                System.out.println(" --> id_stare_ant = " + id_stare_ant);
                
                if( id_stare_tichet.compareTo( id_stare_ant) != 0  ) {
                  isErrors = true;
                  mesaj_ERR += "Tichetul cu numarul "+nr_tichet+" nu are starea " + rs_validare.getString("den_lista") +"!"; ;
                  System.out.println(" --> ERR = " + mesaj_ERR);
                }
                
              }
              else {
                isErrors = true;
                mesaj_ERR += "Codul de stare "+stare_tichet_ant+" nu este definit in lista STARI!" ;
                System.out.println(" --> ERR = " + mesaj_ERR);
              }
              
              if(!isErrors)  {
               
                pstmt_validareLista.clearParameters();
                pstmt_validareLista.setString(1, "STARI");
                pstmt_validareLista.setString(2, stare_tichet);

                try {rs_validare.close();} catch (Exception ex) {}
                rs_validare = pstmt_validareLista.executeQuery();            
                if(rs_validare.next()) {
                  id_stare = rs_validare.getBigDecimal("id_lista_def");
                  System.out.println(" --> id_stare = " + id_stare);
                
                  System.out.println(" --> sqlUpdateTichet = " + sqlUpdateTichet);
                  pstmt_updateTichet = conn.prepareStatement(sqlUpdateTichet);
                  pstmt_updateTichet.clearParameters();
                  pstmt_updateTichet.setBigDecimal(1, id_stare);
                  pstmt_updateTichet.setBigDecimal(2, id_tichet);
                  int updateTichet =  pstmt_updateTichet.executeUpdate();
                  System.out.println(" updateTichet = " + updateTichet );
                  
                  //validare tip_log
                  pstmt_validareLista.clearParameters();
                  pstmt_validareLista.setString(1, "TIPOPER");
                  pstmt_validareLista.setString(2, tip_oper);
                  try {rs_validare.close();} catch (Exception ex) {}
                  rs_validare = pstmt_validareLista.executeQuery();            
                  if(rs_validare.next()) {
                    id_tip_oper = rs_validare.getBigDecimal("id_lista_def");
                    System.out.println(" --> id_tip_oper = " + id_tip_oper);
                  }
                  
                  System.out.println(" --> sqlInsertLog = " + sqlInsertLog);
                  pstmt_insertLog = conn.prepareStatement(sqlInsertLog);
                  //Insert inregistrare in Log pentru schimbarea starii 
                  pstmt_insertLog.clearParameters(); 
                  pstmt_insertLog.setBigDecimal(1, id_tichet);
                  pstmt_insertLog.setBigDecimal(2, id_utiliz );
                  pstmt_insertLog.setBigDecimal(3, id_tip_oper );
                  pstmt_insertLog.setBigDecimal(4, id_stare );
                  //pstmt_insertLog.setBigDecimal(4, id_stare_ant );
                  pstmt_insertLog.setBigDecimal(5, overtime );
                  pstmt_insertLog.setString(6, descriere );
                  int insertLog =  pstmt_insertLog.executeUpdate();
                  System.out.println(" insertLog = " + insertLog );
                  
                }
                else {
                  isErrors = true;
                  mesaj_ERR += "Codul de stare "+stare_tichet+" nu este definit in lista STARI!" ;
                  System.out.println(" --> ERR = " + mesaj_ERR);
                }
              }
              
            }
            else {
              isErrors = true;
              mesaj_ERR += "Nu exista tichetul cu numarul "+ nr_tichet+"!" ;
              System.out.println(" --> ERR = " + mesaj_ERR);
              
            }
            
            conn.commit();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
            try{conn.rollback();} catch (SQLException ex2) {}
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            isErrors = true;
            mesaj_ERR += ex.getMessage();
            
        } finally {
            try {rs.close();} catch (Exception ex) { }
            try {rs_validare.close();} catch (Exception ex) { }
            
            try {pstmt_insertLog.close();} catch (Exception ex) {}
            try {pstmt_selectTichet.close();} catch (Exception ex) {}
            try {pstmt_updateTichet.close();} catch (Exception ex) {}
            try {pstmt_validareLista.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println(" --> mesaj_ERR  = " + mesaj_ERR);
        
        if(isErrors ) {
          schimbareStareObj.setMesaj(mesaj_ERR);          
          schimbareStareObj.setStare_modificata(false);
        }
        else {
          schimbareStareObj.setStare_modificata(true);
        }
        
        return schimbareStareObj;
    }
    
    @RequestMapping(value = "/restAdaugaDetaliu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public DetaliuTichetObj adaugaDetaliuTichet(@RequestParam(value = "id_tichet") BigDecimal id_tichet,
                                                @RequestParam(value = "nr_tichet") BigDecimal nr_tichet,      
                                                @RequestParam(value = "id_utiliz") BigDecimal id_utiliz,
                                                @RequestParam(value = "tip_detaliu") String tip_detaliu,
                                                @RequestParam(value = "descriere_detaliu") String descriere_detaliu
                                             ) {
      
  
       Connection conn = null;
       PreparedStatement pstmt_insertDetaliiTichet = null;
       PreparedStatement pstmt_ValidareLista = null;
       ResultSet rs = null;
       DetaliuTichetObj detaliuTichetObj = new DetaliuTichetObj();
        
       String sqlValidareLista = " select " +
                                  "  LISTE.id_lista, " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.id_lista_def, " +
                                  "  LISTE_DEF.cod_lista, " +
                                  "  LISTE_DEF.den_lista " +
                                  " from " +
                                  "  DOLPHIN.LISTE, " +
                                  "  DOLPHIN.LISTE_DEF " +
                                  " where " +
                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +
                                  //"  and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' ) " +
                                  "  and LISTE.tip_lista like ? " +
                                  "  and LISTE_DEF.cod_lista like ? " ;
       String sqlInsertDetaliuTichet = " insert into " +
                              "  DOLPHIN.TICHETE_DET ( " +                                               
                              "  ID_TICHET_DET, " + //DOLPHIN.SEQ_TICHETE_DET.NEXTVAL 
                              "  ID_TICHET, " + //?
                              "  ID_TIP_DET, " + //?
                              "  DESCRIERE, " + //?
                              "  ID_UTILIZ, " + //?
                              "  DATA_ACT " + //?
                              " ( DOLPHIN.SEQ_TICHETE_DET.NEXTVAL, ?, ?, ?, ?, SYSDATE ) " +
                              "";
               
       BigDecimal id_tip_detaliu = null;    
       try {
          
            System.out.println("-> AdaugaDetaliuTichet " );
            System.out.println("-> id_tichet = " + id_tichet);          
            System.out.println("-> nr_tichet = " + nr_tichet);          
            System.out.println("-> id_utiliz = " + id_utiliz);          
            System.out.println("-> tip_detaliu = " + tip_detaliu);          
            System.out.println("-> descriere_detaliu = " + descriere_detaliu);          
                    
            System.out.println("-> sqlInsertDetaliuTichet = " + sqlInsertDetaliuTichet);
            System.out.println("-> sqlValidareLista = " + sqlValidareLista);
            
            conn = dataSource.getConnection();
            pstmt_ValidareLista = conn.prepareStatement( sqlValidareLista );
            pstmt_ValidareLista.setString(1, "TIPDET");
            pstmt_ValidareLista.setString(2, tip_detaliu);
            rs = pstmt_ValidareLista.executeQuery();
            if(rs.next()) {
              id_tip_detaliu = rs.getBigDecimal("id_lista_def");
              
            }
            
            pstmt_insertDetaliiTichet = conn.prepareStatement( sqlInsertDetaliuTichet );
            pstmt_insertDetaliiTichet.setBigDecimal(1, id_tichet);
            pstmt_insertDetaliiTichet.setBigDecimal(2, id_tip_detaliu);
            pstmt_insertDetaliiTichet.setString(3, descriere_detaliu);
            pstmt_insertDetaliiTichet.setBigDecimal(4, id_utiliz);
            
            int insert_detaliu = pstmt_insertDetaliiTichet.executeUpdate();
            
        
          } catch (SQLException ex) {
              ex.printStackTrace();

              Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);

          } finally {
              try {rs.close();} catch (Exception ex) {}
              try {pstmt_insertDetaliiTichet.close();} catch (Exception ex) {}
              try {pstmt_ValidareLista.close();} catch (Exception ex) {}
             

              try {conn.close();} catch (Exception ex) {
                  Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          return detaliuTichetObj;
      }
        
                                               
    @RequestMapping(value = "/restDetaliiTichet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public DetaliiTichet detaliiTichet(@RequestParam(value = "id_tichet") BigDecimal id_tichet,
                                       @RequestParam(value = "filterScript") String filterScript,
                                       @RequestParam(value = "orderScript") String orderScript,
                                       @RequestParam(value = "curentSet") BigDecimal curentSet,
                                       @RequestParam(value = "rowsNoPerSet") BigDecimal rowsNoPerSet
                                      ) {
      
  
        Connection conn = null;
        PreparedStatement pstmt_detaliiTichet = null;
        ResultSet rs = null;
        DetaliiTichet detaliiTichet = new DetaliiTichet();
        DetaliuTichetObj  detaliuTichetObj = null;
        
        String sqlSelectDetaliiTichet = " select  " +
                                        "  TICHETE_DET.ID_TICHET, " +
                                        "  TICHETE.nr_tichet, " +
                                        "  TICHETE_DET.ID_TICHET_DET, " +
                                        "  TICHETE_DET.ID_TIP_DET, " +
                                        "  LISTE_DEF.cod_lista TIP_DET, " +
                                        "  LISTE_DEF.den_lista  den_TIP_DET, " +
                                        "  TICHETE_DET.DESCRIERE, " +
                                        "  to_char(TICHETE_DET.data_act,'yyyy-MM-dd') data_act, " +
                                        "  to_char(TICHETE_DET.data_act,'HH:mm') ora_act, " +
                                        "  TICHETE_DET.id_utiliz, " +
                                        "  utilizatori.nume_utiliz " +
                                        " from  " +
                                        "  DOLPHIN.TICHETE,     " +
                                        "  DOLPHIN.TICHETE_DET, " +
                                        "  DOLPHIN.LISTE_DEF, " +
                                        "  DOLPHIN.utilizatori  " +
                                        " where " +
                                        "  TICHETE_DET.id_tichet = ?  " +
                                        "  and TICHETE_DET.id_tichet = TICHETE.id_tichet  " +
                                        "  and LISTE_DEF.id_lista_def = TICHETE_DET.id_tip_det  " +
                                        "  and utilizatori.id_utiliz = TICHETE_DET.id_utiliz " +
                                        " order by " +
                                        "  TICHETE_DET.data_act " +
                                        "";
                
        try {            
            System.out.println("-> sqlSelectDetaliiTichet = " + sqlSelectDetaliiTichet);
            conn = dataSource.getConnection();
            
            pstmt_detaliiTichet  = conn.prepareStatement(sqlSelectDetaliiTichet);
            pstmt_detaliiTichet.setBigDecimal(1, id_tichet);
            rs = pstmt_detaliiTichet.executeQuery();
            while(rs.next()) {
              detaliuTichetObj = new DetaliuTichetObj();
              
              detaliuTichetObj.setId_tichet(rs.getBigDecimal("id_tichet"));
              detaliuTichetObj.setNr_tichet(rs.getBigDecimal("nr_tichet"));
              detaliuTichetObj.setId_tichet_det(rs.getBigDecimal("id_tichet_det"));
              detaliuTichetObj.setId_tip_det(rs.getBigDecimal("id_tip_det"));
              detaliuTichetObj.setId_utiliz(rs.getBigDecimal("id_utiliz"));
              
              detaliuTichetObj.setData_act(rs.getString("Data_act"));
              detaliuTichetObj.setOra_act(rs.getString("Ora_act"));
              detaliuTichetObj.setDescriere(rs.getString("Descriere"));
              detaliuTichetObj.setTip_det(rs.getString("Tip_det"));
              detaliuTichetObj.setDen_tip_det(rs.getString("Den_tip_det"));
              detaliuTichetObj.setNume_utiliz(rs.getString("Nume_utiliz"));
                      
              detaliiTichet.addDetaliu( detaliuTichetObj );
              System.out.println("-> detaliuTichetObj.getId_tichet_det = " + detaliuTichetObj.getId_tichet_det());
            } 
        
        } catch (Exception ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) { }
            
            try {pstmt_detaliiTichet.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return detaliiTichet;
    }
        
    
    @RequestMapping(value = "/restConsultareTichete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ListaTichete consultareTichete(@RequestParam(value = "stare_tichet") String stare_tichet,
                                          @RequestParam(value = "filterScript") String filterScript,
                                          @RequestParam(value = "orderScript") String orderScript,
                                          @RequestParam(value = "curentSet") BigDecimal curentSet,
                                          @RequestParam(value = "rowsNoPerSet") BigDecimal rowsNoPerSet
                                          ) {
      
  
        Connection conn = null;
        PreparedStatement pstmt_countTichete = null;
        PreparedStatement pstmt_selectTichete = null;
        PreparedStatement pstmt_validareLista = null;
        
        ResultSet rs = null;
        
        BigDecimal id_stare = null;
        BigDecimal rowsNo = BigDecimal.ZERO;
        BigDecimal setsNo = BigDecimal.ZERO;
        
                                             
        String mesaj_ERR = "";
        boolean isErrors = false;
        
        BigDecimal firstRecord = BigDecimal.ZERO;
        BigDecimal lastRecord = BigDecimal.ZERO;
                
        firstRecord = curentSet.subtract(BigDecimal.ONE).multiply(rowsNoPerSet).add( BigDecimal.ONE );
        lastRecord = curentSet.multiply(rowsNoPerSet);
        
        System.out.println("-> consultareTichete " );
        
        System.out.println("-> stare_tichet = " + stare_tichet);
        System.out.println("-> 1. filterScript = " + filterScript);
        System.out.println("-> 1. orderScript = " + orderScript);
        
        System.out.println("-> curentSet = " + curentSet);
        System.out.println("-> rowsNoPerSet = " + rowsNoPerSet);
        
        System.out.println("-> firstRecord = " + firstRecord);
        System.out.println("-> lastRecord = " + lastRecord);
        
        if ( orderScript == null || orderScript.length()  == 0 ) { 
          orderScript = " TICHETE.NR_TICHET ";
        }
        if(filterScript == null) {
          filterScript = "";
        }
        System.out.println("-> 2. filterScript = " + filterScript);
        System.out.println("-> 2. orderScript = " + orderScript);
        
        String sqlValidareLista = " select " +
                                  "  LISTE.id_lista, " +
                                  "  LISTE.tip_lista, " +
                                  "  LISTE_DEF.id_lista_def, " +
                                  "  LISTE_DEF.cod_lista, " +
                                  "  LISTE_DEF.den_lista " +
                                  " from " +
                                  "  DOLPHIN.LISTE, " +
                                  "  DOLPHIN.LISTE_DEF " +
                                  " where " +
                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +
                                  //"  and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' ) " +
                                  "  and LISTE.tip_lista like ? " +
                                  "  and LISTE_DEF.cod_lista like ? " ;
        
        String sqlCountTichete = " SELECT  " +
                                 "  COUNT(TICHETE.ID_TICHET) ROWS_NO " +
                                 " FROM " +
                                 "  DOLPHIN.TICHETE " + 
                                 " WHERE " +
                                 "  TICHETE.ID_STARE = ? "  +                                   
                                 filterScript  + 
                                 "";                
                
        String sqlSelectTichete = " SELECT  " +
                                  "  TICHETE.ID_TICHET, " +
                                  "  TICHETE.NR_TICHET, " +                
                                  "  TICHETE.NUME_ALARMA, " +
                
                                  "  TICHETE.ID_PRIORITATE, " +                
                                  "  PRIORITATE.COD_LISTA  PRIORITATE, " + 
                                  "  PRIORITATE.DEN_LISTA  DEN_PRIORITATE, " + 
                
                                  "  TICHETE.ID_ENTITATE_ACTIVA, " +                
                                  "  ENTITATE.COD_LISTA  ENTITATE_ACTIVA, " + 
                                  "  ENTITATE.DEN_LISTA  DEN_ENTITATE_ACTIVA, " + 
                
                                  "  TICHETE.DESCRIERE, " +
        
                                  "  TICHETE.ID_SERVICIU, " +
                                  "  SERVICIU.COD_LISTA  SERVICIU, " + 
                                  "  SERVICIU.DEN_LISTA  DEN_SERVICIU, " + 
                
                                  "  TICHETE.ID_COMPONENTA, " +
                                  "  COMPONENTA.COD_LISTA  COMPONENTA, " + 
                                  "  COMPONENTA.DEN_LISTA  DEN_COMPONENTA, " + 
                
                                  "  TICHETE.ID_STARE, " +
                                  "  STARE.COD_LISTA  STARE, " + 
                                  "  STARE.DEN_LISTA  DEN_STARE " + 
                
                                  " FROM " +
                
                                  "  DOLPHIN.TICHETE,  " + 
                                  "  DOLPHIN.LISTE_DEF STARE,  " + 
                                  "  DOLPHIN.LISTE_DEF PRIORITATE,  " + 
                                  "  DOLPHIN.LISTE_DEF COMPONENTA,  " + 
                                  "  DOLPHIN.LISTE_DEF SERVICIU,  " + 
                                  "  DOLPHIN.LISTE_DEF ENTITATE  " + 
                
                                  " WHERE " +
                                  "  TICHETE.ID_STARE = ? "  + 
                                  "  AND SERVICIU.ID_LISTA_DEF = TICHETE.ID_SERVICIU " + 
                                  "  AND PRIORITATE.ID_LISTA_DEF = TICHETE.ID_PRIORITATE " + 
                                  "  AND STARE.ID_LISTA_DEF = TICHETE.ID_STARE " + 
                                  "  AND COMPONENTA.ID_LISTA_DEF = TICHETE.ID_COMPONENTA " + 
                                  "  AND ENTITATE.ID_LISTA_DEF = TICHETE.ID_ENTITATE_ACTIVA " + 
                
                                  "  AND ROWNUM >= " + firstRecord + 
                                  "  AND ROWNUM <= " + lastRecord + 
                                  
                                  filterScript  + 
                                  " order by " +
                                  orderScript +                                                                     
                                  " ";
        
        
        ListaTichete listaTichete = new ListaTichete();
        TichetObj tichetObj = null;
        
        
        try {
            
            System.out.println("-> sqlInsertTichet = " + sqlSelectTichete);
            System.out.println("-> sqlValidareLista = " + sqlValidareLista);
            
            conn = dataSource.getConnection();
            
            pstmt_countTichete = conn.prepareStatement(sqlCountTichete);
            pstmt_selectTichete = conn.prepareStatement(sqlSelectTichete);
            pstmt_validareLista = conn.prepareStatement(sqlValidareLista);
            
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "STARI");
            pstmt_validareLista.setString(2, stare_tichet);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();            
            if(rs.next()) {
              id_stare = rs.getBigDecimal("id_lista_def");                                          
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de stare "+stare_tichet+" nu este definit in lista STARI!" ;
            }
            System.out.println(" --> id_stare = " + id_stare);
            
            
            if(id_stare == null) {
              isErrors = true;
              mesaj_ERR += "Codul de stare "+stare_tichet+" nu este definit in lista STARI!" ;
              listaTichete.setMesaj(mesaj_ERR);
              System.out.println(" --> mesaj_ERR  = " + mesaj_ERR);
            }
            else {
              
              
              try {rs.close();} catch (Exception ex) {}
              pstmt_countTichete.clearParameters(); 
              
              pstmt_countTichete.setBigDecimal(1, id_stare);
              rs = pstmt_countTichete.executeQuery();               
              if(rs.next()) {
                 rowsNo =  rs.getBigDecimal("ROWS_NO");                 
              }
              System.out.println(" --> rowsNo = " + rowsNo);
              listaTichete.setRowsNo( rowsNo );
              
              
              if( BigDecimal.ZERO.compareTo( rowsNo ) < 0 ) {

                setsNo = rowsNo.divide(rowsNoPerSet, 0, RoundingMode.UP );
                listaTichete.setSetsNo(setsNo);
                
                try {rs.close();} catch (Exception ex) {}
                pstmt_selectTichete.clearParameters(); 

                pstmt_selectTichete.setBigDecimal(1, id_stare);
                rs = pstmt_selectTichete.executeQuery(); 

                System.out.println(" --> inainte de rs.next() ");
                
                while(rs.next()) {
                  System.out.println(" --> in interior la rs.next() ");
                  
                  tichetObj = new TichetObj();
                
                  tichetObj.setId( rs.getLong("ID_TICHET") );
                  tichetObj.setNr_tichet(rs.getBigDecimal("NR_TICHET") );
                  tichetObj.setNumne_alarma(rs.getString("NUME_ALARMA") );
                  
                  tichetObj.setId_prioritate(rs.getBigDecimal("ID_PRIORITATE") );
                  tichetObj.setPrioritate(rs.getString("PRIORITATE") );
                  tichetObj.setDen_prioritate(rs.getString("DEN_PRIORITATE") );
                  
                  tichetObj.setId_entitate_activa(rs.getBigDecimal("ID_ENTITATE_ACTIVA") );
                  tichetObj.setEntitate_activa(rs.getString("ENTITATE_ACTIVA") );
                  tichetObj.setDen_entitate_activa(rs.getString("DEN_ENTITATE_ACTIVA") );
                  
                  tichetObj.setDescriere(rs.getString("DESCRIERE") );
                  
                  tichetObj.setId_serviciu(rs.getBigDecimal("ID_SERVICIU") );
                  tichetObj.setServiciu(rs.getString("SERVICIU") );
                  tichetObj.setDen_serviciu(rs.getString("DEN_SERVICIU") );
                  
                  tichetObj.setId_componenta(rs.getBigDecimal("ID_COMPONENTA") );
                  tichetObj.setComponenta(rs.getString("COMPONENTA") );
                  tichetObj.setDen_componenta(rs.getString("DEN_COMPONENTA") );
                  
                  tichetObj.setId_stare(rs.getBigDecimal("ID_STARE") );
                  tichetObj.setStare(rs.getString("STARE") );
                  tichetObj.setDen_stare(rs.getString("DEN_STARE") );
                  
                  System.out.println(" -->Next  Nr_tichet = " + tichetObj.getNr_tichet());
                  
                  listaTichete.addTichet(tichetObj);
                }
                
              }
              else {
                mesaj_ERR += "Nu exista inregistrari pentru selectia facuta!";
                listaTichete.setMesaj(mesaj_ERR);
              }
              
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) { }
            
            try {pstmt_countTichete.close();} catch (Exception ex) {}
            try {pstmt_selectTichete.close();} catch (Exception ex) {}
            try {pstmt_validareLista.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaTichete;
    }
    
    
    
//    @RequestMapping(value = "/restListeTichete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
//    public TichetObj listeTichete(@RequestParam(value = "tip_lista") String tip_lista) {
//      
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        
//        TichetObj tichetObj = new TichetObj();
//        String sql_listeTichete = " select " +
//                                  "  LISTE.id_lista, " +
//                                  "  LISTE.tip_lista, " +
//                                  "  LISTE_DEF.id_lista_def, " +
//                                  "  LISTE_DEF.cod_lista, " +
//                                  "  LISTE_DEF.den_lista " +
//                                  " from " +
//                                  "  DOLPHIN.LISTE, " +
//                                  "  DOLPHIN.LISTE_DEF " +
//                                  " where " +
//                                  "  LISTE.id_lista = LISTE_DEF.id_lista " +
//                                  //"  and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' ) " +
//                                  "  and LISTE.tip_lista in (?, ?, ?, ?, ?) " +
//                                  " order by " +
//                                  "  LISTE.tip_lista, " +
//                                  "  LISTE_DEF.cod_lista ";
//        try {
//          
//          tichetObj.setId(100);
//          System.out.println("-> Apel restListeTichete" );
//          System.out.println("-> tip_lista = " + tip_lista);
//          System.out.println("-> sql_listeTichete = " + sql_listeTichete);
//            
//          conn = dataSource.getConnection();
//          pstmt = conn.prepareStatement( sql_listeTichete );
//          
//          pstmt.clearParameters();
//          pstmt.setString(1, "PRIORITATI");
//          pstmt.setString(2, "ENTITATI");
//          pstmt.setString(3, "SERVICII");
//          pstmt.setString(4, "COMPONENTE");
//          pstmt.setString(5, "STARI");
//          
//          rs = pstmt.executeQuery();
//          while(rs.next()) {
//            System.out.println( rs.getString("tip_lista") + " - " + rs.getString("cod_lista") );
//          }
//          
//        } catch (SQLException ex) {
//            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
//            
//              
//        } finally {
//            try {rs.close();} catch (Exception ex) {}
//            try {pstmt.close();} catch (Exception ex) {}
//            try {conn.close();} catch (Exception ex) {
//                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return tichetObj;
//    }
            
}
