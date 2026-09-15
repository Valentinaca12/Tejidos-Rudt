/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.PedidoEncargoDAO;
import com.tejidosrudt.modelo.PedidoEncargo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class PedidoEncargoServicio {
    private PedidoEncargoDAO pedidoEncargoDAO;

    public PedidoEncargoServicio() {
        pedidoEncargoDAO = new PedidoEncargoDAO();
    }

    // Registrar pedido encargo
    public boolean registrarPedidoEncargo(PedidoEncargo pedido) {
        try {
            if (pedido.getFechaPedido() == null) {
                throw new IllegalArgumentException("La fecha del pedido es obligatoria");
            }
            if (pedido.getFechaEnvio() != null && pedido.getFechaEnvio().before(pedido.getFechaPedido())) {
                throw new IllegalArgumentException("La fecha de envío no puede ser anterior a la fecha del pedido");
            }
            if (pedido.getIdCliente() <= 0) {
                throw new IllegalArgumentException("El ID del cliente no es válido");
            }
            if (pedido.getEstado() == null || pedido.getEstado().isEmpty()){
                throw new IllegalArgumentException("El estado del pedido es obliglatorio");
            }
            if (pedido.getValorTotal() == null || pedido.getValorTotal().compareTo(java.math.BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("El valor total del pedido no es válido o no puede ser negativo");
            }
            if (pedido.getIdVendedor() <= 0) {
            throw new IllegalArgumentException("El ID del vendedor no es válido");
        }

            pedidoEncargoDAO.crearPedidoEncargo(pedido);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar pedido encargo: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Actualizar pedido encargo
    public boolean actualizarPedidoEncargo(PedidoEncargo pedido) {
        try {
            if (pedido.getIdPedido() <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            registrarPedidoEncargo(pedido); 
            pedidoEncargoDAO.actualizarPedidoEncargo(pedido);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar pedido encargo: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }
    
    //Actualizar estado del pedido
   public boolean actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        try {
            if (idPedido <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            if (nuevoEstado == null || nuevoEstado.isEmpty()) {
                throw new IllegalArgumentException("El estado no puede estar vacío");
            }
            pedidoEncargoDAO.actualizarEstadoPedido(idPedido, nuevoEstado);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar estado del pedido: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Eliminar pedido encargo
    public boolean eliminarPedidoEncargo(PedidoEncargo pedido) {
        try {
            if (pedido.getIdPedido() <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            pedidoEncargoDAO.eliminarPedidoEncargo(pedido);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar pedido encargo: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar pedidos encargo
    public List<PedidoEncargo> listarPedidosEncargo() {
        try {
            return pedidoEncargoDAO.listarPedidoEncargo();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pedidos encargo: " + e.getMessage());
            return null;
        }
    }
    
     // Listar pedidos por cliente
    public List<PedidoEncargo> listarPedidosPorCliente(int idCliente) {
        try {
            return pedidoEncargoDAO.listarPorCliente(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pedidos por cliente: " + e.getMessage());
            return null;
        }
    }
    
    //Listar pedidos por vendedor
     public List<PedidoEncargo> listarPedidosPorVendedor(int idVendedor) throws SQLException {
        return pedidoEncargoDAO.listarPorVendedor(idVendedor);
    }

    // Buscar pedido encargo por ID
    public PedidoEncargo buscarPedidoEncargoPorId(int idPedido) {
        try {
            PedidoEncargo pedido = new PedidoEncargo();
            pedido.setIdPedido(idPedido);
            return pedidoEncargoDAO.buscarPorId(idPedido);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar pedido encargo por ID: " + e.getMessage());
            return null;
        }
    }
}
