/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import com.tejidosrudt.modelo.CarritoDetalle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.Cliente.Sexo;
import com.tejidosrudt.modelo.PedidoEncargo;
import com.tejidosrudt.modelo.Producto;
import com.tejidosrudt.servicios.ClienteServicio;
import com.tejidosrudt.servicios.PedidoEncargoServicio;
import com.tejidosrudt.servicios.CarritoServicio;
import com.tejidosrudt.servicios.ProductoServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteController", urlPatterns = {"/clientes"})
public class ClienteController extends HttpServlet {

    private final ClienteServicio clienteServicio = new ClienteServicio();
    private final PedidoEncargoServicio pedidoServicio = new PedidoEncargoServicio();
    private final CarritoServicio carritoServicio = new CarritoServicio();
    private final ProductoServicio productoServicio = new ProductoServicio();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        try {
            switch (accion != null ? accion : "") {
                case "carrito":
                    listarCarrito(request, response);
                    break;
                case "pedidos":
                    listarPedidos(request, response);
                    break;
                case "perfil":
                    verPerfil(request, response);
                    break;
                case "catalogo":
                    verCatalogo(request, response);
                    break;
                default:
                    listarCarrito(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar cliente", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            switch (accion != null ? accion : "") {
                case "crear":
                    crearCliente(request, response);
                    break;
                case "actualizar":
                    actualizarPerfil(request, response);
                    break;
                default:
                    response.sendRedirect("clientes?accion=perfil");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar cliente", e);
        }
    }

    // ---------------- Métodos del cliente ----------------

    private void crearCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Cliente c = new Cliente();
        c.setNombre(request.getParameter("nombre"));
        c.setCorreo(request.getParameter("correo"));
        c.setTelefono(request.getParameter("telefono"));
        c.setEdad(Integer.parseInt(request.getParameter("edad")));
        c.setContraseña(request.getParameter("contraseña"));
        c.setSexo(Cliente.Sexo.valueOf(request.getParameter("sexo").toUpperCase()));

        clienteServicio.registrar(c);
        response.sendRedirect("index.jsp"); // tras registro, ir al login o perfil
    }

    private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idCliente = SessionUtils.obtenerIdCliente(request); // seguridad: usa sesión
        Cliente c = new Cliente();
        c.setIdCliente(idCliente);
        c.setNombre(request.getParameter("nombre"));
        c.setCorreo(request.getParameter("correo"));
        c.setTelefono(request.getParameter("telefono"));
        c.setEdad(Integer.parseInt(request.getParameter("edad")));
        c.setContraseña(request.getParameter("contraseña"));
        c.setSexo(Cliente.Sexo.valueOf(request.getParameter("sexo").toUpperCase()));

        clienteServicio.actualizarCliente(c);
        response.sendRedirect("/cliente/perfil.jsp");
    }

    private void listarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idCliente = SessionUtils.obtenerIdCliente(request);
        List<CarritoDetalle> carrito = clienteServicio.listarCarrito(idCliente);
        request.setAttribute("carrito", carrito);
        request.getRequestDispatcher("/cliente/carrito.jsp").forward(request, response);
    }

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idCliente = SessionUtils.obtenerIdCliente(request);
        List<PedidoEncargo> pedidos = pedidoServicio.listarPedidosPorCliente(idCliente);
        request.setAttribute("pedidos", pedidos);
        request.getRequestDispatcher("/cliente/pedidos.jsp").forward(request, response);
    }

    private void verPerfil(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idCliente = SessionUtils.obtenerIdCliente(request);
        Cliente perfil = clienteServicio.buscarClientePorId(idCliente);
        request.setAttribute("perfil", perfil);
        request.getRequestDispatcher("/cliente/perfil.jsp").forward(request, response);
    }

    private void verCatalogo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Producto> productos = productoServicio.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/cliente/catalogo.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de clientes en Tejidos Rudt";
    }
}


