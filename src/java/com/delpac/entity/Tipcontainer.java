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
public class Tipcontainer implements Serializable{
    String cod_tipcont;
    String tcon_nombre;
    String tcon_des;
    Double val_tara;
    String tcon_codigo1;
    String tcon_codigo2;
    String tcon_codigo3;
    String tcon_codigo4;
    String tcon_estado;

    public String getCod_tipcont() {
        return cod_tipcont;
    }

    public void setCod_tipcont(String cod_tipcont) {
        this.cod_tipcont = cod_tipcont;
    }

    public String getTcon_nombre() {
        return tcon_nombre;
    }

    public void setTcon_nombre(String tcon_nombre) {
        this.tcon_nombre = tcon_nombre;
    }

    public String getTcon_des() {
        return tcon_des;
    }

    public void setTcon_des(String tcon_des) {
        this.tcon_des = tcon_des;
    }

    public Double getVal_tara() {
        return val_tara;
    }

    public void setVal_tara(Double val_tara) {
        this.val_tara = val_tara;
    }

    public String getTcon_codigo1() {
        return tcon_codigo1;
    }

    public void setTcon_codigo1(String tcon_codigo1) {
        this.tcon_codigo1 = tcon_codigo1;
    }

    public String getTcon_codigo2() {
        return tcon_codigo2;
    }

    public void setTcon_codigo2(String tcon_codigo2) {
        this.tcon_codigo2 = tcon_codigo2;
    }

    public String getTcon_codigo3() {
        return tcon_codigo3;
    }

    public void setTcon_codigo3(String tcon_codigo3) {
        this.tcon_codigo3 = tcon_codigo3;
    }

    public String getTcon_codigo4() {
        return tcon_codigo4;
    }

    public void setTcon_codigo4(String tcon_codigo4) {
        this.tcon_codigo4 = tcon_codigo4;
    }

    public String getTcon_estado() {
        return tcon_estado;
    }

    public void setTcon_estado(String tcon_estado) {
        this.tcon_estado = tcon_estado;
    }
    
    
}
