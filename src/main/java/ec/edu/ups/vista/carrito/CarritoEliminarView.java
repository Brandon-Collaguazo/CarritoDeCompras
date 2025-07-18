package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * La clase `CarritoEliminarView` representa la vista para eliminar un carrito de compras.
 * Extiende de `JInternalFrame` para ser utilizada dentro de una aplicación de escritorio.
 * Esta vista permite al usuario buscar un carrito por su código, mostrar los productos
 * asociados en una tabla, y luego proceder con la eliminación del carrito. Soporta la
 * internacionalización de textos a través de `MensajeInternacionalizacionHandler`.
 */
public class CarritoEliminarView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JTextField txtCodigo;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JTable tblProducto;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la clase `CarritoEliminarView`.
     * Inicializa la vista y sus componentes, y actualiza los textos
     * según el idioma configurado.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.eliminar.titulo"));
        lblTitulo.setText(mensaje.get("carrito.eliminar.titulo"));

        lblCodigo.setText(mensaje.get("codigo"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
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
        URL buscarURL = CarritoEliminarView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Buscar.");
        }

        URL eliminarURL = CarritoEliminarView.class.getClassLoader().getResource("imagenes/eliminar_carrito.png");
        if (eliminarURL != null) {
            ImageIcon iconBtnEliminar = new ImageIcon(eliminarURL);
            btnEliminar.setIcon(iconBtnEliminar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Eliminar.");
        }

        configurarTabla();
    }

    /**
     * Configura el modelo de la tabla de productos (`tblProducto`).
     * Establece las columnas de la tabla utilizando textos internacionalizados.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.nombre"),
                mensaje.get("columna.precio"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
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
     * Obtiene el panel superior de la vista.
     *
     * @return El `JPanel` superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     *
     * @param pnlSuperior El `JPanel` superior.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel central de la vista.
     *
     * @return El `JPanel` central.
     */
    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    /**
     * Establece el panel central de la vista.
     *
     * @param pnlCentral El `JPanel` central.
     */
    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
    }

    /**
     * Obtiene el campo de texto para el código del carrito a eliminar.
     *
     * @return El `JTextField` del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del carrito a eliminar.
     *
     * @param txtCodigo El `JTextField` del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el campo de texto que muestra el subtotal del carrito.
     *
     * @return El `JTextField` del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto que muestra el subtotal del carrito.
     *
     * @param txtSubtotal El `JTextField` del subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto que muestra el valor del IVA del carrito.
     *
     * @return El `JTextField` del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto que muestra el valor del IVA del carrito.
     *
     * @param txtIva El `JTextField` del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el campo de texto que muestra el total del carrito.
     *
     * @return El `JTextField` del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto que muestra el total del carrito.
     *
     * @param txtTotal El `JTextField` del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para eliminar el carrito.
     *
     * @return El `JButton` de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar el carrito.
     *
     * @param btnEliminar El `JButton` de eliminar.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene el botón para buscar el carrito.
     *
     * @return El `JButton` de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar el carrito.
     *
     * @param btnBuscar El `JButton` de buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla donde se muestran los productos del carrito a eliminar.
     *
     * @return El `JTable` de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla donde se muestran los productos del carrito a eliminar.
     *
     * @param tblProducto El `JTable` de productos.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
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
     * Obtiene la etiqueta para el código del carrito.
     *
     * @return El `JLabel` para el código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta para el código del carrito.
     *
     * @param lblCodigo El `JLabel` para el código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene la etiqueta para el subtotal del carrito.
     *
     * @return El `JLabel` para el subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta para el subtotal del carrito.
     *
     * @param lblSubtotal El `JLabel` para el subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta para el IVA del carrito.
     *
     * @return El `JLabel` para el IVA.
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta para el IVA del carrito.
     *
     * @param lblIva El `JLabel` para el IVA.
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta para el total del carrito.
     *
     * @return El `JLabel` para el total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta para el total del carrito.
     *
     * @param lblTotal El `JLabel` para el total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }
}