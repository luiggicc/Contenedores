/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.PreDescarga;
import conexion.conexion;
import com.delpac.entity.Usuario;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bottago SA
 */
public class PreDescargaDAO implements Serializable {

    public int guardarPreDescarga(PreDescarga pre, Integer ItinerarioIdSelected) throws SQLException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        int repitedFlag = 0;
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        PreparedStatement pst2;
        String query = "insert into publico.predescarga(ids_itinerario, fec_arribo,"
                + "bl, consignatario, pto_origen, pto_destino, fec_embarque, peso, bulto, embajale, carga, con_codigo,"
                + "temperatura, ventilacion, sello, sown, classimo, unnro, tip_cont, condicion, alm_codigo) "
                + "values(?, to_char(current_timestamp,'YYYY-MM-DD')::timestamp, "
                + "?, ?, ?, ?, to_timestamp(?, 'dd/MM/YYYY'), ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String query2 = "insert into publico.mae_container(con_codigo, con_tipcont, lin_codigo, con_tamano, con_estado, con_ciclo) "
                + "select ?, ?, '0002', left(?,2), 'A', 0 "
                + "where not exists ( "
                + "select * from publico.mae_container where con_codigo =?);";
        pst = con.getConnection().prepareStatement(query);
        pst2 = con.getConnection().prepareStatement(query2);
        try {
            pst.setInt(1, ItinerarioIdSelected);
            pst.setString(2, pre.getBl());
            pst.setString(3, pre.getConsignatario());
            pst.setString(4, pre.getPto_origen());
            pst.setString(5, pre.getPto_destino());
            pst.setString(6, pre.getFec_embarque());
            pst.setDouble(7, pre.getPeso());
            pst.setDouble(8, pre.getBulto());
            pst.setString(9, pre.getEmbalaje());
            pst.setString(10, pre.getCarga());
            pst.setString(11, pre.getCon_codigo());
            pst.setString(12, pre.getTemperatura());
            pst.setString(13, pre.getVentilacion());
            pst.setString(14, pre.getSello());
            pst.setString(15, pre.getSown());
            pst.setString(16, pre.getClassimo() == null ? null : pre.getClassimo());
            pst.setString(17, pre.getUnnro() == null ? null : pre.getUnnro());
            pst.setString(18, pre.getTip_cont());
            pst.setString(19, pre.getCondicion());
            pst.setString(20, pre.getAlm_codigo());
            pst2.setString(1, pre.getCon_codigo());
            pst2.setString(2, pre.getTip_cont());
            pst2.setString(3, pre.getTip_cont());
            pst2.setString(4, pre.getCon_codigo());
            pst.executeUpdate();
            pst2.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO PREDESCARGA CARGA: " + e.getMessage());
            repitedFlag = 1;
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return repitedFlag;
    }
}
