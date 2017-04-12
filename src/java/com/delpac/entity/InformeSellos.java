/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.entity;

import java.io.Serializable;

/**
 *
 * @author Bottago SA
 */
public class InformeSellos implements Serializable{
    int cod_ordenretiro;
    String fecha_crea;
    String cant_tipocont;
    String inv_seguridad;
    String inv_sello;
    String cia_nombre;
    String dsp_itinerario;
    String booking;

    public String getFecha_crea() {
        return fecha_crea;
    }

    public void setFecha_crea(String fecha_crea) {
        this.fecha_crea = fecha_crea;
    }

    public String getCant_tipocont() {
        return cant_tipocont;
    }

    public void setCant_tipocont(String cant_tipocont) {
        this.cant_tipocont = cant_tipocont;
    }

    public String getInv_seguridad() {
        return inv_seguridad;
    }

    public void setInv_seguridad(String inv_seguridad) {
        this.inv_seguridad = inv_seguridad;
    }

    public String getInv_sello() {
        return inv_sello;
    }

    public void setInv_sello(String inv_sello) {
        this.inv_sello = inv_sello;
    }

    public String getCia_nombre() {
        return cia_nombre;
    }

    public void setCia_nombre(String cia_nombre) {
        this.cia_nombre = cia_nombre;
    }

    public String getDsp_itinerario() {
        return dsp_itinerario;
    }

    public void setDsp_itinerario(String dsp_itinerario) {
        this.dsp_itinerario = dsp_itinerario;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public int getCod_ordenretiro() {
        return cod_ordenretiro;
    }

    public void setCod_ordenretiro(int cod_ordenretiro) {
        this.cod_ordenretiro = cod_ordenretiro;
    }
}
