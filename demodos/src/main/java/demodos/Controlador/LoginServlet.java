package demodos.controlador;

import demodos.dao.UsuarioDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. Capturar los datos enviados desde el formulario index.jsp
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // 2. Instanciar el DAO
        UsuarioDao dao = new UsuarioDao();
        
        // 3. Llamar al método correcto que ya tienes en UsuarioDao
        boolean accesoValido = dao.validarUsuario(usuario, clave);

        if (accesoValido) {
            // Si las credenciales son correctas
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            response.sendRedirect("dashboard.jsp");
        } else {
            // Si falla, regresa al index con un parámetro de error
            response.sendRedirect("index.jsp?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}