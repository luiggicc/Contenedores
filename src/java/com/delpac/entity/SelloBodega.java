/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.entity;

import java.util.Date;

/**
 *
 * @author Bottago SA
 */
public class SelloBodega {
   Long sbo_numero;
   String sbo_estado;
   Long sbr_desde;
   Long sbr_hasta;
   Date sbr_fecha;
   String sbr_estado;

    public Long getSbo_numero() {
        return sbo_numero;
    }

    public void setSbo_numero(Long sbo_numero) {
        this.sbo_numero = sbo_numero;
    }

    public String getSbo_estado() {
        return sbo_estado;
    }

    public void setSbo_estado(String sbo_estado) {
        this.sbo_estado = sbo_estado;
    }

    public Long getSbr_desde() {
        return sbr_desde;
    }

    public void setSbr_desde(Long sbr_desde) {
        this.sbr_desde = sbr_desde;
    }

    public Long getSbr_hasta() {
        return sbr_hasta;
    }

    public void setSbr_hasta(Long sbr_hasta) {
        this.sbr_hasta = sbr_hasta;
    }

    public Date getSbr_fecha() {
        return sbr_fecha;
    }

    public void setSbr_fecha(Date sbr_fecha) {
        this.sbr_fecha = sbr_fecha;
    }

    public String getSbr_estado() {
        return sbr_estado;
    }

    public void setSbr_estado(String sbr_estado) {
        this.sbr_estado = sbr_estado;
    }
   
}
