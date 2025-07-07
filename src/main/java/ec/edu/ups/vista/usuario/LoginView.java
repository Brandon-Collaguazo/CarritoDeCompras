package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.MenuPrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LoginView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuIdioma;
    private JMenuItem menuItemEspaniol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtUsuario;
    private JButton btnIniciar;
    private JButton btnRegistrar;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JPanel pnlBotones;
    private JButton btnRecuperar;
    private JLabel lblRecuperar;
    private JPasswordField txtPassword;
    private MensajeInternacionalizacionHandler mensaje;
    private String[] codigosIdioma = {"es", "en", "fr"};
    private String idiomaSeleccionado = "es";
    private String paisSeleccionado = "EC";
    private UsuarioRegistroView usuarioRegistroView;
    private RecuperarContraseniaView recuperarContraseniaView;

    public LoginView() {
        mensaje = new MensajeInternacionalizacionHandler(idiomaSeleccionado, paisSeleccionado);
        initComponents();
        configurarListenersIdiomas();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();

        menuIdioma = new JMenu(mensaje.get("menu.idioma"));

        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        menuIdioma.add(menuItemEspaniol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        menuBar.add(menuIdioma);

        setJMenuBar(menuBar);

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

        URL recuperarURL = LoginView.class.getClassLoader().getResource("imagenes/recuperarcontrasenia.png");
        if(recuperarURL != null) {
            ImageIcon iconoBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconoBtnRecuperar);
        } else {
            System.err.println("Error");
        }

        //Íconos para los items
        URL espaniolURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/espana.png");
        if(espaniolURL != null) {
            ImageIcon iconItemEsp = new ImageIcon(espaniolURL);
            menuItemEspaniol.setIcon(iconItemEsp);
        } else {
            System.out.println("Error, no se cargó la bandera de españa");
        }

        URL inglesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/reino-unido.png");
        if(inglesURL != null) {
            ImageIcon iconItemIng = new ImageIcon(inglesURL);
            menuItemIngles.setIcon(iconItemIng);
        } else {
            System.out.println("Error, no se cargó la bandra inglesa");
        }

        URL francesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/francia.png");
        if(francesURL != null) {
            ImageIcon iconItemFrn = new ImageIcon(francesURL);
            menuItemFrances.setIcon(iconItemFrn);
        } else {
            System.out.println("Error, no se cargó la bandera de francia");
        }
    }

    private void configurarListenersIdiomas() {
        menuItemEspaniol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(0);
            }
        });

        menuItemIngles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(1);
            }
        });

        menuItemFrances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(2);
            }
        });
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("login.titulo"));

        lblTitulo.setText(mensaje.get("login.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("contrasenia"));

        lblRecuperar.setText(mensaje.get("recuperar"));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));
        btnRecuperar.setText(mensaje.get("btn.recuperar"));

        menuIdioma.setText(mensaje.get("menu.idioma"));

        menuItemEspaniol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        menuItemFrances.setText(mensaje.get("menu.idioma.fr"));
    }

    private void cambiarIdioma(int indice) {
        if (indice >= 0 && indice < codigosIdioma.length) {
            idiomaSeleccionado = codigosIdioma[indice];
            paisSeleccionado = idiomaSeleccionado.equals("es") ? "EC" :
                    idiomaSeleccionado.equals("fr") ? "FR" : "US";
            mensaje.setLenguaje(idiomaSeleccionado, paisSeleccionado);
            actualizarTextos();

            if(usuarioRegistroView != null) {
                usuarioRegistroView.cambiarIdioma(idiomaSeleccionado, paisSeleccionado);
            }

            if(recuperarContraseniaView != null) {
                recuperarContraseniaView.cambiarIdioma(idiomaSeleccionado, paisSeleccionado);
            }
        }
    }

    public void setUsuarioRegistroView(UsuarioRegistroView usuarioRegistroView) {
        this.usuarioRegistroView = usuarioRegistroView;
        usuarioRegistroView.setMensaje(this.mensaje);
    }

    public void setRecuperarContraseniaView(RecuperarContraseniaView recuperarContraseniaView) {
        this.recuperarContraseniaView = recuperarContraseniaView;
        recuperarContraseniaView.setMensaje(this.mensaje);
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

    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    public void setMenuIdioma(JMenu menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    public JMenuItem getMenuItemEspaniol() {
        return menuItemEspaniol;
    }

    public void setMenuItemEspaniol(JMenuItem menuItemEspaniol) {
        this.menuItemEspaniol = menuItemEspaniol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JPanel getPnlBotones() {
        return pnlBotones;
    }

    public void setPnlBotones(JPanel pnlBotones) {
        this.pnlBotones = pnlBotones;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public void setBtnRecuperar(JButton btnRecuperar) {
        this.btnRecuperar = btnRecuperar;
    }

    public JLabel getLblRecuperar() {
        return lblRecuperar;
    }

    public void setLblRecuperar(JLabel lblRecuperar) {
        this.lblRecuperar = lblRecuperar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
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

    public void mostrarMensaje(String mensajeKey) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeKey));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
    }
}