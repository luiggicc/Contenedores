/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import conexion.conexion;
import com.delpac.dao.GeneralNavesDAO;
import com.delpac.entity.GeneralNaves;

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
public class GeneralNavesBean {

    List<GeneralNaves> lista = new ArrayList<>();
    List<GeneralNaves> filteredGnaves;
    Date desde = new Date(), hasta = new Date();
    GeneralNavesDAO daoGeneralNaves = new GeneralNavesDAO();
    GeneralNaves gnaves = new GeneralNaves();
    String movimiento;

    public void consultarGeneralNaves() throws SQLException {
        if (movimiento.equals("Descarga")) {
            lista = daoGeneralNaves.CompararContenedores(movimiento, desde, hasta);
        } else {
            lista = daoGeneralNaves.CompararContenedoresExport(movimiento, desde, hasta);
        }
    }

    public void exportpdf() throws JRException, IOException {
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("movimiento", movimiento);
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);

        String reporte = movimiento.equals("Descarga") ? "GeneralNaves.jasper" : "GeneralNavesExport.jasper";

        String dirReporte = servleContext.getRealPath("/reportes/" + reporte);
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=ReporteNaves_A_Naves.pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }

    public List<GeneralNaves> getLista() {
        return lista;
    }

    public void setLista(List<GeneralNaves> lista) {
        this.lista = lista;
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

    public GeneralNavesDAO getDaoGeneralNaves() {
        return daoGeneralNaves;
    }

    public void setDaoGeneralNaves(GeneralNavesDAO daoGeneralNaves) {
        this.daoGeneralNaves = daoGeneralNaves;
    }

    public GeneralNaves getGnaves() {
        return gnaves;
    }

    public void setGnaves(GeneralNaves gnaves) {
        this.gnaves = gnaves;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public List<GeneralNaves> getFilteredGnaves() {
        return filteredGnaves;
    }

    public void setFilteredGnaves(List<GeneralNaves> filteredGnaves) {
        this.filteredGnaves = filteredGnaves;
    }

}
