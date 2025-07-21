package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Modelo Pregunta Seguridad
 * Clase que representa una pregunta de seguridad utilizada para verificación de identidad.
 * Cada pregunta tiene un identificador único y un texto que formula la pregunta.
 */
public class PreguntaSeguridad implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String textoPregunta;

    /**
     * Constructor que crea una pregunta de seguridad con: id y texto específico
     * @param id Identificador único de la pregunta
     * @param textoPregunta textoPregunta texto completo de la pregunta
     */
    public PreguntaSeguridad(int id, String textoPregunta) {
        this.id = id;
        this.textoPregunta = textoPregunta;
    }

    /**
     * Constructor por defecto que crea una pregunta de seguridad vacía
     */
    public PreguntaSeguridad() {

    }

    //Métodos Getters y Setters

    /**
     * Obtiene el identificador único de la pregunta
     * @return El id de la pregunta
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único de la pregunta
     * @param id Nuevo identificador para la pregunta
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el texto de la pregunta de seguridad
     * @return El texto completo de la pregunta
     */
    public String getTextoPregunta() {
        return textoPregunta;
    }

    /**
     * Establece el texto de la pregunta de seguridad
     * @param textoPregunta Nuevo texto para la pregunta
     */
    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    /**
     * Compara esta pregunta de seguridad con otro objeto para determinar igualdad
     * @param o Objeto a comparar
     * @return true si son iguales (mismo id y texto), false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PreguntaSeguridad that = (PreguntaSeguridad) o;
        return Objects.equals(id, that.id) && Objects.equals(textoPregunta, that.textoPregunta);
    }

    /**
     * Genera un código hash para la pregunta de seguridad basado en su id y texto
     * @return Código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, textoPregunta);
    }

    /**
     * Representación en String de la pregunta de seguridad
     * @return String con formato : "Pregunta seguridad{id='X', Texto pregunta='Y}"
     */
    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id='" + id + '\'' +
                ", textoPregunta='" + textoPregunta + '\'' +
                '}';
    }
}
