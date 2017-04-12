/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.SelloBodegaDAO;
import com.delpac.entity.SelloBodega;
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
public class SelloBodegaBean implements Serializable{
    private List<SelloBodega> listadoSelloBodega = new ArrayList<>();
    private List<SelloBodega> listadoSelloBodegaLog = new ArrayList<>();
    private List<SelloBodega> filteredSellosBodega;
    private List<SelloBodega> filteredSellosBodegaLog;
    private Usuario sessionUsuario;
    
    private SelloBodega sbo = new SelloBodega();
    private SelloBodegaDAO daoSelloBodega = new SelloBodegaDAO();
    
    public void authorized() {
    }
    
    public SelloBodegaBean(){
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
                SelloBodegaDAO daoSelloBodega = new SelloBodegaDAO();
                listadoSelloBodega = daoSelloBodega.ConsultaSelloBodega();
                listadoSelloBodegaLog = daoSelloBodega.ConsultaSelloBodegLog();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void onCancelDialog() {
        setSbo(new SelloBodega());
    }

    public void commitCreate() throws SQLException {
        daoSelloBodega.createSelloBodega(sbo);
        listadoSelloBodega = daoSelloBodega.ConsultaSelloBodega();
    }
    
    public List<SelloBodega> getListadoSelloBodega() {
        return listadoSelloBodega;
    }

    public void setListadoSelloBodega(List<SelloBodega> listadoSelloBodega) {
        this.listadoSelloBodega = listadoSelloBodega;
    }

    public List<SelloBodega> getListadoSelloBodegaLog() {
        return listadoSelloBodegaLog;
    }

    public void setListadoSelloBodegaLog(List<SelloBodega> listadoSelloBodegaLog) {
        this.listadoSelloBodegaLog = listadoSelloBodegaLog;
    }

    public List<SelloBodega> getFilteredSellosBodega() {
        return filteredSellosBodega;
    }

    public void setFilteredSellosBodega(List<SelloBodega> filteredSellosBodega) {
        this.filteredSellosBodega = filteredSellosBodega;
    }

    public SelloBodega getSbo() {
        return sbo;
    }

    public void setSbo(SelloBodega sbo) {
        this.sbo = sbo;
    }

    public SelloBodegaDAO getDaoSelloBodega() {
        return daoSelloBodega;
    }

    public void setDaoSelloBodega(SelloBodegaDAO daoSelloBodega) {
        this.daoSelloBodega = daoSelloBodega;
    }

    public List<SelloBodega> getFilteredSellosBodegaLog() {
        return filteredSellosBodegaLog;
    }

    public void setFilteredSellosBodegaLog(List<SelloBodega> filteredSellosBodegaLog) {
        this.filteredSellosBodegaLog = filteredSellosBodegaLog;
    }
    
}
