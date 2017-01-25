/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.CondicionDAO;
import com.delpac.entity.Condicion;
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
public class CondicionBean implements Serializable{
    private List<Condicion> listadoCondiciones = new ArrayList<>();
    private List<Condicion> filteredCondiciones;
    private Usuario sessionUsuario;
    private Condicion cond = new Condicion();
    private CondicionDAO daoCondicion = new CondicionDAO();
    
    public void authorized() {
    }
    
    public CondicionBean(){
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
                listadoCondiciones = daoCondicion.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Condicion condi) {
        cond = condi;
    }

    public void onCancelDialog() {
        setCond(new Condicion());
    }
    
    public void commitEdit() throws SQLException {
        daoCondicion.editCondicion(cond);
        listadoCondiciones = daoCondicion.findAll();
    }

    public void eliminar(Condicion condi) throws SQLException {
        daoCondicion.deleteCondicion(condi);
        listadoCondiciones = daoCondicion.findAll();
    }

    public List<Condicion> getListadoCondiciones() {
        return listadoCondiciones;
    }

    public void setListadoCondiciones(List<Condicion> listadoCondiciones) {
        this.listadoCondiciones = listadoCondiciones;
    }

    public List<Condicion> getFilteredCondiciones() {
        return filteredCondiciones;
    }

    public void setFilteredCondiciones(List<Condicion> filteredCondiciones) {
        this.filteredCondiciones = filteredCondiciones;
    }

    public Condicion getCond() {
        return cond;
    }

    public void setCond(Condicion cond) {
        this.cond = cond;
    }

    public CondicionDAO getDaoCondicion() {
        return daoCondicion;
    }

    public void setDaoCondicion(CondicionDAO daoCondicion) {
        this.daoCondicion = daoCondicion;
    }
    
    
}
