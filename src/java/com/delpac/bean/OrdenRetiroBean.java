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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;

import org.apache.commons.mail.*;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Usar ApplicationScoped en el caso de ver el PDF online
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
    private StreamedContent downloadableFile;

    boolean temperado;
    boolean temperado2;
    String path;
    StreamedContent pdfFile;
    int ingresado;

    private int idClienteSelected;
    private List<Cliente> selectorCliente = new ArrayList<>();

    private int idLineaSelected;
    private List<Linea> selectorLinea = new ArrayList<>();

    private int idPuertoSelected;
    private List<Puerto> selectorPuerto = new ArrayList<>();

    private int idSelloSelected;
    private List<Sellos> selectorSello = new ArrayList<>();

    private int idLocalidadSelected;
    private List<Localidad> selectorLocalidad = new ArrayList<>();

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

                LineaDAO daoLinea = new LineaDAO();
                selectorLinea = daoLinea.findAll();

                PuertoDAO daoPuerto = new PuertoDAO();
                selectorPuerto = daoPuerto.findAllPuertosOrden();

                SellosDAO daoSello = new SellosDAO();
                selectorSello = daoSello.findAll();

                LocalidadDAO daoLocalidad = new LocalidadDAO();
                selectorLocalidad = daoLocalidad.findAll();

                listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
                ord.setMov_xcuenta("Exportador");
                ord.setInv_seguridad("Se entrega en oficina Delpac");
                ord.setObservaciones("Se entrega el sello botella en las oficinas DELPAC. El mismo debe ir forzosamente por asutnos documentales con el Contenedor Asignado");
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void openDialog(OrdenRetiro ord) throws FileNotFoundException {
        path = System.getProperty("catalina.base") + "\\OrdenesRetiro\\Orden_de_Retiro" + ord.getCod_ordenretiro() + ".pdf";
        File file = new File(path);
        getPdfFile();
    }

    public StreamedContent getPdfFile() throws FileNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            DefaultStreamedContent dsc = new DefaultStreamedContent(new FileInputStream(new File(path)), "application/pdf");
            dsc.setName(new File(path).getName() + ".pdf");
            return dsc;
        }
    }

    public void showEditDialog(OrdenRetiro orde) {
        ord = orde;
        temperado2 = (ord.getEs_temperado() != 0);
    }

    public void onCancelDialog() {
        setOrd(new OrdenRetiro());
    }
    
    public void commitEdit() throws SQLException {
        int keyGenerated = daoOrdenRetiro.editOrdenRetiro(ord, temperado2, sessionUsuario);
        listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
    }    

    public void eliminar(OrdenRetiro orde) throws SQLException {
        daoOrdenRetiro.deleteOrden(orde);
        listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
    }

    public void showSendDialog(OrdenRetiro orde) {
        ord = orde;
    }

    public void commitCreate1() throws SQLException, JRException, IOException {
        int keyGenerated = daoOrdenRetiro.crearOrdenRetiro(ord, temperado, sessionUsuario, ingresado);
        conexion con = new conexion();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servleContext = (ServletContext) context.getExternalContext().getContext();
            String temperatura = temperado == true ? "ReporteFreezer.jasper" : "ReporteNoFreezer.jasper";
            String jrxmlPath = temperatura.equals("ReporteFreezer.jasper") ? "ReporteFreezer.jrxml" : "ReporteNoFreezer.jrxml";//server
            String nom_archivo = "OR_BK_" + ord.getBooking() + ".pdf";
            String exportDir = System.getProperty("catalina.base") + "/OrdenesRetiro/";//server
            String exportPath = exportDir + nom_archivo;//server            
            String dirReporte = servleContext.getRealPath("/reportes/" + temperatura);
            File reporte = new File(servleContext.getRealPath("/reportes/") + jrxmlPath);
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            List<JasperPrint> prints = new ArrayList<JasperPrint>();
            for (int cont = 1; cont <= ingresado; cont++) {
                parametros.put("RutaImagen", servleContext.getRealPath("/reportes/"));
                parametros.put("cod_ordenretiro", keyGenerated);
                keyGenerated--;
                JasperPrint jasperPrint = JasperFillManager.fillReport(dirReporte, parametros, con.getConnection());//server
                prints.add(jasperPrint);
            }
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename=" + nom_archivo);
            response.setContentType("application/pdf");
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCreatingBatchModeBookmarks(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            context.responseComplete();
        } catch (Exception e) {
            System.err.println(e);
        }
        listadoOrdenes = daoOrdenRetiro.findAllOrdenes();
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
            String temperatura = ord.getEs_temperado() == 1 ? "ReporteFreezer.jasper" : "ReporteNoFreezer.jasper";
//            String jrxmlPath = temperatura.equals("ReporteFreezer.jasper") ? "ReporteFreezer.jrxml" : "ReporteNoFreezer.jrxml";//server
            //InputStream
//            InputStream is = new FileInputStream(servleContext.getRealPath("/reportes/") + jrxmlPath);//server
            String dirReporte = servleContext.getRealPath("/reportes/" + temperatura);
            String nom_archivo = "OR_BK_" + ord.getBooking() + ".pdf";
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

    public void enviarMail() throws EmailException, SQLException, IOException {
        StringBuilder sb = new StringBuilder();
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
            String authuser = "Customerservice@delpac-sa.com";//LosInkas
            String authpwd = "cs6609";//LosInkas
//            String authuser = "jorge.castaneda@bottago.com";
//            String authpwd = "jorgec012";
//            String authuser = "jorgito14@gmail.com";
//            String authpwd = "p4s4j3r0";
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setSSLOnConnect(false); //LosInkas
//            email.setSSLOnConnect(true); //Gmail
            email.setDebug(true);
            email.setHostName("mailserver.losinkas.com"); //LosInkas
//            email.setHostName("ns0.ovh.net");
//            email.setHostName("smtp.gmail.com");
            email.getMailSession().getProperties().put("mail.smtps.auth", "false");
            email.getMailSession().getProperties().put("mail.debug", "true");
            email.getMailSession().getProperties().put("mail.smtp.port", "26"); //LosInkas
//            email.getMailSession().getProperties().put("mail.smtp.port", "587");
//            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587"); //gmail
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //gmail
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
            email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "false");
            email.setFrom("Customerservice@delpac-sa.com", "Servico al cliente Delpac");//LosInkas
//            email.setFrom("jorgito14@gmail.com", "Prueba envío de correo");
            email.setSubject(ord.getCia_nombre() + " / " + ord.getPto_nombre() + "/ BOOKING " + ord.getBooking() + " " + ord.getDsp_itinerario());

            sb.append("<strong>Estimados:</strong><br />").append(System.lineSeparator());
            sb.append("Buenas, adjunto la orden de retiro para la nave <strong>" + ord.getDsp_itinerario() + "</strong><br />").append(System.lineSeparator());
            sb.append("<br />"
                    + "<ul><li>Favor retirar el sello en nuestras oficinas.</li>"
                    + "<li><strong>Traer la orden de retiro</strong> para poder formalizar la entrega del sello.</li>"
                    + "<li><strong>Copia de cedula</strong> de la persona que retirará el sello.</li>"
                    + "<li><strong>Traer carta de autorización</strong> por parte del exportador nombrando al delegado que retirará el sello.</li>"
                    + "<li><strong>MUY IMPORTANTE: Se recuerda que a partir del 1 de Julio, 2016 todo contenedor deberá contar con certificado de "
                    + "VGM (Masa Bruta Verificada) antes del embarque, caso contrario el contenedor no podrá ser considerado para embarque. CUT OFF VGM, "
                    + "24 horas antes del atraque de la nave.</strong></li> "
                    + "<br /><br />").append(System.lineSeparator());
            sb.append("Señores de <strong>" + ord.getLoc_salidades() + "</strong> favor " + ord.getCondicion() + "<br /><br />").append(System.lineSeparator());
            if (!ord.getDetalle().isEmpty()) {
                sb.append(ord.getDetalle()).append(System.lineSeparator());
                sb.append("<br /><strong>Requerimiento Especial: " + ord.getReq_especial2() + "</strong><br /><br />").append(System.lineSeparator());
                sb.append("<strong>Remark:</strong> " + ord.getRemark() + " <br /><br />").append(System.lineSeparator());
                sb.append("Gracias.<br /><br />"
                        + "<strong>Saludos Cordiales / Best Regards</strong><br />"
                        + "JOSE CARRIEL M. II DELPAC S.A. II Av. 9 de Octubre 2009 y Los Ríos, Edificio el Marqués II Guayaquil - Ecuador <br />"
                        + "Tel.: +593 42371 172/ +593 42365 626 II Cel.: +59 998152266 II Mail: jcarriel@delpac-sa.com");
            } else {
                sb.append("<br /><strong>Requerimiento Especial: " + ord.getReq_especial2() + "</strong><br /><br />").append(System.lineSeparator());
                sb.append("<strong>Remark:</strong> " + ord.getRemark() + " <br /><br />").append(System.lineSeparator());
                sb.append("Gracias.<br /><br />"
                        + "<strong>Saludos Cordiales / Best Regards</strong><br />"
                        + "JOSE CARRIEL M. II DELPAC S.A. II Av. 9 de Octubre 2009 y Los Ríos, Edificio el Marqués II Guayaquil - Ecuador <br />"
                        + "Tel.: +593 42371 172/ +593 42365 626 II Cel.: +59 998152266 II Mail: jcarriel@delpac-sa.com");
                email.setMsg(sb.toString());
            }
            email.setMsg(sb.toString());
            email.addTo(ord.getDestinario().split(","));
            //email.addTo("gint1@tercon.com.ec, gout2@tercon.com.ec, controlgate@tercon.com.ec, " + ord.getDestinario().split(",")); //LosInkas
            if (!ord.getCc().isEmpty()) {
                //email.addCc("dgonzalez@delpac-sa.com, charmsen@delpac-sa.com, vmendoza@delpac-sa.com, vzambrano@delpac-sa.com, vchiriboga@delpac-sa.com, vortiz@delpac-sa.com, mbenitez@delpac-sa.com, crobalino@delpac-sa.com, jcarriel@delpac-sa.com, fsalame@delpac-sa.com," + ord.getCc().split(",")); //LosInkas
                email.addCc("jorge_3_11_91@hotmail," + ord.getCc().split(",")); //LosInkas
            } else {
//                email.addCc("dgonzalez@delpac-sa.com, charmsen@delpac-sa.com, vmendoza@delpac-sa.com, vzambrano@delpac-sa.com, vchiriboga@delpac-sa.com, vortiz@delpac-sa.com, mbenitez@delpac-sa.com, crobalino@delpac-sa.com, jcarriel@delpac-sa.com, fsalame@delpac-sa.com"); //LosInkas
                email.addCc("jorge_3_11_91@hotmail.com"); //LosInkas
            }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPdfFile(StreamedContent pdfFile) {
        this.pdfFile = pdfFile;
    }

    public StreamedContent getDownloadableFile() {
        return downloadableFile;
    }

    public void setDownloadableFile(StreamedContent downloadableFile) {
        this.downloadableFile = downloadableFile;
    }

    public int getIngresado() {
        return ingresado;
    }

    public void setIngresado(int ingresado) {
        this.ingresado = ingresado;
    }

}
