/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.DetallePedidoDAO;
import com.tejidosrudt.modelo.DetallePedido;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class DetallePedidoServicio {
    private DetallePedidoDAO detallePedidoDAO;

    public DetallePedidoServicio() {
        detallePedidoDAO = new DetallePedidoDAO();
    }

    // Registrar detalle de pedido
    public boolean registrarDetallePedido(DetallePedido detalle) {
        try {
            if (detalle.getIdPedido() <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            if (detalle.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }
            if (detalle.getSubtotal() < 0) {
                throw new IllegalArgumentException("El subtotal no puede ser negativo");
            }

            detallePedidoDAO.crearDetallePedido(detalle);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar detalle de pedido: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar detalles de pedido
    public List<DetallePedido> listarDetallesPedido() {
        try {
            return detallePedidoDAO.listarDetallePedido();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar detalles de pedido: " + e.getMessage());
            return null;
        }
    }
    
    // Listar detalles por ID de pedido
    public List<DetallePedido> listarPorPedido(int idPedido) throws SQLException {
        return detallePedidoDAO.listarPorPedido(idPedido);
    }

    // Buscar detalle por ID
    public DetallePedido buscarDetallePedidoPorId(int idDetalle) {
        try {
            DetallePedido detalle = new DetallePedido();
            detalle.setIdDetalle(idDetalle);
            return detallePedidoDAO.buscarXIdDetallePedido(detalle);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar detalle de pedido por ID: " + e.getMessage());
            return null;
        }
    }
}
