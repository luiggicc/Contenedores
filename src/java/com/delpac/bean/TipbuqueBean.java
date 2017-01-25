/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.TipbuqueDAO;
import com.delpac.entity.Aduana;
import com.delpac.entity.Tipbuque;
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
public class TipbuqueBean implements Serializable{
    private List<Tipbuque> listadoTipbuque = new ArrayList<>();
    private List<Tipbuque> filteredTipbuque;
    private Usuario sessionUsuario;
    private Tipbuque tbu = new Tipbuque();
    private TipbuqueDAO daoTipbuque = new TipbuqueDAO();
    
        public void authorized() {
    }
    
    public TipbuqueBean(){
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
                listadoTipbuque = daoTipbuque.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Tipbuque tbuq) {
        tbu = tbuq;
    }

    public void onCancelDialog() {
        setTbu(new Tipbuque());
    }
    
    public void commitEdit() throws SQLException {
        daoTipbuque.editTipbuque(tbu);
        listadoTipbuque = daoTipbuque.findAll();
    }

    public void eliminar(Tipbuque tbuq) throws SQLException {
        daoTipbuque.deleteTipbuque(tbuq);
        listadoTipbuque = daoTipbuque.findAll();
    }

    public List<Tipbuque> getListadoTipbuque() {
        return listadoTipbuque;
    }

    public void setListadoTipbuque(List<Tipbuque> listadoTipbuque) {
        this.listadoTipbuque = listadoTipbuque;
    }

    public List<Tipbuque> getFilteredTipbuque() {
        return filteredTipbuque;
    }

    public void setFilteredTipbuque(List<Tipbuque> filteredTipbuque) {
        this.filteredTipbuque = filteredTipbuque;
    }

    public Tipbuque getTbu() {
        return tbu;
    }

    public void setTbu(Tipbuque tbu) {
        this.tbu = tbu;
    }

    public TipbuqueDAO getDaoTipbuque() {
        return daoTipbuque;
    }

    public void setDaoTipbuque(TipbuqueDAO daoTipbuque) {
        this.daoTipbuque = daoTipbuque;
    }
    
    
}
