package demodos.dao;

import demodos.modelo.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet; // Asegúrate de añadir este import arriba
import java.util.ArrayList; // Y este también
import java.util.List;

public class ProductoDao {

    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, talla, precio, stock) VALUES (?, ?, ?, ?)";
        
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getTalla());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar el producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Producto> listarProductos() {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT * FROM productos";
    
    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
         PreparedStatement pstmt = conexion.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setTalla(rs.getInt("talla"));
            p.setPrecio(rs.getDouble("precio"));
            p.setStock(rs.getInt("stock"));
            lista.add(p);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar los productos: " + e.getMessage());
    }
    return lista;
}
// Método para actualizar un producto existente
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, talla = ?, precio = ?, stock = ? WHERE id = ?";
        
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getTalla());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}