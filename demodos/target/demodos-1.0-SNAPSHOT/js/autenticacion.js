// =================================================================
// 1. FUNCIÓN GLOBAL: ALTERNAR VISIBILIDAD DE CONTRASEÑA
// =================================================================
window.alternarContrasena = function(idInput, boton) {
    const input = document.getElementById(idInput);
    if (!input) {
        console.error("No se encontró el input con ID: " + idInput);
        return;
    }

    if (input.type === "password") {
        input.type = "text";
        boton.textContent = "🙈";
    } else {
        input.type = "password";
        boton.textContent = "👁️";
    }
};

// =================================================================
// 2. INICIO DE EVENTOS DE LA PÁGINA (Se ejecuta al cargar el HTML)
// =================================================================
document.addEventListener('DOMContentLoaded', () => {
    
    // Componentes de error comunes
    const capaError = document.getElementById('capaError');
    const btnCerrarError = document.getElementById('btnCerrarError');

    // -----------------------------------------
    // A. CONTROL DEL FORMULARIO DE LOGIN
    // -----------------------------------------
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

                if (usuarioIngresado === "admin" && contrasenaIngresada === "ebenezer2026") {
                    localStorage.setItem('rolUsuarioEbenezer', 'Administrador');
                    alert("¡Bienvenido Administrador a Calzado Ebenezer!");
                    window.location.href = "dashboard.html";
                } 
                else if (usuarioRegistrado && usuarioIngresado === usuarioRegistrado && contrasenaIngresada === contrasenaRegistrada) {
                    alert(`¡Bienvenido al sistema, ${usuarioIngresado}!`);
                    window.location.href = "dashboard.html";
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
    const registroForm = document.getElementById('registroForm');
    if (registroForm) {
        registroForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const nombreEl = document.getElementById('nombreCompleto');
            const correoEl = document.getElementById('correoRegistro');
            const rolEl = document.getElementById('rolUsuario');
            const passEl = document.getElementById('passRegistro');

            if (nombreEl && correoEl && rolEl && passEl) {
                const nombre = nombreEl.value.trim();
                const correo = correoEl.value.trim();
                const rol = rolEl.value;
                const contrasena = passEl.value;

                if (contrasena.length >= 6 && rol !== "") {
                    localStorage.setItem('nombreUsuarioEbenezer', nombre);
                    localStorage.setItem('correoUsuarioEbenezer', correo);
                    localStorage.setItem('rolUsuarioEbenezer', rol);
                    localStorage.setItem('passUsuarioEbenezer', contrasena);

                    alert(`¡Registro Exitoso!\nUsuario: ${nombre}\nRol: ${rol.toUpperCase()}`);
                    window.location.href = "index.html";
                } else {
                    if (capaError) {
                        capaError.style.display = 'flex';
                    } else {
                        alert("La contraseña debe tener mínimo 6 caracteres y debes seleccionar un rol.");
                    }
                }
            }
        });
    }

    // -----------------------------------------
    // C. EVENTO DE REINTENTAR (Cerrar Alerta)
    // -----------------------------------------
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
    const badgeRol = document.getElementById('badgeRol');
    if (badgeRol) {
        const rolGuardado = localStorage.getItem('rolUsuarioEbenezer') || 'vendedor';

        // 1. Personalizar el Badge Visual de Roles
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

        // 2. Control Visual de Vistas del Dashboard
        const panelCarrito = document.querySelector('.panel-carrito');
        const seccionCatalogo = document.querySelector('.seccion-catalogo');
        const seccionInventario = document.querySelector('.seccion-inventario-bodega');
        const seccionFabrica = document.querySelector('.seccion-fabrica-produccion');
        const mainContent = document.querySelector('.main-content');

        if (mainContent) {
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

        // 3. Control de Opciones del Menú Lateral (Sidebar)
        const menuVentas = document.getElementById('menuVentas');
        const menuBodega = document.getElementById('menuBodega');
        const menuFabrica = document.getElementById('menuFabrica');
        const menuAdmin = document.getElementById('menuAdmin');

        // Ocultamos el menú de administración por defecto para los demás roles
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