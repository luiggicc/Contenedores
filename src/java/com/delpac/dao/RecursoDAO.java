/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Usuario;
import com.delpac.entity.Recurso;
import conexion.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bottago SA
 */
public class RecursoDAO {

    public List<Recurso> findAllRecursoByRol(Usuario u) throws SQLException {
        conexion con = new conexion();
        List<Recurso> lista = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "SELECT RE.rec_IDRECURSO,R.rol_IDROL,RE.rec_ITEM_LABEL,RE.rec_SUBITEM_LABEL, "
                + "RE.rec_RUTA,RE.rec_ITEM_ICON,RE.rec_SUBITEM_ICON "
                + "FROM publico.ROL R INNER JOIN publico.RECURSOROL RR ON R.rol_IDROL=RR.rerol_IDROL "
                + "INNER JOIN publico.RECURSO RE ON RR.rerol_IDRECURSO=RE.rec_IDRECURSO "
                + "WHERE R.rol_IDROL=? AND RR.rerol_ESTADO=1";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, u.getIdRol());
            rs = pst.executeQuery();
            while (rs.next()) {
                Recurso recurso = new Recurso();
                recurso.setIdRecurso(rs.getInt(1));
                recurso.setIdRol(rs.getInt(2));
                recurso.setItemLabel(rs.getString(3));
                recurso.setSubItemLabel(rs.getString(4));
                recurso.setRuta(rs.getString(5));
                recurso.setItemIcon(rs.getString(6));
                recurso.setSubItemIcon(rs.getString(7));
                lista.add(recurso);
            }
        } catch (Exception e) {
            System.out.println("DAO RECURSO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return lista;
    }
}
