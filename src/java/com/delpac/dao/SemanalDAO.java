/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Semanal;
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
public class SemanalDAO implements Serializable {

    public List<Semanal> ReporteSemanal(Date desde, Date hasta) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Semanal> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "select row_number() over (order by fecha_arribo) as Item, des.nave, des.viaje, des.fecha_arribo, "
                    + "count(case when des.movimiento = 'Descarga' and des.status = 'Full' then 1 end) as Cont_import, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Descarga' and des.status = 'Full' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Descarga' and des.status = 'Full' then 2 end) )*2) as Teus_import, "
                    + "count(case when des.movimiento = 'Descarga' and des.status = 'Empty' then 1 end) as cont_empty_import, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Descarga' and des.status = 'Empty' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Descarga' and des.status = 'Empty' then 2 end) )*2) as Teus_empty_import,"
                    + "(count(case when des.movimiento = 'Descarga' and des.status = 'Full' then 1 end)+ "
                    + "count(case when des.movimiento = 'Descarga' and des.status = 'Empty' then 1 end)) as Total_cont_import, "
                    + "((count(case when con.con_tamano='20' and des.movimiento = 'Descarga' and des.status = 'Full' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Descarga' and des.status = 'Full' then 2 end) )*2) + "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Descarga' and des.status = 'Empty' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Descarga' and des.status = 'Empty' then 2 end) )*2)) as Total_Teus_Import, "
                    + "count(case when des.movimiento = 'Export' and des.status = 'Full' then 1 end) as Cont_full_export, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Export' and des.status = 'Full' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' and des.status = 'Full' then 2 end) )*2) as teus_full_export, "
                    + "count(case when des.movimiento = 'Export' and des.status = 'Empty' then 1 end) as Cont_empty_export, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' and des.status = 'Empty' then 2 end) )*2) as teus_empty_export,"
                    + "(count(case when des.movimiento = 'Export' and des.status = 'Full' then 1 end)+ "
                    + "count(case when des.movimiento = 'Export' and des.status = 'Empty' then 1 end)) as Total_cont_export, "
                    + "((count(case when con.con_tamano='20' and des.movimiento = 'Export' and des.status = 'Full' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' and des.status = 'Full' then 2 end) )*2)+ "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end)  + "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' and des.status = 'Empty' then 2 end) )*2)) as Total_Teus_Export "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where (des.movimiento = 'Descarga' or des.movimiento = 'Export') "
                    + "and des.fecha_arribo between ?::timestamp and ?::timestamp "
                    + "group by des.nave, des.viaje, des.fecha_arribo "
                    + "order by des.fecha_arribo";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, format.format(desde));
            pst.setString(2, format.format(hasta));
            rs = pst.executeQuery();
            while (rs.next()) {
                Semanal sem = new Semanal();
                sem.setItem(rs.getInt(1));
                sem.setNave(rs.getString(2));
                sem.setViaje(rs.getString(3));
                sem.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(4).getTime()))));
                sem.setCont_import(rs.getInt(5));
                sem.setTeus_import(rs.getInt(6));
                sem.setCont_empty_import(rs.getInt(7));
                sem.setTeus_empty_import(rs.getInt(8));
                sem.setTotal_cont_import(rs.getInt(9));
                sem.setTotal_teus_import(rs.getInt(10));
                sem.setCont_full_export(rs.getInt(11));
                sem.setTeus_full_export(rs.getInt(12));
                sem.setCont_empty_export(rs.getInt(13));
                sem.setTeus_empty_export(rs.getInt(14));
                sem.setTotal_cont_export(rs.getInt(15));
                sem.setTotal_teus_export(rs.getInt(16));
                lista.add(sem);
            }
        } catch (Exception e) {
            System.out.println("DAO Semanal Weekly: " + e.getMessage());
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
