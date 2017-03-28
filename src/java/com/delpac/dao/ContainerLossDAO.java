/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.ContainerLoss;
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
public class ContainerLossDAO implements Serializable {

    public List<ContainerLoss> ContenedoresPerdidos() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<ContainerLoss> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "select des.recipient, closs.cont_cont_loss, closs.obs_cont_loss, des.status, des.iso, con.con_tipcont, des.sello, des.pto_carga, "
                    + "des.pto_descarga, des.fecha_arribo, des.fecha_salida, max(des.ciclo) as ciclo "
                    + "from publico.container_loss closs "
                    + "inner join publico.descarga des on closs.cont_cont_loss = des.equipo_identi "
                    + "inner join publico.mae_container con on closs.cont_cont_loss = con.con_codigo "
                    + "where des.movimiento = 'Descarga' "
                    + "group by des.recipient, closs.cont_cont_loss, closs.obs_cont_loss, des.status, des.iso, con.con_tipcont, des.sello, "
                    + "des.pto_carga, des.pto_descarga, des.fecha_arribo, des.fecha_salida";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ContainerLoss closs = new ContainerLoss();
                closs.setRecipient(rs.getString(1));
                closs.setCont_cont_loss(rs.getString(2));
                closs.setObs_cont_loss(rs.getString(3));
                closs.setStatus(rs.getString(4));
                closs.setIso(rs.getString(5));
                closs.setCon_tipcont(rs.getString(6));
                closs.setSello(rs.getString(7));
                closs.setPto_carga(rs.getString(8));
                closs.setPto_descarga(rs.getString(9));
                closs.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(10).getTime()))));
                closs.setFecha_salida(format.parse(format.format(new Date(rs.getTimestamp(11).getTime()))));
                lista.add(closs);
            }
        } catch (Exception e) {
            System.out.println("DAO Reporte Containers Perdidos: " + e.getMessage());
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
