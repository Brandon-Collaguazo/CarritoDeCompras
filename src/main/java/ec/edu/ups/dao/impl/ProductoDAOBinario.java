package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Importación para uso potencial con streams

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.ProductoDAO} que
 * gestiona la persistencia de los objetos {@link ec.edu.ups.modelo.Producto}
 * utilizando un archivo binario.
 * <p>
 * Los productos se serializan directamente como objetos Java en el archivo "productos.bin"
 * dentro de la ruta especificada.
 * </p>
 * <p>
 * Para que esta implementación funcione correctamente, la clase {@link ec.edu.ups.modelo.Producto}
 * debe implementar la interfaz {@link java.io.Serializable}.
 * </p>
 * <p>
 * Esta implementación carga todos los productos en memoria al iniciar la DAO
 * y guarda la lista completa en el archivo binario después de cada operación de modificación
 * (crear, actualizar, eliminar).
 * </p>
 *
 * @author [Tu Nombre/El nombre del equipo]
 * @version 1.0
 * @since 2025-07-18
 * @see ec.edu.ups.dao.ProductoDAO
 * @see ec.edu.ups.modelo.Producto
 * @see java.io.Serializable
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 */
public class ProductoDAOBinario implements ProductoDAO {

    /**
     * La ruta del directorio donde se almacenará el archivo binario de productos.
     */
    private final String ruta;

    /**
     * Lista en memoria que contiene todos los objetos {@link Producto} cargados desde el archivo binario.
     */
    private final List<Producto> productos = new ArrayList<>();

    /**
     * Constructor de la clase `ProductoDAOBinario`.
     * <p>
     * Inicializa la ruta del archivo, y luego carga todos los productos existentes
     * desde el archivo binario en la lista en memoria. También asegura que el directorio exista.
     * </p>
     *
     * @param ruta La ruta del directorio donde se guardará el archivo `productos.bin`.
     */
    public ProductoDAOBinario(String ruta) {
        this.ruta = ruta;
        // Asegura que el directorio exista
        File folder = new File(ruta);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        cargarProductos();
    }

    /**
     * Carga todos los objetos {@link Producto} desde el archivo binario "productos.bin"
     * a la lista en memoria (`productos`).
     * <p>
     * Utiliza un `ObjectInputStream` para deserializar los objetos. La lectura continúa
     * hasta que se alcanza el final del archivo (`EOFException`).
     * Si el archivo no existe o está vacío, la lista de productos se mantendrá vacía.
     * </p>
     */
    private void cargarProductos() {
        productos.clear(); // Limpia la lista actual antes de cargar
        File archivo = new File(ruta + "/productos.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("Información: El archivo binario de productos no existe o está vacío. No se cargaron productos.");
            return;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) { // Leer objetos hasta que se lance EOFException
                Producto producto = (Producto) inputStream.readObject();
                productos.add(producto);
            }
        } catch (EOFException e) {
            // Se llega al final del archivo, es un comportamiento esperado al leer objetos serializados.
            System.out.println("Carga de productos finalizada: Fin del archivo alcanzado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar productos desde un archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de productos en memoria en el archivo binario "productos.bin".
     * <p>
     * Utiliza un `ObjectOutputStream` para serializar cada objeto `Producto`.
     * Cada vez que se llama, sobrescribe el contenido del archivo existente.
     * </p>
     */
    private void guardarProductos() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ruta + "/productos.bin"))) {
            for (Producto producto : productos) {
                outputStream.writeObject(producto);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar productos en el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo {@link Producto} añadiéndolo a la lista en memoria
     * y luego persistiendo toda la lista en el archivo binario.
     *
     * @param producto El objeto Producto a crear. No debe ser nulo.
     */
    @Override
    public void crear(Producto producto) {
        if (producto == null) {
            System.err.println("Error: No se puede crear un producto nulo.");
            return;
        }
        productos.add(producto);
        guardarProductos(); // Persiste los cambios
    }

    /**
     * Busca un {@link Producto} por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto si se encuentra, o `null` si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream()
                .filter(producto -> producto.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca y devuelve una lista de {@link Producto} cuyo nombre contenga
     * la cadena de búsqueda (ignorando mayúsculas y minúsculas).
     * <p>
     * Nota: Este método llama a `cargarProductos()` al inicio, lo cual
     * recarga toda la lista desde el archivo. Esto podría ser ineficiente
     * si se llama repetidamente. Si los datos en memoria ya están actualizados,
     * esta llamada podría ser omitida.
     * </p>
     *
     * @param nombre El nombre o parte del nombre del producto a buscar. No debe ser nulo.
     * @return Una {@link List} de Producto que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        // Considerar si es necesario recargar productos aquí en cada llamada,
        // o si la lista 'productos' siempre debe estar actualizada por las operaciones CUD.
        cargarProductos(); // Recarga los productos para asegurar que la lista esté actualizada.

        List<Producto> encontrados = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            return encontrados;
        }
        String nombreLower = nombre.toLowerCase();
        for (Producto producto : productos) {
            if (producto.getNombre() != null && producto.getNombre().toLowerCase().contains(nombreLower)) {
                encontrados.add(producto);
            }
        }
        return encontrados;
        /* Equivalente usando Streams (más conciso):
        String nombreLower = nombre.toLowerCase();
        return productos.stream()
                .filter(producto -> producto.getNombre() != null && producto.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
        */
    }

    /**
     * Actualiza la información de un {@link Producto} existente.
     * Busca el producto por su código y lo reemplaza en la lista en memoria.
     * Después de la actualización, la lista completa se persiste en el archivo binario.
     *
     * @param producto El objeto Producto con la información actualizada. Su código se utiliza para identificarlo.
     * No debe ser nulo.
     */
    @Override
    public void actualizar(Producto producto) {
        if (producto == null) {
            System.err.println("Error: No se puede actualizar un producto nulo.");
            return;
        }
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto); // Reemplaza el producto existente
                guardarProductos(); // Persiste los cambios
                return; // Producto encontrado y actualizado
            }
        }
        System.out.println("Información: Producto con código " + producto.getCodigo() + " no encontrado para actualizar.");
    }

    /**
     * Elimina un {@link Producto} del almacenamiento por su código.
     * La eliminación se realiza de la lista en memoria y luego se persiste el cambio en el archivo binario.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        boolean removido = productos.removeIf(producto -> producto.getCodigo() == codigo);
        if (removido) {
            guardarProductos(); // Guarda los cambios solo si se eliminó un producto
        } else {
            System.out.println("Información: Producto con código " + codigo + " no encontrado para eliminar.");
        }
    }

    /**
     * Lista todos los {@link Producto}s disponibles en el almacenamiento.
     *
     * @return Una nueva {@link List} que contiene todos los productos.
     * Se devuelve una copia para evitar modificaciones externas de la lista interna.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}