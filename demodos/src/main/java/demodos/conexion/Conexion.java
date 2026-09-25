package demodos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConnection() {
    Connection conexion = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String password = "";
        conexion = DriverManager.getConnection(url, usuario, password);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return conexion;
}
    public static void main(String[] args) {
    
    String url = "jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC";
    String usuario = "root";
    String password = ""; 

    try {
       
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Intentar establecer la conexión
        Connection conexion = DriverManager.getConnection(url, usuario, password);

        if (conexion != null) {
            System.out.println("¡Conexión exitosa a la base de datos!");
            conexion.close();
        }
    } catch (ClassNotFoundException e) {
        System.out.println("Error: No se encontró el Driver de MySQL en el proyecto.");
        e.printStackTrace();
    } catch (SQLException e) {
        System.out.println("Error de conexión (Verifica puerto o nombre de base de datos):");
        e.printStackTrace();
    }
}

}