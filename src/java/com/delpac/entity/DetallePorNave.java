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
public class DetallePorNave implements Serializable{
    int item;
    String recipient;
    String equipo_identi;
    String status;
    String iso;
    String peso_bruto;
    String sello;
    String pto_carga;
    String pto_descarga;
    Date fecha_arribo;
    String bl;

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
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

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }
}
