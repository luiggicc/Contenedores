/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.BuqueDAO;
import com.delpac.dao.TipbuqueDAO;
import com.delpac.dao.PaisDAO;
import com.delpac.dao.PuertoDAO;
import com.delpac.dao.LineaDAO;
import com.delpac.dao.TraficoDAO;

import com.delpac.entity.Buque;
import com.delpac.entity.Tipbuque;
import com.delpac.entity.Pais;
import com.delpac.entity.Puerto;
import com.delpac.entity.Linea;
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
public class BuqueBean implements Serializable {

    private List<Buque> listadoBuques = new ArrayList<>();
    private List<Buque> filteredBuques;
    private Usuario sessionUsuario;
    private Buque buq = new Buque();
    private BuqueDAO daoBuque = new BuqueDAO();

    private int idTipBuqSelected;
    private List<Tipbuque> selectorTipBuque = new ArrayList<>();
    
    private int idPaisSelected;
    private List<Pais> selectorPais = new ArrayList<>();
    
    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();
    
    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();
    
    private int idTraficoSelected;
    private List<Trafico> selectorTrafico = new ArrayList<>();

    public void authorized() {
    }

    public BuqueBean() {
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
                TipbuqueDAO daoTipobuque = new TipbuqueDAO();
                selectorTipBuque = daoTipobuque.findAll();
                
                PaisDAO daoPais = new PaisDAO();
                selectorPais = daoPais.findAll();
                
                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAll();
                
                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();
                
                TraficoDAO daoTrafico = new TraficoDAO();
                selectorTrafico = daoTrafico.findAll();
                
                listadoBuques = daoBuque.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Buque buqu) {
        buq = buqu;
    }

    public void onCancelDialog() {
        setBuq(new Buque());
        setIdTipBuqSelected(0);
        setIdPaisSelected(0);
        setIdPuertoSelected(0);
        setIdLineaSelected(0);
        setIdTraficoSelected(0);
    }

    public void commitEdit() throws SQLException {
        daoBuque.editBuque(buq);
        listadoBuques = daoBuque.findAll();
    }

    public void eliminar(Buque buqu) throws SQLException {
        daoBuque.deleteBuque(buqu);
        listadoBuques = daoBuque.findAll();
    }

    public List<Buque> getListadoBuques() {
        return listadoBuques;
    }

    public void setListadoBuques(List<Buque> listadoBuques) {
        this.listadoBuques = listadoBuques;
    }

    public List<Buque> getFilteredBuques() {
        return filteredBuques;
    }

    public void setFilteredBuques(List<Buque> filteredBuques) {
        this.filteredBuques = filteredBuques;
    }

    public Buque getBuq() {
        return buq;
    }

    public void setBuq(Buque buq) {
        this.buq = buq;
    }

    public BuqueDAO getDaoBuque() {
        return daoBuque;
    }

    public void setDaoBuque(BuqueDAO daoBuque) {
        this.daoBuque = daoBuque;
    }

    public int getIdTipBuqSelected() {
        return idTipBuqSelected;
    }

    public void setIdTipBuqSelected(int idTipBuqSelected) {
        this.idTipBuqSelected = idTipBuqSelected;
    }

    public List<Tipbuque> getSelectorTipBuque() {
        return selectorTipBuque;
    }

    public void setSelectorTipBuque(List<Tipbuque> selectorTipBuque) {
        this.selectorTipBuque = selectorTipBuque;
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

    public int getIdPuertoSelected() {
        return idPuertoSelected;
    }

    public void setIdPuertoSelected(int idPuertoSelected) {
        this.idPuertoSelected = idPuertoSelected;
    }

    public List<Puerto> getSelectorPuerto() {
        return selectorPuerto;
    }

    public void setSelectorPuerto(List<Puerto> selectorPuerto) {
        this.selectorPuerto = selectorPuerto;
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

    public int getIdTraficoSelected() {
        return idTraficoSelected;
    }

    public void setIdTraficoSelected(int idTraficoSelected) {
        this.idTraficoSelected = idTraficoSelected;
    }

    public List<Trafico> getSelectorTrafico() {
        return selectorTrafico;
    }

    public void setSelectorTrafico(List<Trafico> selectorTrafico) {
        this.selectorTrafico = selectorTrafico;
    }
    
    
}
