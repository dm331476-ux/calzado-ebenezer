<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calzado Ebenezer - Registro Administrativo</title>
    <link rel="stylesheet" href="css/estilos.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
</head>
<body>
    <main class="contenedor-login">

        <header class="login-header">
            <img src="img/logo.png" alt="Logo Calzado Ebenezer" style="width: 100px; height: auto; object-fit: contain;">
            <h1>REGISTRO</h1>
            <p>Personal Administrativo y Operativo</p>
        </header>

        <form id="registroForm" action="RegistroServlet" method="POST" class="form-login">
            <div class="grupo-input">
                <label for="nombreCompleto">Nombre Completo</label>
                <input type="text" name="nombre" placeholder="Nombre completo" required>
            </div>

            <div class="grupo-input">
                <label for="correoRegistro">Correo Electrónico</label>
                <input type="email" name="email" placeholder="Correo electrónico" required>
            </div>

            <div class="grupo-input">
                <label for="rolUsuario">Rol en el Sistema</label>
                <select id="rolUsuario" class="selector-rol-ebenezer" required>
                   <option value="" disabled selected>Seleccione su rol operativo</option>
                   <option value="vendedor">Vendedor</option>
                   <option value="bodega">Encargado de Bodega</option>
                   <option value="fabrica">Operario de Fábrica</option>
              </select>
            </div>

            <div class="grupo-input">
    <label for="passRegistro">Contraseña</label>
    <div class="contenedor-password-ojo">
        <input type="password" name="password" placeholder="Contraseña" required>
        <button type="button" class="btn-ojo" onclick="alternarContrasena('passRegistro', this)">👁️</button>
    </div>

            <button type="submit" class="btn-ingresar">REGISTRAR ENTRADA</button>
        </form>

        <footer class="login-footer">
            <p>¿Ya tiene una cuenta activa? <a href="index.jsp">Inicie sesión aquí</a></p>
        </footer>
    </main>

    <div class="pantalla-oscura" id="capaError">
        <div class="notificacion-ebenezer">
            <div class="cabecera-transparente">
                <div class="icono-circular-rojo">
                    <span>X</span>
                </div>
                <h2>ERROR DE REGISTRO</h2>
            </div>

            <img src="img/linea-sombra.PNG" alt="divisor" class="linea-divisor-PNG">

            <div class="cuerpo-notificacion">
                <p class="mensaje-italico">"Operación fallida"</p>
                
                <div class="contenedor-codigo">
                    <span class="etiqueta-codigo">CODE:</span>
                    <span class="numero-codigo">#002</span>
                </div>

                <p class="instruccion-gris">Complete todos los campos correctamente.</p>
                <button type="button" class="btn-reintentar-transp" id="btnCerrarError">REINTENTAR</button>
            </div>
        </div>
    </div>
    <script src="js/autenticacion.js"></script>
</body>
</html>