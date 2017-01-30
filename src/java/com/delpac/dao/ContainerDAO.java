/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Container;
import conexion.conexion;

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
public class ContainerDAO implements Serializable {

    public List<Container> findAll() throws SQLException {
        conexion con = new conexion();
        List<Container> listadoContainers = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "Select conta.con_codigo, tcon.cod_tipcont, tcon.tcon_nombre, lin.lin_codigo, lin.lin_nombre, conta.con_tamano, conta.con_tar, "
                + "case conta.con_estado when 'A' then 'Activo' else 'Inactivo' end as con_estado "
                + "from publico.mae_container as conta "
                + "inner join publico.mae_tipcontainer as tcon on conta.con_tipcont = tcon.cod_tipcont "
                + "inner join publico.mae_linea as lin on conta.lin_codigo = lin.lin_codigo "
                + "order by conta.con_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Container conta = new Container();
                conta.setCon_codigo(rs.getString(1));
                conta.setCon_tipcont(rs.getString(2));
                conta.setTcon_nombre(rs.getString(3));
                conta.setLin_codigo(rs.getString(4));
                conta.setLin_nombre(rs.getString(5));
                conta.setCon_tamano(rs.getString(6));
                conta.setCon_tar(rs.getDouble(7));
                conta.setCon_estado(rs.getString(8));
                listadoContainers.add(conta);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST CONTAINER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoContainers;
    }

    public void editContainer(Container conta) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_container "
                + "set con_tipcont=?, lin_codigo=?, con_tamano=?, con_tar=? "
                + "where con_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, conta.getCon_tipcont());
            pst.setString(2, conta.getLin_codigo());
            pst.setString(3, conta.getCon_tamano());
            pst.setDouble(4, conta.getCon_tar());
            pst.setString(5, conta.getCon_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT CONTAINER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteContainer(Container conta) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_container set con_estado = (Case con_estado when 'A' then 'E' when 'E' then 'A' end) where con_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, conta.getCon_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE CONTAINER: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
