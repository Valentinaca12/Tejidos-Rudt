<%-- 
    Document   : pagos.jsp
    Created on : 1/09/2026, 10:33:42 p. m.
    Author     : sarac
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pagos - Vendedor</title>
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
   </style>
   
  
</head>
<body>
    
    <nav>Tejidos Rudt - Pagos</nav>
    
    <h2>Listado de Pagos</h2>

    <%
        java.util.List<com.tejidosrudt.modelo.Pago> pagos =
            (java.util.List<com.tejidosrudt.modelo.Pago>) request.getAttribute("pagos");

        if (pagos == null || pagos.isEmpty()) {
    %>
        <p>No hay pagos registrados.</p>
    <%
        } else {
    %>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>ID Pago</th>
                <th>Método</th>
                <th>Monto</th>
                <th>ID Pedido Asociado</th>
            </tr>
            <%
                for (com.tejidosrudt.modelo.Pago p : pagos) {
            %>
                <tr>
                    <td><%= p.getIdPago() %></td>
                    <td><%= p.getMetodo() %></td>
                    <td><%= p.getMonto() %></td>
                    <td><%= p.getIdPedido()%></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>

    <p>
        <a href="<%= request.getContextPath()%>/vendedor/dashboard.jsp"
           style="border:2px solid #007bff; color:#007bff; padding:10px 20px; border-radius:10px; text-decoration:none;">
           Volver al Dashboard
        </a>
    </p>
</body>
</html>


