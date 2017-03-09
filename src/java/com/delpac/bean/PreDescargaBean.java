/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.PreDescargaDAO;
import com.delpac.dao.ItinerarioDAO;
import com.delpac.dao.LineaDAO;
import com.delpac.dao.PuertoDAO;

import com.delpac.entity.PreDescarga;
import com.delpac.entity.Itinerario;
import com.delpac.entity.Linea;
import com.delpac.entity.Puerto;

import com.delpac.entity.FileUploaded;
import com.delpac.entity.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class PreDescargaBean implements Serializable {

    private StreamedContent downloadableFile;
    private Usuario sessionUsuario;
    private final List<FileUploaded> uploadedFilesList = new ArrayList<>();
    private List<PreDescarga> listadoPreDescarga = new ArrayList<>();
    private List<PreDescarga> failedPreDescargaList = new ArrayList<>();
    private List<PreDescarga> filteredPreDescarga;
    private PreDescarga predescarga = new PreDescarga();
    private PreDescargaDAO daoPreDescarga = new PreDescargaDAO();
//    private int cantidadSellosRepetidos = 0;

    private ItinerarioDAO daoItinerario = new ItinerarioDAO();
    private List<Itinerario> listaItinerarios = daoItinerario.findAllItinerario();
    private int ItinerarioIdSelected;

    private LineaDAO daoLinea = new LineaDAO();
    private List<Linea> listaLineas = daoLinea.findAllLinea();
    private int LineaIdSelected;

    private PuertoDAO daoPuerto = new PuertoDAO();
    private List<Puerto> listaPuertos = daoPuerto.findAllPuertos();
    private int PuertoIdSelected;

    public void authorized() {
    }

    public PreDescargaBean() {
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
                InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/matriz/predescarga.xlsx");
                downloadableFile = new DefaultStreamedContent(stream, "application/xlsx", "predescarga.xlsx");
//                filteredPreDescarga = daoPreDescarga.findAll();

                listaItinerarios = daoItinerario.findAllItinerario();
                listaLineas = daoLinea.findAllLinea();
                listaPuertos = daoPuerto.findAllPuertos();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (event.getFile().equals(null)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hay archivo seleccionado", null));
        }
        try {
            uploadedFilesList.add(new FileUploaded(
                    getExtension(event.getFile().getFileName()),
                    event.getFile().getInputstream()));
        } catch (IOException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer el archivo" + e, null));
        }
    }

    public void guardarListadoPreDescarga() throws SQLException {
        PreDescargaDAO dao = new PreDescargaDAO();
        for (PreDescarga preDescarga : getListadoPreDescarga()) {
            int flag = dao.guardarPreDescarga(preDescarga, ItinerarioIdSelected);

            if (flag != 0) {
                failedPreDescargaList.add(preDescarga);
            }
        }

    }

    public void downloadMatriz() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/matriz/sellos.xlsx");
        downloadableFile = new DefaultStreamedContent(stream, "application/xlsx", "predescarga.xlsx");
    }

    public String getDataFromCell(Cell[][] cell, int i, int j) {
        switch (cell[i][j].getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell[i][j]) || HSSFDateUtil.isCellInternalDateFormatted(cell[i][j])) {
                    SimpleDateFormat objFormat = new SimpleDateFormat("dd/MM/yyyy");
                    return objFormat.format(cell[i][j].getDateCellValue());
                } else {
                    Double value = cell[i][j].getNumericCellValue();
                    return value.intValue() + "";
                }
            case Cell.CELL_TYPE_STRING:
                return cell[i][j].getStringCellValue();
        }
        return "";
    }

    public String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    public void uploadFileList() {
        Workbook workbook = null;
        for (FileUploaded fu : uploadedFilesList) {
            try {
                switch (fu.getExtension()) {
                    case "xls":
                        workbook = new HSSFWorkbook(fu.getFileInputStream());
                        break;
                    case "xlsx":
                        workbook = new XSSFWorkbook(fu.getFileInputStream());
                        break;
                }
                setListadoPreDescarga(importData(workbook, 0));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        uploadedFilesList.clear();
    }

    public List<PreDescarga> importData(Workbook workbook, int tabNumber) throws IOException {
        List<PreDescarga> lista = new ArrayList<>();
        String[][] data;
        DataFormatter df = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(tabNumber);
        data = new String[sheet.getLastRowNum() + 1][];
        Row[] row = new Row[sheet.getLastRowNum() + 1];
        Cell[][] cell = new Cell[row.length][];

        for (int i = 1; i < row.length; i++) {
            row[i] = sheet.getRow(i);
            cell[i] = new Cell[row[i].getLastCellNum()];
            data[i] = new String[row[i].getLastCellNum()];
            PreDescarga preDescarga = new PreDescarga();
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = row[i].getCell(j);
                if (cell[i][j] != null) {
                    switch (j) {
                        case 0:
                            preDescarga.setBl(getDataFromCell(cell, i, j));
                            break;
                        case 1:
                            preDescarga.setConsignatario(getDataFromCell(cell, i, j));
                            break;
                        case 2:
                            preDescarga.setPto_origen(getDataFromCell(cell, i, j));
                            break;
                        case 3:
                            preDescarga.setPto_destino(getDataFromCell(cell, i, j));
                            break;
                        case 4:
                            preDescarga.setFec_embarque(getDataFromCell(cell, i, j));
                            break;
                        case 5:
                            preDescarga.setPeso(Double.parseDouble(getDataFromCell(cell, i, j)));
                            break;
                        case 6:
                            preDescarga.setBulto(Double.parseDouble(getDataFromCell(cell, i, j)));
                            break;
                        case 7:
                            preDescarga.setEmbalaje(getDataFromCell(cell, i, j));
                            break;
                        case 8:
                            preDescarga.setCarga(getDataFromCell(cell, i, j));
                            break;
                        case 9:
                            preDescarga.setCon_codigo(getDataFromCell(cell, i, j));
                            break;
                        case 10:
                            preDescarga.setTemperatura(getDataFromCell(cell, i, j));
                            break;
                        case 11:
                            preDescarga.setVentilacion(getDataFromCell(cell, i, j));
                            break;
                        case 12:
                            preDescarga.setSello(getDataFromCell(cell, i, j));
                            break;
                        case 13:
                            preDescarga.setSown(getDataFromCell(cell, i, j));
                            break;
                        case 14:
                            preDescarga.setClassimo(getDataFromCell(cell, i, j));
                            break;
                        case 15:
                            preDescarga.setUnnro(getDataFromCell(cell, i, j));
                            break;
                        case 16:
                            preDescarga.setTip_cont(getDataFromCell(cell, i, j));
                            break;
                        case 17:
                            preDescarga.setCondicion(getDataFromCell(cell, i, j));
                            break;
                        case 18:
                            preDescarga.setAlm_codigo(getDataFromCell(cell, i, j));
                            break;
                    }
                } else {
                    switch (j) {
                        case 1:
                            preDescarga.setBl("");
                            break;
                        case 2:
                            preDescarga.setConsignatario("");
                            break;
                        case 3:
                            preDescarga.setPto_origen("");
                            break;
                        case 4:
                            preDescarga.setPto_destino("");
                            break;
                        case 5:
                            preDescarga.setFec_embarque("");
                            break;
                        case 6:
                            preDescarga.setPeso(0);
                            break;
                        case 7:
                            preDescarga.setBulto(0);
                            break;
                        case 8:
                            preDescarga.setEmbalaje("");
                            break;
                        case 9:
                            preDescarga.setCarga("");
                            break;
                        case 10:
                            preDescarga.setCon_codigo("");
                            break;
                        case 11:
                            preDescarga.setTemperatura("");
                            break;
                        case 12:
                            preDescarga.setVentilacion("");
                            break;
                        case 13:
                            preDescarga.setSello("");
                            break;
                        case 14:
                            preDescarga.setSown("");
                            break;
                        case 15:
                            preDescarga.setClassimo("");
                            break;
                        case 16:
                            preDescarga.setUnnro("");
                            break;
                        case 17:
                            preDescarga.setTip_cont("");
                            break;
                        case 18:
                            preDescarga.setCondicion("");
                            break;
                        case 19:
                            preDescarga.setAlm_codigo("");
                            break;
                    }
                }
            }
            lista.add(preDescarga);
        }
        return lista;
    }

    public StreamedContent getDownloadableFile() {
        return downloadableFile;
    }

    public void setDownloadableFile(StreamedContent downloadableFile) {
        this.downloadableFile = downloadableFile;
    }

    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
    }

    public List<PreDescarga> getListadoPreDescarga() {
        return listadoPreDescarga;
    }

    public void setListadoPreDescarga(List<PreDescarga> listadoPreDescarga) {
        this.listadoPreDescarga = listadoPreDescarga;
    }

    public List<PreDescarga> getFailedPreDescargaList() {
        return failedPreDescargaList;
    }

    public void setFailedPreDescargaList(List<PreDescarga> failedPreDescargaList) {
        this.failedPreDescargaList = failedPreDescargaList;
    }

    public List<PreDescarga> getFilteredPreDescarga() {
        return filteredPreDescarga;
    }

    public void setFilteredPreDescarga(List<PreDescarga> filteredPreDescarga) {
        this.filteredPreDescarga = filteredPreDescarga;
    }

    public PreDescarga getPredescarga() {
        return predescarga;
    }

    public void setPredescarga(PreDescarga predescarga) {
        this.predescarga = predescarga;
    }

    public PreDescargaDAO getDaoPreDescarga() {
        return daoPreDescarga;
    }

    public void setDaoPreDescarga(PreDescargaDAO daoPreDescarga) {
        this.daoPreDescarga = daoPreDescarga;
    }

    public ItinerarioDAO getDaoItinerario() {
        return daoItinerario;
    }

    public void setDaoItinerario(ItinerarioDAO daoItinerario) {
        this.daoItinerario = daoItinerario;
    }

    public List<Itinerario> getListaItinerarios() {
        return listaItinerarios;
    }

    public void setListaItinerarios(List<Itinerario> listaItinerarios) {
        this.listaItinerarios = listaItinerarios;
    }

    public int getItinerarioIdSelected() {
        return ItinerarioIdSelected;
    }

    public void setItinerarioIdSelected(int ItinerarioIdSelected) {
        this.ItinerarioIdSelected = ItinerarioIdSelected;
    }

    public LineaDAO getDaoLinea() {
        return daoLinea;
    }

    public void setDaoLinea(LineaDAO daoLinea) {
        this.daoLinea = daoLinea;
    }

    public List<Linea> getListaLineas() {
        return listaLineas;
    }

    public void setListaLineas(List<Linea> listaLineas) {
        this.listaLineas = listaLineas;
    }

    public int getLineaIdSelected() {
        return LineaIdSelected;
    }

    public void setLineaIdSelected(int LineaIdSelected) {
        this.LineaIdSelected = LineaIdSelected;
    }

    public PuertoDAO getDaoPuerto() {
        return daoPuerto;
    }

    public void setDaoPuerto(PuertoDAO daoPuerto) {
        this.daoPuerto = daoPuerto;
    }

    public List<Puerto> getListaPuertos() {
        return listaPuertos;
    }

    public void setListaPuertos(List<Puerto> listaPuertos) {
        this.listaPuertos = listaPuertos;
    }

    public int getPuertoIdSelected() {
        return PuertoIdSelected;
    }

    public void setPuertoIdSelected(int PuertoIdSelected) {
        this.PuertoIdSelected = PuertoIdSelected;
    }

}
