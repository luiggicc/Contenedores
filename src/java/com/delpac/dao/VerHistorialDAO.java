/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.Usuario;
import com.delpac.entity.VerHistorial;
import com.delpac.entity.Container;
import com.delpac.entity.Itinerario;
import com.delpac.entity.Puerto;

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
public class VerHistorialDAO implements Serializable {

    public List<VerHistorial> findAll(boolean ciclomaximo, String equipo_identi) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        conexion con = new conexion();
        List<VerHistorial> listadoHistorial = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String query = "";
        try {
            if (equipo_identi == null) {
                if (!ciclomaximo) {
                    System.out.println("Entra al primer if");
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, coalesce(des.fecha_arribo, '0001-01-01 00:00:00') as fecha_salida, coalesce(des.fecha_salida, '0001-01-01 00:00:00') as fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio,  "
                            + "des.temp_ideal, des.temp_min, des.temp_max, des.ciclo "
                            + "from publico.descarga des "
                            + "inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "order by des.ciclo desc, des.equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                } else {
                    System.out.println("Entra al segundo if");
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, coalesce(des.fecha_arribo, '0001-01-01 00:00:00') as fecha_salida, coalesce(des.fecha_salida, '0001-01-01 00:00:00') as fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio,  "
                            + "des.temp_ideal, des.temp_min, des.temp_max, max(des.ciclo) as ciclo "
                            + "from publico.descarga des inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "group by des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, primo.prioridad "
                            + "order by ciclo desc, equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                }
            } else {
                if (ciclomaximo) {
                    System.out.println("Entra al tercer if");
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, coalesce(des.fecha_arribo, '0001-01-01 00:00:00') as fecha_salida, coalesce(des.fecha_salida, '0001-01-01 00:00:00') as fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, max(des.ciclo) as ciclo "
                            + "from publico.descarga des inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "where des.equipo_identi like ? "
                            + "group by des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, primo.prioridad "
                            + "order by ciclo desc, equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                    pst.setString(1, equipo_identi);
                } else {
                    System.out.println("Entra al cuarto if");
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, coalesce(des.fecha_arribo, '0001-01-01 00:00:00') as fecha_salida, coalesce(des.fecha_salida, '0001-01-01 00:00:00') as fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, des.ciclo "
                            + "from publico.descarga des inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "where des.equipo_identi like ? "
                            + "group by des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, primo.prioridad, des.ciclo "
                            + "order by ciclo desc, equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                    pst.setString(1, equipo_identi);
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                VerHistorial vhis = new VerHistorial();
                vhis.setSender(rs.getString(1));
                vhis.setFecha_emision(format.parse(format.format(new Date(rs.getTimestamp(2).getTime()))));
                vhis.setMovimiento(rs.getString(3));
                vhis.setBl(rs.getString(4));
                vhis.setBooking(rs.getString(5));
                vhis.setNave(rs.getString(6));
                vhis.setViaje(rs.getString(7));
                vhis.setItinerario(rs.getString(8));
                vhis.setIdenti_trans(rs.getString(9));
                vhis.setPto_carga(rs.getString(10));
                vhis.setPto_descarga(rs.getString(11));
                vhis.setPto_destino(rs.getString(12));
                vhis.setFecha_arribo(format.parse(format.format(new Date(rs.getTimestamp(13).getTime()))));
                vhis.setFecha_salida(format.parse(format.format(new Date(rs.getTimestamp(14).getTime()))));
                vhis.setEquipo_identi(rs.getString(15));
                vhis.setStatus(rs.getString(16));
                vhis.setPeso_bruto(rs.getString(17));
                vhis.setSello(rs.getString(18));
                vhis.setDanio(rs.getString(19));
                vhis.setTemp_ideal(rs.getString(20));
                vhis.setTemp_min(rs.getString(21));
                vhis.setTemp_max(rs.getString(22));
                vhis.setCiclo(rs.getInt(23));
                listadoHistorial.add(vhis);
            }

        } catch (Exception e) {
            System.out.println("DAO VER HISTORIAL CONTENEDORES: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(VerHistorialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoHistorial;
    }

    public void createMovimiento(VerHistorial vhis, Usuario u, Container cont, Itinerario iti, Puerto pue, Puerto puerDesc, Puerto puerDest) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        PreparedStatement pst2;
        PreparedStatement pst3;
        PreparedStatement pst4;
        PreparedStatement pst5;
        if (vhis.getMovimiento().equals("Descarga")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, bl, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, pto_carga, pto_descarga, pto_destino, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, sello, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', ?, 'ECGYE', 'ECGYE', coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            String query2 = "update publico.mae_container set con_estado = 'A' where con_codigo like ?";
            pst = con.getConnection().prepareStatement(query);
            pst2 = con.getConnection().prepareStatement(query2);
            try {
                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, "COSU" + vhis.getBl());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, iti.getDsp_itinerario());
                pst.setString(7, pue.getPto_codigo());
                pst.setString(8, format.format(vhis.getFecha_arribo()));
                pst.setString(9, format.format(vhis.getFecha_arribo()));
                pst.setString(10, cont.getCon_codigo());
                pst.setString(11, vhis.getStatus());
                pst.setString(12, vhis.getPeso_bruto());
                pst.setString(13, vhis.getSello());
                pst.setString(14, vhis.getDanio());
                pst.setString(15, vhis.getTemp_ideal());
                pst.setString(16, vhis.getTemp_ideal());
                pst.setString(17, vhis.getTemp_ideal());
                pst.setString(18, cont.getCon_codigo());
                pst2.setString(1, cont.getCon_codigo());
                pst.executeUpdate();
                pst2.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO DESCARGA: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }
        else if (vhis.getMovimiento().equals("GateOut Puerto")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, pto_carga, pto_descarga, pto_destino, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, sello, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', ?, 'ECGYE', 'ECGYE', coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            pst = con.getConnection().prepareStatement(query);
            try {

                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, iti.getDsp_itinerario());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, pue.getPto_codigo());
                pst.setString(7, format.format(vhis.getFecha_arribo()));
                pst.setString(8, format.format(vhis.getFecha_arribo()));
                pst.setString(9, cont.getCon_codigo());
                pst.setString(10, vhis.getStatus());
                pst.setString(11, vhis.getPeso_bruto());
                pst.setString(12, vhis.getSello());
                pst.setString(13, vhis.getDanio());
                pst.setString(14, vhis.getTemp_ideal());
                pst.setString(15, vhis.getTemp_ideal());
                pst.setString(16, vhis.getTemp_ideal());
                pst.setString(17, cont.getCon_codigo());
                pst.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO GateOut Puerto: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }
        else if (vhis.getMovimiento().equals("GateIn Patio")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            pst = con.getConnection().prepareStatement(query);
            try {

                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, iti.getDsp_itinerario());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, format.format(vhis.getFecha_arribo()));
                pst.setString(7, format.format(vhis.getFecha_arribo()));
                pst.setString(8, cont.getCon_codigo());
                pst.setString(9, vhis.getStatus());
                pst.setString(10, vhis.getPeso_bruto());
                pst.setString(11, vhis.getDanio());
                pst.setString(12, vhis.getTemp_ideal());
                pst.setString(13, vhis.getTemp_ideal());
                pst.setString(14, vhis.getTemp_ideal());
                pst.setString(15, cont.getCon_codigo());
                pst.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO GateIn Patio: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }else if (vhis.getMovimiento().equals("GateOut Patio")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            pst = con.getConnection().prepareStatement(query);
            try {

                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, iti.getDsp_itinerario());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, format.format(vhis.getFecha_arribo()));
                pst.setString(7, format.format(vhis.getFecha_arribo()));
                pst.setString(8, cont.getCon_codigo());
                pst.setString(9, vhis.getStatus());
                pst.setString(10, vhis.getPeso_bruto());
                pst.setString(11, vhis.getDanio());
                pst.setString(12, vhis.getTemp_ideal());
                pst.setString(13, vhis.getTemp_ideal());
                pst.setString(14, vhis.getTemp_ideal());
                pst.setString(15, cont.getCon_codigo());
                pst.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO GateOut Patio: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }else if (vhis.getMovimiento().equals("GateIn Puerto")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, pto_carga, pto_descarga, pto_destino, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, sello, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', 'ECGYE', ?, ?, coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            pst = con.getConnection().prepareStatement(query);
            try {

                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, iti.getDsp_itinerario());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, puerDesc.getPto_codigo());
                pst.setString(7, puerDest.getPto_codigo());
                pst.setString(7, format.format(vhis.getFecha_arribo()));
                pst.setString(8, format.format(vhis.getFecha_arribo()));
                pst.setString(9, cont.getCon_codigo());
                pst.setString(10, vhis.getStatus());
                pst.setString(11, vhis.getPeso_bruto());
                pst.setString(12, vhis.getSello());
                pst.setString(13, vhis.getDanio());
                pst.setString(14, vhis.getTemp_ideal());
                pst.setString(15, vhis.getTemp_ideal());
                pst.setString(16, vhis.getTemp_ideal());
                pst.setString(17, cont.getCon_codigo());
                pst.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO GateIn Puerto: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }else if (vhis.getMovimiento().equals("Export")) {
            String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, booking, "
                    + "nave, viaje, itinerario, carrier_code, transp_modo, transp_means, pto_carga, pto_descarga, pto_destino, "
                    + "fecha_arribo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, sello, danio, "
                    + "temp_ideal, temp_min, temp_max, archivo, estado, ciclo) "
                    + "values('Manual', 'COSCO', coalesce(current_timestamp, '0001-01-01 00:00:00'), ?, ?, ?, "
                    + "(select dsp_itinerario_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select num_viaje_imp from publico.mae_itinerario where dsp_itinerario = ?), "
                    + "(select dsp_itinerario from publico.mae_itinerario where dsp_itinerario = ?), 'CCL', 'Maritime', "
                    + "'Not Identified', ?, 'ECGYE', 'ECGYE', coalesce(?::timestamp, '0001-01-01 00:00:00'), coalesce(?::timestamp, '0001-01-01 00:00:00'), "
                    + "'Container', ?, ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, (select con_ciclo from publico.mae_container where con_codigo = ?))";
            String query2 = "update publico.invsellos set inv_estado = 'E' where inv_sello like ?";
            String query3 = "insert into publico.selloseliminados(inv_codigo, mot_codigo, seli_fecha, usuario) values((select inv_codigo from publico.invsellos where inv_sello like ?),3,to_char(current_timestamp, 'YYYY-MM-DD HH24:MI:SS')::timestamp, 'Manual')";
            String query4 = "update ublico.mae_container set con_estado = 'E' where con_codigo like ?";
            String query5 = "update publico.mae_container set con_ciclo +1 where con_codigo like ?";
            pst = con.getConnection().prepareStatement(query);
            pst2 = con.getConnection().prepareStatement(query2);
            pst3 = con.getConnection().prepareStatement(query3);
            pst4 = con.getConnection().prepareStatement(query4);
            pst5 = con.getConnection().prepareStatement(query5);
            try {
                pst.setString(1, u.getLogin());
                pst.setString(2, vhis.getMovimiento());
                pst.setString(3, vhis.getBooking());
                pst.setString(4, iti.getDsp_itinerario());
                pst.setString(5, iti.getDsp_itinerario());
                pst.setString(6, iti.getDsp_itinerario());
                pst.setString(7, pue.getPto_codigo());
                pst.setString(8, format.format(vhis.getFecha_arribo()));
                pst.setString(9, format.format(vhis.getFecha_arribo()));
                pst.setString(10, cont.getCon_codigo());
                pst.setString(11, vhis.getStatus());
                pst.setString(12, vhis.getPeso_bruto());
                pst.setString(13, vhis.getSello());
                pst.setString(14, vhis.getDanio());
                pst.setString(15, vhis.getTemp_ideal());
                pst.setString(16, vhis.getTemp_ideal());
                pst.setString(17, vhis.getTemp_ideal());
                pst.setString(18, cont.getCon_codigo());
                
                pst2.setString(1, vhis.getSello());
                pst3.setString(1, vhis.getSello());
                pst4.setString(1, cont.getCon_codigo());
                pst5.setString(1, cont.getCon_codigo());
                
                pst.executeUpdate();
                pst2.executeUpdate();
                pst3.executeUpdate();
                pst4.executeUpdate();
                pst5.executeUpdate();
                con.getConnection().commit();

            } catch (Exception e) {
                System.out.println("DAO CREATE MOVIMIENTO Export: " + e.getMessage());
                con.getConnection().rollback();
            } finally {
                con.desconectar();
            }
        }
    }

    public List<Container> autocompletarcontenedores(String cadena) {
        List<Container> listadoContenedores = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst;
        String sql = "select con_codigo, con_tipcont "
                + "from publico.mae_container "
                + "where upper(con_codigo) like upper(?)";
        try {
            pst = con.getConnection().prepareStatement(sql);
            pst.setString(1, "%" + cadena.trim().concat("%"));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Container cont = new Container();
                cont.setCon_codigo(rs.getString(1));
                cont.setCon_tipcont(rs.getString(2));
                listadoContenedores.add(cont);
            }
        } catch (Exception e) {
            System.out.println("DAO AUTOCOMPLETE CONTENEDORES: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(VerHistorialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoContenedores;
    }

    public List<Puerto> autocompletarpuertos(String cadena) {
        List<Puerto> listadoPuertos = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst;
        String sql = "select pto_codigo, pto_nombre "
                + "from publico.mae_puerto "
                + "where upper(pto_nombre) like upper(?) "
                + "and pto_estado = 'A'";
        try {
            pst = con.getConnection().prepareStatement(sql);
            pst.setString(1, "%" + cadena.trim().concat("%"));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Puerto puer = new Puerto();
                puer.setPto_codigo(rs.getString(1));
                puer.setPto_nombre(rs.getString(2));
                listadoPuertos.add(puer);
            }
        } catch (Exception e) {
            System.out.println("DAO AUTOCOMPLETE PUERTOS: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(VerHistorialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoPuertos;
    }

    public List<Itinerario> autocompletaritinerarios(String cadena) {
        List<Itinerario> listadoItinerarios = new ArrayList<>();
        conexion con = new conexion();
        PreparedStatement pst;
        String sql = "select ids_itinerario, dsp_itinerario "
                + "from publico.mae_itinerario "
                + "where upper(dsp_itinerario) like upper(?)";
        try {
            pst = con.getConnection().prepareStatement(sql);
            pst.setString(1, "%" + cadena.trim().concat("%"));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Itinerario iti = new Itinerario();
                iti.setIds_itinerario(rs.getInt(1));
                iti.setDsp_itinerario(rs.getString(2));
                listadoItinerarios.add(iti);
            }
        } catch (Exception e) {
            System.out.println("DAO AUTOCOMPLETE ITINERARIO: " + e.getMessage());
        } finally {
            try {
                con.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(VerHistorialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoItinerarios;
    }
}
