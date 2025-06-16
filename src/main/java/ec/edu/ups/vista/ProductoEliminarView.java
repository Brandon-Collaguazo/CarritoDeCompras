package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoEliminarView extends JFrame {
    private JPanel pnlPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JTable tblProducto;
    private JButton btnListar;
    private DefaultTableModel modelo;

    public ProductoEliminarView() {
        setContentPane(pnlPrincipal);
        setTitle("ELIMINACIÓN DE PRODUCTOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField textField1) {
        this.txtCodigo = textField1;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTable getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarProducto(List<Producto> listaproductos) {
        limpiarTabla();
        if(listaproductos != null) {
            for(Producto producto : listaproductos) {
                Object[] fila = {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getPrecio()
                };
                modelo.addRow(fila);
            }
        } else {
            mostrarMensaje("Producto no encontrado");
            limpiarCampos();
        }
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
    }

    public void limpiarTabla() {
        modelo.setNumRows(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
