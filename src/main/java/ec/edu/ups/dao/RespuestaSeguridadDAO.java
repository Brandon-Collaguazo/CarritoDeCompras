package ec.edu.ups.dao;

import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Define operaciones para gestionar respuestas de seguridad asociadas a Usuarios.
 */
public interface RespuestaSeguridadDAO {

    /**
     * Obtiene todas las respuestas de seguridad asociadas a un usuario en específico
     * @param usuario Objeto Usuario del cual se requieren las respuestas
     * @return Lista de objetos RespuestaSeguridad del usuario, lista vacía si el usuario
     * no tiene respuestas registradas
     */
    List<RespuestaSeguridad> buscarPorUsuario(Usuario usuario);
}
