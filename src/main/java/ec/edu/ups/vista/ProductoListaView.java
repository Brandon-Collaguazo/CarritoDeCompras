package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoListaView extends JFrame {
    private JTextField txtBuscar;
    private JButton btnListar;
    private JLabel lblBuscar;
    private JTable tblProducto;
    private JPanel pnlPrincipal;
    private DefaultTableModel modelo;

    public ProductoListaView() {
        setContentPane(pnlPrincipal);
        setTitle("LISTADO PRODUCTOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        //setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnBuscar) {
        this.btnListar = btnBuscar;
    }

    public JTable getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    public DefaultTableModel getModelo() {
        return  modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatos(List<Producto> listaproductos) {
        modelo.setNumRows(0);
        for(Producto producto : listaproductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }
}
