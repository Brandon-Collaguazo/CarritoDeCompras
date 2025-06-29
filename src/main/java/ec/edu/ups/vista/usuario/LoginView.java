package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIniciar;
    private JButton btnRegistrar;
    private JComboBox<String> cbxIdioma;
    private JLabel lblUser;
    private JLabel lblContrasenia;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;
    private String[] codigosIdioma = {"es", "en", "fr"};
    private String idiomaSeleccionado = "es";
    private String paisSeleccionado = "EC";

    public LoginView() {
        mensaje = new MensajeInternacionalizacionHandler(idiomaSeleccionado, paisSeleccionado);
        initComponents();
        cargarDatos();
        agregarListeners();
        actualizarTextos();
    }

    private void agregarListeners() {
        cbxIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma();
            }
        });
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem(mensaje.get("menu.idioma.es"));
        cbxIdioma.addItem(mensaje.get("menu.idioma.en"));
        cbxIdioma.addItem(mensaje.get("menu.idioma.fr"));

        if (idiomaSeleccionado.equals("es")) cbxIdioma.setSelectedIndex(0);
        else if (idiomaSeleccionado.equals("en")) cbxIdioma.setSelectedIndex(1);
        else if (idiomaSeleccionado.equals("fr")) cbxIdioma.setSelectedIndex(2);
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("login.titulo"));

        lblTitulo.setText(mensaje.get("login.titulo"));
        lblUser.setText(mensaje.get("usuario"));
        lblContrasenia.setText((mensaje.get("contrasenia")));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));

        cargarDatos();

    }

    private void cambiarIdioma() {
        int seleccion = cbxIdioma.getSelectedIndex();

        if (seleccion != -1 && seleccion < codigosIdioma.length) {
            idiomaSeleccionado = codigosIdioma[seleccion];
            paisSeleccionado = idiomaSeleccionado.equals("es") ? "EC" : idiomaSeleccionado.equals("fr") ? "FR" : "US";
            mensaje.setLenguaje(idiomaSeleccionado, paisSeleccionado);
            actualizarTextos();
        }
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
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

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public JComboBox<String> getCbxIdioma() {
        return cbxIdioma;
    }

    public void setCbxIdioma(JComboBox<String> cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public String[] getCodigosIdioma() {
        return codigosIdioma;
    }

    public void setCodigosIdioma(String[] codigosIdioma) {
        this.codigosIdioma = codigosIdioma;
    }

    public String getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(String idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

    public String getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(String paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }


    public void mostrarMensaje(String mensajeKey) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeKey));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
    }
}
