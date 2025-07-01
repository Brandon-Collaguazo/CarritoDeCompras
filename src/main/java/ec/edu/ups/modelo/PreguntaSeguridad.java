package ec.edu.ups.modelo;

import java.util.Objects;

public class PreguntaSeguridad {
    private int id;
    private String textoPregunta;

    public PreguntaSeguridad() {

    }

    public PreguntaSeguridad(int id, String textoPregunta) {
        this.id = id;
        this.textoPregunta = textoPregunta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PreguntaSeguridad that = (PreguntaSeguridad) o;
        return Objects.equals(id, that.id) && Objects.equals(textoPregunta, that.textoPregunta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textoPregunta);
    }

    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id='" + id + '\'' +
                ", textoPregunta='" + textoPregunta + '\'' +
                '}';
    }
}
