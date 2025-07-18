package ec.edu.ups.vista.autenticacion;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.MenuPrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * La clase `LoginView` representa la ventana de inicio de sesión de la aplicación.
 * Permite a los usuarios ingresar sus credenciales (nombre de usuario y contraseña),
 * seleccionar un tipo de almacenamiento de datos y cambiar el idioma de la interfaz.
 * También proporciona acceso a las vistas de registro de usuario y recuperación de contraseña.
 *
 */
public class LoginView extends JFrame {

    /** Componente JMenuBar para el menú de la aplicación. */
    private JMenuBar menuBar;
    /** Menú desplegable para la selección de idioma. */
    private JMenu menuIdioma;
    /** Opción de menú para cambiar el idioma a Español. */
    private JMenuItem menuItemEspaniol;
    /** Opción de menú para cambiar el idioma a Inglés. */
    private JMenuItem menuItemIngles;
    /** Opción de menú para cambiar el idioma a Francés. */
    private JMenuItem menuItemFrances;
    /** Panel principal que contiene todos los demás componentes. */
    private JPanel pnlPrincipal;
    /** Panel superior de la interfaz, posiblemente para el título. */
    private JPanel pnlSuperior;
    /** Panel central de la interfaz, que contiene los campos de entrada y etiquetas. */
    private JPanel pnlCentral;
    /** Campo de texto para que el usuario ingrese su nombre de usuario. */
    private JTextField txtUsuario;
    /** Botón para iniciar sesión. */
    private JButton btnIniciar;
    /** Botón para registrar un nuevo usuario. */
    private JButton btnRegistrar;
    /** Etiqueta para el título de la ventana de inicio de sesión. */
    private JLabel lblTitulo;
    /** Etiqueta para el campo de nombre de usuario. */
    private JLabel lblUsuario;
    /** Etiqueta para el campo de contraseña. */
    private JLabel lblPassword;
    /** Panel que contiene los botones de acción (iniciar, registrar, recuperar). */
    private JPanel pnlBotones;
    /** Botón para iniciar el proceso de recuperación de contraseña. */
    private JButton btnRecuperar;
    /** Etiqueta para el enlace o botón de recuperación de contraseña. */
    private JLabel lblRecuperar;
    /** Campo de texto para que el usuario ingrese su contraseña (oculto). */
    private JPasswordField txtPassword;
    /** Etiqueta para el selector de tipo de almacenamiento. */
    private JLabel lblAlmacenamiento;
    /** ComboBox para seleccionar el tipo de almacenamiento de datos (Memoria, Archivos Txt, Archivos Binarios). */
    private JComboBox<String> cbxAlmacenamiento;
    /** Campo de texto para ingresar la ruta del archivo de almacenamiento (visible solo para Txt y Binarios). */
    private JTextField txtRuta;
    /** Etiqueta para el campo de ruta del archivo. */
    private JLabel lblRuta;

    /** Array de opciones de almacenamiento disponibles para el ComboBox. */
    private String[] opcionesAlmacenamiento = {"Memoria", "Archivos Txt", "Archivos Binarios"};
    /** Manejador de mensajes de internacionalización para obtener textos en el idioma seleccionado. */
    private MensajeInternacionalizacionHandler mensaje;
    /** Array de códigos de idioma soportados (es, en, fr). */
    private String[] codigosIdioma = {"es", "en", "fr"};
    /** Idioma actualmente seleccionado, por defecto "es" (español). */
    private String idiomaSeleccionado = "es";
    /** País actualmente seleccionado, por defecto "EC" (Ecuador). */
    private String paisSeleccionado = "EC";

    /** Referencia a la vista de registro de usuario, si ha sido establecida. */
    private UsuarioRegistroView usuarioRegistroView;
    /** Referencia a la vista de recuperación de contraseña, si ha sido establecida. */
    private RecuperarContraseniaView recuperarContraseniaView;

    /**
     * Constructor de la clase `LoginView`.
     * Inicializa el manejador de internacionalización, configura los componentes
     * de la interfaz gráfica, establece los listeners para el cambio de idioma
     * y actualiza los textos de la interfaz al idioma por defecto.
     */
    public LoginView() {
        mensaje = new MensajeInternacionalizacionHandler(idiomaSeleccionado, paisSeleccionado);
        initComponents();
        configurarListenersIdiomas();
        actualizarTextos();
    }

    /**
     * Inicializa y configura los componentes de la interfaz gráfica de usuario (GUI).
     * Establece las propiedades de la ventana, inicializa el ComboBox de almacenamiento,
     * configura la barra de menú con las opciones de idioma y carga los iconos para
     * botones y elementos del menú.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.

        cbxAlmacenamiento.setModel(new DefaultComboBoxModel<>(
                new String[]{"Memoria", "Archivos Txt", "Archivos Binarios"}
        ));
        cbxAlmacenamiento.setSelectedIndex(0); // Selecciona "Memoria" por defecto.

        menuBar = new JMenuBar();
        menuIdioma = new JMenu(mensaje.get("menu.idioma")); // Texto del menú de idioma.

        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        menuIdioma.add(menuItemEspaniol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);

        // Carga y asignación de íconos para los botones
        URL iniciarsesionURL = LoginView.class.getClassLoader().getResource("imagenes/iniciarsesion.png");
        if(iniciarsesionURL != null) {
            ImageIcon iconoBtnIniciar = new ImageIcon(iniciarsesionURL);
            btnIniciar.setIcon(iconoBtnIniciar);
        } else {
            System.err.println("Error: no se ha cargado el ícono de iniciar sesión.");
        }

        URL registrarURL = LoginView.class.getClassLoader().getResource("imagenes/registrarusuario.png");
        if(registrarURL != null) {
            ImageIcon iconoBtnRegistrar = new ImageIcon(registrarURL);
            btnRegistrar.setIcon(iconoBtnRegistrar);
        } else {
            System.err.println("Error: no se ha cargado el ícono de registrar usuario.");
        }

        URL recuperarURL = LoginView.class.getClassLoader().getResource("imagenes/recuperarcontrasenia.png");
        if(recuperarURL != null) {
            ImageIcon iconoBtnRecuperar = new ImageIcon(recuperarURL);
            btnRecuperar.setIcon(iconoBtnRecuperar);
        } else {
            System.err.println("Error: no se ha cargado el ícono de recuperar contraseña.");
        }

        // Carga y asignación de íconos para los elementos del menú de idioma
        URL espaniolURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/espana.png");
        if(espaniolURL != null) {
            ImageIcon iconItemEsp = new ImageIcon(espaniolURL);
            menuItemEspaniol.setIcon(iconItemEsp);
        } else {
            System.out.println("Error: no se cargó la bandera de España.");
        }

        URL inglesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/reino-unido.png");
        if(inglesURL != null) {
            ImageIcon iconItemIng = new ImageIcon(inglesURL);
            menuItemIngles.setIcon(iconItemIng);
        } else {
            System.out.println("Error: no se cargó la bandera inglesa.");
        }

        URL francesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/francia.png");
        if(francesURL != null) {
            ImageIcon iconItemFrn = new ImageIcon(francesURL);
            menuItemFrances.setIcon(iconItemFrn);
        } else {
            System.out.println("Error: no se cargó la bandera de Francia.");
        }
    }

    /**
     * Configura los ActionListeners para los elementos del menú de idioma
     * y para el ComboBox de selección de almacenamiento.
     * Al seleccionar un idioma, se llama a `cambiarIdioma`.
     * Al cambiar la opción de almacenamiento, se controla la visibilidad
     * del campo de texto de la ruta.
     */
    private void configurarListenersIdiomas() {
        menuItemEspaniol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(0); // Índice para Español
            }
        });

        menuItemIngles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(1); // Índice para Inglés
            }
        });

        menuItemFrances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma(2); // Índice para Francés
            }
        });

        cbxAlmacenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // La ruta solo es relevante para almacenamiento en Archivos Txt o Binarios.
                boolean mostrarRuta = !cbxAlmacenamiento.getSelectedItem().equals("Memoria");
                lblRuta.setVisible(mostrarRuta);
                txtRuta.setVisible(mostrarRuta);
            }
        });
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario (etiquetas, botones, menú)
     * utilizando el `MensajeInternacionalizacionHandler` para reflejar el idioma actual.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("login.titulo")); // Título de la ventana
        lblTitulo.setText(mensaje.get("login.titulo")); // Etiqueta de título principal

        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("contrasenia"));
        lblAlmacenamiento.setText(mensaje.get("almacenamiento"));
        lblRuta.setText(mensaje.get("ruta"));
        lblRecuperar.setText(mensaje.get("recuperar"));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));
        btnRecuperar.setText(mensaje.get("btn.recuperar"));

        menuIdioma.setText(mensaje.get("menu.idioma"));
        menuItemEspaniol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        menuItemFrances.setText(mensaje.get("menu.idioma.fr"));
    }

    /**
     * Cambia el idioma de la interfaz de usuario.
     * Actualiza el `MensajeInternacionalizacionHandler` con el nuevo idioma y país,
     * luego llama a `actualizarTextos()`. Además, si las vistas de registro y
     * recuperación de contraseña están instanciadas, también les indica que cambien su idioma.
     *
     * @param indice El índice del idioma en el array `codigosIdioma`.
     * 0 para Español, 1 para Inglés, 2 para Francés.
     */
    private void cambiarIdioma(int indice) {
        if (indice >= 0 && indice < codigosIdioma.length) {
            idiomaSeleccionado = codigosIdioma[indice];
            // Asigna el código de país según el idioma seleccionado.
            paisSeleccionado = idiomaSeleccionado.equals("es") ? "EC" :
                    idiomaSeleccionado.equals("fr") ? "FR" : "US"; // Por defecto "US" para inglés
            mensaje.setLenguaje(idiomaSeleccionado, paisSeleccionado); // Actualiza el manejador de mensajes
            actualizarTextos(); // Actualiza los textos de esta vista

            // Si las vistas relacionadas están abiertas, también se actualiza su idioma.
            if(usuarioRegistroView != null) {
                usuarioRegistroView.cambiarIdioma(idiomaSeleccionado, paisSeleccionado);
            }

            if(recuperarContraseniaView != null) {
                recuperarContraseniaView.cambiarIdioma(idiomaSeleccionado, paisSeleccionado);
            }
        }
    }

    /**
     * Establece la vista de registro de usuario y le pasa el manejador de mensajes
     * de internacionalización de esta vista.
     *
     * @param usuarioRegistroView La instancia de `UsuarioRegistroView`.
     */
    public void setUsuarioRegistroView(UsuarioRegistroView usuarioRegistroView) {
        this.usuarioRegistroView = usuarioRegistroView;
        usuarioRegistroView.setMensaje(this.mensaje);
    }

    /**
     * Establece la vista de recuperación de contraseña y le pasa el manejador de mensajes
     * de internacionalización de esta vista.
     *
     * @param recuperarContraseniaView La instancia de `RecuperarContraseniaView`.
     */
    public void setRecuperarContraseniaView(RecuperarContraseniaView recuperarContraseniaView) {
        this.recuperarContraseniaView = recuperarContraseniaView;
        recuperarContraseniaView.setMensaje(this.mensaje);
    }

    // --- Métodos Getters y Setters para los componentes de la interfaz ---

    /**
     * Retorna el panel principal de la vista.
     * @return El {@link JPanel} principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal El {@link JPanel} a establecer como principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Retorna el panel superior de la vista.
     * @return El {@link JPanel} superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     * @param pnlSuperior El {@link JPanel} a establecer como superior.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Retorna el panel central de la vista.
     * @return El {@link JPanel} central.
     */
    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    /**
     * Establece el panel central de la vista.
     * @param pnlCentral El {@link JPanel} a establecer como central.
     */
    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
    }

    /**
     * Retorna el menú de idioma.
     * @return El {@link JMenu} de idioma.
     */
    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    /**
     * Establece el menú de idioma.
     * @param menuIdioma El {@link JMenu} a establecer.
     */
    public void setMenuIdioma(JMenu menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    /**
     * Retorna el elemento de menú para el idioma Español.
     * @return El {@link JMenuItem} de Español.
     */
    public JMenuItem getMenuItemEspaniol() {
        return menuItemEspaniol;
    }

    /**
     * Establece el elemento de menú para el idioma Español.
     * @param menuItemEspaniol El {@link JMenuItem} a establecer.
     */
    public void setMenuItemEspaniol(JMenuItem menuItemEspaniol) {
        this.menuItemEspaniol = menuItemEspaniol;
    }

    /**
     * Retorna el elemento de menú para el idioma Inglés.
     * @return El {@link JMenuItem} de Inglés.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Establece el elemento de menú para el idioma Inglés.
     * @param menuItemIngles El {@link JMenuItem} a establecer.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    /**
     * Retorna el elemento de menú para el idioma Francés.
     * @return El {@link JMenuItem} de Francés.
     */
    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    /**
     * Establece el elemento de menú para el idioma Francés.
     * @param menuItemFrances El {@link JMenuItem} a establecer.
     */
    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    /**
     * Retorna la etiqueta para el campo de usuario.
     * @return El {@link JLabel} del usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta para el campo de usuario.
     * @param lblUsuario El {@link JLabel} a establecer.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Retorna la etiqueta para el campo de contraseña.
     * @return El {@link JLabel} de la contraseña.
     */
    public JLabel getLblPassword() {
        return lblPassword;
    }

    /**
     * Establece la etiqueta para el campo de contraseña.
     * @param lblPassword El {@link JLabel} a establecer.
     */
    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    /**
     * Retorna el panel que contiene los botones.
     * @return El {@link JPanel} de botones.
     */
    public JPanel getPnlBotones() {
        return pnlBotones;
    }

    /**
     * Establece el panel que contiene los botones.
     * @param pnlBotones El {@link JPanel} a establecer.
     */
    public void setPnlBotones(JPanel pnlBotones) {
        this.pnlBotones = pnlBotones;
    }

    /**
     * Retorna el botón de recuperar contraseña.
     * @return El {@link JButton} de recuperar.
     */
    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    /**
     * Establece el botón de recuperar contraseña.
     * @param btnRecuperar El {@link JButton} a establecer.
     */
    public void setBtnRecuperar(JButton btnRecuperar) {
        this.btnRecuperar = btnRecuperar;
    }

    /**
     * Retorna la etiqueta para el botón de recuperar contraseña.
     * @return El {@link JLabel} de recuperar.
     */
    public JLabel getLblRecuperar() {
        return lblRecuperar;
    }

    /**
     * Establece la etiqueta para el botón de recuperar contraseña.
     * @param lblRecuperar El {@link JLabel} a establecer.
     */
    public void setLblRecuperar(JLabel lblRecuperar) {
        this.lblRecuperar = lblRecuperar;
    }

    /**
     * Retorna el campo de texto para el nombre de usuario.
     * @return El {@link JTextField} del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsuario El {@link JTextField} a establecer.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Retorna el campo de contraseña.
     * @return El {@link JPasswordField} de la contraseña.
     */
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    /**
     * Establece el campo de contraseña.
     * @param txtPassword El {@link JPasswordField} a establecer.
     */
    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    /**
     * Retorna el campo de texto para la ruta del archivo.
     * @return El {@link JTextField} de la ruta.
     */
    public JTextField getTxtRuta() {
        return txtRuta;
    }

    /**
     * Establece el campo de texto para la ruta del archivo.
     * @param txtRuta El {@link JTextField} a establecer.
     */
    public void setTxtRuta(JTextField txtRuta) {
        this.txtRuta = txtRuta;
    }

    /**
     * Retorna el botón de iniciar sesión.
     * @return El {@link JButton} de iniciar.
     */
    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    /**
     * Establece el botón de iniciar sesión.
     * @param btnIniciar El {@link JButton} a establecer.
     */
    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    /**
     * Retorna el botón de registrar.
     * @return El {@link JButton} de registrar.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Establece el botón de registrar.
     * @param btnRegistrar El {@link JButton} a establecer.
     */
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    /**
     * Retorna la etiqueta del título de la ventana.
     * @return El {@link JLabel} del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Retorna el ComboBox de selección de tipo de almacenamiento.
     * @return El {@link JComboBox} de almacenamiento.
     */
    public JComboBox getCbxAlmacenamiento() {
        return cbxAlmacenamiento;
    }

    /**
     * Establece el ComboBox de selección de tipo de almacenamiento.
     * @param cbxAlmacenamiento El {@link JComboBox} a establecer.
     */
    public void setCbxAlmacenamiento(JComboBox cbxAlmacenamiento) {
        this.cbxAlmacenamiento = cbxAlmacenamiento;
    }

    /**
     * Establece la etiqueta del título de la ventana.
     * @param lblTitulo El {@link JLabel} a establecer.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Retorna el manejador de mensajes de internacionalización.
     * @return El {@link MensajeInternacionalizacionHandler} utilizado.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de mensajes de internacionalización.
     * @param mensaje El {@link MensajeInternacionalizacionHandler} a establecer.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Retorna el tipo de almacenamiento seleccionado en el ComboBox.
     * @return Una cadena que representa el tipo de almacenamiento (ej., "Memoria", "Archivos Txt").
     */
    public String getTipoAlmacenamiento() {
        return (String) cbxAlmacenamiento.getSelectedItem();
    }

    /**
     * Retorna la ruta del archivo ingresada en el campo de texto, sin espacios al inicio o al final.
     * @return Una cadena que representa la ruta del archivo.
     */
    public String getRutaArchivo() {
        return txtRuta.getText().trim();
    }

    /**
     * Muestra un cuadro de diálogo de mensaje utilizando el texto internacionalizado
     * asociado a la clave proporcionada.
     *
     * @param mensajeKey La clave del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensajeKey) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeKey));
    }

    /**
     * Limpia los campos de texto de usuario y contraseña.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
    }

    /**
     * Establece un texto por defecto en el campo de texto de la ruta del archivo.
     *
     * @param rutaDefault La cadena de texto a establecer como ruta por defecto.
     */
    public void setRutaArchivo(String rutaDefault) {
        txtRuta.setText(rutaDefault);
    }
}