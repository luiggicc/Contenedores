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
public class TransferDAO implements Serializable {

    public List<Sellos> sellosAsignadosbyLocalidad(Integer LocCodigo) {
        List<Sellos> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "";

        try {
            query = " Select i.inv_codigo, i.inv_sello, i.inv_seguridad, l.loc_codigo, l.loc_des "
                    + "from publico.invsellos i "
                    + "inner join publico.mae_localidad l on i.loc_codigo = l.loc_codigo "
                    + "where i.loc_codigo !=?";
            pst = con.getConnection().prepareStatement(query);
            pst.setInt(1, LocCodigo);
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setInv_codigo(rs.getInt(1));
                sel.setInv_sello(rs.getString(2));
                sel.setInv_seguridad(rs.getString(3));
                sel.setLoc_codigo(rs.getInt(4));
                sel.setLoc_des(rs.getString(5));

                lista.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLOS LOCALIDAD: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(TransferDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }

    public void saveResourcesbyLocalidad(List<Sellos> listadoSE, Integer LocCodigo) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        con.getConnection().setAutoCommit(false);
        ResultSet rs = null;
        String query = "";

        try {
            query = "update publico.invsellos set loc_codigo=? where inv_codigo=?";
            pst = con.getConnection().prepareStatement(query);
            for (Sellos se : listadoSE) {
                if (se.getEstado() == true) {
                    pst.setInt(1, LocCodigo);
                    pst.setInt(2, se.getInv_codigo());
                    pst.executeUpdate();
                }

            }
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CAMBIO LOCACION: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }
}
