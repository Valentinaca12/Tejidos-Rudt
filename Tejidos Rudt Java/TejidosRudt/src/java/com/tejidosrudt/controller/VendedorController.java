/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import com.tejidosrudt.modelo.Producto;
import com.tejidosrudt.modelo.PedidoEncargo;
import com.tejidosrudt.servicios.ProductoServicio;
import com.tejidosrudt.servicios.PedidoEncargoServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author sarac
 */

@WebServlet(name = "VendedorController", urlPatterns = {"/vendedores"})
public class VendedorController extends HttpServlet {

    private final ProductoServicio productoServicio = new ProductoServicio();
    private final PedidoEncargoServicio pedidoServicio = new PedidoEncargoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueadoVendedor(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        try {
            switch (accion != null ? accion : "") {
                case "catalogo":
                    listarProductos(request, response);
                    break;
                case "pedidos":
                    listarPedidos(request, response);
                    break;
                case "buscarProducto":
                    buscarProducto(request, response);
                    break;
                case "editarProducto":
                    editarProducto(request, response);
                    break;
                default:
                    listarPedidos(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error en VendedorController", e);
        }
    }

    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueadoVendedor(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        Object vendedor = request.getSession().getAttribute("vendedor");
        System.out.println("DEBUG - Vendedor en sesión: " + vendedor);
        if (vendedor == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        
        String accion = request.getParameter("accion");
        try {
            switch (accion != null ? accion : "") {
                case "crearProducto":
                    crearProducto(request, response);
                    break;
                case "actualizarProducto":
                    actualizarProducto(request, response);
                    break;
                case "eliminarProducto":
                    eliminarProducto(request, response);
                    break;
                case "actualizarEstadoPedido":
                    actualizarEstadoPedido(request, response);
                    break;
                default:
                    response.sendRedirect("vendedores?accion=catalogo");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error en VendedorController", e);
        }
    }

    // ---------------- Métodos de productos ----------------

     //Obtiene la lista completa de productos para mostrar en el catálogo del vendedor
    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Producto> productos = productoServicio.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/vendedor/catalogo.jsp").forward(request, response);
    }

    //Registra un nuevo producto en el catalogo
    private void crearProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Producto p = new Producto();
        p.setNombreProducto(request.getParameter("nombreProducto"));
        p.setEstilo(request.getParameter("estilo"));
        p.setPrecio(new BigDecimal(request.getParameter("precio")));
        p.setTalla(request.getParameter("talla"));
        p.setColor(request.getParameter("color"));
        p.setCapellada(request.getParameter("capellada"));

        productoServicio.registrarProducto(p);
        response.sendRedirect("vendedores?accion=catalogo");
    }

    //Recupera los datos de un producto por su ID para cargarlos en el formulario de edición
    private void editarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = productoServicio.buscarProductoPorId(idProducto);
        request.setAttribute("productoEditar", producto);
        request.getRequestDispatcher("/vendedor/editarProducto.jsp").forward(request, response);
    }

    //Modifica los datos existentes de un producto del catálogo
    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Producto p = new Producto();
        p.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
        p.setNombreProducto(request.getParameter("nombreProducto"));
        p.setEstilo(request.getParameter("estilo"));
        p.setPrecio(new BigDecimal(request.getParameter("precio")));
        p.setTalla(request.getParameter("talla"));
        p.setColor(request.getParameter("color"));
        p.setCapellada(request.getParameter("capellada"));

        productoServicio.actualizarProducto(p);
        response.sendRedirect("vendedores?accion=catalogo");
    }

    //Remueve un producto del catálogo de la BD por su ID
   private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto p = new Producto();
        p.setIdProducto(idProducto);
        productoServicio.eliminarProducto(p);
        response.sendRedirect("vendedores?accion=catalogo");
    }

    //Filtra y busca productos en el catálogo según el nombre proporcionado
    private void buscarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String nombre = request.getParameter("nombreProducto");
        List<Producto> productos = productoServicio.buscarPorNombre(nombre);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/vendedor/catalogo.jsp").forward(request, response);
    }

    // ---------------- Métodos de pedidos ----------------

    //Obtiene la lista de todos los pedidos encargados asignados al vendedor
    private void listarPedidos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idVendedor = SessionUtils.obtenerIdVendedor(request);
        List<PedidoEncargo> pedidos = pedidoServicio.listarPedidosEncargo();
        request.setAttribute("pedidos", pedidos);
        request.getRequestDispatcher("/vendedor/pedidos.jsp").forward(request, response);
    }

    //Modifica el estado actual de un pedido especifico y actualiza la vista
    private void actualizarEstadoPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idPedido = Integer.parseInt(request.getParameter("idPedido"));
        String nuevoEstado = request.getParameter("estado");
        pedidoServicio.actualizarEstadoPedido(idPedido, nuevoEstado);
        response.sendRedirect("vendedores?accion=pedidos");
    }

    @Override
    public String getServletInfo() {
        return "Controlador de vendedores en Tejidos Rudt";
    }
}
