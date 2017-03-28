/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Comparador;
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
public class ComparadorDAO implements Serializable {

    public List<Comparador> CompararContenedores(String Dsp_Itinerario) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Comparador> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = " select pre.con_codigo, des.equipo_identi, des.sender, des.fecha_arribo "
                    + "from publico.predescarga pre "
                    + "left outer join publico.descarga des on pre.con_codigo = des.equipo_identi "
                    + "and pre.dsp_itinerario = des.itinerario and des.movimiento = 'Descarga' "
                    + "where pre.dsp_itinerario like ? "
                    + "union all "
                    + "select pre.con_codigo, des.equipo_identi, des.sender, des.fecha_arribo "
                    + "from publico.descarga des "
                    + "left outer join publico.predescarga pre on des.equipo_identi= pre.con_codigo "
                    + "and des.itinerario= pre.dsp_itinerario "
                    + "where des.itinerario like ? and movimiento like 'Descarga'";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, Dsp_Itinerario);
            pst.setString(2, Dsp_Itinerario);
            rs = pst.executeQuery();
            while (rs.next()) {
                Comparador com = new Comparador();
                com.setCon_codigo(rs.getString(1) == null ? "" : rs.getString(1));
                com.setEquipo_identi(rs.getString(2) == null ? "" : rs.getString(2));
                com.setSender(rs.getString(3) == null ? "" : rs.getString(3));
                com.setFecha_arribo(rs.getTimestamp(4) == null ? format.parse("0001-01-01 00:00:00") : (format.parse(format.format(new Date(rs.getTimestamp(4).getTime())))));
                
                
                lista.add(com);
            }
        } catch (Exception e) {
            System.out.println("DAO COMPARADOR CONTENEDORES: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(TransferDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }
}
