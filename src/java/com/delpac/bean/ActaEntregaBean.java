/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.OrdenRetiroDAO;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.OrdenRetiro;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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

    private int idSelloSelected;
    private List<Sellos> selectorSello = new ArrayList<>();

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

                SellosDAO daoSello = new SellosDAO();
                selectorSello = daoSello.findAll();

                listadoOrdenes = daoOrdenRetiro.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showAsignarDialog(OrdenRetiro orde) {
        ord = orde;
    }

    public void onCancelDialog() {
        setOrd(new OrdenRetiro());
    }

    public void commitAsignar() throws SQLException {
        daoOrdenRetiro.editActaEntrega(ord, sessionUsuario);
        listadoOrdenes = daoOrdenRetiro.findAll();
    }

    public void exportpdf2(OrdenRetiro or) throws JRException, IOException, SQLException {
        conexion con = new conexion();
        try {
            daoOrdenRetiro.updateAsignada(ord, or.getCod_ordenretiro());
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
            parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
            parametros.put("cod_ordenretiro", or.getCod_ordenretiro());
            String temperatura = or.getEs_temperado() == 1 ? "ReporteFreezerAsignada.jasper" : "ReporteNoFreezerAsignada.jasper";
            String jrxmlPath = temperatura.equals("ReporteFreezerAsignada.jasper") ? "ReporteFreezerAsignada.jrxml" : "ReporteNoFreezerAsignada.jrxml";
            InputStream is = new FileInputStream(servleContext.getRealPath("/reportes/") + jrxmlPath);
            String nom_archivo = "Acta_de_Entrega" + or.getCod_ordenretiro() + ".pdf";
            String exportDir = System.getProperty("catalina.base") + "/ActasEntrega/";
            String exportPath = exportDir + nom_archivo;
            JasperDesign design = JRXmlLoader.load(is);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
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
}
