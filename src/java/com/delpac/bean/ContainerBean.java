/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.ContainerDAO;
import com.delpac.dao.LineaDAO;
import com.delpac.dao.TipcontainerDAO;

import com.delpac.entity.Container;
import com.delpac.entity.Linea;
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
public class ContainerBean implements Serializable {

    private List<Container> listadoContainers = new ArrayList<>();
    private List<Container> filteredContainers;
    private Usuario sessionUsuario;
    private Container conta = new Container();
    private ContainerDAO daoContainer = new ContainerDAO();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idTipContSelected;
    private List<Tipcontainer> selectorTipCont = new ArrayList<>();

    public void authorized() {
    }

    public ContainerBean() {
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
                TipcontainerDAO daoTipcontainer = new TipcontainerDAO();
                selectorTipCont = daoTipcontainer.findAll();

                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();

                listadoContainers = daoContainer.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Container contai) {
        conta = contai;
    }

    public void onCancelDialog() {
        setConta(new Container());
        setIdLineaSelected(0);
        setIdTipContSelected(0);
    }

    public void commitEdit() throws SQLException {
        daoContainer.editContainer(conta);
        listadoContainers = daoContainer.findAll();
    }

    public void eliminar(Container contai) throws SQLException {
        daoContainer.deleteContainer(contai);
        listadoContainers = daoContainer.findAll();
    }
    
    public void lossContainer() throws SQLException {
        daoContainer.lossContainer(conta);
        listadoContainers = daoContainer.findAll();
    }

    public List<Container> getListadoContainers() {
        return listadoContainers;
    }

    public void setListadoContainers(List<Container> listadoContainers) {
        this.listadoContainers = listadoContainers;
    }

    public List<Container> getFilteredContainers() {
        return filteredContainers;
    }

    public void setFilteredContainers(List<Container> filteredContainers) {
        this.filteredContainers = filteredContainers;
    }

    public Container getConta() {
        return conta;
    }

    public void setConta(Container conta) {
        this.conta = conta;
    }

    public ContainerDAO getDaoContainer() {
        return daoContainer;
    }

    public void setDaoContainer(ContainerDAO daoContainer) {
        this.daoContainer = daoContainer;
    }

    public int getIdLineaSelected() {
        return idLineaSelected;
    }

    public void setIdLineaSelected(int idLineaSelected) {
        this.idLineaSelected = idLineaSelected;
    }

    public List<Linea> getSelectorLinea() {
        return selectorLinea;
    }

    public void setSelectorLinea(List<Linea> selectorLinea) {
        this.selectorLinea = selectorLinea;
    }

    public int getIdTipContSelected() {
        return idTipContSelected;
    }

    public void setIdTipContSelected(int idTipContSelected) {
        this.idTipContSelected = idTipContSelected;
    }

    public List<Tipcontainer> getSelectorTipCont() {
        return selectorTipCont;
    }

    public void setSelectorTipCont(List<Tipcontainer> selectorTipCont) {
        this.selectorTipCont = selectorTipCont;
    }
    
    
}
