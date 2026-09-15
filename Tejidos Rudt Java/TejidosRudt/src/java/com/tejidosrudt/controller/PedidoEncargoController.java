/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import com.tejidosrudt.dao.DetallePedidoDAO;
import com.tejidosrudt.dao.PedidoEncargoDAO;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.DetallePedido;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import com.tejidosrudt.modelo.PedidoEncargo;
import com.tejidosrudt.servicios.DetallePedidoServicio;
import com.tejidosrudt.servicios.PedidoEncargoServicio;
import com.tejidosrudt.servicios.PedidoVendedorServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author sarac
 */
@WebServlet(name = "PedidoEncargoController", urlPatterns = {"/pedidoencargo"})
public class PedidoEncargoController extends HttpServlet {

    private final PedidoEncargoServicio pedidoEncargoServicio = new PedidoEncargoServicio();
    private final PedidoVendedorServicio pedidoVendedorServicio = new PedidoVendedorServicio();
    private final DetallePedidoServicio detallePedidoServicio = new DetallePedidoServicio();
    private final DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
    private final PedidoEncargoDAO pedidoEncargoDAO = new PedidoEncargoDAO();




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.estaLogueado(request) && !SessionUtils.estaLogueadoVendedor(request)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }


        String accion = request.getParameter("accion");
        try {
            switch (accion != null ? accion : "") {
                case "listarCliente":
                    listarPedidosCliente(request, response);
                    break;
                case "listarVendedor":
                    listarPedidosVendedor(request, response);
                    break;
             /*   case "verDetalle":           
                    verDetallePedido(request, response);
                    break;*/
                case "verDetalleCliente":
                    verDetalleCliente(request, response);
                    break;

                case "verDetalleVendedor":
                    verDetalleVendedor(request, response);
                    break;

                case "buscar":
                    buscarPedido(request, response);
                    break;
                case "editar":
                    editarPedido(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar pedidos encargo", e);
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
        try {
            switch (accion != null ? accion : "") {
                case "crear":
                    crearPedido(request, response);
                    break;
                case "actualizar":
                    actualizarPedido(request, response);
                    break;
                case "actualizarEstado":
                    actualizarEstado(request, response);
                    break;

                case "eliminar":
                    eliminarPedido(request, response);
                    break;
                default:
                    response.sendRedirect("pedidos?accion=listar");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar pedidos encargo", e);
        }
    }

    //Obtiene la lista de pedidos encargados por el cliente
    private void listarPedidosCliente(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogueado");
    
    int idCliente = cliente.getIdCliente();

    List<PedidoEncargo> lista = pedidoEncargoDAO.listarPorCliente(idCliente);
    request.setAttribute("pedidos", lista);
    request.getRequestDispatcher("/cliente/pedidos.jsp").forward(request, response);
    }
    
   // Obtiene la lista de pedidos asignados a un vendedor especifico
    private void listarPedidosVendedor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idVendedorParam = request.getParameter("idVendedor");
        
        // Validación del parámetro
        if (idVendedorParam == null || idVendedorParam.isEmpty()) {
            request.setAttribute("mensajeError", "El parámetro idVendedor no fue proporcionado.");
            request.getRequestDispatcher("/vendedor/pedidoencargo.jsp").forward(request, response);
            return; 
        }

        int idVendedor = Integer.parseInt(idVendedorParam);
        
        List<PedidoEncargo> lista = pedidoEncargoServicio.listarPedidosPorVendedor(idVendedor);
        request.setAttribute("pedidos", lista);
        request.getRequestDispatcher("/vendedor/pedidoencargo.jsp").forward(request, response);
    }

    //Busca un pedido especifico por su ID para mostrar sus detalles
    private void buscarPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPedido"));
        PedidoEncargo pedido = pedidoEncargoServicio.buscarPedidoEncargoPorId(id);
        request.setAttribute("pedidoEncontrado", pedido);
        request.getRequestDispatcher("/pedidos/buscar.jsp").forward(request, response);
    }

    //Recupera los datos de un pedido para cargarlos en el formulario de edición
    private void editarPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPedido"));
        PedidoEncargo pedidoEditar = pedidoEncargoServicio.buscarPedidoEncargoPorId(id);
        request.setAttribute("pedidoEditar", pedidoEditar);
        request.getRequestDispatcher("/pedidos/editar.jsp").forward(request, response);
    }

    //Registra un nuevo pedido en el sitema y redirige a la lista
    private void crearPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        PedidoEncargo p = new PedidoEncargo();
        p.setFechaPedido(Date.valueOf(request.getParameter("fechaPedido")));
        p.setFechaEnvio(request.getParameter("fechaEnvio") != null && !request.getParameter("fechaEnvio").isEmpty()
                ? Date.valueOf(request.getParameter("fechaEnvio"))
                : null);
        p.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        p.setEstado(request.getParameter("estado"));
        p.setValorTotal(new java.math.BigDecimal(request.getParameter("valorTotal")));
        p.setIdVendedor(Integer.parseInt(request.getParameter("idVendedor")));
        
        pedidoEncargoServicio.registrarPedidoEncargo(p);
        response.sendRedirect("pedidos?accion=listar");
    }

    //Modifica los datos generales de un pedido existente y redirige a la lista
    private void actualizarPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        PedidoEncargo p = new PedidoEncargo();
        p.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
        p.setFechaPedido(Date.valueOf(request.getParameter("fechaPedido")));
        p.setFechaEnvio(request.getParameter("fechaEnvio") != null && !request.getParameter("fechaEnvio").isEmpty()
                ? Date.valueOf(request.getParameter("fechaEnvio"))
                : null);
        p.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));   
        p.setEstado(request.getParameter("estado"));
        p.setValorTotal(new java.math.BigDecimal(request.getParameter("valorTotal")));
         p.setIdVendedor(Integer.parseInt(request.getParameter("idVendedor")));

        pedidoEncargoServicio.actualizarPedidoEncargo(p);
        response.sendRedirect("pedidos?accion=listar");
    }
    
    // Modifica unicamente el estado del pedido desde la vista del vendedor
    private void actualizarEstado(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
    int idPedido = Integer.parseInt(request.getParameter("idPedido"));
    String nuevoEstado = request.getParameter("estado");

    boolean actualizado = pedidoEncargoServicio.actualizarEstadoPedido(idPedido, nuevoEstado);

    if (actualizado) {
        //  Redirige nuevamente al detalle del vendedor
        response.sendRedirect("pedidoencargo?accion=verDetalleVendedor&idPedido=" + idPedido);
    } else {
        request.setAttribute("error", "No se pudo actualizar el estado del pedido.");
        request.getRequestDispatcher("vendedor/detalleEncargo.jsp").forward(request, response);
    }
}

    //Remueve un registro de pedido del sistema y redirige a la lista
    private void eliminarPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("idPedido"));
        PedidoEncargo p = new PedidoEncargo();
        p.setIdPedido(id);
        

        pedidoEncargoServicio.eliminarPedidoEncargo(p);
        response.sendRedirect("pedidos?accion=listar");
    }
    

    // Muestra el detalle y los productos de un pedido a un cliente
    private void verDetalleCliente(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    int idPedido = Integer.parseInt(request.getParameter("idPedido"));

    PedidoEncargo pedido = pedidoEncargoServicio.buscarPedidoEncargoPorId(idPedido);

    List<DetallePedido> detalles = detallePedidoDAO.listarPorPedido(idPedido);
    request.setAttribute("pedido", pedido);
    request.setAttribute("detalles", detalles);

    // Redirigir al JSP del cliente
    request.getRequestDispatcher("cliente/detallepedido.jsp").forward(request, response);
}
    
    // Muestra el detalle y los productos de un pedido a un vendedor
    private void verDetalleVendedor(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    int idPedido = Integer.parseInt(request.getParameter("idPedido"));

    PedidoEncargo pedido = pedidoEncargoServicio.buscarPedidoEncargoPorId(idPedido);
    List<DetallePedido> detalles = detallePedidoDAO.listarPorPedido(idPedido);
    request.setAttribute("pedido", pedido);
    request.setAttribute("detalles", detalles);

    // Redirigir al JSP del vendedor
    request.getRequestDispatcher("vendedor/detalleEncargo.jsp").forward(request, response);
}



    @Override
    public String getServletInfo() {
        return "Controlador de pedidos encargo en Tejidos Rudt";
    }
}
