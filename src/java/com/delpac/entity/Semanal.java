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
public class Semanal implements Serializable{
    Date desde;
    Date hasta;
    int item;
    String nave;
    String viaje;
    Date fecha_arribo;
    int cont_import;
    int teus_import;
    int cont_empty_import;
    int teus_empty_import;
    int total_cont_import;
    int total_teus_import;
    int cont_full_export;
    int teus_full_export;
    int cont_empty_export;
    int teus_empty_export;
    int total_cont_export;
    int total_teus_export;

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    public String getViaje() {
        return viaje;
    }

    public void setViaje(String viaje) {
        this.viaje = viaje;
    }

    public Date getFecha_arribo() {
        return fecha_arribo;
    }

    public void setFecha_arribo(Date fecha_arribo) {
        this.fecha_arribo = fecha_arribo;
    }

    public int getCont_import() {
        return cont_import;
    }

    public void setCont_import(int cont_import) {
        this.cont_import = cont_import;
    }

    public int getTeus_import() {
        return teus_import;
    }

    public void setTeus_import(int teus_import) {
        this.teus_import = teus_import;
    }

    public int getCont_empty_import() {
        return cont_empty_import;
    }

    public void setCont_empty_import(int cont_empty_import) {
        this.cont_empty_import = cont_empty_import;
    }

    public int getTeus_empty_import() {
        return teus_empty_import;
    }

    public void setTeus_empty_import(int teus_empty_import) {
        this.teus_empty_import = teus_empty_import;
    }

    public int getTotal_cont_import() {
        return total_cont_import;
    }

    public void setTotal_cont_import(int total_cont_import) {
        this.total_cont_import = total_cont_import;
    }

    public int getTotal_teus_import() {
        return total_teus_import;
    }

    public void setTotal_teus_import(int total_teus_import) {
        this.total_teus_import = total_teus_import;
    }

    public int getCont_full_export() {
        return cont_full_export;
    }

    public void setCont_full_export(int cont_full_export) {
        this.cont_full_export = cont_full_export;
    }

    public int getTeus_full_export() {
        return teus_full_export;
    }

    public void setTeus_full_export(int teus_full_export) {
        this.teus_full_export = teus_full_export;
    }

    public int getCont_empty_export() {
        return cont_empty_export;
    }

    public void setCont_empty_export(int cont_empty_export) {
        this.cont_empty_export = cont_empty_export;
    }

    public int getTeus_empty_export() {
        return teus_empty_export;
    }

    public void setTeus_empty_export(int teus_empty_export) {
        this.teus_empty_export = teus_empty_export;
    }

    public int getTotal_cont_export() {
        return total_cont_export;
    }

    public void setTotal_cont_export(int total_cont_export) {
        this.total_cont_export = total_cont_export;
    }

    public int getTotal_teus_export() {
        return total_teus_export;
    }

    public void setTotal_teus_export(int total_teus_export) {
        this.total_teus_export = total_teus_export;
    }
    
    
}
