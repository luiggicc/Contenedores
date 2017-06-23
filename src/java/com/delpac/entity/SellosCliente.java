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
public class SellosCliente implements Serializable{
    String cia_nombre;
    String booking;
    String inv_seguridad;
    Date fecha_crea;
    int diferencia;
    int cod_ordenretiro;

    public String getCia_nombre() {
        return cia_nombre;
    }

    public void setCia_nombre(String cia_nombre) {
        this.cia_nombre = cia_nombre;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getInv_seguridad() {
        return inv_seguridad;
    }

    public void setInv_seguridad(String inv_seguridad) {
        this.inv_seguridad = inv_seguridad;
    }

    public Date getFecha_crea() {
        return fecha_crea;
    }

    public void setFecha_crea(Date fecha_crea) {
        this.fecha_crea = fecha_crea;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    public int getCod_ordenretiro() {
        return cod_ordenretiro;
    }

    public void setCod_ordenretiro(int cod_ordenretiro) {
        this.cod_ordenretiro = cod_ordenretiro;
    }

   
    
}
