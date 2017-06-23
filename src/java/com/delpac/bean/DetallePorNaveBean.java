/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import conexion.conexion;
import com.delpac.dao.DetallePorNaveDAO;
import com.delpac.entity.DetallePorNave;
import com.delpac.dao.ItinerarioDAO;
import com.delpac.entity.Itinerario;
import com.delpac.entity.Usuario;

import java.io.IOException;
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
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class DetallePorNaveBean {

    List<DetallePorNave> lista = new ArrayList<>();
    List<DetallePorNave> filteredDxn;
    DetallePorNaveDAO daoDetallePorNave = new DetallePorNaveDAO();
    DetallePorNave dxn = new DetallePorNave();
    String itinerario;
    List<Itinerario> selectorItinerario = new ArrayList<>();
    private Usuario sessionUsuario;

    public DetallePorNaveBean() {
        try {
            sessionUsuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Usuario");
            if (sessionUsuario == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Usuario");
                String url = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SesionExpirada");
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } else {
                ItinerarioDAO daoItinerario = new ItinerarioDAO();
                selectorItinerario = daoItinerario.findAllItinerarioMov();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void consultarDetallePorNave() throws SQLException {
        lista = daoDetallePorNave.DetallePorNave(itinerario);
    }

    public void exportpdf() throws JRException, IOException {
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("itinerario", itinerario);

        String dirReporte = servleContext.getRealPath("/reportes/DetallePorNave.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//        response.addHeader("Content-disposition", "attachment;filename=DetallePorNave.pdf");
        response.addHeader("Content-disposition", "inline");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }
    
    public void exportexcel() throws JRException, IOException {
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("itinerario", itinerario);
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, true);
        
        String dirReporte = servleContext.getRealPath("/reportes/DetallePorNave.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=DetallePorNave.xlsx");
        response.setContentType("application/xlsx");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JRXlsxExporter expor = new JRXlsxExporter();
        expor.setExporterInput(new SimpleExporterInput(impres));
        expor.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        
        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setCollapseRowSpan(Boolean.FALSE);
        config.setWhitePageBackground(Boolean.FALSE);
        config.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
        
        expor.setConfiguration(config);
        expor.exportReport();
        context.responseComplete();
    }

    public List<DetallePorNave> getLista() {
        return lista;
    }

    public void setLista(List<DetallePorNave> lista) {
        this.lista = lista;
    }

    public List<DetallePorNave> getFilteredDxn() {
        return filteredDxn;
    }

    public void setFilteredDxn(List<DetallePorNave> filteredDxn) {
        this.filteredDxn = filteredDxn;
    }

    public DetallePorNaveDAO getDaoDetallePorNave() {
        return daoDetallePorNave;
    }

    public void setDaoDetallePorNave(DetallePorNaveDAO daoDetallePorNave) {
        this.daoDetallePorNave = daoDetallePorNave;
    }

    public DetallePorNave getDxn() {
        return dxn;
    }

    public void setDxn(DetallePorNave dxn) {
        this.dxn = dxn;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public List<Itinerario> getSelectorItinerario() {
        return selectorItinerario;
    }

    public void setSelectorItinerario(List<Itinerario> selectorItinerario) {
        this.selectorItinerario = selectorItinerario;
    }

}
