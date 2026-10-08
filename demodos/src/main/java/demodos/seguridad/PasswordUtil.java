package demodos.seguridad;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Protege las contraseñas antes de guardarlas y permite comprobarlas al iniciar sesión.
 */
public final class PasswordUtil {
    private static final String PREFIX = "pbkdf2";
    private static final int ITERATIONS = 210_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BYTES = 32;
    private static final int MIN_ITERATIONS = 10_000;
    private static final int MAX_ITERATIONS = 1_000_000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    /**
     * Crea una versión protegida de la contraseña con sal aleatoria.
     */
    public static String hash(String password) {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        byte[] hash = derive(password.toCharArray(), salt, ITERATIONS, HASH_BYTES);

        return PREFIX + "$" + ITERATIONS + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Compara la contraseña ingresada con un hash nuevo o una contraseña antigua sin proteger.
     */
    public static boolean matches(String password, String storedValue) {
        if (password == null || storedValue == null) {
            return false;
        }

        if (!storedValue.startsWith(PREFIX + "$")) {
            return MessageDigest.isEqual(
                    password.getBytes(StandardCharsets.UTF_8),
                    storedValue.getBytes(StandardCharsets.UTF_8));
        }

        String[] parts = storedValue.split("\\$", -1);
        if (parts.length != 4 || !PREFIX.equals(parts[0])) {
            return false;
        }

        try {
            int iterations = Integer.parseInt(parts[1]);
            if (iterations < MIN_ITERATIONS || iterations > MAX_ITERATIONS) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[3]);
            if (salt.length < 8 || expectedHash.length == 0) {
                return false;
            }

            byte[] actualHash = derive(password.toCharArray(), salt, iterations, expectedHash.length);
            return MessageDigest.isEqual(expectedHash, actualHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Indica si una contraseña antigua debe sustituirse por el formato protegido.
     */
    public static boolean needsRehash(String storedValue) {
        return storedValue == null || !storedValue.startsWith(PREFIX + "$");
    }

    /**
     * Deriva los bytes del hash aplicando PBKDF2 con la sal y el número de iteraciones dados.
     */
    private static byte[] derive(char[] password, byte[] salt, int iterations, int bytes) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo proteger la contraseña.", e);
        } finally {
            spec.clearPassword();
            java.util.Arrays.fill(password, '\0');
        }
    }
}
