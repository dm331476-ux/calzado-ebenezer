package demodos.Controlador;

import demodos.dao.UsuarioDao;
import demodos.util.ApiResponse;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Recibe los datos de acceso y responde si la cuenta puede entrar al sistema.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuarioOEmail = request.getParameter("usuario");
        String password = request.getParameter("clave");

        if (usuarioOEmail == null || password == null
                || usuarioOEmail.trim().isEmpty() || password.isEmpty()) {
            ApiResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, false,
                    "Escribe tu usuario o correo y tu contraseña.", null);
            return;
        }

        try {
            String rol = new UsuarioDao().autenticarUsuario(usuarioOEmail.trim(), password);
            if (rol == null) {
                ApiResponse.send(response, HttpServletResponse.SC_UNAUTHORIZED, false,
                        "El usuario o la contraseña no son correctos.", null);
                return;
            }

            // Crea una sesión nueva para que no se reutilice una sesión anterior al iniciar acceso.
            HttpSession sesionAnterior = request.getSession(false);
            if (sesionAnterior != null) {
                sesionAnterior.invalidate();
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuarioOEmail.trim());
            session.setAttribute("rol", rol);

            ApiResponse.send(response, HttpServletResponse.SC_OK, true,
                    "¡Bienvenido al sistema!", "dashboard.jsp");
        } catch (SQLException e) {
            log("No fue posible comprobar las credenciales en la base de datos.", e);
            ApiResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false,
                    "No se pudo comprobar el acceso. Intenta de nuevo más tarde.", null);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ApiResponse.send(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, false,
                "Usa el formulario para iniciar sesión.", null);
    }
}
