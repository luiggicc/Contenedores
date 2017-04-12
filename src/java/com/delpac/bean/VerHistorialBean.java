/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.VerHistorialDAO;
import com.delpac.dao.ContainerDAO;
import com.delpac.dao.PuertoDAO;

import com.delpac.entity.VerHistorial;
import com.delpac.entity.Container;
import com.delpac.entity.Puerto;
import com.delpac.entity.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class VerHistorialBean implements Serializable {

    private ContainerDAO daoContainer = new ContainerDAO();
    private List<Container> listaContainers = daoContainer.findAll();
    private String ContainerDesSelected;

    private List<VerHistorial> listadoVerHistorial = new ArrayList<>();
    private List<VerHistorial> filteredVerHistorial;
    
    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();
    
    private VerHistorialDAO daoVerHistorial = new VerHistorialDAO();
    private VerHistorial vhis = new VerHistorial();
    private Usuario sessionUsuario;
    boolean ciclomaximo;

    public void authorized() {
    }

    public VerHistorialBean() {
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
                listaContainers = daoContainer.findAll();
                
                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void VerHistorial() throws SQLException {
        setListadoVerHistorial(daoVerHistorial.findAll(ciclomaximo, ContainerDesSelected));
    }
    
    public void commitCreate() throws SQLException {
        daoVerHistorial.createMovimiento(vhis);
    }

    public ContainerDAO getDaoContainer() {
        return daoContainer;
    }

    public void setDaoContainer(ContainerDAO daoContainer) {
        this.daoContainer = daoContainer;
    }

    public List<Container> getListaContainers() {
        return listaContainers;
    }

    public void setListaContainers(List<Container> listaContainers) {
        this.listaContainers = listaContainers;
    }

    public String getContainerDesSelected() {
        return ContainerDesSelected;
    }

    public void setContainerDesSelected(String ContainerDesSelected) {
        this.ContainerDesSelected = ContainerDesSelected;
    }

    public List<VerHistorial> getListadoVerHistorial() {
        return listadoVerHistorial;
    }

    public void setListadoVerHistorial(List<VerHistorial> listadoVerHistorial) {
        this.listadoVerHistorial = listadoVerHistorial;
    }

    public List<VerHistorial> getFilteredVerHistorial() {
        return filteredVerHistorial;
    }

    public void setFilteredVerHistorial(List<VerHistorial> filteredVerHistorial) {
        this.filteredVerHistorial = filteredVerHistorial;
    }

    public VerHistorialDAO getDaoVerHistorial() {
        return daoVerHistorial;
    }

    public void setDaoVerHistorial(VerHistorialDAO daoVerHistorial) {
        this.daoVerHistorial = daoVerHistorial;
    }

    public boolean isCiclomaximo() {
        return ciclomaximo;
    }

    public void setCiclomaximo(boolean ciclomaximo) {
        this.ciclomaximo = ciclomaximo;
    }

    public VerHistorial getVhis() {
        return vhis;
    }

    public void setVhis(VerHistorial vhis) {
        this.vhis = vhis;
    }

    public int getIdPuertoSelected() {
        return idPuertoSelected;
    }

    public void setIdPuertoSelected(int idPuertoSelected) {
        this.idPuertoSelected = idPuertoSelected;
    }

    public List<Puerto> getSelectorPuerto() {
        return selectorPuerto;
    }

    public void setSelectorPuerto(List<Puerto> selectorPuerto) {
        this.selectorPuerto = selectorPuerto;
    }

    
}
