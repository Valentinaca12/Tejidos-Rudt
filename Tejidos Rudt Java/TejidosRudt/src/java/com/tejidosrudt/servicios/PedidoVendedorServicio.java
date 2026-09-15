/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.PedidoVendedorDAO;
import com.tejidosrudt.modelo.PedidoVendedor;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class PedidoVendedorServicio {
    private PedidoVendedorDAO pedidoVendedorDAO;

    public PedidoVendedorServicio() {
        pedidoVendedorDAO = new PedidoVendedorDAO();
    }

    // Asignar vendedor a pedido
    public boolean asignarVendedorAPedido(PedidoVendedor pedVen) {
        try {
            if (pedVen.getIdPedido() <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            if (pedVen.getIdVendedor() <= 0) {
                throw new IllegalArgumentException("El ID del vendedor no es válido");
            }

            pedidoVendedorDAO.asignarVendedor(pedVen.getIdPedido(), pedVen.getIdVendedor());
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al asignar vendedor a pedido: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar pedidos atendidos por un vendedor
    public List<Integer> listarPedidosPorVendedor(int idVendedor) {
        try {
            if (idVendedor <= 0) {
                throw new IllegalArgumentException("El ID del vendedor no es válido");
            }
            return pedidoVendedorDAO.listarPedVende(idVendedor);
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pedidos por vendedor: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return null;
        }
    }

    // Obtener vendedor asignado a un pedido
    public int obtenerVendedorPorPedido(int idPedido) {
        try {
            if (idPedido <= 0) {
                throw new IllegalArgumentException("El ID del pedido no es válido");
            }
            return pedidoVendedorDAO.obtenerVendedorPorPedido(idPedido);
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener vendedor por pedido: " + e.getMessage());
            return -1;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return -1;
        }
    }

    // Buscar relación Pedido–Vendedor por ID de pedido
    public PedidoVendedor buscarPedidoVendedorPorIdPedido(int idPedido) {
        try {
            PedidoVendedor pedVen = new PedidoVendedor();
            pedVen.setIdPedido(idPedido);
            return pedidoVendedorDAO.buscarXIdPedVen(pedVen);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar relación Pedido–Vendedor: " + e.getMessage());
            return null;
        }
    }
}
