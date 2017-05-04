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

/**
 *
 * @author Bottago SA
 */
public class SellosDAO implements Serializable {

    public List<Sellos> findAll() throws SQLException {
        conexion con = new conexion();
        List<Sellos> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select i.inv_codigo,i.inv_sello, i.inv_seguridad, l.loc_des, "
                + "case i.inv_estado "
                + "when 'S' then 'En Stock' "
                + "when 'A' then 'Asignado' "
                + "end as inv_estado "
                + "from publico.invsellos i "
                + "inner join publico.mae_localidad l on i.loc_codigo = l.loc_codigo "
                + "where inv_estado = 'S' or inv_estado = 'A'";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setInv_codigo(rs.getInt(1));
                sel.setInv_sello(rs.getString(2));
                sel.setInv_seguridad(rs.getString(3));
                sel.setLoc_des(rs.getString(4));
                sel.setInv_estado(rs.getString(5));
                listadoSellos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLOS FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }

    public List<Sellos> findAllStock() throws SQLException {
        conexion con = new conexion();
        List<Sellos> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select i.inv_codigo,i.inv_sello, i.inv_seguridad, l.loc_des, "
                + "case i.inv_estado "
                + "when 'S' then 'En Stock' "
                + "when 'A' then 'Asignado' "
                + "end as inv_estado "
                + "from publico.invsellos i "
                + "inner join publico.mae_localidad l on i.loc_codigo = l.loc_codigo "
                + "where inv_estado = 'S'";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setInv_codigo(rs.getInt(1));
                sel.setInv_sello(rs.getString(2));
                sel.setInv_seguridad(rs.getString(3));
                sel.setLoc_des(rs.getString(4));
                sel.setInv_estado(rs.getString(5));
                listadoSellos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLOS FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }

    public List<Sellos> Eliminados() throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        conexion con = new conexion();
        List<Sellos> listadoSellos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select ise.inv_sello, ise.inv_seguridad, ms.mot_des, lo.loc_des, se.seli_fecha "
                + "from publico.selloseliminados se "
                + "left join publico.invsellos ise on se.inv_codigo = ise.inv_codigo "
                + "left join publico.motivosello ms on se.mot_codigo = ms.mot_codigo "
                + "left join publico.mae_localidad lo on ise.inv_codigo = lo.loc_codigo "
                + "where ise.inv_sello is not null "
                + "order by seli_fecha desc";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setInv_sello(rs.getString(1));
                sel.setInv_seguridad(rs.getString(2));
                sel.setMot_des(rs.getString(3));
                sel.setLoc_des(rs.getString(4));
                sel.setSeli_fecha(format.parse(format.format(new Date(rs.getTimestamp(5).getTime()))));
                listadoSellos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO SELLOS LISTADO ELIMINADOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoSellos;
    }

    public List<Sellos> findAllMotivos() throws SQLException {
        conexion con = new conexion();
        List<Sellos> listadoMotivos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = " Select mot_codigo, mot_des, "
                + "case mot_estado "
                + "when 'A' then 'Activo' "
                + "else 'Inactivo' end as mot_estado "
                + "from publico.MotivoSello ";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Sellos sel = new Sellos();
                sel.setMot_codigo(rs.getInt(1));
                sel.setMot_des(rs.getString(2));
                sel.setMot_estado(rs.getString(3));
                listadoMotivos.add(sel);
            }
        } catch (Exception e) {
            System.out.println("DAO MOTIVOS SELLOS ELIMINADOS FINDALL: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoMotivos;
    }

    public void createSellos(Sellos sellos) throws SQLException {
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.invsellos(inv_sello, inv_seguridad, loc_codigo, inv_estado, inv_fechacrec)"
                + " values(?,?, 1, 'S',to_char(current_timestamp, 'YYYY-MM-DD HH24:MI:SS')::timestamp)";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sellos.getInv_sello());
            pst.setString(2, sellos.getInv_seguridad());
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREATE SELLO: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }

    public void editSellos(Sellos sel) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.invsellos "
                + "set inv_sello=?, inv_seguridad=?, inv_fechamod = to_char(current_timestamp, 'YYYY-MM-DD HH24:MI:SS')::timestamp "
                + "where inv_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sel.getInv_sello());
            pst.setString(2, sel.getInv_seguridad());
            pst.setInt(3, sel.getInv_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT SELLOS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteSellos(Sellos sel) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        PreparedStatement pst2;
        PreparedStatement pst3;
        String query = "update publico.invsellos set inv_estado = 'E' where inv_codigo=?";
        String query2 = "insert into publico.SellosEliminados(inv_codigo, mot_codigo, seli_fecha) values (?,?, to_char(current_timestamp, 'YYYY-MM-DD HH24:MI:SS')::timestamp)";
        String query3 = "update publico.sellobodega set estado = 'E' where sbo_numero=?";
        pst = con.getConnection().prepareStatement(query);
        pst2 = con.getConnection().prepareStatement(query2);
        pst3 = con.getConnection().prepareStatement(query3);
        try {
            pst.setInt(1, sel.getInv_codigo());
            pst2.setInt(1, sel.getInv_codigo());
            pst2.setInt(2, sel.getMot_codigo());
            pst3.setLong(1, Long.valueOf(sel.getInv_codigo()));
            pst.executeUpdate();
            pst2.executeUpdate();
            pst3.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE SELLO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public int guardarSellos(Sellos sell) throws SQLException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        int repitedFlag = 0;
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.InvSellos(inv_sello, inv_seguridad, loc_codigo, inv_estado, inv_fechacrec, inv_fechamod) "
                + "values(?,?, 1, 'S', to_char(current_timestamp, 'YYYY-MM-DD HH24:MI:SS')::timestamp, null)";
        //String query = "insert into prospecto values(?,?,?,?,?,?,?,?,?)";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, sell.getInv_sello());
            pst.setString(2, sell.getInv_seguridad().toUpperCase());
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO SELLO CARGA: " + e.getMessage());
            repitedFlag = 1;
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return repitedFlag;
    }
}
