package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.CarritoDAO} que
 * persiste los datos de los {@link ec.edu.ups.modelo.Carrito} en un archivo de texto plano
 * con formato CSV (Comma Separated Values).
 * <p>
 * Los carritos se almacenan en el archivo "carritos.txt" dentro de la ruta especificada.
 * Cada línea del archivo representa un carrito, y los ítems del carrito se serializan
 * dentro de la misma línea, separados por punto y coma.
 * </p>
 * <p>
 * Esta implementación se encarga de cargar los carritos al inicio y guardarlos
 * después de cada operación de modificación (crear, actualizar, eliminar).
 * </p>
 *
 */
public class CarritoDAOArchivoTxt implements CarritoDAO {

    /**
     * Ruta del directorio donde se almacenará el archivo de carritos.
     */
    private final String ruta;

    /**
     * Lista en memoria que contiene todos los objetos Carrito cargados desde el archivo.
     */
    private final List<Carrito> carritos = new ArrayList<>();

    /**
     * Constructor de la clase `CarritoDAOArchivoTxt`.
     * Inicializa la ruta del archivo, verifica y crea el directorio y el archivo
     * si no existen, y carga los carritos existentes en memoria.
     *
     * @param ruta La ruta del directorio donde se guardará el archivo `carritos.txt`.
     */
    public CarritoDAOArchivoTxt(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        // Crea la carpeta si no existe
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/carritos.txt");
        // Crea el archivo de carritos si no existe
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de carritos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        cargarCarritos(); // Carga los carritos existentes al iniciar la DAO
    }

    /**
     * Carga los carritos desde el archivo de texto "carritos.txt" a la lista en memoria.
     * Cada línea del archivo se parsea como un objeto {@link Carrito}.
     * Si el archivo no existe o está vacío, no se carga nada.
     */
    private void cargarCarritos() {
        carritos.clear(); // Limpia la lista actual antes de cargar
        File archivo = new File(ruta + "/carritos.txt");
        if (!archivo.exists() || archivo.length() == 0) {
            return; // No hay nada que cargar
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Carrito carrito = csvToCarrito(linea);
                if (carrito != null) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar carritos desde archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de carritos en memoria en el archivo "carritos.txt".
     * Cada carrito se convierte a una cadena CSV antes de ser escrito en una nueva línea.
     */
    private void guardarCarritos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta + "/carritos.txt"))) {
            for (Carrito carrito : carritos) {
                writer.println(carritoToCSV(carrito));
            }
        } catch (IOException e) {
            System.err.println("Error al guardar carritos en archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto {@link Carrito} a una cadena en formato CSV para su almacenamiento.
     * El formato es: `codigo,username_usuario,fechaCreacionMillis,codigoProducto1:cantidad1;codigoProducto2:cantidad2;...`
     *
     * @param carrito El objeto Carrito a convertir.
     * @return Una cadena que representa el carrito en formato CSV.
     */
    private String carritoToCSV(Carrito carrito) {
        StringBuilder sb = new StringBuilder();
        sb.append(carrito.getCodigo()).append(",");
        // Maneja el caso en que el usuario sea nulo, aunque lógicamente no debería serlo para un carrito válido
        sb.append(carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "").append(",");
        sb.append(carrito.getFechaCreacion().getTimeInMillis()).append(","); // Guarda la fecha como milisegundos

        // Serializa los ítems del carrito
        for (ItemCarrito item : carrito.obtenerItems()) {
            sb.append(item.getProducto().getCodigo()).append(":").append(item.getCantidad()).append(";");
        }
        return sb.toString();
    }

    /**
     * Convierte una cadena CSV a un objeto {@link Carrito}.
     * Reconstruye el objeto Carrito y sus ítems a partir de la cadena.
     * <p>
     * Nota: Para los productos dentro de los ítems, solo se establece el código y un nombre/precio por defecto
     * ya que la información completa del producto debería ser recuperada de una DAO de Producto.
     * </p>
     *
     * @param csv La cadena CSV que representa un carrito.
     * @return Un objeto Carrito reconstruido a partir de la cadena, o `null` si la cadena no es válida.
     */
    private Carrito csvToCarrito(String csv) {
        String[] partes = csv.split(",", 4); // Divide en 4 partes: codigo, username, fecha, items
        if (partes.length < 4) return null; // Validación básica del formato

        int codigo = Integer.parseInt(partes[0]);
        String username = partes[1];
        long fechaMillis = Long.parseLong(partes[2]);

        Carrito carrito = new Carrito();
        carrito.setCodigo(codigo);

        // Se crea un objeto Usuario placeholder solo con el username.
        // La información completa del usuario debería ser cargada por una DAO de Usuario si es necesaria.
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        carrito.setUsuario(usuario);

        GregorianCalendar fechaCreacion = new GregorianCalendar();
        fechaCreacion.setTimeInMillis(fechaMillis);
        carrito.setFechaCreacion(fechaCreacion);

        String itemsStr = partes[3];
        String[] itemsArr = itemsStr.split(";"); // Divide la cadena de ítems

        for (String itemStr : itemsArr) {
            if (itemStr.isEmpty()) continue; // Ignora cadenas vacías resultantes de un split
            String[] itemPartes = itemStr.split(":"); // Divide cada ítem en código de producto y cantidad
            if (itemPartes.length < 2) continue; // Validación de formato de ítem

            int prodCodigo = Integer.parseInt(itemPartes[0]);
            int cantidad = Integer.parseInt(itemPartes[1]);

            // Se crea un objeto Producto placeholder solo con el código.
            // La información completa (nombre, precio) debería ser cargada por una DAO de Producto.
            Producto producto = new Producto(prodCodigo, "Producto Desconocido", 0.0);
            carrito.agregarProducto(producto, cantidad); // Agrega el producto al carrito
        }
        return carrito;
    }

    /**
     * Crea un nuevo {@link Carrito} y lo guarda en el almacenamiento.
     * Después de añadirlo a la lista en memoria, la lista se persiste en el archivo.
     *
     * @param carrito El objeto Carrito a crear.
     */
    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritos(); // Guarda los cambios en el archivo
    }

    /**
     * Busca un {@link Carrito} por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto Carrito si se encuentra, o `null` si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve una lista de {@link Carrito} asociados a un {@link Usuario} específico.
     * La comparación se realiza por la cédula del usuario.
     *
     * @param usuario El objeto Usuario por el cual buscar los carritos.
     * @return Una {@link List} de Carrito que pertenecen al usuario especificado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> encontrados = new ArrayList<>();
        // Asumiendo que el usuario dentro del Carrito tiene la cédula cargada para la comparación
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getCedula() != null &&
                    carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                encontrados.add(carrito);
            }
        }
        return encontrados;
    }

    /**
     * Actualiza la información de un {@link Carrito} existente.
     * Busca el carrito por su código y lo reemplaza en la lista en memoria.
     * Luego, la lista actualizada se persiste en el archivo.
     *
     * @param carrito El objeto Carrito con la información actualizada.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito); // Reemplaza el carrito existente
                guardarCarritos(); // Guarda los cambios en el archivo
                return; // Carrito encontrado y actualizado
            }
        }
    }

    /**
     * Elimina un {@link Carrito} del almacenamiento por su código.
     * La eliminación se realiza de la lista en memoria y luego se persiste el cambio en el archivo.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(carrito -> carrito.getCodigo() == codigo); // Elimina el carrito por su código
        guardarCarritos(); // Guarda los cambios en el archivo
    }

    /**
     * Lista todos los {@link Carrito}s disponibles en el almacenamiento.
     *
     * @return Una {@link List} que contiene todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos); // Devuelve una copia para evitar modificaciones externas
    }

    /**
     * Este método no está implementado para la persistencia por archivo de texto basada solo en username
     * sin cargar la cédula completa del usuario.
     * En una implementación real, sería necesario cargar el usuario completo para comparar por username
     * o modificar el formato de guardado del CSV para incluirlo.
     * <p>
     * Se devuelve una lista vacía como placeholder.
     * </p>
     *
     * @param username El nombre de usuario por el cual listar los carritos.
     * @return Una {@link List} vacía, ya que la funcionalidad no está completa para este DAO.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        // Implementación pendiente o a revisar según el requisito de datos en CSV
        // Si el CSV no guarda la cédula, sería necesario buscar el usuario completo por username
        // en otra DAO (ej. UsuarioDAO) y luego usar su cédula para buscar carritos.
        return List.of(); // Devuelve una lista inmutable vacía.
    }
}