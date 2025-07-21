package ec.edu.ups.dao;

import ec.edu.ups.dao.impl.archTxt.CarritoDAOArchivoTxt;
import ec.edu.ups.dao.impl.archTxt.PreguntaDAOArchivoTxt;
import ec.edu.ups.dao.impl.archTxt.ProductoDAOArchivoTxt;
import ec.edu.ups.dao.impl.archTxt.UsuarioDAOArchivoTxt;
import ec.edu.ups.dao.impl.memoria.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.memoria.PreguntaSeguridadDAOMemoria;
import ec.edu.ups.dao.impl.memoria.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.memoria.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import java.io.File;
import java.util.Date;

/**
 * La clase `ManagerDAO` se encarga de gestionar la inicialización y el acceso
 * a las diferentes implementaciones de los objetos de acceso a datos (DAO)
 * de la aplicación. Permite configurar si los datos se almacenarán en memoria
 * o en archivos de texto, y proporciona los DAOs correspondientes.
 *
 * Esta clase también se encarga de crear usuarios predeterminados (admin y usuario)
 * si no existen al inicializar los DAOs.
 */
public class ManagerDAO {
    /**
     * Objeto DAO para la gestión de usuarios.
     */
    private UsuarioDAO usuarioDAO;
    /**
     * Objeto DAO para la gestión de productos.
     */
    private ProductoDAO productoDAO;
    /**
     * Objeto DAO para la gestión de carritos de compra.
     */
    private CarritoDAO carritoDAO;
    /**
     * Objeto DAO para la gestión de preguntas de seguridad.
     */
    private PreguntaSeguridadDAO preguntaDAO;
    /**
     * Manejador para obtener mensajes internacionalizados.
     */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Clave para el tipo de almacenamiento en memoria, obtenida del manejador de mensajes.
     */
    private static final String ALMACENAMIENTO_MEMORIA = "almacenamiento.memoria.login";
    /**
     * Clave para el tipo de almacenamiento en sistema de archivos (texto), obtenida del manejador de mensajes.
     */
    private static final String ALMACENAMIENTO_SISTEMA = "almacenamiento.memoria.sistema.login";

    /**
     * Constructor de la clase `ManagerDAO`.
     * Inicializa el manejador de mensajes y llama a `inicializarDAOS`
     * para configurar los DAOs por defecto en memoria.
     *
     * @param mensaje El {@link MensajeInternacionalizacionHandler} utilizado para obtener las claves de almacenamiento.
     */
    public ManagerDAO(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        inicializarDAOS(mensaje.get(ALMACENAMIENTO_MEMORIA), null);
    }

    /**
     * Inicializa los objetos DAO basándose en la clave de almacenamiento proporcionada.
     * Soporta almacenamiento en memoria y en archivos de texto. Si se selecciona almacenamiento
     * en archivo, se asegura de que la ruta base exista y construye las rutas completas
     * para cada archivo de datos.
     *
     * @param almacenamientoKey La clave que indica el tipo de almacenamiento a utilizar (ej. "almacenamiento.memoria.login" para memoria,
     * "almacenamiento.memoria.sistema.login" para archivos de texto).
     * @param rutaBase          La ruta base donde se crearán los archivos de datos si se usa almacenamiento en sistema.
     * Puede ser `null` si se usa almacenamiento en memoria.
     */
    public void inicializarDAOS(String almacenamientoKey, String rutaBase) {
        String usuariosRutaArchivo = null;
        String productosRutaArchivo = null;
        String carritosRutaArchivo = null;
        String preguntasRutaArchivo = null;

        if (!almacenamientoKey.equals(mensaje.get(ALMACENAMIENTO_MEMORIA))) {
            if (rutaBase == null || rutaBase.isEmpty()) {
                rutaBase = "data" + File.separator;
            } else if (!rutaBase.endsWith(File.separator)) {
                rutaBase += File.separator;
            }

            File direccionData = new File(rutaBase);
            if (!direccionData.exists()) {
                if (!direccionData.mkdirs()) {
                    System.err.println("Error al crear el directorio: " + rutaBase);
                }
            }

            // Asumiendo que el tipo de archivo (.txt o .bin) también se controla por la clave
            // En este caso, solo .txt para ALMACENAMIENTO_SISTEMA (como se usa en la clase)
            String extensionArchivo = ".txt"; // Forzando a .txt según el uso actual en la clase
            usuariosRutaArchivo = rutaBase + "usuarios" + extensionArchivo;
            productosRutaArchivo = rutaBase + "productos" + extensionArchivo;
            carritosRutaArchivo = rutaBase + "carritos" + extensionArchivo;
            preguntasRutaArchivo = rutaBase + "preguntas" + extensionArchivo;
        }

        if (almacenamientoKey.equals(mensaje.get(ALMACENAMIENTO_MEMORIA))) {
            this.usuarioDAO = new UsuarioDAOMemoria();
            this.productoDAO = new ProductoDAOMemoria();
            this.carritoDAO = new CarritoDAOMemoria();
            this.preguntaDAO = new PreguntaSeguridadDAOMemoria();
        } else if (almacenamientoKey.equals(mensaje.get(ALMACENAMIENTO_SISTEMA))) {
            this.usuarioDAO = new UsuarioDAOArchivoTxt(usuariosRutaArchivo);
            this.productoDAO = new ProductoDAOArchivoTxt(productosRutaArchivo);
            // Se inyecta la dependencia de UsuarioDAO a CarritoDAOArchivoTxt
            this.carritoDAO = new CarritoDAOArchivoTxt(carritosRutaArchivo, this.usuarioDAO);
            this.preguntaDAO = new PreguntaDAOArchivoTxt(preguntasRutaArchivo);
        } else {
            System.err.println("Tipo de almacenamiento desconocido: " + almacenamientoKey + ". Usando memoria.");
            inicializarDAOS(mensaje.get(ALMACENAMIENTO_MEMORIA), null);
        }
        crearUsuarioSiNoExiste();
    }

    /**
     * Crea un usuario "admin" y un usuario "normal" si no existen en el sistema.
     * Esto asegura que siempre haya cuentas básicas disponibles al iniciar la aplicación.
     */
    private void crearUsuarioSiNoExiste() {
        if (usuarioDAO.buscarPorUsername("admin") == null) {
            Usuario admin = new Usuario("0703062885",
                    "Pepe",
                    new Date(),
                    "123456789", "admin@gmail.com",
                    "admin",
                    "12345",
                    Rol.ADMINISTRADOR);
            usuarioDAO.crear(admin);
            System.out.println("Usuario admin creado");
        }

        if (usuarioDAO.buscarPorUsername("usuario") == null) {
            Usuario usuarioNormal = new Usuario("0706780590",
                    "Brandon Rene",
                    new Date(),
                    "0969557675", "brandonc@gmail.com",
                    "usuario",
                    "12345",
                    Rol.USUARIO);
            usuarioDAO.crear(usuarioNormal);
            System.out.println("Usuario normal creado");
        }
    }

    /**
     * Obtiene la instancia del DAO para usuarios.
     *
     * @return El {@link UsuarioDAO} configurado.
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    /**
     * Obtiene la instancia del DAO para productos.
     *
     * @return El {@link ProductoDAO} configurado.
     */
    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    /**
     * Obtiene la instancia del DAO para carritos de compra.
     *
     * @return El {@link CarritoDAO} configurado.
     */
    public CarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    /**
     * Obtiene la instancia del DAO para preguntas de seguridad.
     *
     * @return El {@link PreguntaSeguridadDAO} configurado.
     */
    public PreguntaSeguridadDAO getPreguntaDAO() {
        return preguntaDAO;
    }
}