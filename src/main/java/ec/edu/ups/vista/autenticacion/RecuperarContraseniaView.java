package ec.edu.ups.vista.autenticacion;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Visita para la recuperación de contraseña de usuarios.
 * Permite a los usuarios recuperar su contraseña mediante preguntas de seguridad
 */
public class RecuperarContraseniaView extends JFrame {
    private JPanel pnlPrincipal;
    private JTextField txtUsuario;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JButton btnRecuperar;
    private JButton btnCancelar;
    private JLabel lblPregunta;
    private JLabel lblRespuesta;
    private JTextField txtPregunta;
    private JTextField txtRespuesta;
    private JButton btnBuscar;
    private MensajeInternacionalizacionHandler mensaje;
    private List<PreguntaSeguridad> preguntas;
    private List<String> respuestas = new ArrayList<>();

    /**
     * Constructor por defecto que inicializa la vista con internacionalización en español (EC).
     */
    public RecuperarContraseniaView() {
        this.mensaje = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();
        actualizarTextos();
    }

    /**
     * Constructor que permite especificar un manejador de internacionalización personalizado.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos de la interfaz.
     */
    public RecuperarContraseniaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, posición y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(700,400);
        setResizable(true);
        setLocationRelativeTo(getParent());

        //Íconos para los botones
        URL buscarURL = RecuperarContraseniaView.class.getClassLoader().getResource("imagenes/buscarUsuario.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error no se cargó el buscar en recuperar");
        }

        URL recuperarURL = RecuperarContraseniaView.class.getClassLoader().getResource("imagenes/recuperarcontrasenia.png");
        if(recuperarURL != null) {
            ImageIcon iconBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconBtnRecuperar);
        } else {
            System.err.println("Error no se cargó el icono de recuperar en su ventana");
        }

        URL cancelarURL = RecuperarContraseniaView.class.getClassLoader().getResource("imagenes/cancelar.png");
        if(cancelarURL != null) {
            ImageIcon iconBtnCancelar = new ImageIcon(cancelarURL);
            btnCancelar.setIcon(iconBtnCancelar);
        } else {
            System.err.println("Error no se cargó el boton cancelar en recuperar");
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("recuperar.contrasenia.titulo"));

        lblTitulo.setText(mensaje.get("recuperar.contrasenia.titulo"));

        lblPregunta.setText(mensaje.get("usuario.pregunta"));
        lblRespuesta.setText(mensaje.get("usuario.respuesta"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("nueva.contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.password"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnRecuperar.setText(mensaje.get("btn.recuperar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }

    /**
     * Muestra una pregunta de seguridad en la interfaz.
     *
     * @param pregunta La pregunta de seguridad a mostrar, o null para limpiar el campo.
     */
    public void mostrarPregunta(PreguntaSeguridad pregunta) {
        if(pregunta != null) {
            txtPregunta.setText(pregunta.getTextoPregunta());
            txtRespuesta.setEnabled(true);
            txtRespuesta.requestFocus();
        } else {
            txtPregunta.setText("");
        }
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
     * Obtiene el campo de texto para el nombre de usuario.
     * @return El campo de texto del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsuario El campo de texto a establecer.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el campo de contraseña para la nueva contraseña.
     * @return El campo de contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de contraseña para la nueva contraseña.
     * @param txtContrasenia El campo de contraseña a establecer.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el campo de confirmación de contraseña.
     * @return El campo de confirmación de contraseña.
     */
    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    /**
     * Establece el campo de confirmación de contraseña.
     * @param txtConfirmar El campo de confirmación a establecer.
     */
    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    /**
     * Obtiene el campo que muestra la pregunta de seguridad.
     * @return El campo de pregunta de seguridad.
     */
    public JTextField getTxtPregunta() {
        return txtPregunta;
    }

    /**
     * Establece el campo que muestra la pregunta de seguridad.
     * @param txtPregunta El campo de pregunta a establecer.
     */
    public void setTxtPregunta(JTextField txtPregunta) {
        this.txtPregunta = txtPregunta;
    }

    /**
     * Obtiene el campo para ingresar la respuesta de seguridad.
     * @return El campo de respuesta de seguridad.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo para ingresar la respuesta de seguridad.
     * @param txtRespuesta El campo de respuesta a establecer.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el botón para recuperar la contraseña.
     * @return El botón de recuperación.
     */
    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    /**
     * Establece el botón para recuperar la contraseña.
     * @param btnRecuperar El botón de recuperación a establecer.
     */
    public void setBtnRecuperar(JButton btnRecuperar) {
        this.btnRecuperar = btnRecuperar;
    }

    /**
     * Obtiene el botón para cancelar la operación.
     * @return El botón de cancelación.
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Establece el botón para cancelar la operación.
     * @param btnCancelar El botón de cancelación a establecer.
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * Obtiene el botón para buscar el usuario.
     * @return El botón de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar el usuario.
     * @param btnBuscar El botón de búsqueda a establecer.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la lista de preguntas de seguridad.
     * @return Lista de preguntas de seguridad.
     */
    public List<PreguntaSeguridad> getPreguntas() {
        return preguntas;
    }

    /**
     * Establece la lista de preguntas de seguridad.
     * @param preguntas Lista de preguntas a establecer.
     */
    public void setPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
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