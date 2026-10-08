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
     * Revisa que se pidan usuario, contraseña y rol, y que los datos se envíen al servlet.
     */
    @Test
    public void formularioDebeEnviarLosCamposNecesariosParaElRegistro() throws Exception {
        // Lee el formulario de registro para verificar sus campos y destino.
        Path registroJsp = Paths.get("src/main/webapp/registro.jsp");
        String contenido = Files.readString(registroJsp);

        // Confirma que estén presentes los datos requeridos y el destino del formulario.
        assertTrue("Debe existir el campo usuario en el formulario", contenido.contains("name=\"usuario\""));
        assertTrue("Debe existir el campo password en el formulario", contenido.contains("name=\"password\""));
        assertTrue("Debe existir el campo rol en el formulario", contenido.contains("name=\"rol\""));
        assertTrue("Debe enviar el formulario al RegistroServlet", contenido.contains("action=\"RegistroServlet\""));
    }
}
