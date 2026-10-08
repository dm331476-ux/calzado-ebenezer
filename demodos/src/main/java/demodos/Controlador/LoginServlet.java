package demodos.Controlador;

import demodos.dao.UsuarioDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Recibe los datos del formulario de acceso y decide si el usuario puede entrar al sistema.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Comprueba los datos recibidos y envía al usuario al panel o de vuelta al inicio.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Lee el usuario y la contraseña que se escribieron en el formulario de acceso.
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // Deja la comprobación de las credenciales a la clase que consulta los usuarios.
        UsuarioDao dao = new UsuarioDao();
        
        boolean accesoValido = dao.validarUsuario(usuario, clave);

        if (accesoValido) {
            // Guarda el nombre en la sesión para identificar al usuario durante su visita
            // y lo lleva al panel principal.
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            response.sendRedirect("dashboard.jsp");
        } else {
            // Si los datos no coinciden, vuelve al inicio indicando que hubo un error.
            response.sendRedirect("index.jsp?error=true");
        }
    }

    /**
     * Atiende las solicitudes GET y las procesa con la misma comprobación de acceso.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Atiende el envío del formulario y lo procesa con la comprobación de acceso.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}