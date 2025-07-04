package ec.edu.ups.dao;

import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.List;

public interface PreguntaSeguridadDAO {
    void crear(PreguntaSeguridad pregunta);
    PreguntaSeguridad buscarPorId(int id);
    List<PreguntaSeguridad> listarTodas();
    List<PreguntaSeguridad> obtenerPreguntasPorUsername(String username);
    void actualizar(PreguntaSeguridad pregunta);
    void eliminar(int id);
}
