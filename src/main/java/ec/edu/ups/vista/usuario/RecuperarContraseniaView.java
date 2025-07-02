package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.List;

public class RecuperarContraseniaView extends JDialog {
    private JPanel pnlPrincipal;
    private JTextField txtUsuario;
    private JTextField txtRes1;
    private JComboBox cbxPregunta1;
    private JComboBox cbxPregunta2;
    private JTextField txtRes2;
    private JComboBox cbxPregunta3;
    private JTextField txtRes3;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPregunta1;
    private JLabel lblRespuesta1;
    private JLabel lblPregunta2;
    private JLabel lblRespuesta2;
    private JLabel lblPregunta3;
    private JLabel lblRespuesta3;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JButton btnRecuperar;
    private JButton btnCancelar;
    private MensajeInternacionalizacionHandler mensaje;

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

        URL recuperarURL = RecuperarContraseniaView.class.getResource("imagenes/recuperarcontrasenia.png");
        if(recuperarURL != null) {
            ImageIcon iconBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconBtnRecuperar);
        } else {
            System.err.println("Error");
        }

        URL cancelarURL = RecuperarContraseniaView.class.getResource("imagenes/cancelar.png");
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
        lblPregunta1.setText("1. " + mensaje.get("pregunta.1"));
        lblPregunta2.setText("2. " + mensaje.get("pregunta.2"));
        lblPregunta3.setText("3. " + mensaje.get("pregunta.3"));
        lblContrasenia.setText(mensaje.get("nueva.contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.contrasenia"));

        btnRecuperar.setText(mensaje.get("btn.recuperar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }

    public void cargarPreguntas(List<PreguntaSeguridad> preguntas) {
        if(preguntas.size() > 0) lblPregunta1.setText("1. " + preguntas.get(0).getTextoPregunta());
        if(preguntas.size() > 1) lblPregunta2.setText("2. " + preguntas.get(1).getTextoPregunta());
        if(preguntas.size() > 2) lblPregunta3.setText("3. " + preguntas.get(2).getTextoPregunta());
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

    public JTextField getTxtRes1() {
        return txtRes1;
    }

    public void setTxtRes1(JTextField txtRes1) {
        this.txtRes1 = txtRes1;
    }

    public JComboBox getCbxPregunta1() {
        return cbxPregunta1;
    }

    public void setCbxPregunta1(JComboBox cbxPregunta1) {
        this.cbxPregunta1 = cbxPregunta1;
    }

    public JComboBox getCbxPregunta2() {
        return cbxPregunta2;
    }

    public void setCbxPregunta2(JComboBox cbxPregunta2) {
        this.cbxPregunta2 = cbxPregunta2;
    }

    public JTextField getTxtRes2() {
        return txtRes2;
    }

    public void setTxtRes2(JTextField txtRes2) {
        this.txtRes2 = txtRes2;
    }

    public JComboBox getCbxPregunta3() {
        return cbxPregunta3;
    }

    public void setCbxPregunta3(JComboBox cbxPregunta3) {
        this.cbxPregunta3 = cbxPregunta3;
    }

    public JTextField getTxtRes3() {
        return txtRes3;
    }

    public void setTxtRes3(JTextField txtRes3) {
        this.txtRes3 = txtRes3;
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

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public void setLblPregunta1(JLabel lblPregunta1) {
        this.lblPregunta1 = lblPregunta1;
    }

    public JLabel getLblRespuesta1() {
        return lblRespuesta1;
    }

    public void setLblRespuesta1(JLabel lblRespuesta1) {
        this.lblRespuesta1 = lblRespuesta1;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public void setLblPregunta2(JLabel lblPregunta2) {
        this.lblPregunta2 = lblPregunta2;
    }

    public JLabel getLblRespuesta2() {
        return lblRespuesta2;
    }

    public void setLblRespuesta2(JLabel lblRespuesta2) {
        this.lblRespuesta2 = lblRespuesta2;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public void setLblPregunta3(JLabel lblPregunta3) {
        this.lblPregunta3 = lblPregunta3;
    }

    public JLabel getLblRespuesta3() {
        return lblRespuesta3;
    }

    public void setLblRespuesta3(JLabel lblRespuesta3) {
        this.lblRespuesta3 = lblRespuesta3;
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
