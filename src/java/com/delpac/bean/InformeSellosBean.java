/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.InformeSellosDAO;
import com.delpac.entity.InformeSellos;
import com.delpac.entity.Usuario;

import conexion.conexion;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
public class InformeSellosBean implements Serializable {

    private List<InformeSellos> listadoOrdenes = new ArrayList<>();
    private List<InformeSellos> filteredOrdenes;
    private Usuario sessionUsuario;
    String booking;

    private InformeSellos inse = new InformeSellos();
    private InformeSellosDAO daoInformeSellos = new InformeSellosDAO();

    public void authorized() {
    }

    public InformeSellosBean() {
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
                
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }
    
    public void consultarInforme() throws SQLException {
        listadoOrdenes = daoInformeSellos.findAll(booking);
    }
    
    public void exportpdf() throws JRException, IOException {
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("booking", booking);
        String dirReporte = servleContext.getRealPath("/reportes/informeSellos.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=Informe_"+ booking + ".pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }
    
    public void limpiar() {
        inse = new InformeSellos();
        listadoOrdenes.clear();
    }

    public List<InformeSellos> getListadoOrdenes() {
        return listadoOrdenes;
    }

    public void setListadoOrdenes(List<InformeSellos> listadoOrdenes) {
        this.listadoOrdenes = listadoOrdenes;
    }

    public List<InformeSellos> getFilteredOrdenes() {
        return filteredOrdenes;
    }

    public void setFilteredOrdenes(List<InformeSellos> filteredOrdenes) {
        this.filteredOrdenes = filteredOrdenes;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public InformeSellos getInse() {
        return inse;
    }

    public void setInse(InformeSellos inse) {
        this.inse = inse;
    }

    public InformeSellosDAO getDaoInformeSellos() {
        return daoInformeSellos;
    }

    public void setDaoInformeSellos(InformeSellosDAO daoInformeSellos) {
        this.daoInformeSellos = daoInformeSellos;
    }
    
    
}
