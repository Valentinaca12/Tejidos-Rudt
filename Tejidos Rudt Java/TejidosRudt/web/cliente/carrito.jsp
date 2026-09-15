<%-- 
    Document   : carrito.jsp
    Created on : 18/08/2026, 8:46:19 p. m.
    Author     : sarac
--%>

<%@page import="com.tejidosrudt.modelo.CarritoDetalle"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tejidosrudt.modelo.Producto" %>
<%@ page import="com.tejidosrudt.modelo.Cliente" %>
<%@ page import="com.tejidosrudt.servicios.ClienteServicio" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

    if (cliente == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    ClienteServicio clienteServicio = new ClienteServicio();
    List<CarritoDetalle> productosCarrito = clienteServicio.listarCarrito(cliente.getIdCliente());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mi Carrito - Tejidos Rudt</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9d6d6;
            text-align: center;
        }
        
        nav {
            background-color: #d36b9b;
            padding: 15px;
            color: white;
            font-weight: bold;
            font-size: 18px;
        }
        
        h2 {
            color: #333;
            margin-top: 30px;
        }
        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 80%;
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #d46a8f;
            color: white;
        }
        tr:hover {
            background-color: #f2f2f2;
        }
        .btn {
            background-color: #007bff;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .total {
            font-weight: bold;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    
    <nav>Tejidos Rudt - Carrito</nav>
    <h2>🛒 Mi Carrito</h2>
    

    <%
        if (productosCarrito == null || productosCarrito.isEmpty()) {
    %>
        <p>Tu carrito está vacío. <a href="<%= request.getContextPath() %>/clientes?accion=catalogo">Ir al catálogo</a></p>
    <%
        } else {
            double total = 0;
    %>
        <table>
            <tr>
                <th>Producto</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
                <th>Acciones</th>
            </tr>
            <%
                for (CarritoDetalle cd : productosCarrito) {
                    double subtotal = cd.getProducto().getPrecio().doubleValue() * cd.getCantidad();
                    total += subtotal;
            %>
            <tr>
                <td><%= cd.getProducto().getNombreProducto()%></td>
                <td><%= cd.getProducto().getDescripcion() %></td>
                <td>$<%= cd.getProducto().getPrecio() %></td>
                
                <!-- Actualizar -->
                <td>
                    <form action="<%= request.getContextPath() %>/carrito" method="post" style="display:inline;">
                        <input type="hidden" name="accion" value="actualizar">
                        <input type="hidden" name="idProducto" value="<%= cd.getProducto().getIdProducto() %>">
                        <input type="number" name="cantidad" value="<%= cd.getCantidad()%>" min="1" style="width:60px;">
                        <button type="submit" class="btn">Actualizar</button>
                    </form>
                </td>
                <td>$<%= subtotal %></td>
                
                <!-- Eliminar -->
                <td>
                    <form action="<%= request.getContextPath() %>/carrito" method="post" style="display:inline;">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="idProducto" value="<%= cd.getProducto().getIdProducto() %>">
                        <button type="submit" class="btn btn-danger">Eliminar</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>

        <p class="total">Total: $<%= total %></p>
        
        <p style="margin-top:20px;">
            <!-- Volver al catálogo -->
            <a href="<%= request.getContextPath() %>/clientes?accion=catalogo" class="btn">Volver al catálogo</a>

            <!-- Volver al dashboard -->
            <a href="<%= request.getContextPath() %>/cliente/dashboard.jsp" class="btn">Volver al Dashboard</a>
                
        </p>

        <form action="<%= request.getContextPath() %>/carrito?accion=confirmar" method="post">
            <input type="hidden" name="accion" value="confirmar">
            <button type="submit" class="btn">Confirmar Pedido</button>
        </form>
    <%
        }
    %>

</body>
</html>

