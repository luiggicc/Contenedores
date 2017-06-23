/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.SellosCliente;
import com.delpac.entity.Usuario;
import conexion.conexion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bottago SA
 */
public class SellosClienteDAO implements Serializable {

    public List<SellosCliente> findAll() throws SQLException {
        conexion con = new conexion();
        List<SellosCliente> listadoSellosCliente = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select cia_nombre, ord.cod_ordenretiro, ord.booking, ord.inv_seguridad, ord.fecha_crea, "
                + "date_part('day', age(current_date, ord.fecha_crea)) as diferencia "
                + "from publico.ordenretiro ord "
                + "inner join publico.invsellos i on ord.inv_seguridad = i.inv_seguridad "
                + "left outer join publico.selloseliminados se on i.inv_codigo = se.inv_codigo "
                + "inner join publico.mae_clientes cli on ord.cia_codigo = cli.cia_codigo "
                + "where date_part('day', age(current_date, ord.fecha_crea)) > 10";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                SellosCliente scli = new SellosCliente();
                scli.setCia_nombre(rs.getString(1));
                scli.setCod_ordenretiro(rs.getInt(2));
                scli.setBooking(rs.getString(3));
                scli.setInv_seguridad(rs.getString(4));
                scli.setFecha_crea(rs.getDate(5));
                scli.setDiferencia(rs.getInt(6));
                listadoSellosCliente.add(scli);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST SELLOS EN CLIENTE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellosCliente;
    }
}
