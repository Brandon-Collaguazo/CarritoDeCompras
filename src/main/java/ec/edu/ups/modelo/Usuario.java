package ec.edu.ups.modelo;

import ec.edu.ups.excepciones.CedulaException;
import ec.edu.ups.excepciones.ContraseniaException;
import ec.edu.ups.excepciones.CorreoException;
import ec.edu.ups.excepciones.FechaException;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Modelo Usuario
 * Clase que representa un usuario del sistema.
 * Contiene información general, credenciales de acceso, rol y preguntas de seguridad.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cedula;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String telefono;
    private String correo;
    private String username;
    private String contrasenia;
    private Rol rol;
    private List<Integer> idPreguntas;
    private List<String> respuestas;

    /**
     * Constructor principal que inicializa todos los campos básicos del usuario
     * @param nombreCompleto Nombre completo del usuario
     * @param fechaNacimiento Fecha de nacimiento del usuario
     * @param telefono Número telefónico del usuario
     * @param correo Correo personal del usaurio
     * @param username Nombre de usuario único
     * @param contrasenia Contraseña
     * @param rol Rol del usuario
     */
    public Usuario(String cedula, String nombreCompleto, Date fechaNacimiento, String telefono,
                   String correo, String username, String contrasenia, Rol rol) {
        this.cedula = cedula;
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

    /**
     * Constructor por defecto que inicializa listas vacías
     */
    public Usuario() {
        this.idPreguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    /**
     * Añade una pregunta de seguridad con su respuesta
     * @param idPregunta Id de la pregunta de seguridad
     * @param respuesta Respuesta a la pregunta
     */
    public void addPreguntaSeguridad(int idPregunta, String respuesta) {
        idPreguntas.add(idPregunta);
        respuestas.add(respuesta.toLowerCase().trim());
    }

    /**
     * Verifica si una respuesta coincide con la almacenada para una pregunta
     * @param idPregunta Id de la pregunta a verificar
     * @param respuesta Respuesta a comparar
     * @return true si la repsuesta coincide, en caso contrario retorna false
     */
    public boolean verificarRespuesta(int idPregunta, String respuesta) {
        int indice = idPreguntas.indexOf(idPregunta);
        if(indice == -1) {
            return  false;
        }
        return respuestas.get(indice).equalsIgnoreCase(respuesta.trim());
    }

    /**
     * Obtiene una pregunta de seguridad aleatoria asociada al usuario
     * @return ID de una pregunta aleatoria, o -1 si no hay preguntas
     */
    public int obtenerPreguntaAleatoria() {
        if(idPreguntas.isEmpty()) {
            return -1;
        }
        int random = ThreadLocalRandom.current().nextInt(idPreguntas.size());
        return idPreguntas.get(random);
    }

    public void validarCedula() throws CedulaException {
        if(cedula == null || cedula.length() != 10 || !cedula.matches("\\d+")) {
            throw new CedulaException("cedula.invalida");
        }

        int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            suma += (i % 2 == 0) ? digito * 2 : digito;
        }

        int verfificadorCalculado = (10 - (suma % 10)) % 10;
        if (verfificadorCalculado != ultimoDigito) {
            throw new CedulaException("digito.verificador");
        }
    }

    public void validarContrasenia() throws ContraseniaException {
        if (contrasenia == null || contrasenia.isEmpty()) {
            throw new ContraseniaException("contrasenia.vacia");
        }

        if (contrasenia == null || contrasenia.length() < 8) {
            throw new ContraseniaException("contrasenia.corta");
        }

        if (!contrasenia.matches(".*[A-Z].*")) {
            throw new ContraseniaException("contrasenia.sin.mayuscula");
        }

        if (!contrasenia.matches(".*\\d.*")) {
            throw new ContraseniaException("contrasenia.sin.numero");
        }

        if (!contrasenia.matches(".*[!@#$%^&*()_+].*")) {
            throw new ContraseniaException("contrasenia.sin.caracter");
        }
    }

    public void validarCorreo() throws CorreoException {
        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new CorreoException();
        }
    }

    public void validarFecha(String fecha) throws FechaException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);

        try {
            fechaNacimiento = simpleDateFormat.parse(fecha);
        } catch (ParseException e) {
            throw new FechaException();
        }
    }

    public void validar(String fechaNacimientoStr) throws CedulaException, ContraseniaException, CorreoException, FechaException {
        validarCedula();
        validarContrasenia();
        validarCorreo();
        validarFecha(fechaNacimientoStr);
    }

    //Métodos Getters y Setters

    /**
     * @return Cédula de identidad del usuario
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula Nueva cédula para el usuario
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return Lista de respuestas de seguridad
     */
    public List<String> getRespuestas() {
        return respuestas;
    }

    /**
     * @return Nombre completo del usuario
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo
     * @param nombreCompleto No puede ser nulo
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return Fecha de nacimiento del usuario
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento Nueva fecha de nacimiento
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return Número telefónico del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono Nuevo número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return Correo personal del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo Nuevo correo del usuario
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return Nombre de usuario para autenticación
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username Nuevo nombre único para el usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Contraseña del usuario
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia Nueva contrasenia para el usuario
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return Rol del usuario
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol Nuevo rol para el usuario
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
