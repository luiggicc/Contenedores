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
public class Localidad implements Serializable {
   Integer loc_codigo;
   String loc_des;
   String loc_estado;
   String loc_sender;
   String loc_tipo;

    public Integer getLoc_codigo() {
        return loc_codigo;
    }

    public void setLoc_codigo(Integer loc_codigo) {
        this.loc_codigo = loc_codigo;
    }

    public String getLoc_des() {
        return loc_des;
    }

    public void setLoc_des(String loc_des) {
        this.loc_des = loc_des;
    }

    public String getLoc_estado() {
        return loc_estado;
    }

    public void setLoc_estado(String loc_estado) {
        this.loc_estado = loc_estado;
    }

    public String getLoc_sender() {
        return loc_sender;
    }

    public void setLoc_sender(String loc_sender) {
        this.loc_sender = loc_sender;
    }

    public String getLoc_tipo() {
        return loc_tipo;
    }

    public void setLoc_tipo(String loc_tipo) {
        this.loc_tipo = loc_tipo;
    }
   
   
}
