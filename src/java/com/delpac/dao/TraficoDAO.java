/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Trafico;
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
public class TraficoDAO implements Serializable {

    public List<Trafico> findAll() throws SQLException {
        conexion con = new conexion();
        List<Trafico> listadoTraficos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select tra_codigo, tra_nombre, tra_des, tra_codigo1, tra_codigo2, tra_codigo3, "
                + "case tra_estado when 'A' then 'Activo' else 'Inactivo' end as tra_estado "
                + "from publico.mae_trafico "
                + "order by tra_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Trafico tra = new Trafico();
                tra.setTra_codigo(rs.getString(1));
                tra.setTra_nombre(rs.getString(2));
                tra.setTra_des(rs.getString(3));
                tra.setTra_codigo1(rs.getString(4));
                tra.setTra_codigo2(rs.getString(5));
                tra.setTra_codigo3(rs.getString(6));
                tra.setTra_estado(rs.getString(7));
                listadoTraficos.add(tra);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST TRAFICOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoTraficos;
    }

    public void editTrafico(Trafico tra) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_trafico "
                + "set tra_nombre=?, tra_des=?, tra_codigo1=?, tra_codigo2=?, tra_codigo3=? "
                + "where tra_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tra.getTra_nombre());
            pst.setString(2, tra.getTra_des());
            pst.setString(3, tra.getTra_codigo1());
            pst.setString(4, tra.getTra_codigo2());
            pst.setString(5, tra.getTra_codigo3());
            pst.setString(6, tra.getTra_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT TRAFICO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteTrafico(Trafico tra) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_trafico set tra_estado = (Case tra_estado when 'A' then 'E' when 'E' then 'A' end) where tra_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, tra.getTra_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE TRAFICO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

}
