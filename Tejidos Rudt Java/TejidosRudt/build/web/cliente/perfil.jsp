<%-- 
    Document   : perfil.jsp
    Created on : 24/08/2026, 10:48:17 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Mi Perfil</title>
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

    <form action="${pageContext.request.contextPath}/cliente" method="post">
        <input type="hidden" name="accion" value="actualizarPerfil">
        <input type="hidden" name="idCliente" value="${cliente.idCliente}">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${cliente.nombre}" required>

        <label for="correo">Correo:</label>
        <input type="email" id="correo" name="correo" value="${cliente.correo}" required>

        <label for="telefono">Teléfono:</label>
        <input type="text" id="telefono" name="telefono" value="${cliente.telefono}">

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" value="${cliente.password}" required>

        <button type="submit">Guardar Cambios</button>
    </form>
</body>
</html>
