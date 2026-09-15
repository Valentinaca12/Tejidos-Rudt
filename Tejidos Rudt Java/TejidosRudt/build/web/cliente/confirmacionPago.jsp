<%-- 
    Document   : confirmacionPago.jsp
    Created on : 8/09/2026, 8:15:42 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tejidos Rudt - Pago Exitoso</title>
    <style>
        .card { text-align: center; margin-top: 50px; font-family: Arial, sans-serif; }
        .success-icon { color: green; font-size: 50px; }
        .btn { display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="card">
        <div class="success-icon">✓</div>
        <h2>¡Tu pago ha sido procesado con éxito!</h2>
        <p>El pedido #<%= request.getParameter("idPedido") %> ha sido registrado como <strong>Pagado</strong>.</p>
        <p>El vendedor ya está preparando tus tejidos.</p>
        <br>
       
        <a href="<%= request.getContextPath() %>/cliente/dashboard.jsp" class="btn">Volver al Dashboard</a>
    </div>
</body>
</html>
