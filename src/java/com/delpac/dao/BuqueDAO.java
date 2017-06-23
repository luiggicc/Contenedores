/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Buque;
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
public class BuqueDAO implements Serializable {

    public List<Buque> findAll() throws SQLException {
        conexion con = new conexion();
        List<Buque> listadoBuques = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select buq.buq_codigo, tbu.tbu_codigo, tbu.tbu_nombre, pai.pai_codigo, pai.pai_nombre, pue.pto_codigo, pue.pto_nombre, "
                + "lin.lin_codigo, lin.lin_nombre, tra.tra_codigo, tra.tra_nombre, buq.buq_nombre, buq.buq_des, buq.num_trb, "
                + "buq.num_eslora, buq.max_tons, buq.reg_lloyd, buq.buq_codigo1, buq.codigo_omi, "
                + "case buq.buq_estado when 'A' then 'Activo' else 'Inactivo' end as buq_estado "
                + "from publico.mae_buque as buq "
                + "left outer join publico.mae_tipbuque as tbu on buq.tbu_codigo = tbu.tbu_codigo "
                + "left outer join publico.mae_pais as pai on buq.pai_codigo = pai.pai_codigo "
                + "left outer join publico.mae_puerto as pue on buq.pto_codigo = pue.pto_codigo "
                + "left outer join publico.mae_linea as lin on buq.lin_codigo = lin.lin_codigo "
                + "left outer join publico.mae_trafico as tra on buq.tra_codigo = tra.tra_codigo "
                + "order by case when pai.pai_nombre like 'E%' then 1 "
                + "end";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Buque buq = new Buque();
                buq.setBuq_codigo(rs.getString(1));
                buq.setTbu_codigo(rs.getString(2));
                buq.setTbu_nombre(rs.getString(3));
                buq.setPai_codigo(rs.getString(4));
                buq.setPai_nombre(rs.getString(5));
                buq.setPto_codigo(rs.getString(6));
                buq.setPto_nombre(rs.getString(7));
                buq.setLin_codigo(rs.getString(8));
                buq.setLin_nombre(rs.getString(9));
                buq.setTra_codigo(rs.getString(10));
                buq.setTra_nombre(rs.getString(11));
                buq.setBuq_nombre(rs.getString(12));
                buq.setBuq_des(rs.getString(13));
                buq.setNum_trb(rs.getInt(14));
                buq.setNum_eslora(rs.getInt(15));
                buq.setMax_tons(rs.getInt(16));
                buq.setReg_lloyd(rs.getString(17));
                buq.setBuq_codigo1(rs.getString(18));
                buq.setCodigo_omi(rs.getString(19));
                buq.setBuq_estado(rs.getString(20));
                listadoBuques.add(buq);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST BUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoBuques;
    }

    public void editBuque(Buque buq) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_buque "
                + "set tbu_codigo=?, pai_codigo=?, pto_codigo=?, lin_codigo=?, tra_codigo=?, buq_nombre=?, num_trb=?, "
                + "num_eslora=?, max_tons=?, reg_lloyd=? "
                + "where buq_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, buq.getTbu_codigo());
            pst.setString(2, buq.getPai_codigo());
            pst.setString(3, buq.getPto_codigo());
            pst.setString(4, buq.getLin_codigo());
            pst.setString(5, buq.getTra_codigo());
            pst.setString(6, buq.getBuq_nombre());
            pst.setInt(7, buq.getNum_trb());
            pst.setInt(8, buq.getNum_eslora());
            pst.setInt(9, buq.getMax_tons());
            pst.setString(10, buq.getReg_lloyd());
            pst.setString(11, buq.getBuq_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT BUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteBuque(Buque buq) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_buque set buq_estado = (Case buq_estado when 'A' then 'E' when 'E' then 'A' end) where buq_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, buq.getBuq_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE BUQUE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
