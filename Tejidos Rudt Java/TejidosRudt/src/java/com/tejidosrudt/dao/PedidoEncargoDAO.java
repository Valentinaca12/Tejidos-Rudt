
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.PedidoEncargo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author sarac
 */
public class PedidoEncargoDAO {
    Connection con;

    public PedidoEncargoDAO() {
        con = ConexionBD.getConnection();
    }

    // Registar un nuevo pedido en la BD
    public void crearPedidoEncargo(PedidoEncargo pedido) throws SQLException {
        String SQL = "INSERT INTO pedidoencargo (fechaPedido, fechaEnvio, idCliente, estado, valorTotal, idVendedor) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setDate(1, pedido.getFechaPedido());
            if (pedido.getFechaEnvio() != null) {
                ps.setDate(2, pedido.getFechaEnvio());
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, pedido.getIdCliente());  
   
            ps.setString(4, pedido.getEstado());
            ps.setBigDecimal(5, pedido.getValorTotal());
            ps.setInt(6, pedido.getIdVendedor());
            ps.executeUpdate();
        }
    }

    // Actualiza la información de un pedido existente
    public void actualizarPedidoEncargo(PedidoEncargo pedido) throws SQLException {
        String SQL = "UPDATE pedidoencargo SET fechaPedido=?, fechaEnvio=?, idCliente=?, estado=?, valorTotal=?, idVendedor=? WHERE idPedido=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setDate(1, pedido.getFechaPedido());
            if (pedido.getFechaEnvio() != null) {
                ps.setDate(2, pedido.getFechaEnvio());
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, pedido.getIdCliente());
            ps.setString(4, pedido.getEstado());
            ps.setBigDecimal(5, pedido.getValorTotal());
            ps.setInt(6, pedido.getIdVendedor());
            ps.setInt(7, pedido.getIdPedido());
            ps.executeUpdate();
        }
    }
    
    //Actualiza el estado del pedido
    public void actualizarEstadoPedido(int idPedido, String nuevoEstado) throws SQLException {
        String SQL = "UPDATE pedidoencargo SET estado=? WHERE idPedido=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }


    // Elimina un pedido de la BD
    public void eliminarPedidoEncargo(PedidoEncargo pedido) throws SQLException {
        String SQL = "DELETE FROM pedidoencargo WHERE idPedido=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, pedido.getIdPedido());
            ps.executeUpdate();
        }
    }

    // Lista todos los pedidos registrados
    public List<PedidoEncargo> listarPedidoEncargo() throws SQLException {
        List<PedidoEncargo> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pedidoencargo";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPedidoEncargo(rs));
            }
        }
        return lista;
    }
    
    // Lista los pedidos realizados por un cliente especifico (por su ID)
    
    public List<PedidoEncargo> listarPorCliente(int idCliente) throws SQLException {
    List<PedidoEncargo> lista = new ArrayList<>();

    String sql = "SELECT p.idPedido, p.fechaPedido, p.fechaEnvio, " +
                 "p.idCliente, p.estado, p.valorTotal " +
                 "FROM pedidoencargo p " +
                 "WHERE p.idCliente = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        System.out.println("[Debug Cliente DAO] SQL: SELECT idPedido, fechaPedido, estado, valorTotal FROM pedidoencargo WHERE idCliente=" + idCliente);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoEncargo p = new PedidoEncargo();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setFechaPedido(rs.getDate("fechaPedido"));
                p.setFechaEnvio(rs.getDate("fechaEnvio"));
                p.setIdCliente(rs.getInt("idCliente"));
                p.setEstado(rs.getString("estado"));
                p.setValorTotal(rs.getBigDecimal("valorTotal"));
                lista.add(p);
            }
        }
    }
    return lista;
}

    
    // Lista los pedidos asignados a un vendedor especifico (por su ID)
    
    public List<PedidoEncargo> listarPorVendedor(int idVendedor) throws SQLException {
    List<PedidoEncargo> pedidos = new ArrayList<>();
    String sql = "SELECT p.idPedido, p.fechaPedido, p.fechaEnvio, p.idCliente, p.estado, p.valorTotal, p.idVendedor " +
                 "FROM pedidoencargo p " +
                 "WHERE p.idVendedor = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idVendedor);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoEncargo p = new PedidoEncargo();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setFechaPedido(rs.getDate("fechaPedido"));
                p.setFechaEnvio(rs.getDate("fechaEnvio"));
                p.setIdCliente(rs.getInt("idCliente"));   
                p.setValorTotal(rs.getBigDecimal("valorTotal"));
                p.setIdVendedor (rs.getInt("idVendedor"));
                p.setEstado(rs.getString("estado"));
                pedidos.add(p);
            }
        }
    }
    return pedidos;
}

    // Busca un pedido por su ID
    public PedidoEncargo buscarPorId(int idPedido) throws SQLException {
        String SQL = "SELECT * FROM pedidoencargo WHERE idPedido=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearPedidoEncargo(rs);
            }
        }
        return null;
    }

    // Busca los pedidos asociados a un cliente
    public List<PedidoEncargo> buscarPorCliente(int idCliente) throws SQLException {
        List<PedidoEncargo> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pedidoencargo WHERE idCliente=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPedidoEncargo(rs));
            }
        }
        return lista;
    }

    // Busca pedidos registrados dentro de un rango de fechas
    public List<PedidoEncargo> buscarPorRangoFechas(Date inicio, Date fin) throws SQLException {
        List<PedidoEncargo> lista = new ArrayList<>();
        String SQL = "SELECT * FROM pedidoencargo WHERE fechaPedido BETWEEN ? AND ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setDate(1, inicio);
            ps.setDate(2, fin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPedidoEncargo(rs));
            }
        }
        return lista;
    }

    // JOIN: lista los pedidos con información del cliente y pago
  public List<PedidoEncargo> listarPedidosConClienteYPago() throws SQLException {
    List<PedidoEncargo> lista = new ArrayList<>();
    String SQL = "SELECT p.idPedido, p.fechaPedido, p.fechaEnvio, p.idCliente, p.estado, p.valorTotal, p.idVendedor, " +
                 "c.nombre AS nombreCliente, pa.idPago, pa.monto, pa.metodo, pa.fechaPago " +
                 "FROM pedidoencargo p " +
                 "JOIN cliente c ON p.idCliente = c.idCliente " +
                 "LEFT JOIN pago pa ON p.idPedido = pa.idPedido";
    try (PreparedStatement ps = con.prepareStatement(SQL);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            PedidoEncargo pedido = mapearPedidoEncargo(rs);
            lista.add(pedido);
        }
    }
    return lista;
}


    /* --- Método auxiliar para mapear ResultSet a PedidoEncargo --- */
    private PedidoEncargo mapearPedidoEncargo(ResultSet rs) throws SQLException {
        PedidoEncargo p = new PedidoEncargo();
        p.setIdPedido(rs.getInt("idPedido"));
        p.setFechaPedido(rs.getDate("fechaPedido"));
        p.setFechaEnvio(rs.getDate("fechaEnvio"));
        p.setIdCliente(rs.getInt("idCliente"));
        p.setEstado(rs.getString("estado"));
        p.setValorTotal(rs.getBigDecimal("valorTotal"));
        p.setIdVendedor(rs.getInt("idVendedor"));
        return p;
    }
}