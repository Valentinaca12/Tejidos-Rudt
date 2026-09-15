/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.ProductoDAO;
import com.tejidosrudt.modelo.Producto;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author sarac
 */
public class ProductoServicio {
    private final ProductoDAO productoDAO;

    public ProductoServicio() {
        productoDAO = new ProductoDAO();
    }

    // Registrar producto
    public boolean registrarProducto(Producto producto) {
        try {
            validarCamposObligatorios(producto);
            productoDAO.crearProducto(producto);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar producto: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Actualizar producto
    public boolean actualizarProducto(Producto producto) {
        try {
            if (producto.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            validarCamposObligatorios(producto);
            productoDAO.actualizarProducto(producto);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar producto: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Eliminar producto
    public boolean eliminarProducto(Producto producto) {
        try {
            if (producto.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            productoDAO.eliminarProducto(producto);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar producto: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar productos
    public List<Producto> listarProductos() {
        try {
            return productoDAO.listarProductos();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Buscar producto por ID
    public Producto buscarProductoPorId(int idProducto) {
        try {
            if (idProducto <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            return productoDAO.buscarPorId(idProducto);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar producto por ID: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return null;
        }
    }

    // Buscar producto por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            return productoDAO.buscarPorNombre(nombre);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar producto por nombre: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return null;
        }
    }

    // Buscar producto por estilo o color
    public List<Producto> buscarPorEstiloOColor(String estilo, String color) {
        try {
            return productoDAO.buscarPorEstiloOColor(estilo, color);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar producto por estilo o color: " + e.getMessage());
            return null;
        }
    }

    // ---------------- Método auxiliar ----------------
    private void validarCamposObligatorios(Producto producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getEstilo() == null || producto.getEstilo().isEmpty()) {
            throw new IllegalArgumentException("El estilo es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (producto.getTalla() == null || producto.getTalla().isEmpty()) {
            throw new IllegalArgumentException("La talla es obligatoria");
        }
        if (producto.getColor() == null || producto.getColor().isEmpty()) {
            throw new IllegalArgumentException("El color es obligatorio");
        }
        if (producto.getCapellada() == null || producto.getCapellada().isEmpty()) {
            throw new IllegalArgumentException("La capellada es obligatoria");
        }
         if (producto.getDescripcion()== null || producto.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
    }
}
