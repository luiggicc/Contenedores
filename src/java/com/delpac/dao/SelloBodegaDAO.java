/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.SelloBodega;
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

/**
 *
 * @author Bottago SA
 */
public class SelloBodegaDAO implements Serializable {

    public List<SelloBodega> ConsultaSelloBodega() throws SQLException {
        conexion con = new conexion();
        List<SelloBodega> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select sbo_numero, estado from publico.sellobodega where estado = 'A' order by sbo_numero desc";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                SelloBodega sbo = new SelloBodega();
                sbo.setSbo_numero(rs.getLong(1));
                sbo.setSbo_estado(rs.getString(2));
                listadoSellos.add(sbo);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLO BODEGA DAO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }

    public List<SelloBodega> ConsultaSelloBodegLog() throws SQLException {
        conexion con = new conexion();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<SelloBodega> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select sbr_desde, sbr_hasta, sbr_fecha, sbr_estado "
                + "from publico.sellobodegarango "
                + "order by sbr_fecha desc";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                SelloBodega sbr = new SelloBodega();
                sbr.setSbr_desde(rs.getLong(1));
                sbr.setSbr_hasta(rs.getLong(2));
                sbr.setSbr_fecha(format.parse(format.format(new Date(rs.getTimestamp(3).getTime()))));
                sbr.setSbr_estado(rs.getString(4));
                listadoSellos.add(sbr);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLO BODEGA RANGO DAO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }
    
    public void createSelloBodega(SelloBodega sbo) throws SQLException {
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "select publico.sellosenbodega(?,?)";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setLong(1, sbo.getSbr_desde());
            pst.setLong(2, sbo.getSbr_hasta());
            pst.executeQuery();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREATE RANGO SELLOS: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }
}
