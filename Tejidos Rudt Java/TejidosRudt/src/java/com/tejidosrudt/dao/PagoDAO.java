/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Pago;
import com.tejidosrudt.modelo.Pago.MetodoPago;
import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;



/**
 *
 * @author sarac
 */
public class PagoDAO {
    Connection con;

    public PagoDAO() {
        con = ConexionBD.getConnection();
    }

    // Registra un nuevo pago asociado a un pedido
    public void crearPago(Pago pago) throws SQLException {
        String SQL = "INSERT INTO pago (idPedido, monto, metodo) VALUES (?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, pago.getIdPedido());
            ps.setBigDecimal(2, pago.getMonto());
            ps.setString(3, pago.getMetodo().name()); 
            ps.executeUpdate();
        }
    }

    // Actualiza la información de un pago registrado
    public void actualizarPago(Pago pago) throws SQLException {
        String SQL = "UPDATE pago SET idPedido=? monto=?, metodo=? WHERE idPago=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, pago.getIdPedido());
            ps.setBigDecimal(2, pago.getMonto());
            ps.setString(3, pago.getMetodo().name());
            ps.setInt(4, pago.getIdPago()); // corregido índice
            ps.executeUpdate();
        }
    }

    // Elimina un pago de la BD
    public void eliminarPago(Pago pago) throws SQLException {
        String SQL = "DELETE FROM pago WHERE idPago=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, pago.getIdPago());
            ps.executeUpdate();
        }
    }

    // Lista todos los pagos registrados
    public List<Pago> listarPagos() throws SQLException {
        List<Pago> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pago";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPago(rs));
            }
        }
        return lista;
    }


    // Busca un pago por su ID
    public Pago buscarPorId(int idPago) throws SQLException {
        String SQL = "SELECT * FROM pago WHERE idPago=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idPago);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearPago(rs);
            }
        }
        return null;
    }

    // Busca pagos por método de pago
    public List<Pago> buscarPorMetodo(MetodoPago metodo) throws SQLException {
        List<Pago> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pago WHERE metodo=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, metodo.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPago(rs));
            }
        }
        return lista;
    }

    // Buscar pagos por rango de monto
    public List<Pago> buscarPorRangoMonto(BigDecimal minimo, BigDecimal maximo) throws SQLException {
        List<Pago> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pago WHERE monto BETWEEN ? AND ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setBigDecimal(1, minimo);
            ps.setBigDecimal(2, maximo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPago(rs));
            }
        }
        return lista;
    }

    //JOIN: lista pagos con información de los pedidos asociados
    public List<Pago> listarPagosConPedidos() throws SQLException {
        List<Pago> lista = new ArrayList<>();
        String SQL = "SELECT pa.idPago, pa.monto, pa.metodo, pe.idPedido " +
                     "FROM pago pa " +
                     "LEFT JOIN pedidoencargo pe ON pa.idPedido = pe.idPedido";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pago pago = mapearPago(rs);
                lista.add(pago);
            }
        }
        return lista;
        
    }

    /* --- Método auxiliar para mapear ResultSet a Pago --- */
    private Pago mapearPago(ResultSet rs) throws SQLException {
        Pago p = new Pago();
        p.setIdPago(rs.getInt("idPago"));
        p.setMonto(rs.getBigDecimal("monto"));
        p.setMetodo(MetodoPago.valueOf(rs.getString("metodo").trim())); 
        p.setIdPedido(rs.getInt("idPedido"));
        return p;
    }
}