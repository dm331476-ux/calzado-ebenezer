package demodos.modelo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Corregido: @WebServlet en lugar de @WebServlel
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Capturamos los datos que vienen del formulario
        String user = request.getParameter("usuario");
        String pass = request.getParameter("password");

        // 2. Validación (aquí puedes usar tu UsuarioDao.validarUsuario más adelante)
        if (user.equals("admin") && pass.equals("ebenezer2026")) {
            // 3. Si es correcto, redirigimos al Dashboard
            response.sendRedirect("dashboard.jsp");
        } else {
            // 4. Si falla, regresamos al index.jsp
            response.sendRedirect("index.jsp?error=1");
        }
    }
}