/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import conexion.conexion;
import com.delpac.dao.ReeferStockDAO;
import com.delpac.entity.ReeferStock;

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
public class ReeferStockBean {
    List<ReeferStock> lista = new ArrayList<>();
    List<ReeferStock> filteredRstock;
    Date desde = new Date(), hasta = new Date();
    ReeferStockDAO daoReeferStock = new ReeferStockDAO();
    ReeferStock rstock = new ReeferStock();
    
    public void ConsultarReeferStock() throws SQLException{
        lista = daoReeferStock.ReeferStockConsulta(desde, hasta);
    }
    
    public void exportpdf() throws JRException, IOException{
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("DesdeEmpty", desde);
        parametros.put("HastaEmpty", hasta);
        parametros.put("DesdeImport", desde);
        parametros.put("HastaImport", hasta);
        parametros.put("DesdeExport", desde);
        parametros.put("HastaExport", hasta);

        String dirReporte = servleContext.getRealPath("/reportes/ReeferStock.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=Reefer_Stock.pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }

    public List<ReeferStock> getLista() {
        return lista;
    }

    public void setLista(List<ReeferStock> lista) {
        this.lista = lista;
    }

    public List<ReeferStock> getFilteredRstock() {
        return filteredRstock;
    }

    public void setFilteredRstock(List<ReeferStock> filteredRstock) {
        this.filteredRstock = filteredRstock;
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

    public ReeferStockDAO getDaoReeferStock() {
        return daoReeferStock;
    }

    public void setDaoReeferStock(ReeferStockDAO daoReeferStock) {
        this.daoReeferStock = daoReeferStock;
    }

    public ReeferStock getRstock() {
        return rstock;
    }

    public void setRstock(ReeferStock rstock) {
        this.rstock = rstock;
    }
    
    
}
