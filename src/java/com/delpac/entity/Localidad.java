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
   
   
}
