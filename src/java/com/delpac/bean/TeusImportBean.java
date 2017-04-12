/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import conexion.conexion;
import com.delpac.dao.TeusImportsDAO;
import com.delpac.entity.TeusImports;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
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
public class TeusImportBean {
    List<TeusImports> lista = new ArrayList<>();
    List<TeusImports> filteredTimpo;
    String anio;
    TeusImportsDAO daoTeusImports = new TeusImportsDAO();
    TeusImports rli = new TeusImports();
    
    public void ConsultarReportLineImport() throws SQLException{
            lista = daoTeusImports.ReportImport(anio);
    }
    
    public void exportpdf() throws JRException, IOException{
        conexion con = new conexion();
        Float f = Float.parseFloat(anio);
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("anio", f);

        String dirReporte = servleContext.getRealPath("/reportes/TeusImports.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=ReportLine_Imports_"+anio+ ".pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    

    public List<TeusImports> getLista() {
        return lista;
    }

    public void setLista(List<TeusImports> lista) {
        this.lista = lista;
    }

    public List<TeusImports> getFilteredTimpo() {
        return filteredTimpo;
    }

    public void setFilteredTimpo(List<TeusImports> filteredTimpo) {
        this.filteredTimpo = filteredTimpo;
    }

    public TeusImportsDAO getDaoTeusImports() {
        return daoTeusImports;
    }

    public void setDaoTeusImports(TeusImportsDAO daoTeusImports) {
        this.daoTeusImports = daoTeusImports;
    }

    public TeusImports getRli() {
        return rli;
    }

    public void setRli(TeusImports rli) {
        this.rli = rli;
    }

    
}

