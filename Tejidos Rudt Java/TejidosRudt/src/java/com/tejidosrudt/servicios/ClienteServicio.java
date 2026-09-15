/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.CarritoDAO;
import com.tejidosrudt.dao.ClienteDAO;
import com.tejidosrudt.dao.PedidoEncargoDAO;
import com.tejidosrudt.modelo.CarritoDetalle;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.Producto;
import com.tejidosrudt.modelo.PedidoEncargo;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

/**
 *
 * @author sarac
 */
public class ClienteServicio {
    private final ClienteDAO clienteDAO;
    private final CarritoDAO carritoDAO;
    private final PedidoEncargoDAO pedidoEncargoDAO;

    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
        this.carritoDAO = new CarritoDAO();
        this.pedidoEncargoDAO = new PedidoEncargoDAO();
    }

    // Registrar cliente (usado en registro.jsp)
    public boolean registrar(Cliente cliente) throws SQLException {
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (cliente.getCorreo() == null || !cliente.getCorreo().contains("@")) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        if (cliente.getEdad() <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor a 0");
        }
        if (cliente.getSexo() == null) {
            throw new IllegalArgumentException("El sexo es obligatorio");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().length() < 7) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }
        if (cliente.getContraseña() == null || cliente.getContraseña().length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
        }

        // Verificar si el correo ya existe
        Cliente existente = clienteDAO.buscarXCorreoCliente(cliente.getCorreo());
        if (existente != null) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        clienteDAO.crearCliente(cliente);
        return true;
    }

    // Login (usado en login.jsp)
    public Cliente login(String correo, String contraseña) throws SQLException {
        Cliente cliente = clienteDAO.buscarXCorreoCliente(correo);
        if (cliente != null && cliente.getContraseña().equals(contraseña)) {
            return cliente;
        }
        return null;
    }

    // Actualizar cliente
    public boolean actualizarCliente(Cliente cliente) {
        try {
            if (cliente.getIdCliente() <= 0) {
                throw new IllegalArgumentException("El ID de cliente no es válido");
            }
            clienteDAO.actualizarCliente(cliente);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar cliente: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminarCliente(Cliente cliente) {
        try {
            if (cliente.getIdCliente() <= 0) {
                throw new IllegalArgumentException("El ID de cliente no es válido");
            }
            clienteDAO.eliminarCliente(cliente);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar cliente: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar clientes
    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.listarCliente();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar clientes: " + e.getMessage());
            return null;
        }
    }

    // Listar productos del carrito
    public List<CarritoDetalle> listarCarrito(int idCliente) {
        try {
            return carritoDAO.listarProductosCarrito(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al listar carrito: " + e.getMessage());
            return null;
        }
    }

    // Listar pedidos
    public List<PedidoEncargo> listarPedidos(int idCliente) {
        try {
            return pedidoEncargoDAO.listarPorCliente(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pedidos: " + e.getMessage());
            return null;
        }
    }

    // Ver perfil
    public Cliente verPerfil(int idCliente) {
        try {
            return clienteDAO.buscarXIdCliente(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al ver perfil: " + e.getMessage());
            return null;
        }
    }

    // Buscar por ID
    public Cliente buscarClientePorId(int idCliente) {
        try {
            return clienteDAO.buscarXIdCliente(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar cliente por ID: " + e.getMessage());
            return null;
        }
    }

    // Buscar por correo
    public Cliente buscarClientePorCorreo(String correo) {
        try {
            return clienteDAO.buscarXCorreoCliente(correo);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar cliente por correo: " + e.getMessage());
            return null;
        }
    }
}
