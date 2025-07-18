package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Define las operaciones CRUD para la gestión de objetos Carrito.
 */
public interface CarritoDAO {

    /**
     * Crea un nuevo registro de carrito en la base de datos
     * @param carrito Objeto Carrito que contiene la información a persistir
     */
    void crear(Carrito carrito);

    /**
     * Buscar un carrito específico utilizando su código único
     * @param codigo Entero que representar el ID único del carrito
     * @return Carrito si se encuentra, o null si no existe
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Obtiene todos los carritos asociados a un usuario específico
     * @param usuario Nombre de usuario para filtrar los carritos
     * @return Lista de objetos asociados al usuario
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);

    /**
     * Actualiza la información de un carrito existente en la base de datos
     * @param carrito Objeto carrito con la información actualizada
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito de la base de datos usando su código único
     * @param codigo Entero que representa el identificador del carrito a eliminar
     */
    void eliminar(int codigo);

    /**
     * Obtiene una lista de todos los carritos existentes del sistema
     * @return Lista de todos los objetos Carrito en la base de datos
     */
    List<Carrito> listarTodos();

    /**
     * Obtiene una lista de carritos filtrados por el nombre del usuario
     * @param username Nombre de usuario para filtrar carritos
     * @return Lista de objetos Carrito asociados al usuario
     */
    List<Carrito> listarPorUsuario(String username);
}
