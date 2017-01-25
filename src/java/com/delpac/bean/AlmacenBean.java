/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.AlmacenDAO;
import com.delpac.entity.Almacen;
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
public class AlmacenBean implements Serializable {

    private List<Almacen> listadoAlmacenes = new ArrayList<>();
    private List<Almacen> filteredAlmacenes;
    private Usuario sessionUsuario;
    private Almacen alm = new Almacen();
    private AlmacenDAO daoAlmacen = new AlmacenDAO();

    public void authorized() {
    }

    public AlmacenBean() {
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
                listadoAlmacenes = daoAlmacen.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Almacen alma) {
        alm = alma;
    }

    public void onCancelDialog() {
        setAlm(new Almacen());
    }

    public void commitEdit() throws SQLException {
        daoAlmacen.editAlmacen(alm);
        listadoAlmacenes = daoAlmacen.findAll();
    }

    public void eliminar(Almacen alma) throws SQLException {
        daoAlmacen.deleteAlmacen(alma);
        listadoAlmacenes = daoAlmacen.findAll();
    }

    public List<Almacen> getListadoAlmacenes() {
        return listadoAlmacenes;
    }

    public void setListadoAlmacenes(List<Almacen> listadoAlmacenes) {
        this.listadoAlmacenes = listadoAlmacenes;
    }

    public List<Almacen> getFilteredAlmacenes() {
        return filteredAlmacenes;
    }

    public void setFilteredAlmacenes(List<Almacen> filteredAlmacenes) {
        this.filteredAlmacenes = filteredAlmacenes;
    }

    public Almacen getAlm() {
        return alm;
    }

    public void setAlm(Almacen alm) {
        this.alm = alm;
    }

    public AlmacenDAO getDaoAlmacen() {
        return daoAlmacen;
    }

    public void setDaoAlmacen(AlmacenDAO daoAlmacen) {
        this.daoAlmacen = daoAlmacen;
    }
    
    
}
