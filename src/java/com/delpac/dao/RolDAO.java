/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;
import com.delpac.entity.Rol;
import com.delpac.entity.Usuario;
import conexion.conexion;
import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bottago SA
 */
public class RolDAO implements Serializable {
    public List<Rol> findAll() {
        conexion con = new conexion();
        List<Rol> listadoRoles = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select * from publico.rol where rol_estado = 1";
        try {
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt(1));
                rol.setDescripcion(rs.getString(2));
                listadoRoles.add(rol);
            }
        } catch (Exception e) {
            System.out.println("DAO ROL: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoRoles;
    }
    
    public boolean editRol(Rol rol, Usuario u) throws SQLException {
        boolean done=false;
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "update publico.rol set rol_descripcion=?"
                + " where rol_idrol=? ";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, rol.getDescripcion().toUpperCase());
            pst.setInt(2, rol.getIdRol());
            pst.executeUpdate();
            con.getConnection().commit();
            done=true;
        } catch (Exception e) {
            System.out.println("DAO ROL: " + e.getMessage());
            con.getConnection().rollback();
            done=false;
        } finally {
            con.desconectar();
        }
        return done;
    }
    
    public boolean deleteRol(Rol rol, Usuario u) throws SQLException {
        boolean done=false;
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "update publico.rol set rol_estado = 0 where rol_idrol=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, rol.getIdRol());
            pst.executeUpdate();
            con.getConnection().commit();
            done=true;
        } catch (Exception e) {
            System.out.println("DAO ROL: " + e.getMessage());
            con.getConnection().rollback();
            done=false;
        } finally {
            con.desconectar();
        }
        return done;
    }
    
    public boolean createRol(Rol rol, Usuario u) throws SQLException {
        boolean done=false;
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.rol(rol_descripcion, rol_estado) values(?,1)";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, rol.getDescripcion().toUpperCase());
            pst.executeUpdate();
            con.getConnection().commit();
            done=true;
        } catch (Exception e) {
            System.out.println("DAO ROL: " + e.getMessage());
            con.getConnection().rollback();
            done=false;
        } finally {
            con.desconectar();
        }
        return done;
    }
}
