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

        lblUsuario = new JLabel();
        lblPassword = new JLabel();
        lblConfirmarPassword = new JLabel();

        btnRegistrar = new JButton();

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmarPassword = new JPasswordField();

    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.registro.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("password"));
        lblConfirmarPassword.setText(mensaje.get("confirmar.password"));

        btnRegistrar.setText(mensaje.get("registrar"));
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
    }
}
