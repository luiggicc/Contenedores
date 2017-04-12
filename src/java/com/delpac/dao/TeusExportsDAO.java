/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;
import com.delpac.entity.TeusExports;
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
public class TeusExportsDAO implements Serializable{
        public List<TeusExports> ReportExport(String anio) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<TeusExports> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "Select to_char(des.fecha_salida, 'TMMONTH') as anio, des.fecha_salida, des.itinerario, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Export' then 1 end)) as cont20, "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' then 1 end)) as cont40, "
                    + "(count(case when con.con_tipcont='40RH' and des.movimiento = 'Descarga' then 1 end)) as cont40rh, "
                    + "(count(case when con.con_tamano='20' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end)) as cont20mty, "
                    + "(count(case when con.con_tamano='40' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end)) as cont40mty, "
                    + "((count(case when con.con_tamano = '20' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end)) + "
                    + "(count(case when con.con_tamano = '40' and des.movimiento = 'Export' and des.status = 'Empty' then 1 end))*2) as contemptyteus, "
                    + "((count(case when con.con_tamano = '20' and des.movimiento = 'Export' and des.status = 'Full' then 1 end)) + "
                    + "(count(case when con.con_tamano = '40' and des.movimiento = 'Export' and des.status = 'Full' then 1 end))*2) as contfullteus "
                    + "from publico.descarga des "
                    + "inner join publico.mae_container con on des.equipo_identi = con.con_codigo "
                    + "where des.movimiento = 'Export' and extract(year from des.fecha_salida) = ?::float "
                    + "group by anio, des.fecha_salida, des.itinerario";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, anio);
            rs = pst.executeQuery();
            while (rs.next()) {
                TeusExports rli = new TeusExports();
                rli.setMes(rs.getString(1));
                rli.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(2).getTime()))));
                rli.setItinerario(rs.getString(3));
                rli.setCont20(rs.getInt(4));
                rli.setCont40(rs.getInt(5));
                rli.setCont40rh(rs.getInt(6));
                rli.setCont20mty(rs.getInt(7));
                rli.setCont40mty(rs.getInt(8));
                rli.setContemptyteus(rs.getInt(9));
                rli.setContfullteus(rs.getInt(10));
                lista.add(rli);
            }
        } catch (Exception e) {
            System.out.println("DAO ReportLine Export : " + e.getMessage());
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
