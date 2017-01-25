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
public class Condicion implements Serializable{
    String cond_codigo;
    String cond_nombre;
    String cond_des;
    String cond_codigo1;
    String cond_codigo2;
    String cond_codigo3;
    String cond_estado;

    public String getCond_codigo() {
        return cond_codigo;
    }

    public void setCond_codigo(String cond_codigo) {
        this.cond_codigo = cond_codigo;
    }

    public String getCond_nombre() {
        return cond_nombre;
    }

    public void setCond_nombre(String cond_nombre) {
        this.cond_nombre = cond_nombre;
    }

    public String getCond_des() {
        return cond_des;
    }

    public void setCond_des(String cond_des) {
        this.cond_des = cond_des;
    }

    public String getCond_codigo1() {
        return cond_codigo1;
    }

    public void setCond_codigo1(String cond_codigo1) {
        this.cond_codigo1 = cond_codigo1;
    }

    public String getCond_codigo2() {
        return cond_codigo2;
    }

    public void setCond_codigo2(String cond_codigo2) {
        this.cond_codigo2 = cond_codigo2;
    }

    public String getCond_codigo3() {
        return cond_codigo3;
    }

    public void setCond_codigo3(String cond_codigo3) {
        this.cond_codigo3 = cond_codigo3;
    }

    public String getCond_estado() {
        return cond_estado;
    }

    public void setCond_estado(String cond_estado) {
        this.cond_estado = cond_estado;
    }
    
    
}
