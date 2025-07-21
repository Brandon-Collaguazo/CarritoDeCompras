package ec.edu.ups.dao.impl.archBin;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.PreguntaSeguridadDAO}
 * que gestiona la persistencia de {@link PreguntaSeguridad} y
 * {@link RespuestaSeguridad} utilizando archivos binarios.
 * <p>
 * Las preguntas de seguridad se serializan en `preguntas.bin` y las respuestas
 * de los usuarios a estas preguntas se serializan en `respuestas.bin`.
 * </p>
 * <p>
 * Para que esta implementación funcione correctamente, las clases {@link PreguntaSeguridad}
 * y {@link RespuestaSeguridad} deben implementar la interfaz {@link java.io.Serializable}.
 * </p>
 * <p>
 * Las operaciones de lectura cargan los datos en memoria al inicio de la aplicación,
 * y las operaciones de escritura (especialmente para respuestas) persisten los cambios
 * en los archivos correspondientes.
 * </p>
 */
public class PreguntaDAOBinario implements PreguntaSeguridadDAO {

    /**
     * La ruta del directorio donde se almacenarán los archivos binarios de preguntas y respuestas.
     */
    private final String ruta;

    /**
     * Lista en memoria que almacena todas las preguntas de seguridad cargadas desde el archivo binario.
     */
    private final List<PreguntaSeguridad> preguntas = new ArrayList<>();

    /**
     * Lista en memoria que almacena todas las respuestas de seguridad cargadas desde el archivo binario.
     */
    private final List<RespuestaSeguridad> respuestas = new ArrayList<>();

    /**
     * Constructor de la clase `PreguntaDAOBinario`.
     * <p>
     * Inicializa la ruta y procede a cargar las preguntas y respuestas existentes
     * desde sus respectivos archivos binarios. También asegura que la carpeta de la ruta exista.
     * </p>
     *
     * @param ruta La ruta del directorio base para los archivos de datos binarios.
     */
    public PreguntaDAOBinario(String ruta) {
        this.ruta = ruta;
        // Asegurarse de que la carpeta exista
        File folder = new File(ruta);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        cargarPreguntas();
        cargarRespuestas();
    }

    /**
     * Carga los objetos {@link PreguntaSeguridad} desde el archivo binario `preguntas.bin`
     * a la lista en memoria (`preguntas`).
     * <p>
     * Utiliza un `ObjectInputStream` para deserializar los objetos. La lectura continúa
     * hasta que se alcanza el final del archivo (`EOFException`).
     * </p>
     */
    private void cargarPreguntas() {
        File archivo = new File(ruta + "/preguntas.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("Advertencia: El archivo binario de preguntas no existe o está vacío: " + archivo.getAbsolutePath());
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) { // Leer objetos hasta que se lance EOFException
                PreguntaSeguridad pregunta = (PreguntaSeguridad) ois.readObject();
                preguntas.add(pregunta);
            }
        } catch (EOFException e) {
            // Fin del archivo, es un comportamiento esperado al leer objetos serializados.
            System.out.println("Carga de preguntas finalizada: Fin del archivo alcanzado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar preguntas desde el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los objetos {@link RespuestaSeguridad} desde el archivo binario `respuestas.bin`
     * a la lista en memoria (`respuestas`).
     * <p>
     * Utiliza un `ObjectInputStream` para deserializar los objetos. La lectura continúa
     * hasta que se alcanza el final del archivo (`EOFException`).
     * </p>
     */
    private void cargarRespuestas() {
        File archivo = new File(ruta + "/respuestas.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("Advertencia: El archivo binario de respuestas no existe o está vacío: " + archivo.getAbsolutePath());
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) { // Leer objetos hasta que se lance EOFException
                RespuestaSeguridad respuesta = (RespuestaSeguridad) ois.readObject();
                respuestas.add(respuesta);
            }
        } catch (EOFException e) {
            // Fin del archivo, es un comportamiento esperado al leer objetos serializados.
            System.out.println("Carga de respuestas finalizada: Fin del archivo alcanzado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar respuestas desde el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de preguntas de seguridad en memoria (`preguntas`)
     * en el archivo binario `preguntas.bin`.
     * <p>
     * Utiliza un `ObjectOutputStream` para serializar cada objeto `PreguntaSeguridad`.
     * Cada vez que se llama, sobrescribe el contenido del archivo existente.
     * </p>
     */
    private void guardarPreguntas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/preguntas.bin"))) {
            for (PreguntaSeguridad p : preguntas) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar preguntas en el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda la lista actual de respuestas de seguridad en memoria (`respuestas`)
     * en el archivo binario `respuestas.bin`.
     * <p>
     * Utiliza un `ObjectOutputStream` para serializar cada objeto `RespuestaSeguridad`.
     * Cada vez que se llama, sobrescribe el contenido del archivo existente.
     * </p>
     */
    private void guardarRespuestas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/respuestas.bin"))) {
            for (RespuestaSeguridad r : respuestas) {
                oos.writeObject(r);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar respuestas en el archivo binario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lista todas las preguntas de seguridad disponibles cargadas en memoria.
     *
     * @return Una nueva lista que contiene todas las preguntas de seguridad.
     * Se devuelve una copia para evitar modificaciones directas de la lista interna.
     */
    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Obtiene una lista de preguntas de seguridad seleccionadas aleatoriamente.
     * La lista interna de preguntas se mezcla y luego se devuelve un subconjunto
     * de la cantidad especificada.
     *
     * @param cantidad El número de preguntas aleatorias a obtener.
     * @return Una lista de preguntas de seguridad seleccionadas al azar.
     */
    @Override
    public List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad) {
        Collections.shuffle(preguntas); // Mezcla la lista de preguntas
        // Devuelve un subconjunto de la lista mezclada, limitado por la cantidad o el tamaño total de preguntas
        return new ArrayList<>(preguntas.subList(0, Math.min(cantidad, preguntas.size())));
    }

    /**
     * Obtiene todas las respuestas de seguridad asociadas a un nombre de usuario específico.
     *
     * @param username El nombre de usuario para el cual se desean obtener las respuestas.
     * @return Una lista de {@link RespuestaSeguridad} que corresponden al usuario.
     */
    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        if (username == null) return new ArrayList<>(); // Retorna lista vacía si el username es nulo
        return respuestas.stream()
                .filter(r -> username.equals(r.getUsername()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de preguntas de seguridad que un usuario aún no ha respondido.
     * Esto se determina filtrando las preguntas disponibles por aquellas cuyas IDs no están
     * en el conjunto de IDs de preguntas ya respondidas por el usuario.
     *
     * @param username El nombre de usuario para el cual se buscan preguntas no respondidas.
     * @return Una lista de {@link PreguntaSeguridad} que el usuario no ha respondido.
     */
    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        Set<Integer> idsPreguntasRespondidas = obtenerRespuestasPorUsuario(username).stream()
                .map(RespuestaSeguridad::getIdPregunta)
                .collect(Collectors.toSet());

        return preguntas.stream()
                .filter(p -> !idsPreguntasRespondidas.contains(p.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda una respuesta de seguridad para un usuario.
     * Si el usuario ya tenía una respuesta para la misma pregunta, esta se actualiza (reemplaza).
     * Después de la modificación en memoria, se persisten todas las respuestas en el archivo binario.
     *
     * @param respuesta El objeto {@link RespuestaSeguridad} a guardar/actualizar.
     */
    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        // Elimina cualquier respuesta existente para el mismo usuario y pregunta
        respuestas.removeIf(r ->
                r.getUsername().equals(respuesta.getUsername()) &&
                        r.getIdPregunta() == respuesta.getIdPregunta());

        respuestas.add(respuesta); // Añade la nueva o actualizada respuesta
        guardarRespuestas(); // Persiste los cambios
    }

    /**
     * Busca una pregunta de seguridad por su identificador único (ID).
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@link PreguntaSeguridad} si se encuentra, o `null` si no existe.
     */
    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        return preguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Valida si una respuesta dada coincide con la respuesta almacenada
     * para un usuario y una pregunta de seguridad específicos. La comparación
     * de la respuesta ignora mayúsculas y minúsculas.
     *
     * @param username   El nombre de usuario.
     * @param idPregunta El ID de la pregunta de seguridad.
     * @param respuesta  La respuesta proporcionada por el usuario.
     * @return `true` si la respuesta coincide, `false` en caso contrario.
     */
    @Override
    public boolean validarRespuesta(String username, int idPregunta, String respuesta) {
        if (username == null || respuesta == null) return false;
        return respuestas.stream()
                .anyMatch(r -> username.equals(r.getUsername()) &&
                        r.getIdPregunta() == idPregunta &&
                        respuesta.equalsIgnoreCase(r.getRespuesta()));
    }

    /**
     * Alias del método {@link #validarRespuesta(String, int, String)}.
     * Realiza la misma validación de una respuesta de seguridad.
     *
     * @param username   El nombre de usuario.
     * @param idPregunta El ID de la pregunta de seguridad.
     * @param respuesta  La respuesta proporcionada por el usuario.
     * @return `true` si la respuesta coincide, `false` en caso contrario.
     */
    @Override
    public boolean verificarRespuesta(String username, int idPregunta, String respuesta) {
        return validarRespuesta(username, idPregunta, respuesta);
    }

    /**
     * Obtiene una pregunta de seguridad aleatoria que el usuario aún no ha respondido.
     * Si no hay preguntas disponibles para el usuario, devuelve `null`.
     *
     * @param username El nombre de usuario para el cual buscar una pregunta aleatoria.
     * @return Una {@link PreguntaSeguridad} aleatoria no respondida por el usuario, o `null`.
     */
    @Override
    public PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username) {
        List<PreguntaSeguridad> disponibles = obtenerPreguntasPorUsuario(username);
        if (disponibles.isEmpty()) {
            return null;
        }
        Collections.shuffle(disponibles); // Mezcla las preguntas disponibles
        return disponibles.get(0); // Devuelve la primera pregunta de la lista mezclada
    }
}