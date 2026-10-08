// =================================================================
// 1. FUNCIÓN GLOBAL: ALTERNAR VISIBILIDAD DE CONTRASEÑA
// =================================================================
/**
 * Muestra u oculta el texto de un campo de contraseña y actualiza el icono del botón.
 * @param {string} idInput Identificador del campo cuya visibilidad se cambia.
 * @param {HTMLButtonElement} boton Botón que activa el cambio y muestra el icono.
 */
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
// 2. INICIO DE EVENTOS DE LA PÁGINA
// =================================================================
// Los controles se conectan después de cargar el DOM; las comprobaciones permiten
// compartir este script entre páginas que no contienen todos los formularios.
document.addEventListener('DOMContentLoaded', () => {
    
    const capaError = document.getElementById('capaError');
    const btnCerrarError = document.getElementById('btnCerrarError');
    const notificacion = capaError ? capaError.querySelector('.notificacion-ebenezer') : null;
    const tituloNotificacion = capaError ? capaError.querySelector('.cabecera-transparente h2') : null;
    const iconoNotificacion = capaError ? capaError.querySelector('.icono-circular-rojo span') : null;
    const detalleNotificacion = capaError ? capaError.querySelector('.mensaje-italico') : null;
    const etiquetaCodigo = capaError ? capaError.querySelector('.etiqueta-codigo') : null;
    const codigoNotificacion = capaError ? capaError.querySelector('.numero-codigo') : null;
    const mensajeNotificacion = capaError ? capaError.querySelector('.instruccion-gris') : null;
    let destinoNotificacion = null;

    // Usa la misma ventana para informar si una operación salió bien o necesita corregirse.
    const mostrarNotificacion = (mensaje, esExito, destino, esRegistro, codigoHttp) => {
        if (!capaError || !mensajeNotificacion) {
            alert(mensaje);
            if (esExito && destino) window.location.assign(destino);
            return;
        }

        destinoNotificacion = esExito ? destino : null;
        if (notificacion) notificacion.classList.toggle('exito', esExito);
        if (tituloNotificacion) {
            tituloNotificacion.textContent = esExito
                ? (esRegistro ? 'REGISTRO COMPLETADO' : 'ACCESO CONCEDIDO')
                : 'NO SE PUDO COMPLETAR';
        }
        if (iconoNotificacion) iconoNotificacion.textContent = esExito ? '✓' : 'X';
        if (detalleNotificacion) {
            detalleNotificacion.textContent = esExito ? 'Operación completada' : 'Operación fallida';
        }
        if (etiquetaCodigo) etiquetaCodigo.textContent = esExito ? 'ESTADO HTTP:' : 'CÓDIGO:';
        if (codigoNotificacion) {
            codigoNotificacion.textContent = esExito
                ? `#${codigoHttp}`
                : (esRegistro ? '#002' : '#001');
        }
        mensajeNotificacion.textContent = mensaje;
        if (btnCerrarError) btnCerrarError.textContent = esExito ? 'CONTINUAR' : 'REINTENTAR';
        capaError.style.display = 'flex';
    };

    // Envía el formulario al servidor y presenta el resultado que responde la API.
    const enviarFormulario = async (form) => {
        try {
            const respuesta = await fetch(form.action, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: new URLSearchParams(new FormData(form))
            });
            const resultado = await respuesta.json();

            if (respuesta.ok && resultado.success) {
                mostrarNotificacion(
                    resultado.message,
                    true,
                    resultado.redirect,
                    form.id === 'registroForm',
                    respuesta.status
                );
            } else {
                mostrarNotificacion(
                    resultado.message || 'Revisa los datos e intenta de nuevo.',
                    false,
                    null,
                    form.id === 'registroForm'
                );
            }
        } catch (error) {
            mostrarNotificacion(
                'No se pudo completar la solicitud. Revisa tu conexión e intenta de nuevo.',
                false,
                null,
                form.id === 'registroForm'
            );
        }
    };

    // -----------------------------------------
    // A. CONTROL DEL FORMULARIO DE LOGIN
    // -----------------------------------------
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            await enviarFormulario(loginForm);
        });
    }

    // -----------------------------------------
    // B. CONTROL DEL FORMULARIO DE REGISTRO
    // -----------------------------------------
    const registroForm = document.getElementById('registroForm');
    if (registroForm) {
        registroForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            await enviarFormulario(registroForm);
        });
    }

    // -----------------------------------------
    // C. EVENTO DE REINTENTAR (Cerrar Alerta)
    // -----------------------------------------
    if (btnCerrarError) {
        btnCerrarError.addEventListener('click', () => {
            if (destinoNotificacion) {
                window.location.assign(destinoNotificacion);
                return;
            }

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
        const rolGuardado = badgeRol.dataset.rol || 'vendedor';

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

        // Muestra el contenido asociado al rol o sección seleccionada y ajusta la cuadrícula.
        const mostrarSeccion = (tipo) => {
            if (!mainContent) return;
            if (rolGuardado !== 'Administrador'
                    && tipo !== (rolGuardado === 'bodega' ? 'bodega'
                        : rolGuardado === 'fabrica' ? 'fabrica' : 'ventas')) {
                return;
            }

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

        // Mantiene un solo elemento del menú marcado como activo.
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