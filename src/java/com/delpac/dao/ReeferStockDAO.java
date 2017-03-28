/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.ReeferStock;
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
public class ReeferStockDAO implements Serializable {

    public List<ReeferStock> ReeferStockConsulta(Date desde, Date hasta) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<ReeferStock> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "select 'Empty Stocks' as estado, "
                    + "count(case when con_tipcont = '20RF' then 1 end) as RF20, "
                    + "count(case when con_tipcont = '40HC' then 1 end) as RQ40, "
                    + "count(case when con_tipcont = '40RF' then 1 end) as RF40,"
                    + "max(des.ciclo) as ciclo "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = 'GateIn Puerto'  "
                    + "and des.fecha_arribo >= ?::timestamp and des.fecha_arribo <= ?::timestamp "
                    + "union all "
                    + "select 'Import Full' as estado, "
                    + "count(case when con.con_tipcont = '20RF' then 1 end) as RF20, "
                    + "count(case when con.con_tipcont = '40HC' then 1 end) as RQ40, "
                    + "count(case when con.con_tipcont = '40RF' then 1 end) as RF40,"
                    + "max(des.ciclo) as ciclo "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = 'Descarga' and status = 'Full' "
                    + "and des.fecha_arribo >= ?::timestamp and des.fecha_arribo <= ?::timestamp "
                    + "union all "
                    + "select 'Export Full' as estado, "
                    + "count(case when con.con_tipcont = '20RF' then 1 end) as RF20, "
                    + "count(case when con.con_tipcont = '40HC' then 1 end) as RQ40, "
                    + "count(case when con.con_tipcont = '40RF' then 1 end) as RF40, "
                    + "max(des.ciclo) as ciclo "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = 'Export' and status = 'Full' "
                    + "and des.fecha_arribo >= ?::timestamp and des.fecha_arribo <= ?::timestamp ";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, format.format(desde));
            pst.setString(2, format.format(hasta));
            pst.setString(3, format.format(desde));
            pst.setString(4, format.format(hasta));
            pst.setString(5, format.format(desde));
            pst.setString(6, format.format(hasta));
            rs = pst.executeQuery();
            while (rs.next()) {
                ReeferStock rfs = new ReeferStock();
                rfs.setEstado(rs.getString(1));
                rfs.setRf20(rs.getInt(2));
                rfs.setRq40(rs.getInt(3));
                rfs.setRf40(rs.getInt(4));
                lista.add(rfs);
            }
        } catch (Exception e) {
            System.out.println("DAO Reefer Stock: " + e.getMessage());
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
