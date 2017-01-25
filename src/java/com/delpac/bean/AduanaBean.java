/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.AduanaDAO;
import com.delpac.entity.Aduana;
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
public class AduanaBean implements Serializable{
    private List<Aduana> listadoAduanas = new ArrayList<>();
    private List<Aduana> filteredAduanas;
    private Usuario sessionUsuario;
    private Aduana adu = new Aduana();
    private AduanaDAO daoAduana = new AduanaDAO();
    
    public void authorized() {
    }
    
    public AduanaBean(){
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
                listadoAduanas = daoAduana.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Aduana adua) {
        adu = adua;
    }

    public void onCancelDialog() {
        setAdu(new Aduana());
    }
    
    public void commitEdit() throws SQLException {
        daoAduana.editAduana(adu);
        listadoAduanas = daoAduana.findAll();
    }

    public void eliminar(Aduana adua) throws SQLException {
        daoAduana.deleteAduana(adua);
        listadoAduanas = daoAduana.findAll();
    }

    public List<Aduana> getListadoAduanas() {
        return listadoAduanas;
    }

    public void setListadoAduanas(List<Aduana> listadoAduanas) {
        this.listadoAduanas = listadoAduanas;
    }

    public List<Aduana> getFilteredAduanas() {
        return filteredAduanas;
    }

    public void setFilteredAduanas(List<Aduana> filteredAduanas) {
        this.filteredAduanas = filteredAduanas;
    }

    public AduanaDAO getDaoAduana() {
        return daoAduana;
    }

    public void setDaoAduana(AduanaDAO daoAduana) {
        this.daoAduana = daoAduana;
    }

    public Aduana getAdu() {
        return adu;
    }

    public void setAdu(Aduana adu) {
        this.adu = adu;
    }
}
