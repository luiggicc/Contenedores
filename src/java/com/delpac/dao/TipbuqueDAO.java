/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Tipbuque;
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
public class TipbuqueDAO implements Serializable {

    public List<Tipbuque> findAll() throws SQLException {
        conexion con = new conexion();
        List<Tipbuque> listadoTipbuque = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select tbu_codigo, tbu_nombre, tbu_des, tbu_codigo1, tbu_codigo2, tbu_codigo3, "
                + "case tbu_estado when 'A' then 'Activo' else 'Inactivo' end as tbu_estado "
                + "from publico.mae_tipbuque "
                + "order by tbu_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Tipbuque tbu = new Tipbuque();
                tbu.setTbu_codigo(rs.getString(1));
                tbu.setTbu_nombre(rs.getString(2));
                tbu.setTbu_des(rs.getString(3));
                tbu.setTbu_codigo1(rs.getString(4));
                tbu.setTbu_codigo2(rs.getString(5));
                tbu.setTbu_codigo3(rs.getString(6));
                tbu.setTbu_estado(rs.getString(7));
                listadoTipbuque.add(tbu);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST TIPBUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoTipbuque;
    }

    public void editTipbuque(Tipbuque tbu) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipbuque "
                + "set tbu_nombre=?, tbu_des=?, tbu_codigo1=?, tbu_codigo2=?, tbu_codigo3=? "
                + "where tbu_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tbu.getTbu_nombre());
            pst.setString(2, tbu.getTbu_des());
            pst.setString(3, tbu.getTbu_codigo1());
            pst.setString(4, tbu.getTbu_codigo2());
            pst.setString(5, tbu.getTbu_codigo3());
            pst.setString(6, tbu.getTbu_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT TIPBUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteTipbuque(Tipbuque tbu) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_tipbuque set tbu_estado = (Case tbu_estado when 'A' then 'E' when 'E' then 'A' end) where tbu_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tbu.getTbu_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE TIPBUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

}
