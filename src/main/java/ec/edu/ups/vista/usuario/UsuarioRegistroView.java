package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;

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
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JTextField txtTelefono;
    private JLabel lblTelefono;
    private JLabel lblFechaDeNacimiento;
    private JTextField txtFechaNacimiento;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
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
        setSize(750, 300);
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
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    public String[] mostrarPreguntasSeguridad(List<PreguntaSeguridad> preguntas) {
        Object[] message = {
                mensaje.get("preguntas.seguridad.mensaje"),
                " "
        };

        JOptionPane.showMessageDialog(this, message, mensaje.get("preguntas.seguridad.titulo"),
                JOptionPane.INFORMATION_MESSAGE);

        String[] resultados = new String[6]; // 3 Preguntas y 3 Respuestas

        for(int i = 0; i < 3; i++) {
            String pregunta = (String) JOptionPane.showInputDialog(
                    this,
                    mensaje.get("pregunta.seleccione") + (i + 1),
                    mensaje.get("preguntas.seguridad.titulo"),
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    preguntas.toArray(),
                    preguntas.get(0)
            );
            if(pregunta == null)
                return null;
            String respuesta =  JOptionPane.showInputDialog(
                    this,
                    mensaje.get("pregunta.responda") + "\n" + pregunta,
                    mensaje.get("preguntas.seguridad.titulo"),
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == null || respuesta.trim().isEmpty()) {
                mostrarMensaje("pregunta.respuesta.requerida");
                return null;
            }
            resultados[i*2] = pregunta;
            resultados[i*2+1] = respuesta;
        }
        return resultados;
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
