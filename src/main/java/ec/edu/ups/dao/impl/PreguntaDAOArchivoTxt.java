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

public class PreguntaDAOArchivoTxt implements PreguntaSeguridadDAO {

    private final String ruta;
    private final List<PreguntaSeguridad> preguntas = new ArrayList<>();
    private final List<RespuestaSeguridad> respuestas = new ArrayList<>();

    public PreguntaDAOArchivoTxt(String ruta) {
        this.ruta = ruta;
        cargarPreguntas();
        cargarRespuestas();
    }

    private void cargarPreguntas() {
        File archivo = new File(ruta + "/preguntas.txt");
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                PreguntaSeguridad pregunta = parsePregunta(linea);
                if (pregunta != null) {
                    preguntas.add(pregunta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarRespuestas() {
        File archivo = new File(ruta + "/respuestas.txt");
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 3) {
                    respuestas.add(new RespuestaSeguridad(
                            partes[0].trim(),
                            Integer.parseInt(partes[1].trim()),
                            partes[2].trim()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar respuestas: " + e.getMessage());
        }
    }

    private void guardarRespuestas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + "/respuestas.txt"))) {
            for (RespuestaSeguridad rs : respuestas) {
                pw.println(String.join("|",
                        rs.getUsername(),
                        String.valueOf(rs.getIdPregunta()),
                        rs.getRespuesta()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error al guardar respuestas: " + e.getMessage());
        }
    }

    private PreguntaSeguridad parsePregunta(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 2) {
            return new PreguntaSeguridad(
                    Integer.parseInt(partes[0]),
                    partes[1]
            );
        }
        return null;
    }

    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasAleatorias(int cantidad) {
        Collections.shuffle(preguntas);
        return preguntas.subList(0, Math.min(cantidad, preguntas.size()));
    }

    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        return respuestas.stream()
                .filter(respuestaSeguridad -> respuestaSeguridad.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        Set<Integer> idRespondidas = obtenerRespuestasPorUsuario(username).stream()
                .map(RespuestaSeguridad::getIdPregunta)
                .collect(Collectors.toSet());
        return preguntas.stream()
                .filter(preguntaSeguridad -> !idRespondidas.contains(preguntaSeguridad.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        respuestas.removeIf(respuestaSeguridad -> respuestaSeguridad.getUsername().equals(respuestaSeguridad.getRespuesta()) &&
                respuestaSeguridad.getIdPregunta() == respuesta.getIdPregunta());
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
