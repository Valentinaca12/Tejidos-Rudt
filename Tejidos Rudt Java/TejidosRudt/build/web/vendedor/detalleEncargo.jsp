<%-- 
    Document   : detalleEncargo.jsp
    Created on : 4/09/2026, 8:29:35 p. m.
    Author     : sarac
--%>

<%@page import="com.tejidosrudt.modelo.DetallePedido"%>
<%@page import="java.util.List"%>
<%@page import="com.tejidosrudt.modelo.PedidoEncargo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <title>Detalle del Encargo</title>
    <style>
        body {
            background-color: #f4b6c2;
            font-family: 'Segoe UI', sans-serif;
            color: #333;
            text-align: center;
        }
        nav {
            background-color: #d36b9b;
            padding: 15px;
            color: white;
            font-weight: bold;
            font-size: 18px;
        }
        table {
            margin: 40px auto;
            border-collapse: collapse;
            width: 90%;
            background-color: white;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
        }
        th {
            background-color: #d36b9b;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9dbe2;
        }
        .btn {
            background-color: #007bff;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <nav>Tejidos Rudt - Detalle del Encargo</nav>

    <%
        PedidoEncargo pedido = (PedidoEncargo) request.getAttribute("pedido");
        List<DetallePedido> detalles = (List<DetallePedido>) request.getAttribute("detalles");
    %>

    <h2>Encargo #<%= pedido.getIdPedido() %></h2>
    <p><strong>Cliente:</strong> <%= pedido.getIdCliente() %></p>
    <p><strong>Fecha Pedido:</strong> <%= pedido.getFechaPedido() %></p>
    <p><strong>Fecha Envío:</strong> <%= pedido.getFechaEnvio() != null ? pedido.getFechaEnvio() : "—" %></p>
    <p><strong>Estado:</strong> <%= pedido.getEstado() != null ? pedido.getEstado() : "Sin estado" %></p>
    <p><strong>Total:</strong> $<%= pedido.getValorTotal() %></p>

    <h3>Productos del Encargo</h3>
    <table>
        <tr>
            <th>ID Detalle</th>
            <th>ID Producto</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>
        <%
            if (detalles != null && !detalles.isEmpty()) {
                for (DetallePedido d : detalles) {
        %>
        <tr>
            <td><%= d.getIdDetalle() %></td>
            <td><%= d.getIdProducto() %></td>
            <td><%= d.getCantidad() %></td>
            <td>$<%= d.getSubtotal() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">No hay productos en este encargo.</td>
        </tr>
        <% } %>
    </table>

    <h3>Actualizar Estado</h3>
    <form action="<%= request.getContextPath() %>/pedidoencargo" method="post">
        <input type="hidden" name="accion" value="actualizarEstado"/>
        <input type="hidden" name="idPedido" value="<%= pedido.getIdPedido() %>"/>
        <select name="estado">
            <option value="Pendiente">Pendiente</option>
            <option value="En proceso">En proceso</option>
            <option value="Enviado">Enviado</option>
            <option value="Entregado">Entregado</option>
        </select>
        <button type="submit" class="btn">Guardar</button>
    </form>

    <footer style="background-color:#212529; color:white; padding:20px; text-align:center; margin-top:80px;">
        © 2026 Tejidos Rudt - Todos los derechos reservados
    </footer>

</body>
</html>

