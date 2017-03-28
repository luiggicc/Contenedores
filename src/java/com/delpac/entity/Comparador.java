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
public class Comparador implements Serializable{
    String con_codigo;
    String equipo_identi;
    String sender;
    Date fecha_arribo;

    public String getCon_codigo() {
        return con_codigo;
    }

    public void setCon_codigo(String con_codigo) {
        this.con_codigo = con_codigo;
    }

    public String getEquipo_identi() {
        return equipo_identi;
    }

    public void setEquipo_identi(String equipo_identi) {
        this.equipo_identi = equipo_identi;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getFecha_arribo() {
        return fecha_arribo;
    }

    public void setFecha_arribo(Date fecha_arribo) {
        this.fecha_arribo = fecha_arribo;
    }
    
    
}
