package ec.edu.ups.vista.autenticacion;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para el registro de nuevos usuarios en el sistema.
 * Permite ingresar información personal, credenciales y preguntas de seguridad.
 */
public class UsuarioRegistroView extends JFrame {
    // Componentes de la interfaz
    private JPanel pnlPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private JPasswordField txtConfirmarPassword;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblConfirmarPassword;
    private JLabel lblTitulo;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JTextField txtTelefono;
    private JLabel lblTelefono;
    private JLabel lblFechaDeNacimiento;
    private JTextField txtFechaNacimiento;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JComboBox<String> cbxPregunta;
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JLabel lblRespuesta;
    private JButton btnSiguiente;
    private JLabel lblPreguntas;
    private JLabel lblCedula;
    private JTextField txtCedula;

    // Manejadores y datos
    private MensajeInternacionalizacionHandler mensaje;
    private List<String> preguntaSelecionada = new ArrayList<>();
    private List<String> respuestas = new ArrayList<>();

    /**
     * Constructor por defecto que inicializa la vista con internacionalización en español (EC).
     */
    public UsuarioRegistroView() {
        this.mensaje = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();
        actualizarTextos();
    }

    /**
     * Constructor que permite especificar un manejador de internacionalización personalizado.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos de la interfaz.
     */
    public UsuarioRegistroView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, posición, comportamiento y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setTitle("Registro de Usuario");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Carga de íconos para los botones
        URL registrarURL = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/registrarusuario.png");
        if(registrarURL != null) {
            ImageIcon iconBtnRegistrar = new ImageIcon(registrarURL);
            btnRegistrar.setIcon(iconBtnRegistrar);
        } else {
            System.err.println("Error al cargar ícono de registrar");
        }

        URL siguienteURL = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/siguiente.png");
        if(siguienteURL != null) {
            ImageIcon iconBtnRegistrar = new ImageIcon(siguienteURL);
            btnRegistrar.setIcon(iconBtnRegistrar);
        } else {
            System.err.println("Error al cargar ícono de siguiente");
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.registro.titulo"));

        lblTitulo.setText(mensaje.get("usuario.registro.titulo"));

        lblPregunta.setText(mensaje.get("usuario.pregunta"));
        lblRespuesta.setText(mensaje.get("usuario.respuesta"));

        lblCedula.setText(mensaje.get("usuario.cedula"));
        lblNombre.setText(mensaje.get("registro.nombre"));
        lblFechaDeNacimiento.setText(mensaje.get("fecha.nacimiento"));
        lblTelefono.setText(mensaje.get("telefono"));
        lblCorreo.setText(mensaje.get("correo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("contrasenia"));
        lblConfirmarPassword.setText(mensaje.get("confirmar.password"));

        btnRegistrar.setText(mensaje.get("registrar"));
        btnSiguiente.setText(mensaje.get("siguiente"));
    }

    /**
     * Cambia el idioma de la interfaz.
     *
     * @param lenguaje Código del lenguaje (ej. "es", "en").
     * @param pais Código del país (ej. "EC", "US").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    /**
     * Habilita todos los campos de entrada de datos.
     */
    public void habilitarCampos() {
        txtCedula.setEnabled(true);
        txtNombre.setEnabled(true);
        txtFechaNacimiento.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtUsername.setEnabled(true);
        txtPassword.setEnabled(true);
        txtConfirmarPassword.setEnabled(true);

        // Deshabilitar las preguntas ya respondidas
        txtRespuesta.setEnabled(false);
    }

    /**
     * Configura una pregunta de seguridad en la interfaz.
     *
     * @param pregunta La pregunta de seguridad a mostrar.
     * @param numPregunta El número de la pregunta actual (1-3).
     */
    public void configurarPreguntaSeguridad(PreguntaSeguridad pregunta, int numPregunta) {
        if (pregunta == null) return;

        lblPreguntas.setText(pregunta.getTextoPregunta());
        txtRespuesta.setText("");
        txtRespuesta.requestFocus();
        btnSiguiente.setText(numPregunta == 3 ? mensaje.get("finalizar.preguntas") : mensaje.get("siguiente"));
    }

    /**
     * Obtiene la respuesta ingresada para la pregunta de seguridad.
     *
     * @return La respuesta ingresada, sin espacios al inicio o final.
     */
    public String obtenerRespuestaSeguridad() {
        return txtRespuesta.getText().trim();
    }

    /**
     * Valida que todos los campos obligatorios estén completos y sean válidos.
     *
     * @return true si todos los campos son válidos, false si hay errores.
     */
    public boolean validarCampos() {
        if (getTxtCedula().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.cedula.vacio");
            txtCedula.requestFocus();
            return false;
        }

        if (getTxtNombre().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.nombre.vacio");
            txtNombre.requestFocus();
            return false;
        }

        if (getTxtFechaNacimiento().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.fecha.vacio");
            txtFechaNacimiento.requestFocus();
            return false;
        }

        if (getTxtTelefono().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.telefono.vacio");
            txtTelefono.requestFocus();
            return false;
        }

        if (getTxtCorreo().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.correo.vacio");
            txtCorreo.requestFocus();
            return false;
        }

        if (getTxtUsername().getText().trim().isEmpty()) {
            mostrarMensaje("usuario.username.vacio");
            txtUsername.requestFocus();
            return false;
        }

        String password = new String(getTxtPassword().getPassword());
        if (password.trim().isEmpty()) {
            mostrarMensaje("usuario.contrasenia.vacio");
            getTxtPassword().requestFocus();
            return false;
        }

        String confirmar = new String(getTxtConfirmarPassword().getPassword());
        if (!password.equals(confirmar)) {
            mostrarMensaje("contrasenias.no.coinciden");
            getTxtConfirmarPassword().requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Limpia todos los campos de entrada de datos.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
    }

    // Métodos getters y setters

    /**
     * Obtiene el panel principal de la vista.
     * @return El panel principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal El panel principal a establecer.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el campo de texto para la cédula.
     * @return El campo de cédula.
     */
    public JTextField getTxtCedula() {
        return txtCedula;
    }

    /**
     * Establece el campo de texto para la cédula.
     * @param txtCedula El campo de cédula a establecer.
     */
    public void setTxtCedula(JTextField txtCedula) {
        this.txtCedula = txtCedula;
    }

    /**
     * Obtiene el campo de texto para el nombre de usuario.
     * @return El campo de nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsername El campo de nombre de usuario a establecer.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el campo de texto para el nombre completo.
     * @return El campo de nombre completo.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre completo.
     * @param txtNombre El campo de nombre completo a establecer.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el teléfono.
     * @return El campo de teléfono.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Establece el campo de texto para el teléfono.
     * @param txtTelefono El campo de teléfono a establecer.
     */
    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    /**
     * Obtiene el campo de texto para la fecha de nacimiento.
     * @return El campo de fecha de nacimiento.
     */
    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    /**
     * Establece el campo de texto para la fecha de nacimiento.
     * @param txtFechaNacimiento El campo de fecha de nacimiento a establecer.
     */
    public void setTxtFechaNacimiento(JTextField txtFechaNacimiento) {
        this.txtFechaNacimiento = txtFechaNacimiento;
    }

    /**
     * Obtiene el campo de texto para el correo electrónico.
     * @return El campo de correo electrónico.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Establece el campo de texto para el correo electrónico.
     * @param txtCorreo El campo de correo electrónico a establecer.
     */
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * Obtiene el campo de contraseña.
     * @return El campo de contraseña.
     */
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    /**
     * Establece el campo de contraseña.
     * @param txtPassword El campo de contraseña a establecer.
     */
    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    /**
     * Obtiene el campo de confirmación de contraseña.
     * @return El campo de confirmación de contraseña.
     */
    public JPasswordField getTxtConfirmarPassword() {
        return txtConfirmarPassword;
    }

    /**
     * Establece el campo de confirmación de contraseña.
     * @param txtConfirmarPassword El campo de confirmación a establecer.
     */
    public void setTxtConfirmarPassword(JPasswordField txtConfirmarPassword) {
        this.txtConfirmarPassword = txtConfirmarPassword;
    }

    /**
     * Obtiene el campo de texto para la respuesta de seguridad.
     * @return El campo de respuesta de seguridad.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto para la respuesta de seguridad.
     * @param txtRespuesta El campo de respuesta a establecer.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el combo box de preguntas de seguridad.
     * @return El combo box de preguntas.
     */
    public JComboBox<String> getCbxPregunta() {
        return cbxPregunta;
    }

    /**
     * Establece el combo box de preguntas de seguridad.
     * @param cbxPregunta El combo box de preguntas a establecer.
     */
    public void setCbxPregunta(JComboBox<String> cbxPregunta) {
        this.cbxPregunta = cbxPregunta;
    }

    /**
     * Obtiene la lista de preguntas seleccionadas.
     * @return Lista de preguntas seleccionadas.
     */
    public List<String> getPreguntaSelecionada() {
        return preguntaSelecionada;
    }

    /**
     * Establece la lista de preguntas seleccionadas.
     * @param preguntaSelecionada Lista de preguntas a establecer.
     */
    public void setPreguntaSelecionada(List<String> preguntaSelecionada) {
        this.preguntaSelecionada = preguntaSelecionada;
    }

    /**
     * Obtiene la lista de respuestas de seguridad.
     * @return Lista de respuestas.
     */
    public List<String> getRespuestas() {
        return respuestas;
    }

    /**
     * Establece la lista de respuestas de seguridad.
     * @param respuestas Lista de respuestas a establecer.
     */
    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    /**
     * Obtiene el botón de registro.
     * @return El botón de registro.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Establece el botón de registro.
     * @param btnRegistrar El botón de registro a establecer.
     */
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    /**
     * Obtiene el botón de siguiente.
     * @return El botón de siguiente.
     */
    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    /**
     * Establece el botón de siguiente.
     * @param btnSiguiente El botón de siguiente a establecer.
     */
    public void setBtnSiguiente(JButton btnSiguiente) {
        this.btnSiguiente = btnSiguiente;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return El manejador de mensajes internacionalizados.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje El manejador de mensajes a establecer.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje al usuario en un diálogo.
     * @param keyMensaje Clave del mensaje a mostrar (según el archivo de internacionalización).
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}