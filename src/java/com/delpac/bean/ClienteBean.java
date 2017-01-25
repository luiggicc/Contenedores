/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.ClienteDAO;
import com.delpac.dao.TipidentificaDAO;
import com.delpac.dao.PaisDAO;

import com.delpac.entity.Cliente;
import com.delpac.entity.Tipidentifica;
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
public class ClienteBean implements Serializable {

    private List<Cliente> listadoClientes = new ArrayList<>();
    private List<Cliente> filteredClientes;
    private Usuario sessionUsuario;
    private Cliente cli = new Cliente();
    private ClienteDAO daoCliente = new ClienteDAO();

    private int idTipIdenSelected;
    private List<Tipidentifica> selectorTipIdent = new ArrayList<>();
    
    private int idPaisSelected;
    private List<Pais> selectorPais = new ArrayList<>();

    public void authorized() {
    }

    public ClienteBean() {
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
                TipidentificaDAO daoTipIdentifica = new TipidentificaDAO();
                selectorTipIdent = daoTipIdentifica.findAll();
                
                PaisDAO daoPais = new PaisDAO();
                selectorPais = daoPais.findAll();
                listadoClientes = daoCliente.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Cliente clie) {
        cli = clie;
    }

    public void onCancelDialog() {
        setCli(new Cliente());
        setIdTipIdenSelected(0);
        setIdPaisSelected(0);
    }

    public void commitEdit() throws SQLException {
        daoCliente.editCliente(cli);
        listadoClientes = daoCliente.findAll();
    }

    public void eliminar(Cliente clie) throws SQLException {
        daoCliente.deleteCliente(clie);
        listadoClientes = daoCliente.findAll();
    }

    public List<Cliente> getListadoClientes() {
        return listadoClientes;
    }

    public void setListadoClientes(List<Cliente> listadoClientes) {
        this.listadoClientes = listadoClientes;
    }

    public List<Cliente> getFilteredClientes() {
        return filteredClientes;
    }

    public void setFilteredClientes(List<Cliente> filteredClientes) {
        this.filteredClientes = filteredClientes;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public ClienteDAO getDaoCliente() {
        return daoCliente;
    }

    public void setDaoCliente(ClienteDAO daoCliente) {
        this.daoCliente = daoCliente;
    }

    public int getIdTipIdenSelected() {
        return idTipIdenSelected;
    }

    public void setIdTipIdenSelected(int idTipIdenSelected) {
        this.idTipIdenSelected = idTipIdenSelected;
    }

    public List<Tipidentifica> getSelectorTipIdent() {
        return selectorTipIdent;
    }

    public void setSelectorTipIdent(List<Tipidentifica> selectorTipIdent) {
        this.selectorTipIdent = selectorTipIdent;
    }

    public int getIdPaisSelected() {
        return idPaisSelected;
    }

    public void setIdPaisSelected(int idPaisSelected) {
        this.idPaisSelected = idPaisSelected;
    }

    public List<Pais> getSelectorPais() {
        return selectorPais;
    }

    public void setSelectorPais(List<Pais> selectorPais) {
        this.selectorPais = selectorPais;
    }
    
    
}
