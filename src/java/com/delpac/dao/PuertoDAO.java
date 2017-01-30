/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Puerto;
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
public class PuertoDAO implements Serializable {

    public List<Puerto> findAll() throws SQLException {
        conexion con = new conexion();
        List<Puerto> listadoPuertos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "Select pue.pto_codigo, pai.pai_codigo, pai.pai_nombre, pue.pto_nombre, pue.pto_des, pue.pto_codigo1, pue.pto_codigo2, pue.pto_codigo3, "
                + "pue.cod_serv, case pue.pto_estado when 'A' then 'Activo' else 'Inactivo' end as pto_estado "
                + "from publico.mae_puerto as pue "
                + "inner join publico.mae_pais as pai on pue.pai_codigo = pai.pai_codigo "
                + "order by pue.pto_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Puerto pue = new Puerto();
                pue.setPto_codigo(rs.getString(1));
                pue.setPai_codigo(rs.getString(2));
                pue.setPai_nombre(rs.getString(3));
                pue.setPto_nombre(rs.getString(4));
                pue.setPto_des(rs.getString(5));
                pue.setPto_codigo1(rs.getString(6));
                pue.setPto_codigo2(rs.getString(7));
                pue.setPto_codigo3(rs.getString(8));
                pue.setCod_serv(rs.getString(9));
                pue.setPto_estado(rs.getString(10));
                listadoPuertos.add(pue);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST PUERTO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoPuertos;
    }

    public void editPuerto(Puerto pue) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_puerto "
                + "set pai_codigo=?, pto_nombre=?, pto_des=?, pto_codigo1=?, pto_codigo2=?, pto_codigo3=?, cod_serv=? "
                + "where pto_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, pue.getPai_codigo());
            pst.setString(2, pue.getPto_nombre());
            pst.setString(3, pue.getPto_des());
            pst.setString(4, pue.getPto_codigo1());
            pst.setString(5, pue.getPto_codigo2());
            pst.setString(6, pue.getPto_codigo3());
            pst.setString(7, pue.getCod_serv());
            pst.setString(8, pue.getPto_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT PUERTO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deletePuerto(Puerto pue) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_puerto set pto_estado = (Case pto_estado when 'A' then 'E' when 'E' then 'A' end) where pto_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, pue.getPto_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE PUERTO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
