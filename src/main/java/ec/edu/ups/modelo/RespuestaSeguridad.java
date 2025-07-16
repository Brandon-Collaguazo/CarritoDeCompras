package ec.edu.ups.modelo;

import java.util.Objects;

/**
 * Modelo Respuesta Seguridad
 * Clase que representa una respuesta de seguridad asociada a un usuario.
 * Almacena la combinación de usuario, pregunta de seguridad y respuesta correspondiente
 * para propósitos de verificación de identidad.
 */
public class RespuestaSeguridad {
    /**
     * Nombre de usuario al que pertenece la respuesta
     */
    private String username;

    /**
     * Identificador de la pregunta de seguridad respondida.
     */
    private int idPregunta;

    /**
     * Respuesta por defecto que crea una respuesta vacía
     */
    private String respuesta;

    /**
     * Constructor por defecto que crea una respuesta vacía
     */
    public RespuestaSeguridad() {

    }

    /**
     * Constructor que inicializa todos los campos de la respuesta de seguridad
     * @param username
     * @param idPregunta
     * @param respuesta
     */
    public RespuestaSeguridad(String username, int idPregunta, String respuesta) {
        this.username = username;
        this.idPregunta = idPregunta;
        this.respuesta = respuesta;
    }

    //Métodos Getters y Setters

    /**
     * Obtiene el nombre de usuario asociado
     * @return Nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario asociado
     * @param username Nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el id de la pregunta de seguridad
     * @return Identificador numérico de la pregunta
     */
    public int getIdPregunta() {
        return idPregunta;
    }

    /**
     * Establece el id de la pregunta de seguridad
     * @param idPregunta Identificador de la pregunta
     */
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    /**
     * Obtiene la respuesta de seguridad
     * @return Texto de la pregunta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta de seguridad
     * @param respuesta Texto de la respuesta
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Compara esta respuesta con otro objeto para determinar igualdad
     * @param o Objeto a comparar
     * @return true si son iguales (mismo username, idPregunta y respuesta), false en
     * caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaSeguridad that = (RespuestaSeguridad) o;
        return idPregunta == that.idPregunta && Objects.equals(username, that.username) && Objects.equals(respuesta, that.respuesta);
    }

    /**
     * Genera código hash basado en username, idPregunta y respuesta
     * @return Código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, idPregunta, respuesta);
    }

    /**
     * Representación en String de la respuesta de seguridad
     * @return String con formato: "RespuestaSeguridad{username='X', idPregunta='Y', respuesta'Z'}"
     */
    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "username='" + username + '\'' +
                ", idPregunta=" + idPregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
