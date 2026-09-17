<%-- 
    Document   : perfil.jsp
    Created on : 24/08/2026, 10:48:17 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String mensaje = (String) session.getAttribute("mensaje");
session.removeAttribute("mensaje");
%>

<!DOCTYPE html>
<html>
    <head>
    <title>Mi Perfil</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/ui/1.14.2/jquery-ui.js"></script>
    <link rel="stylesheet" href="../css/estilos.css">
    <style>
    body {
        background-color: #f4b6c2;
        font-family: 'Segoe UI', sans-serif;
        color: #333;
        text-align: center;
        margin: 0;
        padding: 0;
    }
    
    nav {
            background-color: #d36b9b;
            padding: 15px;
            color: white;
            font-weight: bold;
            font-size: 18px;
        }
        
    form {
        width: 50%;
        margin: 40px auto;
        background-color: white; 
        border: 1px solid #ccc;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    }

    label {
        display: block;
        margin-top: 15px;
        font-weight: bold;
        text-align: left; 
        color: #333;
    }

    input {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 6px;
        box-sizing: border-box;
        font-family: 'Segoe UI', sans-serif;
    }
    
    button {
        margin-top: 25px;
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        cursor: pointer;
        font-weight: bold;
        font-family: 'Segoe UI', sans-serif;
        width: 100%; 
    }

    button:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
    <nav>Tejidos Rudt - Perfil</nav>
    <h2>Mi Perfil</h2>

    <form action="${pageContext.request.contextPath}/clientes" method="post">
        <input type="hidden" name="accion" value="actualizarPerfil">
        <input type="hidden" name="idCliente" value="${cliente.idCliente}">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${cliente.nombre}" required>

        <label for="correo">Correo:</label>
        <input type="email" id="correo" name="correo" value="${cliente.correo}" required>

        <label for="telefono">Teléfono:</label>
        <input type="text" id="telefono" name="telefono" value="${cliente.telefono}">
        
        <label for="contraseña">Contraseña:</label>
        <input type="password" id="contraseña" name="contraseña" value="${cliente.contraseña}" required>
        
        <label for="edad">Edad:</label>
        <input type="number" id="edad" name="edad" value="${cliente.edad}" min="1" required>

        <button type="submit">Guardar Cambios</button>
    </form>
        
        <div id="dialog-mensaje" title="Comunicado" style="display:none;">
            <p id="textoMensaje"></p>
        </div>
        
        <script>  
        $(document).ready(function () {

        let mensaje = "<%= mensaje %>";

        if (mensaje === "perfilActualizado") {

            $("#textoMensaje").html(
                "✅ Perfil actualizado correctamente");

            $("#dialog-mensaje").dialog({
                modal: true, width: 400});
        }

        if (mensaje !== "null" && mensaje !== "" && mensaje !== "perfilActualizado") {

            $("#textoMensaje").html(
                "⚠️ " + mensaje);

            $("#dialog-mensaje").dialog({
                modal: true, width: 450});
        }
       });
    </script>
</body>
</html>
