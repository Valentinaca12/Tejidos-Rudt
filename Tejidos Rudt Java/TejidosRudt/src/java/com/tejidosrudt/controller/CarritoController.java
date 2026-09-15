/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tejidosrudt.modelo.Carrito;
import com.tejidosrudt.modelo.CarritoDetalle;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.Producto;
import com.tejidosrudt.servicios.CarritoServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author sarac
 */
@WebServlet(name = "CarritoController", urlPatterns = {"/carrito"})
public class CarritoController extends HttpServlet {

    private final CarritoServicio carritoServicio = new CarritoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueado(request) && !SessionUtils.estaLogueadoVendedor(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int idCliente = SessionUtils.obtenerIdCliente(request);
        try {
           List<CarritoDetalle> productos = carritoServicio.listarProductosCarrito(idCliente);
            request.setAttribute("productosCarrito", productos);
            request.getRequestDispatcher("/cliente/carrito.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al listar carrito", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueado(request) && !SessionUtils.estaLogueadoVendedor(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        int idCliente = SessionUtils.obtenerIdCliente(request);

         try {
            switch (accion) {
                case "agregar":
                    agregarProducto(request, idCliente);
                    break;
                case "actualizar":
                    actualizarCantidad(request, idCliente);
                    break;
                case "eliminar":
                    eliminarProducto(request, idCliente);
                    break;
                case "confirmar":
                    confirmarPedido(request, response);
                return;

            }
            response.sendRedirect("carrito");
        } catch (SQLException e) {
            throw new ServletException("Error al procesar carrito", e);
        }
    }

    //Agregar producto al carrito
    private void agregarProducto(HttpServletRequest request, int idCliente) throws SQLException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        carritoServicio.agregarProductoAlCarrito(idCliente, idProducto, cantidad);
    }

    //Modifica la cantidad de un producto especifico en el carrito
    private void actualizarCantidad(HttpServletRequest request, int idCliente) throws SQLException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        carritoServicio.actualizarCantidadProducto(idCliente, idProducto, cantidad);
    }

    //Elimina un producto del carrito del cliente
    private void eliminarProducto(HttpServletRequest request, int idCliente) throws SQLException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        carritoServicio.eliminarProductoDelCarrito(idCliente, idProducto);
    }

    //Procesa la orden de compra y confirma el pedido del cliente
    private void confirmarPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Cliente cliente= (Cliente) request.getSession().getAttribute("clienteLogueado");
        int idCliente = cliente.getIdCliente();
 
        carritoServicio.confirmarPedido(idCliente);
        response.sendRedirect(request.getContextPath()  + "/pedidoencargo?accion=listarCliente&idCliente=" + idCliente);
}

}

