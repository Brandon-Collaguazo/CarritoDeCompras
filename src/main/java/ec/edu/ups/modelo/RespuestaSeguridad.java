package ec.edu.ups.modelo;

import java.util.Objects;

public class RespuestaSeguridad {
    private String username;
    private int idPregunta;
    private String respuesta;

    public RespuestaSeguridad() {

    }

    public RespuestaSeguridad(String username, int idPregunta, String respuesta) {
        this.username = username;
        this.idPregunta = idPregunta;
        this.respuesta = respuesta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaSeguridad that = (RespuestaSeguridad) o;
        return idPregunta == that.idPregunta && Objects.equals(username, that.username) && Objects.equals(respuesta, that.respuesta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, idPregunta, respuesta);
    }

    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "username='" + username + '\'' +
                ", idPregunta=" + idPregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
