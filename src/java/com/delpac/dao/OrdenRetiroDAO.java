/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.OrdenRetiro;
import com.delpac.entity.Usuario;
import conexion.conexion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bottago SA
 */
public class OrdenRetiroDAO implements Serializable {

    public List<OrdenRetiro> findAll() throws SQLException {
        conexion con = new conexion();
        List<OrdenRetiro> listadoOrdenes = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select ord.cod_ordenretiro, cli.cia_codigo, cli.cia_nombre, iti.ids_itinerario, iti.dsp_itinerario, lin.lin_codigo, lin.lin_nombre, ord.booking, "
                + "pto.pto_codigo, pto.pto_nombre, ord.mov_xcuenta, ord.cant_tipocont, ord.tipo_carga, ord.req_especial, ord.inv_seguridad, ord.es_temperado, "
                + "ord.temperatura, ord.ventilacion, ord.observaciones, loc.loc_codigo as loc_salida, "
                + "loc.loc_des as loc_salidades, loca.loc_codigo as loc_entrada, loca.loc_des as loc_entradades "
                + "from publico.ordenretiro ord "
                + "inner join publico.mae_clientes cli on ord.cia_codigo = cli.cia_codigo "
                + "inner join publico.mae_itinerario iti on ord.ids_itinerario = iti.ids_itinerario "
                + "inner join publico.mae_linea lin on ord.lin_codigo = lin.lin_codigo "
                + "inner join publico.mae_puerto pto on ord.pto_codigo = pto.pto_codigo "
                + "inner join publico.mae_localidad as loc on ord.loc_salida = loc.loc_codigo "
                + "inner join publico.mae_localidad as loca on ord.loc_entrada = loca.loc_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                OrdenRetiro ord = new OrdenRetiro();
                ord.setCod_ordenretiro(rs.getInt(1));
                ord.setCia_codigo(rs.getString(2));
                ord.setCia_nombre(rs.getString(3));
                ord.setIds_itinerario(rs.getInt(4));
                ord.setDsp_itinerario(rs.getString(5));
                ord.setLin_codigo(rs.getString(6));
                ord.setLin_nombre(rs.getString(7));
                ord.setBooking(rs.getString(8));
                ord.setPto_codigo(rs.getString(9));
                ord.setPto_nombre(rs.getString(10));
                ord.setMov_xcuenta(rs.getString(11));
                ord.setCant_tipocont(rs.getString(12));
                ord.setTipo_carga(rs.getString(13));
                ord.setReq_especial(rs.getString(14));
                ord.setInv_seguridad(rs.getString(15));
                ord.setEs_temperado(rs.getInt(16));
                ord.setTemperatura(rs.getString(17));
                ord.setVentilacion(rs.getString(18));
                ord.setObservaciones(rs.getString(19));
                ord.setLoc_salida(rs.getInt(20));
                ord.setLoc_salidades(rs.getString(21));
                ord.setLoc_entrada(rs.getInt(22));
                ord.setLoc_entradades(rs.getString(23));
                listadoOrdenes.add(ord);
            }
        } catch (Exception e) {
            System.out.println("DAO ORDENES RETIRO FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoOrdenes;
    }

    public void crearOrdenRetiro(OrdenRetiro or, boolean temperado, Usuario u) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        con.getConnection().setAutoCommit(false);
        ResultSet rs = null;
        String query = "";

        try {
            if (temperado) {
                query = "INSERT INTO publico.ordenretiro( "
                        + "cod_ordenretiro, cia_codigo, ids_itinerario, lin_codigo, booking, "
                        + "pto_codigo, mov_xcuenta, cant_tipocont, tipo_carga, req_especial, "
                        + "inv_seguridad, es_temperado, temperatura, ventilacion, observaciones, "
                        + "loc_salida, loc_entrada, fecha_crea, fecha_mod, usu_cre, usu_mod, est_orden, estadopdf) "
                        + "VALUES (cast(extract(year from current_timestamp)||cast(nextval('publico.sec_orden') as text) as numeric),?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, "
                        + "?, current_timestamp, null, ?, null, 1, 0); ";
            } else {
                query = " INSERT INTO publico.ordenretiro( "
                        + "cod_ordenretiro, cia_codigo, ids_itinerario, lin_codigo, booking, "
                        + "pto_codigo, mov_xcuenta, cant_tipocont, tipo_carga, req_especial, "
                        + "inv_seguridad, es_temperado, temperatura, ventilacion, observaciones, "
                        + "loc_salida, loc_entrada, fecha_crea, fecha_mod, usu_cre, usu_mod, est_orden, estadopdf) "
                        + "VALUES (cast(extract(year from current_timestamp)||cast(nextval('publico.sec_orden') as text) as numeric),?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, "
                        + "?, 0, null, null, ?, ?, "
                        + "?, current_timestamp, null, ?, null, 1, 0);";
            }
            pst = con.getConnection().prepareStatement(query);

            if (temperado == true) {
//                for (OrdenRetiro or : listadoOR) {
                pst.setString(1, or.getCia_codigo());
                pst.setInt(2, or.getIds_itinerario());
                pst.setString(3, or.getLin_codigo());
                pst.setString(4, or.getBooking());
                pst.setString(5, or.getPto_codigo());
                pst.setString(6, or.getMov_xcuenta());
                pst.setString(7, or.getCant_tipocont());
                pst.setString(8, or.getTipo_carga());
                pst.setString(9, or.getReq_especial());
                pst.setString(10, or.getInv_seguridad());
                pst.setInt(11, 1);
                pst.setString(12, or.getTemperatura());
                pst.setString(13, or.getVentilacion());
                pst.setString(14, or.getObservaciones());
                pst.setInt(15, or.getLoc_salida());
                pst.setInt(16, or.getLoc_entrada());
                pst.setString(17, u.getLogin());
                pst.executeUpdate();
            }
            if (temperado == false) {
                pst.setString(1, or.getCia_codigo());
                pst.setInt(2, or.getIds_itinerario());
                pst.setString(3, or.getLin_codigo());
                pst.setString(4, or.getBooking());
                pst.setString(5, or.getPto_codigo());
                pst.setString(6, or.getMov_xcuenta());
                pst.setString(7, or.getCant_tipocont());
                pst.setString(8, or.getTipo_carga());
                pst.setString(9, or.getReq_especial());
                pst.setString(10, or.getInv_seguridad());
                pst.setString(11, or.getObservaciones());
                pst.setInt(12, or.getLoc_salida());
                pst.setInt(13, or.getLoc_entrada());
                pst.setString(14, u.getLogin());
                pst.executeUpdate();
            }

            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREAR ORDEN RETIRO: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }
    
    public void updateVerificaPDF(OrdenRetiro ord, int cod_ordenretiro) throws SQLException {        
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.ordenretiro"
                + " set estadopdf=?"
                + " where cod_ordenretiro=? ";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, 1);
            pst.setInt(2, cod_ordenretiro);
            pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("DAO UPDATE VERIFICA PDF: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void editOrdenRetiro(OrdenRetiro or, boolean temperado, Usuario u) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        con.getConnection().setAutoCommit(false);
        ResultSet rs = null;
        String query = "";
        try {
            if (temperado) {
                query = "update publico.ordenretiro"
                        + "set cia_codigo=?, ids_itinerario=?, lin_codigo=?, booking=?, pto_codigo=?, mov_xcuenta=?, cant_tipocont=?, "
                        + "tipo_carga=?, req_especial=?,"
                        + "inv_seguridad=?, temperatura=?, ventilacion=?, observaciones=?, loc_salida=?, loc_entrada=?, "
                        + "fecha_mod=current_timestamp, usu_mod=?"
                        + "where cod_ordenretiro=?";
            } else {
                query = "update publico.ordenretiro"
                        + "set cia_codigo=?, ids_itinerario=?, lin_codigo=?, booking=?, pto_codigo=?, mov_xcuenta=?, cant_tipocont=?, "
                        + "tipo_carga=?, req_especial=?, "
                        + "inv_seguridad=?, observaciones=?, loc_salida=?, loc_entrada=?, fecha_mod=current_timestamp, usu_mod=?"
                        + "where cod_ordenretiro=?";
            }
            pst = con.getConnection().prepareStatement(query);
            if (temperado == true) {
                pst.setString(1, or.getCia_codigo());
                pst.setInt(2, or.getIds_itinerario());
                pst.setString(3, or.getLin_codigo());
                pst.setString(4, or.getBooking());
                pst.setString(5, or.getPto_codigo());
                pst.setString(6, or.getMov_xcuenta());
                pst.setString(7, or.getCant_tipocont());
                pst.setString(8, or.getTipo_carga());
                pst.setString(9, or.getReq_especial());
                pst.setString(10, or.getInv_seguridad());
                pst.setString(12, or.getTemperatura());
                pst.setString(13, or.getVentilacion());
                pst.setString(14, or.getObservaciones());
                pst.setInt(15, or.getLoc_salida());
                pst.setInt(16, or.getLoc_entrada());
                pst.setString(17, u.getLogin());
                pst.executeUpdate();
            }
            if (temperado == false) {
                pst.setString(1, or.getCia_codigo());
                pst.setInt(2, or.getIds_itinerario());
                pst.setString(3, or.getLin_codigo());
                pst.setString(4, or.getBooking());
                pst.setString(5, or.getPto_codigo());
                pst.setString(6, or.getMov_xcuenta());
                pst.setString(7, or.getCant_tipocont());
                pst.setString(8, or.getTipo_carga());
                pst.setString(9, or.getReq_especial());
                pst.setString(10, or.getInv_seguridad());
                pst.setString(11, or.getObservaciones());
                pst.setInt(12, or.getLoc_salida());
                pst.setInt(13, or.getLoc_entrada());
                pst.setString(14, u.getLogin());
                pst.executeUpdate();
            }

            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO EDITAR ORDEN RETIRO: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }
}
