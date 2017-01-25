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
public class Tipmanifiesto implements Serializable {
    String tman_codigo;
    String tman_nombre;
    String tman_des;
    String tman_codigo1;
    String tman_codigo2;
    String tman_codigo3;
    String tman_estado;

    public String getTman_codigo() {
        return tman_codigo;
    }

    public void setTman_codigo(String tman_codigo) {
        this.tman_codigo = tman_codigo;
    }

    public String getTman_nombre() {
        return tman_nombre;
    }

    public void setTman_nombre(String tman_nombre) {
        this.tman_nombre = tman_nombre;
    }

    public String getTman_des() {
        return tman_des;
    }

    public void setTman_des(String tman_des) {
        this.tman_des = tman_des;
    }

    public String getTman_codigo1() {
        return tman_codigo1;
    }

    public void setTman_codigo1(String tman_codigo1) {
        this.tman_codigo1 = tman_codigo1;
    }

    public String getTman_codigo2() {
        return tman_codigo2;
    }

    public void setTman_codigo2(String tman_codigo2) {
        this.tman_codigo2 = tman_codigo2;
    }

    public String getTman_codigo3() {
        return tman_codigo3;
    }

    public void setTman_codigo3(String tman_codigo3) {
        this.tman_codigo3 = tman_codigo3;
    }

    public String getTman_estado() {
        return tman_estado;
    }

    public void setTman_estado(String tman_estado) {
        this.tman_estado = tman_estado;
    }
    
    
}
