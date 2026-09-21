/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.CarritoDAO;
import com.tejidosrudt.modelo.Carrito;
import com.tejidosrudt.modelo.CarritoDetalle;
import com.tejidosrudt.modelo.Producto;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class CarritoServicio {
    private CarritoDAO carritoDAO;

    public CarritoServicio() {
        carritoDAO = new CarritoDAO();
    }

    // Listar productos del carrito por cliente
   public List<CarritoDetalle> listarProductosCarrito(int idCliente) {
        try {
            return carritoDAO.listarProductosCarrito(idCliente);
        } catch (SQLException e) {
            System.err.println("Error SQL al listar productos del carrito: " + e.getMessage());
            return null;
        }
    }


    // Agregar producto al carrito
   public void agregarProductoAlCarrito(int idCliente, int idProducto, int cantidad) throws SQLException {

    //  Validación de datos
    if (idCliente <= 0 || idProducto <= 0 || cantidad <= 0) {
        throw new IllegalArgumentException("Datos inválidos para agregar producto al carrito");
    }
    if (carritoDAO.productoExisteEnCarrito(
        idCliente,
        idProducto)) {

    throw new IllegalArgumentException(
        "El producto ya está en el carrito");
    }
    carritoDAO.agregarProducto(idCliente, idProducto, cantidad);
    recalcularValorTotal(idCliente);
}


    //  Actualizar cantidad de producto en el carrito
    public void actualizarCantidadProducto(int idCliente, int idProducto, int cantidad) throws SQLException {
        if (idCliente <= 0 || idProducto <= 0 || cantidad <= 0) {
            throw new IllegalArgumentException("Datos inválidos para actualizar cantidad");
        }
        carritoDAO.actualizarCantidad(idCliente, idProducto, cantidad);
        recalcularValorTotal(idCliente);
    }

    //Eliminar producto del carrito
    public void eliminarProductoDelCarrito(int idCliente, int idProducto) throws SQLException {
        if (idCliente <= 0 || idProducto <= 0) {
            throw new IllegalArgumentException("Datos inválidos para eliminar producto del carrito");
        }
        carritoDAO.eliminarProducto(idCliente, idProducto);
        recalcularValorTotal(idCliente);
    }

    // Confirmar pedido (convertir carrito en pedido)
    public void confirmarPedido(int idCliente) throws SQLException {
        if (idCliente <= 0){
            throw new IllegalArgumentException("El ID del cliente no es válido");
        }
        carritoDAO.confirmarPedido(idCliente);
    }
    
    // Recalcular el valor total del carrito
    private void recalcularValorTotal(int idCliente) throws SQLException {
        List<CarritoDetalle> detalles = carritoDAO.listarProductosCarrito(idCliente);
        int nuevoTotal = 0;

        for (CarritoDetalle detalle : detalles) {
            nuevoTotal += detalle.getProducto().getPrecio().intValue() * detalle.getCantidad();
        }

        Carrito carrito = new Carrito();
        carrito.setIdCarrito(carritoDAO.obtenerCarritoPorCliente(idCliente));
        carrito.setValorTotal(nuevoTotal);
        carritoDAO.actualizarCarrito(carrito);
    }
}
