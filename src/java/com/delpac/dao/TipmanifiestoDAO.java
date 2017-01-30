/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Tipmanifiesto;
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
public class TipmanifiestoDAO implements Serializable {

    public List<Tipmanifiesto> findAll() throws SQLException {
        conexion con = new conexion();
        List<Tipmanifiesto> listadoTipmanifiestos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select tman_codigo, tman_nombre, tman_des, tman_codigo1, tman_codigo2, tman_codigo3, "
                + "case tman_estado when 'A' then 'Activo' else 'Inactivo' end as tman_estado "
                + "from publico.mae_tipmanifiesto "
                + "order by tman_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Tipmanifiesto tman = new Tipmanifiesto();
                tman.setTman_codigo(rs.getString(1));
                tman.setTman_nombre(rs.getString(2));
                tman.setTman_des(rs.getString(3));
                tman.setTman_codigo1(rs.getString(4));
                tman.setTman_codigo2(rs.getString(5));
                tman.setTman_codigo3(rs.getString(6));
                tman.setTman_estado(rs.getString(7));
                listadoTipmanifiestos.add(tman);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST TIPO MANIFIESTOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoTipmanifiestos;
    }

    public void editTipmanifiesto(Tipmanifiesto tman) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipmanifiesto "
                + "set tman_des=?, tman_nombre=?, tman_codigo1=?, tman_codigo2=?, tman_codigo3=? "
                + "where tman_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tman.getTman_des());
            pst.setString(2, tman.getTman_nombre());
            pst.setString(3, tman.getTman_codigo1());
            pst.setString(4, tman.getTman_codigo2());
            pst.setString(5, tman.getTman_codigo3());
            pst.setString(6, tman.getTman_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT TIPO MANIFIESTO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteTipmanifiesto(Tipmanifiesto tman) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipmanifiesto set tman_estado = (Case tman_estado when 'A' then 'E' when 'E' then 'A' end) where tman_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tman.getTman_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE TIPO MANIFIESTO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
