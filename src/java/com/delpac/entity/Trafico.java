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
public class Trafico implements Serializable{
    String tra_codigo;
    String tra_nombre;
    String tra_des;
    String tra_codigo1;
    String tra_codigo2;
    String tra_codigo3;
    String tra_estado;

    public String getTra_codigo() {
        return tra_codigo;
    }

    public void setTra_codigo(String tra_codigo) {
        this.tra_codigo = tra_codigo;
    }

    public String getTra_nombre() {
        return tra_nombre;
    }

    public void setTra_nombre(String tra_nombre) {
        this.tra_nombre = tra_nombre;
    }

    public String getTra_des() {
        return tra_des;
    }

    public void setTra_des(String tra_des) {
        this.tra_des = tra_des;
    }

    public String getTra_codigo1() {
        return tra_codigo1;
    }

    public void setTra_codigo1(String tra_codigo1) {
        this.tra_codigo1 = tra_codigo1;
    }

    public String getTra_codigo2() {
        return tra_codigo2;
    }

    public void setTra_codigo2(String tra_codigo2) {
        this.tra_codigo2 = tra_codigo2;
    }

    public String getTra_codigo3() {
        return tra_codigo3;
    }

    public void setTra_codigo3(String tra_codigo3) {
        this.tra_codigo3 = tra_codigo3;
    }

    public String getTra_estado() {
        return tra_estado;
    }

    public void setTra_estado(String tra_estado) {
        this.tra_estado = tra_estado;
    }
    
    
}
