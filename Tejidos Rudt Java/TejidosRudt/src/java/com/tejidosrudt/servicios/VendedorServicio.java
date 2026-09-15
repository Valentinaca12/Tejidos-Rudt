/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.PedidoEncargoDAO;
import com.tejidosrudt.dao.VendedorDAO;
import com.tejidosrudt.modelo.PedidoEncargo;
import com.tejidosrudt.modelo.Vendedor;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author sarac
 */
public class VendedorServicio {
    private VendedorDAO vendedorDAO;
    
     private final PedidoEncargoDAO pedidoEncargoDAO = new PedidoEncargoDAO();

    public VendedorServicio() {
        vendedorDAO = new VendedorDAO();
    }

    // Registrar vendedor
    public boolean registrarVendedor(Vendedor vendedor) {
        try {
            if (vendedor.getNombre() == null || vendedor.getNombre().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            if (vendedor.getContraseña() == null || vendedor.getContraseña().length() < 6) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
            }

            vendedorDAO.crearVendedor(vendedor);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar vendedor: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }
    
   // Login de Vendedor
   public Vendedor login(String nombre, String contrasena) throws SQLException {
        Vendedor vendedor = vendedorDAO.buscarXNombreVendedor(nombre);
        if (vendedor != null && vendedor.getContraseña().equals(contrasena)) {
            return vendedor;
        }
        return null;
}



    // Actualizar vendedor
    public boolean actualizarVendedor(Vendedor vendedor) {
        try {
            if (vendedor.getIdVendedor() <= 0) {
                throw new IllegalArgumentException("El ID del vendedor no es válido");
            }
            if (vendedor.getNombre() == null || vendedor.getNombre().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            if (vendedor.getContraseña() == null || vendedor.getContraseña().length() < 6) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
            }

            vendedorDAO.actualizarVendedor(vendedor);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar vendedor: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Eliminar vendedor
    public boolean eliminarVendedor(Vendedor vendedor) {
        try {
            if (vendedor.getIdVendedor() <= 0) {
                throw new IllegalArgumentException("El ID del vendedor no es válido");
            }
            vendedorDAO.eliminarVendedor(vendedor);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar vendedor: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar vendedores
    public List<Vendedor> listarVendedores() {
        try {
            return vendedorDAO.listarVendedores();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar vendedores: " + e.getMessage());
            return null;
        }
    }
    
    // Listar todos los pedidos
    public List<PedidoEncargo> listarTodosLosPedidos() {
        try {
            return pedidoEncargoDAO.listarPedidoEncargo();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar todos los pedidos: " + e.getMessage());
            return null;
        }
}
    

    // Buscar vendedor por ID
    public Vendedor buscarVendedorPorId(int idVendedor) {
        try {
            Vendedor vendedor = new Vendedor();
            vendedor.setIdVendedor(idVendedor);
            return vendedorDAO.buscarPorId(idVendedor);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar vendedor por ID: " + e.getMessage());
            return null;
        }
    }
}
