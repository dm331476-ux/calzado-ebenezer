package demodos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import demodos.seguridad.PasswordUtil;
import org.junit.Test;

public class PasswordUtilTest {

    @Test
    public void hashProtegeLaClaveYPermiteComprobarla() {
        String password = "clave-segura-123";
        String hash = PasswordUtil.hash(password);

        assertNotEquals(password, hash);
        assertTrue(PasswordUtil.matches(password, hash));
        assertFalse(PasswordUtil.matches("otra-clave", hash));
    }

    @Test
    public void hashUsaUnaSalDistintaParaCadaCuenta() {
        String firstHash = PasswordUtil.hash("clave-segura-123");
        String secondHash = PasswordUtil.hash("clave-segura-123");

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    public void aceptaTemporalmenteClavesAntiguasParaPoderActualizarSuHash() {
        assertTrue(PasswordUtil.matches("clave-antigua", "clave-antigua"));
        assertTrue(PasswordUtil.needsRehash("clave-antigua"));
        assertFalse(PasswordUtil.needsRehash(PasswordUtil.hash("clave-antigua")));
    }
}
