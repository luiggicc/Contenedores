/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Sellos;
import conexion.conexion;
import com.delpac.entity.Usuario;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bottago SA
 */
public class SellosDAO implements Serializable {

    public List<Sellos> findAll() throws SQLException {
        conexion con = new conexion();
        List<Sellos> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select i.inv_codigo,i.inv_sello, i.inv_seguridad, l.loc_des, "
                + "case i.inv_estado "
                + "when 'S' then 'En Stock' "
                + "when 'A' then 'Asignado' "
                + "else 'Eliminado' end as inv_estado "
                + "from publico.invsellos i "
                + "inner join publico.mae_localidad l on i.loc_codigo = l.loc_codigo "
                + "where inv_estado = 'S' or inv_estado = 'A'";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setInv_codigo(rs.getInt(1));
                sel.setInv_sello(rs.getString(2));
                sel.setInv_seguridad(rs.getString(3));
                sel.setLoc_des(rs.getString(4));
                sel.setInv_estado(rs.getString(5));
                listadoSellos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLOS FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }

    public List<Sellos> findAllMotivos() throws SQLException {
        conexion con = new conexion();
        List<Sellos> listadoMotivos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select mot_codigo, mot_des, "
                + "case mot_estado "
                + "when 'A' then 'Activo' "
                + "else 'Inactivo' end as mot_estado "
                + "from publico.MotivoSello ";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setMot_codigo(rs.getInt(1));
                sel.setMot_des(rs.getString(2));
                sel.setMot_estado(rs.getString(3));
                listadoMotivos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO MOTIVOS SELLOS ELIMINADOS FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoMotivos;
    }

    public void createSellos(Sellos sellos) throws SQLException {
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.invsellos values(1, ?,?, 1, 'S')";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sellos.getInv_sello());
            pst.setString(2, sellos.getInv_seguridad());
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREATE SELLO: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }

    public void editSellos(Sellos sel) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.invsellos "
                + "set inv_sello=?, inv_seguridad=? "
                + "where inv_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sel.getInv_sello());
            pst.setString(2, sel.getInv_seguridad());
            pst.setInt(3, sel.getInv_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT SELLOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteSellos(Sellos sel) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        PreparedStatement pst2;
        String query = "update publico.invsellos set inv_estado = 'E' where inv_codigo=?";
        String query2 = "insert into publico.SellosEliminados(inv_codigo, mot_codigo) values (?,?)";
        pst = con.getConnection().prepareStatement(query);
        pst2 = con.getConnection().prepareStatement(query2);
        try {
            pst.setInt(1, sel.getInv_codigo());
            pst2.setInt(1, sel.getInv_codigo());
            pst2.setInt(2, sel.getMot_codigo());
            System.out.println(pst);
            System.out.println(pst2);
            pst.executeUpdate();
            pst2.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE SELLO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public int guardarSellos(Sellos sell) throws SQLException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        int repitedFlag = 0;
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.InvSellos(inv_sello, inv_seguridad, loc_codigo, inv_estado) "
                + "values(?,?, 1, 'S')";
        //String query = "insert into prospecto values(?,?,?,?,?,?,?,?,?)";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sell.getInv_sello());
            pst.setString(2, sell.getInv_seguridad().toUpperCase());
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO SELLO CARGA: " + e.getMessage());
            repitedFlag = 1;
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return repitedFlag;
    }
}
