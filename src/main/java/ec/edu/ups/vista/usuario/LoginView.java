package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LoginView extends JFrame {
    private JPanel pnlPrincipal;
    private JTextField txtPassword;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIniciar;
    private JButton btnRegistrar;
    private JComboBox<String> cbxIdioma;
    private JLabel lblContrasenia;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JPanel pnlBotones;
    private JButton btnRecuperar;
    private JLabel lblRecuperar;
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

        //Íconos para los botones
        URL iniciarsesionURL = LoginView.class.getClassLoader().getResource("imagenes/iniciarsesion.png");
        if(iniciarsesionURL != null) {
            ImageIcon iconoBtnIniciar = new ImageIcon(iniciarsesionURL);
            btnIniciar.setIcon(iconoBtnIniciar);
        } else {
            System.err.println("Error. no se ha cargado");
        }

        URL registrarURL = LoginView.class.getClassLoader().getResource("imagenes/registrarusuario.png");
        if(registrarURL != null) {
            ImageIcon iconoBtnRegistrar = new ImageIcon(registrarURL);
            btnRegistrar.setIcon(iconoBtnRegistrar);
        } else {
            System.err.println("Error ");
        }

        URL recuperarURL = LoginView.class.getClassLoader().getResource("imagenes/recuperarcontrasenia");
        if(recuperarURL != null) {
            ImageIcon iconoBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconoBtnRecuperar);
        } else {
            System.err.println("Error");
        }
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
        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText((mensaje.get("contrasenia")));

        lblRecuperar.setText(mensaje.get("recuperar"));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));
        btnRecuperar.setText(mensaje.get("btn.recuperar"));

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
