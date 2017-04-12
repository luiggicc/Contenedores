/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import conexion.conexion;
import com.delpac.dao.TeusExportsDAO;
import com.delpac.entity.TeusExports;

import java.io.IOException;
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
public class TeusExportBean {
    List<TeusExports> lista = new ArrayList<>();
    List<TeusExports> filteredTexpo;
    String anio;
    TeusExportsDAO daoTeusExports = new TeusExportsDAO();
    TeusExports rli = new TeusExports();
    
    public void ConsultarReportLineExport() throws SQLException{
            lista = daoTeusExports.ReportExport(anio);
    }
    
    public void exportpdf() throws JRException, IOException{
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("anio", anio);

        String dirReporte = servleContext.getRealPath("/reportes/TeusExports.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=ReportLine_Exports_"+anio+ ".pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }

    public List<TeusExports> getLista() {
        return lista;
    }

    public void setLista(List<TeusExports> lista) {
        this.lista = lista;
    }

    public List<TeusExports> getFilteredTexpo() {
        return filteredTexpo;
    }

    public void setFilteredTexpo(List<TeusExports> filteredTexpo) {
        this.filteredTexpo = filteredTexpo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public TeusExportsDAO getDaoTeusExports() {
        return daoTeusExports;
    }

    public void setDaoTeusExports(TeusExportsDAO daoTeusExports) {
        this.daoTeusExports = daoTeusExports;
    }

    public TeusExports getRli() {
        return rli;
    }

    public void setRli(TeusExports rli) {
        this.rli = rli;
    }
    
    
}
