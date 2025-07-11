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

public class CarritoActualizarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;

    private JTextField txtCodigoC;
    private JTextField txtCodigoP;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;

    private JButton btnBuscarC;
    private JButton btnBuscarP;
    private JButton btnAnadir;
    private JButton btnGuardar;

    private JTable tblProducto;

    private JLabel lblTitulo;
    private JLabel lblCodigoCarrito;
    private JLabel lblCodigoProducto;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    private ActionListener modificarListener;
    private ActionListener eliminarListener;

    private MensajeInternacionalizacionHandler mensaje;

    public CarritoActualizarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(900, 500);

        URL buscarCarritoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if(buscarCarritoURL != null) {
            ImageIcon iconBtnBuscarC = new ImageIcon(buscarCarritoURL);
            btnBuscarC.setIcon(iconBtnBuscarC);
        } else {
            System.out.println("Error");
        }
        URL buscarProductoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/buscar_producto.png");
        if(buscarProductoURL != null) {
            ImageIcon iconBtnBuscarP = new ImageIcon(buscarProductoURL);
            btnBuscarP.setIcon(iconBtnBuscarP);
        } else {
            System.out.println("Error");
        }

        URL anadirProductoURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/anadir_producto.png");
        if(anadirProductoURL != null) {
            ImageIcon iconBtnAnadir = new ImageIcon(anadirProductoURL);
            btnAnadir.setIcon(iconBtnAnadir);
        } else {
            System.out.println("error");
        }

        URL guardarURL = CarritoActualizarView.class.getClassLoader().getResource("imagenes/guardar.png");
        if(guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(guardarURL);
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.out.println("error");
        }

        configurarTabla();
    }

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

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnModificar;
        private JButton btnEliminar;

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

    // Clase interna para manejar eventos de botones
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton btnModificar;
        private JButton btnEliminar;
        private int currentRow;

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

    public int getCodigoProductoEnFila(int fila) {
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            return -1;
        }
    }

    // Métodos principales
    public void limpiarTabla() {
        ((DefaultTableModel)tblProducto.getModel()).setRowCount(0);
        actualizarTotales(0.0, 0.0, 0.0);
    }

    public void actualizarTotales(double subtotal, double iva, double total) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mensaje.getLocale());
        txtSubtotal.setText(formatoMoneda.format(subtotal));
        txtIva.setText(formatoMoneda.format(iva));
        txtTotal.setText(formatoMoneda.format(total));
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JTextField getTxtCodigoC() {
        return txtCodigoC;
    }

    public void setTxtCodigoC(JTextField txtCodigoC) {
        this.txtCodigoC = txtCodigoC;
    }

    public JTextField getTxtCodigoP() {
        return txtCodigoP;
    }

    public void setTxtCodigoP(JTextField txtCodigoP) {
        this.txtCodigoP = txtCodigoP;
    }

    public JButton getBtnBuscarC() {
        return btnBuscarC;
    }

    public void setBtnBuscarC(JButton btnBuscarC) {
        this.btnBuscarC = btnBuscarC;
    }

    public JButton getBtnBuscarP() {
        return btnBuscarP;
    }

    public void setBtnBuscarP(JButton btnBuscarP) {
        this.btnBuscarP = btnBuscarP;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JTable getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblCodigoCarrito() {
        return lblCodigoCarrito;
    }

    public void setLblCodigoCarrito(JLabel lblCodigoCarrito) {
        this.lblCodigoCarrito = lblCodigoCarrito;
    }

    public JLabel getLblCodigoProducto() {
        return lblCodigoProducto;
    }

    public void setLblCodigoProducto(JLabel lblCodigoProducto) {
        this.lblCodigoProducto = lblCodigoProducto;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public ActionListener getModificarListener() {
        return modificarListener;
    }

    public ActionListener getEliminarListener() {
        return eliminarListener;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void setModificarListener(ActionListener listener) { this.modificarListener = listener; }
    public void setEliminarListener(ActionListener listener) { this.eliminarListener = listener; }
}