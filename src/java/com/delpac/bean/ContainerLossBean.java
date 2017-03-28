/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import conexion.conexion;
import com.delpac.dao.ContainerLossDAO;
import com.delpac.entity.ContainerLoss;

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
public class ContainerLossBean implements Serializable{
    List<ContainerLoss> lista = new ArrayList<>();
    List<ContainerLoss> filteredCloss;
    ContainerLossDAO daoContainerLoss = new ContainerLossDAO();
    ContainerLoss closs = new ContainerLoss();

    public ContainerLossBean() {
        setLista(daoContainerLoss.ContenedoresPerdidos());
    }
    
    public void exportpdf() throws JRException, IOException {
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        
        String dirReporte = servleContext.getRealPath("/reportes/ContainerLoss.jasper");
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename=Contenedores_Perdidos.pdf");
        response.setContentType("application/pdf");

        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();
    }
    
    public List<ContainerLoss> getLista() {
        return lista;
    }

    public void setLista(List<ContainerLoss> lista) {
        this.lista = lista;
    }

    public List<ContainerLoss> getFilteredCloss() {
        return filteredCloss;
    }

    public void setFilteredCloss(List<ContainerLoss> filteredCloss) {
        this.filteredCloss = filteredCloss;
    }

    public ContainerLossDAO getDaoContainerLoss() {
        return daoContainerLoss;
    }

    public void setDaoContainerLoss(ContainerLossDAO daoContainerLoss) {
        this.daoContainerLoss = daoContainerLoss;
    }

    public ContainerLoss getCloss() {
        return closs;
    }

    public void setCloss(ContainerLoss closs) {
        this.closs = closs;
    }
    
    
}
