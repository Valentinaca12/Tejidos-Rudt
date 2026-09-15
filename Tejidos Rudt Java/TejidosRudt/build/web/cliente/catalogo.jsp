<%-- 
    Document   : catalogo.jsp
    Created on : 18/08/2026, 3:39:52 p. m.
    Author     : sarac
--%>

<%@page import="com.tejidosrudt.modelo.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <title>Catálogo de Productos</title>
    <style>
        body{
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
        
        table{
            margin: 40px auto;
            border-collapse: collapse;
            width: 90%;
            background-color: white;         
        }
        
        th, td{
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        
        th{
            background-color: #d36b9b;
            color: white;
        }
        
        tr:nth-child(even){
            background-color: #f9dbe2;
        }
        
        input[type="number"]{
           width: 50px;
           padding: 4px;
           text-align: center;
           border: 1px solid #ccc;
           border-radius: 4px;
           margin-right: 5px;
        }
        
        button[type="submit"]{
            background-color: #007bff;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        
        button[type="submit"]:hover{
            background-color: #0056b3;
        }
        
        
    </style>
</head>
<body>
    <nav>Tejidos Rudt - Catálogo</nav>
    <h2>Catálogo de Productos</h2>

    <table border="1" cellpadding="8" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Estilo</th>
                <th>Precio</th>
                <th>Talla</th>
                <th>Color</th>
                <th>Capellada</th>
                <th>Descripción</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            if (productos != null && !productos.isEmpty()) {
                for (Producto p : productos) {
        %>
            <tr>
                <td><%= p.getNombreProducto() %></td>
                <td><%= p.getEstilo() %></td>
                <td>$<%= p.getPrecio() %></td>
                <td><%= p.getTalla() %></td>
                <td><%= p.getColor() %></td>
                <td><%= p.getCapellada() %></td>
                <td><%= p.getDescripcion() %></td>
                <td>
                   <% if (session.getAttribute("clienteLogueado") != null) { %>
                    <form action="<%= request.getContextPath() %>/carrito" method="post">
                        <input type="hidden" name="accion" value="agregar"/>
                        <input type="hidden" name="idProducto" value="<%= p.getIdProducto() %>"/>
                        <input type="number" name="cantidad" value="1" min="1" style="width:60px;"/>
                        <button type="submit">Agregar al carrito</button>
                    </form>
                <% } else { %>
                    <p>Debes iniciar sesión para agregar productos al carrito.</p>
                <% } %>

                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="8">No hay productos disponibles en el catálogo.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>
