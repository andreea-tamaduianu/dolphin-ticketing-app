/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dolphin.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.dolphin.obj.ListaUtilizatori;
import ro.ase.dolphin.obj.Liste_defObj;
import ro.ase.dolphin.obj.TichetObj;
import ro.ase.dolphin.obj.UtilizatorObj;
import ro.ase.dolphin.obj.ValoriListaObj;

/**
 *
 * @author Ana
 */
@RestController
public class RestUtilizatorController {
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    
    @RequestMapping(value = "/restAdaugaUtilizator", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public UtilizatorObj adaugaUtilizator(@RequestParam(value = "nume_utiliz") String nume_utiliz,
                                          @RequestParam(value = "prenume_utiliz") String prenume_utiliz,
                                          @RequestParam(value = "cod_utiliz") String cod_utiliz,
                                          @RequestParam(value = "parola") String parola,
                                          @RequestParam(value = "echipa") String echipa,
                                          @RequestParam(value = "rol") String rol_utiliz,
                                          @RequestParam(value = "stare") String stare,
                                          @RequestParam(value = "email") String email) {
                       
        Connection conn = null;
        PreparedStatement pstmt_insertUtilizator = null;
        PreparedStatement pstmt_validareLista = null;
        PreparedStatement pstmt_generarePK = null;
        ResultSet rs = null;
        
        BigDecimal id_echipa = null;
        BigDecimal id_rol = null;
        BigDecimal id_stare = null;
        BigDecimal id_utiliz = null;
       //TO DO
       
                                             
        String mesaj_ERR = "";
        boolean isErrors = false;
        
        UtilizatorObj utilizatorObj = new UtilizatorObj();
        
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
                                  "  and LISTE.tip_lista like upper(?) " +
                                  "  and LISTE_DEF.cod_lista like upper(?) " ;
        
        
        String sqlInsertUtilizator = " insert into DOLPHIN.UTILIZATORI ( " +
                                     "  ID_UTILIZ, " +
                                     "  COD_UTILIZ, " +
                                     "  NUME_UTILIZ, " +      
                                     "  ID_ROL_UTILIZ, " +  
                                     "  PAROLA, " +
                                     "  ID_STARE, " +
                                     "  PRENUME_UTILIZ, " +
                                     "  ID_ECHIPA, EMAIL ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sqlPK_Utilizator = " select DOLPHIN.SEQ_UTILIZ.nextval PK_VAL from dual ";
        
        try {
          
            System.out.println("-> ApelrestAdaugaUtilizator " );
            System.out.println("-> cod_utiliz = " + cod_utiliz);
            System.out.println("-> nume_utiliz = " + nume_utiliz);
            System.out.println("-> rol_utiliz = " + rol_utiliz);
            System.out.println("-> parola = " + parola);
            System.out.println("-> stare = " + stare);
            System.out.println("-> prenume_utiliz = " + prenume_utiliz); 
            System.out.println("-> echipa = " + echipa); 
            System.out.println("-> echipa = " + email); 
            
             /*
             -> cod_utiliz = AB
             -> nume_utiliz = A
             -> rol_utiliz = Admin
             -> parola = 1234
             -> stare = Deschis
             -> prenume_utiliz = B
             -> echipa = DBA
             */ 

            
            System.out.println("-> sqlInsertUtilizator = " + sqlInsertUtilizator);
            System.out.println("-> sqlValidareLista = " + sqlValidareLista);
            System.out.println("-> sqlPK_Utilizator = " + sqlPK_Utilizator);
            
            conn = dataSource.getConnection();
            
            pstmt_generarePK = conn.prepareStatement(sqlPK_Utilizator);
            pstmt_insertUtilizator = conn.prepareStatement(sqlInsertUtilizator);
            pstmt_validareLista = conn.prepareStatement(sqlValidareLista);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "ECHIPE");
            pstmt_validareLista.setString(2, echipa);
            
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_echipa = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul de echipa "+echipa+" nu este definit in lista ECHIPE!" ;
            }
            System.out.println(" --> id_ECHIPA = " + id_echipa);
            
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "ROLURI");
            pstmt_validareLista.setString(2, rol_utiliz);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_rol = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul rolului "+rol_utiliz+" nu este definit in lista ROLURI!" ;
            }
            System.out.println(" --> id_ROL = " + id_rol);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "STARI_U");
            pstmt_validareLista.setString(2, stare);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_stare = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul starii utilizatorului"+stare+" nu este definita in lista STARI!" ;
            }
            System.out.println(" --> id_STARE = " + id_stare);
            
            pstmt_validareLista.clearParameters();
            
            if(isErrors) {
              utilizatorObj.setUtilizator_adaugat("NU"); 
              utilizatorObj.setErr_message( mesaj_ERR );
              System.out.println(" --> mesaj_ERR  = " + mesaj_ERR);
              
            }
            else {
              
              try {rs.close();} catch (Exception ex) {}
              rs = pstmt_generarePK.executeQuery(); 
              if(rs.next()) {
                id_utiliz = rs.getBigDecimal("PK_VAL");                
              }        
              System.out.println(" --> id_utiliz = " + id_utiliz);
              
              utilizatorObj.setId_utiliz(id_utiliz.longValue() );
              
              pstmt_insertUtilizator.clearParameters(); 
              pstmt_insertUtilizator.setBigDecimal(1, id_utiliz);
              
              pstmt_insertUtilizator.setString(2, cod_utiliz);
              pstmt_insertUtilizator.setString(3, nume_utiliz);
              
              pstmt_insertUtilizator.setBigDecimal(4, id_rol);
              pstmt_insertUtilizator.setString(5, parola);
              
              pstmt_insertUtilizator.setBigDecimal(6, id_stare);
              pstmt_insertUtilizator.setString(7, prenume_utiliz);
              pstmt_insertUtilizator.setBigDecimal(8, id_echipa);
              pstmt_insertUtilizator.setString(9, email);
              
              int insert_row = pstmt_insertUtilizator.executeUpdate();
              
              System.out.println(" --> insert_row = " + insert_row);
              utilizatorObj.setUtilizator_adaugat("DA"); 
              utilizatorObj.setErr_message( null );
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestUtilizatorController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) {}
            try {pstmt_insertUtilizator.close();} catch (Exception ex) {}
            try {pstmt_validareLista.close();} catch (Exception ex) {}
            try {pstmt_generarePK.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestUtilizatorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return utilizatorObj;
    }
    
    @RequestMapping(value = "/restConsultareUtilizatori", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public ListaUtilizatori consultareUtilizatori(@RequestParam(value = "filterScript") String filterScript,
                                              @RequestParam(value = "orderScript") String orderScript,
                                              @RequestParam(value = "curentSet") BigDecimal curentSet,
                                              @RequestParam(value = "rowsNoPerSet") BigDecimal rowsNoPerSet
                                              ) {
      
  
        Connection conn = null;
        PreparedStatement pstmt_countUtilizatori = null;
        PreparedStatement pstmt_selectUtilizatori = null;
        
        ResultSet rs = null;
        
        BigDecimal rowsNo = BigDecimal.ZERO;
        BigDecimal setsNo = BigDecimal.ZERO;
        
                                             
        String mesaj_ERR = "";
        boolean isErrors = false;
        
        BigDecimal firstRecord = BigDecimal.ZERO;
        BigDecimal lastRecord = BigDecimal.ZERO;
                
        firstRecord = curentSet.subtract(BigDecimal.ONE).multiply(rowsNoPerSet).add( BigDecimal.ONE );
        lastRecord = curentSet.multiply(rowsNoPerSet);
        
        System.out.println("-> consultareUtilizatori " );
        
        System.out.println("-> 1. filterScript = " + filterScript);
        System.out.println("-> 1. orderScript = " + orderScript);
        
        System.out.println("-> curentSet = " + curentSet);
        System.out.println("-> rowsNoPerSet = " + rowsNoPerSet);
        
        System.out.println("-> firstRecord = " + firstRecord);
        System.out.println("-> lastRecord = " + lastRecord);
        
        if ( orderScript == null || orderScript.length()  == 0 ) { 
          orderScript = " utilizatori.cod_utiliz ";
        }
        if(filterScript == null) {
          filterScript = "";
        }
        System.out.println("-> 2. filterScript = " + filterScript);
        System.out.println("-> 2. orderScript = " + orderScript);
        
        String sqlCountUtilizatori = " SELECT  " +
                                 "  COUNT(utilizatori.id_utiliz) ROWS_NO " +
                                 " FROM " +
                                 "  DOLPHIN.utilizatori " + 
                                 " WHERE " +
                                 "  1=1 "  +                                   
                                 filterScript  + 
                                 "";                
                
        String sqlSelectUtilizatori = " select " +
                                      "  utilizatori.id_utiliz, " +
                                      "  utilizatori.cod_utiliz, " +
                                      "  utilizatori.nume_utiliz, " +
                                      "  utilizatori.prenume_utiliz, " +
                
                                      "  utilizatori.id_rol_utiliz, " +                                      
                                      "  rol.cod_lista rol_utiliz, " +
                                      "  rol.den_lista den_rol_utiliz, " +
                                      
                                      "  utilizatori.id_stare, " +
                                      "  stare.cod_lista stare, " +
                                      "  stare.den_lista den_stare, " +
                                      
                                      "  utilizatori.id_echipa, " +
                                      "  echipa.cod_lista echipa, " +
                                      "  echipa.den_lista den_echipa, " +
                                      
                                      "  utilizatori.email " +
                                      " from " +
                                      "  dolphin.utilizatori, " +
                                      "  dolphin.liste_def stare, " +
                                      "  dolphin.liste_def echipa, " +
                                      "  dolphin.liste_def rol " +
                                      " where  " +
                                      "  stare.id_lista_def = utilizatori.id_stare    " +
                                      "  and echipa.id_lista_def = utilizatori.id_echipa " +
                                      "  and rol.id_lista_def = utilizatori.id_rol_utiliz  " + 
                
                                  "  AND ROWNUM >= " + firstRecord + 
                                  "  AND ROWNUM <= " + lastRecord + 
                                  
                                  filterScript  + 
                                  " order by " +
                                  orderScript +                                                                     
                                  " ";
        
        
        ListaUtilizatori listaUtilizatori = new ListaUtilizatori();
        UtilizatorObj utilizatorObj = null;
        
        
        try {
            
            System.out.println("-> sqlInsertTichet = " + sqlSelectUtilizatori);
            
            conn = dataSource.getConnection();
            
            pstmt_countUtilizatori = conn.prepareStatement(sqlCountUtilizatori);
            pstmt_selectUtilizatori = conn.prepareStatement(sqlSelectUtilizatori);
            

            try {rs.close();} catch (Exception ex) {}
            
            pstmt_countUtilizatori.clearParameters(); 
            rs = pstmt_countUtilizatori.executeQuery();
            
            if(rs.next()) {
               rowsNo =  rs.getBigDecimal("ROWS_NO");                 
            }
            System.out.println(" --> rowsNo = " + rowsNo);
            listaUtilizatori.setRowsNo( rowsNo );


            if( BigDecimal.ZERO.compareTo( rowsNo ) < 0 ) {

              setsNo = rowsNo.divide(rowsNoPerSet, 0, RoundingMode.UP );
              listaUtilizatori.setSetsNo(setsNo);

              try {rs.close();} catch (Exception ex) {}
              pstmt_selectUtilizatori.clearParameters(); 

              rs = pstmt_selectUtilizatori.executeQuery(); 

              System.out.println(" --> inainte de rs.next() ");

              while(rs.next()) {
                System.out.println(" --> in interior la rs.next() ");

                utilizatorObj = new UtilizatorObj();

                utilizatorObj.setId_utiliz(rs.getLong("id_utiliz") );
                
                utilizatorObj.setCod_utiliz(rs.getString("cod_utiliz") );
                utilizatorObj.setNume_utiliz(rs.getString("nume_utiliz") );
                utilizatorObj.setPrenume_utiliz(rs.getString("prenume_utiliz") );
                
                utilizatorObj.setId_rol_utiliz(rs.getBigDecimal("id_rol_utiliz") );
                utilizatorObj.setRol_utiliz(rs.getString("rol_utiliz") );
                utilizatorObj.setDen_rol_utiliz(rs.getString("den_rol_utiliz") );
                
                utilizatorObj.setId_stare(rs.getBigDecimal("ID_STARE") );
                utilizatorObj.setStare(rs.getString("STARE") );
                utilizatorObj.setDen_stare(rs.getString("DEN_STARE") );
                
                utilizatorObj.setId_echipa(rs.getBigDecimal("id_echipa") );
                utilizatorObj.setEchipa(rs.getString("echipa") );
                utilizatorObj.setDen_echipa(rs.getString("den_echipa") );
 
                utilizatorObj.setEmail(rs.getString("email") );
                

                System.out.println(" -->Next  Cod_utiliz = " + utilizatorObj.getCod_utiliz() );

                listaUtilizatori.addTichet(utilizatorObj);
              }

            }
            else {
              mesaj_ERR += "Nu exista inregistrari pentru selectia facuta!";
              listaUtilizatori.setMesaj(mesaj_ERR);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) { }
            
            try {pstmt_countUtilizatori.close();} catch (Exception ex) {}
            try {pstmt_selectUtilizatori.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaUtilizatori;
    }
    
    
    
    
}
