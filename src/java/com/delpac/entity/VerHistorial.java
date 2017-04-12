/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Bottago SA
 */
public class VerHistorial implements Serializable {

    String sender;
    String recipient;
    Date fecha_emision;
    String id_sender;
    String movimiento;
    String bl;
    String booking;
    String nave;
    String viaje;
    String itinerario;
    String carrier_code;
    String identi_tip;
    String identi_trans;
    String pto_carga;
    String pto_descarga;
    String pto_destino;
    Date fecha_arribo;
    Date fecha_salida;
    String equipo_identi;
    String status;
    String peso_bruto;
    String sello;
    String danio;
    String danio_cod;
    String temp_ideal;
    String temp_min;
    String temp_max;
    String iso;
    int ciclo;
    String pto_codigo;
    String pto_nombre;

    public String getPto_codigo() {
        return pto_codigo;
    }

    public void setPto_codigo(String pto_codigo) {
        this.pto_codigo = pto_codigo;
    }

    public String getPto_nombre() {
        return pto_nombre;
    }

    public void setPto_nombre(String pto_nombre) {
        this.pto_nombre = pto_nombre;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getDanio_cod() {
        return danio_cod;
    }

    public void setDanio_cod(String danio_cod) {
        this.danio_cod = danio_cod;
    }

    public String getIdenti_tip() {
        return identi_tip;
    }

    public void setIdenti_tip(String identi_tip) {
        this.identi_tip = identi_tip;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    public String getViaje() {
        return viaje;
    }

    public void setViaje(String viaje) {
        this.viaje = viaje;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getIdenti_trans() {
        return identi_trans;
    }

    public void setIdenti_trans(String identi_trans) {
        this.identi_trans = identi_trans;
    }

    public String getPto_carga() {
        return pto_carga;
    }

    public void setPto_carga(String pto_carga) {
        this.pto_carga = pto_carga;
    }

    public String getPto_descarga() {
        return pto_descarga;
    }

    public void setPto_descarga(String pto_descarga) {
        this.pto_descarga = pto_descarga;
    }

    public String getPto_destino() {
        return pto_destino;
    }

    public void setPto_destino(String pto_destino) {
        this.pto_destino = pto_destino;
    }

    public Date getFecha_arribo() {
        return fecha_arribo;
    }

    public void setFecha_arribo(Date fecha_arribo) {
        this.fecha_arribo = fecha_arribo;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getEquipo_identi() {
        return equipo_identi;
    }

    public void setEquipo_identi(String equipo_identi) {
        this.equipo_identi = equipo_identi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeso_bruto() {
        return peso_bruto;
    }

    public void setPeso_bruto(String peso_bruto) {
        this.peso_bruto = peso_bruto;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getDanio() {
        return danio;
    }

    public void setDanio(String danio) {
        this.danio = danio;
    }

    public String getTemp_ideal() {
        return temp_ideal;
    }

    public void setTemp_ideal(String temp_ideal) {
        this.temp_ideal = temp_ideal;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

}
