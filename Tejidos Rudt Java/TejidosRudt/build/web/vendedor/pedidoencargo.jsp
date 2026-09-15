<%-- 
    Document   : pedidoencargo.jsp
    Created on : 31/08/2026, 9:51:59 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.tejidosrudt.modelo.PedidoEncargo"%>

<!DOCTYPE html>
<html>
    <head>
    <title>Encargos - Panel del Vendedor</title>
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

    <nav>Tejidos Rudt - Encargos</nav>

    <h2>Lista de Encargos</h2>

    <table>
        <tr>
            <th>ID Pedido</th>
            <th>Fecha Pedido</th>
            <th>Fecha Envío</th>
            <th>ID Cliente</th>
            <th>Estado</th>
            <th>Valor Total</th>
            
            
        </tr>

        <%
            List<PedidoEncargo> listaEncargos = (List<PedidoEncargo>) request.getAttribute("pedidos");
            if (listaEncargos != null && !listaEncargos.isEmpty()) {
                for (PedidoEncargo p : listaEncargos) {
        %>
        <tr>
            <td><%= p.getIdPedido() %></td>
            <td><%= p.getFechaPedido() %></td>
            <td><%= p.getFechaEnvio() != null ? p.getFechaEnvio() : "—" %></td>
            <td><%= p.getIdCliente() %></td>
            <td><%= p.getEstado() != null ? p.getEstado() : "Sin estado" %></td>
            <td>$<%= p.getValorTotal() %></td>
           
            <td>
  
                <form action="<%= request.getContextPath() %>/pedidoencargo" method="get" style="display:inline;">
                    <input type="hidden" name="accion" value="verDetalleVendedor">
                    <input type="hidden" name="idPedido" value="<%= p.getIdPedido() %>">
                    <button type="submit" class="btn">Ver Detalle</button>
                </form>

            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="8">No hay encargos registrados.</td>
        </tr>
        <% } %>
    </table>

    <footer style="background-color:#212529; color:white; padding:20px; text-align:center; margin-top:80px;">
        © 2026 Tejidos Rudt - Todos los derechos reservados
    </footer>

</body>
</html>
