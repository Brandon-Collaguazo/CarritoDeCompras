package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

/**
 * Vista para listar y buscar productos en el sistema.
 * Muestra una tabla con los productos disponibles y permite realizar búsquedas.
 */
public class ProductoListaView extends JInternalFrame {
    // Componentes de la interfaz
    private JTextField txtBuscar;
    private JButton btnListar;
    private JLabel lblBuscar;
    private JTable tblProducto;
    private JPanel pnlPrincipal;
    private JLabel lblTitulo;

    // Modelo de datos
    private DefaultTableModel modelo;

    // Manejador de internacionalización
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con un manejador de internacionalización.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, comportamiento y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Carga de íconos para los botones
        URL listaURL = ProductoListaView.class.getClassLoader().getResource("imagenes/listar_producto.png");
        if(listaURL != null) {
            ImageIcon iconBtnLista = new ImageIcon(listaURL);
            btnListar.setIcon(iconBtnLista);
        }

        configurarTabla();
    }

    /**
     * Configura la tabla de productos con las columnas necesarias.
     */
    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("codigo"),
                mensaje.get("nombre"),
                mensaje.get("precio")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.lista.titulo"));
        lblTitulo.setText(mensaje.get("producto.lista.titulo"));
        lblBuscar.setText(mensaje.get("buscar"));
        btnListar.setText(mensaje.get("listar"));
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
     * Carga una lista de productos en la tabla.
     *
     * @param listaproductos Lista de productos a mostrar
     */
    public void cargarDatos(List<Producto> listaproductos) {
        modelo.setNumRows(0);
        for(Producto producto : listaproductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Limpia el campo de búsqueda.
     */
    public void limpiarCampos() {
        txtBuscar.setText("");
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
     * Obtiene el campo de texto para búsqueda.
     * @return Campo de búsqueda
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto para búsqueda.
     * @param txtBuscar Campo de búsqueda a establecer
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón para listar productos.
     * @return Botón de listar
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón para listar productos.
     * @param btnBuscar Botón de listar a establecer
     */
    public void setBtnListar(JButton btnBuscar) {
        this.btnListar = btnBuscar;
    }

    /**
     * Obtiene la tabla de productos.
     * @return Tabla de productos
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla de productos.
     * @param tblProducto Tabla de productos a establecer
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el modelo de datos de la tabla.
     * @return Modelo de tabla
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de datos de la tabla.
     * @param modelo Modelo de tabla a establecer
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return Manejador de mensajes internacionalizados
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }
}