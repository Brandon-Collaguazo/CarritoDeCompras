package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Modelo Carrito
 * Clase que representa un carrito de compras de un usuario.
 * Permite agregar, eliminar, y modificar productos, así como calcular
 * los valores totales de la compra incluyendo subtotal, IVA y total
 */
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;
    private final double IVA = 0.12;
    private static int contador = 1;
    private int codigo;
    private Usuario usuario;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;

    /**
     * Constructor por defecto que inicializa un carrito vacío
     * con fecha de creación actual y código autoincremental
     */
    public Carrito() {
        this.codigo = contador++;
        this.usuario = usuario;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    /**
     * Constructor que crea un carrito asociado a un usuario específico
     * @param usuario Usuario dueño del carrito
     */
    public Carrito(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    // Métodos Geters y setters

    /**
     * @return Valor del IVA aplicado
     */
    public double getIVA() {
        return IVA;
    }

    /**
     * @return Código del carrito
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @return Valor actual del contador estático
     */
    public static int getContador() {
        return contador;
    }

    /**
     * Establece el valor del contador estático
     * @param contador Nuevo valor para el contador
     */
    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    /**
     * Establece el código del carrito
     * @param codigo Nuevo código para el carrito
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return Fecha de creación del carrito
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del carrito
     * @param fechaCreacion Nueva fecha de creación
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return Usuario asociado al carrito
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado al carrito
     * @param usuario Nuevo usuario para el carrito
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return Lista de Items en el carrito
     */
    public List<ItemCarrito> getItems() {
        return items;
    }

    /**
     * Establece la lista de items del carrito
     * @param items Nueva lista de items
     */
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    // Métodos de funcionalidad del carrito

    /**
     * Agrega un producto al carrito con la cantidad especificada
     * @param producto Producto a agregar
     * @param cantidad Cantidad del producto
     */
    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito según su código
     * @param codigoProducto Código del producto a eliminar
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Vacía completamente el carrito eliminando todos los items
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * @return Lista de items en el carrito
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío
     * @return true si no hay items, false en caso contrario
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el subtotal de la compra (suma de precios sin IVA)
     * @return Valor del subtotal
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /**
     * Calcula el valor del IVA para la compra actual
     * @return Valor del IVA
     */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    /**
     * Calcula el total a pagar (subtotal + IVA)
     * @return Valor total de la compra
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Modifica la cantidad de un producto en el carrito
     * @param codigoProducto Código del producto a modificar
     * @param nuevaCantidad Nueva cantidad para el producto
     */
    public void modificarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    /**
     * Verifica si un producto está en el carrito
     * @param codigoProducto Código del producto a buscar
     * @return true si el producto está en el carrito, false en caso contrario
     */
    public boolean contieneProducto(int codigoProducto) {
        return items.stream().anyMatch(item ->
                item.getProducto().getCodigo() == codigoProducto
        );
    }

    /**
     * Busca un item en el carrito por el código de producto
     * @param codigo Código del producto a buscar
     * @return ItemCarrito encontrado o null si no existe
     */
    public ItemCarrito buscarItemPorCodigo(int codigo) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigo) {
                return item;
            }
        }
        return null;
    }

    /**
     * Representación en String del carrito
     * @return String con los datos del carrito
     */
    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", Código: " + codigo +
                ", Usuario: " + usuario +
                ", Fecha de Creación: " + fechaCreacion +
                ", Items: " + items +
                '}';
    }
}