/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.Sellos;
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
public class InventarioBean implements Serializable {
    private List<Sellos> listadoSellos = new ArrayList<>();
    private List<Sellos> listadoEliminados = new ArrayList<>();
    private List<Sellos> filteredSellos;
    private List<Sellos> filteredEliminados;
    private Usuario sessionUsuario;
    private Sellos sel = new Sellos();
    private SellosDAO daoSellos = new SellosDAO();
    
    private int idMotSelloSelected;
    private List<Sellos> selectorMotivoSello = new ArrayList<>();
    
    public void authorized() {
    }
    
    public InventarioBean(){
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
                SellosDAO daoSellos = new SellosDAO();
                selectorMotivoSello = daoSellos.findAllMotivos();
                
                listadoSellos = daoSellos.findAll();
                listadoEliminados = daoSellos.Eliminados();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Sellos sell) {
        sel = sell;
    }
    
    public void showDeleteDialog(Sellos sell) {
        sel = sell;
    }

    public void onCancelDialog() {
        setSel(new Sellos());
    }
    
    public void commitCreate() throws SQLException {
        daoSellos.createSellos(sel);
        listadoSellos = daoSellos.findAll();
    }
    
    public void commitEdit() throws SQLException {
        daoSellos.editSellos(sel);
        listadoSellos = daoSellos.findAll();
    }

    public void eliminar() throws SQLException {
        daoSellos.deleteSellos(sel, sessionUsuario);
        listadoSellos = daoSellos.findAll();
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

    public Sellos getSel() {
        return sel;
    }

    public void setSel(Sellos sel) {
        this.sel = sel;
    }

    public SellosDAO getDaoSellos() {
        return daoSellos;
    }

    public void setDaoSellos(SellosDAO daoSellos) {
        this.daoSellos = daoSellos;
    }

    public int getIdMotSelloSelected() {
        return idMotSelloSelected;
    }

    public void setIdMotSelloSelected(int idMotSelloSelected) {
        this.idMotSelloSelected = idMotSelloSelected;
    }

    public List<Sellos> getSelectorMotivoSello() {
        return selectorMotivoSello;
    }

    public void setSelectorMotivoSello(List<Sellos> selectorMotivoSello) {
        this.selectorMotivoSello = selectorMotivoSello;
    }

    public List<Sellos> getListadoEliminados() {
        return listadoEliminados;
    }

    public void setListadoEliminados(List<Sellos> listadoEliminados) {
        this.listadoEliminados = listadoEliminados;
    }

    public List<Sellos> getFilteredEliminados() {
        return filteredEliminados;
    }

    public void setFilteredEliminados(List<Sellos> filteredEliminados) {
        this.filteredEliminados = filteredEliminados;
    }
    
    
}
