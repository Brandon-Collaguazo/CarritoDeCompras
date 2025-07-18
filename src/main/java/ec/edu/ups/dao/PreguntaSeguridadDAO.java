package ec.edu.ups.dao;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.util.List;

/**
 * Define operaciones para gestionar preguntas de seguridad y sus respuestas asociadas al usuario
 * en un sistema de autenticación
 */
public interface PreguntaSeguridadDAO {

    /**
     * Obtiene todas las preguntas de seguridad disponibles en el sistema
     * @return Lista completa de objetos PreguntaSeguridad
     */
    List<PreguntaSeguridad> listarTodasLasPreguntas();

    /**
     * Selecciona un conjunto aleatorio de preguntas de seguridad
     * @param cantidad Número de preguntas a retornar
     * @return Lista con la cantidad especificada de preguntas seleccionadas aleatoriamente
     */
    List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad);

    /**
     * Obtiene todas las respuestas de seguridad registradas por un usuario
     * @param username Identificador del usuario
     * @return Lista de objetos RespuestaSeguridad asociadas al usuario
     */
    List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username);

    /**
     * Obtiene las preguntas de seguridad asignadas a un usuario específico
     * @param username Identificador de usuario
     * @return Lista de objetos PreguntaSeguridad asociadas al usuario
     */
    List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username);

    /**
     * Almacena o actualiza una respuesta de seguridad de usuario
     * @param respuesta Objeto RespuestaSeguridad a persistir
     */
    void guardarRespuesta(RespuestaSeguridad respuesta);

    /**
     * Busca una pregunta de seguridad por su identificador único
     * @param id Identificador numérico de la pregunta
     * @return Objeto PreguntaSeguridad o null si no existe
     */
    PreguntaSeguridad buscarPorId(int id);

    /**
     * Valida si la respuesta proporcionada coincide con la registrada por un usuario y pregunta
     * específicos
     * @param username Identificador del usuario
     * @param idPregunta Id de la pregunta de seguridad
     * @param respuesta Respuesta a validar
     * @return true si la respuesta es correcta, false en caso contrario
     */
    boolean validarRespuesta(String username, int idPregunta, String respuesta);

    /**
     * Método aparente redundante con validarRespuesta que realiza la misma función de
     * verificación
     * @param username Identificador del usuario
     * @param idPregunta Id de la pregunta de seguridad
     * @param respuesta Respuesta a validar
     * @return true si la respuesta es correcta, false en caso contrario
     */
    boolean verificarRespuesta(String username, int idPregunta, String respuesta);

    /**
     * Obtiene una pregunta aleatoria entre las asignadas a un usuario específico
     * @param username Identificador del usuario
     * @return Un objeto PreguntaSeguridad seleccionado aleatoriamente entre las del usuario
     */
    PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username);
}
