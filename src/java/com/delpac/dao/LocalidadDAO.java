/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Localidad;
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
public class LocalidadDAO implements Serializable{
    public List<Localidad> findAll() throws SQLException {
        conexion con = new conexion();
        List<Localidad> listadoLocalidades = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        
        String query = "select loc_codigo, loc_des, loc_sender, loc_tipo, " +
                       "case loc_estado when 'A' then 'Activo' else 'Inactivo' end as loc_estado " +
                       "from publico.mae_localidad";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Localidad loc = new Localidad();
                loc.setLoc_codigo(rs.getInt(1));
                loc.setLoc_des(rs.getString(2));
                loc.setLoc_sender(rs.getString(3));
                loc.setLoc_tipo(rs.getString(4));
                loc.setLoc_estado(rs.getString(5));
                listadoLocalidades.add(loc);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST LOCALIDADES: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoLocalidades;
    }
    
    public List<Localidad> findAllLocalTransfer(){
        conexion con = new conexion();
        List<Localidad> listadoLocalidades = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        
        String query = "select loc_codigo, loc_des, " +
                       "case loc_estado when 'A' then 'Activo' else 'Inactivo' end as loc_estado " +
                       "from publico.mae_localidad";
        
        try {
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Localidad loc = new Localidad();
                loc.setLoc_codigo(rs.getInt(1));
                loc.setLoc_des(rs.getString(2));
                loc.setLoc_estado(rs.getString(3));
                listadoLocalidades.add(loc);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST LOCALIDADES TRANSFER: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(LocalidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoLocalidades;
    }
    
    public void editLocalidad(Localidad loc) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_localidad " +
                       "set loc_des " +
                       "where loc_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, loc.getLoc_des());
            pst.setInt(2, loc.getLoc_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT LOCALIDAD: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
    
    public void deleteLocalidad(Localidad loc) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_localidad set loc_estado = (Case loc_estado when 'A' then 'E' when 'E' then 'A' end) where loc_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, loc.getLoc_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE LOCALIDAD: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
