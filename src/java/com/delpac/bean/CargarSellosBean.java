/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;
import com.delpac.dao.SellosDAO;
import com.delpac.entity.Sellos;
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
public class CargarSellosBean implements Serializable{
    private StreamedContent downloadableFile;
    private Usuario sessionUsuario;
    private final List<FileUploaded> uploadedFilesList = new ArrayList<>();
    private List<Sellos> listadoSellos = new ArrayList<>();
    private List<Sellos> failedSellosList = new ArrayList<>();
    private List<Sellos> selectedSellos;
    private List<Sellos> filteredSellos;
    private Sellos sellos = new Sellos();
    private SellosDAO daoSellos = new SellosDAO();
    private int cantidadSellosRepetidos = 0;
    
    public void authorized() {
    }
    
    public CargarSellosBean(){
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
                InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/matriz/sellos.xlsx");
                downloadableFile = new DefaultStreamedContent(stream, "application/xlsx", "sellos.xlsx");
                filteredSellos = daoSellos.findAll();
            }
        } catch (Exception e) {
            System.out.println("Bean Constructor: " + e.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (event.getFile().equals(null)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File is null", null));
        }
        try {
            uploadedFilesList.add(new FileUploaded(
                    getExtension(event.getFile().getFileName()),
                    event.getFile().getInputstream()));
        } catch (IOException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error reading file" + e, null));
        }
    }
    
    public List<Sellos> verifyFromExcel(List<Sellos> listadoXLS) {
        int secuencialSellos = getListadoSellos().size();
        List<Sellos> lista = getListadoSellos();
        for (Sellos s : listadoXLS) {
            secuencialSellos++;
            s.setSecuencial(secuencialSellos);
            if (lista.contains(s)) {
                s.setRepeated("repetido");
                cantidadSellosRepetidos++;
                lista.add(s);
            } else {
                lista.add(s);
            }
        }
        return lista;
    }

    public void clearFailedList() {
        failedSellosList.clear();
        uploadedFilesList.clear();
        listadoSellos.clear();
        cantidadSellosRepetidos = 0;
    }

    public void guardarListadoSellos() throws SQLException {
        SellosDAO dao = new SellosDAO();
        for (Sellos sellos : getListadoSellos()) {
            int flag = dao.guardarSellos(sellos);
            if (flag != 0) {
                failedSellosList.add(sellos);
            }
        }
        getListadoSellos().clear();
        clearFailedList();
    }

    public void deleteRows() {
        Iterator iterador = listadoSellos.iterator();
        System.out.println(selectedSellos.size());
        if (!selectedSellos.isEmpty()) {
            for (Sellos s : selectedSellos) {
                while (iterador.hasNext()) {
                    Sellos lss = (Sellos) iterador.next();
                    if (lss.getInv_sello().equals(s.getInv_sello()) && lss.getSecuencial() == s.getSecuencial()) {
                        iterador.remove();
                        if (s.getRepeated().equals("repetido")) {
                            cantidadSellosRepetidos--;
                        }
                    }
                }
                iterador = listadoSellos.iterator();
            }
            Iterator iterador2 = listadoSellos.iterator();
            int contador = 0;
            while (iterador2.hasNext()) {
                contador++;
                Sellos piterado = (Sellos) iterador2.next();
                piterado.setSecuencial(contador);
                int indice = contador - 1;
                listadoSellos.set(indice, piterado);
            }
            RequestContext.getCurrentInstance().update("frm:sellTable");
        }
    }

    public void downloadMatriz() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/matriz/sellos.xlsx");
        downloadableFile = new DefaultStreamedContent(stream, "application/xlsx", "sellos.xlsx");
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
                setListadoSellos(verifyFromExcel(importData(workbook, 0)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (cantidadSellosRepetidos != 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            String texto = "El listado cargado contiene " + cantidadSellosRepetidos + " elementos repetidos. Por favor verifica.";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", texto));
        }
        uploadedFilesList.clear();
    }

    public List<Sellos> importData(Workbook workbook, int tabNumber) throws IOException {
        List<Sellos> lista = new ArrayList<>();
        String[][] data;
        Sheet sheet = workbook.getSheetAt(tabNumber);
        data = new String[sheet.getLastRowNum() + 1][];
        Row[] row = new Row[sheet.getLastRowNum() + 1];
        Cell[][] cell = new Cell[row.length][];

        for (int i = 1; i < row.length; i++) {
            row[i] = sheet.getRow(i);
            cell[i] = new Cell[row[i].getLastCellNum()];
            data[i] = new String[row[i].getLastCellNum()];
            Sellos sellos = new Sellos();
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = row[i].getCell(j);
                if (cell[i][j] != null) {
                    switch (j) {
                        case 0:
                            sellos.setInv_seguridad(getDataFromCell(cell, i, j));
                            break;
                        case 1:
                            sellos.setInv_sello(getDataFromCell(cell, i, j));
                            break;
                    }
                } else {
                    switch (j) {
                        case 0:
                            sellos.setInv_seguridad("");
                            break;
                        case 1:
                            sellos.setInv_sello("");
                            break;
                    }
                }
            }
            if (!"".equals(sellos.getInv_sello())) {
                lista.add(sellos);
            }
        }

        return lista;
    }

    public StreamedContent getDownloadableFile() {
        return downloadableFile;
    }

    public void setDownloadableFile(StreamedContent downloadableFile) {
        this.downloadableFile = downloadableFile;
    }

    public List<Sellos> getListadoSellos() {
        return listadoSellos;
    }

    public void setListadoSellos(List<Sellos> listadoSellos) {
        this.listadoSellos = listadoSellos;
    }

    public List<Sellos> getFailedSellosList() {
        return failedSellosList;
    }

    public void setFailedSellosList(List<Sellos> failedSellosList) {
        this.failedSellosList = failedSellosList;
    }

    public List<Sellos> getSelectedSellos() {
        return selectedSellos;
    }

    public void setSelectedSellos(List<Sellos> selectedSellos) {
        this.selectedSellos = selectedSellos;
    }

    public List<Sellos> getFilteredSellos() {
        return filteredSellos;
    }

    public void setFilteredSellos(List<Sellos> filteredSellos) {
        this.filteredSellos = filteredSellos;
    }

    public Sellos getSellos() {
        return sellos;
    }

    public void setSellos(Sellos sellos) {
        this.sellos = sellos;
    }

    public SellosDAO getDaoSellos() {
        return daoSellos;
    }

    public void setDaoSellos(SellosDAO daoSellos) {
        this.daoSellos = daoSellos;
    }    
}
