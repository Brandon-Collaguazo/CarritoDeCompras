package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRegistroView extends JFrame {
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
    private JButton btnSiguiente;
    private MensajeInternacionalizacionHandler mensaje;
    private List<String> preguntaSelecionada = new ArrayList<>();
    private List<String> respuestas = new ArrayList<>();

    public UsuarioRegistroView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setTitle("Registro de Usuario");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Íconos para los botones
        URL registrarURL = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/registrarusuario.png");
        if(registrarURL != null) {
            ImageIcon iconBtnRegistrar = new ImageIcon(registrarURL);
            btnRegistrar.setIcon(iconBtnRegistrar);
        } else {
            System.err.println("Error");
        }

        URL siguienteURL = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/siguiente.png");
        if(siguienteURL != null) {
            ImageIcon iconBtnRegistrar = new ImageIcon(siguienteURL);
            btnRegistrar.setIcon(iconBtnRegistrar);
        } else {
            System.err.println("Error");
        }
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.registro.titulo"));

        lblTitulo.setText(mensaje.get("usuario.registro.titulo"));
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

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    public void mostrarPreguntasSeguridad(List<PreguntaSeguridad> preguntas) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for(PreguntaSeguridad pregunta : preguntas) {
            modelo.addElement(pregunta.getTextoPregunta());
        }
        cbxPregunta.setModel(modelo);
        cbxPregunta.setSelectedIndex(-1);
    }

    private void guardarPreguntaYAvanzar() {
        String pregunta = (String) cbxPregunta.getSelectedItem();
        String respuesta = txtRespuesta.getText().trim();
        if (pregunta == null || respuesta.isEmpty()) {
            mostrarMensaje("seleccion.pregunta");
            return;
        }

        preguntaSelecionada.add(pregunta);
        respuestas.add(respuesta);
        txtRespuesta.setText("");
        cbxPregunta.setSelectedIndex(-1);
        if (preguntaSelecionada.size() >= 3) {
            btnSiguiente.setEnabled(false);
            mostrarMensaje(mensaje.get("preguntas.guardadas"));
        }
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public void setTxtFechaNacimiento(JTextField txtFechaNacimiento) {
        this.txtFechaNacimiento = txtFechaNacimiento;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JPasswordField getTxtConfirmarPassword() {
        return txtConfirmarPassword;
    }

    public void setTxtConfirmarPassword(JPasswordField txtConfirmarPassword) {
        this.txtConfirmarPassword = txtConfirmarPassword;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JComboBox getCbxPregunta() {
        return cbxPregunta;
    }

    public void setCbxPregunta(JComboBox<String> cbxPregunta) {
        this.cbxPregunta = cbxPregunta;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public void setBtnSiguiente(JButton btnSiguiente) {
        this.btnSiguiente = btnSiguiente;
    }

    public List<String> getPreguntaSelecionada() {
        return preguntaSelecionada;
    }

    public void setPreguntaSelecionada(List<String> preguntaSelecionada) {
        this.preguntaSelecionada = preguntaSelecionada;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }


    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
    }
}
