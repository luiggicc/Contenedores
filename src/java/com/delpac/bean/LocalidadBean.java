/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.LocalidadDAO;
import com.delpac.entity.Localidad;
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
public class LocalidadBean implements Serializable{
   private List<Localidad> listadoLocalidades = new ArrayList<>();
    private List<Localidad> filteredLocalidades;
    private Usuario sessionUsuario;
    private Localidad loc = new Localidad();
    private LocalidadDAO daoLocalidad = new LocalidadDAO();
    
    public void authorized() {
    }
    
    public LocalidadBean(){
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
                listadoLocalidades = daoLocalidad.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Localidad loca) {
        loc = loca;
    }

    public void onCancelDialog() {
        setLoc(new Localidad());
    }
    
    public void commitEdit() throws SQLException {
        daoLocalidad.editLocalidad(loc);
        listadoLocalidades = daoLocalidad.findAll();
    }

    public void eliminar(Localidad loca) throws SQLException {
        daoLocalidad.deleteLocalidad(loca);
        listadoLocalidades = daoLocalidad.findAll();
    } 

    public List<Localidad> getListadoLocalidades() {
        return listadoLocalidades;
    }

    public void setListadoLocalidades(List<Localidad> listadoLocalidades) {
        this.listadoLocalidades = listadoLocalidades;
    }

    public List<Localidad> getFilteredLocalidades() {
        return filteredLocalidades;
    }

    public void setFilteredLocalidades(List<Localidad> filteredLocalidades) {
        this.filteredLocalidades = filteredLocalidades;
    }

    public Localidad getLoc() {
        return loc;
    }

    public void setLoc(Localidad loc) {
        this.loc = loc;
    }

    public LocalidadDAO getDaoLocalidad() {
        return daoLocalidad;
    }

    public void setDaoLocalidad(LocalidadDAO daoLocalidad) {
        this.daoLocalidad = daoLocalidad;
    }
    
    
}
