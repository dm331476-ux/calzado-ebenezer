package demodos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import demodos.conexion.Conexion;

public class UsuarioDao {

    public boolean registrarUsuario(String nombre, String usuario, String email, String password) {
        String sql = "INSERT INTO usuarios (nombre, usuario, email, password) VALUES (?, ?, ?, ?)";
        
            try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) { 
            
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, email);
            ps.setString(4, password);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    } // <-- Aquí se cierra correctamente registrarUsuario

    public boolean validarUsuario(String usuario, String password) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, password);
            
            return ps.executeQuery().next();
            
        } catch (SQLException e) {
            System.out.println("Error al validar: " + e.getMessage());
            return false;
        }
    }
}