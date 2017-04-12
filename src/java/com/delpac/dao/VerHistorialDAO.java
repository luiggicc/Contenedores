/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import com.delpac.entity.VerHistorial;
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
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio,  "
                            + "des.temp_ideal, des.temp_min, des.temp_max, des.ciclo "
                            + "from publico.descarga des "
                            + "inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "order by des.ciclo desc, des.equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                } else {
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio,  "
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
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, max(des.ciclo) as ciclo "
                            + "from publico.descarga des inner join publico.prioridad_movimiento primo on primo.movimiento = des.movimiento "
                            + "where des.equipo_identi like ? "
                            + "group by des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
                            + "des.temp_ideal, des.temp_min, des.temp_max, primo.prioridad "
                            + "order by ciclo desc, equipo_identi, primo.prioridad";
                    pst = con.getConnection().prepareStatement(query);
                    pst.setString(1, equipo_identi);
                    rs = pst.executeQuery();
                } else {
                    query = "select des.sender, des.fecha_emision, des.movimiento, des.bl, des.booking, des.nave, des.viaje, des.itinerario, des.identi_trans, des.pto_carga, "
                            + "des.pto_descarga, des.pto_destino, des.fecha_arribo, des.fecha_salida, des.equipo_identi, des.status, des.peso_bruto, des.sello, des.danio, "
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
                Logger.getLogger(TransferDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listadoHistorial;
    }

    public void createMovimiento(VerHistorial vhis) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        conexion con = new conexion();
        con.getConnection().setAutoCommit(false);
        PreparedStatement pst;
        String query = "insert into publico.descarga(sender, recipient, fecha_emision, id_sender, movimiento, bl, booking, nave, viaje, itinerario, carrier_code, identi_tip, identi_trans, transp_modo, transp_means, pto_carga, pto_descarga, pto_destino, fecha_arrivo, fecha_salida, equipo_clasif, equipo_identi, status, peso_bruto, sello, danio, danio_cod, temp_ideal, temp_min, temp_max, archivo, estado, ciclo, iso) "
                + "values(?, ?, ?::timestamp, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Maritime', 'Not Identified', ?, ?, ?, ?, ?, 'Container', ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Agregado Manual', 1, ?, ?)";
        pst = con.getConnection().prepareStatement(query);
        try {
            //pst.setString(1, sellos.getInv_sello());
            pst.setString(1, vhis.getSender());
            pst.setString(2, vhis.getRecipient());
            pst.setString(3, format.format(vhis.getFecha_emision()));
            pst.setString(4, vhis.getId_sender());
            pst.setString(5, vhis.getMovimiento());
            pst.setString(6, vhis.getBl());
            pst.setString(7, vhis.getBooking());
            pst.setString(8, vhis.getNave());
            pst.setString(9, vhis.getViaje());
            pst.setString(10, vhis.getItinerario());
            pst.setString(11, vhis.getCarrier_code());
            pst.setString(12, vhis.getIdenti_tip());
            pst.setString(13, vhis.getIdenti_trans());
            pst.setString(14, vhis.getPto_carga());
            pst.setString(15, vhis.getPto_descarga());
            pst.setString(16, vhis.getPto_destino());
            pst.setString(17, format.format(vhis.getFecha_arribo()));
            pst.setString(18, format.format(vhis.getFecha_salida()));
            pst.setString(19, vhis.getEquipo_identi());
            pst.setString(20, vhis.getStatus());
            pst.setString(21, vhis.getPeso_bruto());
            pst.setString(22, vhis.getSello());
            pst.setString(23, vhis.getDanio());
            pst.setString(24, vhis.getDanio_cod());
            pst.setString(25, vhis.getTemp_ideal());
            pst.setString(26, vhis.getTemp_min());
            pst.setString(27, vhis.getTemp_max());
            pst.setInt(28, vhis.getCiclo());
            pst.setString(29, vhis.getIso());
            
            pst.executeUpdate();
            con.getConnection().commit();
        } catch (Exception e) {
            System.out.println("DAO CREATE MOVIMIENTO DESCARGA: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
    }
}
