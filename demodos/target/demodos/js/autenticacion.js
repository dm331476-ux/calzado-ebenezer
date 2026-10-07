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
                    window.location.href = "dashboard.jsp";
                }
                else if (usuarioRegistrado && usuarioIngresado === usuarioRegistrado && contrasenaIngresada === contrasenaRegistrada) {
                    const rolRegistrado = localStorage.getItem('rolUsuarioEbenezer');
                    if (rolRegistrado === 'Administrador') {
                        alert(`¡Bienvenido Administrador, ${usuarioIngresado}!`);
                    } else {
                        alert(`¡Bienvenido al sistema, ${usuarioIngresado}!`);
                    }
                    window.location.href = "dashboard.jsp";
                }
                else {
                    if (capaError) {
                        capaError.style.display = 'flex';
                        const mensaje = capaError.querySelector('.instruccion-gris');
                        if (mensaje) {
                            mensaje.textContent = 'Usuario o contraseña incorrectos. Verifique sus credenciales.';
                        }
                    }
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

            if (nombre === '' || usuario === '' || correo === '' || rol === '' || contrasena.length < 6) {
                if (capaError) {
                    capaError.style.display = 'flex';
                    const mensaje = capaError.querySelector('.instruccion-gris');
                    if (mensaje) {
                        mensaje.textContent = 'Complete todos los campos correctamente y use al menos 6 caracteres.';
                    }
                } else {
                    alert('Complete todos los campos correctamente y use al menos 6 caracteres.');
                }
                return;
            }

            localStorage.setItem('nombreUsuarioEbenezer', usuario);
            localStorage.setItem('correoUsuarioEbenezer', correo);
            localStorage.setItem('rolUsuarioEbenezer', rol);
            localStorage.setItem('passUsuarioEbenezer', contrasena);

            alert(`¡Registro Exitoso!\nUsuario: ${usuario}\nRol: ${rol.toUpperCase()}`);
            window.location.href = 'index.jsp';
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

            const nombreRegistro = document.getElementById('nombreCompleto');
            const usuarioRegistro = document.getElementById('usuarioRegistro');
            const correoRegistro = document.getElementById('correoRegistro');
            const rolRegistro = document.getElementById('rolUsuario');
            const passRegistro = document.getElementById('passRegistro');

            if (nombreRegistro) nombreRegistro.value = "";
            if (usuarioRegistro) usuarioRegistro.value = "";
            if (correoRegistro) correoRegistro.value = "";
            if (rolRegistro) rolRegistro.value = "";
            if (passRegistro) passRegistro.value = "";

            const mensaje = capaError ? capaError.querySelector('.instruccion-gris') : null;
            if (mensaje) {
                mensaje.textContent = 'Complete todos los campos correctamente.';
            }
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
        const seccionAdmin = document.querySelector('.seccion-admin');
        const mainContent = document.querySelector('.main-content');

        const mostrarSeccion = (tipo) => {
            if (!mainContent) return;

            const catalogoVisible = tipo === 'ventas';
            const inventarioVisible = tipo === 'bodega';
            const fabricaVisible = tipo === 'fabrica';
            const adminVisible = tipo === 'admin';

            if (seccionCatalogo) seccionCatalogo.style.display = catalogoVisible ? 'flex' : 'none';
            if (panelCarrito) panelCarrito.style.display = catalogoVisible ? 'flex' : 'none';
            if (seccionInventario) seccionInventario.style.display = inventarioVisible ? 'flex' : 'none';
            if (seccionFabrica) seccionFabrica.style.display = fabricaVisible ? 'flex' : 'none';
            if (seccionAdmin) seccionAdmin.style.display = adminVisible ? 'flex' : 'none';

            mainContent.style.gridTemplateColumns = catalogoVisible ? '1fr 350px' : '1fr';
        };

        if (rolGuardado === 'bodega') {
            mostrarSeccion('bodega');
        } else if (rolGuardado === 'fabrica') {
            mostrarSeccion('fabrica');
        } else if (rolGuardado === 'Administrador') {
            mostrarSeccion('admin');
        } else {
            mostrarSeccion('ventas');
        }

        // 3. Control de Opciones del Menú Lateral (Sidebar)
        const menuVentas = document.getElementById('menuVentas');
        const menuBodega = document.getElementById('menuBodega');
        const menuFabrica = document.getElementById('menuFabrica');
        const menuAdmin = document.getElementById('menuAdmin');

        const activarMenu = (menuActivo) => {
            const items = document.querySelectorAll('.menu-item');
            items.forEach(item => {
                item.classList.remove('activo');
            });

            if (menuActivo) {
                menuActivo.classList.add('activo');
            }
        };

        if (menuVentas) {
            menuVentas.addEventListener('click', (e) => {
                e.preventDefault();
                activarMenu(menuVentas);
                mostrarSeccion('ventas');
            });
        }

        if (menuBodega) {
            menuBodega.addEventListener('click', (e) => {
                e.preventDefault();
                activarMenu(menuBodega);
                mostrarSeccion('bodega');
            });
        }

        if (menuFabrica) {
            menuFabrica.addEventListener('click', (e) => {
                e.preventDefault();
                activarMenu(menuFabrica);
                mostrarSeccion('fabrica');
            });
        }

        if (menuAdmin) {
            menuAdmin.addEventListener('click', (e) => {
                e.preventDefault();
                activarMenu(menuAdmin);
                mostrarSeccion('admin');
            });
        }

        // Ocultamos el menú de administración por defecto para los demás roles
        if (menuAdmin) menuAdmin.style.display = 'none';

        if (rolGuardado === 'Administrador') {
            if (menuVentas) menuVentas.style.display = 'block';
            if (menuBodega) menuBodega.style.display = 'block';
            if (menuFabrica) menuFabrica.style.display = 'block';
            if (menuAdmin) menuAdmin.style.display = 'block';
        } else if (rolGuardado === 'vendedor') {
            if (menuVentas) menuVentas.style.display = 'block';
            if (menuBodega) menuBodega.style.display = 'none';
            if (menuFabrica) menuFabrica.style.display = 'none';
        } else if (rolGuardado === 'bodega') {
            if (menuVentas) menuVentas.style.display = 'none';
            if (menuBodega) menuBodega.style.display = 'block';
            if (menuFabrica) menuFabrica.style.display = 'none';
        } else if (rolGuardado === 'fabrica') {
            if (menuVentas) menuVentas.style.display = 'none';
            if (menuBodega) menuBodega.style.display = 'none';
            if (menuFabrica) menuFabrica.style.display = 'block';
        }
    }
});