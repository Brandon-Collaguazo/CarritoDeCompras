package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Vista para la gestión y actualización del carrito de compras.
 * Permite buscar carritos, agregar/eliminar productos y calcular totales.
 *
 * @author [Nombre del autor]
 * @version 1.0
 */
public class CarritoActualizarView extends JInternalFrame {
    // Componentes de la interfaz
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;

    // Campos de texto
    private JTextField txtCodigoC;
    private JTextField txtCodigoP;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;

    // Botones
    private JButton btnBuscarC;
    private JButton btnBuscarP;
    private JButton btnAnadir;
    private JButton btnGuardar;

    // Tabla de productos
    private JTable tblProducto;

    // Etiquetas
    private JLabel lblTitulo;
    private JLabel lblCodigoCarrito;
    private JLabel lblCodigoProducto;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    // Listeners
    private ActionListener modificarListener;
    private ActionListener eliminarListener;

    // Internacionalización
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con un manejador de internacionalización.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos
     */
    public CarritoActualizarView(MensajeInternacionalizacionHandler mensaje) {
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
        setClosable(true);
        setResizable(true);
        setSize(1200, 500);

        // Carga de íconos para los botones
        URL buscarCarritoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if(buscarCarritoURL != null) {
            ImageIcon iconBtnBuscarC = new ImageIcon(buscarCarritoURL);
            btnBuscarC.setIcon(iconBtnBuscarC);
        } else {
            System.out.println("Error al cargar ícono de buscar carrito");
        }

        URL buscarProductoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/buscar_producto.png");
        if(buscarProductoURL != null) {
            ImageIcon iconBtnBuscarP = new ImageIcon(buscarProductoURL);
            btnBuscarP.setIcon(iconBtnBuscarP);
        } else {
            System.out.println("Error al cargar ícono de buscar producto");
        }

        URL anadirProductoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/anadir_producto.png");
        if(anadirProductoURL != null) {
            ImageIcon iconBtnAnadir = new ImageIcon(anadirProductoURL);
            btnAnadir.setIcon(iconBtnAnadir);
        } else {
            System.out.println("Error al cargar ícono de añadir producto");
        }

        URL guardarURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/guardar.png");
        if(guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(guardarURL);
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.out.println("Error al cargar ícono de guardar");
        }

        configurarTabla();
    }

    /**
     * Configura la tabla de productos con columnas y renderizadores personalizados.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                        mensaje.get("columna.codigo"),
                        mensaje.get("columna.nombre"),
                        mensaje.get("columna.precio"),
                        mensaje.get("columna.cantidad"),
                        mensaje.get("columna.subtotal"),
                        mensaje.get("columna.acciones")
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 5;
            }
        };

        tblProducto.setModel(modelo);

        tblProducto.getColumn(mensaje.get("columna.acciones"))
                .setCellRenderer(new ButtonRenderer());

        tblProducto.getColumn(mensaje.get("columna.acciones"))
                .setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.actualizar.titulo"));
        lblTitulo.setText(mensaje.get("carrito.actualizar.titulo"));

        lblCodigoCarrito.setText(mensaje.get("codigo.carrito"));
        lblCodigoProducto.setText(mensaje.get("codigo.producto"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        btnBuscarC.setText(mensaje.get("buscar.carrito"));
        btnBuscarP.setText(mensaje.get("buscar.producto"));
        btnAnadir.setText(mensaje.get("anadir"));
        btnGuardar.setText(mensaje.get("guardar"));
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
     * Renderizador personalizado para celdas con botones de acción.
     */
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnModificar;
        private JButton btnEliminar;

        /**
         * Constructor que inicializa los botones de acción.
         */
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModificar = new JButton(mensaje.get("modificar"));
            btnEliminar = new JButton(mensaje.get("eliminar"));
            add(btnModificar);
            add(btnEliminar);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    /**
     * Editor personalizado para celdas con botones de acción.
     */
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton btnModificar;
        private JButton btnEliminar;
        private int currentRow;

        /**
         * Constructor que configura los botones y sus listeners.
         *
         * @param checkBox Componente base para el editor
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModificar = new JButton(mensaje.get("modificar"));
            btnEliminar = new JButton(mensaje.get("eliminar"));

            panel.add(btnModificar);
            panel.add(btnEliminar);

            btnModificar.addActionListener(e -> {
                fireEditingStopped();
                if (modificarListener != null) {
                    modificarListener.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(currentRow)));
                }
            });

            btnEliminar.addActionListener(e -> {
                fireEditingStopped();
                if (eliminarListener != null) {
                    eliminarListener.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(currentRow)));
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    /**
     * Obtiene la cantidad de un producto en una fila específica.
     *
     * @param fila Índice de la fila en la tabla
     * @return Cantidad del producto
     * @throws IllegalArgumentException Si la fila es inválida
     * @throws RuntimeException Si hay error al convertir la cantidad
     */
    public int getCantidadEnFila(int fila) {
        if(fila < 0 || fila >= tblProducto.getRowCount()) {
            throw new IllegalArgumentException("Fila inválida");
        }
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 3).toString());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la cantidad");
        }
    }

    /**
     * Obtiene el código de un producto en una fila específica.
     *
     * @param fila Índice de la fila en la tabla
     * @return Código del producto o -1 si hay error
     */
    public int getCodigoProductoEnFila(int fila) {
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Limpia todos los datos de la tabla de productos.
     */
    public void limpiarTabla() {
        ((DefaultTableModel)tblProducto.getModel()).setRowCount(0);
        actualizarTotales(0.0, 0.0, 0.0);
    }

    /**
     * Actualiza los campos de totales con los valores calculados.
     *
     * @param subtotal Subtotal de la compra
     * @param iva Valor del IVA
     * @param total Total a pagar
     */
    public void actualizarTotales(double subtotal, double iva, double total) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mensaje.getLocale());
        txtSubtotal.setText(formatoMoneda.format(subtotal));
        txtIva.setText(formatoMoneda.format(iva));
        txtTotal.setText(formatoMoneda.format(total));
    }

    /**
     * Muestra un mensaje al usuario en un diálogo.
     *
     * @param keyMensaje Clave del mensaje a mostrar
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
     * Obtiene el campo de texto para el código de carrito.
     * @return Campo de código de carrito
     */
    public JTextField getTxtCodigoC() {
        return txtCodigoC;
    }

    /**
     * Establece el campo de texto para el código de carrito.
     * @param txtCodigoC Campo de código a establecer
     */
    public void setTxtCodigoC(JTextField txtCodigoC) {
        this.txtCodigoC = txtCodigoC;
    }

    /**
     * Obtiene el campo de texto para el código de producto.
     * @return Campo de código de producto
     */
    public JTextField getTxtCodigoP() {
        return txtCodigoP;
    }

    /**
     * Establece el campo de texto para el código de producto.
     * @param txtCodigoP Campo de código a establecer
     */
    public void setTxtCodigoP(JTextField txtCodigoP) {
        this.txtCodigoP = txtCodigoP;
    }

    /**
     * Obtiene el botón para buscar carrito.
     * @return Botón de buscar carrito
     */
    public JButton getBtnBuscarC() {
        return btnBuscarC;
    }

    /**
     * Establece el botón para buscar carrito.
     * @param btnBuscarC Botón a establecer
     */
    public void setBtnBuscarC(JButton btnBuscarC) {
        this.btnBuscarC = btnBuscarC;
    }

    /**
     * Obtiene el botón para buscar producto.
     * @return Botón de buscar producto
     */
    public JButton getBtnBuscarP() {
        return btnBuscarP;
    }

    /**
     * Establece el botón para buscar producto.
     * @param btnBuscarP Botón a establecer
     */
    public void setBtnBuscarP(JButton btnBuscarP) {
        this.btnBuscarP = btnBuscarP;
    }

    /**
     * Obtiene el botón para añadir producto.
     * @return Botón de añadir producto
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón para añadir producto.
     * @param btnAnadir Botón a establecer
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    /**
     * Obtiene el botón para guardar cambios.
     * @return Botón de guardar
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar cambios.
     * @param btnGuardar Botón a establecer
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
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
     * @param tblProducto Tabla a establecer
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el campo de subtotal.
     * @return Campo de subtotal
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de subtotal.
     * @param txtSubtotal Campo a establecer
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de IVA.
     * @return Campo de IVA
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de IVA.
     * @param txtIva Campo a establecer
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el campo de total.
     * @return Campo de total
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de total.
     * @param txtTotal Campo a establecer
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el panel superior.
     * @return Panel superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior.
     * @param pnlSuperior Panel a establecer
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior.
     * @return Panel inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior.
     * @param pnlInferior Panel a establecer
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    /**
     * Obtiene la etiqueta de título.
     * @return Etiqueta de título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta de título.
     * @param lblTitulo Etiqueta a establecer
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta de código de carrito.
     * @return Etiqueta de código
     */
    public JLabel getLblCodigoCarrito() {
        return lblCodigoCarrito;
    }

    /**
     * Establece la etiqueta de código de carrito.
     * @param lblCodigoCarrito Etiqueta a establecer
     */
    public void setLblCodigoCarrito(JLabel lblCodigoCarrito) {
        this.lblCodigoCarrito = lblCodigoCarrito;
    }

    /**
     * Obtiene la etiqueta de código de producto.
     * @return Etiqueta de código
     */
    public JLabel getLblCodigoProducto() {
        return lblCodigoProducto;
    }

    /**
     * Establece la etiqueta de código de producto.
     * @param lblCodigoProducto Etiqueta a establecer
     */
    public void setLblCodigoProducto(JLabel lblCodigoProducto) {
        this.lblCodigoProducto = lblCodigoProducto;
    }

    /**
     * Obtiene la etiqueta de subtotal.
     * @return Etiqueta de subtotal
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta de subtotal.
     * @param lblSubtotal Etiqueta a establecer
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta de IVA.
     * @return Etiqueta de IVA
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta de IVA.
     * @param lblIva Etiqueta a establecer
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta de total.
     * @return Etiqueta de total
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta de total.
     * @param lblTotal Etiqueta a establecer
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene el listener para modificar productos.
     * @return Listener de modificación
     */
    public ActionListener getModificarListener() {
        return modificarListener;
    }

    /**
     * Obtiene el listener para eliminar productos.
     * @return Listener de eliminación
     */
    public ActionListener getEliminarListener() {
        return eliminarListener;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return Manejador de mensajes
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje Manejador a establecer
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Establece el listener para modificar productos.
     * @param listener Listener a establecer
     */
    public void setModificarListener(ActionListener listener) {
        this.modificarListener = listener;
    }

    /**
     * Establece el listener para eliminar productos.
     * @param listener Listener a establecer
     */
    public void setEliminarListener(ActionListener listener) {
        this.eliminarListener = listener;
    }
}