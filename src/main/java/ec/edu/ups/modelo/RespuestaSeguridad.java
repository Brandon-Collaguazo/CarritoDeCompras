package ec.edu.ups.modelo;

import java.util.Objects;

public class RespuestaSeguridad {
    private PreguntaSeguridad pregunta;
    private String respuesta;
    private Usuario usuario;

    public RespuestaSeguridad() {

    }

    public RespuestaSeguridad(PreguntaSeguridad pregunta, String respuesta, Usuario usuario) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.usuario = usuario;
    }

    public PreguntaSeguridad getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaSeguridad pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaSeguridad that = (RespuestaSeguridad) o;
        return Objects.equals(pregunta, that.pregunta) && Objects.equals(respuesta, that.respuesta) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pregunta, respuesta, usuario);
    }

    @Override
    public String toString() {
        return "RespuestaSeguridad{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
