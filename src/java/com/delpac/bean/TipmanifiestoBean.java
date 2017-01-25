/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.TipmanifiestoDAO;
import com.delpac.entity.Tipmanifiesto;
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
public class TipmanifiestoBean implements Serializable {

    private List<Tipmanifiesto> listadoTipmanifiestos = new ArrayList<>();
    private List<Tipmanifiesto> filteredTipmanifiestos;
    private Usuario sessionUsuario;
    private Tipmanifiesto tman = new Tipmanifiesto();
    private TipmanifiestoDAO daoTipmanifiesto = new TipmanifiestoDAO();

    public void authorized() {
    }

    public TipmanifiestoBean() {
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
                listadoTipmanifiestos = daoTipmanifiesto.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Tipmanifiesto tmani) {
        tman = tmani;
    }

    public void onCancelDialog() {
        setTman(new Tipmanifiesto());
    }

    public void commitEdit() throws SQLException {
        daoTipmanifiesto.editTipmanifiesto(tman);
        listadoTipmanifiestos = daoTipmanifiesto.findAll();
    }

    public void eliminar(Tipmanifiesto tmani) throws SQLException {
        daoTipmanifiesto.deleteTipmanifiesto(tmani);
        listadoTipmanifiestos = daoTipmanifiesto.findAll();
    }

    public List<Tipmanifiesto> getListadoTipmanifiestos() {
        return listadoTipmanifiestos;
    }

    public void setListadoTipmanifiestos(List<Tipmanifiesto> listadoTipmanifiestos) {
        this.listadoTipmanifiestos = listadoTipmanifiestos;
    }

    public List<Tipmanifiesto> getFilteredTipmanifiestos() {
        return filteredTipmanifiestos;
    }

    public void setFilteredTipmanifiestos(List<Tipmanifiesto> filteredTipmanifiestos) {
        this.filteredTipmanifiestos = filteredTipmanifiestos;
    }

    public Tipmanifiesto getTman() {
        return tman;
    }

    public void setTman(Tipmanifiesto tman) {
        this.tman = tman;
    }

    public TipmanifiestoDAO getDaoTipmanifiesto() {
        return daoTipmanifiesto;
    }

    public void setDaoTipmanifiesto(TipmanifiestoDAO daoTipmanifiesto) {
        this.daoTipmanifiesto = daoTipmanifiesto;
    }
    
    
}
