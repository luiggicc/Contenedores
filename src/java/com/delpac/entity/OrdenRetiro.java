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
public class OrdenRetiro implements Serializable {

    int cod_ordenretiro;
    String cia_codigo;
    String cia_nombre;
    int ids_itinerario;
    String dsp_itinerario;
    String lin_codigo;
    String lin_nombre;
    String booking;
    String pto_codigo;
    String pto_nombre;
    String mov_xcuenta;
    String cant_tipocont;
    String tipo_carga;
    String req_especial;
    int inv_codigo;
    String inv_sello;
    String inv_seguridad;
    int int_seguridad;
    int es_temperado;
    String temperatura;
    String ventilacion;
    String observaciones;
    int loc_codigo;
    int loc_salida;
    String loc_salidades;
    int loc_entrada;
    String loc_entradades;
    String usu_cre;
    String usu_mod;
    int estadopdf;
    int est_orden;
    String req_especial2;
    String destinario;
    String cc;
    int mai_codigo;
    String mai_nombre;
    String mai_mail;
    int asignada;

    public int getCod_ordenretiro() {
        return cod_ordenretiro;
    }

    public void setCod_ordenretiro(int cod_ordenretiro) {
        this.cod_ordenretiro = cod_ordenretiro;
    }

    public String getCia_codigo() {
        return cia_codigo;
    }

    public void setCia_codigo(String cia_codigo) {
        this.cia_codigo = cia_codigo;
    }

    public String getCia_nombre() {
        return cia_nombre;
    }

    public void setCia_nombre(String cia_nombre) {
        this.cia_nombre = cia_nombre;
    }

    public int getIds_itinerario() {
        return ids_itinerario;
    }

    public void setIds_itinerario(int ids_itinerario) {
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

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
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

    public String getMov_xcuenta() {
        return mov_xcuenta;
    }

    public void setMov_xcuenta(String mov_xcuenta) {
        this.mov_xcuenta = mov_xcuenta;
    }

    public String getCant_tipocont() {
        return cant_tipocont;
    }

    public void setCant_tipocont(String cant_tipocont) {
        this.cant_tipocont = cant_tipocont;
    }

    public String getTipo_carga() {
        return tipo_carga;
    }

    public void setTipo_carga(String tipo_carga) {
        this.tipo_carga = tipo_carga;
    }

    public String getReq_especial() {
        return req_especial;
    }

    public void setReq_especial(String req_especial) {
        this.req_especial = req_especial;
    }

    public int getInv_codigo() {
        return inv_codigo;
    }

    public void setInv_codigo(int inv_codigo) {
        this.inv_codigo = inv_codigo;
    }

    public String getInv_sello() {
        return inv_sello;
    }

    public void setInv_sello(String inv_sello) {
        this.inv_sello = inv_sello;
    }

    public String getInv_seguridad() {
        return inv_seguridad;
    }

    public void setInv_seguridad(String inv_seguridad) {
        this.inv_seguridad = inv_seguridad;
    }

    public int getInt_seguridad() {
        return int_seguridad;
    }

    public void setInt_seguridad(int int_seguridad) {
        this.int_seguridad = int_seguridad;
    }

    public int getEs_temperado() {
        return es_temperado;
    }

    public void setEs_temperado(int es_temperado) {
        this.es_temperado = es_temperado;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getLoc_codigo() {
        return loc_codigo;
    }

    public void setLoc_codigo(int loc_codigo) {
        this.loc_codigo = loc_codigo;
    }

    public String getLoc_salidades() {
        return loc_salidades;
    }

    public void setLoc_salidades(String loc_salidades) {
        this.loc_salidades = loc_salidades;
    }

    public String getLoc_entradades() {
        return loc_entradades;
    }

    public void setLoc_entradades(String loc_entradades) {
        this.loc_entradades = loc_entradades;
    }

    public String getUsu_cre() {
        return usu_cre;
    }

    public void setUsu_cre(String usu_cre) {
        this.usu_cre = usu_cre;
    }

    public String getUsu_mod() {
        return usu_mod;
    }

    public void setUsu_mod(String usu_mod) {
        this.usu_mod = usu_mod;
    }

    public int getLoc_salida() {
        return loc_salida;
    }

    public void setLoc_salida(int loc_salida) {
        this.loc_salida = loc_salida;
    }

    public int getLoc_entrada() {
        return loc_entrada;
    }

    public void setLoc_entrada(int loc_entrada) {
        this.loc_entrada = loc_entrada;
    }

    public int getEstadopdf() {
        return estadopdf;
    }

    public void setEstadopdf(int estadopdf) {
        this.estadopdf = estadopdf;
    }

    public int getEst_orden() {
        return est_orden;
    }

    public void setEst_orden(int est_orden) {
        this.est_orden = est_orden;
    }

    public String getReq_especial2() {
        return req_especial2;
    }

    public void setReq_especial2(String req_especial2) {
        this.req_especial2 = req_especial2;
    }

    public String getDestinario() {
        return destinario;
    }

    public void setDestinario(String destinario) {
        this.destinario = destinario;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public int getMai_codigo() {
        return mai_codigo;
    }

    public void setMai_codigo(int mai_codigo) {
        this.mai_codigo = mai_codigo;
    }

    public String getMai_nombre() {
        return mai_nombre;
    }

    public void setMai_nombre(String mai_nombre) {
        this.mai_nombre = mai_nombre;
    }

    public String getMai_mail() {
        return mai_mail;
    }

    public void setMai_mail(String mai_mail) {
        this.mai_mail = mai_mail;
    }

    public int getAsignada() {
        return asignada;
    }

    public void setAsignada(int asignada) {
        this.asignada = asignada;
    }
    
    
}
