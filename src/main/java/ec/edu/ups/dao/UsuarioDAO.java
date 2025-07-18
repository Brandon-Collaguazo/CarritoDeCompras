package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.util.List;

/**
 * Define operaciones del CRUD para la gestión de usuarios y sus roles en el sistema
 */
public interface UsuarioDAO {

    /**
     * Verifica las credenciales del usuario
     * @param username Username del usuario
     * @param contrasenia Contraseña del usuario
     * @return Objeto si las credenciales son válidas, null si la autenticación falla
     */
    Usuario autenticar(String username, String contrasenia);

    /**
     * Registra un nuevo usuario
     * @param usuario Objeto Usuario a registrar
     */
    void crear(Usuario usuario);

    /**
     * Busca usuario por su username único
     * @param username Nombre de usuario a buscar
     * @return Objeto Usuario si lo encuentra, null en caso contrario
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un usuario del sistema
     * @param username Nombre de usuario a eliminar
     */
    void eliminar(String username);

    /**
     * Actualiza la información de un usuario existente
     * @param usuario Objeto Usuario con datos actualizados
     */
    void actualizar(Usuario usuario);

    /**
     * Obtiene todos los usuarios registrados
     * @return Lista completa de usuarios, lista vacía si no hay usuarios
     */
    List<Usuario> listarTodos();

    /**
     * Filtra usuarios por rol específico
     * @param rol Objeto Rol para filtrar
     * @return Lista de usuarios con rol especificado, lista vacía si no hay coincidencias
     */
    List<Usuario> listarPorRol(Rol rol);

}