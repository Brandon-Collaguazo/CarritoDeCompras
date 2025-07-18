package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Define las operaciones básicas del CRUD para la gestión de productos en el sistema
 */
public interface ProductoDAO {
    /**
     * Crea un nuevo producto
     * @param producto Objeto Producto con los datos a persistir
     */
    void crear(Producto producto);

    /**
     * Busca un producto por su código único
     * @param codigo Identificador numérico del producto
     * @return Objeto Producto si se encuentra, null si no existe producto
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos cuyo nombre coincida total o parcialmente con el parámetro
     * @param nombre Cadena de texto para buscar coincidencias en nombres de productos
     * @return Lista de productos que coinciden con el criterio de búsqueda, lista vacía
     * si no se encuentran coincidencias
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza la información de un producto existente
     * @param producto Objeto Producto con los datos actualizados
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema usando su código único
     * @param codigo Identificador numérico del producto a eliminar
     */
    void eliminar(int codigo);

    /**
     * Obtiene todos los productos registrados en el sistema
     * @return Lista completa de todos los productos, lista vacía si no hay productos
     */
    List<Producto> listarTodos();

}
