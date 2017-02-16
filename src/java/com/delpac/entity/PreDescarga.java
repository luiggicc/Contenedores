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
public class PreDescarga implements Serializable{
    Integer ids_itinerario;
    String dsp_itinerario;
    String lin_codigo;
    String lin_nombre;
    String pto_codigo;
    String pto_nombre;
    Date fec_arribo;
    String n_manifiesto;
    String bl;
    String consignatario;
    String pto_origen;
    String pto_destino;
    String fec_embarque;
    double peso;
    double bulto;
    String embalaje;
    String carga;
    String con_codigo;
    String temperatura;
    String ventilacion;
    String sello;
    String sown;
    String classimo;
    String unnro;
    String tip_cont;
    String condicion;
    String alm_codigo;

    public Integer getIds_itinerario() {
        return ids_itinerario;
    }

    public void setIds_itinerario(Integer ids_itinerario) {
        this.ids_itinerario = ids_itinerario;
    }

    public String getDsp_itinerario() {
        return dsp_itinerario;
    }

    public void setDsp_itinerario(String dsp_itinerario) {
        this.dsp_itinerario = dsp_itinerario;
    }

    public String getLin_codigo() {
        return lin_codigo;
    }

    public void setLin_codigo(String lin_codigo) {
        this.lin_codigo = lin_codigo;
    }

    public String getLin_nombre() {
        return lin_nombre;
    }

    public void setLin_nombre(String lin_nombre) {
        this.lin_nombre = lin_nombre;
    }

    public String getPto_codigo() {
        return pto_codigo;
    }

    public void setPto_codigo(String pto_codigo) {
        this.pto_codigo = pto_codigo;
    }

    public String getPto_nombre() {
        return pto_nombre;
    }

    public void setPto_nombre(String pto_nombre) {
        this.pto_nombre = pto_nombre;
    }

    public Date getFec_arribo() {
        return fec_arribo;
    }

    public void setFec_arribo(Date fec_arribo) {
        this.fec_arribo = fec_arribo;
    }

    public String getN_manifiesto() {
        return n_manifiesto;
    }

    public void setN_manifiesto(String n_manifiesto) {
        this.n_manifiesto = n_manifiesto;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getConsignatario() {
        return consignatario;
    }

    public void setConsignatario(String consignatario) {
        this.consignatario = consignatario;
    }

    public String getPto_origen() {
        return pto_origen;
    }

    public void setPto_origen(String pto_origen) {
        this.pto_origen = pto_origen;
    }

    public String getPto_destino() {
        return pto_destino;
    }

    public void setPto_destino(String pto_destino) {
        this.pto_destino = pto_destino;
    }

    public String getFec_embarque() {
        return fec_embarque;
    }

    public void setFec_embarque(String fec_embarque) {
        this.fec_embarque = fec_embarque;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getBulto() {
        return bulto;
    }

    public void setBulto(double bulto) {
        this.bulto = bulto;
    }

    public String getEmbalaje() {
        return embalaje;
    }

    public void setEmbalaje(String embalaje) {
        this.embalaje = embalaje;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getCon_codigo() {
        return con_codigo;
    }

    public void setCon_codigo(String con_codigo) {
        this.con_codigo = con_codigo;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getVentilacion() {
        return ventilacion;
    }

    public void setVentilacion(String ventilacion) {
        this.ventilacion = ventilacion;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getSown() {
        return sown;
    }

    public void setSown(String sown) {
        this.sown = sown;
    }

    public String getClassimo() {
        return classimo;
    }

    public void setClassimo(String classimo) {
        this.classimo = classimo;
    }

    public String getUnnro() {
        return unnro;
    }

    public void setUnnro(String unnro) {
        this.unnro = unnro;
    }

    public String getTip_cont() {
        return tip_cont;
    }

    public void setTip_cont(String tip_cont) {
        this.tip_cont = tip_cont;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getAlm_codigo() {
        return alm_codigo;
    }

    public void setAlm_codigo(String alm_codigo) {
        this.alm_codigo = alm_codigo;
    }
}
