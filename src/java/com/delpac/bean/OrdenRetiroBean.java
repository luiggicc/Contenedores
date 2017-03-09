/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.OrdenRetiroDAO;
import com.delpac.entity.OrdenRetiro;
import com.delpac.dao.ClienteDAO;
import com.delpac.entity.Cliente;
import com.delpac.dao.ItinerarioDAO;
import com.delpac.entity.Itinerario;
import com.delpac.dao.LineaDAO;
import com.delpac.entity.Linea;
import com.delpac.dao.PuertoDAO;
import com.delpac.entity.Puerto;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.Sellos;
import com.delpac.dao.LocalidadDAO;
import com.delpac.entity.Localidad;

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
import javax.faces.context.ExternalContext;
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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.*;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class OrdenRetiroBean implements Serializable {

    private List<OrdenRetiro> listadoOrdenes = new ArrayList<>();
    private List<OrdenRetiro> filteredOrdenes;
    private Usuario sessionUsuario;
    private OrdenRetiro ord = new OrdenRetiro();
    private OrdenRetiroDAO daoOrdenRetiro = new OrdenRetiroDAO();
    boolean temperado;
    boolean temperado2;
    StreamedContent stream;

    private int idClienteSelected;
    private List<Cliente> selectorCliente = new ArrayList<>();

    private int idItinerarioSelected;
    private List<Itinerario> selectorItinerario = new ArrayList<>();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();

    private int idSelloSelected;
    private List<Sellos> selectorSello = new ArrayList<>();

    private int idLocalidadSelected;
    private List<Localidad> selectorLocalidad = new ArrayList<>();

    private int idMailSelected;
    private List<OrdenRetiro> selectorMail = new ArrayList<>();

    public void authorized() {
    }

    public OrdenRetiroBean() {
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

                ItinerarioDAO daoItinerario = new ItinerarioDAO();
                selectorItinerario = daoItinerario.findAll();

                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();

                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAll();

                SellosDAO daoSello = new SellosDAO();
                selectorSello = daoSello.findAll();

                LocalidadDAO daoLocalidad = new LocalidadDAO();
                selectorLocalidad = daoLocalidad.findAll();

                selectorMail = daoOrdenRetiro.Mails();
                listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void showEditDialog(OrdenRetiro orde) {
        ord = orde;
        temperado2 = (ord.getEs_temperado() != 0);
    }

    public void onCancelDialog() {
        setOrd(new OrdenRetiro());
    }

    public void showSendDialog(OrdenRetiro orde) {
        ord = orde;
    }

    public void showPdfDialog(OrdenRetiro orde) {
        ord = orde;
    }

    public void commitCreate() throws SQLException {
        daoOrdenRetiro.crearOrdenRetiro(ord, temperado, sessionUsuario);
    }

    public void commitEdit() throws SQLException {
        daoOrdenRetiro.editOrdenRetiro(ord, temperado2, sessionUsuario);
        listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
    }

    public boolean temperaturaEdit() {
        boolean check;
        if (ord.getEs_temperado() == 1) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public void exportpdf(OrdenRetiro or) throws JRException, IOException, SQLException {
        daoOrdenRetiro.updateVerificaPDF(ord, or.getCod_ordenretiro());
        conexion con = new conexion();
        Map<String, Object> parametros = new HashMap<String, Object>();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
        parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
        parametros.put("cod_ordenretiro", or.getCod_ordenretiro());

        String temperatura = or.getEs_temperado() == 1 ? "ReporteFreezer.jasper" : "ReporteNoFreezer.jasper";

        String dirReporte = servleContext.getRealPath("/reportes/" + temperatura);
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        String nom_archivo = "Orden_de_Retiro" + or.getCod_ordenretiro() + ".pdf";
        response.addHeader("Content-disposition", "attachment;filename=" + nom_archivo);

        response.setContentType("application/pdf");
        JasperPrint impres = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());
        JasperExportManager.exportReportToPdfStream(impres, response.getOutputStream());
        context.responseComplete();

    }

    public void exportpdf2(OrdenRetiro or) throws JRException, IOException, SQLException {
        conexion con = new conexion();
        try {
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
            parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
            parametros.put("cod_ordenretiro", or.getCod_ordenretiro());
            String temperatura = or.getEs_temperado() == 1 ? "ReporteFreezer.jasper" : "ReporteNoFreezer.jasper";
            String jrxmlPath = temperatura.equals("ReporteFreezer.jasper") ? "ReporteFreezer.jrxml" : "ReporteNoFreezer.jrxml";
            InputStream is = new FileInputStream(servleContext.getRealPath("/reportes/") + jrxmlPath);
            String nom_archivo = "Orden_de_Retiro" + or.getCod_ordenretiro() + ".pdf";
            String exportDir = System.getProperty("catalina.base") + "/OrdenesRetiro/";
            String exportPath = exportDir + nom_archivo;
            JasperDesign design = JRXmlLoader.load(is);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
            FacesContext context2 = FacesContext.getCurrentInstance();
            context2.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "PDF Generado " + nom_archivo));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void enviarMail() throws EmailException, SQLException, IOException {

        MultiPartEmail email = new HtmlEmail();
        try {
            EmailAttachment attachment = new EmailAttachment();
            String exportDir = System.getProperty("catalina.base") + "/OrdenesRetiro/";
            String nom_archivo = "Orden_de_Retiro" + ord.getCod_ordenretiro() + ".pdf";
            attachment.setPath(exportDir + nom_archivo);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("Orden de retiro");
            attachment.setName(nom_archivo);
            //Mail
            String authuser = "Customerservice@delpac-sa.com";
            String authpwd = "cs6609";
//            String authuser = "jorge.castaneda@bottago.com";
//            String authpwd = "jorgec012";
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setSSLOnConnect(false);
            email.setDebug(true);
            email.setHostName("mailserver.losinkas.com");
//            email.setHostName("ns0.ovh.net");
            email.getMailSession().getProperties().put("mail.smtps.auth", "false");
            email.getMailSession().getProperties().put("mail.debug", "true");
            email.getMailSession().getProperties().put("mail.smtp.port", "26");
//            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
//            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
            email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "false");
            email.setFrom("Customerservice@delpac-sa.com", "Servico al cliente Delpac");
            email.setSubject(ord.getCia_nombre() + " / " + ord.getPto_nombre() + "/ BOOKING " + ord.getBooking() + ord.getDsp_itinerario());
            email.setMsg("<strong>Estimados:</strong><br />"
                    + "Buenas tardes, adjunto la orden de retiro para la nave <strong>" + ord.getDsp_itinerario() + "</strong><br />"
                    + "<br />"
                    + "<ul><li>Favor retirar el sello en nuestras oficinas.</li>"
                    + "<li><strong>Traer la orden de retiro</strong> para poder formalizar la entrega del sello.</li>"
                    + "<li><strong>Copia de cedula</strong> de la persona que retirará el sello.</li>"
                    + "<li><strong>Traer carta de autorización</strong> por parte del exportador nombrando al delegado que retirará el sello.</li>"
                    + "<li><strong>MUY IMPORTANTE: Se recuerda que a partir del 1 de Julio, 2016 todo contenedor deberá contar con certificado de "
                    + "VGM (Masa Bruta Verificada) antes del embarque, caso contrario el contenedor no podrá ser considerado para embarque. CUT OFF VGM, "
                    + "24 horas antes del atraque de la nave.</strong></li> "
                    + "<br /><br />"
                    + "Señores de <strong>" + ord.getLoc_salidades() + "</strong> favor designar la unidad.<br /><br />"
                    + "<strong>Requerimiento Especial:</strong> Contenedores <strong>" + ord.getReq_especial2() + "</strong><br /><br />"
                    + "<strong>Remark:</strong> el contenedor deberá ingresar al terminal Portuario <strong>" + ord.getLoc_entradades() + "</strong> a la zona "
                    + "que ellos le "
                    + "asignen al momento de ingresar por Gate."
                    + "<br /><br />"
                    + "Gracias.<br /><br />"
                    + "<strong>Saludos Cordiales / Best Regards</strong><br />"
                    + "JOSE CARRIEL M. II DELPAC S.A. II Av. 9 de Octubre 2009 y Los Ríos, Edificio el Marqués II Guayaquil - Ecuador <br />"
                    + "Tel.: +593 42371 172/ +593 42365 626 II Cel.: +59 998152266 II Mail: jcarriel@delpac-sa.com");

            email.addTo("gint1@TERCON.COM.EC, gout2@TERCON.COM.EC, controlgate@TERCON.COM.EC, " +ord.getDestinario().split(","));
            email.addCc("dgonzalez@delpac-sa.com, charmsen@delpac-sa.com, vmendoza@delpac-sa.com, vzambrano@delpac-sa.com, vchiriboga@delpac-sa.com, vortiz@delpac-sa.com, @operaciones@delpac-sa.com," + ord.getCc().split(","));

            //Add attach
            email.attach(attachment);

            //Send mail
            email.send();
            daoOrdenRetiro.updateVerificaPDF(ord, ord.getCod_ordenretiro());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "El mail ha sido enviado"));
        } catch (EmailException ee) {
            ee.printStackTrace();
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

    public int getIdItinerarioSelected() {
        return idItinerarioSelected;
    }

    public void setIdItinerarioSelected(int idItinerarioSelected) {
        this.idItinerarioSelected = idItinerarioSelected;
    }

    public List<Itinerario> getSelectorItinerario() {
        return selectorItinerario;
    }

    public void setSelectorItinerario(List<Itinerario> selectorItinerario) {
        this.selectorItinerario = selectorItinerario;
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

    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
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

    public int getIdMailSelected() {
        return idMailSelected;
    }

    public void setIdMailSelected(int idMailSelected) {
        this.idMailSelected = idMailSelected;
    }

    public List<OrdenRetiro> getSelectorMail() {
        return selectorMail;
    }

    public void setSelectorMail(List<OrdenRetiro> selectorMail) {
        this.selectorMail = selectorMail;
    }

//    public StreamedContent getStream() throws IOException {
//        try {
////            String exportDir = System.getProperty("catalina.base") + "/OrdenesRetiro/";
////            String nom_archivo = "Orden_de_Retiro" + ord.getCod_ordenretiro() + ".pdf";
////            String nom_archivo_sinext = "Orden_de_Retiro" + ord.getCod_ordenretiro();
//            return new DefaultStreamedContent(new FileInputStream(
//                    new File("C:\\Program Files\\Apache Software Foundation\\apache-tomcat-8.0.39\\OrdenesRetiro\\Orden_de_Retiro2017100020.pdf")),
//                    "application/pdf", "Orden_de_Retiro2017100020");
//        } catch (FileNotFoundException e) {
//            throw new FacesException(e);
//        }
//    }
    public void setStream(StreamedContent stream) {
        this.stream = stream;
    }

}
