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
public class ReeferStock implements Serializable {

    String estado;
    int rf20;
    int rq40;
    int rf40;
    Date desde;
    Date hasta;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getRf20() {
        return rf20;
    }

    public void setRf20(int rf20) {
        this.rf20 = rf20;
    }

    public int getRq40() {
        return rq40;
    }

    public void setRq40(int rq40) {
        this.rq40 = rq40;
    }

    public int getRf40() {
        return rf40;
    }

    public void setRf40(int rf40) {
        this.rf40 = rf40;
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

}
