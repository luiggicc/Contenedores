package com.delpac.bean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class IdleMonitorBean implements Serializable {
    
    public void WelcomeListener(){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "Bienvenido de nuevo", "Continúa tu trabajo"));
    }
    
    public void logoutListener() throws IOException{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Se ha cerrado la sesion", "Tiempo de inactividad cumplido"));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
          ec.invalidateSession();
          ec.redirect(ec.getRequestContextPath()+"/");
    }
}
