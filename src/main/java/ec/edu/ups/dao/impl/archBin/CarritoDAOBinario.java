package ec.edu.ups.dao.impl.archBin;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.CarritoDAO} que
 * persiste los objetos {@link ec.edu.ups.modelo.Carrito} en un archivo binario.
 * <p>
 * Los carritos se serializan directamente como objetos Java en el archivo "carritos.bin"
 * dentro de la ruta especificada.
 * </p>
 * <p>
 * Para que esta implementación funcione correctamente, la clase {@link ec.edu.ups.modelo.Carrito}
 * y todas las clases que la componen (ej. {@link ec.edu.ups.modelo.ItemCarrito},
 * {@link ec.edu.ups.modelo.Producto}, {@link ec.edu.ups.modelo.Usuario})
 * deben implementar la interfaz {@link java.io.Serializable}.
 * </p>
 * <p>
 * Esta implementación se encarga de cargar los carritos al inicio de la aplicación
 * y de guardarlos después de cada operación de modificación (crear, actualizar, eliminar).
 * </p>
 */
public class CarritoDAOBinario implements CarritoDAO {

    /**
     * Ruta del directorio donde se almacenará el archivo binario de carritos.
     */
    private final String ruta;

    /**
     * Lista en memoria que contiene todos los objetos Carrito cargados desde el archivo binario.
     */
    private final List<Carrito> carritos = new ArrayList<>();

    /**
     * Constructor de la clase `CarritoDAOBinario`.
     * <p>
     * Inicializa la ruta del archivo, verifica y crea el directorio y el archivo
     * binario si no existen. Luego, carga los carritos existentes desde el archivo
     * en la lista en memoria.
     * </p>
     *
     * @param ruta La ruta del directorio donde se guardará el archivo `carritos.bin`.
     */
    public CarritoDAOBinario(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        // Crea el directorio si no existe
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivo = new File(ruta + "/carritos.bin");
        // Crea el archivo binario si no existe
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo binario de carritos: " + e.getMessage());
                e.printStackTrace();
            }
        }

        cargarCarritos(); // Carga los carritos existentes al iniciar la DAO
    }

    /**
     * Carga los objetos {@link Carrito} desde el archivo binario "carritos.bin"
     * a la lista en memoria (`carritos`).
     * <p>
     * Utiliza un `ObjectInputStream` para deserializar los objetos. La lectura continúa
     * hasta que se alcanza el final del archivo (`EOFException`).
     * </p>
     */
    private void cargarCarritos() {
        carritos.clear(); // Limpia la lista actual antes de cargar
        File archivo = new File(ruta + "/carritos.bin");
        // Si el archivo no existe o está vacío, no hay carritos que cargar.
        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) { // Leer objetos hasta que se lance EOFException
                Carrito carrito = (Carrito) objectInputStream.readObject();
                carritos.add(carrito);
            }
        } catch (EOFException e) {
            // Se llega al final del archivo, es un comportamiento esperado al leer objetos serializados.
            System.out.println("Carga de carritos finalizada: Fin del archivo alcanzado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar carritos desde un archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de {@link Carrito} en memoria (`carritos`) en el
     * archivo binario "carritos.bin".
     * <p>
     * Utiliza un `ObjectOutputStream` para serializar cada objeto `Carrito`.
     * Cada vez que se llama, sobrescribe el contenido del archivo existente.
     * </p>
     */
    private void guardarCarritos() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ruta + "/carritos.bin"))) {
            for (Carrito carrito : carritos) {
                objectOutputStream.writeObject(carrito);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar carritos en archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo {@link Carrito} añadiéndolo a la lista en memoria
     * y luego persistiendo toda la lista en el archivo binario.
     *
     * @param carrito El objeto Carrito a ser creado y persistido.
     */
    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritos(); // Persiste los cambios
    }

    /**
     * Busca y devuelve un {@link Carrito} por su código único.
     *
     * @param codigo El código entero del carrito a buscar.
     * @return El objeto Carrito si se encuentra, o `null` si no existe un carrito con ese código.
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
     * La comparación se realiza por la cédula del usuario, asumiendo que el objeto
     * {@link Usuario} dentro del {@link Carrito} tiene la cédula correctamente cargada.
     *
     * @param usuario El objeto Usuario por el cual buscar los carritos. Se utiliza su cédula para la comparación.
     * @return Una {@link List} de Carrito que pertenecen al usuario especificado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> encontrados = new ArrayList<>();
        if (usuario == null || usuario.getCedula() == null) {
            System.err.println("Advertencia: Intento de buscar carritos con un usuario o cédula nulos.");
            return encontrados;
        }
        for (Carrito carrito : carritos) {
            // Asegura que el carrito tiene un usuario y que su cédula no es nula antes de comparar
            if (carrito.getUsuario() != null && carrito.getUsuario().getCedula() != null &&
                    carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                encontrados.add(carrito);
            }
        }
        return encontrados;
    }

    /**
     * Actualiza la información de un {@link Carrito} existente en el almacenamiento.
     * Busca el carrito por su código y lo reemplaza en la lista en memoria.
     * Después de la actualización, la lista completa se persiste en el archivo binario.
     *
     * @param carrito El objeto Carrito con la información actualizada. Su código se utiliza para identificarlo.
     */
    @Override
    public void actualizar(Carrito carrito) {
        if (carrito == null) {
            System.err.println("Advertencia: Intento de actualizar un carrito nulo.");
            return;
        }
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito); // Reemplaza el objeto Carrito existente
                guardarCarritos(); // Persiste los cambios
                return; // Carrito encontrado y actualizado, sale del método
            }
        }
        System.out.println("Información: Carrito con código " + carrito.getCodigo() + " no encontrado para actualizar.");
    }

    /**
     * Elimina un {@link Carrito} del almacenamiento por su código único.
     * La eliminación se realiza primero de la lista en memoria y luego
     * la lista modificada se persiste en el archivo binario.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        // Usa removeIf para eliminar el carrito cuya lambda condición es verdadera
        boolean removido = carritos.removeIf(carrito -> carrito.getCodigo() == codigo);
        if (removido) {
            guardarCarritos(); // Persiste los cambios solo si se eliminó un carrito
        } else {
            System.out.println("Información: Carrito con código " + codigo + " no encontrado para eliminar.");
        }
    }

    /**
     * Lista todos los {@link Carrito}s disponibles actualmente en el almacenamiento.
     *
     * @return Una nueva {@link ArrayList} que contiene todos los carritos.
     * Se devuelve una copia para proteger la lista interna de modificaciones externas.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Busca y devuelve una lista de {@link Carrito} asociados a un nombre de usuario (username) específico.
     *
     * @param username El nombre de usuario por el cual listar los carritos.
     * @return Una {@link List} de Carrito que pertenecen al nombre de usuario especificado.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> encontrados = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Advertencia: Intento de listar carritos con un nombre de usuario nulo o vacío.");
            return encontrados;
        }
        for (Carrito carrito : carritos) {
            // Asegura que el carrito tiene un usuario y que su username no es nulo antes de comparar
            if (carrito.getUsuario() != null && carrito.getUsuario().getUsername() != null &&
                    carrito.getUsuario().getUsername().equals(username)) {
                encontrados.add(carrito);
            }
        }
        return encontrados;
    }
}