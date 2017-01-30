/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Almacen;
import conexion.conexion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bottago SA
 */
public class AlmacenDAO implements Serializable {

    public List<Almacen> findAll() throws SQLException {
        conexion con = new conexion();
        List<Almacen> listadoAlmacenes = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "Select alm_codigo, alm_nombre, alm_des, alm_tip, alm_tipcom, alm_tipind, alm_codigo1, alm_codigo2, "
                + "alm_codigo3, case alm_estado when 'A' then 'Activo' else 'Inactivo' end as alm_estado "
                + "from publico.mae_almacen "
                + "order by alm_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Almacen alm = new Almacen();
                alm.setAlm_codigo(rs.getString(1));
                alm.setAlm_nombre(rs.getString(2));
                alm.setAlm_des(rs.getString(3));
                alm.setAlm_tip(rs.getString(4));
                alm.setAlm_tipcom(rs.getString(5));
                alm.setAlm_tipind(rs.getString(6));
                alm.setAlm_codigo1(rs.getString(7));
                alm.setAlm_codigo2(rs.getString(8));
                alm.setAlm_codigo3(rs.getString(9));
                alm.setAlm_estado(rs.getString(10));
                listadoAlmacenes.add(alm);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST ALMACENES: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoAlmacenes;
    }

    public void editAlmacen(Almacen alm) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_almacen "
                + "set alm_nombre=?"
                + "where alm_codigo=? ";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, alm.getAlm_nombre());
//            pst.setString(2, alm.getAlm_des());
            pst.setString(2, alm.getAlm_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT ALMACEN: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteAlmacen(Almacen alm) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_almacen set alm_estado = (Case alm_estado when 'A' then 'E' when 'E' then 'A' end) where alm_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, alm.getAlm_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE ALMACEN: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
