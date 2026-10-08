package demodos.modelo;

/**
 * Guarda los datos principales de un producto para usarlos en el sistema.
 */
public class Producto {
    // Datos que identifican el producto y describen su precio y disponibilidad.
    private int id;
    private String nombre;
    private int talla;
    private double precio;
    private int stock;

    /**
     * Crea un producto sin datos para completarlos más adelante.
     */
    public Producto() {
    }

    /**
     * Crea un producto nuevo con su nombre, talla, precio y cantidad disponible.
     */
    public Producto(String nombre, int talla, double precio, int stock) {
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;
    }

    // Estos métodos permiten consultar o cambiar cada dato del producto.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}