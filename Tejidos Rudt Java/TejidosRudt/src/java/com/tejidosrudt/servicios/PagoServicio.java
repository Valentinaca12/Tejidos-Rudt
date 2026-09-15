/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.PagoDAO;
import com.tejidosrudt.dao.PedidoEncargoDAO;
import com.tejidosrudt.modelo.Pago;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author sarac
 */
public class PagoServicio {
    
    private PagoDAO pagoDAO;

    public PagoServicio() {
        pagoDAO = new PagoDAO();
    }
    private PedidoEncargoDAO pedidoEncargoDAO = new PedidoEncargoDAO();

    // Registrar pago
    public boolean registrarPago(Pago pago) {
        try {
            if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto debe ser mayor a 0");
            }
            if (pago.getMetodo() == null) {
                throw new IllegalArgumentException("El método de pago es obligatorio");
            }

            pagoDAO.crearPago(pago);
            pedidoEncargoDAO.actualizarEstadoPedido(pago.getIdPedido(), "Pagado");
            
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar pago: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Actualizar pago
    public boolean actualizarPago(Pago pago) {
        try {
            if (pago.getIdPago() <= 0) {
                throw new IllegalArgumentException("El ID del pago no es válido");
            }
            registrarPago(pago); 
            pagoDAO.actualizarPago(pago);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar pago: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Eliminar pago
    public boolean eliminarPago(Pago pago) {
        try {
            if (pago.getIdPago() <= 0) {
                throw new IllegalArgumentException("El ID del pago no es válido");
            }
            pagoDAO.eliminarPago(pago);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar pago: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar pagos
    public List<Pago> listarPagos() {
        try {
            return pagoDAO.listarPagos();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pagos: " + e.getMessage());
            return null;
        }
    }
    // Listar pagos con pedidos
    public List<Pago> listarPagosConPedidos() throws SQLException {
        return pagoDAO.listarPagosConPedidos();
    }

    // Buscar pago por ID
    public Pago buscarPagoPorId(int idPago) {
        try {
            Pago pago = new Pago();
            pago.setIdPago(idPago);
            return pagoDAO.buscarPorId(idPago);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar pago por ID: " + e.getMessage());
            return null;
        }
    }
}
