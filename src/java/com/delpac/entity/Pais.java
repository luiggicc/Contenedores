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
public class Pais implements Serializable{
    String pai_codigo;
    String pai_nombre;
    String pai_des;
    String pai_codigo1;
    String pai_codigo2;
    String pai_codigo3;
    String pai_estado;

    public String getPai_codigo1() {
        return pai_codigo1;
    }

    public void setPai_codigo1(String pai_codigo1) {
        this.pai_codigo1 = pai_codigo1;
    }

    public String getPai_codigo2() {
        return pai_codigo2;
    }

    public void setPai_codigo2(String pai_codigo2) {
        this.pai_codigo2 = pai_codigo2;
    }

    public String getPai_codigo3() {
        return pai_codigo3;
    }

    public void setPai_codigo3(String pai_codigo3) {
        this.pai_codigo3 = pai_codigo3;
    }

    public String getPai_codigo() {
        return pai_codigo;
    }

    public void setPai_codigo(String pai_codigo) {
        this.pai_codigo = pai_codigo;
    }

    public String getPai_nombre() {
        return pai_nombre;
    }

    public void setPai_nombre(String pai_nombre) {
        this.pai_nombre = pai_nombre;
    }

    public String getPai_des() {
        return pai_des;
    }

    public void setPai_des(String pai_des) {
        this.pai_des = pai_des;
    }

    
    public String getPai_estado() {
        return pai_estado;
    }

    public void setPai_estado(String pai_estado) {
        this.pai_estado = pai_estado;
    }
    
    
}
