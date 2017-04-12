/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.InformeSellos;
import conexion.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

/**
 *
 * @author Bottago SA
 */
public class InformeSellosDAO implements Serializable {

    public List<InformeSellos> findAll(String booking) throws SQLException {
        conexion con = new conexion();
        List<InformeSellos> listadoOrdenes = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select cod_ordenretiro, to_char(ord.fecha_crea, 'DD-MM-YYYY') as fecha_crea, ord.cant_tipocont, "
                + "ord.inv_seguridad, isel.inv_sello, cli.cia_nombre, ord.dsp_itinerario, ord.booking "
                + "from publico.ordenretiro ord "
                + "inner join publico.invsellos isel on ord.inv_seguridad = isel.inv_seguridad "
                + "inner join publico.mae_clientes cli on ord.cia_codigo = cli.cia_codigo "
                + "where ord.est_orden = 2 and booking like ?";
        pst = con.getConnection().prepareStatement(query);
        pst.setString(1, booking);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                InformeSellos inse = new InformeSellos();
                inse.setCod_ordenretiro(rs.getInt(1));
                inse.setFecha_crea(rs.getString(2));
                inse.setCant_tipocont(rs.getString(3));
                inse.setInv_seguridad(rs.getString(4));
                inse.setInv_sello(rs.getString(5));
                inse.setCia_nombre(rs.getString(6));
                inse.setDsp_itinerario(rs.getString(7));
                inse.setBooking(rs.getString(8));
                listadoOrdenes.add(inse);
            }
        } catch (Exception e) {
            System.out.println("DAO INFORME SELLOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoOrdenes;
    }
}
