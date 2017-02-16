/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Aduana;
import com.delpac.entity.Linea;
import conexion.conexion;

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
public class LineaDAO implements Serializable {

    public List<Linea> findAll() throws SQLException {
        conexion con = new conexion();
        List<Linea> listadoLineas = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "select lin_codigo, lin_nombre, lin_des, cae_usuario, cae_clave, ope_codigo, com_codigo, lin_codigo1, lin_codigo2, lin_codigo3, "
                + "val_comision_imp, val_comision_exp, cod_operadora, nom_operadora, car_contenedor, car_suelta, car_consolidada, imp_servicio, "
                + "imp_emision, imp_europa, imp_thc, imp_garantia, ofi_puerto, ofi_propia, ofi_transporte, num_diasdemor1, num_diasdemor2, "
                + "num_diasdemor3, num_diasdemor4, sec_personalizada, cae_biccode, ekc_operada, est_operada, "
                + "case lin_estado when 'A' then 'Activo' else 'Inactivo' end as lin_estado "
                + "from publico.mae_linea "
                + "order by lin_codigo";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Linea lin = new Linea();
                lin.setLin_codigo(rs.getString(1));
                lin.setLin_nombre(rs.getString(2));
                lin.setLin_des(rs.getString(3));
                lin.setCae_usuario(rs.getString(4));
                lin.setCae_clave(rs.getString(5));
                lin.setOpe_codigo(rs.getString(6));
                lin.setCom_codigo(rs.getString(7));
                lin.setLin_codigo1(rs.getString(8));
                lin.setLin_codigo2(rs.getString(9));
                lin.setLin_codigo3(rs.getString(10));
                lin.setVal_comision_imp(rs.getDouble(11));
                lin.setVal_comision_exp(rs.getDouble(12));
                lin.setCod_operadora(rs.getString(13));
                lin.setNom_operadora(rs.getString(14));
                lin.setCar_contenedor(rs.getString(15));
                lin.setCar_suelta(rs.getString(16));
                lin.setCar_consolidada(rs.getString(17));
                lin.setImp_servicio(rs.getString(18));
                lin.setImp_emision(rs.getString(19));
                lin.setImp_europa(rs.getString(20));
                lin.setImp_thc(rs.getString(21));
                lin.setImp_garantia(rs.getString(22));
                lin.setOfi_puerto(rs.getString(23));
                lin.setOfi_propia(rs.getString(24));
                lin.setOfi_transporte(rs.getString(25));
                lin.setNum_diasdemor1(rs.getDouble(26));
                lin.setNum_diasdemor2(rs.getDouble(27));
                lin.setNum_diasdemor3(rs.getDouble(28));
                lin.setNum_diasdemor4(rs.getDouble(29));
                lin.setSec_personalizada(rs.getInt(30));
                lin.setCae_biccode(rs.getString(31));
                lin.setEkc_operada(rs.getString(32));
                lin.setEst_operada(rs.getString(33));
                lin.setLin_estado(rs.getString(34));
                listadoLineas.add(lin);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST LINEAS: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoLineas;
    }
    
    public List<Linea> findAllLinea(){
        conexion con = new conexion();
        List<Linea> listadoLineas = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        
        String query = "select lin_codigo, lin_nombre, " +
                       "case lin_estado when 'A' then 'Activo' else 'Inactivo' end as lin_estado " +
                       "from publico.mae_linea";
        
        try {
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Linea lin = new Linea();
                lin.setLin_codigo(rs.getString(1));
                lin.setLin_nombre(rs.getString(2));
                lin.setLin_estado(rs.getString(3));
                listadoLineas.add(lin);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST LINEAS PREDESCARGA: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(LocalidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoLineas;
    }

    public void editLinea(Linea lin) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_linea "
                + "set lin_nombre=?, lin_des=?, cae_usuario=?, cae_clave=?, ope_codigo=?, com_codigo=?, lin_codigo1=?, lin_codigo2=?, lin_codigo3=?, "
                + "val_comision_imp=?, val_comision_exp=?, cod_operadora=?, nom_operadora=?, car_contenedor=?, car_suelta=?, car_consolidada=?, "
                + "imp_servicio=?, imp_emision=?, imp_europa=?, imp_thc=?, imp_garantia=?, ofi_puerto=?, ofi_propia=?, ofi_transporte=?, "
                + "num_diasdemor1=?, num_diasdemor2=?, num_diasdemor3=?, num_diasdemor4=?, sec_personalizada=?, cae_biccode=?, ekc_operada=?::bit, "
                + "est_operada=?::bit "
                + "where lin_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, lin.getLin_nombre());//check
            pst.setString(2, lin.getLin_des());//check
            pst.setString(3, lin.getCae_usuario());//check
            pst.setString(4, lin.getCae_clave());//check
            pst.setString(5, lin.getOpe_codigo());//check
            pst.setString(6, lin.getCom_codigo());//check
            pst.setString(7, lin.getLin_codigo1());//check
            pst.setString(8, lin.getLin_codigo2());//check
            pst.setString(9, lin.getLin_codigo3());//check
            pst.setDouble(10, lin.getVal_comision_imp());//check
            pst.setDouble(11, lin.getVal_comision_exp());//check
            pst.setString(12, lin.getCod_operadora());//check
            pst.setString(13, lin.getNom_operadora());//check
            pst.setString(14, lin.getCar_contenedor());//check
            pst.setString(15, lin.getCar_suelta());//check
            pst.setString(16, lin.getCar_consolidada());//check
            pst.setString(17, lin.getImp_servicio());//check
            pst.setString(18, lin.getImp_emision());//check
            pst.setString(19, lin.getImp_europa());//check
            pst.setString(20, lin.getImp_thc());//check
            pst.setString(21, lin.getImp_garantia());//check
            pst.setString(22, lin.getOfi_puerto());//check
            pst.setString(23, lin.getOfi_propia());//check
            pst.setString(24, lin.getOfi_transporte());//check
            pst.setDouble(25, lin.getNum_diasdemor1());//check
            pst.setDouble(26, lin.getNum_diasdemor2());//check
            pst.setDouble(27, lin.getNum_diasdemor3());//check
            pst.setDouble(28, lin.getNum_diasdemor4());//check
            pst.setInt(29, lin.getSec_personalizada());//check
            pst.setString(30, lin.getCae_biccode());//check
            pst.setString(31, lin.getEkc_operada());//check
            pst.setString(32, lin.getEst_operada());//check
            pst.setString(33, lin.getLin_codigo());//check
            
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT LINEA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteLinea(Linea lin) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_linea set lin_estado = (Case lin_estado when 'A' then 'E' when 'E' then 'A' end) where lin_codigo=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, lin.getLin_codigo());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE LINEA: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
