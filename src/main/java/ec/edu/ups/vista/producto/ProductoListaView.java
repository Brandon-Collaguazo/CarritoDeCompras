package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnListar;
    private JLabel lblBuscar;
    private JTable tblProducto;
    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;

    public ProductoListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(500, 500);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        lblTitulo = new JLabel();
        lblBuscar = new JLabel();

        btnListar = new JButton();
        txtBuscar = new JTextField();

        configurarTabla();
    }

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

    private void actualizarTextos() {
        setTitle(mensaje.get("producto.lista.titulo"));
        lblTitulo.setText(mensaje.get("producto.lista.titulo"));
        lblBuscar.setText(mensaje.get("buscar"));
        btnListar.setText(mensaje.get("listar"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
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
                    producto.getPrecio(),
            };
            modelo.addRow(fila);
        }
    }
}
