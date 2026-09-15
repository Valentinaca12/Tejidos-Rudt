/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import com.tejidosrudt.modelo.Pago;
import com.tejidosrudt.servicios.PagoServicio;
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
@WebServlet(name = "PagoController", urlPatterns = {"/pagos"})
public class PagoController extends HttpServlet {

    private final PagoServicio pagoServicio = new PagoServicio();

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
                case "listar":
                    listarPagos(request, response);
                    break;
                case "listarConPedidos":
                    listarPagosConPedidos(request, response);
                    break;
                case "buscar":
                    buscarPago(request, response);
                    break;
                case "editar":
                    editarPago(request, response);
                    break;
                default:
                    listarPagos(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar pagos", e);
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
                    crearPago(request, response);
                    break;
                case "actualizar":
                    actualizarPago(request, response);
                    break;
                case "eliminar":
                    eliminarPago(request, response);
                    break;
                default:
                    response.sendRedirect("pagos?accion=listar");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar pagos", e);
        }
    }

    //Obtiene la lista general de pagos y la envia a la vista del vendedor
    private void listarPagos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Pago> lista = pagoServicio.listarPagos();
        request.setAttribute("pagos", lista);
        request.getRequestDispatcher("/vendedor/pagos.jsp").forward(request, response);
    }

    //Obtiene los pagos relacionados con los pedidos
    private void listarPagosConPedidos(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    List<Pago> lista = pagoServicio.listarPagosConPedidos();
    request.setAttribute("pagos", lista);
    request.getRequestDispatcher("/vendedor/pagos.jsp").forward(request, response);
    }

    //Busca un pago especifico por su ID
    private void buscarPago(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPago"));
        Pago pago = pagoServicio.buscarPagoPorId(id);
        request.setAttribute("pagoEncontrado", pago);
        request.getRequestDispatcher("/pagos/buscar.jsp").forward(request, response);
    }

    //Recupera los datos de un pago por su ID y los carga en el formulario de edición 
    private void editarPago(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idPago"));
        Pago pagoEditar = pagoServicio.buscarPagoPorId(id);
        request.setAttribute("pagoEditar", pagoEditar);
        request.getRequestDispatcher("/pagos/editar.jsp").forward(request, response);
    }

    //Registra un nuevo pago de la BD y redirige a la ista general
    private void crearPago(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Pago p = new Pago();
        p.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
        p.setMonto(new BigDecimal(request.getParameter("monto")));
        p.setMetodo(Pago.MetodoPago.valueOf(request.getParameter("metodo")));

       
        pagoServicio.registrarPago(p);
        response.sendRedirect(request.getContextPath()+ "/cliente/confirmacionPago.jsp?idPedido="+ p.getIdPedido());
    }

    //Modifica os datos existentes de un pago y redirige a la lista general
    private void actualizarPago(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Pago p = new Pago();
        p.setIdPago(Integer.parseInt(request.getParameter("idPago")));
        p.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
        p.setMonto(new BigDecimal(request.getParameter("monto")));
        p.setMetodo(Pago.MetodoPago.valueOf(request.getParameter("metodo")));

        pagoServicio.actualizarPago(p);
        response.sendRedirect("pagos?accion=listar");
    }

    //Remueve un registro de pago de la BD y redirige a la lista general
    private void eliminarPago(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("idPago"));
        Pago p = new Pago();
        p.setIdPago(id);

        pagoServicio.eliminarPago(p);
        response.sendRedirect("pagos?accion=listar");
    }

    @Override
    public String getServletInfo() {
        return "Controlador de pagos en Tejidos Rudt";
    }
}
