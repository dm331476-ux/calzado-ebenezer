package demodos;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * Comprueba que el formulario de registro incluya los datos que necesita el sistema.
 */
public class RegistroFormTest {

    /**
     * Revisa que el formulario no exponga el rol y que el servidor asigne vendedor.
     */
    @Test
    public void formularioDebeEnviarLosCamposNecesariosSinExponerElRol() throws Exception {
        // Lee el formulario de registro para verificar sus campos y destino.
        Path registroJsp = Paths.get("src/main/webapp/registro.jsp");
        String contenido = Files.readString(registroJsp);
        Path usuarioDao = Paths.get("src/main/java/demodos/dao/UsuarioDao.java");
        String daoContenido = Files.readString(usuarioDao);

        // Confirma que estén presentes los datos requeridos y el destino del formulario.
        assertTrue("Debe existir el campo usuario en el formulario", contenido.contains("name=\"usuario\""));
        assertTrue("Debe existir el campo password en el formulario", contenido.contains("name=\"password\""));
        assertTrue("El formulario no debe permitir enviar roles",
                !contenido.contains("name=\"rol\""));
        assertTrue("El formulario no debe mostrar una sección de rol",
                !contenido.contains("Rol en el sistema")
                        && !contenido.contains("value=\"Vendedor\""));
        assertTrue("El servidor debe asignar el rol básico al registrar",
                daoContenido.contains("VALUES (?, ?, ?, ?, 'vendedor')"));
        assertTrue("Debe enviar el formulario al RegistroServlet",
                contenido.contains("action=\"RegistroServlet\""));
    }
}
