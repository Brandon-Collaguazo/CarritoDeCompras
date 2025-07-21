package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase que representa un ítem dentro de un carrito de compras.
 * Contiene un producto y la cantidad seleccionada de dicho producto,
 * además de proporcionar métodos para calcular el subtotal del ítem.
 */
public class ItemCarrito implements Serializable {

    private static final long serialVersionUID = 1L;
    private Producto producto;
    private int cantidad;


    public ItemCarrito() {
    }

    /**
     * Constructor que crea un ítem con un producto y cantidad específicos
     * @param producto Producto a asociar al ítem
     * @param cantidad Cantidad del producto
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Métodos getters y setters

    /**
     * Establece el producto asociado a este ítem
     * @param producto Producto a establecer
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece la cantidad del producto en este ítem
     * @param cantidad Nueva cantidad (debe ser un valor positivo)
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return Producto asociado a este ítem
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @return Cantidad del producto en este ítem
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal para este ítem (precio del producto * cantidad)
     * @return Valor del subtotal para este ítem
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Representación en String del ítem del carrito
     * @return String con formato: "Producto x cantidad = $subtotal"
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}

