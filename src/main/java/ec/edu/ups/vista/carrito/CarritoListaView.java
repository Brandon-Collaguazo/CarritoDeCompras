package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoListaView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTable tblCarrito;
    private JButton btnDetalle;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
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

        lblTitulo = new JLabel();
        lblUsuario = new JLabel();

        btnBuscar = new JButton();
        btnDetalle = new JButton();

        configurarTabla();
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.subtotal"),
                mensaje.get("columna.iva"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.lista.titulo"));
        lblTitulo.setText(mensaje.get("carrito.lista.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));

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

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
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

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public void setBtnDetalle(JButton btnDetalle) {
        this.btnDetalle = btnDetalle;
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
