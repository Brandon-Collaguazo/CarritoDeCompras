package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.UsuarioDAO} que
 * gestiona la persistencia de los objetos {@link ec.edu.ups.modelo.Usuario}
 * utilizando un archivo binario.
 * <p>
 * Los usuarios se serializan directamente como una colección de objetos Java en el archivo especificado.
 * </p>
 * <p>
 * Para que esta implementación funcione correctamente, la clase {@link ec.edu.ups.modelo.Usuario}
 * debe implementar la interfaz {@link java.io.Serializable}.
 * </p>
 */
public class UsuarioDAOBinario implements UsuarioDAO {

    /**
     * La ruta del archivo binario donde se almacenarán los datos de los usuarios.
     */
    private String rutaArchivo;

    /**
     * Lista en memoria que contiene todos los objetos {@link Usuario} cargados desde el archivo.
     */
    private List<Usuario> usuarios;

    /**
     * Constructor de la clase `UsuarioDAOBinario`.
     * Inicializa la ruta del archivo y la lista de usuarios. Al instanciarse,
     * intenta cargar los usuarios existentes desde el archivo binario.
     *
     * @param rutaArchivo La ruta completa al archivo binario que se usará para la persistencia.
     */
    public UsuarioDAOBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>(); // Inicializa la lista antes de cargar
        cargarUsuarios();
    }

    /**
     * Autentica un usuario buscando una coincidencia de nombre de usuario y contraseña
     * en la lista de usuarios en memoria.
     *
     * @param username    El nombre de usuario para autenticar.
     * @param contrasenia La contraseña del usuario.
     * @return El objeto {@link Usuario} si la autenticación es exitosa, de lo contrario, `null`.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) &&
                    usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo {@link Usuario}. Si el nombre de usuario no existe,
     * el usuario es añadido a la lista en memoria y luego toda la lista se guarda
     * en el archivo binario.
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
     * Elimina un {@link Usuario} por su nombre de usuario.
     * Si el usuario es encontrado y removido de la lista, la lista actualizada
     * se guarda en el archivo binario.
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
     * Busca el usuario por su nombre de usuario. Si se encuentra,
     * reemplaza sus datos en la lista y guarda la lista actualizada en el archivo.
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
     * Recupera una lista de todos los {@link Usuario}s almacenados.
     * Se devuelve una nueva {@link ArrayList} para evitar modificaciones directas.
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
     * </p>
     *
     * @param rol El {@link Rol} por el cual se filtrarán los usuarios.
     * @return Una {@link List} de {@link Usuario}s que coinciden con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return List.of();
    }

    /**
     * Carga la lista de {@link Usuario}s desde el archivo binario especificado.
     * La lista completa de usuarios se lee como un solo objeto serializado.
     * Si el archivo no existe, la lista de usuarios se mantiene vacía.
     * Captura y reporta errores de E/S o de clase no encontrada.
     */
    private void cargarUsuarios() {
        File archivo = new File(rutaArchivo);
        // Asegura que el directorio exista para evitar FileNotFoundException al crear el stream
        if (!archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(rutaArchivo))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, lo cual es normal la primera vez. Se creará al guardar.
            System.out.println("Archivo de usuarios no encontrado. Se creará al guardar.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar usuarios desde el archivo binario: " + e.getMessage());
            e.printStackTrace();
            // Asegura que la lista esté vacía en caso de un error de carga para evitar datos corruptos.
            usuarios = new ArrayList<>();
        }
    }

    /**
     * Guarda la lista actual de {@link Usuario}s en memoria en el archivo binario.
     * La lista completa de usuarios se serializa como un solo objeto.
     * El archivo existente es sobrescrito con los datos actuales.
     * Captura y reporta errores de E/S.
     */
    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios en el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}