package demodos.menu;

import demodos.dao.ProductoDao;
import demodos.modelo.Producto;
import java.util.Scanner;
import java.util.List;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductoDao dao = new ProductoDao();
        int opcion = 0;

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
                    System.out.print("Nombre: "); String nom = sc.nextLine();
                    System.out.print("Talla: "); int tall = sc.nextInt();
                    System.out.print("Precio: "); double pre = sc.nextDouble();
                    System.out.print("Stock: "); int sto = sc.nextInt();
                    dao.registrarProducto(new Producto(nom, tall, pre, sto));
                    System.out.println("¡Registrado!");
                    break;
                case 2:
                    List<Producto> lista = dao.listarProductos();
                    System.out.println("\n--- LISTA DE PRODUCTOS ---");
                    for (Producto p : lista) {
                        System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Talla: " + p.getTalla()+" | Precio: " + p.getPrecio()+" | Stock: " + p.getStock());
                    }
                    break;
                case 3:
                    System.out.print("ID del producto a actualizar: "); int idAct = sc.nextInt(); sc.nextLine();
                    System.out.print("Nuevo Nombre: "); String nuevoNom = sc.nextLine();
                    Producto pAct = new Producto(nuevoNom, 40, 100000.0, 5);
                    pAct.setId(idAct);
                    dao.actualizarProducto(pAct);
                    break;
                case 4:
                    System.out.print("ID a eliminar: "); int idDel = sc.nextInt();
                    dao.eliminarProducto(idDel);
                    break;
            }
        } while (opcion != 5);
        sc.close();
    }
}