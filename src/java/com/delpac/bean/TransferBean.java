/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.TransferDAO;
import com.delpac.dao.LocalidadDAO;
import com.delpac.entity.Sellos;
import com.delpac.entity.Localidad;
import com.delpac.entity.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class TransferBean implements Serializable {
    private LocalidadDAO daoLocalidad = new LocalidadDAO();
    private List<Localidad> listaLocalidades = daoLocalidad.findAllLocalTransfer();
    private int LocalidadIdSelected;
    
    private List<Sellos> listadoSellos = new ArrayList<>();
    private List<Sellos> filteredSellos;
    private TransferDAO daoTransfer = new TransferDAO();
    private Usuario sessionUsuario;

    public void authorized(){}
    
    public TransferBean() {
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
                listaLocalidades = daoLocalidad.findAllLocalTransfer();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void obtenerSellosLocalidad() {
        setListadoSellos(daoTransfer.sellosAsignadosbyLocalidad(LocalidadIdSelected));
    }

    public void asdasdas() {
        getListadoSellos().clear();
        setLocalidadIdSelected(0);
    }

    public void guardarPermisos() throws SQLException {
        daoTransfer.saveResourcesbyLocalidad(getListadoSellos(), LocalidadIdSelected);
        obtenerSellosLocalidad();
    }

    public LocalidadDAO getDaoLocalidad() {
        return daoLocalidad;
    }

    public void setDaoLocalidad(LocalidadDAO daoLocalidad) {
        this.daoLocalidad = daoLocalidad;
    }

    public List<Localidad> getListaLocalidades() {
        return listaLocalidades;
    }

    public void setListaLocalidades(List<Localidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    public int getLocalidadIdSelected() {
        return LocalidadIdSelected;
    }

    public void setLocalidadIdSelected(int LocalidadIdSelected) {
        this.LocalidadIdSelected = LocalidadIdSelected;
    }

    public List<Sellos> getListadoSellos() {
        return listadoSellos;
    }

    public void setListadoSellos(List<Sellos> listadoSellos) {
        this.listadoSellos = listadoSellos;
    }

    public List<Sellos> getFilteredSellos() {
        return filteredSellos;
    }

    public void setFilteredSellos(List<Sellos> filteredSellos) {
        this.filteredSellos = filteredSellos;
    }

    public TransferDAO getDaoTransfer() {
        return daoTransfer;
    }

    public void setDaoTransfer(TransferDAO daoTransfer) {
        this.daoTransfer = daoTransfer;
    }
    
    
}
