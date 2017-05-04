/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delpac.dao;

import conexion.conexion;
import com.delpac.entity.Dashboard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bottago SA
 */
public class DashboardDAO {

    public List<Dashboard> TipoContenedores() throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select con_tipcont, count(con_tipcont) as tipo_cont "
                    + "from publico.mae_container "
                    + "where con_estado = 'A' "
                    + "group by con_tipcont";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setTipo_cont(rs.getString("con_tipcont"));
                dashboard.setConteo_tipo_cont(rs.getInt("tipo_cont"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Tipo Contenedores: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }

    public List<Dashboard> DetalleContenedores(String tipo_cont) throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select status, count(x.*) as conteo "
                    + "from (select des.movimiento, des.equipo_identi, des.status, des.ciclo, "
                    + "rank() over (partition by des.equipo_identi order by des.ciclo desc, primo.prioridad desc) as rank "
                    + "from publico.descarga des "
                    + "inner join publico.prioridad_movimiento primo "
                    + "on des.movimiento = primo.movimiento "
                    + "inner join publico.mae_container con "
                    + "on des.equipo_identi = con.con_codigo "
                    + "where con.con_tipcont = ? "
                    + "group by des.movimiento, des.equipo_identi, des.status, des.ciclo, primo.prioridad "
                    + ") x "
                    + "where rank = 1 and movimiento != 'Export' "
                    + "group by status";
            pst = con.getConnection().prepareStatement(query);
            pst.setString(1, tipo_cont);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setStatus(rs.getString("status"));
                dashboard.setConteo_detalle_contenedor(rs.getInt("conteo"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Conteo Detalle Tipo Contenedores: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }

    public List<Dashboard> ContenedoresEnPuerto() throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select count(x.*) as Puerto "
                    + "from (select des.movimiento, des.equipo_identi, des.status, des.ciclo, "
                    + "rank() over (partition by des.equipo_identi order by des.ciclo desc, primo.prioridad desc) as rank "
                    + "from publico.descarga des "
                    + "inner join publico.prioridad_movimiento primo "
                    + "on des.movimiento = primo.movimiento "
                    + ") x "
                    + "where (x.rank = 1 and (x.movimiento = 'GateIn Puerto' or x.movimiento = 'Descarga'))";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setConteo_puerto(rs.getInt("Puerto"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Conteo Puerto: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }

    public List<Dashboard> ContenedoresEnCliente() throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select count(y.*) as Cliente "
                    + "from (select des.movimiento, des.equipo_identi, des.status, des.ciclo, "
                    + "rank() over (partition by des.equipo_identi order by des.ciclo desc, primo.prioridad desc) as rank "
                    + "from publico.descarga des "
                    + "inner join publico.prioridad_movimiento primo "
                    + "on des.movimiento = primo.movimiento "
                    + ") y "
                    + "where rank = 1 and (y.movimiento = 'GateOut Puerto' or y.movimiento = 'GateOut Patio')";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setConteo_cliente(rs.getInt("Cliente"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Conteo Cliente: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }

    public List<Dashboard> ContenedoresVacio() throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select count(z.*) as Vacio "
                    + "from (select des.movimiento, des.equipo_identi, des.status, des.ciclo, "
                    + "rank() over (partition by des.equipo_identi order by des.ciclo desc, primo.prioridad desc) as rank "
                    + "from publico.descarga des "
                    + "inner join publico.prioridad_movimiento primo "
                    + "on des.movimiento = primo.movimiento "
                    + ") z "
                    + "where z.rank = 1 and (z.movimiento = 'GateIn Patio')";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setConteo_vacio(rs.getInt("Vacio"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Conteo Vacio: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }

    public List<Dashboard> StockSellos() throws SQLException {
        List<Dashboard> lista = null;
        ResultSet rs;
        conexion con = new conexion();
        PreparedStatement pst;
        try {
            String query = "select case inv_estado when 'A' then 'Asignado' "
                    + "when 'E' then 'Eliminado' "
                    + "when 'S' then 'Disponible' "
                    + "END as estado, "
                    + "count(inv_estado) as stock "
                    + "from publico.invsellos "
                    + "group by estado ";
            pst = con.getConnection().prepareStatement(query);
            rs = pst.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Dashboard dashboard = new Dashboard();
                dashboard.setSellos_stock(rs.getInt("stock"));
                dashboard.setSellos_estado(rs.getString("estado"));
                lista.add(dashboard);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("DAO Dashboard Stock sellos: " + e.getMessage());
            con.getConnection().rollback();
        } finally {
            con.desconectar();
        }
        return lista;
    }
}
