/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.ItinerarioDAO;
import com.delpac.dao.ClienteDAO;
import com.delpac.dao.LineaDAO;
import com.delpac.dao.BuqueDAO;
import com.delpac.dao.PuertoDAO;
import com.delpac.dao.TraficoDAO;
import com.delpac.dao.AduanaDAO;
import com.delpac.dao.TipmanifiestoDAO;

import com.delpac.entity.Itinerario;
import com.delpac.entity.Cliente;
import com.delpac.entity.Linea;
import com.delpac.entity.Buque;
import com.delpac.entity.Puerto;
import com.delpac.entity.Trafico;
import com.delpac.entity.Aduana;
import com.delpac.entity.Tipmanifiesto;

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
public class ItinerarioBean implements Serializable {

    private List<Itinerario> listadoItinerarios = new ArrayList<>();
    private List<Itinerario> filteredItinerarios;
    private Usuario sessionUsuario;
    private Itinerario iti = new Itinerario();
    private ItinerarioDAO daoItinerario = new ItinerarioDAO();

    private int idClienteSelected;
    private List<Cliente> selectorCliente = new ArrayList<>();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idBuqueSelected;
    private List<Buque> selectorBuque = new ArrayList<>();

    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();

    private int idTraficoSelected;
    private List<Trafico> selectorTrafico = new ArrayList<>();

    private int idAduanaSelected;
    private List<Aduana> selectorAduana = new ArrayList<>();

    private int idTipManiSelected;
    private List<Tipmanifiesto> selectorTipMani = new ArrayList<>();

    public void authorized() {
    }

    public ItinerarioBean() {
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
                ClienteDAO daoCliente = new ClienteDAO();
                selectorCliente = daoCliente.findAll();

                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();
                
                BuqueDAO daoBuque = new BuqueDAO();
                selectorBuque = daoBuque.findAll();
                
                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAll();
                
                TraficoDAO daoTrafico = new TraficoDAO();
                selectorTrafico = daoTrafico.findAll();
                
                AduanaDAO daoAduana = new AduanaDAO();
                selectorAduana = daoAduana.findAll();
                
                TipmanifiestoDAO daoTipManifiesto = new TipmanifiestoDAO();
                selectorTipMani = daoTipManifiesto.findAll();
                
                listadoItinerarios = daoItinerario.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(Itinerario itin) {
        iti = itin;
    }

    public void onCancelDialog() {
        setIti(new Itinerario());
        setIdClienteSelected(0);
        setIdLineaSelected(0);
        setIdBuqueSelected(0);
        setIdPuertoSelected(0);
        setIdTraficoSelected(0);
        setIdAduanaSelected(0);
        setIdTipManiSelected(0);        
    }

    public void commitEdit() throws SQLException {
        daoItinerario.editItinerario(iti);
        listadoItinerarios = daoItinerario.findAll();
    }

    public void eliminar(Itinerario itin) throws SQLException {
        daoItinerario.deleteItinerario(itin);
        listadoItinerarios = daoItinerario.findAll();
    }

    public List<Itinerario> getListadoItinerarios() {
        return listadoItinerarios;
    }

    public void setListadoItinerarios(List<Itinerario> listadoItinerarios) {
        this.listadoItinerarios = listadoItinerarios;
    }

    public List<Itinerario> getFilteredItinerarios() {
        return filteredItinerarios;
    }

    public void setFilteredItinerarios(List<Itinerario> filteredItinerarios) {
        this.filteredItinerarios = filteredItinerarios;
    }

    public Itinerario getIti() {
        return iti;
    }

    public void setIti(Itinerario iti) {
        this.iti = iti;
    }

    public ItinerarioDAO getDaoItinerario() {
        return daoItinerario;
    }

    public void setDaoItinerario(ItinerarioDAO daoItinerario) {
        this.daoItinerario = daoItinerario;
    }

    public int getIdClienteSelected() {
        return idClienteSelected;
    }

    public void setIdClienteSelected(int idClienteSelected) {
        this.idClienteSelected = idClienteSelected;
    }

    public List<Cliente> getSelectorCliente() {
        return selectorCliente;
    }

    public void setSelectorCliente(List<Cliente> selectorCliente) {
        this.selectorCliente = selectorCliente;
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

    public int getIdBuqueSelected() {
        return idBuqueSelected;
    }

    public void setIdBuqueSelected(int idBuqueSelected) {
        this.idBuqueSelected = idBuqueSelected;
    }

    public List<Buque> getSelectorBuque() {
        return selectorBuque;
    }

    public void setSelectorBuque(List<Buque> selectorBuque) {
        this.selectorBuque = selectorBuque;
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

    public int getIdAduanaSelected() {
        return idAduanaSelected;
    }

    public void setIdAduanaSelected(int idAduanaSelected) {
        this.idAduanaSelected = idAduanaSelected;
    }

    public List<Aduana> getSelectorAduana() {
        return selectorAduana;
    }

    public void setSelectorAduana(List<Aduana> selectorAduana) {
        this.selectorAduana = selectorAduana;
    }

    public int getIdTipManiSelected() {
        return idTipManiSelected;
    }

    public void setIdTipManiSelected(int idTipManiSelected) {
        this.idTipManiSelected = idTipManiSelected;
    }

    public List<Tipmanifiesto> getSelectorTipMani() {
        return selectorTipMani;
    }

    public void setSelectorTipMani(List<Tipmanifiesto> selectorTipMani) {
        this.selectorTipMani = selectorTipMani;
    }
    
    
}
