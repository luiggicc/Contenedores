/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.TraficoDAO;
import com.delpac.entity.Trafico;
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
public class TraficoBean implements Serializable{
    private List<Trafico> listadoTraficos = new ArrayList<>();
    private List<Trafico> filteredTraficos;
    private Usuario sessionUsuario;
    private Trafico tra = new Trafico();
    private TraficoDAO daoTrafico = new TraficoDAO();
    
    public void authorized() {
    }
    
    public TraficoBean(){
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
                listadoTraficos = daoTrafico.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Trafico traf) {
        tra = traf;
    }

    public void onCancelDialog() {
        setTra(new Trafico());
    }
    
    public void commitEdit() throws SQLException {
        daoTrafico.editTrafico(tra);
        listadoTraficos = daoTrafico.findAll();
    }

    public void eliminar(Trafico traf) throws SQLException {
        daoTrafico.deleteTrafico(traf);
        listadoTraficos = daoTrafico.findAll();
    }

    public List<Trafico> getListadoTraficos() {
        return listadoTraficos;
    }

    public void setListadoTraficos(List<Trafico> listadoTraficos) {
        this.listadoTraficos = listadoTraficos;
    }

    public List<Trafico> getFilteredTraficos() {
        return filteredTraficos;
    }

    public void setFilteredTraficos(List<Trafico> filteredTraficos) {
        this.filteredTraficos = filteredTraficos;
    }

    public Trafico getTra() {
        return tra;
    }

    public void setTra(Trafico tra) {
        this.tra = tra;
    }

    public TraficoDAO getDaoTrafico() {
        return daoTrafico;
    }

    public void setDaoTrafico(TraficoDAO daoTrafico) {
        this.daoTrafico = daoTrafico;
    }
    
    
}
