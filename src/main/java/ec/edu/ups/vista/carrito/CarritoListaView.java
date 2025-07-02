package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

public class CarritoListaView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTable tblCarrito;
    private JButton btnDetalle;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;

    public CarritoListaView(MensajeInternacionalizacionHandler mensaje) {
        //super("Listar Carritos", true, true, false, true);
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        URL buscarURL = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.out.println("Error");
        }

        URL detalleURL = CarritoListaView.class.getClassLoader().getResource("imagenes/detalle.png");
        if(detalleURL != null) {
            ImageIcon iconBtnDetalle = new ImageIcon(detalleURL);
            btnDetalle.setIcon(iconBtnDetalle);
        } else {
            System.out.println("Error");
        }

        configurarTabla();
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.fecha"),
                mensaje.get("columna.codigo"),
                mensaje.get("columna.usuario"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.lista.titulo"));
        lblTitulo.setText(mensaje.get("carrito.lista.titulo"));

        lblCodigo.setText(mensaje.get("codigo"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnDetalle.setText(mensaje.get("detalle"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    public JTextField getTxtCodigo() {
        return txtUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}
