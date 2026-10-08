package demodos.dao;

import demodos.modelo.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de guardar, consultar, modificar y eliminar productos en la base de datos.
 */
public class ProductoDao {

    /**
     * Guarda un producto nuevo con sus datos y avisa si pudo registrarlo.
     */
    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, talla, precio, stock) VALUES (?, ?, ?, ?)";
        
        // Se abre la conexión y se preparan los datos para guardarlos en la tabla.
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            // Cada valor del producto se coloca en el espacio que le corresponde.
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getTalla());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            
            // Si la base de datos acepta el registro, la operación termina correctamente.
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar el producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca todos los productos y devuelve sus datos en una lista.
     */
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Se convierte cada fila encontrada en un producto y se agrega a la lista.
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

    /**
     * Cambia los datos del producto que coincide con el identificador recibido.
     */
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, talla = ?, precio = ?, stock = ? WHERE id = ?";
        
        // Se preparan los nuevos datos y se identifica qué producto debe cambiarse.
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
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

    /**
     * Quita de la lista el producto que tenga el identificador indicado.
     */
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        // El identificador permite borrar solo el producto seleccionado.
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC", "root", "");
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