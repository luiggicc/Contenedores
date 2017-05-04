/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Cliente;
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
public class ClienteDAO implements Serializable {

    public List<Cliente> findAll() throws SQLException {
        conexion con = new conexion();
        List<Cliente> listadoClientes = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "Select cli.cia_codigo, tpi.tpi_codigo, tpi.tpi_nombre, cli.ruc_numero, cli.cia_nombre, pai.pai_codigo, pai.pai_nombre, cli.tip_shi, "
                + "cli.tip_con, cli.tip_emb, cli.tip_pro, cli.tip_ope, cli.cia_direccion, cli.nom_ciudad, cli.telefono1, "
                + "cli.telefono2, cli.cia_fax, cli.cia_mail, cli.cia_obs, "
                + "case cli.cia_estado when 'A' then 'Activo' else 'Inactivo' end as cia_estado "
                + "from publico.mae_clientes as cli "
                + "inner join publico.mae_tipidentifica as tpi on cli.cod_tipidentifica = tpi.tpi_codigo "
                + "inner join publico.mae_pais as pai on cli.pai_codigo = pai.pai_codigo "
                + "order by cli.cia_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setCia_codigo(rs.getString(1));
                cli.setCod_tipidentifica(rs.getString(2));
                cli.setTpi_nombre(rs.getString(3));
                cli.setRuc_numero(rs.getString(4));
                cli.setCia_nombre(rs.getString(5));
                cli.setPai_codigo(rs.getString(6));
                cli.setPai_nombre(rs.getString(7));
                cli.setTip_shi(rs.getString(8));
                cli.setTip_con(rs.getString(9));
                cli.setTip_emb(rs.getString(10));
                cli.setTip_pro(rs.getString(11));
                cli.setTip_ope(rs.getString(12));
                cli.setCia_direccion(rs.getString(13));
                cli.setNom_ciudad(rs.getString(14));
                cli.setTelefono1(rs.getString(15));
                cli.setTelefono2(rs.getString(16));
                cli.setCia_fax(rs.getString(17));
                cli.setCia_mail(rs.getString(18));
                cli.setCia_obs(rs.getString(19));
                cli.setCia_estado(rs.getString(20));
                listadoClientes.add(cli);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST CLIENTE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoClientes;
    }
    
    public void createCliente(Cliente cli) throws SQLException {
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.mae_clientes(cod_tipidentifica, cia_codigo, ruc_numero, cia_nombre, pai_codigo, "
                + "cia_direccion, nom_ciudad, telefono1, telefono2, cia_fax, cia_mail, cia_obs, cia_estado)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A')";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, cli.getCod_tipidentifica());
            pst.setString(2, cli.getCia_codigo());
            pst.setString(3, cli.getRuc_numero());
            pst.setString(4, cli.getCia_nombre());
            pst.setString(5, cli.getPai_codigo());
            pst.setString(6, cli.getCia_direccion());
            pst.setString(7, cli.getNom_ciudad());
            pst.setString(8, cli.getTelefono1());
            pst.setString(9, cli.getTelefono2());
            pst.setString(10, cli.getCia_fax());
            pst.setString(11, cli.getCia_mail());
            pst.setString(12, cli.getCia_obs());
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREATE CLIENTE: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }

    public void editCliente(Cliente cli) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_clientes "
                + "set cod_tipidentifica=?, ruc_numero=?, cia_nombre=?, pai_codigo=?, tip_shi=?, tip_con=?, tip_emb=?, tip_pro=?, tip_ope=?, "
                + "cia_direccion=?, nom_ciudad=?, telefono1=?, telefono2=?, cia_fax=?, cia_mail=?, cia_obs=? "
                + "where cia_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, cli.getCod_tipidentifica());
            pst.setString(2, cli.getRuc_numero());
            pst.setString(3, cli.getCia_nombre());
            pst.setString(4, cli.getPai_codigo());
            pst.setString(5, cli.getTip_shi());
            pst.setString(6, cli.getTip_con());
            pst.setString(7, cli.getTip_emb());
            pst.setString(8, cli.getTip_pro());
            pst.setString(9, cli.getTip_ope());
            pst.setString(10, cli.getCia_direccion());
            pst.setString(11, cli.getNom_ciudad());
            pst.setString(12, cli.getTelefono1());
            pst.setString(13, cli.getTelefono2());
            pst.setString(14, cli.getCia_fax());
            pst.setString(15, cli.getCia_mail());
            pst.setString(16, cli.getCia_obs());
            pst.setString(17, cli.getCia_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT CLIENTE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteCliente(Cliente cli) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_clientes set cia_estado = (Case cia_estado when 'A' then 'E' when 'E' then 'A' end) where cia_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, cli.getCia_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE CLIENTE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
