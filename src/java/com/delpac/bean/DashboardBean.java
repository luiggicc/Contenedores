/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.bean;

import com.delpac.dao.DashboardDAO;
import com.delpac.entity.Dashboard;
import com.delpac.entity.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Set;
import java.util.Map.*;

import org.primefaces.event.ItemSelectEvent;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Bottago SA
 */
@ManagedBean
@ViewScoped
public class DashboardBean {

    private List<Dashboard> lista = new ArrayList<>();
    private List<Dashboard> lista1 = new ArrayList<>();
    private List<Dashboard> lista2 = new ArrayList<>();
    private List<Dashboard> lista3 = new ArrayList<>();
    private List<Dashboard> lista4 = new ArrayList<>();
    private List<Dashboard> lista5 = new ArrayList<>();

    private BarChartModel barra;
    private BarChartModel barra1;
    private BarChartModel barra2;
    private BarChartModel barra3;
    private BarChartModel barra4;
    private BarChartModel barra5;

    private Dashboard dash = new Dashboard();
    private Usuario sessionUsuario;

    public DashboardBean() throws IOException {
        listar();
        graficar();
    }

    public void listar() {
        DashboardDAO dash;
        try {
            dash = new DashboardDAO();
            lista = dash.TipoContenedores();
            lista2 = dash.ContenedoresEnPuerto();
            lista3 = dash.ContenedoresEnCliente();
            lista4 = dash.ContenedoresVacio();
            lista5 = dash.StockSellos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void graficar() {
        barra = new BarChartModel();
        barra1 = new BarChartModel();
        barra2 = new BarChartModel();
        barra3 = new BarChartModel();
        barra4 = new BarChartModel();
        barra5 = new BarChartModel();

        ChartSeries serie = new BarChartSeries();
        ChartSeries serie2 = new BarChartSeries();
        ChartSeries serie3 = new BarChartSeries();
        ChartSeries serie4 = new BarChartSeries();
        ChartSeries serie5 = new BarChartSeries();

        serie.setLabel("Tipo Contenedor");
        for (Dashboard dashboard : lista) {
            serie.set(dashboard.getTipo_cont(), dashboard.getConteo_tipo_cont());
        }

        barra.addSeries(serie);
        barra.setTitle("Total contenedores según el tipo");
        barra.setDatatipFormat("%2$d");
        barra.setShowPointLabels(true);
        barra.setAnimate(true);

        Axis xAxis = barra.getAxis(AxisType.X);
        xAxis.setLabel("Tipo de Contenedor");

        Axis yAxis = barra.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad");
        yAxis.setMin(0);

        // ----------------------------------------------------------------------------------- //
        serie2.setLabel("Contenedores en Puerto");
        for (Dashboard dashboard : lista2) {
            serie2.set("Puerto", dashboard.getConteo_puerto());
        }

        barra2.addSeries(serie2);
        barra2.setTitle("Total contenedores que están en Puerto");
        barra2.setDatatipFormat("%3$d");
        barra2.setShowPointLabels(true);
        barra2.setAnimate(true);

        Axis xAxis2 = barra2.getAxis(AxisType.X);

        Axis yAxis2 = barra2.getAxis(AxisType.Y);
        yAxis2.setLabel("Cantidad");
        yAxis2.setMin(0);

        // ----------------------------------------------------------------------------------- //
        serie3.setLabel("Contenedores en Cliente");
        for (Dashboard dashboard : lista3) {
            serie3.set("Cliente", dashboard.getConteo_cliente());
        }

        barra3.addSeries(serie3);
        barra3.setTitle("Total contenedores que están en Cliente");
        barra3.setDatatipFormat("%3$d");
        barra3.setShowPointLabels(true);
        barra3.setAnimate(true);

        Axis xAxis3 = barra3.getAxis(AxisType.X);

        Axis yAxis3 = barra3.getAxis(AxisType.Y);
        yAxis3.setLabel("Cantidad");
        yAxis3.setMin(0);

        // ----------------------------------------------------------------------------------- //
        serie4.setLabel("Contenedores Vacio");
        for (Dashboard dashboard : lista4) {
            serie4.set("Vacio", dashboard.getConteo_vacio());
        }

        barra4.addSeries(serie4);
        barra4.setTitle("Total contenedores que están Vacios");
        barra4.setDatatipFormat("%3$d");
        barra4.setShowPointLabels(true);
        barra4.setAnimate(true);

        Axis xAxis4 = barra4.getAxis(AxisType.X);

        Axis yAxis4 = barra4.getAxis(AxisType.Y);
        yAxis4.setLabel("Cantidad");
        yAxis4.setMin(0);

        // ---------------------------------------------------------------------------------
        serie5.setLabel("Stock Sellos");
        for (Dashboard dashboard : lista5) {
            serie5.set(dashboard.getSellos_estado(), dashboard.getSellos_stock());
        }

        barra5.addSeries(serie5);
        barra5.setTitle("Stock de Sellos");        
        barra5.setDatatipFormat("%3$d");
        barra5.setShowPointLabels(true);
        barra5.setAnimate(true);
        

        Axis xAxis5 = barra5.getAxis(AxisType.X);
        xAxis5.setLabel("Estado");

        Axis yAxis5 = barra.getAxis(AxisType.Y);
        yAxis5.setLabel("Cantidad");
        yAxis5.setMin(0);
    }

    public void handleSelect(ItemSelectEvent event) {
        Integer seriesIndex = event.getSeriesIndex();
        Integer itemIndex = event.getItemIndex();
        BarChartModel modelo = (BarChartModel) ((org.primefaces.component.chart.Chart) event.getSource()).getModel();
        ChartSeries miserie = modelo.getSeries().get(event.getSeriesIndex());
        Set<Entry<Object, Number>> mapValues = miserie.getData().entrySet();
        Entry<Object, Number>[] test = new Entry[mapValues.size()];
        mapValues.toArray(test);
        String tipo_cont = test[event.getItemIndex()].getKey().toString();
        String cantidad = test[event.getItemIndex()].getValue().toString();

        DashboardDAO dash;
        try {
            dash = new DashboardDAO();
            lista1 = dash.DetalleContenedores(tipo_cont);
            barra1 = new BarChartModel();
            ChartSeries serie1 = new BarChartSeries();
            serie1.setLabel("Detalle Contenedor");
            for (Dashboard dashboard : lista1) {
                serie1.set(dashboard.getStatus(), dashboard.getConteo_detalle_contenedor());
            }
            barra1.addSeries(serie1);
            barra1.setTitle("Total contenedores según el estado");
            barra1.setDatatipFormat("%3$d");
            barra1.setShowPointLabels(true);
            barra1.setAnimate(true);

            Axis xAxis = barra1.getAxis(AxisType.X);
            xAxis.setLabel("Status");
            xAxis.setMax(500);

            Axis yAxis = barra1.getAxis(AxisType.Y);
            yAxis.setLabel("Cantidad");
            yAxis.setMin(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Dashboard> getLista() {
        return lista;
    }

    public void setLista(List<Dashboard> lista) {
        this.lista = lista;
    }

    public List<Dashboard> getLista1() {
        return lista1;
    }

    public void setLista1(List<Dashboard> lista1) {
        this.lista1 = lista1;
    }

    public List<Dashboard> getLista2() {
        return lista2;
    }

    public void setLista2(List<Dashboard> lista2) {
        this.lista2 = lista2;
    }

    public BarChartModel getBarra() {
        return barra;
    }

    public void setBarra(BarChartModel barra) {
        this.barra = barra;
    }

    public BarChartModel getBarra1() {
        return barra1;
    }

    public void setBarra1(BarChartModel barra1) {
        this.barra1 = barra1;
    }

    public Dashboard getDash() {
        return dash;
    }

    public void setDash(Dashboard dash) {
        this.dash = dash;
    }

    public List<Dashboard> getLista3() {
        return lista3;
    }

    public void setLista3(List<Dashboard> lista3) {
        this.lista3 = lista3;
    }

    public List<Dashboard> getLista4() {
        return lista4;
    }

    public void setLista4(List<Dashboard> lista4) {
        this.lista4 = lista4;
    }

    public BarChartModel getBarra2() {
        return barra2;
    }

    public void setBarra2(BarChartModel barra2) {
        this.barra2 = barra2;
    }

    public BarChartModel getBarra3() {
        return barra3;
    }

    public void setBarra3(BarChartModel barra3) {
        this.barra3 = barra3;
    }

    public BarChartModel getBarra4() {
        return barra4;
    }

    public void setBarra4(BarChartModel barra4) {
        this.barra4 = barra4;
    }

    public List<Dashboard> getLista5() {
        return lista5;
    }

    public void setLista5(List<Dashboard> lista5) {
        this.lista5 = lista5;
    }

    public BarChartModel getBarra5() {
        return barra5;
    }

    public void setBarra5(BarChartModel barra5) {
        this.barra5 = barra5;
    }

}
