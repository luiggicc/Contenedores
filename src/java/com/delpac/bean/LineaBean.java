/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.LineaDAO;
import com.delpac.entity.Linea;
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
public class LineaBean implements Serializable{
    private List<Linea> listadoLineas = new ArrayList<>();
    private List<Linea> filteredLineas;
    private Usuario sessionUsuario;
    private Linea lin = new Linea();
    private LineaDAO daoLinea = new LineaDAO();
    
    public void authorized() {
    }
    
    public LineaBean(){
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
                listadoLineas = daoLinea.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void showEditDialog(Linea line) {
        lin = line;
    }

    public void onCancelDialog() {
        setLin(new Linea());
    }
    
    public void commitEdit() throws SQLException {
        daoLinea.editLinea(lin);
        listadoLineas = daoLinea.findAll();
    }

    public void eliminar(Linea line) throws SQLException {
        daoLinea.deleteLinea(line);
        listadoLineas = daoLinea.findAll();
    }

    public List<Linea> getListadoLineas() {
        return listadoLineas;
    }

    public void setListadoLineas(List<Linea> listadoLineas) {
        this.listadoLineas = listadoLineas;
    }

    public List<Linea> getFilteredLineas() {
        return filteredLineas;
    }

    public void setFilteredLineas(List<Linea> filteredLineas) {
        this.filteredLineas = filteredLineas;
    }

    public Linea getLin() {
        return lin;
    }

    public void setLin(Linea lin) {
        this.lin = lin;
    }

    public LineaDAO getDaoLinea() {
        return daoLinea;
    }

    public void setDaoLinea(LineaDAO daoLinea) {
        this.daoLinea = daoLinea;
    }
    
    
}
