/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import com.tejidosrudt.modelo.Producto;
import com.tejidosrudt.servicios.ProductoServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author sarac
 */
@WebServlet(name = "ProductoController", urlPatterns = {"/productos"})
public class ProductoController extends HttpServlet {

    private final ProductoServicio productoServicio = new ProductoServicio();

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
                    listarProductos(request, response);
                    break;
                case "buscar":
                    buscarProducto(request, response);
                    break;
                case "editar":
                    editarProducto(request, response);
                    break;
                default:
                    listarProductos(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar productos", e);
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
            System.out.println("Acción recibida: " + request.getParameter("accion"));

            switch (accion != null ? accion : "") {
                case "crear":
                    crearProducto(request, response);
                    break;
                case "actualizar":
                    actualizarProducto(request, response);
                    break;
                case "eliminar":
                    eliminarProducto(request, response);
                    break;
                default:
                    response.sendRedirect("productos?accion=listar");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar productos", e);
        }
    }

    //Obtiene la lista completa de productos rgistrados
    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Producto> lista = productoServicio.listarProductos();
        request.setAttribute("listaProductos", lista);
        request.getRequestDispatcher("vendedor/productos.jsp").forward(request, response);
    }

    //Busca un producto especifico por su ID para mostrar su detalles en pantalla
    private void buscarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = productoServicio.buscarProductoPorId(id);
        request.setAttribute("productoEncontrado", producto);
        request.getRequestDispatcher("/productos/buscar.jsp").forward(request, response);
    }

    //Recupera los datos de un producto para cargarlos en el formulario de edición
    private void editarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
            String idParam = request.getParameter("idProducto");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("productos?accion=listar");
                return;
            }
            
        int id = Integer.parseInt(request.getParameter("idProducto"));
        
        Producto productoEditar = productoServicio.buscarProductoPorId(id);
        request.setAttribute("productoEditar", productoEditar);
        request.getRequestDispatcher("/vendedor/editarProducto.jsp").forward(request, response);
    }

    //Registra un nuevo producto en el catálogo y actualiza la vista
  /*  private void crearProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
       
        Producto p = new Producto();
        p.setNombreProducto(request.getParameter("nombreProducto"));
        p.setEstilo(request.getParameter("estilo"));
        p.setPrecio(new BigDecimal(request.getParameter("precio")));
        p.setTalla(request.getParameter("talla"));
        p.setColor(request.getParameter("color"));
        p.setCapellada(request.getParameter("capellada"));
        p.setDescripcion(request.getParameter("descripcion"));

        productoServicio.registrarProducto(p);
          listarProductos(request, response);
    }
*/
    private void crearProducto(HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

        Producto p = new Producto();

        p.setNombreProducto(request.getParameter("nombreProducto"));
        p.setEstilo(request.getParameter("estilo"));
        p.setPrecio(new BigDecimal(request.getParameter("precio")));
        p.setTalla(request.getParameter("talla"));
        p.setColor(request.getParameter("color"));
        p.setCapellada(request.getParameter("capellada"));
        p.setDescripcion(request.getParameter("descripcion"));

        boolean registrado =
                productoServicio.registrarProducto(p);

        if (registrado) {

            request.getSession().setAttribute(
                    "mensaje",
                    "productoRegistrado"
            );

        } else {

            request.getSession().setAttribute(
                    "mensaje",
                    "errorProductoRegistrado"
            );

        }

        response.sendRedirect(
                "productos?accion=listar"
        );
    }

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
        p.setDescripcion(request.getParameter("descripcion"));

        boolean actualizado =
                productoServicio.actualizarProducto(p);

        if (actualizado) {

            request.getSession().setAttribute(
                    "mensaje",
                    "productoActualizado"
            );

        } else {

            request.getSession().setAttribute(
                    "mensaje",
                    "errorProductoActualizado"
            );

        }

        response.sendRedirect("productos?accion=listar");
    }
  /*  
    //Modifica los datos existentes de un producto y redirige al listado
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
        p.setDescripcion(request.getParameter("descripcion"));

        productoServicio.actualizarProducto(p);
        response.sendRedirect("productos?accion=listar");
    }*/
    
    private void eliminarProducto(HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

    int idProducto =
            Integer.parseInt(
                    request.getParameter("idProducto"));

    Producto p = new Producto();
    p.setIdProducto(idProducto);

    boolean eliminado =
            productoServicio.eliminarProducto(p);

    if (eliminado) {

        request.getSession().setAttribute(
                "mensaje",
                "eliminado");

    } else {

        request.getSession().setAttribute(
                "mensaje",
                "errorEliminar");

    }

    response.sendRedirect(
            "productos?accion=listar");
}
/*
    //Remueve un producto del catálogo de la BD por su ID
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("idProducto"));
        Producto p = new Producto();
        p.setIdProducto(id);

        productoServicio.eliminarProducto(p);
        response.sendRedirect("productos?accion=listar");
    }*/

    @Override
    public String getServletInfo() {
        return "Controlador de productos en Tejidos Rudt";
    }
}
