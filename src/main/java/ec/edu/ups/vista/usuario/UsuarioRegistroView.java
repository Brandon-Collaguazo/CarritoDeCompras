package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class UsuarioRegistroView extends JDialog {
    private JPanel pnlPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private JPasswordField txtConfirmarPassword;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblConfirmarPassword;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioRegistroView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setTitle("Registro de Usuario");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.registro.titulo"));

        lblTitulo.setText(mensaje.get("usuario.registro.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("contrasenia"));
        lblConfirmarPassword.setText(mensaje.get("confirmar.password"));

        btnRegistrar.setText(mensaje.get("registrar"));
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

    public JTextField getTxtUsername() {
        return txtUsername;
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
