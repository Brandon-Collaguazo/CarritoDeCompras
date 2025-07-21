package ec.edu.ups.dao.impl.memoria;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.UsuarioDAO} que
 * gestiona los objetos {@link ec.edu.ups.modelo.Usuario} completamente en memoria RAM.
 * <p>
 * Esta clase inicializa un conjunto predefinido de usuarios al ser instanciada.
 * Los datos se almacenan en una lista en memoria y se perderán al finalizar la
 * ejecución de la aplicación. Es adecuada para propósitos de pruebas o desarrollo.
 * </p>
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    /**
     * Lista en memoria que almacena todos los objetos {@link Usuario}.
     */
    private List<Usuario> usuarios;

    /**
     * Constructor de la clase `UsuarioDAOMemoria`.
     * Inicializa la lista de usuarios como un nuevo {@link ArrayList} y
     * luego añade dos usuarios predefinidos (un administrador y un usuario normal).
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<Usuario>();
        // Se crean y añaden usuarios de ejemplo al inicializar la DAO en memoria.
        // La fecha de nacimiento es 'null' en estos ejemplos.
        crear(new Usuario("0703062885", "Pepe", Date.from(Instant.ofEpochMilli(2000/1/1)), "1234556789","admin@gmail.com", "admin", "12345", Rol.ADMINISTRADOR));
        crear(new Usuario("0706780590", "Brandon", null, "0969557675", "", "usuario", "12345", Rol.USUARIO));
    }

    /**
     * Autentica un usuario verificando si el nombre de usuario y la contraseña proporcionados
     * coinciden con los de un usuario existente en la lista en memoria.
     *
     * @param username    El nombre de usuario a autenticar.
     * @param contrasenia La contraseña del usuario.
     * @return El objeto {@link Usuario} si las credenciales son válidas, o `null` en caso contrario.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo {@link Usuario} añadiéndolo a la lista en memoria.
     * No realiza validaciones de unicidad del nombre de usuario en esta implementación.
     *
     * @param usuario El objeto {@link Usuario} a ser creado.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
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
     * Elimina un {@link Usuario} de la lista en memoria por su nombre de usuario.
     * Utiliza un {@link Iterator} para una eliminación segura mientras se itera la lista.
     * Se detiene después de encontrar y eliminar la primera coincidencia.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break; // Se asume que el username es único y se elimina el primero encontrado.
            }
        }
    }

    /**
     * Actualiza la información de un {@link Usuario} existente en la memoria.
     * Busca el usuario por su nombre de usuario. Si lo encuentra,
     * reemplaza sus datos en la lista con los datos del objeto {@link Usuario} proporcionado.
     *
     * @param usuario El objeto {@link Usuario} con la información actualizada. Su username se usa para identificarlo.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario); // Reemplaza el objeto Usuario existente
                // Si solo se espera una coincidencia, se podría añadir un 'break;' aquí.
            }
        }
    }

    /**
     * Recupera una lista de todos los {@link Usuario}s almacenados en la memoria.
     * Devuelve la referencia directa a la lista interna.
     *
     * @return Una {@link List} que contiene todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios; // Retorna la referencia a la lista interna. Para protegerla, se podría devolver una copia: new ArrayList<>(usuarios);
    }

    /**
     * Recupera una lista de {@link Usuario}s que tienen un {@link Rol} específico.
     *
     * @param rol El {@link Rol} por el cual se filtrarán los usuarios.
     * @return Una {@link List} de {@link Usuario}s que coinciden con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        if (rol == null) {
            return usuariosEncontrados; // Retorna lista vacía si el rol es nulo.
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getRol() != null && usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}