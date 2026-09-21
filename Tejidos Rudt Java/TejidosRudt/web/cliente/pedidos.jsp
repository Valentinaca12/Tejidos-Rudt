<%@page import="com.tejidosrudt.modelo.PedidoEncargo"%>
<%-- 
    Document   : pedidos.jsp
    Created on : 23/08/2026, 9:31:05 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%
String mensaje = (String) session.getAttribute("mensaje");
session.removeAttribute("mensaje");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Mis Pedidos</title>
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
    <nav>Tejidos Rudt - Pedido/Encargo</nav>
    <h2>Mis Pedidos</h2>

    <%
        List<PedidoEncargo> pedidos = (List<PedidoEncargo>) request.getAttribute("pedidos");
        if (pedidos == null || pedidos.isEmpty()) {
    %>
        <p style="text-align:center; color:red;">No tienes pedidos registrados.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>ID Pedido</th>
                <th>Fecha Pedido</th>
                <th>Fecha Envío</th>
                <th>Estado</th>
                <th>Valor Total</th>
                <th>Acción</th>
            </tr>
            <%
                for (PedidoEncargo pedido : pedidos) {
            %>
                <tr>
                    <td><%= pedido.getIdPedido() %></td>
                    <td><%= pedido.getFechaPedido() %></td>
                    <td><%= (pedido.getFechaEnvio() != null ? pedido.getFechaEnvio() : "No asignada") %></td>
                    <td><%= pedido.getEstado() %></td>
                    <td>$<%= pedido.getValorTotal() %></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/pedidoencargo" method="get">
                            <input type="hidden" name="accion" value="verDetalleCliente">
                            <input type="hidden" name="idPedido" value="<%= pedido.getIdPedido() %>">
                            <button type="submit">Ver Detalle</button>
                        </form>
                            
                            <% if("Pendiente".equalsIgnoreCase(pedido.getEstado())){%> 
                            <a href="<%= request.getContextPath()%>/cliente/pagarPedido.jsp?idPedido=<%= pedido.getIdPedido()%>&monto=<%= pedido.getValorTotal() %>"
                               class="btn-pagar">Pagar</a>
                            <%
                                }
                            %>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
    <div id="dialog-mensaje" title="Comunicado" style="display:none;">
            <p id="textoMensaje"></p>
    </div>
    
    <script>

    $(document).ready(function () {

        let mensaje = "<%= mensaje %>";

        if (mensaje === "pedidoConfirmado") {

            $("#textoMensaje").html(
                "✅ Pedido confirmado correctamente"
            );

            $("#dialog-mensaje").dialog({
                modal: true,
                width: 450
            });

        }

    });

    </script>
</body>
</html>

</html>
