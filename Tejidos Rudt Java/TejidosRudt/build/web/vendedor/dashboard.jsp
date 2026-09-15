<%-- 
    Document   : dashboard.jsp
    Created on : 25/07/2026, 10:16:51 p. m.
    Author     : sarac
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tejidosrudt.modelo.Vendedor" %>
<%@ page import="com.tejidosrudt.util.SessionUtils" %>
<%
    Vendedor vendedor = SessionUtils.obtenerVendedorLogueado(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Vendedor</title>
</head>
<body style="background-color:#f4b6c2; font-family:'Segoe UI', sans-serif; color:#333;">

    <!-- Barra superior -->
    <nav style="background-color:#d36b9b; padding:15px;">
        <span style="color:white; font-weight:bold; font-size:18px;">Tejidos Rudt</span>
        <span style="float:right; color:white; margin-right:20px;">
            Hola, <%= (vendedor != null ? vendedor.getNombre() : "Vendedor") %>
        </span>
        <a href="<%= request.getContextPath()%>/auth?accion=logout"
           style="background-color:#dc3545; color:white; padding:5px 12px; border-radius:5px; text-decoration:none; float:right; margin-right:10px;">
           Cerrar sesión
        </a>
    </nav>

    <!-- Contenido principal -->
    <div style="text-align:center; margin-top:50px;">
        <h1 style="color:#333;">Panel del Vendedor</h1>
        <p>Desde aquí puedes gestionar tus productos, encargos y revisar pagos.</p>

        <div style="margin-top:30px;">
            <a href="<%= request.getContextPath()%>/productos?accion=listar"
               style="border:2px solid #007bff; color:#007bff; padding:10px 30px; border-radius:10px; text-decoration:none; margin:10px; display:inline-block;">
               Gestionar Productos
            </a>
            <a href="<%= request.getContextPath()%>/pedidoencargo?accion=listarVendedor&idVendedor=${vendedor.idVendedor}"
               style="border:2px solid #007bff; color:#007bff; padding:10px 30px; border-radius:10px; text-decoration:none; margin:10px; display:inline-block;">
               Ver Encargos
            </a>
            <a href="<%= request.getContextPath()%>/pagos?accion=listarConPedidos"
               style="border:2px solid #007bff; color:#007bff; padding:10px 30px; border-radius:10px; text-decoration:none; margin:10px; display:inline-block;">
               Revisar Pagos
            </a>
        </div>
    </div>

    <!-- Footer -->
    <footer style="background-color:#212529; color:white; padding:20px; text-align:center; margin-top:50px;">
        © 2026 Tejidos Rudt - Todos los derechos reservados
    </footer>

</body>
</html>

