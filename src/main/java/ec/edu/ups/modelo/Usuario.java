package ec.edu.ups.modelo;

import java.util.*;

public class Usuario {
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String telefono;
    private String correo;
    private String username;
    private String contrasenia;
    private Rol rol;
    private List<String> preguntas;
    private List<String> respuestas;

    public Usuario(String nombreCompleto, Date fechaNacimiento, String telefono,
                   String correo, String username, String contrasenia, Rol rol) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.preguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    public Usuario() {
        this.preguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    public void addPreguntaSeguridad(String pregunta, String respuesta) {
        preguntas.add(pregunta);
        respuestas.add(respuesta);
    }

    public List<String> getPreguntas() {
        return preguntas;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombreCompleto, usuario.nombreCompleto) &&
                Objects.equals(fechaNacimiento, usuario.fechaNacimiento) &&
                Objects.equals(telefono, usuario.telefono) &&
                Objects.equals(correo, usuario.correo) &&
                Objects.equals(username, usuario.username) &&
                Objects.equals(contrasenia, usuario.contrasenia) &&
                rol == usuario.rol &&
                Objects.equals(preguntas, usuario.preguntas) &&
                Objects.equals(respuestas, usuario.respuestas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCompleto, fechaNacimiento, telefono, correo, username, contrasenia, rol, preguntas, respuestas);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", preguntas=" + preguntas +
                ", respuestas=" + respuestas +
                '}';
    }
}
