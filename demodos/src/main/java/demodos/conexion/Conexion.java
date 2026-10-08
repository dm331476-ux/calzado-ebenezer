package demodos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Reúne la forma de conectarse a la base de datos del sistema.
 */
public class Conexion {

    /**
     * Abre una conexión con la base de datos Ebenezer para que otras partes del
     * sistema puedan consultar o guardar información.
     * Si no se puede conectar, muestra el error y devuelve null.
     */
    public static Connection getConnection() {
        try {
            // Prepara el controlador que permite a Java comunicarse con MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Indica dónde está la base de datos y qué cuenta se usa para acceder.
            String url = "jdbc:mysql://localhost:3306/ebenezer?useSSL=false&serverTimezone=UTC";
            String usuario = "root";
            String password = "";
            return DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            // Informa el motivo del fallo para facilitar la revisión de la conexión.
            e.printStackTrace();
            return null;
        }
    }
}