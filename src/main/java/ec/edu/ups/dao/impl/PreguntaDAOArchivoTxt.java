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

/**
 * Implementación de la interfaz {@link ec.edu.ups.dao.PreguntaSeguridadDAO}
 * que gestiona la persistencia de {@link PreguntaSeguridad} y
 * {@link RespuestaSeguridad} utilizando archivos de texto plano.
 * <p>
 * Las preguntas de seguridad se almacenan en `preguntas.txt` y las respuestas
 * de los usuarios a estas preguntas se guardan en `respuestas.txt`.
 * Ambos archivos utilizan un formato delimitado por `|` (pipe).
 * </p>
 * <p>
 * Las operaciones de lectura cargan los datos en memoria al inicio,
 * y las operaciones de escritura persisten los cambios en los archivos correspondientes.
 * </p>
 *
 * @author [Tu Nombre/El nombre del equipo]
 * @version 1.0
 * @since 2025-07-18
 * @see ec.edu.ups.dao.PreguntaSeguridadDAO
 * @see ec.edu.ups.modelo.PreguntaSeguridad
 * @see ec.edu.ups.modelo.RespuestaSeguridad
 */
public class PreguntaDAOArchivoTxt implements PreguntaSeguridadDAO {

    /**
     * La ruta del directorio donde se almacenarán los archivos de preguntas y respuestas.
     */
    private final String ruta;

    /**
     * Lista en memoria que almacena todas las preguntas de seguridad cargadas desde el archivo.
     */
    private final List<PreguntaSeguridad> preguntas = new ArrayList<>();

    /**
     * Lista en memoria que almacena todas las respuestas de seguridad cargadas desde el archivo.
     */
    private final List<RespuestaSeguridad> respuestas = new ArrayList<>();

    /**
     * Constructor de la clase `PreguntaDAOArchivoTxt`.
     * Inicializa la ruta y procede a cargar las preguntas y respuestas existentes
     * desde sus respectivos archivos.
     *
     * @param ruta La ruta del directorio base para los archivos de datos.
     */
    public PreguntaDAOArchivoTxt(String ruta) {
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
     * Carga las preguntas de seguridad desde el archivo `preguntas.txt`
     * a la lista en memoria `preguntas`.
     * Cada línea del archivo se espera en formato "ID|Pregunta".
     */
    private void cargarPreguntas() {
        File archivo = new File(ruta + "/preguntas.txt");
        if (!archivo.exists()) {
            System.out.println("Advertencia: El archivo de preguntas no existe en: " + archivo.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                PreguntaSeguridad pregunta = parsePregunta(linea);
                if (pregunta != null) {
                    preguntas.add(pregunta);
                } else {
                    System.err.println("Advertencia: Línea de pregunta inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar preguntas desde el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga las respuestas de seguridad desde el archivo `respuestas.txt`
     * a la lista en memoria `respuestas`.
     * Cada línea del archivo se espera en formato "Username|ID_Pregunta|Respuesta".
     */
    private void cargarRespuestas() {
        File archivo = new File(ruta + "/respuestas.txt");
        if (!archivo.exists()) {
            System.out.println("Advertencia: El archivo de respuestas no existe en: " + archivo.getAbsolutePath());
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 3) {
                    try {
                        respuestas.add(new RespuestaSeguridad(
                                partes[0].trim(),
                                Integer.parseInt(partes[1].trim()),
                                partes[2].trim()
                        ));
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico al cargar respuesta: " + linea + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Advertencia: Línea de respuesta inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar respuestas desde el archivo de texto: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actual de respuestas de seguridad en memoria (`respuestas`)
     * en el archivo `respuestas.txt`. Cada respuesta se serializa en una línea
     * con formato "Username|ID_Pregunta|Respuesta".
     * El archivo existente es sobrescrito.
     */
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
            System.err.println("Error al guardar respuestas en el archivo de texto: " + e.getMessage());
        }
    }

    /**
     * Parsea una línea de texto para crear un objeto {@link PreguntaSeguridad}.
     * Se espera el formato "ID|Pregunta".
     *
     * @param linea La cadena de texto a parsear.
     * @return Un objeto `PreguntaSeguridad` si la línea es válida, o `null` en caso contrario.
     */
    private PreguntaSeguridad parsePregunta(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 2) {
            try {
                return new PreguntaSeguridad(
                        Integer.parseInt(partes[0].trim()),
                        partes[1].trim()
                );
            } catch (NumberFormatException e) {
                System.err.println("Error de formato numérico al parsear pregunta: " + linea + " - " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Lista todas las preguntas de seguridad disponibles cargadas en memoria.
     *
     * @return Una nueva lista que contiene todas las preguntas de seguridad.
     */
    @Override
    public List<PreguntaSeguridad> listarTodasLasPreguntas() {
        return new ArrayList<>(preguntas); // Devuelve una copia para evitar modificaciones directas
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
        return preguntas.subList(0, Math.min(cantidad, preguntas.size())); // Devuelve un subconjunto
    }

    /**
     * Obtiene todas las respuestas de seguridad asociadas a un nombre de usuario específico.
     *
     * @param username El nombre de usuario para el cual se desean obtener las respuestas.
     * @return Una lista de {@link RespuestaSeguridad} que corresponden al usuario.
     */
    @Override
    public List<RespuestaSeguridad> obtenerRespuestasPorUsuario(String username) {
        if (username == null) return new ArrayList<>();
        return respuestas.stream()
                .filter(respuestaSeguridad -> username.equals(respuestaSeguridad.getUsername()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de preguntas de seguridad que un usuario aún no ha respondido.
     * Esto se determina comparando el conjunto de todas las preguntas con las IDs de
     * las preguntas ya respondidas por el usuario.
     *
     * @param username El nombre de usuario para el cual se buscan preguntas no respondidas.
     * @return Una lista de {@link PreguntaSeguridad} que el usuario no ha respondido.
     */
    @Override
    public List<PreguntaSeguridad> obtenerPreguntasPorUsuario(String username) {
        Set<Integer> idRespondidas = obtenerRespuestasPorUsuario(username).stream()
                .map(RespuestaSeguridad::getIdPregunta)
                .collect(Collectors.toSet());
        return preguntas.stream()
                .filter(preguntaSeguridad -> !idRespondidas.contains(preguntaSeguridad.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda una respuesta de seguridad para un usuario.
     * Si el usuario ya tenía una respuesta para la misma pregunta, esta se actualiza.
     * Después de la modificación en memoria, se persisten todas las respuestas en el archivo.
     * <p>
     * Nota: La condición de `removeIf` `respuestaSeguridad.getUsername().equals(respuestaSeguridad.getRespuesta())`
     * parece ser un error lógico. Debería ser `respuestaSeguridad.getUsername().equals(respuesta.getUsername())`.
     * Se ajusta la descripción para reflejar la intención de actualizar respuestas existentes.
     * </p>
     *
     * @param respuesta El objeto {@link RespuestaSeguridad} a guardar/actualizar.
     */
    @Override
    public void guardarRespuesta(RespuestaSeguridad respuesta) {
        // Corrección lógica en el removeIf si la intención es actualizar una respuesta existente
        // La condición original: respuestaSeguridad.getUsername().equals(respuestaSeguridad.getRespuesta())
        // probablemente sea un error. La intención debería ser:
        respuestas.removeIf(rs -> rs.getUsername().equals(respuesta.getUsername()) &&
                rs.getIdPregunta() == respuesta.getIdPregunta());

        respuestas.add(respuesta);
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