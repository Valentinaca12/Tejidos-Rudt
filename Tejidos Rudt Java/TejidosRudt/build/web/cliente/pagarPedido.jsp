<%-- 
    Document   : pagarPedido.jsp
    Created on : 8/09/2026, 4:45:02 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.tejidosrudt.modelo.Pago.MetodoPago" %>
<%
    String idPedido = request.getParameter("idPedido");
    String monto = request.getParameter("monto");
%>
<!DOCTYPE html>
<html>
<head><title>Pagar Pedido #<%= idPedido %></title></head>
<body>
    <h2>Formulario de Pago</h2>
    <p><strong>Pedido ID:</strong> <%= idPedido %></p>
    <p><strong>Total a Pagar:</strong> $<%= monto %></p>

    <!-- El formulario envía los datos a tu PagoController (Servlet) -->
    <form action="<%=request.getContextPath()%>/pagos" method="POST">
        <!-- Campos ocultos para mantener los IDs obligatorios -->
        <input type="hidden" name="accion" value="crear">
        <input type="hidden" name="idPedido" value="<%= idPedido %>">
        <input type="hidden" name="monto" value="<%= monto %>">

        <label for="metodo">Seleccione el Método de Pago:</label>
        <select name="metodo" id="metodo" required>
            <option value="">-- Seleccione --</option>
            <option value="Efectivo">Efectivo</option>
            <option value="Tarjeta">Tarjeta</option>
            <option value="Transferencia">Transferencia</option>
        </select>

        <button type="submit">Confirmar Pago</button>
    </form>
</body>
</html>

