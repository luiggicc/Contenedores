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
public class Puerto implements Serializable{
    String pto_codigo;
    String pai_codigo;
    String pto_nombre;
    String pto_des;
    String pto_codigo1;
    String pto_codigo2;
    String pto_codigo3;
    String pto_estado;
    String cod_serv;
    String pai_nombre;

    public String getPto_codigo() {
        return pto_codigo;
    }

    public void setPto_codigo(String pto_codigo) {
        this.pto_codigo = pto_codigo;
    }

    public String getPai_codigo() {
        return pai_codigo;
    }

    public void setPai_codigo(String pai_codigo) {
        this.pai_codigo = pai_codigo;
    }

    public String getPto_nombre() {
        return pto_nombre;
    }

    public void setPto_nombre(String pto_nombre) {
        this.pto_nombre = pto_nombre;
    }

    public String getPto_des() {
        return pto_des;
    }

    public void setPto_des(String pto_des) {
        this.pto_des = pto_des;
    }

    public String getPto_codigo1() {
        return pto_codigo1;
    }

    public void setPto_codigo1(String pto_codigo1) {
        this.pto_codigo1 = pto_codigo1;
    }

    public String getPto_codigo2() {
        return pto_codigo2;
    }

    public void setPto_codigo2(String pto_codigo2) {
        this.pto_codigo2 = pto_codigo2;
    }

    public String getPto_codigo3() {
        return pto_codigo3;
    }

    public void setPto_codigo3(String pto_codigo3) {
        this.pto_codigo3 = pto_codigo3;
    }

    public String getPto_estado() {
        return pto_estado;
    }

    public void setPto_estado(String pto_estado) {
        this.pto_estado = pto_estado;
    }

    public String getCod_serv() {
        return cod_serv;
    }

    public void setCod_serv(String cod_serv) {
        this.cod_serv = cod_serv;
    }

    public String getPai_nombre() {
        return pai_nombre;
    }

    public void setPai_nombre(String pai_nombre) {
        this.pai_nombre = pai_nombre;
    }
    
    
}
