package ec.edu.ups.dao.impl.archTxt;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.ProductoDAO} que
 * gestiona la persistencia de los objetos {@link ec.edu.ups.modelo.Producto}
 * utilizando un archivo de texto plano con formato delimitado por `|` (pipe).
 * <p>
 * Los productos se almacenan en el archivo `productos.txt` dentro de la ruta especificada.
 * Cada línea del archivo representa un producto.
 * </p>
 * <p>
 * Esta implementación carga todos los productos en memoria al iniciar la DAO
 * y guarda la lista completa en el archivo después de cada operación de modificación
 * (crear, actualizar, eliminar).
 * </p>
 */
public class ProductoDAOArchivoTxt implements ProductoDAO {

    /**
     * La ruta del directorio donde se almacenará el archivo de productos.
     */
    private final String ruta;

    /**
     * Lista en memoria que contiene todos los objetos {@link Producto} cargados desde el archivo.
     */
    private final List<Producto> productos = new ArrayList<>();

    /**
     * Constructor de la clase `ProductoDAOArchivoTxt`.
     * <p>
     * Inicializa la ruta del archivo, verifica y crea el archivo `productos.txt`
     * si no existe, y luego carga todos los productos existentes en memoria.
     * </p>
     *
     * @param ruta La ruta del directorio donde se guardará el archivo `productos.txt`.
     */
    public ProductoDAOArchivoTxt(String ruta) {
        this.ruta = ruta;
        // Asegura que el directorio exista
        File folder = new File(ruta);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File archivo = new File(ruta + "/productos.txt");
        crearArchivoInexistente(archivo);
        cargaProductos();
    }

    /**
     * Crea el archivo especificado si no existe.
     * Si ocurre un error de E/S durante la creación, imprime la traza de la pila.
     *
     * @param archivo El objeto {@link File} que representa el archivo a crear.
     */
    private void crearArchivoInexistente(File archivo) {
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de productos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Carga todos los productos desde el archivo `productos.txt` a la lista en memoria.
     * Cada línea del archivo se espera en formato "Código|Nombre|Precio".
     * Si el archivo no existe o está vacío, la lista de productos se mantendrá vacía.
     */
    private void cargaProductos() {
        productos.clear(); // Limpia la lista actual antes de cargar
        File archivo = new File(ruta + "/productos.txt");
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("Información: El archivo de productos no existe o está vacío. Cargando productos iniciales.");
            inicializarProductos(); // Carga productos iniciales solo si el archivo está vacío
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Producto producto = parseProducto(linea);
                if (producto != null) {
                    productos.add(producto);
                } else {
                    System.err.println("Advertencia: Línea de producto inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar productos desde el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inicializador de Productos
     * Método que inicializa los productos en el archivo
     */
    private void inicializarProductos() {
        System.out.println("Cargando productos");
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
     * Parsea una línea de texto para crear un objeto {@link Producto}.
     * Se espera el formato "Código|Nombre|Precio".
     *
     * @param linea La cadena de texto a parsear.
     * @return Un objeto `Producto` si la línea es válida, o `null` en caso contrario.
     */
    private Producto parseProducto(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 3) {
            try {
                return new Producto(
                        Integer.parseInt(partes[0].trim()),  // Código
                        partes[1].trim(),                   // Nombre
                        Double.parseDouble(partes[2].trim()) // Precio
                );
            } catch (NumberFormatException e) {
                System.err.println("Error de formato numérico al parsear producto: " + linea + " - " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Convierte un objeto {@link Producto} a una cadena en formato delimitado por `|` (pipe)
     * para su almacenamiento en el archivo de texto.
     * El formato es: `codigo|nombre|precio`
     *
     * @param producto El objeto Producto a convertir.
     * @return Una cadena que representa el producto en el formato de archivo.
     */
    private String productoToCSV(Producto producto) {
        return String.join("|",
                String.valueOf(producto.getCodigo()),
                producto.getNombre(),
                String.valueOf(producto.getPrecio())
        );
    }

    /**
     * Crea un nuevo {@link Producto} añadiéndolo a la lista en memoria
     * y luego persistiendo toda la lista en el archivo.
     *
     * @param producto El objeto Producto a crear.
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
     *
     * @param nombre El nombre o parte del nombre del producto a buscar.
     * @return Una {@link List} de Producto que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            return encontrados; // Retorna lista vacía si el nombre de búsqueda es nulo o vacío
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
     * Después de la actualización, la lista completa se persiste en el archivo.
     *
     * @param producto El objeto Producto con la información actualizada. Su código se utiliza para identificarlo.
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
     * La eliminación se realiza de la lista en memoria y luego se persiste el cambio en el archivo.
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

    /**
     * Guarda la lista actual de productos en memoria en el archivo `productos.txt`.
     * Cada producto se convierte a una cadena con el formato delimitado por `|`
     * antes de ser escrito en una nueva línea. El archivo existente es sobrescrito.
     */
    private void guardarProductos() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(ruta + "/productos.txt"))) {
            for (Producto producto : productos) {
                printWriter.println(productoToCSV(producto));
            }
        } catch (IOException e) {
            System.err.println("Error al guardar productos en el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}