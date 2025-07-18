package ec.edu.ups.vista.producto;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * La clase `ProductoEliminarView` representa la vista para eliminar un producto.
 * Extiende de `JInternalFrame` para ser utilizada dentro de una aplicación de escritorio.
 * Esta vista permite al usuario buscar un producto por su código, ver su información
 * (nombre y precio), y luego proceder con la eliminación. Soporta la internacionalización
 * de textos a través de `MensajeInternacionalizacionHandler`.
 */
public class ProductoEliminarView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la clase `ProductoEliminarView`.
     * Inicializa la vista y sus componentes, y actualiza los textos
     * según el idioma configurado.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el panel principal, las propiedades del frame (tamaño, cerrable, iconificable, redimensionable),
     * y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(500, 200);
        // setLocationRelativeTo(null); // Comentado ya que JInternalFrame no suele tener un método directo para esto.
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Íconos de los botones
        URL buscarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/buscar_producto.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Buscar.");
        }

        URL eliminarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/eliminar_producto.png");
        if (eliminarURL != null) {
            ImageIcon iconBtnEliminar = new ImageIcon(eliminarURL);
            btnEliminar.setIcon(iconBtnEliminar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Eliminar.");
        }
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.eliminar.titulo"));
        lblTitulo.setText(mensaje.get("producto.eliminar.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
    }

    /**
     * Cambia el idioma de la interfaz gráfica actualizando la configuración del
     * `MensajeInternacionalizacionHandler` y luego volviendo a cargar los textos.
     *
     * @param lenguaje El código de lenguaje (ej. "es", "en").
     * @param pais     El código de país (ej. "EC", "US").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    /**
     * Limpia el campo de texto del código del producto.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
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
     * Establece el panel principal de la vista.
     *
     * @param pnlPrincipal El `JPanel` a establecer como principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     *
     * @return El `JTextField` del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del producto.
     *
     * @param txtCodigo El `JTextField` del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón para buscar un producto.
     *
     * @return El `JButton` de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar un producto.
     *
     * @param btnBuscar El `JButton` de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para eliminar el producto.
     *
     * @return El `JButton` de eliminación.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar el producto.
     *
     * @param btnEliminar El `JButton` de eliminación.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene la etiqueta del código del producto.
     *
     * @return El `JLabel` del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código del producto.
     *
     * @param lblCodigo El `JLabel` del código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene el campo de texto para el nombre del producto.
     *
     * @return El `JTextField` del nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del producto.
     *
     * @param txtNombre El `JTextField` del nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el precio del producto.
     *
     * @return El `JTextField` del precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio del producto.
     *
     * @param txtPrecio El `JTextField` del precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene la etiqueta del nombre del producto.
     *
     * @return El `JLabel` del nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del nombre del producto.
     *
     * @param lblNombre El `JLabel` del nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene la etiqueta del precio del producto.
     *
     * @return El `JLabel` del precio.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * Establece la etiqueta del precio del producto.
     *
     * @param lblPrecio El `JLabel` del precio.
     */
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
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
     * Establece la etiqueta del título de la vista.
     *
     * @param lblTitulo El `JLabel` del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
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
     * Establece el manejador de internacionalización de mensajes.
     *
     * @param mensaje El `MensajeInternacionalizacionHandler` a establecer.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }
}