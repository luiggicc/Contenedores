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

public class ContainerLoss implements Serializable{
    String recipient;
    String cont_cont_loss;
    String obs_cont_loss;
    String status;
    String iso;
    String con_tipcont;
    String sello;
    String pto_carga;
    String pto_descarga;
    Date fecha_arribo;
    Date fecha_salida;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCont_cont_loss() {
        return cont_cont_loss;
    }

    public void setCont_cont_loss(String cont_cont_loss) {
        this.cont_cont_loss = cont_cont_loss;
    }

    public String getObs_cont_loss() {
        return obs_cont_loss;
    }

    public void setObs_cont_loss(String obs_cont_loss) {
        this.obs_cont_loss = obs_cont_loss;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCon_tipcont() {
        return con_tipcont;
    }

    public void setCon_tipcont(String con_tipcont) {
        this.con_tipcont = con_tipcont;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
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
