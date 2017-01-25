/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Tipcontainer;
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
public class TipcontainerDAO implements Serializable {

    public List<Tipcontainer> findAll() throws SQLException {
        conexion con = new conexion();
        List<Tipcontainer> listadoTipcontainers = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select cod_tipcont, tcon_nombre, tcon_des, val_tara, tcon_codigo1, tcon_codigo2, tcon_codigo3, tcon_codigo4,  "
                + "case tcon_estado when 'A' then 'Activo' else 'Inactivo' end as tcon_estado "
                + "from publico.mae_tipcontainer";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Tipcontainer tcon = new Tipcontainer();
                tcon.setCod_tipcont(rs.getString(1));
                tcon.setTcon_nombre(rs.getString(2));
                tcon.setTcon_des(rs.getString(3));
                tcon.setVal_tara(rs.getDouble(4));
                tcon.setTcon_codigo1(rs.getString(5));
                tcon.setTcon_codigo2(rs.getString(6));
                tcon.setTcon_codigo3(rs.getString(7));
                tcon.setTcon_codigo4(rs.getString(8));
                tcon.setTcon_estado(rs.getString(9));
                listadoTipcontainers.add(tcon);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST TIPO CONTAINERS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoTipcontainers;
    }

    public void editTipcontainer(Tipcontainer tcon) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipcontainer "
                + "set tcon_nombre=?, tcon_des=?, val_tara=?, tcon_codigo1=?, tcon_codigo2=?, tcon_codigo3=?, tcon_codigo4=? "
                + "where cod_tipcont=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tcon.getTcon_nombre());
            pst.setString(2, tcon.getTcon_des());
            pst.setDouble(3, tcon.getVal_tara());
            pst.setString(4, tcon.getTcon_codigo1());
            pst.setString(5, tcon.getTcon_codigo2());
            pst.setString(6, tcon.getTcon_codigo3());
            pst.setString(7, tcon.getTcon_codigo4());
            pst.setString(8, tcon.getCod_tipcont());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT TIPO CONTAINER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteTipcontainer(Tipcontainer tcon) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipcontainer set tcon_estado = (Case tcon_estado when 'A' then 'E' when 'E' then 'A' end) where cod_tipcont=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tcon.getCod_tipcont());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE TIPO CONTAINER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
