/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Condicion;
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
public class CondicionDAO implements Serializable {

    public List<Condicion> findAll() throws SQLException {
        conexion con = new conexion();
        List<Condicion> listadoCondiciones = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select cond_codigo, cond_nombre, cond_des, cond_codigo1, cond_codigo2, cond_codigo3, "
                + "case cond_estado when 'A' then 'Activo' else 'Inactivo' end as cond_estado "
                + "from publico.mae_condicion "
                + "order by cond_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Condicion cond = new Condicion();
                cond.setCond_codigo(rs.getString(1));
                cond.setCond_nombre(rs.getString(2));
                cond.setCond_des(rs.getString(3));
                cond.setCond_codigo1(rs.getString(4));
                cond.setCond_codigo2(rs.getString(5));
                cond.setCond_codigo3(rs.getString(6));
                cond.setCond_estado(rs.getString(7));
                listadoCondiciones.add(cond);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST CONDICIONES: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoCondiciones;
    }

    public void editCondicion(Condicion cond) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_condicion "
                + "set cond_nombre=?, cond_des=?, cond_codigo1=?, cond_codigo2=?, cond_codigo3=? "
                + "where cond_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, cond.getCond_nombre());
            pst.setString(2, cond.getCond_des());
            pst.setString(3, cond.getCond_codigo1());
            pst.setString(4, cond.getCond_codigo2());
            pst.setString(5, cond.getCond_codigo3());
            pst.setString(6, cond.getCond_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT CONDICION: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteCondicion(Condicion cond) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_condicion set cond_estado = (Case cond_estado when 'A' then 'E' when 'E' then 'A' end) where cond_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, cond.getCond_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE CONDICION: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
