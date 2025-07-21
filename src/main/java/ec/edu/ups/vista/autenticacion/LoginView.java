package ec.edu.ups.vista.autenticacion;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.MenuPrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

/**
 * <p>La clase {@code LoginView} representa la ventana de inicio de sesión de la aplicación.</p>
 * <p>Permite a los usuarios ingresar sus credenciales, acceder a las opciones de registro
 * y recuperación de contraseña, así como seleccionar el tipo de almacenamiento de datos
 * y la ruta de archivos. También ofrece soporte para la internacionalización de la interfaz de usuario.</p>
 */
public class LoginView extends JFrame {
    /**
     * Referencia a la vista principal de la aplicación, utilizada para la navegación posterior al login.
     */
    private MenuPrincipalView principalView;
    /**
     * Barra de menú principal de la ventana.
     */
    private JMenuBar menuBar;
    /**
     * Menú desplegable para la selección del idioma.
     */
    private JMenu menuIdioma;
    /**
     * Opción de menú para cambiar el idioma a español.
     */
    private JMenuItem menuItemEspaniol;
    /**
     * Opción de menú para cambiar el idioma a inglés.
     */
    private JMenuItem menuItemIngles;
    /**
     * Opción de menú para cambiar el idioma a francés.
     */
    private JMenuItem menuItemFrances;
    /**
     * Panel principal que contiene todos los demás componentes de la vista.
     */
    private JPanel pnlPrincipal;
    /**
     * Panel superior de la interfaz, típicamente para el título o encabezado.
     */
    private JPanel pnlSuperior;
    /**
     * Panel central donde se ubican los campos de usuario, contraseña y opciones de almacenamiento.
     */
    private JPanel pnlCentral;
    /**
     * Campo de texto para que el usuario ingrese su nombre de usuario.
     */
    private JTextField txtUsuario;
    /**
     * Botón para iniciar sesión con las credenciales ingresadas.
     */
    private JButton btnIniciar;
    /**
     * Botón para acceder a la vista de registro de nuevos usuarios.
     */
    private JButton btnRegistrar;
    /**
     * Etiqueta para mostrar el título principal de la ventana de login.
     */
    private JLabel lblTitulo;
    /**
     * Etiqueta para el campo de entrada de usuario.
     */
    private JLabel lblUsuario;
    /**
     * Etiqueta para el campo de entrada de contraseña.
     */
    private JLabel lblPassword;
    /**
     * Panel que agrupa los botones de acción como iniciar sesión, registrar y recuperar contraseña.
     */
    private JPanel pnlBotones;
    /**
     * Botón para iniciar el proceso de recuperación de contraseña.
     */
    private JButton btnRecuperar;
    /**
     * Etiqueta informativa o de título para la sección de recuperación de contraseña.
     */
    private JLabel lblRecuperar;
    /**
     * Campo de contraseña para que el usuario ingrese su clave.
     */
    private JPasswordField txtPassword;
    /**
     * Etiqueta para el combo box de selección del tipo de almacenamiento.
     */
    private JLabel lblAlmacenamiento;
    /**
     * Combo box para seleccionar el tipo de almacenamiento de datos (Memoria, Archivos Txt, Archivos Binarios).
     */
    private JComboBox<String> cbxAlmacenamiento;
    /**
     * Campo de texto para que el usuario especifique la ruta de los archivos de datos.
     */
    private JTextField txtRuta;
    /**
     * Etiqueta para el campo de entrada de la ruta de archivos.
     */
    private JLabel lblRuta;
    /**
     * Botón para abrir un explorador de archivos y seleccionar una ruta de directorio.
     */
    private JButton btnRuta;

    /**
     * Opciones de almacenamiento disponibles para el JComboBox.
     */
    private String[] opcionesAlmacenamiento = {"Memoria", "Archivos Txt", "Archivos Binarios"};
    /**
     * Manejador para obtener los textos de la interfaz en el idioma seleccionado.
     */
    private MensajeInternacionalizacionHandler mensaje;
    /**
     * Códigos de idioma soportados (ej. "es", "en", "fr").
     */
    private String[] codigosIdioma = {"es", "en", "fr"};
    /**
     * Idioma actualmente seleccionado en la interfaz. Por defecto "es".
     */
    private String idiomaSeleccionado = "es";
    /**
     * País asociado al idioma seleccionado, utilizado para la internacionalización. Por defecto "EC".
     */
    private String paisSeleccionado = "EC";

    /**
     * Referencia a la vista de registro de usuarios, para permitir la navegación.
     */
    private UsuarioRegistroView usuarioRegistroView;
    /**
     * Referencia a la vista de recuperación de contraseña.
     */
    private RecuperarContraseniaView recuperarContraseniaView;

    /**
     * <p>Construye e inicializa una nueva instancia de {@code LoginView}.</p>
     * <p>Configura los componentes de la interfaz de usuario, los listeners para el cambio de idioma,
     * y actualiza todos los textos de la vista de acuerdo al idioma inicial.</p>
     *
     * @param mensaje El {@link MensajeInternacionalizacionHandler} utilizado para la gestión de idiomas y textos.
     */
    public LoginView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        configurarListenersIdiomas();
        actualizarTextos();
    }

    /**
     * <p>Inicializa y configura todos los componentes gráficos de la ventana de login.</p>
     * <p>Esto incluye la configuración del diseño del panel principal, los campos de entrada,
     * botones, la barra de menú con las opciones de idioma, y la carga de los iconos
     * para los botones y elementos del menú.</p>
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        cbxAlmacenamiento.setModel(new DefaultComboBoxModel<>(
                new String[]{"Memoria", "Archivos Txt", "Archivos Binarios"}
        ));
        cbxAlmacenamiento.setSelectedIndex(0);

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
     * <p>Configura los {@link ActionListener} para los elementos interactivos de la vista.</p>
     * <p>Esto incluye los ítems del menú de idioma para permitir el cambio de interfaz,
     * el {@link JComboBox} de selección de almacenamiento para mostrar u ocultar el campo de ruta,
     * y el botón de selección de ruta de archivos.</p>
     */
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

        cbxAlmacenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoKey = (String) cbxAlmacenamiento.getSelectedItem();
                boolean esArchivo = tipoKey != null &&
                        (tipoKey.equals(mensaje.get("login.almacenamiento.archivo.sistema")) ||
                                tipoKey.equals(mensaje.get("login.almacenamiento.binario")));
                lblRuta.setVisible(esArchivo);
                txtRuta.setVisible(esArchivo);
                btnRecuperar.setVisible(esArchivo);
                if(esArchivo && txtRuta.getText().isEmpty()) {
                    txtRuta.setText("data" + File.separator);
                } else if (!esArchivo) {
                    txtRuta.setText("");
                }
            }
        });
        cbxAlmacenamiento.setSelectedIndex(0);
        if (cbxAlmacenamiento.getActionListeners().length > 0) {
            cbxAlmacenamiento.getActionListeners()[0].actionPerformed(
                    new ActionEvent(cbxAlmacenamiento, ActionEvent.ACTION_PERFORMED, null)
            );
        }

        btnRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int opcion = fileChooser.showOpenDialog(LoginView.this);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File directorioSelected = fileChooser.getSelectedFile();
                    txtRuta.setText(directorioSelected.getAbsolutePath() + File.separator);
                }
            }
        });
    }

    /**
     * <p>Actualiza todos los textos visibles en la interfaz de usuario de acuerdo
     * con el idioma actualmente configurado en el {@link MensajeInternacionalizacionHandler}.</p>
     * <p>Esto incluye el título de la ventana, etiquetas, textos de botones y elementos de menú.</p>
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("login.titulo"));
        lblTitulo.setText(mensaje.get("login.titulo"));

        lblUsuario.setText(mensaje.get("usuario"));
        lblPassword.setText(mensaje.get("contrasenia"));
        lblAlmacenamiento.setText(mensaje.get("almacenamiento"));
        lblRuta.setText(mensaje.get("ruta"));
        lblRecuperar.setText(mensaje.get("recuperar"));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));
        btnRecuperar.setText(mensaje.get("btn.recuperar"));
        btnRuta.setText(mensaje.get("btn.ruta"));

        menuIdioma.setText(mensaje.get("menu.idioma"));
        menuItemEspaniol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        menuItemFrances.setText(mensaje.get("menu.idioma.fr"));
    }

    /**
     * <p>Cambia el idioma de la interfaz de usuario de la aplicación.</p>
     * <p>Actualiza el {@code Locale} en el {@link MensajeInternacionalizacionHandler} y luego
     * refresca todos los textos de la vista de login. También propaga el cambio de idioma
     * a las vistas dependientes, como {@link UsuarioRegistroView} y {@link RecuperarContraseniaView},
     * si estas han sido inicializadas.</p>
     *
     * @param indice El índice del idioma a seleccionar:
     * <ul>
     * <li>0 para español ("es", país "EC")</li>
     * <li>1 para inglés ("en", país "US")</li>
     * <li>2 para francés ("fr", país "FR")</li>
     * </ul>
     */
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

    /**
     * <p>Establece la instancia de la vista de registro de usuarios.</p>
     * <p>También transfiere el manejador de mensajes a la vista de registro.</p>
     *
     * @param usuarioRegistroView La {@link UsuarioRegistroView} a enlazar.
     */
    public void setUsuarioRegistroView(UsuarioRegistroView usuarioRegistroView) {
        this.usuarioRegistroView = usuarioRegistroView;
        usuarioRegistroView.setMensaje(this.mensaje);
    }

    /**
     * <p>Establece la instancia de la vista de recuperación de contraseña.</p>
     * <p>También transfiere el manejador de mensajes a la vista de recuperación.</p>
     *
     * @param recuperarContraseniaView La {@link RecuperarContraseniaView} a enlazar.
     */
    public void setRecuperarContraseniaView(RecuperarContraseniaView recuperarContraseniaView) {
        this.recuperarContraseniaView = recuperarContraseniaView;
        recuperarContraseniaView.setMensaje(this.mensaje);
    }

    /**
     * Obtiene el panel principal de la vista de login.
     *
     * @return El {@link JPanel} principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista de login.
     *
     * @param pnlPrincipal El {@link JPanel} a establecer como principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista de login.
     *
     * @return El {@link JPanel} superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista de login.
     *
     * @param pnlSuperior El {@link JPanel} a establecer como superior.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel central de la vista de login.
     *
     * @return El {@link JPanel} central.
     */
    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    /**
     * Establece el panel central de la vista de login.
     *
     * @param pnlCentral El {@link JPanel} a establecer como central.
     */
    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
    }

    /**
     * Obtiene el menú de selección de idioma.
     *
     * @return El {@link JMenu} para idiomas.
     */
    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    /**
     * Establece el menú de selección de idioma.
     *
     * @param menuIdioma El {@link JMenu} a establecer para idiomas.
     */
    public void setMenuIdioma(JMenu menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    /**
     * Obtiene el ítem de menú para el idioma español.
     *
     * @return El {@link JMenuItem} para español.
     */
    public JMenuItem getMenuItemEspaniol() {
        return menuItemEspaniol;
    }

    /**
     * Establece el ítem de menú para el idioma español.
     *
     * @param menuItemEspaniol El {@link JMenuItem} a establecer para español.
     */
    public void setMenuItemEspaniol(JMenuItem menuItemEspaniol) {
        this.menuItemEspaniol = menuItemEspaniol;
    }

    /**
     * Obtiene el ítem de menú para el idioma inglés.
     *
     * @return El {@link JMenuItem} para inglés.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Establece el ítem de menú para el idioma inglés.
     *
     * @param menuItemIngles El {@link JMenuItem} a establecer para inglés.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    /**
     * Obtiene el ítem de menú para el idioma francés.
     *
     * @return El {@link JMenuItem} para francés.
     */
    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    /**
     * Establece el ítem de menú para el idioma francés.
     *
     * @param menuItemFrances El {@link JMenuItem} a establecer para francés.
     */
    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    /**
     * Obtiene la etiqueta del campo de usuario.
     *
     * @return El {@link JLabel} para el usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta del campo de usuario.
     *
     * @param lblUsuario El {@link JLabel} a establecer para el usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene la etiqueta del campo de contraseña.
     *
     * @return El {@link JLabel} para la contraseña.
     */
    public JLabel getLblPassword() {
        return lblPassword;
    }

    /**
     * Establece la etiqueta del campo de contraseña.
     *
     * @param lblPassword El {@link JLabel} a establecer para la contraseña.
     */
    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    /**
     * Obtiene el panel que contiene los botones de acción.
     *
     * @return El {@link JPanel} de botones.
     */
    public JPanel getPnlBotones() {
        return pnlBotones;
    }

    /**
     * Establece el panel que contiene los botones de acción.
     *
     * @param pnlBotones El {@link JPanel} a establecer para los botones.
     */
    public void setPnlBotones(JPanel pnlBotones) {
        this.pnlBotones = pnlBotones;
    }

    /**
     * Obtiene el botón para recuperar la contraseña.
     *
     * @return El {@link JButton} de recuperar contraseña.
     */
    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    /**
     * Establece el botón para recuperar la contraseña.
     *
     * @param btnRecuperar El {@link JButton} a establecer para recuperar contraseña.
     */
    public void setBtnRecuperar(JButton btnRecuperar) {
        this.btnRecuperar = btnRecuperar;
    }

    /**
     * Obtiene la etiqueta relacionada con la recuperación de contraseña.
     *
     * @return El {@link JLabel} de recuperación.
     */
    public JLabel getLblRecuperar() {
        return lblRecuperar;
    }

    /**
     * Establece la etiqueta relacionada con la recuperación de contraseña.
     *
     * @param lblRecuperar El {@link JLabel} a establecer para recuperación.
     */
    public void setLblRecuperar(JLabel lblRecuperar) {
        this.lblRecuperar = lblRecuperar;
    }

    /**
     * Obtiene la instancia del campo de texto de usuario.
     *
     * @return El {@link JTextField} del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece la instancia del campo de texto de usuario.
     *
     * @param txtUsuario El {@link JTextField} a establecer para el usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene la instancia del campo de contraseña.
     *
     * @return El {@link JPasswordField} de la contraseña.
     */
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    /**
     * Establece la instancia del campo de contraseña.
     *
     * @param txtPassword El {@link JPasswordField} a establecer para la contraseña.
     */
    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    /**
     * Obtiene la instancia del campo de texto para la ruta de archivos.
     *
     * @return El {@link JTextField} de la ruta.
     */
    public JTextField getTxtRuta() {
        return txtRuta;
    }

    /**
     * Establece la instancia del campo de texto para la ruta de archivos.
     *
     * @param txtRuta El {@link JTextField} a establecer para la ruta.
     */
    public void setTxtRuta(JTextField txtRuta) {
        this.txtRuta = txtRuta;
    }

    /**
     * Obtiene el botón para iniciar sesión.
     *
     * @return El {@link JButton} de iniciar sesión.
     */
    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    /**
     * Establece el botón para iniciar sesión.
     *
     * @param btnIniciar El {@link JButton} a establecer para iniciar sesión.
     */
    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    /**
     * Obtiene el botón para registrar un nuevo usuario.
     *
     * @return El {@link JButton} de registrar.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Establece el botón para registrar un nuevo usuario.
     *
     * @param btnRegistrar El {@link JButton} a establecer para registrar.
     */
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    /**
     * Obtiene el botón para seleccionar la ruta de archivos.
     *
     * @return El {@link JButton} de ruta.
     */
    public JButton getBtnRuta() {
        return btnRuta;
    }

    /**
     * Establece el botón para seleccionar la ruta de archivos.
     *
     * @param btnRuta El {@link JButton} a establecer para ruta.
     */
    public void setBtnRuta(JButton btnRuta) {
        this.btnRuta = btnRuta;
    }

    /**
     * Obtiene el combo box de selección de tipo de almacenamiento.
     *
     * @return El {@link JComboBox} de almacenamiento.
     */
    public JComboBox getCbxAlmacenamiento() {
        return cbxAlmacenamiento;
    }

    /**
     * Establece el combo box de selección de tipo de almacenamiento.
     *
     * @param cbxAlmacenamiento El {@link JComboBox} a establecer para almacenamiento.
     */
    public void setCbxAlmacenamiento(JComboBox cbxAlmacenamiento) {
        this.cbxAlmacenamiento = cbxAlmacenamiento;
    }

    /**
     * Establece la etiqueta del título principal de la ventana.
     *
     * @param lblTitulo El {@link JLabel} a establecer como título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la instancia de la vista principal de la aplicación.
     *
     * @return La {@link MenuPrincipalView} enlazada.
     */
    public MenuPrincipalView getPrincipalView() {
        return principalView;
    }

    /**
     * Establece la instancia de la vista principal de la aplicación.
     *
     * @param principalView La {@link MenuPrincipalView} a enlazar.
     */
    public void setPrincipalView(MenuPrincipalView principalView) {
        this.principalView = principalView;
    }

    /**
     * Obtiene el manejador de mensajes para internacionalización.
     *
     * @return El {@link MensajeInternacionalizacionHandler} utilizado en la vista.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de mensajes para internacionalización.
     *
     * @param mensaje El {@link MensajeInternacionalizacionHandler} a establecer.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el texto del ítem actualmente seleccionado en el combo box de tipo de almacenamiento.
     *
     * @return El {@code String} que representa el tipo de almacenamiento seleccionado.
     */
    public String getTipoAlmacenamiento() {
        return (String) cbxAlmacenamiento.getSelectedItem();
    }

    /**
     * Obtiene la ruta de archivo especificada en el campo de texto.
     *
     * @return La ruta de archivo ingresada, sin espacios al inicio o al final.
     */
    public String getRutaArchivo() {
        return txtRuta.getText().trim();
    }

    /**
     * Muestra un mensaje de información en un cuadro de diálogo modal.
     * El texto del mensaje se obtiene a través del manejador de internacionalización.
     *
     * @param mensajeKey La clave de internacionalización del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensajeKey) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeKey));
    }

    /**
     * Limpia el contenido de los campos de texto de usuario y contraseña.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
    }

    /**
     * Establece el texto predeterminado en el campo de texto de la ruta de archivos.
     *
     * @param rutaDefault La cadena de texto que representa la ruta predeterminada a establecer.
     */
    public void setRutaArchivo(String rutaDefault) {
        txtRuta.setText(rutaDefault);
    }

    /**
     * <p>Obtiene el texto del ítem actualmente seleccionado en el combo box de tipo de almacenamiento.</p>
     * <p>Este método devuelve el texto visible en el JComboBox, que puede usarse como clave
     * para determinar el tipo de almacenamiento en otras partes de la aplicación.</p>
     *
     * @return El {@code String} que representa la clave del tipo de almacenamiento seleccionado.
     */
    public String getSelectedStorageTypeKey() {
        return (String) cbxAlmacenamiento.getSelectedItem();
    }
}