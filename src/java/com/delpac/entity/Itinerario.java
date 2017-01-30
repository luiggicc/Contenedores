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
public class Itinerario implements Serializable{
    Integer ids_itinerario;
    String cia_codigo;
    String cia_nombre;
    String lin_codigo;
    String lin_nombre;
    String buq_codigo;
    String buq_nombre;
    String cod_ptoarribo;
    String nom_ptoarribo;
    String cod_ptoinicio;
    String nom_ptoinicio;
    String cod_ptoeta;
    String nom_ptoeta;
    String cod_ptofisico;
    String tra_codigo;
    String tra_nombre;
    String adu_codigo;
    String adu_nombre;
    String cod_tipmanifiesto;
    String nom_tipmanifiesto;
    String cod_tipmanifiesto_exp;
    String nom_tipmanifiesto_exp;
    String num_viaje;
    String nom_capitan;
    String cod_manifiesto;
    String cod_manifiesto_exp;
    Integer num_anio;
    String obs_itinerario;
    String nom_muelle;
    Date fec_arribo;
    String hor_arribo;
    Date fec_platica;
    String hor_platica;
    Date fec_muelle;
    String hor_muelle;
    Date fec_iniope;
    String hor_iniope;
    Date fec_finope;
    String hor_finope;
    Date fec_cutdry;
    String hor_cutdry;
    Date fec_cutssr;
    String hor_cutssr;
    Date fec_zarpe;
    String hor_zarpe;
    Date fec_ing;
    String usr_ing;
    Date fec_mod;
    String usr_mod;
    String dsp_itinerario;
    String dsp_itinerario_imp;
    String num_viaje_imp;
    String dsp_itinerario_exp;
    String num_viaje_exp;
    
    String est_itinerario;

    public Integer getIds_itinerario() {
        return ids_itinerario;
    }

    public void setIds_itinerario(Integer ids_itinerario) {
        this.ids_itinerario = ids_itinerario;
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

    public String getBuq_codigo() {
        return buq_codigo;
    }

    public void setBuq_codigo(String buq_codigo) {
        this.buq_codigo = buq_codigo;
    }

    public String getBuq_nombre() {
        return buq_nombre;
    }

    public void setBuq_nombre(String buq_nombre) {
        this.buq_nombre = buq_nombre;
    }

    public String getCod_ptoarribo() {
        return cod_ptoarribo;
    }

    public void setCod_ptoarribo(String cod_ptoarribo) {
        this.cod_ptoarribo = cod_ptoarribo;
    }

    public String getNom_ptoarribo() {
        return nom_ptoarribo;
    }

    public void setNom_ptoarribo(String nom_ptoarribo) {
        this.nom_ptoarribo = nom_ptoarribo;
    }

    public String getCod_ptoinicio() {
        return cod_ptoinicio;
    }

    public void setCod_ptoinicio(String cod_ptoinicio) {
        this.cod_ptoinicio = cod_ptoinicio;
    }

    public String getNom_ptoinicio() {
        return nom_ptoinicio;
    }

    public void setNom_ptoinicio(String nom_ptoinicio) {
        this.nom_ptoinicio = nom_ptoinicio;
    }

    public String getCod_ptoeta() {
        return cod_ptoeta;
    }

    public void setCod_ptoeta(String cod_ptoeta) {
        this.cod_ptoeta = cod_ptoeta;
    }

    public String getNom_ptoeta() {
        return nom_ptoeta;
    }

    public void setNom_ptoeta(String nom_ptoeta) {
        this.nom_ptoeta = nom_ptoeta;
    }

    public String getCod_ptofisico() {
        return cod_ptofisico;
    }

    public void setCod_ptofisico(String cod_ptofisico) {
        this.cod_ptofisico = cod_ptofisico;
    }

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

    public String getAdu_codigo() {
        return adu_codigo;
    }

    public void setAdu_codigo(String adu_codigo) {
        this.adu_codigo = adu_codigo;
    }

    public String getAdu_nombre() {
        return adu_nombre;
    }

    public void setAdu_nombre(String adu_nombre) {
        this.adu_nombre = adu_nombre;
    }

    public String getCod_tipmanifiesto() {
        return cod_tipmanifiesto;
    }

    public void setCod_tipmanifiesto(String cod_tipmanifiesto) {
        this.cod_tipmanifiesto = cod_tipmanifiesto;
    }

    public String getNom_tipmanifiesto() {
        return nom_tipmanifiesto;
    }

    public void setNom_tipmanifiesto(String nom_tipmanifiesto) {
        this.nom_tipmanifiesto = nom_tipmanifiesto;
    }

    public String getCod_tipmanifiesto_exp() {
        return cod_tipmanifiesto_exp;
    }

    public void setCod_tipmanifiesto_exp(String cod_tipmanifiesto_exp) {
        this.cod_tipmanifiesto_exp = cod_tipmanifiesto_exp;
    }

    public String getNom_tipmanifiesto_exp() {
        return nom_tipmanifiesto_exp;
    }

    public void setNom_tipmanifiesto_exp(String nom_tipmanifiesto_exp) {
        this.nom_tipmanifiesto_exp = nom_tipmanifiesto_exp;
    }

    public String getNum_viaje() {
        return num_viaje;
    }

    public void setNum_viaje(String num_viaje) {
        this.num_viaje = num_viaje;
    }

    public String getNom_capitan() {
        return nom_capitan;
    }

    public void setNom_capitan(String nom_capitan) {
        this.nom_capitan = nom_capitan;
    }

    public String getCod_manifiesto() {
        return cod_manifiesto;
    }

    public void setCod_manifiesto(String cod_manifiesto) {
        this.cod_manifiesto = cod_manifiesto;
    }

    public String getCod_manifiesto_exp() {
        return cod_manifiesto_exp;
    }

    public void setCod_manifiesto_exp(String cod_manifiesto_exp) {
        this.cod_manifiesto_exp = cod_manifiesto_exp;
    }

    public Integer getNum_anio() {
        return num_anio;
    }

    public void setNum_anio(Integer num_anio) {
        this.num_anio = num_anio;
    }

    public String getObs_itinerario() {
        return obs_itinerario;
    }

    public void setObs_itinerario(String obs_itinerario) {
        this.obs_itinerario = obs_itinerario;
    }

    public String getNom_muelle() {
        return nom_muelle;
    }

    public void setNom_muelle(String nom_muelle) {
        this.nom_muelle = nom_muelle;
    }

    public Date getFec_arribo() {
        return fec_arribo;
    }

    public void setFec_arribo(Date fec_arribo) {
        this.fec_arribo = fec_arribo;
    }

    public String getHor_arribo() {
        return hor_arribo;
    }

    public void setHor_arribo(String hor_arribo) {
        this.hor_arribo = hor_arribo;
    }

    public Date getFec_platica() {
        return fec_platica;
    }

    public void setFec_platica(Date fec_platica) {
        this.fec_platica = fec_platica;
    }

    public String getHor_platica() {
        return hor_platica;
    }

    public void setHor_platica(String hor_platica) {
        this.hor_platica = hor_platica;
    }

    public Date getFec_muelle() {
        return fec_muelle;
    }

    public void setFec_muelle(Date fec_muelle) {
        this.fec_muelle = fec_muelle;
    }

    public String getHor_muelle() {
        return hor_muelle;
    }

    public void setHor_muelle(String hor_muelle) {
        this.hor_muelle = hor_muelle;
    }

    public Date getFec_iniope() {
        return fec_iniope;
    }

    public void setFec_iniope(Date fec_iniope) {
        this.fec_iniope = fec_iniope;
    }

    public String getHor_iniope() {
        return hor_iniope;
    }

    public void setHor_iniope(String hor_iniope) {
        this.hor_iniope = hor_iniope;
    }

    public Date getFec_finope() {
        return fec_finope;
    }

    public void setFec_finope(Date fec_finope) {
        this.fec_finope = fec_finope;
    }

    public String getHor_finope() {
        return hor_finope;
    }

    public void setHor_finope(String hor_finope) {
        this.hor_finope = hor_finope;
    }

    public Date getFec_cutdry() {
        return fec_cutdry;
    }

    public void setFec_cutdry(Date fec_cutdry) {
        this.fec_cutdry = fec_cutdry;
    }

    public String getHor_cutdry() {
        return hor_cutdry;
    }

    public void setHor_cutdry(String hor_cutdry) {
        this.hor_cutdry = hor_cutdry;
    }

    public Date getFec_cutssr() {
        return fec_cutssr;
    }

    public void setFec_cutssr(Date fec_cutssr) {
        this.fec_cutssr = fec_cutssr;
    }

    public String getHor_cutssr() {
        return hor_cutssr;
    }

    public void setHor_cutssr(String hor_cutssr) {
        this.hor_cutssr = hor_cutssr;
    }

    public Date getFec_zarpe() {
        return fec_zarpe;
    }

    public void setFec_zarpe(Date fec_zarpe) {
        this.fec_zarpe = fec_zarpe;
    }

    public String getHor_zarpe() {
        return hor_zarpe;
    }

    public void setHor_zarpe(String hor_zarpe) {
        this.hor_zarpe = hor_zarpe;
    }

    public Date getFec_ing() {
        return fec_ing;
    }

    public void setFec_ing(Date fec_ing) {
        this.fec_ing = fec_ing;
    }

    public String getUsr_ing() {
        return usr_ing;
    }

    public void setUsr_ing(String usr_ing) {
        this.usr_ing = usr_ing;
    }

    public Date getFec_mod() {
        return fec_mod;
    }

    public void setFec_mod(Date fec_mod) {
        this.fec_mod = fec_mod;
    }

    public String getUsr_mod() {
        return usr_mod;
    }

    public void setUsr_mod(String usr_mod) {
        this.usr_mod = usr_mod;
    }

    public String getDsp_itinerario() {
        return dsp_itinerario;
    }

    public void setDsp_itinerario(String dsp_itinerario) {
        this.dsp_itinerario = dsp_itinerario;
    }

    public String getDsp_itinerario_imp() {
        return dsp_itinerario_imp;
    }

    public void setDsp_itinerario_imp(String dsp_itinerario_imp) {
        this.dsp_itinerario_imp = dsp_itinerario_imp;
    }

    public String getNum_viaje_imp() {
        return num_viaje_imp;
    }

    public void setNum_viaje_imp(String num_viaje_imp) {
        this.num_viaje_imp = num_viaje_imp;
    }

    public String getDsp_itinerario_exp() {
        return dsp_itinerario_exp;
    }

    public void setDsp_itinerario_exp(String dsp_itinerario_exp) {
        this.dsp_itinerario_exp = dsp_itinerario_exp;
    }

    public String getNum_viaje_exp() {
        return num_viaje_exp;
    }

    public void setNum_viaje_exp(String num_viaje_exp) {
        this.num_viaje_exp = num_viaje_exp;
    }

    public String getEst_itinerario() {
        return est_itinerario;
    }

    public void setEst_itinerario(String est_itinerario) {
        this.est_itinerario = est_itinerario;
    }
    
    
}
