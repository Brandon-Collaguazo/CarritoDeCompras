package ec.edu.ups.dao;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.util.List;

public interface PreguntaSeguridadDAO {
    List<PreguntaSeguridad> listarTodasLasPreguntas();

    List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad);

    List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username);

    List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username);

    void guardarRespuesta(RespuestaSeguridad respuesta);

    PreguntaSeguridad buscarPorId(int id);

    boolean validarRespuesta(String username, int idPregunta, String respuesta);

    boolean verificarRespuesta(String username, int idPregunta, String respuesta);

    PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username);
}
