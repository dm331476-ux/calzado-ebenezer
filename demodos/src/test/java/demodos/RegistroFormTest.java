package demodos;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class RegistroFormTest {

    @Test
    public void formularioDebeEnviarLosCamposNecesariosParaElRegistro() throws Exception {
        Path registroJsp = Paths.get("src/main/webapp/registro.jsp");
        String contenido = Files.readString(registroJsp);

        assertTrue("Debe existir el campo usuario en el formulario", contenido.contains("name=\"usuario\""));
        assertTrue("Debe existir el campo password en el formulario", contenido.contains("name=\"password\""));
        assertTrue("Debe existir el campo rol en el formulario", contenido.contains("name=\"rol\""));
        assertTrue("Debe enviar el formulario al RegistroServlet", contenido.contains("action=\"RegistroServlet\""));
    }
}
