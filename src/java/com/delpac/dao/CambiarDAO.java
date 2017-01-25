/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;
import com.delpac.entity.Usuario;
import com.delpac.entity.CambiarContrasena;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexion.conexion;
import java.sql.ResultSet;

/**
 *
 * @author Bottago SA
 */
public class CambiarDAO implements Serializable{
    public boolean editCambiar(CambiarContrasena ca, Usuario u) throws SQLException {
        boolean flag;
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        ResultSet rs;
        PreparedStatement pst;
        String sqlVerifica = "select * from publico.usuario where usu_cedula=? and usu_clave=?";
        pst = con.getConnection().prepareStatement(sqlVerifica, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        try {
            pst.setString(1, u.getCedula());
            pst.setString(2, ca.getActualPassword());
            rs = pst.executeQuery();
            rs.last();
            int numReg = rs.getRow();
            if (numReg <= 0) {
                flag = false;
            } else {
                String sqlModifica = "update publico.usuario set usu_clave=?, usu_estadoclave=?"
                        + " where usu_cedula=? ";
                pst = con.getConnection().prepareStatement(sqlModifica);
                pst.setString(1, ca.getConfirmpassword());
                pst.setInt(2, 0);
                pst.setString(3, u.getCedula());

                pst.executeUpdate();
                con.getConnection().commit();
                flag = true;
            }

        } catch (Exception e) {
            System.out.println("DAO REESTABLECER: " + e.getMessage());
            con.getConnection().rollback();
            flag = false;
        } finally {
            con.desconectar();
        }
        return flag;
    }
}
