/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.tejidosrudt.modelo.Cliente;
import com.tejidosrudt.modelo.Vendedor;

/**
 *
 * @author sarac
 */
public class SessionUtils {
    
    /*   MÉTODOS CLIENTE   */
    
    // Verifica si hay sesión activa
    public static boolean estaLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute("clienteLogueado") != null);
    }
    
       // Redirige según sesión activa
    public static void redirigirSegunSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("clienteLogueado") != null) {
            response.sendRedirect("catalogo.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }
    
    //  Obtener cliente logueado
    public static Cliente obtenerClienteLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object clienteObj = session.getAttribute("clienteLogueado");
            if (clienteObj instanceof Cliente) {
                return (Cliente) clienteObj;
            }
        }
        return null;
    }
    
    //Obtener idCliente
    public static int obtenerIdCliente(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
        Object clienteObj = session.getAttribute("clienteLogueado");
        if (clienteObj instanceof Cliente) {
            Cliente cliente = (Cliente) clienteObj;
            return cliente.getIdCliente();
        }
    }
    return 0;
}


    /*   MÉTODOS VENDEDOR   */
    
    // Verifica si hay sesión activa (vendedor)
    public static boolean estaLogueadoVendedor(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute("vendedor") != null);
    }

    // Redirige según sesión activa (vendedor)
    public static void redirigirSegunSesionVendedor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("vendedor") != null) {
            response.sendRedirect("vendedor/dashboard.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    // Obtener vendedor logueado
    public static Vendedor obtenerVendedorLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object vendedorObj = session.getAttribute("vendedor");
            if (vendedorObj instanceof Vendedor) {
                return (Vendedor) vendedorObj;
            }
        }
        return null;
    }

    // Obtener idVendedor
    public static int obtenerIdVendedor(HttpServletRequest request) {
        Object id = request.getSession().getAttribute("idVendedor");
        return id != null ? (int) id : 0;
    }
}
