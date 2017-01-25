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
public class Tipidentifica implements Serializable {
    String tpi_codigo;
    String tpi_nombre;
    String tpi_des;
    String tpi_codigo1;
    String tpi_codigo2;
    String tpi_codigo3;
    String tpi_estado;

    public String getTpi_codigo() {
        return tpi_codigo;
    }

    public void setTpi_codigo(String tpi_codigo) {
        this.tpi_codigo = tpi_codigo;
    }

    public String getTpi_nombre() {
        return tpi_nombre;
    }

    public void setTpi_nombre(String tpi_nombre) {
        this.tpi_nombre = tpi_nombre;
    }

    public String getTpi_des() {
        return tpi_des;
    }

    public void setTpi_des(String tpi_des) {
        this.tpi_des = tpi_des;
    }

    public String getTpi_codigo1() {
        return tpi_codigo1;
    }

    public void setTpi_codigo1(String tpi_codigo1) {
        this.tpi_codigo1 = tpi_codigo1;
    }

    public String getTpi_codigo2() {
        return tpi_codigo2;
    }

    public void setTpi_codigo2(String tpi_codigo2) {
        this.tpi_codigo2 = tpi_codigo2;
    }

    public String getTpi_codigo3() {
        return tpi_codigo3;
    }

    public void setTpi_codigo3(String tpi_codigo3) {
        this.tpi_codigo3 = tpi_codigo3;
    }

    public String getTpi_estado() {
        return tpi_estado;
    }

    public void setTpi_estado(String tpi_estado) {
        this.tpi_estado = tpi_estado;
    }
    
    
}
