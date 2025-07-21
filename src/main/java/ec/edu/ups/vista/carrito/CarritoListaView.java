package ec.edu.ups.vista.carrito;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * La clase `CarritoListaView` representa la vista para listar los carritos de compras.
 * Extiende de `JInternalFrame` para ser utilizada dentro de una aplicación de escritorio.
 * Esta vista permite buscar carritos, mostrar una lista de ellos en una tabla,
 * y ver los detalles de un carrito seleccionado. Soporta la internacionalización
 * de textos a través de `MensajeInternacionalizacionHandler` y ajusta la visibilidad
 * de ciertos componentes según el rol del usuario.
 */
public class CarritoListaView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario; // Se usa para el código del usuario o del carrito, dependiendo del rol.
    private JButton btnBuscar;
    private JTable tblCarrito;
    private JButton btnDetalle;
    private JLabel lblTitulo;
    private JLabel lblCodigo; // Etiqueta para el campo de búsqueda de código/usuario.
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;
    private Rol rol;

    /**
     * Constructor de la clase `CarritoListaView`.
     * Inicializa la vista y sus componentes, establece el rol del usuario,
     * configura el acceso y actualiza los textos según el idioma.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public CarritoListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        this.rol = rol;
        initComponents();
        configurarAcceso(); // Configura la visibilidad de los componentes según el rol
        actualizarTextos();
    }

    /**
     * Configura la visibilidad de ciertos componentes de la interfaz
     * basándose en el rol del usuario. Por ejemplo, oculta el campo de
     * búsqueda de usuario si el rol es `USUARIO`.
     */
    private void configurarAcceso() {
        if (rol == Rol.USUARIO) {
            lblCodigo.setVisible(false);
            txtUsuario.setVisible(false);
        }
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el panel principal, las propiedades del frame (cerrable, redimensionable, tamaño),
     * y carga los íconos de los botones. También llama a `configurarTabla()`.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        // Íconos para los botones
        URL buscarURL = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Buscar.");
        }

        URL detalleURL = CarritoListaView.class.getClassLoader().getResource("imagenes/detalle.png");
        if (detalleURL != null) {
            ImageIcon iconBtnDetalle = new ImageIcon(detalleURL);
            btnDetalle.setIcon(iconBtnDetalle);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Detalle.");
        }

        configurarTabla();
    }

    /**
     * Configura el modelo de la tabla de carritos (`tblCarrito`).
     * Establece las columnas de la tabla utilizando textos internacionalizados.
     */
    public void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.usuario"),
                mensaje.get("columna.fecha"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.lista.titulo"));
        lblTitulo.setText(mensaje.get("carrito.lista.titulo"));

        // Se usa lblCodigo para el texto "Código" o "Usuario" dependiendo del contexto de búsqueda
        lblCodigo.setText(mensaje.get("codigo"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnDetalle.setText(mensaje.get("detalle"));
    }

    /**
     * Cambia el idioma de la interfaz gráfica actualizando la configuración del
     * `MensajeInternacionalizacionHandler` y luego volviendo a cargar los textos y la configuración de la tabla.
     *
     * @param lenguaje El código de lenguaje (ej. "es", "en").
     * @param pais     El código de país (ej. "EC", "US").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Limpia el campo de texto del usuario y vacía la tabla de carritos.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tblCarrito.getModel();
        modelo.setRowCount(0);
    }

    /**
     * Muestra un mensaje de advertencia o información en un `JOptionPane`.
     * El mensaje se obtiene a través del objeto `MensajeInternacionalizacionHandler` usando la clave proporcionada.
     *
     * @param keyMensaje La clave del mensaje a mostrar, definida en los recursos de internacionalización.
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    // --- Getters y Setters para los componentes de la interfaz ---

    /**
     * Obtiene el panel principal de la vista.
     *
     * @return El `JPanel` principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista.
     *
     * @return El `JPanel` superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Obtiene el panel inferior de la vista.
     *
     * @return El `JPanel` inferior.
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Obtiene el campo de texto utilizado para el código de usuario o del carrito.
     *
     * @return El `JTextField` del código de usuario.
     */
    public JTextField getTxtCodigo() { // Nombre del método ajustado a `getTxtCodigo` para consistencia, aunque el campo sea `txtUsuario`.
        return txtUsuario;
    }

    /**
     * Obtiene el botón para iniciar la búsqueda de carritos.
     *
     * @return El `JButton` de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene la tabla donde se listan los carritos.
     *
     * @return El `JTable` de carritos.
     */
    public JTable getTblCarrito() {
        return tblCarrito;
    }

    /**
     * Obtiene el botón para ver el detalle de un carrito seleccionado.
     *
     * @return El `JButton` de detalle.
     */
    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    /**
     * Obtiene la etiqueta del título de la vista.
     *
     * @return El `JLabel` del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Obtiene la etiqueta para el campo de búsqueda de código/usuario.
     *
     * @return El `JLabel` para el código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Obtiene el manejador de internacionalización de mensajes.
     *
     * @return El `MensajeInternacionalizacionHandler` utilizado en la vista.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene el modelo de tabla utilizado para `tblCarrito`.
     *
     * @return El `DefaultTableModel` de la tabla de carritos.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de tabla para `tblCarrito`.
     *
     * @param modelo El `DefaultTableModel` a establecer.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}