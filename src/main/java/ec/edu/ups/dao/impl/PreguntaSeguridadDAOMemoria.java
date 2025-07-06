package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.util.*;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private List<PreguntaSeguridad> preguntas;
    private List<RespuestaSeguridad> respuestas;

    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
        inicializarPreguntas();
    }

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

    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad) {
        List<PreguntaSeguridad> preguntasAleatorioas = new ArrayList<>(preguntas);
        Collections.shuffle(preguntasAleatorioas);
        return preguntasAleatorioas.subList(0, Math.min(cantidad, preguntasAleatorioas.size()));
    }

    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        respuestas.add(respuesta);
    }

    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        List<RespuestaSeguridad> respuestasUsuario = new ArrayList<>();
        for(RespuestaSeguridad respuesta : respuestas) {
            if(respuesta.getUsername().equals(username)) {
                respuestasUsuario.add(respuesta);
            }
        }
        return respuestasUsuario;
    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        List<PreguntaSeguridad> preguntasUsuario = new ArrayList<>();
        for(RespuestaSeguridad respuesta : respuestas) {
            if(respuesta.getUsername().equals(username)) {
                PreguntaSeguridad pregunta = buscarPorId(respuesta.getIdPregunta());
                if(pregunta != null) {
                    preguntasUsuario.add(pregunta);
                }
            }
        }
        return preguntasUsuario;
    }

    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        for(PreguntaSeguridad pregunta : preguntas) {
            if(pregunta.getId() == id) {
                return pregunta;
            }
        }
        return null;
    }

    @Override
    public boolean validarRespuesta(String username, int idPregunta, String respuesta) {
        for(RespuestaSeguridad respuestaGuardada : respuestas) {
            if (respuestaGuardada.getUsername().equals(username) &&
                respuestaGuardada.getIdPregunta() == idPregunta &&
                respuestaGuardada.getRespuesta().equalsIgnoreCase(respuesta.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verificarRespuesta(String username, int idPregunta, String respuesta) {
        return validarRespuesta(username, idPregunta, respuesta);
    }

    @Override
    public PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username) {
        List<PreguntaSeguridad> preguntasUsuario = obtenerPreguntasPorUsuario(username);
        if(preguntasUsuario.isEmpty()) {
            return null;
        }
        return preguntasUsuario.get(new Random().nextInt(preguntasUsuario.size()));
    }
}
