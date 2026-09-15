<%-- 
    Document   : editarProducto.jsp
    Created on : 25/08/2026, 9:38:51 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Producto</title>
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
    <nav>Tejidos Rudt - Edición de productos</nav>
    <h2>Editar Producto</h2>

    <form action="${pageContext.request.contextPath}/productos" method="post">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idProducto" value="${productoEditar.idProducto}">

        <label for="nombreProducto">Nombre del Producto:</label>
        <input type="text" id="nombreProducto" name="nombreProducto" value="${productoEditar.nombreProducto}" required>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion" value="${productoEditar.descripcion}" required>

        <label for="precio">Precio:</label>
        <input type="number" step="0.01" id="precio" name="precio" value="${productoEditar.precio}" required>

        <label for="talla">Talla:</label>
        <input type="text" id="talla" name="talla" value="${productoEditar.talla}">

        <label for="color">Color:</label>
        <input type="text" id="color" name="color" value="${productoEditar.color}">

        <label for="estilo">Estilo:</label>
        <input type="text" id="estilo" name="estilo" value="${productoEditar.estilo}">

        <label for="capellada">Capellada:</label>
        <input type="text" id="capellada" name="capellada" value="${productoEditar.capellada}">

        <button type="submit">Actualizar Producto</button>
    </form>
</body>
</html>
