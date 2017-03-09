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
public class Sellos implements Serializable {

    int inv_codigo;
    String inv_sello="";
    String inv_seguridad;
    Integer loc_codigo;
    String loc_des;
    String inv_estado;
    int mot_codigo;
    String mot_des;
    String mot_estado;
    private int secuencial;
    private String repeated = "único";
    boolean estado;
    Date seli_fecha;

    @Override
    public boolean equals(Object object) {
        if (this == null) {
            return true;
        }
        if (!(object instanceof Sellos)) {
            return false;
        }
        Sellos s = (Sellos) object;
        return this.inv_sello.equals(s.inv_sello);
    }

    public String getInv_sello() {
        return inv_sello;
    }

    public void setInv_sello(String inv_sello) {
        this.inv_sello = inv_sello;
    }

    public String getInv_seguridad() {
        return inv_seguridad;
    }

    public void setInv_seguridad(String inv_seguridad) {
        this.inv_seguridad = inv_seguridad;
    }

    public Integer getLoc_codigo() {
        return loc_codigo;
    }

    public void setLoc_codigo(Integer loc_codigo) {
        this.loc_codigo = loc_codigo;
    }

    public int getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.secuencial = secuencial;
    }

    public String getRepeated() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public String getLoc_des() {
        return loc_des;
    }

    public void setLoc_des(String loc_des) {
        this.loc_des = loc_des;
    }

    public String getInv_estado() {
        return inv_estado;
    }

    public void setInv_estado(String inv_estado) {
        this.inv_estado = inv_estado;
    }

    public int getInv_codigo() {
        return inv_codigo;
    }

    public void setInv_codigo(int inv_codigo) {
        this.inv_codigo = inv_codigo;
    }

    public int getMot_codigo() {
        return mot_codigo;
    }

    public void setMot_codigo(int mot_codigo) {
        this.mot_codigo = mot_codigo;
    }

    public String getMot_des() {
        return mot_des;
    }

    public void setMot_des(String mot_des) {
        this.mot_des = mot_des;
    }

    public String getMot_estado() {
        return mot_estado;
    }

    public void setMot_estado(String mot_estado) {
        this.mot_estado = mot_estado;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getSeli_fecha() {
        return seli_fecha;
    }

    public void setSeli_fecha(Date seli_fecha) {
        this.seli_fecha = seli_fecha;
    }

    
}
