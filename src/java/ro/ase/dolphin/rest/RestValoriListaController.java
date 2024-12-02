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
import ro.ase.dolphin.obj.Liste_defObj;
import ro.ase.dolphin.obj.SchimbareStareObj;
import ro.ase.dolphin.obj.ValoriListaObj;


/**
 *
 * @author ana.maria.tamaduianu
 */
@RestController
public class RestValoriListaController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/restAdaugaValoriListaDef", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    
    public Liste_defObj adaugaLista_def(@RequestParam(value = "tip_lista") String tip_lista,
                                  @RequestParam(value = "tip_lista_def") String tip_lista_def,
                                  @RequestParam(value = "cod_lista") String cod_lista,
                                  @RequestParam(value = "den_lista") String den_lista)
                                  {
      
                       
        Connection conn = null;
        PreparedStatement pstmt_insertListaDef = null;
        PreparedStatement pstmt_validareLista = null;
        PreparedStatement pstmt_generarePK = null;
        ResultSet rs = null;
        
        
        BigDecimal id_lista_def = null;
        BigDecimal id_lista = null;
                                             
        String mesaj_ERR = "";
        boolean isErrors = false;
        
        Liste_defObj listaDefObj = new Liste_defObj();
        
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
        
        
        String sqlInsertListaDef = " insert into DOLPHIN.LISTE_DEF (" +
                                  "  ID_LISTA_DEF, " +
                                  "  ID_LISTA, " +
                                  "  COD_LISTA, " +
                                  "  DEN_LISTA) values (?, ?, ?, ?)";
        
        String sqlPK_ListaDef = " select DOLPHIN.SEQ_LISTE_DEF.nextval PK_VAL from dual ";
        
        try {
          
            System.out.println("-> ApelrestAdaugaListaDef " );
            System.out.println("-> tip_lista = " + tip_lista);
            System.out.println("-> cod_lista = " + cod_lista);
            System.out.println("-> den_lista = " + den_lista);
           // System.out.println("-> order_no = " + order_no);
                        
            System.out.println("-> sqlInsertListaDef = " + sqlInsertListaDef);
            System.out.println("-> sqlValidareLista = " + sqlValidareLista);
            System.out.println("-> sqlPK_ListaDef = " + sqlPK_ListaDef);
            
            conn = dataSource.getConnection();
            
            pstmt_generarePK = conn.prepareStatement(sqlPK_ListaDef);
            pstmt_insertListaDef = conn.prepareStatement(sqlInsertListaDef);
            pstmt_validareLista = conn.prepareStatement(sqlValidareLista);
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "TIPURI_LISTA");
            pstmt_validareLista.setString(2, tip_lista);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_lista = rs.getBigDecimal("id_lista");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul tipului de lista "+id_lista+" nu este definit in lista TIPURI_LISTA!" ;
            }
            System.out.println(" --> id_lista= " + id_lista);
            
            
            
            pstmt_validareLista.clearParameters();
            pstmt_validareLista.setString(1, "TIPURI_LISTA_DEF");
            pstmt_validareLista.setString(2, tip_lista_def);
            try {rs.close();} catch (Exception ex) {}
            rs = pstmt_validareLista.executeQuery();
            if(rs.next()) {
              id_lista_def = rs.getBigDecimal("id_lista_def");
            }
            else {
              isErrors = true;
              mesaj_ERR += "Codul tipului de lista def "+id_lista_def+" nu este definit in lista TIPURI_LISTA_DEF!" ;
            }
            System.out.println(" --> id_lista_def = " + id_lista_def);
            
                   
            if(isErrors) {
              listaDefObj.setLista_adaugata("NU"); 
              listaDefObj.setErr_message( mesaj_ERR );
              System.out.println(" --> mesaj_ERR  = " + mesaj_ERR);
              
            }
            else {
              
              try {rs.close();} catch (Exception ex) {}
              rs = pstmt_generarePK.executeQuery(); 
              if(rs.next()) {
                id_lista_def = rs.getBigDecimal("PK_VAL");
                
              }        
              System.out.println(" --> id_lista_def = " + id_lista_def);
              
                      
              listaDefObj.setId_lista_def(id_lista_def);
              
              
              pstmt_insertListaDef.clearParameters(); 
              pstmt_insertListaDef.setBigDecimal(1, id_lista_def);
              pstmt_insertListaDef.setBigDecimal(2, id_lista);
              pstmt_insertListaDef.setString(3, cod_lista);
              pstmt_insertListaDef.setString(4, den_lista);
              
              int insert_row = pstmt_insertListaDef.executeUpdate();
              
              System.out.println(" --> insert_row = " + insert_row);
              listaDefObj.setLista_adaugata("DA"); 
              listaDefObj.setErr_message( null );
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            
            Logger.getLogger(RestTichetController.class.getName()).log(Level.SEVERE, null, ex);
              
        } finally {
            try {rs.close();} catch (Exception ex) {}
            try {pstmt_insertListaDef.close();} catch (Exception ex) {}
            try {pstmt_validareLista.close();} catch (Exception ex) {}
            try {pstmt_generarePK.close();} catch (Exception ex) {}
            
            try {conn.close();} catch (Exception ex) {
                Logger.getLogger(RestValoriListaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaDefObj;
    }
                
}

