package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PreguntaDAOBinario implements PreguntaSeguridadDAO {

    private final String ruta;
    private final List<PreguntaSeguridad> preguntas = new ArrayList<>();
    private final List<RespuestaSeguridad> respuestas = new ArrayList<>();
    public PreguntaDAOBinario(String ruta) {
        this.ruta = ruta;
        cargarPreguntas();
        cargarRespuestas();
    }

    private void cargarPreguntas() {
        File archivo = new File(ruta + "/preguntas.bin");
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                PreguntaSeguridad pregunta = (PreguntaSeguridad) ois.readObject();
                preguntas.add(pregunta);
            }
        } catch (EOFException e) {
            // Fin del archivo, es normal
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }
    }

    private void cargarRespuestas() {
        File archivo = new File(ruta + "/respuestas.bin");
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                RespuestaSeguridad respuesta = (RespuestaSeguridad) ois.readObject();
                respuestas.add(respuesta);
            }
        } catch (EOFException e) {
            // Fin del archivo, es normal
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar respuestas: " + e.getMessage());
        }
    }

    private void guardarPreguntas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/preguntas.bin"))) {
            for (PreguntaSeguridad p : preguntas) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar preguntas: " + e.getMessage());
        }
    }
    private void guardarRespuestas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/respuestas.bin"))) {
            for (RespuestaSeguridad r : respuestas) {
                oos.writeObject(r);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar respuestas: " + e.getMessage());
        }
    }

    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas);

    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad) {
        Collections.shuffle(preguntas);
        return new ArrayList<>(preguntas.subList(0, Math.min(cantidad, preguntas.size())));
    }

    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        return respuestas.stream()
                .filter(r -> r.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        Set<Integer> idsPreguntasRespondidas = obtenerRespuestasPorUsuario(username).stream()
                .map(RespuestaSeguridad::getIdPregunta)
                .collect(Collectors.toSet());

        return preguntas.stream()
                .filter(p -> !idsPreguntasRespondidas.contains(p.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        respuestas.removeIf(r ->
                r.getUsername().equals(respuesta.getUsername()) &&
                        r.getIdPregunta() == respuesta.getIdPregunta());

        respuestas.add(respuesta);
        guardarRespuestas();
    }

    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        return preguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validarRespuesta(String username, int idPregunta, String respuesta) {
        return respuestas.stream()
                .anyMatch(r -> r.getUsername().equals(username) &&
                        r.getIdPregunta() == idPregunta &&
                        r.getRespuesta().equalsIgnoreCase(respuesta));
    }

    @Override
    public boolean verificarRespuesta(String username, int idPregunta, String respuesta) {
        return validarRespuesta(username, idPregunta, respuesta);
    }

    @Override
    public PreguntaSeguridad obtenerPreguntasAleatoriasPorUsuario(String username) {
        List<PreguntaSeguridad> disponibles = obtenerPreguntasPorUsuario(username);
        if (disponibles.isEmpty()) return null;
        Collections.shuffle(disponibles);
        return disponibles.get(0);
    }
}
