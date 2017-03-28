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
public class GeneralNaves implements Serializable{
    String itinerario;
    Date desde;
    Date hasta;
    Date fecha_arribo;
    Date fecha_salida;
    String tipo_cont;
    int total_cont;

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getTipo_cont() {
        return tipo_cont;
    }

    public void setTipo_cont(String tipo_cont) {
        this.tipo_cont = tipo_cont;
    }

    public int getTotal_cont() {
        return total_cont;
    }

    public void setTotal_cont(int total_cont) {
        this.total_cont = total_cont;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
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
    
}
