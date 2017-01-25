/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Tipidentifica;
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
public class TipidentificaDAO implements Serializable {

    public List<Tipidentifica> findAll() throws SQLException {
        conexion con = new conexion();
        List<Tipidentifica> listadoTipidentificador = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select tpi_codigo, tpi_nombre, tpi_des, tpi_codigo1, tpi_codigo2, tpi_codigo3, "
                + "case tpi_estado when 'A' then 'Activo' else 'Inactivo' end as tpi_estado "
                + "from publico.mae_tipidentifica";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Tipidentifica tip = new Tipidentifica();
                tip.setTpi_codigo(rs.getString(1));
                tip.setTpi_nombre(rs.getString(2));
                tip.setTpi_des(rs.getString(3));
                tip.setTpi_codigo1(rs.getString(4));
                tip.setTpi_codigo2(rs.getString(5));
                tip.setTpi_codigo3(rs.getString(6));
                tip.setTpi_estado(rs.getString(7));
                listadoTipidentificador.add(tip);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST TIPIDENTIFICA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoTipidentificador;
    }

    public void editTipidentifica(Tipidentifica tip) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipidentifica "
                + "set tpi_nombre=?, tpi_des=?, tpi_codigo1=?, tpi_codigo2=?, tpi_codigo3=? "
                + "where tpi_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tip.getTpi_nombre());
            pst.setString(2, tip.getTpi_des());
            pst.setString(3, tip.getTpi_codigo1());
            pst.setString(4, tip.getTpi_codigo2());
            pst.setString(5, tip.getTpi_codigo3());
            pst.setString(6, tip.getTpi_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT TIPIDENTIFICA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteTipidentifica(Tipidentifica tip) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipidentifica set tip_estado = (Case tip_estado when 'A' then 'E' when 'E' then 'A' end) where tip_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tip.getTpi_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE TIPIDENTIFICA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
