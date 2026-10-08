<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calzado Ebenezer - Registro de Cuenta</title>
    <link rel="stylesheet" href="css/estilos.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
</head>
<body>
    <main class="contenedor-login">

        <%-- Presenta el nombre del sistema y explica a quién está dirigido el registro. --%>
        <header class="login-header">
            <img class="logo-login" src="img/logo.png" alt="Logo Calzado Ebenezer">
            <h1>REGISTRO</h1>
            <p>Registro de cuenta para el área de ventas</p>
        </header>

        <%-- Reúne los datos necesarios para crear una cuenta de vendedor. --%>
        <form id="registroForm" action="RegistroServlet" method="POST" class="form-login">
            <div class="grupo-input">
                <label for="nombreCompleto">Nombre Completo</label>
                <input id="nombreCompleto" type="text" name="nombre" placeholder="Nombre completo" required>
            </div>

            <div class="grupo-input">
                <label for="usuarioRegistro">Usuario</label>
                <input id="usuarioRegistro" type="text" name="usuario" placeholder="Nombre de usuario" required>
            </div>

            <div class="grupo-input">
                <label for="correoRegistro">Correo Electrónico</label>
                <input id="correoRegistro" type="email" name="email" placeholder="Correo electrónico" required>
            </div>

            <div class="grupo-input">
                <label>Rol en el sistema</label>
                <input type="text" value="Vendedor" disabled>
            </div>

            <div class="grupo-input">
                <label for="passRegistro">Contraseña</label>
                <div class="contenedor-password-ojo">
                    <input id="passRegistro" type="password" name="password" placeholder="Contraseña" required>
                    <%-- Permite mostrar u ocultar la contraseña mientras se escribe. --%>
                    <button type="button" class="btn-ojo" onclick="alternarContrasena('passRegistro', this)">👁️</button>
                </div>
            </div>

            <%-- Envía los datos del formulario para intentar crear la cuenta. --%>
            <button type="submit" class="btn-ingresar">REGISTRAR ENTRADA</button>
        </form>

        <%-- Ofrece volver a la pantalla de acceso si la persona ya tiene una cuenta. --%>
        <footer class="login-footer">
            <p>¿Ya tiene una cuenta activa? <a href="index.jsp">Inicie sesión aquí</a></p>
        </footer>
    </main>

    <%-- Aviso que el script puede mostrar cuando algún dato del registro requiere atención. --%>
    <div class="pantalla-oscura" id="capaError">
        <div class="notificacion-ebenezer">
            <div class="cabecera-transparente">
                <div class="icono-circular-rojo">
                    <span>X</span>
                </div>
                <h2>ERROR DE REGISTRO</h2>
            </div>

            <img src="${pageContext.request.contextPath}/img/linea-sombra.png" alt="" class="linea-divisor-PNG">

            <div class="cuerpo-notificacion">
                <p class="mensaje-italico">"Operación fallida"</p>
                
                <div class="contenedor-codigo">
                    <span class="etiqueta-codigo">CÓDIGO:</span>
                    <span class="numero-codigo">#002</span>
                </div>

                <p class="instruccion-gris">Complete todos los campos correctamente.</p>
                <button type="button" class="btn-reintentar-transp" id="btnCerrarError">REINTENTAR</button>
            </div>
        </div>
    </div>

    <%-- Activa la validación del registro, el aviso de error y el control de contraseña. --%>
    <script src="js/autenticacion.js"></script>
</body>
</html>