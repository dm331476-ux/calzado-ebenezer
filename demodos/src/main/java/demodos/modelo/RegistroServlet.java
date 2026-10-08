package demodos.modelo;

import demodos.dao.UsuarioDao;
import demodos.util.ApiResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Recibe los datos del formulario y crea una cuenta pública de vendedor.
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {
    private static final Pattern EMAIL_VALIDO =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (nombre == null || usuario == null || email == null || password == null) {
            ApiResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, false,
                    "Completa todos los datos del formulario.", null);
            return;
        }

        nombre = nombre.trim();
        usuario = usuario.trim();
        email = email.trim();

        if (nombre.isEmpty() || usuario.isEmpty() || email.isEmpty()
                || password.length() < 6 || password.length() > 128
                || nombre.length() > 150 || usuario.length() > 100 || email.length() > 254
                || !EMAIL_VALIDO.matcher(email).matches()) {
            ApiResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, false,
                    "Revisa los datos. La contraseña debe tener entre 6 y 128 caracteres y el correo debe ser válido.",
                    null);
            return;
        }

        try {
            // El rol lo asigna el servidor para que el registro público no pueda dar permisos especiales.
            new UsuarioDao().registrarUsuario(nombre, usuario, email, password);
            ApiResponse.send(response, HttpServletResponse.SC_CREATED, true,
                    "La cuenta de vendedor se creó correctamente. Ya puedes iniciar sesión.",
                    "index.jsp");
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                ApiResponse.send(response, HttpServletResponse.SC_CONFLICT, false,
                        "Ese usuario o correo ya está registrado.", null);
                return;
            }

            log("No fue posible guardar la nueva cuenta.", e);
            ApiResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false,
                    "No se pudo completar el registro. Intenta de nuevo más tarde.", null);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ApiResponse.send(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, false,
                "Usa el formulario para crear una cuenta.", null);
    }
}
