// =================================================================
// 1. FUNCIÓN GLOBAL: ALTERNAR VISIBILIDAD DE CONTRASEÑA
// =================================================================
/**
 * Permite ver u ocultar el contenido de un campo de contraseña.
 * @param {string} idInput Identificador del campo que se quiere cambiar.
 * @param {HTMLButtonElement} boton Botón que acompaña al campo.
 */
window.alternarContrasena = function(idInput, boton) {
    const input = document.getElementById(idInput);
    if (!input) {
        console.error("No se encontró el input con ID: " + idInput);
        return;
    }

    if (input.type === "password") {
        input.type = "text";
        boton.textContent = "👁️";
    } else {
        input.type = "password";
        boton.textContent = "👁️";
    }
};

// =================================================================
// 2. INICIO DE EVENTOS DE LA PÁGINA (Se ejecuta al cargar el HTML)
// =================================================================
// Espera a que la página esté lista antes de conectar sus formularios y controles.
document.addEventListener('DOMContentLoaded', () => {

    // Estos elementos aparecen en las páginas de acceso y registro.
    const capaError = document.getElementById('capaError');
    const btnCerrarError = document.getElementById('btnCerrarError');

    // -----------------------------------------
    // A. CONTROL DEL FORMULARIO DE LOGIN
    // -----------------------------------------
    // Solo prepara el inicio de sesión cuando esta página contiene el formulario.
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const userEl = document.getElementById('userLogin');
            const passEl = document.getElementById('passLogin');

            if (userEl && passEl) {
                const usuarioIngresado = userEl.value.trim();
                const contrasenaIngresada = passEl.value;

                const usuarioRegistrado = localStorage.getItem('nombreUsuarioEbenezer');
                const contrasenaRegistrada = localStorage.getItem('passUsuarioEbenezer');

                // Acepta la cuenta administrativa definida aquí o los datos guardados al registrarse.
                if (usuarioIngresado === "admin" && contrasenaIngresada === "ebenezer2026") {
                    localStorage.setItem('rolUsuarioEbenezer', 'Administrador');
                    alert("¡Bienvenido Administrador a Calzado Ebenezer!");
                    window.location.href = "dashboard.jsp";
                } 
                else if (usuarioRegistrado && usuarioIngresado === usuarioRegistrado && contrasenaIngresada === contrasenaRegistrada) {
                    alert(`¡Bienvenido al sistema, ${usuarioIngresado}!`);
                    window.location.href = "dashboard.jsp";
                } 
                else {
                    if (capaError) capaError.style.display = 'flex';
                }
            }
        });
    }

    // -----------------------------------------
    // B. CONTROL DEL FORMULARIO DE REGISTRO
    // -----------------------------------------
    // Revisa los datos antes de dejar que el formulario se envíe.
    const registroForm = document.getElementById('registroForm');
    if (registroForm) {
        registroForm.addEventListener('submit', (e) => {
            const nombreEl = document.getElementById('nombreCompleto');
            const usuarioEl = document.getElementById('usuarioRegistro');
            const correoEl = document.getElementById('correoRegistro');
            const rolEl = document.getElementById('rolUsuario');
            const passEl = document.getElementById('passRegistro');

            if (!nombreEl || !usuarioEl || !correoEl || !rolEl || !passEl) {
                return;
            }

            const nombre = nombreEl.value.trim();
            const usuario = usuarioEl.value.trim();
            const correo = correoEl.value.trim();
            const rol = rolEl.value;
            const contrasena = passEl.value;

            // Si falta un dato o la contraseña tiene menos de seis caracteres,
            // se detiene el envío y se avisa a la persona.
            if (nombre === '' || usuario === '' || correo === '' || rol === '' || contrasena.length < 6) {
                e.preventDefault();
                if (capaError) {
                    capaError.style.display = 'flex';
                } else {
                    alert("Completa todos los campos y usa una contraseña de al menos 6 caracteres.");
                }
                return;
            }

            // Conserva los datos de la cuenta en el navegador para usarlos al iniciar sesión.
            localStorage.setItem('nombreUsuarioEbenezer', usuario);
            localStorage.setItem('correoUsuarioEbenezer', correo);
            localStorage.setItem('rolUsuarioEbenezer', rol);
            localStorage.setItem('passUsuarioEbenezer', contrasena);
        });
    }

    // -----------------------------------------
    // C. EVENTO DE REINTENTAR (Cerrar Alerta)
    // -----------------------------------------
    // Cierra el aviso y limpia las contraseñas para que se puedan volver a escribir.
    if (btnCerrarError) {
        btnCerrarError.addEventListener('click', () => {
            if (capaError) capaError.style.display = 'none';
            const passLogin = document.getElementById('passLogin');
            if (passLogin) passLogin.value = "";
            const passRegistro = document.getElementById('passRegistro');
            if (passRegistro) passRegistro.value = "";
        });
    }

    // -----------------------------------------
    // D. DETECTAR Y APLICAR ROL EN EL DASHBOARD (Solo si existe badgeRol)
    // -----------------------------------------
    // La presencia de esta etiqueta indica que se está mostrando el panel principal.
    const badgeRol = document.getElementById('badgeRol');
    if (badgeRol) {
        const rolGuardado = localStorage.getItem('rolUsuarioEbenezer') || 'vendedor';

        // Muestra el nombre del rol con un color que ayuda a distinguirlo.
        if (rolGuardado === 'bodega') {
            badgeRol.textContent = 'Encargado Bodega';
            badgeRol.style.backgroundColor = '#795548';
        } else if (rolGuardado === 'fabrica') {
            badgeRol.textContent = 'Operario Fábrica';
            badgeRol.style.backgroundColor = '#455a64';
        } else if (rolGuardado === 'Administrador') {
            badgeRol.textContent = 'Administrador';
            badgeRol.style.backgroundColor = '#2e7d32';
        } else {
            badgeRol.textContent = 'Ventas / Vendedor';
            badgeRol.style.backgroundColor = 'var(--cafe-ebenezer)';
        }

        // Guarda referencias a las secciones para mostrar solo las que corresponden al rol.
        const panelCarrito = document.querySelector('.panel-carrito');
        const seccionCatalogo = document.querySelector('.seccion-catalogo');
        const seccionInventario = document.querySelector('.seccion-inventario-bodega');
        const seccionFabrica = document.querySelector('.seccion-fabrica-produccion');
        const mainContent = document.querySelector('.main-content');

        if (mainContent) {
            // Bodega ve el inventario; fábrica ve sus órdenes; ventas y administración
            // ven el catálogo y el carrito. Los demás casos muestran solo el catálogo.
            if (rolGuardado === 'bodega') {
                if (seccionCatalogo) seccionCatalogo.style.display = 'none';
                if (panelCarrito) panelCarrito.style.display = 'none';
                if (seccionFabrica) seccionFabrica.style.display = 'none';
                if (seccionInventario) seccionInventario.style.display = 'flex';
                mainContent.style.gridTemplateColumns = '1fr'; 
            } 
            else if (rolGuardado === 'fabrica') {
                if (seccionCatalogo) seccionCatalogo.style.display = 'none';
                if (panelCarrito) panelCarrito.style.display = 'none';
                if (seccionInventario) seccionInventario.style.display = 'none';
                if (seccionFabrica) seccionFabrica.style.display = 'flex';
                mainContent.style.gridTemplateColumns = '1fr';
            }
            else if (rolGuardado === 'vendedor' || rolGuardado === 'Administrador') {
                if (seccionCatalogo) seccionCatalogo.style.display = 'flex';
                if (panelCarrito) panelCarrito.style.display = 'flex';
                if (seccionInventario) seccionInventario.style.display = 'none';
                if (seccionFabrica) seccionFabrica.style.display = 'none';
                mainContent.style.gridTemplateColumns = '1fr 350px';
            } 
            else {
                if (seccionCatalogo) seccionCatalogo.style.display = 'flex';
                if (panelCarrito) panelCarrito.style.display = 'none';
                if (seccionInventario) seccionInventario.style.display = 'none';
                if (seccionFabrica) seccionFabrica.style.display = 'none';
                mainContent.style.gridTemplateColumns = '1fr';
            }
        }

        // Busca las opciones del menú que se mostrarán según el rol guardado.
        const menuVentas = document.getElementById('menuVentas');
        const menuBodega = document.getElementById('menuBodega');
        const menuFabrica = document.getElementById('menuFabrica');
        const menuAdmin = document.getElementById('menuAdmin');

        // La opción administrativa solo se habilita para quien tiene ese rol.
        if (menuAdmin) menuAdmin.style.display = 'none';

        if (rolGuardado === 'Administrador') {
            if (menuVentas) menuVentas.style.display = 'block';
            if (menuBodega) menuBodega.style.display = 'block';
            if (menuFabrica) menuFabrica.style.display = 'block';
            if (menuAdmin) menuAdmin.style.display = 'block';
        } 
        else if (rolGuardado === 'vendedor') {
            if (menuVentas) menuVentas.style.display = 'block';
            if (menuBodega) menuBodega.style.display = 'none';
            if (menuFabrica) menuFabrica.style.display = 'none';
        } 
        else if (rolGuardado === 'bodega') {
            if (menuVentas) menuVentas.style.display = 'none';
            if (menuBodega) menuBodega.style.display = 'block';
            if (menuFabrica) menuFabrica.style.display = 'none';
        } 
        else if (rolGuardado === 'fabrica') {
            if (menuVentas) menuVentas.style.display = 'none';
            if (menuBodega) menuBodega.style.display = 'none';
            if (menuFabrica) menuFabrica.style.display = 'block';
        }
    }
});