/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.ReestablecerDAO;
import com.delpac.entity.Usuario;
import com.delpac.entity.ReestablecerContra;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class ReestablecerBean implements Serializable{
    private List<ReestablecerContra> listadoUsuarios = new ArrayList<>();
    private List<ReestablecerContra> filteredUsers;
    private Usuario sessionUsuario;
    private ReestablecerContra reestablecer = new ReestablecerContra();
    private ReestablecerDAO daoReestablecer = new ReestablecerDAO();

    public void authorized() {
    }

    public ReestablecerBean() {
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
                listadoUsuarios = daoReestablecer.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }

    }

    public void showEditDialog(ReestablecerContra r) {
        reestablecer = r;
    }

    public void onCancelDialog() {
        setUsuario(new ReestablecerContra());
    }

    public void commitEdit() throws SQLException {
        boolean flag = daoReestablecer.editContra(reestablecer, sessionUsuario);
        if (flag) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Contraseña restablecida"));
            listadoUsuarios = daoReestablecer.findAll();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lo sentimos, ocurrió un problema"));
        }
    }

    public List<ReestablecerContra> getListadoUsuarios() {
        return listadoUsuarios;
    }

    public void setListadoUsuarios(List<ReestablecerContra> listadoUsuarios) {
        this.listadoUsuarios = listadoUsuarios;
    }

    public List<ReestablecerContra> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<ReestablecerContra> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public ReestablecerContra getUsuario() {
        return reestablecer;
    }

    public void setReestablecer(ReestablecerContra reestablecer) {
        this.reestablecer = reestablecer;
    }

    
    public void setUsuario(ReestablecerContra reestablecer) {
        this.reestablecer = reestablecer;
    }
    
}
