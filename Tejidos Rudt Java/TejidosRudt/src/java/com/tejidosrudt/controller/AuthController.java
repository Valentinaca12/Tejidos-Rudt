/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.controller;

import java.io.IOException;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.Vendedor;
import com.tejidosrudt.servicios.ClienteServicio;
import com.tejidosrudt.servicios.VendedorServicio;
import com.tejidosrudt.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * @author sarac
 */

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private final ClienteServicio clienteServicio = new ClienteServicio();
    private final VendedorServicio vendedorServicio = new VendedorServicio();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("logout".equalsIgnoreCase(accion)) {
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }else{
            SessionUtils.redirigirSegunSesion(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion != null ? accion : "") {
                case "login":
                    login(request, response);
                    break;
                case "loginVendedor":
                    loginVendedor(request, response);
                    break;
                case "registro":
                    registrar(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error en autenticación", e);
        }
    }
    
    
    
    
//Login para Cliente
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        //Captura de credenciales enviadas desde el formulario login.jsp
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        //Consulta en la base de datos a traves del servicio de autenticación de clientes
        Cliente cliente = clienteServicio.login(correo, contrasena);

        if (cliente != null) {
            //Credenciale válidas: Se crea la sesión HTTP y se almacena el objeto clienteLogueado
            HttpSession session = request.getSession();
            session.setAttribute("clienteLogueado", cliente);

            // Redirigir al dashboard del cliente
            response.sendRedirect(request.getContextPath() + "/cliente/dashboard.jsp");
        } else {
            //Credenciales invalidas: mensaje de error
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }

    //Registra un nuevo cliente en el sistema
    private void registrar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {

        // Captura de datos 
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        int edad = Integer.parseInt(request.getParameter("edad")); 
        String sexo = request.getParameter("sexo"); 

        // Creación e inicialización del objeto Cliente con los datos capturados
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setContraseña(contraseña);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEdad(edad);
        nuevoCliente.setSexo(Cliente.Sexo.valueOf(sexo.toUpperCase()));
   
   try {
       //Envío del objeto al servicio para su validación e inserción en la BD
    boolean registrado =
            clienteServicio.registrar(nuevoCliente);

    if (registrado) {
        // Si el registro es exitoso, se guarda un mensaje en sesión y se redirecciona al login
        request.getSession().setAttribute( "mensaje", "clienteRegistrado");

        response.sendRedirect(
                request.getContextPath() + "/auth/login.jsp");
    }

    } catch (IllegalArgumentException e) {
        //Captura de errores y redirecciójn con mensaje de error
        request.getSession().setAttribute( "mensaje",e.getMessage());

        response.sendRedirect(request.getContextPath()+ "/auth/registro.jsp"
        );

    }
  

}
    
    // Login para Vendedor
    private void loginVendedor(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {

    String nombre = request.getParameter("nombre").trim().toLowerCase();
    String contrasena = request.getParameter("contrasena");

    Vendedor vendedor = vendedorServicio.login(nombre, contrasena);

    if (vendedor != null) {
        HttpSession session = request.getSession();
        session.setAttribute("vendedor", vendedor);
        response.sendRedirect(request.getContextPath() + "/vendedor/dashboard.jsp");
    } else {
        request.setAttribute("error", "Nombre o contraseña incorrectos");
        request.getRequestDispatcher("/auth/loginVendedor.jsp").forward(request, response);
    }
}

    @Override
    public String getServletInfo() {
        return "Controlador de autenticación de Tejidos Rudt";
    }
}
