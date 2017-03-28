/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.ComparadorDAO;
import com.delpac.dao.ItinerarioDAO;

import com.delpac.entity.Comparador;
import com.delpac.entity.Itinerario;
import com.delpac.entity.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class ComparadorBean implements Serializable {

    private ItinerarioDAO daoItinerario = new ItinerarioDAO();
    private List<Itinerario> listaItinerarios = daoItinerario.findAllItinerario();
    private String ItinerarioDSPSelected;

    private List<Comparador> listadoComparador = new ArrayList<>();
    private List<Comparador> filteredComparador;
    private ComparadorDAO daoComparador = new ComparadorDAO();
    private Usuario sessionUsuario;

    public void authorized() {
    }

    public ComparadorBean(){
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
                listaItinerarios = daoItinerario.findAllItinerario();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void CompararContenedores() {
        setListadoComparador(daoComparador.CompararContenedores(ItinerarioDSPSelected));
    }

    public ItinerarioDAO getDaoItinerario() {
        return daoItinerario;
    }

    public void setDaoItinerario(ItinerarioDAO daoItinerario) {
        this.daoItinerario = daoItinerario;
    }

    public List<Itinerario> getListaItinerarios() {
        return listaItinerarios;
    }

    public void setListaItinerarios(List<Itinerario> listaItinerarios) {
        this.listaItinerarios = listaItinerarios;
    }

    public String getItinerarioDSPSelected() {
        return ItinerarioDSPSelected;
    }

    public void setItinerarioDSPSelected(String ItinerarioDSPSelected) {
        this.ItinerarioDSPSelected = ItinerarioDSPSelected;
    }

    public List<Comparador> getListadoComparador() {
        return listadoComparador;
    }

    public void setListadoComparador(List<Comparador> listadoComparador) {
        this.listadoComparador = listadoComparador;
    }

    public List<Comparador> getFilteredComparador() {
        return filteredComparador;
    }

    public void setFilteredComparador(List<Comparador> filteredComparador) {
        this.filteredComparador = filteredComparador;
    }

    public ComparadorDAO getDaoComparador() {
        return daoComparador;
    }

    public void setDaoComparador(ComparadorDAO daoComparador) {
        this.daoComparador = daoComparador;
    }
    
    
}
