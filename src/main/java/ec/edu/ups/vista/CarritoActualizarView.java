package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarritoActualizarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JTextField txtCodigoC;
    private JButton btnBuscarC;
    private JTextField txtCodigoP;
    private JButton btnBuscarP;
    private JButton btnAnadir;
    private JTable tblProducto;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;

    // Listeners para los botones de la tabla
    private ActionListener modificarListener;
    private ActionListener eliminarListener;

    public CarritoActualizarView() {
        // Configuración básica de la ventana
        setContentPane(pnlPrincipal);
        setTitle("Actualizar Carrito");
        setClosable(true);
        setResizable(true);
        setSize(1000, 500);

        // Configurar modelo de tabla con botones
        setupTableModel();
    }

    public void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblProducto.getModel();
        model.setRowCount(0);
        txtSubtotal.setText("0.00");
        txtIva.setText("0.00");
        txtTotal.setText("0.00");
    }

    private void setupTableModel() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Código", "Nombre", "Precio", "Cantidad", "SubTotal", "Acciones"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 5;
            }
        };
        tblProducto.setModel(modelo);

        // Configurar renderizador y editor para botones
        tblProducto.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tblProducto.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // Clase interna para renderizar botones
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnModificar;
        private JButton btnEliminar;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModificar = new JButton("Mod");
            btnEliminar = new JButton("Elim");
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
            btnModificar = new JButton("Mod");
            btnEliminar = new JButton("Elim");

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

    // Métodos para establecer los listeners desde el controlador
    public void setModificarListener(ActionListener listener) {
        this.modificarListener = listener;
    }

    public void setEliminarListener(ActionListener listener) {
        this.eliminarListener = listener;
    }

    // Todos tus getters existentes (los mantengo igual)
    public JPanel getPnlPrincipal() { return pnlPrincipal; }
    public JTextField getTxtCodigoC() { return txtCodigoC; }
    public JButton getBtnBuscarC() { return btnBuscarC; }
    public JTextField getTxtCodigoP() { return txtCodigoP; }
    public JButton getBtnBuscarP() { return btnBuscarP; }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JTable getTblProducto() { return tblProducto; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIva() { return txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnGuardar() { return btnGuardar; }

    // Métodos utilitarios (los mantengo igual)
    public int getFilaSeleccionada() {
        return tblProducto.getSelectedRow();
    }

    public int getCodigoProductoEnFila(int fila) {
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            return -1;
        }
    }

    public int getCantidadEnFila(int fila) {
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 3).toString());
        } catch (Exception e) {
            return -1;
        }
    }

    public void actualizarTotales(double subtotal, double iva, double total) {
        txtSubtotal.setText(String.format("$%.2f", subtotal));
        txtIva.setText(String.format("$%.2f", iva));
        txtTotal.setText(String.format("$%.2f", total));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}