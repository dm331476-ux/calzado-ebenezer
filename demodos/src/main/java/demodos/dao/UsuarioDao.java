package demodos.dao;

import demodos.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Se encarga de guardar usuarios y comprobar sus datos de acceso.
 */
public class UsuarioDao {

    /**
     * Guarda los datos de una cuenta nueva y devuelve si se pudo completar el registro.
     */
    public boolean registrarUsuario(String nombre, String usuario, String email, String password) {
        String sql = "INSERT INTO usuarios (nombre, usuario, email, password) VALUES (?, ?, ?, ?)";

        // Usa la conexión compartida del sistema y prepara los datos para guardarlos.
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Coloca cada dato de la cuenta en el campo correspondiente.
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, email);
            ps.setString(4, password);

            // Informa si se agregó una fila a la tabla de usuarios.
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Revisa si existe una cuenta con el usuario y la contraseña recibidos.
     */
    public boolean validarUsuario(String usuario, String password) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        // Busca una coincidencia usando ambos datos de acceso.
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            // Si la consulta encuentra una fila, los datos coinciden con una cuenta.
            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("Error al validar: " + e.getMessage());
            return false;
        }
    }
}