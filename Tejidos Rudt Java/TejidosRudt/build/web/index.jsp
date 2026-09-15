<%-- 
    Document   : index.jsp
    Created on : 18/08/2026, 4:29:32 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <title>Inicio - Tejidos Rudt</title>
</head>
<body style="background-color:#f4b6c2; font-family:'Segoe UI', sans-serif; color:#333; text-align:center;">

    <!-- Barra superior -->
    <nav style="background-color:#d36b9b; padding:15px;">
        <span style="color:white; font-weight:bold; font-size:18px;">Tejidos Rudt</span>
    </nav>

    <!-- Contenido principal -->
    <div style="margin-top:80px;">
        <h1 style="color:#333;">Bienvenido a Tejidos Rudt</h1>
        <p style="font-size:16px;">Explora nuestros productos tejidos y gestiona tus pedidos fácilmente.</p>

        <div style="margin-top:40px;">
            <a href="<%= request.getContextPath() %>/auth/login.jsp"
               style="border:2px solid #007bff; color:#007bff; padding:10px 30px; border-radius:10px; text-decoration:none; margin:10px; display:inline-block;">
               Iniciar sesión
            </a>
            <a href="<%= request.getContextPath() %>/auth/registro.jsp"
               style="border:2px solid #007bff; color:#007bff; padding:10px 30px; border-radius:10px; text-decoration:none; margin:10px; display:inline-block;">
               Registrarse
            </a>
        </div>
    </div>

    <!-- Footer -->
    <footer style="background-color:#212529; color:white; padding:20px; text-align:center; margin-top:80px;">
        © 2026 Tejidos Rudt - Todos los derechos reservados
    </footer>

</body>
</html>
