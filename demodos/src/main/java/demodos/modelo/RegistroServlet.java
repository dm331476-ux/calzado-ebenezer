package demodos.modelo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demodos.dao.UsuarioDao;

// Esta anotación es clave: vincula el form del JSP con este Servlet
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Capturamos los datos que vienen del formulario (debe coincidir con el 'name' en el JSP)
        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

       // 2. Imprimimos en consola para verificar que los datos están llegando correctamente
        System.out.println("Registrando usuario: " + usuario);
        System.out.println("Email: " + email);

        // 3. Llamamos a nuestro UsuarioDao para guardar en la base de datos
        UsuarioDao dao = new UsuarioDao();
        boolean exito = dao.registrarUsuario(nombre, usuario, email, password);

        // 4. Redirección según el resultado
        if (exito) {
            response.sendRedirect("index.jsp?mensaje=registrado");
        } else {
            response.sendRedirect("registro.jsp?error=bd");
        }
     }
}