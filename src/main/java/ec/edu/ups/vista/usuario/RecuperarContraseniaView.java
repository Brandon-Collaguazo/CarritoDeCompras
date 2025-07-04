package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecuperarContraseniaView extends JDialog {
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
    private MensajeInternacionalizacionHandler mensaje;
    private List<PreguntaSeguridad> preguntas;
    private List<String> respuestas = new ArrayList<>();

    public RecuperarContraseniaView(JFrame parent, MensajeInternacionalizacionHandler mensaje) {
        super(parent, true);
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(500,500);
        setResizable(true);
        setLocationRelativeTo(getParent());

        //Íconos para los botones
        URL recuperarURL = RecuperarContraseniaView.class.getClassLoader().getResource("imagenes/recuperarcontrasenia.png");
        if(recuperarURL != null) {
            ImageIcon iconBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconBtnRecuperar);
        } else {
            System.err.println("Error");
        }

        URL cancelarURL = RecuperarContraseniaView.class.getClassLoader().getResource("imagenes/cancelar.png");
        if(cancelarURL != null) {
            ImageIcon iconBtnCancelar = new ImageIcon(cancelarURL);
            btnRecuperar.setIcon(iconBtnCancelar);
        } else {
            System.err.println("Error");
        }
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("recuperar.contrasenia.titulo"));

        lblTitulo.setText(mensaje.get("recuperar.contrasenia.titulo"));

        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("nueva.contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.contrasenia"));

        btnRecuperar.setText(mensaje.get("btn.recuperar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        this.respuestas.clear(); // Limpiar respuestas anteriores

        for (PreguntaSeguridad pregunta : preguntas) {
            String respuesta = JOptionPane.showInputDialog(
                    this,
                    pregunta.getTextoPregunta(),
                    mensaje.get("responda.pregunta"),
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == null) {
                respuestas = null;
                return;
            }
            respuestas.add(respuesta.trim());
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

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
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

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JLabel getLblConfirmar() {
        return lblConfirmar;
    }

    public void setLblConfirmar(JLabel lblConfirmar) {
        this.lblConfirmar = lblConfirmar;
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
