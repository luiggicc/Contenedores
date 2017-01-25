/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Aduana;
import com.delpac.entity.Pais;
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
public class PaisDAO implements Serializable {

    public List<Pais> findAll() throws SQLException {
        conexion con = new conexion();
        List<Pais> listadoPaises = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select pai_codigo, pai_nombre, pai_des, pai_codigo1, pai_codigo2, pai_codigo3, "
                + "case pai_estado when 'A' then 'Activo' else 'Inactivo' end as pai_estado "
                + "from publico.mae_pais";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Pais pai = new Pais();
                pai.setPai_codigo(rs.getString(1));
                pai.setPai_nombre(rs.getString(2));
                pai.setPai_des(rs.getString(3));
                pai.setPai_codigo1(rs.getString(4));
                pai.setPai_codigo2(rs.getString(5));
                pai.setPai_codigo3(rs.getString(6));
                pai.setPai_estado(rs.getString(7));
                listadoPaises.add(pai);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST PAISES: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoPaises;
    }

    public void editPais(Pais pai) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_pais "
                + "set pai_nombre=?, pai_des=?, pai_codigo1=?, pai_codigo2=?, pai_codigo3=? "
                + "where pai_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, pai.getPai_nombre());
            pst.setString(2, pai.getPai_des());
            pst.setString(3, pai.getPai_codigo1());
            pst.setString(4, pai.getPai_codigo2());
            pst.setString(5, pai.getPai_codigo3());
            pst.setString(6, pai.getPai_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT PAIS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deletePais(Pais pai) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_pais set pai_estado = (Case pai_estado when 'A' then 'E' when 'E' then 'A' end) where pai_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, pai.getPai_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE PAIS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

}
