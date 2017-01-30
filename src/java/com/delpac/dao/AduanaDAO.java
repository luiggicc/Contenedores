/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Aduana;
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
public class AduanaDAO implements Serializable {

    public List<Aduana> findAll() throws SQLException {
        conexion con = new conexion();
        List<Aduana> listadoAduanas = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        //String query = "select * from producto";
        String query = "select adu_codigo, adu_nombre, adu_des, adu_codigo1, adu_codigo2, adu_codigo3, "
                + "case adu_estado when 'A' then 'Activo' else 'Inactivo' end as adu_estado "
                + "from publico.mae_aduana "
                + "order by adu_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Aduana adu = new Aduana();
                adu.setAdu_codigo(rs.getString(1));
                adu.setAdu_nombre(rs.getString(2));
                adu.setAdu_des(rs.getString(3));
                adu.setAdu_codigo1(rs.getString(4));
                adu.setAdu_codigo2(rs.getString(5));
                adu.setAdu_codigo3(rs.getString(6));
                adu.setAdu_estado(rs.getString(7));
                listadoAduanas.add(adu);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST ADUANAS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoAduanas;
    }

    public void editAduana(Aduana adu) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_aduana "
                + "set adu_nombre=?, adu_des=?, adu_codigo1=?, adu_codigo2=?, adu_codigo3=? "
                + "where adu_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, adu.getAdu_nombre());
            pst.setString(2, adu.getAdu_des());
            pst.setString(3, adu.getAdu_codigo1());
            pst.setString(4, adu.getAdu_codigo2());
            pst.setString(5, adu.getAdu_codigo3());
            pst.setString(6, adu.getAdu_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT ADUANA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteAduana(Aduana adu) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_aduana set adu_estado = (Case adu_estado when 'A' then 'E' when 'E' then 'A' end) where adu_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, adu.getAdu_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE ADUANA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
