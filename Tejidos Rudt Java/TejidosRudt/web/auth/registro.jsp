<%-- 
    Document   : registro.jsp
    Created on : 18/08/2026, 4:43:22 p. m.
    Author     : sarac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String mensaje =
        (String) session.getAttribute("mensaje");

session.removeAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
    <title>Registro - Tejidos Rudt</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/ui/1.14.2/jquery-ui.js"></script>
</head>
<body style="background-color:#f4b6c2; font-family:'Segoe UI', sans-serif; color:#333; text-align:center;">

    <!-- Barra superior -->
    <nav style="background-color:#d36b9b; padding:15px;">
        <span style="color:white; font-weight:bold; font-size:18px;">Tejidos Rudt</span>
    </nav>

    <!-- Contenido principal -->
    <div style="margin-top:80px;">
        <h1 style="color:#333;">Crear Cuenta</h1>
        <p style="font-size:16px;">Regístrate para acceder a nuestros productos y servicios.</p>

       <!-- Formulario de registro -->
        <form action="<%= request.getContextPath() %>/auth" method="post" style="margin-top:30px;">
            <input type="hidden" name="accion" value="registro">

            <div style="margin:15px;">
                <input type="text" name="nombre" placeholder="Nombre completo"
                       style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
            </div>

            <div style="margin:15px;">
                <input type="number" name="edad" placeholder="Edad" min="1"
                       style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
            </div>

            <div style="margin:15px;">
                <select name="sexo" style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
                    <option value="">Selecciona tu sexo</option>
                    <option value="FEMENINO">Femenino</option>
                    <option value="MASCULINO">Masculino</option>
                    <option value="OTRO">Otro</option>
                </select>
            </div>

            <div style="margin:15px;">
                <input type="email" name="correo" placeholder="Correo electrónico"
                       style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
            </div>

            <div style="margin:15px;">
                <input type="password" name="contrasena" placeholder="Contraseña"
                       style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
            </div>

            <div style="margin:15px;">
                <input type="text" name="telefono" placeholder="Teléfono"
                       style="padding:10px; width:250px; border-radius:8px; border:1px solid #ccc;" required>
            </div>

            <div style="margin:20px;">
                <button type="submit"
                        style="background-color:#007bff; color:white; padding:10px 30px; border:none; border-radius:10px; cursor:pointer;">
                    Registrarse
                </button>
            </div>
        </form>

        <!-- Enlace a login -->
        <p style="margin-top:20px;">
            ¿Ya tienes cuenta?
            <a href="<%= request.getContextPath() %>/auth/login.jsp" style="color:#007bff; text-decoration:none;">
                Inicia sesión aquí
            </a>
        </p>
    </div>
                
    <div id="dialog-mensaje" title="Comunicado" style="display:none;">
      <p id="textoMensaje"></p>
    </div>    
            
    <script>

    $(document).ready(function () {

        let mensaje = "<%= mensaje %>";

        if (mensaje !== "null" && mensaje !== "") {

            $("#textoMensaje").html(
                "⚠️ " + mensaje
            );

            $("#dialog-mensaje").dialog({
                modal: true,
                width: 450
            });
        }
    });

    </script>            

    <!-- Footer -->
    <footer style="background-color:#212529; color:white; padding:20px; text-align:center; margin-top:80px;">
        © 2026 Tejidos Rudt - Todos los derechos reservados
    </footer>

</body>
</html>
