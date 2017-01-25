/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.AsignaRolDAO;
import com.delpac.entity.AsignaRol;
import com.delpac.dao.RolDAO;
import com.delpac.dao.UsuarioDAO;
import com.delpac.entity.Rol;
import com.delpac.entity.Usuario;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{
    private List<Usuario> listadoUsuarios = new ArrayList<>();
    private List<Usuario> filteredUsers;
    private Usuario sessionUsuario;
    private Usuario usuario = new Usuario();
    private UsuarioDAO daoUsuario = new UsuarioDAO();
    private List<AsignaRol> listadoPermisos = new ArrayList<>();
    private List<AsignaRol> filteredAccess;
    private AsignaRolDAO daoAsignaRol = new AsignaRolDAO();
    
        public void authorized() {
    }
        
            public UsuarioBean() {
        try {
            sessionUsuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Usuario");
            if (sessionUsuario == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Usuario");
                String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SesionExpirada");
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } else {
                listadoUsuarios = daoUsuario.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
            
                public void showEditDialog(Usuario u) {
        usuario = u;
    }

    public void showProfilesDialog(Usuario u) {
        usuario = u;
        listadoPermisos = daoAsignaRol.rolesAsignadosbyUsuario(usuario);
    }

    public void showCreateDialog() {
        usuario = new Usuario();
    }

    public void onCancelDialog() {
        setUsuario(new Usuario());
    }
    
    public void commitEdit() throws SQLException {
        boolean flag = daoUsuario.editUsuario(usuario, sessionUsuario);
        if (flag) {
            listadoUsuarios = daoUsuario.findAll();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del usuario actualizados correctamente"));
            RequestContext.getCurrentInstance().update("frm:growl");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Lo sentimos, ocurrió un problema"));
            RequestContext.getCurrentInstance().update("frm:growl");
        }
    }

    public void commitCreate() throws SQLException {
        usuario.setPassword(usuario.getCedula());
        boolean flag = daoUsuario.createUsuario(usuario, sessionUsuario);
        if (flag) {
            listadoUsuarios = daoUsuario.findAll();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Nuevo usuario añadido correctamente al sistema, favor asignarle un rol"));
            RequestContext.getCurrentInstance().update("frm:growl");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Lo sentimos, ocurrió un problema"));
            RequestContext.getCurrentInstance().update("frm:growl");
        }
    }

    public void eliminar(Usuario u) throws SQLException {
        boolean flag = daoUsuario.deleteUsuario(u, sessionUsuario);
        if (flag) {
            listadoUsuarios = daoUsuario.findAll();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Usuario desactivado"));
            RequestContext.getCurrentInstance().update("frm:growl");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Lo sentimos, ocurrió un problema"));
            RequestContext.getCurrentInstance().update("frm:growl");
        }
    }
    
    public void commitProfiles() throws SQLException, IOException {
        boolean flagValidation = validateProfiles();
        System.out.println(flagValidation);
        if (flagValidation) {
            daoAsignaRol.saveProfilesbyUsuario(listadoPermisos, usuario, sessionUsuario);
            setListadoPermisos(daoAsignaRol.rolesAsignadosbyUsuario(usuario));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Roles concedidos correctamente"));
            RequestContext.getCurrentInstance().update("frm:growl");
        } else {
            setListadoPermisos(daoAsignaRol.rolesAsignadosbyUsuario(usuario));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Un usuario debe tener al menos un rol"));
            RequestContext.getCurrentInstance().update("frm:growl");
        }
    }
    
    public boolean validateProfiles() {
        int contador = 0;
        for (AsignaRol ar : getListadoPermisos()) {
            if (ar.getEstado()) {
                contador++;
            }
        }
        return contador >= 1;
    }
    
    public void onCancelProfileDialog() throws IOException {
        //String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("Url");
        //FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/faces/usuarioCRUD.xhtml");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath()+"/faces/usuarioCRUD.xhtml");
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListadoUsuarios() {
        return listadoUsuarios;
    }

    public void setListadoUsuarios(List<Usuario> listadoUsuarios) {
        this.listadoUsuarios = listadoUsuarios;
    }

    public List<AsignaRol> getFilteredAccess() {
        return filteredAccess;
    }

    public void setFilteredAccess(List<AsignaRol> filteredAccess) {
        this.filteredAccess = filteredAccess;
    }

    public List<Usuario> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<Usuario> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public List<AsignaRol> getListadoPermisos() {
        return listadoPermisos;
    }

    public void setListadoPermisos(List<AsignaRol> listadoPermisos) {
        this.listadoPermisos = listadoPermisos;
    }

    
}


