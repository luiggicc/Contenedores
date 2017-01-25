/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.AsignaRecurso;
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
public class AsignaRecursoDAO implements Serializable {

    public List<AsignaRecurso> recursosAsignadosbyRol(int idRol) {
        List<AsignaRecurso> lista = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT R.rec_IDRECURSO,R.rec_ITEM_LABEL,A.rerol_ESTADO FROM publico.RECURSO R LEFT JOIN "
                + "(SELECT RR.rerol_IDRECURSO,RR.rerol_ESTADO FROM publico.RECURSOROL RR INNER JOIN publico.ROL R ON "
                + "RR.rerol_IDROL = R.rol_IDROL WHERE R.rol_IDROL = ?) A ON A.rerol_IDRECURSO = R.rec_IDRECURSO ";
        try {
            pst = con.getConnection().prepareStatement(query);
            pst.setInt(1, idRol);
            rs = pst.executeQuery();
            while (rs.next()) {
                AsignaRecurso ar = new AsignaRecurso();
                ar.setIdRecurso(rs.getInt(1));
                ar.setDescripcionRecurso(rs.getString(2));
                ar.setEstado((rs.getInt(3) != 0));
                lista.add(ar);
            }
        } catch (Exception e) {
            System.out.println("DAO ASIGNARECURSO: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(AsignaRecursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }

    public boolean saveResourcesbyProfile(List<AsignaRecurso> listadoAR, int idrol, Usuario u) throws SQLException {
        boolean done = false;
        conexion con = new conexion();
        PreparedStatement pst;
        con.getConnection().setAutoCommit(false);
        String query = "MERGE publico.RECURSOROL AS A "
                + "USING (SELECT ? AS IDROL,? AS IDRECURSO, ? AS ESTADO) AS T "
                + "ON (A.rerol_IDROL=T.IDROL AND A.rerol_IDRECURSO=T.IDRECURSO) "
                + "WHEN MATCHED THEN UPDATE SET A.rerol_ESTADO=T.ESTADO "
                + "WHEN NOT MATCHED THEN INSERT (IDROL,IDRECURSO,ESTADO)VALUES(?,?,?);";
        try {
            pst = con.getConnection().prepareStatement(query);
            for (AsignaRecurso ar : listadoAR) {
                pst.setInt(1, idrol);
                pst.setInt(2, ar.getIdRecurso());
                pst.setInt(3, ar.getEstado() == true ? 1 : 0);
                pst.setInt(4, idrol);
                pst.setInt(5, ar.getIdRecurso());
                pst.setInt(6, ar.getEstado() == true ? 1 : 0);
                pst.executeUpdate();
            }
            con.getConnection().commit();
            done = true;
        } catch (Exception e) {
            System.out.println("DAO ASIGNARECURSO: " + e.getMessage());
            con.getConnection().rollback();
            done = false;
        } finally {
            con.desconectar();
        }
        return done;
    }
}
