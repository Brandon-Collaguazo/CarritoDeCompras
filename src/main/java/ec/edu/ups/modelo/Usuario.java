package ec.edu.ups.modelo;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Usuario {
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String telefono;
    private String correo;
    private String username;
    private String contrasenia;
    private Rol rol;
    private List<Integer> idPreguntas;
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
        this.idPreguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    public Usuario() {
        this.idPreguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    public void addPreguntaSeguridad(int idPregunta, String respuesta) {
        idPreguntas.add(idPregunta);
        respuestas.add(respuesta.toLowerCase().trim());
    }

    public boolean verificarRespuesta(int idPregunta, String respuesta) {
        int indice = idPreguntas.indexOf(idPregunta);
        if(indice == -1) {
            return  false;
        }
        return respuestas.get(indice).equalsIgnoreCase(respuesta.trim());
    }

    public int obtenerPreguntaAleatoria() {
        if(idPreguntas.isEmpty()) {
            return -1;
        }
        int random = ThreadLocalRandom.current().nextInt(idPreguntas.size());
        return idPreguntas.get(random);
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


}
