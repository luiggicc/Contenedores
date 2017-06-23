/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Itinerario;
import com.delpac.entity.Usuario;
import conexion.conexion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Bottago SA
 */
public class ItinerarioDAO implements Serializable {

    public List<Itinerario> findAll() throws SQLException {
        conexion con = new conexion();
        List<Itinerario> listadoItinerarios = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "Select iti.ids_itinerario, cli.cia_codigo, cli.cia_nombre, lin.lin_codigo, lin.lin_nombre, buq.buq_codigo, "
                + "buq.buq_nombre, pue.pto_codigo as cod_ptoarribo, pue.pto_nombre as nom_ptoarribo, "
                + "puer.pto_codigo as cod_ptoinicio, puer.pto_nombre as nom_ptoinicio, puert.pto_codigo as cod_ptoeta, "
                + "puert.pto_nombre as nom_ptoeta, iti.cod_ptofisico, tra.tra_codigo, tra.tra_nombre, adu.adu_codigo, "
                + "adu.adu_nombre, tman.tman_codigo as cod_tipmanifiesto, tman.tman_nombre as nom_tipmanifiesto, "
                + "tmani.tman_codigo as cod_tipmanifiesto_exp, tmani.tman_nombre as nom_tipmanifiesto_exp, "
                + "iti.num_viaje, iti.nom_capitan, iti.cod_manifiesto, iti.cod_manifiesto_exp, iti.num_anio, "
                + "iti.obs_itinerario, iti.nom_muelle, iti.fec_arribo, iti.hor_arribo, iti.fec_platica, iti.hor_platica, "
                + "iti.fec_muelle, iti.hor_muelle, iti.fec_iniope, iti.hor_iniope, iti.fec_finope, iti.hor_finope, "
                + "iti.fec_cutdry, iti.hor_cutdry, iti.fec_cutssr, iti.hor_cutssr, iti.fec_zarpe, iti.hor_zarpe, "
                + "iti.fec_ing, iti.usr_ing, iti.fec_mod, iti.usr_mod, iti.dsp_itinerario, iti.dsp_itinerario_imp, "
                + "iti.num_viaje_imp, iti.dsp_itinerario_exp, iti.num_viaje_exp, "
                + "case iti.est_itinerario when 'A' then 'Activo' else 'Inactivo' end as est_itinerario "
                + "from publico.mae_itinerario as iti "
                + "left outer join publico.mae_clientes as cli on iti.cod_cia = cli.cia_codigo "
                + "left outer join publico.mae_linea as lin on iti.cod_linea = lin.lin_codigo "
                + "left outer join publico.mae_buque as buq on iti.cod_buque = buq.buq_codigo "
                + "left outer join publico.mae_puerto as pue on iti.cod_ptoarribo = pue.pto_codigo "
                + "left outer join publico.mae_puerto as puer on iti.cod_ptoinicio = puer.pto_codigo "
                + "left outer join publico.mae_puerto as puert on iti.cod_ptoeta = puert.pto_codigo "
                + "left outer join publico.mae_trafico as tra on iti.cod_trafico = tra.tra_codigo "
                + "left outer join publico.mae_aduana as adu on iti.cod_aduana = adu.adu_codigo "
                + "left outer join publico.mae_tipmanifiesto as tman on iti.cod_tipmanifiesto = tman.tman_codigo "
                + "left outer join publico.mae_tipmanifiesto as tmani on iti.cod_tipmanifiesto_exp = tmani.tman_codigo "
                + "order by iti.ids_itinerario";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Itinerario iti = new Itinerario();
                iti.setIds_itinerario(rs.getInt(1));
                iti.setCia_codigo(rs.getString(2));
                iti.setCia_nombre(rs.getString(3));
                iti.setLin_codigo(rs.getString(4));
                iti.setLin_nombre(rs.getString(5));
                iti.setBuq_codigo(rs.getString(6));
                iti.setBuq_nombre(rs.getString(7));
                iti.setCod_ptoarribo(rs.getString(8));
                iti.setNom_ptoarribo(rs.getString(9));
                iti.setCod_ptoinicio(rs.getString(10));
                iti.setNom_ptoinicio(rs.getString(11));
                iti.setCod_ptoeta(rs.getString(12));
                iti.setNom_ptoeta(rs.getString(13));
                iti.setCod_ptofisico(rs.getString(14));
                iti.setTra_codigo(rs.getString(15));
                iti.setTra_nombre(rs.getString(16));
                iti.setAdu_codigo(rs.getString(17));
                iti.setAdu_nombre(rs.getString(18));
                iti.setCod_tipmanifiesto(rs.getString(19));
                iti.setNom_tipmanifiesto(rs.getString(20));
                iti.setCod_tipmanifiesto_exp(rs.getString(21));
                iti.setNom_tipmanifiesto_exp(rs.getString(22));
                iti.setNum_viaje(rs.getString(23));
                iti.setNom_capitan(rs.getString(24));
                iti.setCod_manifiesto(rs.getString(25));
                iti.setCod_manifiesto_exp(rs.getString(26));
                iti.setNum_anio(rs.getInt(27));
                iti.setObs_itinerario(rs.getString(28));
                iti.setNom_muelle(rs.getString(29));
                iti.setFec_arribo(rs.getDate(30));
                iti.setHor_arribo(rs.getString(31));
                iti.setFec_platica(rs.getDate(32));
                iti.setHor_platica(rs.getString(33));
                iti.setFec_muelle(rs.getDate(34));
                iti.setHor_muelle(rs.getString(35));
                iti.setFec_iniope(rs.getDate(36));
                iti.setHor_iniope(rs.getString(37));
                iti.setFec_finope(rs.getDate(38));
                iti.setHor_finope(rs.getString(39));
                iti.setFec_cutdry(rs.getDate(40));
                iti.setHor_cutdry(rs.getString(41));
                iti.setFec_cutssr(rs.getDate(42));
                iti.setHor_cutssr(rs.getString(43));
                iti.setFec_zarpe(rs.getDate(44));
                iti.setHor_zarpe(rs.getString(45));
                iti.setFec_ing(rs.getDate(46));
                iti.setUsr_ing(rs.getString(47));
                iti.setFec_mod(rs.getDate(48));
                iti.setUsr_mod(rs.getString(49));
                iti.setDsp_itinerario(rs.getString(50));
                iti.setDsp_itinerario_imp(rs.getString(51));
                iti.setNum_viaje_imp(rs.getString(52));
                iti.setDsp_itinerario_exp(rs.getString(53));
                iti.setNum_viaje_exp(rs.getString(54));
                iti.setEst_itinerario(rs.getString(55));
                listadoItinerarios.add(iti);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST ITINERARIO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoItinerarios;
    }
    
    public List<Itinerario> findAllItinerarioMov() throws SQLException {
        conexion con = new conexion();
        List<Itinerario> listadoItinerarios = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;

        String query = "select distinct(itinerario) as itinerario "
                + "from publico.descarga "
                + "where movimiento = 'Descarga' "
                + "and itinerario is not null";
        pst = con.getConnection().prepareStatement(query);
        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                Itinerario iti = new Itinerario();
                iti.setDsp_itinerario(rs.getString(1));
                listadoItinerarios.add(iti);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST ITINERARIO MOVIMIENTO DETALLE POR NAVE: " + e.getMessage());
        } finally {
            con.desconectar();
        }
        return listadoItinerarios;
    }
    
    public List<Itinerario> findAllItinerario(){
        conexion con = new conexion();
        List<Itinerario> listadoItinerarios = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        
        String query = "select ids_itinerario, dsp_itinerario, " +
                       "case est_itinerario when 'A' then 'Activo' else 'Inactivo' end as est_itinerario " +
                       "from publico.mae_itinerario";
        
        try {
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Itinerario iti = new Itinerario();
                iti.setIds_itinerario(rs.getInt(1));
                iti.setDsp_itinerario(rs.getString(2));
                iti.setEst_itinerario(rs.getString(3));
                listadoItinerarios.add(iti);
            }
        } catch (Exception e) {
            System.out.println("DAO LIST ITINERARIOS PREDESCARGA: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(LocalidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoItinerarios;
    }

    public void editItinerario(Itinerario iti, Usuario usu) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_itinerario "
                + "set cod_linea=?, cod_buque=?, cod_ptoarribo=?, cod_ptoinicio=?, cod_ptoeta=?, cod_trafico=?, "
                + "num_viaje=?, cod_manifiesto=?, "
                + "cod_manifiesto_exp=?, num_anio=?, fec_arribo=?, hor_arribo=?, "
                + "fec_zarpe=?, hor_zarpe=?, fec_mod=(to_char(current_timestamp,'YYYY-MM-DD HH24:MI:SS'))::timestamp, usr_mod=?, dsp_itinerario=?, "
                + "dsp_itinerario_imp=?, num_viaje_imp=?, dsp_itinerario_exp=?, num_viaje_exp=? "
                + "where ids_itinerario=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setString(1, iti.getLin_codigo());
            pst.setString(2, iti.getBuq_codigo());
            pst.setString(3, iti.getCod_ptoarribo());
            pst.setString(4, iti.getCod_ptoinicio());
            pst.setString(5, iti.getCod_ptoeta());
            pst.setString(6, iti.getTra_codigo());
            pst.setString(7, iti.getNum_viaje());
            pst.setString(8, iti.getCod_manifiesto());
            pst.setString(9, iti.getCod_manifiesto_exp());
            pst.setInt(10, iti.getNum_anio());
            //pst.setTimestamp(11, new jva.sql.Timestamp(iti.getFec_arribo().getTime())
            pst.setDate(11, java.sql.Date.valueOf(format.format(iti.getFec_arribo())));
            pst.setString(12, iti.getHor_arribo());
            pst.setObject(13, java.sql.Date.valueOf(format.format(iti.getFec_zarpe())));
            pst.setString(14, iti.getHor_zarpe());
            pst.setString(15, usu.getLogin());
            pst.setString(16, iti.getDsp_itinerario());
            pst.setString(17, iti.getDsp_itinerario_imp());
            pst.setString(18, iti.getNum_viaje_imp());
            pst.setString(19, iti.getDsp_itinerario_exp());
            pst.setString(20, iti.getNum_viaje_exp());
            pst.setInt(21, iti.getIds_itinerario());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO EDIT ITINERARIO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }

    public void deleteItinerario(Itinerario iti) throws SQLException {
        conexion con = new conexion();
        PreparedStatement pst;
        String query = "update publico.mae_itinerario set est_itinerario = (Case est_itinerario when 'A' then 'E' when 'E' then 'A' end) where ids_itinerario=?";
        pst = con.getConnection().prepareStatement(query);
        try {
            pst.setInt(1, iti.getIds_itinerario());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("DAO DELETE ITINERARIO: " + e.getMessage());
        } finally {
            con.desconectar();
        }
    }
}
