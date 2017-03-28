/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.GeneralNaves;
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
public class GeneralNavesDAO implements Serializable {

    public List<GeneralNaves> CompararContenedores(String movimiento, Date desde, Date hasta) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<GeneralNaves> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "select des.itinerario, des.fecha_arribo, con.con_tipcont || ' ' || des.status as tipo_cont, count(con.con_tipcont) as total_cont "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = ? and fecha_arribo between ?::timestamp and ?::timestamp "
                    + "group by des.itinerario, des.fecha_arribo, con.con_tipcont, des.status "
                    + "order by itinerario";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, movimiento);
            pst.setString(2, format.format(desde));
            pst.setString(3, format.format(hasta));
            rs = pst.executeQuery();
            while (rs.next()) {
                GeneralNaves gna = new GeneralNaves();
                gna.setItinerario(rs.getString(1));
                gna.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(2).getTime()))));
                gna.setTipo_cont(rs.getString(3));
                gna.setTotal_cont(rs.getInt(4));
                lista.add(gna);
            }
        } catch (Exception e) {
            System.out.println("DAO General Naves: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(TransferDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }
    
    public List<GeneralNaves> CompararContenedoresExport(String movimiento, Date desde, Date hasta) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<GeneralNaves> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "select des.itinerario, des.fecha_salida, con.con_tipcont || ' ' || des.status as tipo_cont, count(con.con_tipcont) as total_cont "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = ? and fecha_salida between ?::timestamp and ?::timestamp "
                    + "group by des.itinerario, des.fecha_salida, con.con_tipcont, des.status "
                    + "order by itinerario";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, movimiento);
            pst.setString(2, format.format(desde));
            pst.setString(3, format.format(hasta));
            rs = pst.executeQuery();
            while (rs.next()) {
                GeneralNaves gna = new GeneralNaves();
                gna.setItinerario(rs.getString(1));
                gna.setFecha_salida(format.parse(format.format(new Date(rs.getTimestamp(2).getTime()))));
                gna.setTipo_cont(rs.getString(3));
                gna.setTotal_cont(rs.getInt(4));
                lista.add(gna);
            }
        } catch (Exception e) {
            System.out.println("DAO General Naves Export: " + e.getMessage());
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
