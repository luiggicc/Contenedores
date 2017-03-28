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
public class Dashboard implements Serializable{
    String tipo_cont;
    int conteo_tipo_cont;
    String movimiento;
    String status;
    int conteo_detalle_contenedor;
    int conteo_puerto;
    int conteo_cliente;
    int conteo_vacio;

    public String getTipo_cont() {
        return tipo_cont;
    }

    public void setTipo_cont(String tipo_cont) {
        this.tipo_cont = tipo_cont;
    }

    public int getConteo_tipo_cont() {
        return conteo_tipo_cont;
    }

    public void setConteo_tipo_cont(int conteo_tipo_cont) {
        this.conteo_tipo_cont = conteo_tipo_cont;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConteo_detalle_contenedor() {
        return conteo_detalle_contenedor;
    }

    public void setConteo_detalle_contenedor(int conteo_detalle_contenedor) {
        this.conteo_detalle_contenedor = conteo_detalle_contenedor;
    }

    public int getConteo_puerto() {
        return conteo_puerto;
    }

    public void setConteo_puerto(int conteo_puerto) {
        this.conteo_puerto = conteo_puerto;
    }

    public int getConteo_cliente() {
        return conteo_cliente;
    }

    public void setConteo_cliente(int conteo_cliente) {
        this.conteo_cliente = conteo_cliente;
    }

    public int getConteo_vacio() {
        return conteo_vacio;
    }

    public void setConteo_vacio(int conteo_vacio) {
        this.conteo_vacio = conteo_vacio;
    }
    
    
}
