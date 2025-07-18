package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * La clase `CarritoAnadirView` representa la vista para añadir productos al carrito de compras.
 * Extiende de `JInternalFrame` para ser utilizada dentro de un entorno de aplicación de escritorio.
 * Esta vista permite buscar productos, añadirlos a una tabla, y calcular subtotales, IVA y totales.
 * También soporta la internacionalización de textos a través de `MensajeInternacionalizacionHandler`.
 *
 * @author [Tu Nombre/El nombre del equipo]
 * @version 1.0
 * @since 2025-07-18
 */
public class CarritoAnadirView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JButton btnBuscar;
    private JTable tblProducto;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JPanel pnlSuperior;
    private JPanel pnlCentral;
    private JPanel pnlInferior;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la clase `CarritoAnadirView`.
     * Inicializa la vista y sus componentes, además de cargar los datos iniciales
     * y actualizar los textos según el idioma configurado.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        cargarDatos();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el panel principal, el título del frame, las propiedades de cierre y redimensionamiento,
     * el tamaño, y carga los iconos de los botones. También llama a `configurarTabla()`.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setTitle(mensaje.get("carrito.anadir.titulo"));
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        // Íconos de los botones
        URL buscarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/buscar_producto.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Buscar.");
        }

        URL anadirURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/anadir_producto.png");
        if (anadirURL != null) {
            ImageIcon iconBtnAnadir = new ImageIcon(anadirURL);
            btnAnadir.setIcon(iconBtnAnadir);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Añadir.");
        }

        URL guardarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(guardarURL);
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Guardar.");
        }

        URL limpiarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(limpiarURL);
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Limpiar.");
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
                mensaje.get("columna.subtotal"),
                mensaje.get("columna.iva"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     */
    private void actualizarTextos() {
        // Actualizar título y labels
        setTitle(mensaje.get("carrito.anadir.titulo"));
        lblTitulo.setText(mensaje.get("carrito.anadir.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));
        lblCantidad.setText(mensaje.get("cantidad"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        // Actualizar botones
        btnBuscar.setText(mensaje.get("buscar"));
        btnAnadir.setText(mensaje.get("anadir"));
        btnGuardar.setText(mensaje.get("guardar"));
        btnLimpiar.setText(mensaje.get("limpiar"));
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
     * Carga los datos iniciales para el combo box de cantidad (`cbxCantidad`).
     * Añade números del 1 al 10 como opciones por defecto y selecciona el primer elemento.
     */
    public void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 0; i < 10; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
        cbxCantidad.setSelectedIndex(0);
    }

    /**
     * Muestra un mensaje de advertencia o información en un `JOptionPane`.
     * El mensaje se obtiene a través del objeto `MensajeInternacionalizacionHandler` usando la clave proporcionada.
     *
     * @param keymensaje La clave del mensaje a mostrar, definida en los recursos de internacionalización.
     */
    public void mostrarMensaje(String keymensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keymensaje));
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
     * Obtiene el botón para buscar productos.
     *
     * @return El `JButton` de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar productos.
     *
     * @param btnBuscar El `JButton` de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla donde se muestran los productos añadidos.
     *
     * @return El `JTable` de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla donde se muestran los productos añadidos.
     *
     * @param tblProducto El `JTable` de productos.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el campo de texto para el subtotal.
     *
     * @return El `JTextField` del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto para el subtotal.
     *
     * @param txtSubtotal El `JTextField` del subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto para el IVA.
     *
     * @return El `JTextField` del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto para el IVA.
     *
     * @param txtIva El `JTextField` del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el campo de texto para el total.
     *
     * @return El `JTextField` del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto para el total.
     *
     * @param txtTotal El `JTextField` del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para guardar el carrito.
     *
     * @return El `JButton` de guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar el carrito.
     *
     * @param btnGuardar El `JButton` de guardar.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene el botón para limpiar los campos y la tabla.
     *
     * @return El `JButton` de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón para limpiar los campos y la tabla.
     *
     * @param btnLimpiar El `JButton` de limpiar.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene el combo box para seleccionar la cantidad.
     *
     * @return El `JComboBox` de cantidad.
     */
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Establece el combo box para seleccionar la cantidad.
     *
     * @param cbxCantidad El `JComboBox` de cantidad.
     */
    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
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
     * Obtiene el panel inferior de la vista.
     *
     * @return El `JPanel` inferior.
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior de la vista.
     *
     * @param pnlInferior El `JPanel` inferior.
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
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
     * Obtiene el botón para añadir productos a la tabla.
     *
     * @return El `JButton` de añadir.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón para añadir productos a la tabla.
     *
     * @param btnAnadir El `JButton` de añadir.
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
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
     * Obtiene la etiqueta de la cantidad.
     *
     * @return El `JLabel` de cantidad.
     */
    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    /**
     * Establece la etiqueta de la cantidad.
     *
     * @param lblCantidad El `JLabel` de cantidad.
     */
    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     *
     * @return El `JLabel` del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta del subtotal.
     *
     * @param lblSubtotal El `JLabel` del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta del IVA.
     *
     * @return El `JLabel` del IVA.
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta del IVA.
     *
     * @param lblIva El `JLabel` del IVA.
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta del total.
     *
     * @return El `JLabel` del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta del total.
     *
     * @param lblTotal El `JLabel` del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
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
