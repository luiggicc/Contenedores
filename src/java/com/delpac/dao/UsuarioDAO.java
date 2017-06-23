/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Usuario;
import conexion.conexion;

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
public class UsuarioDAO implements Serializable {

    public boolean createUsuario(Usuario us, Usuario session) throws SQLException {
        boolean done = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String sql = "insert into publico.usuario(usu_cedula, usu_nombres, usu_apellidos, usu_usuario, usu_clave, usu_estadoclave, usu_activo, usu_fecha_creac, usu_fecha_modif) "
                + " values(?,?,?,?,?,?,?,current_timestamp,current_timestamp)";
        pst = con.getConnection().prepareStatement(sql);
        try {
            pst.setString(1, us.getCedula());
            pst.setString(2, us.getNombres().toUpperCase());
            pst.setString(3, us.getApellidos().toUpperCase());
            pst.setString(4, us.getLogin().toLowerCase());
            pst.setString(5, us.getPassword());
            pst.setInt(6, 0); //valor que representa la clave debe ser cambiada.
            pst.setInt(7, 1); //valor que representa el usuario se puedo autenticar en el sistema.
            pst.executeUpdate();

            con.getConnection().commit();
            done = true;
        } catch (Exception e) {
            System.out.println("DAO USUARIO: " + e.getMessage());
            con.getConnection().rollback();
            done = false;
        } finally {
            con.desconectar();
        }
        return done;
    }

    public boolean editUsuario(Usuario us, Usuario session) throws SQLException {
        boolean done = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "update publico.usuario set usu_nombres=?,usu_apellidos=?,usu_usuario=?,usu_fecha_modif=current_timestamp "
                + " where usu_cedula=? ";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, us.getNombres().toUpperCase());
            pst.setString(2, us.getApellidos().toUpperCase());
            pst.setString(3, us.getLogin().toLowerCase());
//            pst.setString(4, fmt.format(new Date()));
            pst.setString(4, us.getCedula());

            pst.executeUpdate();
            con.getConnection().commit();
            done = true;
        } catch (Exception e) {
            System.out.println("DAO USUARIO: " + e.getMessage());
            con.getConnection().rollback();
            done = false;
        } finally {
            con.desconectar();
        }
        return done;
    }

    public boolean deleteUsuario(Usuario us, Usuario session) throws SQLException {
        boolean done = false;
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "update publico.usuario set usu_activo=? where usu_cedula=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, 0);
            pst.setString(2, us.getCedula());
            pst.executeUpdate();
            con.getConnection().commit();
            done = true;
        } catch (Exception e) {
            System.out.println("DAO USUARIO: " + e.getMessage());
            con.getConnection().rollback();
            done = false;
        } finally {
            con.desconectar();
        }
        return done;
    }

    public List<Usuario> findAll() {
        conexion con = new conexion();
        List<Usuario> listadoUsuarios = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select u.usu_cedula,u.usu_nombres,u.usu_apellidos,u.usu_usuario,r.rol_IDROL,r.rol_DESCRIPCION "
                + "from publico.USUARIO U left join publico.USUARIOROL RU on U.usu_CEDULA=RU.usrol_CEDULA "
                + "LEFT JOIN publico.ROL R ON R.rol_IDROL=RU.usrol_IDROL "
                + "WHERE U.usu_ACTIVO=? AND RU.usrol_ESTADO=1 OR RU.usrol_ESTADO IS NULL";
        try {
            pst = con.getConnection().prepareStatement(query);
            pst.setInt(1, 1); //todos los usuario que pueden autenticarse en el sistema.
            rs = pst.executeQuery();
            while (rs.next()) {
                Usuario us = new Usuario();
                us.setCedula(rs.getString(1));
                us.setNombres(rs.getString(2));
                us.setApellidos(rs.getString(3));
                us.setLogin(rs.getString(4));
                us.setIdRol(rs.getInt(5));
                us.setDescripcionRol(rs.getString(6));
                listadoUsuarios.add(us);
            }
        } catch (Exception e) {
            System.out.println("DAO USUARIO: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoUsuarios;
    }

    public Usuario loginAction(String login, String contrasena, Usuario u, int idrol) throws SQLException {
        conexion con = new conexion();
        Usuario us = new Usuario();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select u.usu_CEDULA,u.usu_NOMBRES,u.usu_APELLIDOS, "
                + "u.usu_usuario,r.rol_IDROL, "
                + "r.rol_DESCRIPCION,u.usu_ESTADOCLAVE, "
                + "u.usu_ACTIVO "
                + "from publico.usuario u "
                + "inner join publico.USUARIOROL ur on ur.usrol_CEDULA=u.usu_CEDULA "
                + "inner join publico.ROL r on r.rol_IDROL=ur.usrol_IDROL "
                + "where u.usu_USUARIO=? and u.usu_CLAVE=? and r.rol_idrol=? and u.usu_ACTIVO=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, login);
            pst.setString(2, contrasena);
            pst.setInt(3, idrol);
            pst.setInt(4, 1);
            rs = pst.executeQuery();
            while (rs.next()) {
                us.setCedula(rs.getString(1));
                us.setNombres(rs.getString(2));
                us.setApellidos(rs.getString(3));
                us.setLogin(rs.getString(4));
                us.setIdRol(rs.getInt(5));
                us.setDescripcionRol(rs.getString(6));
                us.setEstadoClave(rs.getInt(7));
                us.setActivo(rs.getInt(8));
                return us;
            }
        } catch (Exception e) {
            System.out.println("DAO USUARIO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return null;
    }

}
