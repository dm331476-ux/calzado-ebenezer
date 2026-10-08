package demodos.dao;

import demodos.conexion.Conexion;
import demodos.seguridad.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Guarda las cuentas nuevas y busca los datos necesarios para iniciar sesión.
 */
public class UsuarioDao {

    /**
     * Crea una cuenta de vendedor y guarda la contraseña en formato protegido.
     */
    public void registrarUsuario(String nombre, String usuario, String email, String password)
            throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, usuario, email, password, rol) "
                + "VALUES (?, ?, ?, ?, 'vendedor')";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, email);
            ps.setString(4, PasswordUtil.hash(password));
            ps.executeUpdate();
        }
    }

    /**
     * Comprueba una cuenta por nombre de usuario o correo y devuelve su rol si coincide.
     */
    public String autenticarUsuario(String usuarioOEmail, String password) throws SQLException {
        String sql = "SELECT id, password, rol FROM usuarios "
                + "WHERE usuario = ? OR email = ? LIMIT 2";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuarioOEmail);
            ps.setString(2, usuarioOEmail);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                int id = rs.getInt("id");
                String storedPassword = rs.getString("password");
                String rol = rs.getString("rol");
                if (rs.next()) {
                    return null;
                }

                if (!PasswordUtil.matches(password, storedPassword)) {
                    return null;
                }

                // Actualiza la contraseña antigua al formato protegido cuando el acceso es correcto.
                if (PasswordUtil.needsRehash(storedPassword)) {
                    String updateSql = "UPDATE usuarios SET password = ? WHERE id = ?";
                    try (PreparedStatement update = con.prepareStatement(updateSql)) {
                        update.setString(1, PasswordUtil.hash(password));
                        update.setInt(2, id);
                        update.executeUpdate();
                    }
                }

                return esRolValido(rol) ? rol : "vendedor";
            }
        }
    }

    /**
     * Acepta únicamente los roles reconocidos; los valores desconocidos usan el rol básico.
     */
    private static boolean esRolValido(String rol) {
        return "Administrador".equals(rol)
                || "vendedor".equals(rol)
                || "bodega".equals(rol)
                || "fabrica".equals(rol);
    }
}
