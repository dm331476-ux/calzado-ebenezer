<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Página de acceso: define el documento y carga los recursos visuales del inicio de sesión. --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calzado Ebenezer - Sistema de Gestión</title>
    <link rel="stylesheet" href="css/estilos.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
</head>
<body>
    <main class="contenedor-login">

        <%-- Presenta la identidad del sistema antes de solicitar las credenciales. --%>
        <header class="login-header">
            <img class="logo-login" src="img/logo.png" alt="Logo Calzado Ebenezer">
            <h1>INICIAR SESIÓN</h1>
            <p>Sistema de Gestión de Ventas e Inventario</p>
        </header>

        <%-- Declara el destino del formulario; el script intercepta el envío para validar el acceso. --%>
<form id="loginForm" class="form-login" action="LoginServlet" method="POST">
    <div class="grupo-input">
        <label for="userLogin">Usuario o Correo</label>
        <input type="text" id="userLogin" name="usuario" placeholder="Ingrese su usuario" required>
    </div>

    <div class="grupo-input">
        <label for="passLogin">Contraseña</label>
        <div class="contenedor-password-ojo">
            <input type="password" id="passLogin" name="clave" placeholder="Ingresa tu contraseña" required>
            <button type="button" class="btn-ojo" onclick="alternarContrasena('passLogin', this)">👁️</button>
        </div>
    </div>

    <button type="submit" class="btn-ingresar">INGRESAR</button>
</form>

        <%-- Ofrece navegación al formulario para crear una cuenta administrativa. --%>
        <footer class="login-footer">
            <p>¿No tiene una cuenta administrativa? <a href="registro.jsp">Regístrese aquí</a></p>
        </footer>
    </main>

    <%-- Capa inicialmente controlada por JavaScript para mostrar errores y permitir reintentar. --%>
    <div class="pantalla-oscura" id="capaError">
        <div class="notificacion-ebenezer">
            
            <div class="cabecera-transparente">
                <div class="icono-circular-rojo">
                    <span>X</span>
                </div>
                <h2>ERROR DE ACCESO</h2>
            </div>

            <img src="img/linea-sombra.PNG" alt="divisor" class="linea-divisor-PNG">

            <div class="cuerpo-notificacion">
                <p class="mensaje-italico">"Datos incorrectos"</p>
                
                <div class="contenedor-codigo">
                    <span class="etiqueta-codigo">CODE:</span>
                    <span class="numero-codigo">#001</span>
                </div>

                <p class="instruccion-gris">Verifique sus credenciales y reintente.</p>
                
                <button type="button" class="btn-reintentar-transp" id="btnCerrarError">REINTENTAR</button>
            </div>
        </div>
    </div>

    <%-- Activa el cambio de visibilidad de contraseña y los eventos de autenticación. --%>
    <script src="js/autenticacion.js"></script>
</body>
</html>