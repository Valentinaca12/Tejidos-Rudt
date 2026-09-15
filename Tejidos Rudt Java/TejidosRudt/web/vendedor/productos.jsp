<%-- 
    Document   : productos.jsp
    Created on : 25/08/2026, 7:32:58 p. m.
    Author     : sarac
--%>

<%@page import="com.tejidosrudt.modelo.Producto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Productos</title>
    <link rel="stylesheet" href="../css/estilos.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/ui/1.14.2/jquery-ui.js"></script>
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
        
        
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #d36b9b;
            color: white;
        }
        
        tr:nth-child(even){
            background-color: #f9dbe2;
        }
        
        .boton {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .boton:hover {
            background-color: #0056b3;
        }
        .nuevo {
            display: block;
            width: 200px;
            margin: 20px auto;
            text-align: center;
            background-color: #007bff;
            color: white;
            padding: 10px;
            border-radius: 5px;
            text-decoration: none;
        }
        .nuevo:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <nav>Tejidos Rudt - Gestión de productos</nav>
    
    <h2>Catálogo de productos</h2>
    
    

    <a href="<%= request.getContextPath() %>/vendedor/nuevoProducto.jsp" class="nuevo">Agregar Nuevo Producto</a>

    <%
    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
    
String mensaje =(String) session.getAttribute("mensaje");
session.removeAttribute("mensaje");

     %>
<div id="dialog-mensaje"
     title="Comunicado"
     style="display:none;">

    <p id="textoMensaje"></p>

</div>


    
    <% 
        // Verificar si la lista de productos está vacía
        if (listaProductos == null || listaProductos.isEmpty()) { 
    %>
        <p style="text-align:center; color:red;">No hay productos registrados.</p>
    <% 
        } else { 
    %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Estilo</th>
                <th>Talla</th>
                <th>Color</th>
                <th>Capellada</th>
            </tr>
            <% for (Producto producto : listaProductos) { %>
                <tr>
                    <td><%= producto.getIdProducto() %></td>
                    <td><%= producto.getNombreProducto() %></td>
                    <td><%= producto.getDescripcion() %></td>
                    <td>$<%= producto.getPrecio() %></td>
                    <td><%= producto.getEstilo() %></td>
                    <td><%= producto.getTalla() %></td>
                    <td><%= producto.getColor() %></td>
                    <td><%= producto.getCapellada() %></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/productos" method="get" style="display:inline;">
                            <input type="hidden" name="accion" value="editar">
                            <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>">
                            <button type="submit" class="boton">Editar</button>
                        </form>
                        <form action="<%= request.getContextPath() %>/productos" method="post" class="formEliminar" style="display:inline;">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>">
                            <button type="submit" class="boton" style="background-color:#dc3545;">Eliminar</button>
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>
    <script>
    $(document).ready(function () {

    let mensaje = "<%= mensaje %>";

    if (mensaje === "eliminado") {

        $("#textoMensaje").html(
            "✅ Producto eliminado correctamente"
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 400
            
        });

    }

    if (mensaje === "errorEliminar") {

        $("#textoMensaje").html(
            "⚠️ No se puede eliminar el producto porque está asociado a pedidos u otros registros."
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 450
           
        });

    }
    
        if (mensaje === "productoRegistrado") {

        $("#textoMensaje").html(
            "✅ Producto registrado correctamente"
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 400
        });

    }
    
        if (mensaje === "errorProductoRegistrado") {

        $("#textoMensaje").html(
            "❌ No fue posible registrar el producto"
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 400
        });

    }
    
        if (mensaje === "productoActualizado") {

        $("#textoMensaje").html(
            "✅ Producto actualizado correctamente"
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 400
        });

    }
    
        if (mensaje === "errorProductoActualizado") {

        $("#textoMensaje").html(
            "❌ No fue posible actualizar el producto"
        );

        $("#dialog-mensaje").dialog({
            modal: true,
            width: 400
        });

    }

});
</script>
<div id="dialog-confirmar"
     title="Confirmar eliminación"
     style="display:none;">

    <p>
        ⚠️ ¿Está seguro de eliminar este producto?
    </p>

</div>
<script>
    $(document).ready(function () {

    let formularioActual = null;

    $(".formEliminar").on("submit", function (e) {

        e.preventDefault();

        formularioActual = this;

        $("#dialog-confirmar").dialog({

            modal: true,
            width: 400,
            resizable: false,

            buttons: {

                "Confirmar": function () {

                    $(this).dialog("close");

                    formularioActual.submit();

                },

                "Cancelar": function () {

                    $(this).dialog("close");

                }

            }

        });

    });

});
</script>
</body>

</html>

