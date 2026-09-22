<%-- 
    Document   : detallepedido.jsp
    Created on : 1/09/2026, 3:37:00 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.tejidosrudt.modelo.DetallePedido"%>

<!DOCTYPE html>
<html>
<head>
    <title>Detalle del Pedido</title>
    <style>
        body { background-color: #f4b6c2; font-family: 'Segoe UI', sans-serif; text-align: center; }
        table { margin: 40px auto; border-collapse: collapse; width: 80%; background-color: white; }
        th, td { border: 1px solid #ccc; padding: 10px; }
        th { background-color: #d36b9b; color: white; }
        tr:nth-child(even) { background-color: #f9dbe2; }
    </style>
</head>
<body>

    <h2>Detalle del Pedido</h2>

    <table>
        <tr>
            <th>ID Pedido</th>
            <th>Nombre Producto</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>

        <%
            List<DetallePedido> detalles = (List<DetallePedido>) request.getAttribute("detalles");
            if (detalles != null && !detalles.isEmpty()) {
                for (DetallePedido d : detalles) {
        %>
        <tr>
            <td><%= d.getIdPedido() %></td>
            <td><%= d.getNombreProducto()%></td>
            <td><%= d.getCantidad() %></td>
            <td>$<%= d.getSubtotal() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">No hay detalles para este pedido.</td>
        </tr>
        <% } %>
    </table>

    <a href="<%= request.getContextPath() %>/pedidoencargo?accion=listarCliente&idCliente=${cliente.idCliente}">Volver a mis pedidos</a>

</body>
</html>

