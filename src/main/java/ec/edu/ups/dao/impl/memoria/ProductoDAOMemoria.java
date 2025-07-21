package ec.edu.ups.dao.impl.memoria;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.ProductoDAO} que
 * gestiona los objetos {@link ec.edu.ups.modelo.Producto} completamente en memoria RAM.
 * <p>
 * Esta clase inicializa un conjunto predefinido de productos al ser instanciada.
 * Los datos se almacenan en una lista en memoria y se perderán al finalizar la
 * ejecución de la aplicación. Es adecuada para propósitos de pruebas, desarrollo
 * rápido o aplicaciones de corta duración donde la persistencia no es requerida.
 * </p>
 *
 * @author [Tu Nombre/El nombre del equipo]
 * @version 1.0
 * @since 2025-07-18
 * @see ec.edu.ups.dao.ProductoDAO
 * @see ec.edu.ups.modelo.Producto
 */
public class ProductoDAOMemoria implements ProductoDAO {

    /**
     * Lista en memoria que almacena todos los objetos {@link Producto}.
     */
    private List<Producto> productos;

    /**
     * Constructor de la clase `ProductoDAOMemoria`.
     * Inicializa la lista de productos como un nuevo {@link ArrayList} y
     * luego carga un conjunto predefinido de productos.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
        inicializarProductos();
    }

    /**
     * Inicializa la lista de productos con un conjunto predefinido de objetos {@link Producto}.
     * Este método se llama durante la construcción del objeto para poblar los datos iniciales.
     */
    private void inicializarProductos() {
        productos.add(new Producto(1, "Pantalla", 250.00));
        productos.add(new Producto(2, "Mouse", 15.00));
        productos.add(new Producto(3, "Teclado", 20.00));
        productos.add(new Producto(4, "Laptop", 850.00));
        productos.add(new Producto(5, "Impresora", 120.00));
        productos.add(new Producto(6, "Parlantes", 35.00));
        productos.add(new Producto(7, "Webcam", 40.00));
        productos.add(new Producto(8, "Micrófono", 30.00));
        productos.add(new Producto(9, "Router", 60.00));
        productos.add(new Producto(10, "Disco Duro", 100.00));
    }

    /**
     * Crea un nuevo {@link Producto} añadiéndolo a la lista en memoria.
     * Si el producto proporcionado es nulo, se imprime un mensaje de error y no se añade.
     *
     * @param producto El objeto {@link Producto} a ser creado.
     */
    @Override
    public void crear(Producto producto) {
        if (producto == null) {
            System.err.println("Error: No se puede crear un producto nulo.");
            return;
        }
        productos.add(producto);
    }

    /**
     * Busca y devuelve un {@link Producto} por su código único.
     *
     * @param codigo El código entero del producto a buscar.
     * @return El objeto {@link Producto} si se encuentra, o `null` si no existe un producto con ese código.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
        /* Equivalente usando Streams (más conciso):
        return productos.stream()
                .filter(producto -> producto.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
        */
    }

    /**
     * Busca y devuelve una lista de {@link Producto} cuyo nombre comienza con la
     * cadena de búsqueda (sensible a mayúsculas y minúsculas por `startsWith`).
     * Si la cadena de búsqueda es nula o vacía, devuelve una lista vacía.
     *
     * @param nombre El prefijo del nombre del producto a buscar.
     * @return Una {@link List} de {@link Producto} que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        if (nombre == null || nombre.isEmpty()) {
            return productosEncontrados; // Retorna una lista vacía si el nombre de búsqueda es nulo o vacío
        }
        for (Producto producto : productos) {
            if (producto.getNombre() != null && producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
        /* Equivalente usando Streams (más conciso y opcionalmente case-insensitive):
        if (nombre == null) return new ArrayList<>();
        String nombreFinal = nombre; // Para usar en la lambda
        return productos.stream()
                .filter(producto -> producto.getNombre() != null &&
                                    producto.getNombre().toLowerCase().startsWith(nombreFinal.toLowerCase())) // Para insensible a mayúsculas
                .collect(Collectors.toList());
        */
    }

    /**
     * Actualiza la información de un {@link Producto} existente en la memoria.
     * Busca el producto por su código y lo reemplaza en la lista.
     * Si el producto proporcionado es nulo o no se encuentra, se imprime un mensaje informativo.
     *
     * @param producto El objeto {@link Producto} con la información actualizada. Su código se utiliza para identificarlo.
     */
    @Override
    public void actualizar(Producto producto) {
        if (producto == null) {
            System.err.println("Error: No se puede actualizar un producto nulo.");
            return;
        }
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto); // Reemplaza el objeto Producto existente
                return; // Producto encontrado y actualizado, sale del método
            }
        }
        System.out.println("Información: Producto con código " + producto.getCodigo() + " no encontrado para actualizar.");
    }

    /**
     * Elimina un {@link Producto} de la lista en memoria por su código.
     * Utiliza un {@link Iterator} para evitar `ConcurrentModificationException`
     * al eliminar elementos mientras se itera. Si el producto no se encuentra,
     * se imprime un mensaje informativo.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        boolean removido = false;
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove(); // Elimina el producto de forma segura
                removido = true;
                // Si solo se espera un producto por código, se puede añadir un break aquí:
                break; // Se añade el break para mejorar eficiencia si el código es único.
            }
        }
        if (!removido) {
            System.out.println("Información: Producto con código " + codigo + " no encontrado para eliminar.");
        }
    }

    /**
     * Lista todos los {@link Producto}s disponibles actualmente en la memoria.
     *
     * @return Una {@link List} que contiene todos los productos.
     * Se devuelve una nueva {@link ArrayList} para proteger la lista interna
     * de modificaciones externas no deseadas.
     */
    @Override
    public List<Producto> listarTodos() {
        // Se devuelve una nueva ArrayList para evitar que modificaciones externas
        // afecten directamente la lista interna de la DAO.
        return new ArrayList<>(productos);
    }
}