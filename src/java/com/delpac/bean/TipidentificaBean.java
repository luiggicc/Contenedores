/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.TipidentificaDAO;
import com.delpac.entity.Tipidentifica;
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
public class TipidentificaBean implements Serializable {

    private List<Tipidentifica> listadoTipidentificador = new ArrayList<>();
    private List<Tipidentifica> filteredTipidentificador;
    private Usuario sessionUsuario;
    private Tipidentifica tip = new Tipidentifica();
    private TipidentificaDAO daoTipidentifica = new TipidentificaDAO();

    public void authorized() {
    }

    public TipidentificaBean() {
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
                listadoTipidentificador = daoTipidentifica.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Tipidentifica tipi) {
        tip = tipi;
    }

    public void onCancelDialog() {
        setTip(new Tipidentifica());
    }

    public void commitEdit() throws SQLException {
        daoTipidentifica.editTipidentifica(tip);
        listadoTipidentificador = daoTipidentifica.findAll();
    }

    public void eliminar(Tipidentifica tipi) throws SQLException {
        daoTipidentifica.deleteTipidentifica(tipi);
        listadoTipidentificador = daoTipidentifica.findAll();
    }

    public List<Tipidentifica> getListadoTipidentificador() {
        return listadoTipidentificador;
    }

    public void setListadoTipidentificador(List<Tipidentifica> listadoTipidentificador) {
        this.listadoTipidentificador = listadoTipidentificador;
    }

    public List<Tipidentifica> getFilteredTipidentificador() {
        return filteredTipidentificador;
    }

    public void setFilteredTipidentificador(List<Tipidentifica> filteredTipidentificador) {
        this.filteredTipidentificador = filteredTipidentificador;
    }

    public Tipidentifica getTip() {
        return tip;
    }

    public void setTip(Tipidentifica tip) {
        this.tip = tip;
    }

    public TipidentificaDAO getDaoTipidentifica() {
        return daoTipidentifica;
    }

    public void setDaoTipidentifica(TipidentificaDAO daoTipidentifica) {
        this.daoTipidentifica = daoTipidentifica;
    }
    
    
}
