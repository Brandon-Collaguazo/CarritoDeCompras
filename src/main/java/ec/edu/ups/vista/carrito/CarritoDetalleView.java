package ec.edu.ups.vista.carrito;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoDetalleView extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JTable tblCarrito;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;

    public CarritoDetalleView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(600, 400);
        setModal(true);

        lblUsuario = new JLabel();
        lblFecha = new JLabel();
        lblSubtotal = new JLabel();
        lblIva = new JLabel();
        lblTotal = new JLabel();

        configurarTabla();
    }

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

    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.detalle.titulo"));

        lblUsuario.setText(mensaje.get("usuario"));
        lblFecha.setText(mensaje.get("fecha"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));
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

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
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

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
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

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}
