package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public RecuperarContraseniaView() {
        this.mensaje = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();
        actualizarTextos();
    }

    public RecuperarContraseniaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

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

    public void mostrarPregunta(PreguntaSeguridad pregunta) {
        if(pregunta != null) {
            txtPregunta.setText(pregunta.getTextoPregunta());
            txtRespuesta.setEnabled(true);
            txtRespuesta.requestFocus();
        } else {
            txtPregunta.setText("");
        }
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    public JTextField getTxtPregunta() {
        return txtPregunta;
    }

    public void setTxtPregunta(JTextField txtPregunta) {
        this.txtPregunta = txtPregunta;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public void setBtnRecuperar(JButton btnRecuperar) {
        this.btnRecuperar = btnRecuperar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public List<PreguntaSeguridad> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaSeguridad> preguntas) {
        this.preguntas = preguntas;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
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
}
