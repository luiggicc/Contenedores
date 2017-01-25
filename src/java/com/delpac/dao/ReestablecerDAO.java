/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.ReestablecerContra;
import conexion.conexion;
import com.delpac.entity.Usuario;

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
public class ReestablecerDAO implements Serializable {

    public boolean editContra(ReestablecerContra r, Usuario u) throws SQLException {
        boolean done = false;
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.usuario set usu_clave=?,usu_estadoclave=?"
                + " where usu_cedula=? ";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, r.getPassword());
            pst.setInt(2, 1);
            pst.setString(3, r.getUsu_cedula());
            pst.executeUpdate();
            done = true;
        } catch (Exception e) {
            System.out.println("DAO EDIT REESTABLECER: " + e.getMessage());
            done = false;
        } finally {
            con.desconectar();
        }
        return done;
    }

    public List<ReestablecerContra> findAll() throws SQLException {
        conexion con = new conexion();
        List<ReestablecerContra> listadoUsuarios = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select u.usu_cedula,u.usu_nombres,u.usu_apellidos,u.usu_usuario,r.rol_IDROL,r.rol_DESCRIPCION,u.usu_estadoclave,u.usu_activo "
                + "from publico.USUARIO u "
                + "inner join publico.USUARIOROL ru on u.usu_CEDULA=ru.usrol_cedula "
                + "inner join publico.ROL r on ru.usrol_IDROL=r.rol_IDROL";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                ReestablecerContra r = new ReestablecerContra();
                r.setUsu_cedula(rs.getString(1));
                r.setUsu_nombres(rs.getString(2));
                r.setUsu_apellidos(rs.getString(3));
                r.setUsu_usuario(rs.getString(4));
                r.setRol(rs.getInt(5));
                r.setRol_descripcion(rs.getString(6));
                listadoUsuarios.add(r);
            }
        } catch (Exception e) {
            System.out.println("DAO USUARIO REESTABLECER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoUsuarios;
    }
}
