<%-- 
    Document   : nuevoProducto.jsp
    Created on : 25/08/2026, 7:40:31 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Nuevo Producto</title>
    <link rel="stylesheet" href="../css/estilos.css">
    <style>
        body { font-family: Arial, sans-serif; }
        h1 { text-align: center; color: #007bff; }
        form {
            width: 50%;
            margin: 0 auto;
            border: 1px solid #007bff;
            padding: 20px;
            border-radius: 10px;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            margin-top: 15px;
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h1>Agregar Nuevo Producto</h1>

    <form action="${pageContext.request.contextPath}/productos" method="post">
        <input type="hidden" name="accion" value="crear">

        <label for="nombreProducto">Nombre del Producto:</label>
        <input type="text" id="nombreProducto" name="nombreProducto" required>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion" required>

        <label for="precio">Precio:</label>
        <input type="number" step="0.01" id="precio" name="precio" required>

        <label for="talla">Talla:</label>
        <input type="text" id="talla" name="talla">

        <label for="color">Color:</label>
        <input type="text" id="color" name="color">

        <label for="estilo">Estilo:</label>
        <input type="text" id="estilo" name="estilo">

        <label for="capellada">Capellada:</label>
        <input type="text" id="capellada" name="capellada">

        <button type="submit">Registrar Producto</button>
    </form>
</body>
</html>
