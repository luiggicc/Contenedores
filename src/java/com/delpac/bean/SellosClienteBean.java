/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.SellosClienteDAO;
import com.delpac.entity.SellosCliente;

import com.delpac.entity.Usuario;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class SellosClienteBean {
    private List<SellosCliente> listadoSellosCliente = new ArrayList<>();
    private List<SellosCliente> filteredSellosCliente;
    private Usuario sessionUsuario;
    private SellosClienteDAO daoSellosCliente = new SellosClienteDAO();
    
    public void authorized() {
    }

    public SellosClienteBean() {
        try {
            sessionUsuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Usuario");
            if (sessionUsuario == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Usuario");
                String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SesionExpirada");
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } else {
                /**
                 * se ejecutan las lineas del constructor**
                 */
                
                listadoSellosCliente = daoSellosCliente.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public List<SellosCliente> getListadoSellosCliente() {
        return listadoSellosCliente;
    }

    public void setListadoSellosCliente(List<SellosCliente> listadoSellosCliente) {
        this.listadoSellosCliente = listadoSellosCliente;
    }

    public List<SellosCliente> getFilteredSellosCliente() {
        return filteredSellosCliente;
    }

    public void setFilteredSellosCliente(List<SellosCliente> filteredSellosCliente) {
        this.filteredSellosCliente = filteredSellosCliente;
    }

    public SellosClienteDAO getDaoSellosCliente() {
        return daoSellosCliente;
    }

    public void setDaoSellosCliente(SellosClienteDAO daoSellosCliente) {
        this.daoSellosCliente = daoSellosCliente;
    }
    
    
    
}
