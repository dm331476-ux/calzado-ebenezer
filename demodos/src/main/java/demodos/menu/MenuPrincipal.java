package demodos.menu;

import demodos.dao.ProductoDao;
import demodos.modelo.Producto;
import java.util.Scanner;
import java.util.List;

/**
 * Muestra un menú en consola para administrar los productos del calzado.
 */
public class MenuPrincipal {

    /**
     * Presenta las opciones y llama a ProductoDao según lo que elija la persona.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductoDao dao = new ProductoDao();
        int opcion = 0;

        // Repite el menú hasta que se elija la opción para salir.
        do {
            System.out.println("\n--- SISTEMA DE GESTION CALZADO EBENEZER ---");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // Pide los datos de un producto nuevo y solicita guardarlo.
                    System.out.print("Nombre: "); String nom = sc.nextLine();
                    System.out.print("Talla: "); int tall = sc.nextInt();
                    System.out.print("Precio: "); double pre = sc.nextDouble();
                    System.out.print("Stock: "); int sto = sc.nextInt();
                    dao.registrarProducto(new Producto(nom, tall, pre, sto));
                    System.out.println("¡Registrado!");
                    break;
                case 2:
                    // Obtiene los productos guardados y muestra sus datos en pantalla.
                    List<Producto> lista = dao.listarProductos();
                    System.out.println("\n--- LISTA DE PRODUCTOS ---");
                    for (Producto p : lista) {
                        System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Talla: " + p.getTalla()+" | Precio: " + p.getPrecio()+" | Stock: " + p.getStock());
                    }
                    break;
                case 3:
                    // Pide el identificador del producto y los datos que se actualizarán.
                    System.out.print("ID del producto a actualizar: "); int idAct = sc.nextInt(); sc.nextLine();
                    System.out.print("Nuevo Nombre: "); String nuevoNom = sc.nextLine();
                    Producto pAct = new Producto(nuevoNom, 40, 100000.0, 5);
                    pAct.setId(idAct);
                    dao.actualizarProducto(pAct);
                    break;
                case 4:
                    // Elimina el producto que coincida con el identificador indicado.
                    System.out.print("ID a eliminar: "); int idDel = sc.nextInt();
                    dao.eliminarProducto(idDel);
                    break;
            }
        } while (opcion != 5);

        // Cierra la lectura de teclado al terminar el menú.
        sc.close();
    }
}