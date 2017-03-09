/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.AsignaRol;
import conexion.conexion;
import com.delpac.entity.Usuario;
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
public class AsignaRolDAO implements Serializable {

    public List<AsignaRol> rolesAsignadosbyUsuario(Usuario u) {
        List<AsignaRol> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT R.rol_IDROL,R.rol_DESCRIPCION,A.usrol_ESTADO FROM publico.ROL R LEFT JOIN "
                + "(SELECT UR.usrol_CEDULA,UR.usrol_IDROL,UR.usrol_ESTADO FROM publico.USUARIOROL UR "
                + "INNER JOIN publico.USUARIO U ON "
                + "UR.usrol_CEDULA=U.usu_CEDULA WHERE U.usu_CEDULA=?) A ON A.usrol_IDROL=R.rol_IDROL "
                + "where r.rol_ESTADO=1";
        try {
            pst = con.getConnection().prepareStatement(sql);
            pst.setString(1, u.getCedula());
            rs = pst.executeQuery();
            while (rs.next()) {
                AsignaRol ar = new AsignaRol();
                ar.setIdRol(rs.getInt(1));
                ar.setDescripcionRol(rs.getString(2));
                ar.setEstado((rs.getInt(3) != 0));
                lista.add(ar);
            }
        } catch (Exception e) {
            System.out.println("DAO ASIGNAROL: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(AsignaRolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }

    public void saveProfilesbyUsuario(List<AsignaRol> listadoAR, Usuario us, Usuario session) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        con.getConnection().setAutoCommit(false);
        String query = "insert into publico.usuariorol (usrol_cedula, usrol_idrol, usrol_estado) "
                + "values (?,?,?) "
                + "on conflict(usrol_cedula, usrol_idrol) do update "
                + "set usrol_estado = EXCLUDED.usrol_estado";
        try {
            pst = con.getConnection().prepareStatement(query);
            for (AsignaRol ar : listadoAR) {
                pst.setString(1, us.getCedula());
                pst.setInt(2, ar.getIdRol());
                pst.setInt(3, ar.getEstado() == true ? 1 : 0);
                pst.executeUpdate();
            }
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO ASIGNAROL: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }

}
