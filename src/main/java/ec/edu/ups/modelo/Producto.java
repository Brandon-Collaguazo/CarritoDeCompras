package ec.edu.ups.modelo;

/**
 * Modelo Producto
 * Clase que representa un producto disponible en el sistema.
 * Contiene información básica como: código, nombre y precio.
 */
public class Producto {
    /**
     * Código único que identifica al producto
     */
    private int codigo;

    /**
     * Nombre descriptivo del producto
     */
    private String nombre;

    /**
     * Precio unitario del producto
     */
    private double precio;

    /**
     * Constructor por defecto que crea un producto vacío
     */
    public Producto() {
    }

    /**
     * Constructor que inicializa un producto con todos sus atributos
     * @param codigo Código único del producto
     * @param nombre Nombre descriptivo del producto
     * @param precio Precio unitario del producto
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    //Métodos Getters y Setters

    /**
     * Establece el código único del producto
     * @param codigo Nuevo código para el producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto
     * @param nombre Nuevo nombre para el producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto
     * @param precio Nuevo precio para el producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código único del producto
     * @return El código del producto
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre descriptivo del producto
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio unitario del producto
     * @return El precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Representación en String del producto
     * @return String con formato: "Nombre - $Precio"
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }

}