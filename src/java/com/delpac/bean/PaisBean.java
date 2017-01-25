/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.PaisDAO;
import com.delpac.entity.Pais;
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
public class PaisBean implements Serializable{
    private List<Pais> listadoPaises = new ArrayList<>();
    private List<Pais> filteredPaises;
    private Usuario sessionUsuario;
    private Pais pai = new Pais();
    private PaisDAO daoPais = new PaisDAO();
    
    public void authorized() {
    }
    
    public PaisBean(){
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
                listadoPaises = daoPais.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Pais pais) {
        pai = pais;
    }

    public void onCancelDialog() {
        setPai(new Pais());
    }
    
    public void commitEdit() throws SQLException {
        daoPais.editPais(pai);
        listadoPaises = daoPais.findAll();
    }

    public void eliminar(Pais pais) throws SQLException {
        daoPais.deletePais(pais);
        listadoPaises = daoPais.findAll();
    }

    public List<Pais> getListadoPaises() {
        return listadoPaises;
    }

    public void setListadoPaises(List<Pais> listadoPaises) {
        this.listadoPaises = listadoPaises;
    }

    public List<Pais> getFilteredPaises() {
        return filteredPaises;
    }

    public void setFilteredPaises(List<Pais> filteredPaises) {
        this.filteredPaises = filteredPaises;
    }

    public Pais getPai() {
        return pai;
    }

    public void setPai(Pais pai) {
        this.pai = pai;
    }

    public PaisDAO getDaoPais() {
        return daoPais;
    }

    public void setDaoPais(PaisDAO daoPais) {
        this.daoPais = daoPais;
    }
    
    
}
