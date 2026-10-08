package demodos.modelo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demodos.dao.UsuarioDao;

/**
 * Recibe los datos del formulario de registro y los envía a guardar.
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    /**
     * Revisa que el formulario esté completo, registra la cuenta y muestra el resultado.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lee los datos enviados desde el formulario de registro.
        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Si falta algún dato, vuelve al formulario e indica que debe completarse.
        if (nombre == null || usuario == null || email == null || password == null ||
                nombre.trim().isEmpty() || usuario.trim().isEmpty() ||
                email.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("registro.jsp?error=campos");
            return;
        }

        // Deja una referencia en la consola para saber qué cuenta se está procesando.
        System.out.println("Registrando usuario: " + usuario);
        System.out.println("Email: " + email);

        // Pide a UsuarioDao que guarde la cuenta y devuelve el resultado.
        UsuarioDao dao = new UsuarioDao();
        boolean exito = dao.registrarUsuario(nombre, usuario, email, password);

        // Lleva al inicio si el registro se guardó; si no, vuelve al formulario con un aviso.
        if (exito) {
            response.sendRedirect("index.jsp?mensaje=registrado");
        } else {
            response.sendRedirect("registro.jsp?error=bd");
        }
     }
}