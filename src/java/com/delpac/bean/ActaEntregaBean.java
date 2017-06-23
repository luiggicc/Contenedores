/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.ClienteDAO;
import com.delpac.dao.LineaDAO;
import com.delpac.dao.LocalidadDAO;
import com.delpac.dao.OrdenRetiroDAO;
import com.delpac.dao.PuertoDAO;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.Cliente;
import com.delpac.entity.Linea;
import com.delpac.entity.Localidad;
import com.delpac.entity.OrdenRetiro;
import com.delpac.entity.Puerto;
import com.delpac.entity.Sellos;
import com.delpac.entity.Usuario;

import conexion.conexion;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class ActaEntregaBean implements Serializable {

    private List<OrdenRetiro> listadoOrdenes = new ArrayList<>();
    private List<OrdenRetiro> filteredOrdenes;
    private Usuario sessionUsuario;
    private OrdenRetiro ord = new OrdenRetiro();
    private OrdenRetiroDAO daoOrdenRetiro = new OrdenRetiroDAO();
    StreamedContent stream;
    boolean temperado;
    boolean temperado2;

    private String idSelloSelected;
    private List<Sellos> selectorSello = new ArrayList<>();

    private int idClienteSelected;
    private List<Cliente> selectorCliente = new ArrayList<>();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();

    private int idLocalidadSelected;
    private List<Localidad> selectorLocalidad = new ArrayList<>();

    public void authorized() {
    }

    public ActaEntregaBean() {
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

                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAllPuertosOrden();

                LocalidadDAO daoLocalidad = new LocalidadDAO();
                selectorLocalidad = daoLocalidad.findAll();

                SellosDAO daoSello = new SellosDAO();
                selectorSello = daoSello.findAllStockAsignar();

                listadoOrdenes = daoOrdenRetiro.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showAsignarDialog(OrdenRetiro orde) {
        ord = orde;
    }
    
    public void showEditDialogActa(OrdenRetiro orde) {
        ord = orde;
        temperado2 = (ord.getEs_temperado() != 0);
    }
    
    public void commitEditActa() throws SQLException {
        int keyGenerated = daoOrdenRetiro.editOrdenRetiro(ord, temperado2, sessionUsuario);
        listadoOrdenes = daoOrdenRetiro.findAll();
    }

    public void onCancelDialog() {
        setOrd(new OrdenRetiro());
    }
    
    public void regresion(OrdenRetiro orde) throws SQLException {
        daoOrdenRetiro.regresionOrden(orde);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        "Regresión exitosa", "Has devuelto el sello al stock"));
        listadoOrdenes = daoOrdenRetiro.findAll();
    }

    public void commitAsignar() throws SQLException, JRException, IOException {
//        int keyGenerated = daoOrdenRetiro.editActaEntrega(ord, sessionUsuario);
        daoOrdenRetiro.editActaEntrega(ord, sessionUsuario);
        conexion con = new conexion();
        try {
            daoOrdenRetiro.updateAsignada(ord);
//            HashMap<String, Object> parametros = new HashMap<String, Object>();
//            FacesContext context = FacesContext.getCurrentInstance();
//            ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
//            parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
//            parametros.put("cod_ordenretiro", keyGenerated);
//            String temperatura = ord.getEs_temperado() == 1 ? "ReporteFreezerAsignada.jasper" : "ReporteNoFreezerAsignada.jasper";
//            String jrxmlPath = temperatura.equals("ReporteFreezerAsignada.jasper") ? "ReporteFreezerAsignada.jrxml" : "ReporteNoFreezerAsignada.jrxml";
//            InputStream is = new FileInputStream(servleContext.getRealPath("/reportes/") + jrxmlPath);
//            String nom_archivo = "Acta_de_Entrega" + keyGenerated + ".pdf";
//            String exportDir = System.getProperty("catalina.base") + "/ActasEntrega/";
//            String exportPath = exportDir + nom_archivo;
//            JasperDesign design = JRXmlLoader.load(is);
//            JasperReport jasperReport = JasperCompileManager.compileReport(design);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con.getConnection());
//            JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
        } catch (Exception e) {
            System.err.println(e);
        }
        listadoOrdenes = daoOrdenRetiro.findAll();
    }

    public void download(OrdenRetiro ord) throws SQLException, JRException, IOException {
        conexion con = new conexion();
        try {
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            //Contexto
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
            //Parametros
            parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
            parametros.put("cod_ordenretiro", ord.getCod_ordenretiro());
            //String del jasper
            String temperatura = ord.getEs_temperado() == 1 ? "ReporteFreezerAsignada.jasper" : "ReporteNoFreezerAsignada.jasper";
//            String jrxmlPath = temperatura.equals("ReporteFreezer.jasper") ? "ReporteFreezer.jrxml" : "ReporteNoFreezer.jrxml";//server
            //InputStream
//            InputStream is = new FileInputStream(servleContext.getRealPath("/reportes/") + jrxmlPath);//server
            String dirReporte = servleContext.getRealPath("/reportes/" + temperatura);
            String nom_archivo = "AE_BK_" + ord.getBooking() + ".pdf";
//            String exportDir = System.getProperty("catalina.base") + "/OrdenesRetiro/";//server
//            String exportPath = exportDir + nom_archivo;//server
            //JasperDesign
//            JasperDesign design = JRXmlLoader.load(is);//server
//            JasperReport jasperReport = JasperCompileManager.compileReport(design);//server
            //Servlet Response
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename=" + nom_archivo);
            response.setContentType("application/pdf");
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con.getConnection());//server
            JasperPrint jasperPrint = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
//            JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath); //server
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            context.responseComplete();
        } catch (Exception e) {
            System.err.println(e);
        }
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

    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
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

    public StreamedContent getStream() {
        return stream;
    }

    public void setStream(StreamedContent stream) {
        this.stream = stream;
    }

    public String getIdSelloSelected() {
        return idSelloSelected;
    }

    public void setIdSelloSelected(String idSelloSelected) {
        this.idSelloSelected = idSelloSelected;
    }

    public List<Sellos> getSelectorSello() {
        return selectorSello;
    }

    public void setSelectorSello(List<Sellos> selectorSello) {
        this.selectorSello = selectorSello;
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

    public boolean isTemperado() {
        return temperado;
    }

    public void setTemperado(boolean temperado) {
        this.temperado = temperado;
    }

    public boolean isTemperado2() {
        return temperado2;
    }

    public void setTemperado2(boolean temperado2) {
        this.temperado2 = temperado2;
    }

    
}
