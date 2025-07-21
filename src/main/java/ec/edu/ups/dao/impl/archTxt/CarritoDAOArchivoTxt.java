package ec.edu.ups.dao.impl.archTxt;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.UsuarioDAO; // Importar UsuarioDAO
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.CarritoDAO} que
 * persiste los datos de los {@link ec.edu.ups.modelo.Carrito} en un archivo de texto plano.
 * <p>
 * Cada carrito se almacena como un bloque de múltiples líneas en el archivo,
 * delimitado por un marcador de fin de carrito ("---FIN_CARRITO---").
 * Esta implementación requiere una instancia de {@link UsuarioDAO} para
 * asociar los carritos con objetos {@link Usuario} completos al leerlos del archivo.
 * </p>
 */
public class CarritoDAOArchivoTxt implements CarritoDAO {

    private String ruta;
    private static final String NOMBRE_ARCHIVO_CARRITOS = "carritos.txt";
    private UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase `CarritoDAOArchivoTxt`.
     * Inicializa la ruta del directorio de almacenamiento y la dependencia {@link UsuarioDAO}.
     * También se asegura de que el archivo de carritos exista o lo crea si es necesario.
     *
     * @param ruta       La ruta del directorio base donde se guardará el archivo `carritos.txt`.
     * @param usuarioDAO La implementación de {@link UsuarioDAO} que se utilizará para
     * obtener los detalles completos de los usuarios asociados a los carritos.
     */
    public CarritoDAOArchivoTxt(String ruta, UsuarioDAO usuarioDAO) {
        // Asegurarse de que la ruta termina con el separador de archivos del sistema operativo
        this.ruta = ruta.endsWith(File.separator) ? ruta : ruta + File.separator;
        this.usuarioDAO = usuarioDAO; // Asignar la instancia de UsuarioDAO inyectada
        crearArchivoSiNoExiste();
    }

    /**
     * Verifica si el archivo de datos de carritos existe en la ruta especificada.
     * Si no existe, intenta crear el archivo y sus directorios padres si son necesarios.
     * Imprime un mensaje de error en caso de que la creación del archivo falle.
     */
    private void crearArchivoSiNoExiste() {
        try {
            File archivo = new File(ruta + NOMBRE_ARCHIVO_CARRITOS);
            if (!archivo.exists()) {
                // Crea los directorios padres si no existen
                File parentDir = archivo.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                archivo.createNewFile(); // Crea el archivo carritos.txt
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de carritos: " + ruta + NOMBRE_ARCHIVO_CARRITOS);
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo {@link Carrito} persistiendo sus datos en el archivo de texto.
     * El carrito se añade al final del archivo.
     *
     * @param carrito El objeto {@link Carrito} a ser creado y guardado.
     */
    @Override
    public void crear(Carrito carrito) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta + NOMBRE_ARCHIVO_CARRITOS, true))) {
            writer.write(carritoToString(carrito));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca y recupera un {@link Carrito} por su código único.
     * Lee todos los carritos del archivo y los compara por su código.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@link Carrito} si se encuentra, de lo contrario, `null`.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        List<Carrito> carritos = listarTodos();
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Busca y recupera una lista de {@link Carrito}s asociados a un {@link Usuario} específico.
     * Este método delega la búsqueda a {@link #listarPorUsuario(String)}.
     *
     * @param usuario El objeto {@link Usuario} cuyos carritos se desean buscar.
     * @return Una {@link List} de {@link Carrito}s asociados al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return listarPorUsuario(usuario.getUsername());
    }

    /**
     * Actualiza la información de un {@link Carrito} existente en el archivo.
     * Recrea completamente el archivo con todos los carritos, aplicando el cambio
     * al carrito que coincide con el código.
     *
     * @param carrito El objeto {@link Carrito} con la información actualizada.
     */
    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta + NOMBRE_ARCHIVO_CARRITOS))) {
            for (Carrito c : carritos) {
                if (c.getCodigo() == carrito.getCodigo()) {
                    writer.write(carritoToString(carrito));
                } else {
                    writer.write(carritoToString(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un {@link Carrito} del archivo utilizando su código.
     * Recrea el archivo excluyendo el carrito con el código especificado.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta + NOMBRE_ARCHIVO_CARRITOS))) { // Sobreescribe el archivo
            for (Carrito c : carritos) {
                if (c.getCodigo() != codigo) {
                    writer.write(carritoToString(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera una lista de todos los {@link Carrito}s almacenados en el archivo.
     * Este método lee el archivo línea por línea, agrupando las líneas que pertenecen
     * a un solo carrito utilizando el marcador "---FIN_CARRITO---".
     *
     * @return Una {@link List} que contiene todos los carritos leídos del archivo.
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta + NOMBRE_ARCHIVO_CARRITOS))) {
            String line;
            StringBuilder carritoEnConstruccion = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                carritoEnConstruccion.append(line).append("\n");
                if (line.trim().equals("---FIN_CARRITO---")) {
                    Carrito carrito = stringToCarrito(carritoEnConstruccion.toString());
                    if (carrito != null) {
                        carritos.add(carrito);
                    }
                    carritoEnConstruccion.setLength(0);
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return carritos;
    }

    /**
     * Recupera una lista de {@link Carrito}s asociados a un nombre de usuario específico.
     * Filtra la lista completa de carritos para encontrar aquellos cuyo usuario coincide con el `username` dado.
     *
     * @param username El nombre de usuario del {@link Usuario} cuyos carritos se desean obtener.
     * @return Una {@link List} de {@link Carrito}s pertenecientes al usuario especificado.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> carritos = listarTodos();
        List<Carrito> carritosPorUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getUsername() != null && carrito.getUsuario().getUsername().equals(username)) {
                carritosPorUsuario.add(carrito);
            }
        }
        return carritosPorUsuario;
    }

    /**
     * Convierte un objeto {@link Carrito} a su representación en formato de texto multilinea.
     * Este formato es el que se escribe en el archivo. Incluye un marcador de fin de carrito
     * para facilitar la lectura.
     *
     * @param carrito El objeto {@link Carrito} a convertir.
     * @return Una {@link String} que representa el carrito en formato de texto.
     */
    private String carritoToString(Carrito carrito) {
        SimpleDateFormat sDf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaDf = sDf.format(carrito.getFechaCreacion().getTime());
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(carrito.getCodigo()).append("\n")
                .append("Usuario: ").append(carrito.getUsuario().getUsername()).append("\n")
                .append("Fecha: ").append(fechaDf).append("\n")
                .append("Número de Ítems: ").append(carrito.getItems().size()).append("\n")
                .append("Items:\n");

        for (ItemCarrito item : carrito.getItems()) {
            sb.append("- Producto Código: ").append(item.getProducto().getCodigo())
                    .append(", Cantidad: ").append(item.getCantidad()).append("\n");
        }
        sb.append("---FIN_CARRITO---\n"); // Marcador para delimitar el final de cada carrito
        return sb.toString();
    }

    /**
     * Convierte un bloque de texto (múltiples líneas) que representa un carrito
     * a un objeto {@link Carrito}.
     * Extrae los datos del código, usuario, fecha e ítems del carrito del bloque de texto.
     * Utiliza la instancia de {@link UsuarioDAO} inyectada para obtener el objeto {@link Usuario} completo.
     *
     * @param bloqueDeTextoCarrito El {@link String} que contiene todas las líneas de datos de un carrito.
     * @return El objeto {@link Carrito} reconstruido a partir del texto, o `null` si hay un error crítico.
     */
    private Carrito stringToCarrito(String bloqueDeTextoCarrito) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Carrito carrito = new Carrito();
        String usernameLeido = null;
        List<ItemCarrito> items = new ArrayList<>();

        String[] lineas = bloqueDeTextoCarrito.split("\n");

        for (String linea : lineas) {
            if (linea.startsWith("Código: ")) {
                try {
                    carrito.setCodigo(Integer.parseInt(linea.substring("Código: ".length()).trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato al parsear código de carrito: " + linea);
                    e.printStackTrace();
                }
            } else if (linea.startsWith("Usuario: ")) {
                usernameLeido = linea.substring("Usuario: ".length()).trim();
            } else if (linea.startsWith("Fecha: ")) {
                try {
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(sdf.parse(linea.substring("Fecha: ".length()).trim()));
                    carrito.setFechaCreacion(gc);
                } catch (ParseException e) {
                    System.err.println("Error de formato al parsear fecha de carrito: " + linea);
                    e.printStackTrace();
                }
            } else if (linea.startsWith("- Producto Código: ")) {
                try {
                    String itemData = linea.substring("- Producto Código: ".length()).trim();
                    String[] partesItem = itemData.split(", Cantidad: ");
                    if (partesItem.length == 2) {
                        int codigoProducto = Integer.parseInt(partesItem[0].trim());
                        int cantidad = Integer.parseInt(partesItem[1].trim());

                        Producto producto = new Producto();
                        producto.setCodigo(codigoProducto);

                        ItemCarrito item = new ItemCarrito();
                        item.setProducto(producto);
                        item.setCantidad(cantidad);
                        items.add(item);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error de formato al parsear ítem de carrito: " + linea);
                    e.printStackTrace();
                }
            }
        }
        carrito.setItems(items);

        if (usernameLeido != null && usuarioDAO != null) {
            Usuario usuarioCompleto = usuarioDAO.buscarPorUsername(usernameLeido);
            if (usuarioCompleto != null) {
                carrito.setUsuario(usuarioCompleto);
            } else {
                System.err.println("Advertencia: Usuario '" + usernameLeido + "' no encontrado en el sistema para el carrito con código " + carrito.getCodigo());
                Usuario usuarioPlaceholder = new Usuario();
                usuarioPlaceholder.setUsername(usernameLeido + " (no encontrado)");
                carrito.setUsuario(usuarioPlaceholder);
            }
        } else {
            System.err.println("Advertencia: No se pudo obtener el usuario para el carrito con código " + carrito.getCodigo() + ". Username: " + usernameLeido + ". UsuarioDAO: " + (usuarioDAO == null ? "nulo" : "disponible"));
            Usuario usuarioVacio = new Usuario();
            usuarioVacio.setUsername(usernameLeido != null ? usernameLeido + " (fallido/desconocido)" : "Desconocido");
            carrito.setUsuario(usuarioVacio);
        }

        return carrito;
    }
}