package ec.edu.ups.dao.impl.memoria;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.util.*;

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.PreguntaSeguridadDAO}
 * que gestiona las {@link PreguntaSeguridad} y {@link RespuestaSeguridad}
 * completamente en memoria RAM.
 * <p>
 * Esta clase inicializa un conjunto predefinido de preguntas de seguridad
 * al ser instanciada. Las respuestas de los usuarios se almacenan en una
 * lista en memoria y se pierden al finalizar la ejecución de la aplicación.
 * Es adecuada para propósitos de pruebas o demostraciones sencillas.
 * </p>
 */
public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {

    /**
     * Lista en memoria que almacena todas las preguntas de seguridad predefinidas.
     */
    private List<PreguntaSeguridad> preguntas;

    /**
     * Lista en memoria que almacena las respuestas de seguridad proporcionadas por los usuarios.
     */
    private List<RespuestaSeguridad> respuestas;

    /**
     * Constructor de la clase `PreguntaSeguridadDAOMemoria`.
     * Inicializa las listas de preguntas y respuestas, y carga un conjunto
     * predefinido de preguntas de seguridad.
     */
    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
        inicializarPreguntas();
    }

    /**
     * Inicializa la lista de preguntas de seguridad con un conjunto predefinido.
     * Este método se llama durante la construcción del objeto para poblar las preguntas disponibles.
     */
    private void inicializarPreguntas() {
        preguntas.add(new PreguntaSeguridad(1, "¿Cuál es el nombre de tu primera mascota?"));
        preguntas.add(new PreguntaSeguridad(2, "¿En qué ciudad naciste?"));
        preguntas.add(new PreguntaSeguridad(3, "¿Cuál es el nombre de tu mejor amigo de la infancia?"));
        preguntas.add(new PreguntaSeguridad(4, "¿Cuál es tu comida favorita?"));
        preguntas.add(new PreguntaSeguridad(5, "¿Cuál es el nombre de tu escuela primaria?"));
        preguntas.add(new PreguntaSeguridad(6, "¿Cuál es tu color favorito?"));
        preguntas.add(new PreguntaSeguridad(7, "¿Cuál es el segundo nombre de tu madre?"));
        preguntas.add(new PreguntaSeguridad(8, "¿En qué año te graduaste de secundaria?"));
        preguntas.add(new PreguntaSeguridad(9, "¿Cuál es tu película favorita?"));
        preguntas.add(new PreguntaSeguridad(10, "¿Cuál es el nombre de tu primer trabajo?"));
    }

    /**
     * Lista todas las preguntas de seguridad disponibles actualmente en memoria.
     *
     * @return Una nueva {@link List} que contiene todas las preguntas de seguridad.
     * Se devuelve una copia para proteger la lista interna de modificaciones externas.
     */
    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Obtiene una lista de preguntas de seguridad seleccionadas aleatoriamente.
     * Se crea una copia de la lista de preguntas, se mezcla y se devuelve
     * un subconjunto de la cantidad especificada.
     *
     * @param cantidad El número de preguntas aleatorias a obtener.
     * @return Una {@link List} de preguntas de seguridad seleccionadas al azar.
     */
    @Override
    public List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad) {
        List<PreguntaSeguridad> preguntasAleatorias = new ArrayList<>(preguntas);
        Collections.shuffle(preguntasAleatorias); // Mezcla aleatoriamente la lista
        // Devuelve un subconjunto, asegurando no exceder el tamaño de la lista
        return preguntasAleatorias.subList(0, Math.min(cantidad, preguntasAleatorias.size()));
    }

    /**
     * Guarda una nueva {@link RespuestaSeguridad} en la lista en memoria.
     * Este método simplemente añade la respuesta; no hay validación
     * para duplicados ni lógica de actualización en esta implementación.
     *
     * @param respuesta El objeto {@link RespuestaSeguridad} a guardar.
     */
    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        // En una implementación en memoria, podrías querer añadir lógica
        // para reemplazar una respuesta existente del mismo usuario a la misma pregunta
        // si se espera que las respuestas sean únicas por usuario y pregunta.
        // Por ejemplo:
        // respuestas.removeIf(r -> r.getUsername().equals(respuesta.getUsername()) && r.getIdPregunta() == respuesta.getIdPregunta());
        respuestas.add(respuesta);
    }

    /**
     * Obtiene todas las respuestas de seguridad asociadas a un nombre de usuario específico.
     *
     * @param username El nombre de usuario para el cual se desean obtener las respuestas.
     * @return Una {@link List} de {@link RespuestaSeguridad} que corresponden al usuario.
     */
    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        List<RespuestaSeguridad> respuestasUsuario = new ArrayList<>();
        if (username == null) return respuestasUsuario; // Retorna una lista vacía si username es nulo

        for (RespuestaSeguridad respuesta : respuestas) {
            if (respuesta.getUsername() != null && respuesta.getUsername().equals(username)) {
                respuestasUsuario.add(respuesta);
            }
        }
        return respuestasUsuario;
        /* Equivalente usando Streams (más conciso):
        return respuestas.stream()
            .filter(respuesta -> username.equals(respuesta.getUsername()))
            .collect(Collectors.toList());
        */
    }

    /**
     * Obtiene una lista de preguntas de seguridad que un usuario ya ha respondido.
     * Es importante notar que la implementación actual devuelve las preguntas a las que el usuario *ha respondido*.
     * Si la intención es devolver preguntas *no respondidas*, la lógica debería cambiar.
     * Basado en el nombre del método, se asume que la intención original era "obtener preguntas respondidas por usuario".
     *
     * @param username El nombre de usuario para el cual se buscan las preguntas.
     * @return Una {@link List} de {@link PreguntaSeguridad} a las que el usuario ha respondido.
     */
    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        // La implementación actual del código busca las preguntas a las que el usuario ya ha respondido.
        // Si el requisito es obtener las preguntas *NO* respondidas por el usuario, la lógica debería ser:
        // 1. Obtener todas las preguntas.
        // 2. Obtener las IDs de las preguntas respondidas por el usuario.
        // 3. Filtrar las preguntas totales para excluir las ya respondidas.
        // La implementación actual:
        List<PreguntaSeguridad> preguntasRespondidasPorUsuario = new ArrayList<>();
        if (username == null) return preguntasRespondidasPorUsuario;

        for (RespuestaSeguridad respuesta : respuestas) {
            if (respuesta.getUsername() != null && respuesta.getUsername().equals(username)) {
                PreguntaSeguridad pregunta = buscarPorId(respuesta.getIdPregunta());
                // Asegura que la pregunta existe y que no se añade duplicada a la lista de retorno
                if (pregunta != null && !preguntasRespondidasPorUsuario.contains(pregunta)) {
                    preguntasRespondidasPorUsuario.add(pregunta);
                }
            }
        }
        return preguntasRespondidasPorUsuario;
    }


    /**
     * Busca una {@link PreguntaSeguridad} por su identificador único (ID).
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto {@link PreguntaSeguridad} si se encuentra, o `null` si no existe.
     */
    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        for (PreguntaSeguridad pregunta : preguntas) {
            if (pregunta.getId() == id) {
                return pregunta;
            }
        }
        return null;
    }

    /**
     * Valida si una respuesta proporcionada por un usuario coincide con una
     * respuesta almacenada para una pregunta de seguridad específica.
     * La comparación de la respuesta ignora mayúsculas y minúsculas y recorta espacios en blanco.
     *
     * @param username   El nombre de usuario para el cual se valida la respuesta.
     * @param idPregunta El ID de la pregunta de seguridad.
     * @param respuesta  La respuesta proporcionada por el usuario a validar.
     * @return `true` si la respuesta coincide con la almacenada, `false` en caso contrario.
     */
    @Override
    public boolean validarRespuesta(String username, int idPregunta, String respuesta) {
        if (username == null || respuesta == null) return false;
        for (RespuestaSeguridad respuestaGuardada : respuestas) {
            if (respuestaGuardada.getUsername() != null &&
                    respuestaGuardada.getUsername().equals(username) &&
                    respuestaGuardada.getIdPregunta() == idPregunta &&
                    respuestaGuardada.getRespuesta().equalsIgnoreCase(respuesta.trim())) { // trim() para eliminar espacios
                return true;
            }
        }
        return false;
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
     * Obtiene una pregunta de seguridad aleatoria a la que el usuario *ha respondido*.
     * Si el usuario no ha respondido ninguna pregunta, devuelve `null`.
     * <p>
     * Nota: Si la intención es obtener una pregunta *no respondida* aleatoria,
     * la lógica de `obtenerPreguntasPorUsuario` (que se llama internamente)
     * necesitaría ser modificada para devolver preguntas *no* respondidas.
     * </p>
     *
     * @param username El nombre de usuario para el cual buscar una pregunta aleatoria respondida.
     * @return Una {@link PreguntaSeguridad} aleatoria respondida por el usuario, o `null` si no hay.
     */
    @Override
    public PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username) {
        // Este método actualmente devuelve una pregunta aleatoria de las que el usuario *ya ha respondido*.
        // Si se pretende obtener una pregunta *sin responder* aleatoria,
        // el método `obtenerPreguntasPorUsuario` debería retornar las preguntas *no* respondidas.
        List<PreguntaSeguridad> preguntasRespondidas = obtenerPreguntasPorUsuario(username);
        if (preguntasRespondidas.isEmpty()) {
            return null; // No hay preguntas respondidas por el usuario
        }
        // Devuelve una pregunta aleatoria de las que el usuario ya respondió.
        return preguntasRespondidas.get(new Random().nextInt(preguntasRespondidas.size()));
    }
}