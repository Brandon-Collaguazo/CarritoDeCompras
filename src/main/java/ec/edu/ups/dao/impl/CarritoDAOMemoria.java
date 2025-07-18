package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.CarritoDAO} que
 * gestiona los objetos {@link ec.edu.ups.modelo.Carrito} en memoria RAM.
 * <p>
 * Esta clase no persiste los datos a ningún almacenamiento secundario,
 * por lo que todos los carritos se perderán al finalizar la ejecución de la aplicación.
 * Es útil para propósitos de pruebas o para aplicaciones de corta duración.
 * </p>
 */
public class CarritoDAOMemoria implements CarritoDAO {

    /**
     * Lista en memoria que almacena todos los objetos Carrito.
     */
    private List<Carrito> listaCarritos;

    /**
     * Constructor de la clase `CarritoDAOMemoria`.
     * Inicializa la lista de carritos como un nuevo {@link ArrayList}.
     */
    public CarritoDAOMemoria() {
        listaCarritos = new ArrayList<Carrito>();
    }

    /**
     * Crea un nuevo {@link Carrito} añadiéndolo a la lista en memoria.
     *
     * @param carrito El objeto Carrito a ser creado.
     */
    @Override
    public void crear(Carrito carrito) {
        listaCarritos.add(carrito);
    }

    /**
     * Busca y devuelve un {@link Carrito} por su código único.
     *
     * @param codigo El código entero del carrito a buscar.
     * @return El objeto Carrito si se encuentra, o `null` si no existe un carrito con ese código.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : listaCarritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve una lista de {@link Carrito} asociados a un {@link Usuario} específico.
     * La comparación se realiza por la cédula del usuario, asumiendo que el objeto
     * {@link Usuario} dentro del {@link Carrito} tiene la cédula correctamente cargada.
     *
     * @param usuario El objeto Usuario por el cual buscar los carritos. Se utiliza su cédula para la comparación.
     * @return Una {@link List} de Carrito que pertenecen al usuario especificado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        // Asegura que el usuario pasado no es nulo y tiene una cédula para comparar
        if (usuario != null && usuario.getCedula() != null) {
            for (Carrito carrito : listaCarritos) {
                // Compara la cédula del usuario del carrito con la cédula del usuario dado
                if (carrito.getUsuario() != null &&
                        carrito.getUsuario().getCedula() != null &&
                        carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                    carritosUsuario.add(carrito);
                }
            }
        } else {
            System.err.println("Advertencia: Intento de buscar carritos con un objeto Usuario o cédula nulos.");
        }
        return carritosUsuario;
    }

    /**
     * Actualiza la información de un {@link Carrito} existente en la memoria.
     * Busca el carrito por su código y lo reemplaza en la lista.
     *
     * @param carrito El objeto Carrito con la información actualizada. Su código se utiliza para identificarlo.
     */
    @Override
    public void actualizar(Carrito carrito) {
        if (carrito == null) {
            System.err.println("Advertencia: Intento de actualizar un carrito nulo.");
            return;
        }
        for (int i = 0; i < listaCarritos.size(); i++) {
            if (listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito); // Reemplaza el objeto Carrito existente
                return; // Carrito encontrado y actualizado, sale del método
            }
        }
        System.out.println("Información: Carrito con código " + carrito.getCodigo() + " no encontrado para actualizar.");
    }

    /**
     * Elimina un {@link Carrito} de la lista en memoria por su código.
     * Utiliza un {@link Iterator} para evitar `ConcurrentModificationException`
     * al eliminar elementos mientras se itera.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = listaCarritos.iterator();
        boolean removido = false;
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove(); // Elimina el carrito de forma segura
                removido = true;
                // Si solo puede haber un carrito con este código, se puede añadir un break aquí
                // break;
            }
        }
        if (!removido) {
            System.out.println("Información: Carrito con código " + codigo + " no encontrado para eliminar.");
        }
    }

    /**
     * Lista todos los {@link Carrito}s disponibles actualmente en la memoria.
     *
     * @return La {@link List} interna que contiene todos los carritos.
     * Se devuelve una referencia a la lista interna.
     */
    @Override
    public List<Carrito> listarTodos() {
        return listaCarritos;
    }

    /**
     * Busca y devuelve una lista de {@link Carrito} asociados a un nombre de usuario (username) específico.
     *
     * @param username El nombre de usuario por el cual listar los carritos.
     * @return Una {@link List} de Carrito que pertenecen al nombre de usuario especificado.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Advertencia: Intento de listar carritos con un nombre de usuario nulo o vacío.");
            return carritosUsuario;
        }
        for (Carrito carrito : listaCarritos) {
            // Asegura que el carrito tiene un usuario y que su username no es nulo antes de comparar
            if (carrito.getUsuario() != null
                    && carrito.getUsuario().getUsername() != null
                    && carrito.getUsuario().getUsername().equals(username)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }
}