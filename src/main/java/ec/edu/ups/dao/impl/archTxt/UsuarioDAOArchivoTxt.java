package ec.edu.ups.dao.impl.archTxt;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.FechaException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.UsuarioDAO} que
 * gestiona la persistencia de los objetos {@link ec.edu.ups.modelo.Usuario}
 * utilizando un archivo de texto plano.
 * <p>
 * Los usuarios se almacenan en un archivo de texto plano donde cada línea
 * representa un usuario y los campos están delimitados por el carácter '|'.
 * </p>
 * <p>
 * Esta clase carga todos los usuarios en memoria al inicio de la aplicación
 * y persiste los cambios de vuelta al archivo después de cada operación
 * de modificación (creación, eliminación o actualización).
 * </p>
 */
public class UsuarioDAOArchivoTxt implements UsuarioDAO {

    /**
     * La ruta del archivo de texto donde se almacenarán los datos de los usuarios.
     */
    private String rutaArchivo;

    /**
     * Lista en memoria que contiene todos los objetos {@link Usuario} cargados desde el archivo.
     */
    private List<Usuario> usuarios;

    /**
     * Constructor de la clase `UsuarioDAOArchivoTxt`.
     * Inicializa la ruta del archivo, la lista de usuarios y carga los usuarios
     * existentes desde el archivo al momento de la instanciación.
     *
     * @param rutaArchivo La ruta completa al archivo de texto que se usará para la persistencia.
     */
    public UsuarioDAOArchivoTxt(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    /**
     * Verifica si el archivo de datos de usuarios existe en la ruta especificada.
     * Si no existe, intenta crear el archivo y sus directorios padres si son necesarios.
     * Imprime un mensaje de error en caso de que la creación del archivo falle.
     */
    private void crearSiNoExiste() {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                // Crea los directorios padres si no existen
                File parentDir = archivo.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                archivo.createNewFile(); // Crea el archivo
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + rutaArchivo);
            e.printStackTrace();
        }
    }

    /**
     * Autentica un usuario verificando el nombre de usuario y la contraseña.
     * Itera sobre la lista de usuarios en memoria para encontrar una coincidencia.
     *
     * @param username    El nombre de usuario para la autenticación.
     * @param contrasenia La contraseña para la autenticación.
     * @return El objeto {@link Usuario} si la autenticación es exitosa, de lo contrario, `null`.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username.trim()) &&
                    usuario.getContrasenia().trim().equals(contrasenia.trim())) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo {@link Usuario} en el sistema.
     * Antes de añadir el usuario, verifica si ya existe un usuario con el mismo
     * nombre de usuario. Si no existe, lo añade a la lista en memoria y guarda
     * la lista actualizada en el archivo.
     *
     * @param usuario El objeto {@link Usuario} a ser creado.
     */
    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsername(usuario.getUsername()) == null) {
            usuarios.add(usuario);
            guardarUsuarios();
        }
    }

    /**
     * Busca y recupera un {@link Usuario} por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return El objeto {@link Usuario} si se encuentra, de lo contrario, `null`.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un {@link Usuario} del sistema utilizando su nombre de usuario.
     * Si el usuario es encontrado, se remueve de la lista en memoria y
     * la lista actualizada se guarda en el archivo.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null) {
            usuarios.remove(usuario);
            guardarUsuarios();
        }
    }

    /**
     * Actualiza la información de un {@link Usuario} existente.
     * Busca el usuario por su nombre de usuario. Si lo encuentra,
     * reemplaza sus datos en la lista en memoria con los datos del
     * objeto {@link Usuario} proporcionado y guarda la lista actualizada en el archivo.
     *
     * @param usuario El objeto {@link Usuario} con la información actualizada.
     */
    @Override
    public void actualizar(Usuario usuario) {
        int index = usuarios.indexOf(buscarPorUsername(usuario.getUsername()));
        if (index != -1) {
            usuarios.set(index, usuario);
            guardarUsuarios();
        }
    }

    /**
     * Recupera una lista de todos los {@link Usuario}s almacenados en el sistema.
     * Se devuelve una nueva {@link ArrayList} para evitar modificaciones directas
     * de la lista interna.
     *
     * @return Una {@link List} que contiene todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Recupera una lista de {@link Usuario}s que tienen un rol específico.
     * <p>
     * Actualmente, esta implementación devuelve una lista inmutable vacía.
     * Este método debería ser implementado para filtrar usuarios por su rol.
     * </p>
     *
     * @param rol El {@link Rol} por el cual se filtrarán los usuarios.
     * @return Una {@link List} de {@link Usuario}s que coinciden con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosPorRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() != null && usuario.getRol().equals(rol)) {
                usuariosPorRol.add(usuario);
            }
        }
        return usuariosPorRol;
    }

    /**
     * Carga los datos de los usuarios desde el archivo de texto especificado por `rutaArchivo`
     * a la lista en memoria (`usuarios`).
     * <p>
     * Se espera que cada línea del archivo contenga los datos de un usuario
     * en el siguiente formato, delimitados por '|':
     * Cédula|NombreCompleto|FechaNacimiento(dd/MM/yyyy)|Teléfono|Correo|Username|Contraseña|Rol
     * </p>
     * Si ocurre un error durante la lectura del archivo o el parseo de la fecha,
     * se imprime un mensaje de error y se salta la línea problemática.
     */
    private void cargarUsuarios() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 8) {
                    Usuario usuario = new Usuario();
                    usuario.setCedula(datos[0]);
                    usuario.setNombreCompleto(datos[1]);
                    // Validar y setear la fecha de nacimiento
                    try {
                        usuario.validarFecha(datos[2]);
                    } catch (FechaException e) {
                        System.err.println("Error al validar la fecha para el usuario en línea: '" + linea + "'. Mensaje: " + e.getMessage());
                        continue;
                    }
                    usuario.setTelefono(datos[3]);
                    usuario.setCorreo(datos[4]);
                    usuario.setUsername(datos[5]);
                    usuario.setContrasenia(datos[6]);
                    try {
                        usuario.setRol(Rol.valueOf(datos[7]));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Rol inválido para el usuario en línea: '" + linea + "'. Rol recibido: '" + datos[7] + "'. Mensaje: " + e.getMessage());
                        continue;
                    }
                    usuarios.add(usuario);
                    System.out.println("Usuario cargado: " + usuario.getUsername()); // Mensaje de depuración
                } else {
                    System.err.println("Advertencia: Línea de usuario con formato incorrecto ignorada: '" + linea + "'");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Información: El archivo de usuarios no fue encontrado. Se creará uno nuevo al guardar.");
        } catch (IOException e) {
            System.err.println("Error de E/S al cargar usuarios desde el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Guarda la lista actual de {@link Usuario}s en memoria en el archivo de texto.
     * <p>
     * Cada usuario se escribe en una nueva línea con sus propiedades delimitadas por '|'.
     * El formato de guardado es:
     * Cédula|NombreCompleto|FechaNacimiento(como String)|Teléfono|Correo|Username|Contraseña|Rol
     * </p>
     * Si ocurre un error de E/S durante la escritura, imprime la traza de la pila.
     */
    private void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                String fechaNacimientoStr = (usuario.getFechaNacimiento() != null) ? usuario.getFechaNacimiento().toString() : "";

                pw.println(usuario.getCedula() + "|" +
                        usuario.getNombreCompleto() + "|" +
                        fechaNacimientoStr + "|" +
                        usuario.getTelefono() + "|" +
                        usuario.getCorreo() + "|" +
                        usuario.getUsername() + "|" +
                        usuario.getContrasenia() + "|" +
                        usuario.getRol());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios en el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}