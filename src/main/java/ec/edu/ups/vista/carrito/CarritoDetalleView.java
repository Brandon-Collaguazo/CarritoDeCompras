package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Vista para mostrar el detalle de un carrito de compras.
 * Permite visualizar información del carrito, productos asociados y totales.
 *
 * @author [Nombre del autor]
 * @version 1.0
 */
public class CarritoDetalleView extends JFrame {
    // Componentes de la interfaz
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;

    // Campos de texto
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JTextField txtCodigo;

    // Tabla de productos
    private JTable tblCarrito;

    // Etiquetas
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JLabel lblCodigo;
    private JLabel lblTitulo;

    // Modelo de tabla
    private DefaultTableModel modelo;

    // Manejador de internacionalización
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con un manejador de internacionalización.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos
     */
    public CarritoDetalleView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, comportamiento y crea la tabla de productos.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(600, 400);
        setResizable(true);

        configurarTabla();
    }

    /**
     * Configura la tabla de productos con las columnas necesarias.
     */
    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.nombre"),
                mensaje.get("columna.precio"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.detalle.titulo"));

        lblTitulo.setText(mensaje.get("carrito.detalle.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblFecha.setText(mensaje.get("fecha.creacion"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));
    }

    /**
     * Cambia el idioma de la interfaz.
     *
     * @param lenguaje Código del lenguaje (ej. "es", "en")
     * @param pais Código del país (ej. "EC", "US")
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Muestra un mensaje al usuario en un diálogo.
     *
     * @param keyMensaje Clave del mensaje a mostrar (según el archivo de internacionalización)
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    // Métodos getters y setters

    /**
     * Obtiene el panel principal de la vista.
     * @return Panel principal
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal Panel principal a establecer
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista.
     * @return Panel superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     * @param pnlSuperior Panel superior a establecer
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior de la vista.
     * @return Panel inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Obtiene el campo de texto para el usuario.
     * @return Campo de usuario
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Obtiene el campo de texto para la fecha.
     * @return Campo de fecha
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return Campo de subtotal
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return Campo de IVA
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Obtiene el campo de texto para el total.
     * @return Campo de total
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Obtiene la tabla de productos del carrito.
     * @return Tabla de productos
     */
    public JTable getTblCarrito() {
        return tblCarrito;
    }

    /**
     * Obtiene la etiqueta de usuario.
     * @return Etiqueta de usuario
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Obtiene la etiqueta de fecha.
     * @return Etiqueta de fecha
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Obtiene la etiqueta de subtotal.
     * @return Etiqueta de subtotal
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Obtiene la etiqueta de IVA.
     * @return Etiqueta de IVA
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Obtiene la etiqueta de total.
     * @return Etiqueta de total
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Obtiene el campo de texto para el código.
     * @return Campo de código
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene la etiqueta de código.
     * @return Etiqueta de código
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Obtiene el modelo de la tabla.
     * @return Modelo de tabla
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return Manejador de mensajes internacionalizados
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje Manejador de mensajes a establecer
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }
}