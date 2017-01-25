/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.PuertoDAO;
import com.delpac.dao.PaisDAO;
import com.delpac.entity.Puerto;
import com.delpac.entity.Pais;
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
public class PuertoBean implements Serializable {

    private List<Puerto> listadoPuertos = new ArrayList<>();
    private List<Puerto> filteredPuertos;
    private Usuario sessionUsuario;
    private Puerto pue = new Puerto();
    private PuertoDAO daoPuerto = new PuertoDAO();

    private int idPaisSelected;
    private List<Pais> selectorPais = new ArrayList<>();

    public void authorized() {
    }

    public PuertoBean() {
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
                PaisDAO daoPais = new PaisDAO();
                selectorPais = daoPais.findAll();
                listadoPuertos = daoPuerto.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Puerto puer) {
        pue = puer;
    }

    public void onCancelDialog() {
        setPue(new Puerto());
        setIdPaisSelected(0);
    }

    public void commitEdit() throws SQLException {
        daoPuerto.editPuerto(pue);
        listadoPuertos = daoPuerto.findAll();
    }

    public void eliminar(Puerto puer) throws SQLException {
        daoPuerto.deletePuerto(puer);
        listadoPuertos = daoPuerto.findAll();
    }

    public List<Puerto> getListadoPuertos() {
        return listadoPuertos;
    }

    public void setListadoPuertos(List<Puerto> listadoPuertos) {
        this.listadoPuertos = listadoPuertos;
    }

    public List<Puerto> getFilteredPuertos() {
        return filteredPuertos;
    }

    public void setFilteredPuertos(List<Puerto> filteredPuertos) {
        this.filteredPuertos = filteredPuertos;
    }

    public Puerto getPue() {
        return pue;
    }

    public void setPue(Puerto pue) {
        this.pue = pue;
    }

    public PuertoDAO getDaoPuerto() {
        return daoPuerto;
    }

    public void setDaoPuerto(PuertoDAO daoPuerto) {
        this.daoPuerto = daoPuerto;
    }

    public int getIdPaisSelected() {
        return idPaisSelected;
    }

    public void setIdPaisSelected(int idPaisSelected) {
        this.idPaisSelected = idPaisSelected;
    }

    public List<Pais> getSelectorPais() {
        return selectorPais;
    }

    public void setSelectorPais(List<Pais> selectorPais) {
        this.selectorPais = selectorPais;
    }
    
    
}
