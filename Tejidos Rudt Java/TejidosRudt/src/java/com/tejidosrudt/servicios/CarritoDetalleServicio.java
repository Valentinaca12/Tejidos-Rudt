/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.CarritoDetalleDAO;
import com.tejidosrudt.modelo.CarritoDetalle;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class CarritoDetalleServicio {
    private CarritoDetalleDAO carritoDetalleDAO;

    public CarritoDetalleServicio() {
        carritoDetalleDAO = new CarritoDetalleDAO();
    }

    // Registra detalle en carrito
    public boolean registrarCarritoDetalle(CarritoDetalle detalle) {
        try {
            if (detalle.getIdCarrito() <= 0) {
                throw new IllegalArgumentException("El ID del carrito no es válido");
            }
            if (detalle.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            carritoDetalleDAO.crearCarritoDetalle(detalle);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar detalle de carrito: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Actualiza detalle en carrito
    public boolean actualizarCarritoDetalle(CarritoDetalle detalle) {
        try {
            if (detalle.getIdCarrito() <= 0) {
                throw new IllegalArgumentException("El ID del carrito no es válido");
            }
            if (detalle.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            carritoDetalleDAO.actualizarCarritoDetalle(detalle);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar detalle de carrito: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    //Elimina detalle de carrito
    public boolean eliminarCarritoDetalle(CarritoDetalle detalle) {
        try {
            if (detalle.getIdCarrito() <= 0) {
                throw new IllegalArgumentException("El ID del carrito no es válido");
            }
            carritoDetalleDAO.eliminarCarritoDetalle(detalle);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar detalle de carrito: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    //lista detalles del carrito
    public List<CarritoDetalle> listarCarritoDetalles() {
        try {
            return carritoDetalleDAO.listarCarritoDetalle();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar detalles de carrito: " + e.getMessage());
            return null;
        }
    }

    // Busca y obtiene los detalles.  (----> Revisar método)
    public CarritoDetalle buscarCarritoDetallePorIdCarrito(int idCarrito) {
        try {
            CarritoDetalle detalle = new CarritoDetalle();
            detalle.setIdCarrito(idCarrito);
            return carritoDetalleDAO.buscarXIdCarritoDetalle(detalle);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar detalle de carrito: " + e.getMessage());
            return null;
        }
    }
}
