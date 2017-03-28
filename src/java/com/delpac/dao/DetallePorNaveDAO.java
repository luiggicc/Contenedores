/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.DetallePorNave;
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
public class DetallePorNaveDAO implements Serializable {

    public List<DetallePorNave> DetallePorNave(String itinerario) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<DetallePorNave> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = "Select row_number() over (order by equipo_identi) as item, recipient, equipo_identi, status, iso, peso_bruto, sello, pto_carga, "
                    + "pto_descarga, fecha_arribo, bl "
                    + "from publico.descarga "
                    + "where itinerario = ? and movimiento = 'Descarga' "
                    + "order by equipo_identi";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, itinerario);
            rs = pst.executeQuery();
            while (rs.next()) {
                DetallePorNave dxn = new DetallePorNave();
                dxn.setItem(rs.getInt(1));
                dxn.setRecipient(rs.getString(2));
                dxn.setEquipo_identi(rs.getString(3));
                dxn.setStatus(rs.getString(4));
                dxn.setIso(rs.getString(5));
                dxn.setPeso_bruto(rs.getString(6));
                dxn.setSello(rs.getString(7));
                dxn.setPto_carga(rs.getString(8));
                dxn.setPto_descarga(rs.getString(9));
                dxn.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(10).getTime()))));
                dxn.setBl(rs.getString(11));
                lista.add(dxn);
            }
        } catch (Exception e) {
            System.out.println("DAO Detalle por Nave: " + e.getMessage());
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
