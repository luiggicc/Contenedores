/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
public class CerrarSesionBean implements Serializable {

    public void logout() throws IOException {
        System.out.println("saliendo del sistema");

//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Usuario", null);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Usuario");
//        String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("Url");
//        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Has cerrado sesión", "Gracias por usar nuestro sistema"));
        ec.getFlash().setKeepMessages(true);
        ec.redirect(ec.getRequestContextPath() + "/");

    }
}
