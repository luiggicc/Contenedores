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
public class TeusExports implements Serializable {

    String anio;
    Date fecha_arribo;
    String itinerario;
    String mes;
    int cont20;
    int cont40;
    int cont40rh;
    int cont20mty;
    int cont40mty;
    int contemptyteus;
    int contfullteus;

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Date getFecha_arribo() {
        return fecha_arribo;
    }

    public void setFecha_arribo(Date fecha_arribo) {
        this.fecha_arribo = fecha_arribo;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public int getCont20() {
        return cont20;
    }

    public void setCont20(int cont20) {
        this.cont20 = cont20;
    }

    public int getCont40() {
        return cont40;
    }

    public void setCont40(int cont40) {
        this.cont40 = cont40;
    }

    public int getCont40rh() {
        return cont40rh;
    }

    public void setCont40rh(int cont40rh) {
        this.cont40rh = cont40rh;
    }

    public int getCont20mty() {
        return cont20mty;
    }

    public void setCont20mty(int cont20mty) {
        this.cont20mty = cont20mty;
    }

    public int getCont40mty() {
        return cont40mty;
    }

    public void setCont40mty(int cont40mty) {
        this.cont40mty = cont40mty;
    }

    public int getContemptyteus() {
        return contemptyteus;
    }

    public void setContemptyteus(int contemptyteus) {
        this.contemptyteus = contemptyteus;
    }

    public int getContfullteus() {
        return contfullteus;
    }

    public void setContfullteus(int contfullteus) {
        this.contfullteus = contfullteus;
    }

}
