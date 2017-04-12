/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import conexion.conexion;
import com.delpac.dao.SemanalDAO;
import com.delpac.entity.Semanal;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class SemanalBean implements Serializable{
    List<Semanal> lista = new ArrayList<>();
    List<Semanal> filteredSemanal;
    Date desde = new Date(), hasta = new Date();
    SemanalDAO daoSemanal = new SemanalDAO();
    Semanal sema = new Semanal();
    
    public void ConsultarSemanal() throws SQLException{
        lista = daoSemanal.ReporteSemanal(desde, hasta);
    }
    
    public void exportpdf() throws JRException, IOException{
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);

        String dirReporte = servleContext.getRealPath("/reportes/Semanal.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=Semanal.pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }

    public List<Semanal> getLista() {
        return lista;
    }

    public void setLista(List<Semanal> lista) {
        this.lista = lista;
    }

    public List<Semanal> getFilteredSemanal() {
        return filteredSemanal;
    }

    public void setFilteredSemanal(List<Semanal> filteredSemanal) {
        this.filteredSemanal = filteredSemanal;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public SemanalDAO getDaoSemanal() {
        return daoSemanal;
    }

    public void setDaoSemanal(SemanalDAO daoSemanal) {
        this.daoSemanal = daoSemanal;
    }

    public Semanal getSema() {
        return sema;
    }

    public void setSema(Semanal sema) {
        this.sema = sema;
    }
    
    
}
