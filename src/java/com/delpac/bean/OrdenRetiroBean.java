/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.OrdenRetiroDAO;
import com.delpac.entity.OrdenRetiro;
import com.delpac.dao.ClienteDAO;
import com.delpac.entity.Cliente;
import com.delpac.dao.ItinerarioDAO;
import com.delpac.entity.Itinerario;
import com.delpac.dao.LineaDAO;
import com.delpac.entity.Linea;
import com.delpac.dao.PuertoDAO;
import com.delpac.entity.Puerto;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.Sellos;
import com.delpac.dao.LocalidadDAO;
import com.delpac.entity.Localidad;

import com.delpac.entity.Usuario;
import conexion.conexion;
import java.io.IOException;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class OrdenRetiroBean implements Serializable {

    private List<OrdenRetiro> listadoOrdenes = new ArrayList<>();
    private List<OrdenRetiro> filteredOrdenes;
    private Usuario sessionUsuario;
    private OrdenRetiro ord = new OrdenRetiro();
    private OrdenRetiroDAO daoOrdenRetiro = new OrdenRetiroDAO();
    boolean temperado;
    int es_temperado;
    boolean temperado1;
    
    private int idClienteSelected;
    private List<Cliente> selectorCliente = new ArrayList<>();

    private int idItinerarioSelected;
    private List<Itinerario> selectorItinerario = new ArrayList<>();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();

    private int idSelloSelected;
    private List<Sellos> selectorSello = new ArrayList<>();

    private int idLocalidadSelected;
    private List<Localidad> selectorLocalidad = new ArrayList<>();

    public void authorized() {
    }

    public OrdenRetiroBean() {
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

                ItinerarioDAO daoItinerario = new ItinerarioDAO();
                selectorItinerario = daoItinerario.findAll();

                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();

                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAll();

                SellosDAO daoSello = new SellosDAO();
                selectorSello = daoSello.findAll();

                LocalidadDAO daoLocalidad = new LocalidadDAO();
                selectorLocalidad = daoLocalidad.findAll();

                listadoOrdenes = daoOrdenRetiro.findAll();
                
                
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(OrdenRetiro orde) {
        ord = orde;
    }

    public void onCancelDialog() {
        setOrd(new OrdenRetiro());
    }

    public boolean verificaCheck(){
        boolean est_habilitado = ord.getEstadopdf();
        System.out.println(est_habilitado);
        return est_habilitado;
        
    }
    
    public void commitCreate() throws SQLException {
        daoOrdenRetiro.crearOrdenRetiro(ord, temperado, sessionUsuario);
    }

    public void commitEdit() throws SQLException {
        daoOrdenRetiro.editOrdenRetiro(ord, temperado, sessionUsuario);
        listadoOrdenes = daoOrdenRetiro.findAll();
    }
    
    public void exportpdf(OrdenRetiro or) throws JRException, IOException, SQLException {
        daoOrdenRetiro.updateVerificaPDF(ord, or.getCod_ordenretiro());
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("cod_ordenretiro", or.getCod_ordenretiro());

        String temperatura = or.getEs_temperado() == 1 ? "ReporteFreezer.jasper" : "ReporteNoFreezer.jasper";

        String dirReporte = servleContext.getRealPath("/reportes/" + temperatura);
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=Orden_de_Retiro" + or.getCod_ordenretiro() + ".pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
        
    }

    public List<OrdenRetiro> getListadoOrdenes() {
        return listadoOrdenes;
    }

    public void setListadoOrdenes(List<OrdenRetiro> listadoOrdenes) {
        this.listadoOrdenes = listadoOrdenes;
    }

    public List<OrdenRetiro> getFilteredOrdenes() {
        return filteredOrdenes;
    }

    public void setFilteredOrdenes(List<OrdenRetiro> filteredOrdenes) {
        this.filteredOrdenes = filteredOrdenes;
    }

    public OrdenRetiro getOrd() {
        return ord;
    }

    public void setOrd(OrdenRetiro ord) {
        this.ord = ord;
    }

    public OrdenRetiroDAO getDaoOrdenRetiro() {
        return daoOrdenRetiro;
    }

    public void setDaoOrdenRetiro(OrdenRetiroDAO daoOrdenRetiro) {
        this.daoOrdenRetiro = daoOrdenRetiro;
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

    public int getIdItinerarioSelected() {
        return idItinerarioSelected;
    }

    public void setIdItinerarioSelected(int idItinerarioSelected) {
        this.idItinerarioSelected = idItinerarioSelected;
    }

    public List<Itinerario> getSelectorItinerario() {
        return selectorItinerario;
    }

    public void setSelectorItinerario(List<Itinerario> selectorItinerario) {
        this.selectorItinerario = selectorItinerario;
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

    public int getIdSelloSelected() {
        return idSelloSelected;
    }

    public void setIdSelloSelected(int idSelloSelected) {
        this.idSelloSelected = idSelloSelected;
    }

    public List<Sellos> getSelectorSello() {
        return selectorSello;
    }

    public void setSelectorSello(List<Sellos> selectorSello) {
        this.selectorSello = selectorSello;
    }

    public int getIdLocalidadSelected() {
        return idLocalidadSelected;
    }

    public void setIdLocalidadSelected(int idLocalidadSelected) {
        this.idLocalidadSelected = idLocalidadSelected;
    }

    public List<Localidad> getSelectorLocalidad() {
        return selectorLocalidad;
    }

    public void setSelectorLocalidad(List<Localidad> selectorLocalidad) {
        this.selectorLocalidad = selectorLocalidad;
    }

    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
    }

    public boolean isTemperado() {
        return temperado;
    }

    public void setTemperado(boolean temperado) {
        this.temperado = temperado;
    }

    public int getEs_temperado() {
        return es_temperado;
    }

    public void setEs_temperado(int es_temperado) {
        this.es_temperado = es_temperado;
    }

    public boolean isTemperado1() {
        return temperado1;
    }

    public void setTemperado1(boolean temperado1) {
        this.temperado1 = temperado1;
    }

//    public boolean isEst_habilitado() {
//        return est_habilitado;
//    }
//
//    public void setEst_habilitado(boolean est_habilitado) {
//        this.est_habilitado = est_habilitado;
//    }
    
    
}
