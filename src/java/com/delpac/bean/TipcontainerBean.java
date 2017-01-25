/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.TipcontainerDAO;
import com.delpac.entity.Tipcontainer;
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
public class TipcontainerBean implements Serializable{
    private List<Tipcontainer> listadoTipcontainers = new ArrayList<>();
    private List<Tipcontainer> filteredTipcontainers;
    private Usuario sessionUsuario;
    private Tipcontainer tcon = new Tipcontainer();
    private TipcontainerDAO daoTipcontainer = new TipcontainerDAO();
    
    public void authorized() {
    }
    
    public TipcontainerBean(){
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
                listadoTipcontainers = daoTipcontainer.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Tipcontainer tcont) {
        tcon = tcont;
    }

    public void onCancelDialog() {
        setTcon(new Tipcontainer());
    }
    
    public void commitEdit() throws SQLException {
        daoTipcontainer.editTipcontainer(tcon);
        listadoTipcontainers = daoTipcontainer.findAll();
    }

    public void eliminar(Tipcontainer tcont) throws SQLException {
        daoTipcontainer.deleteTipcontainer(tcont);
        listadoTipcontainers = daoTipcontainer.findAll();
    }

    public List<Tipcontainer> getListadoTipcontainers() {
        return listadoTipcontainers;
    }

    public void setListadoTipcontainers(List<Tipcontainer> listadoTipcontainers) {
        this.listadoTipcontainers = listadoTipcontainers;
    }

    public List<Tipcontainer> getFilteredTipcontainers() {
        return filteredTipcontainers;
    }

    public void setFilteredTipcontainers(List<Tipcontainer> filteredTipcontainers) {
        this.filteredTipcontainers = filteredTipcontainers;
    }

    public Tipcontainer getTcon() {
        return tcon;
    }

    public void setTcon(Tipcontainer tcon) {
        this.tcon = tcon;
    }

    public TipcontainerDAO getDaoTipcontainer() {
        return daoTipcontainer;
    }

    public void setDaoTipcontainer(TipcontainerDAO daoTipcontainer) {
        this.daoTipcontainer = daoTipcontainer;
    }
    
    
}
