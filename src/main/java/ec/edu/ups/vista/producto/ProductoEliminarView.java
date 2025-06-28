package ec.edu.ups.vista.producto;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JButton btnListar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(500, 200);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        lblTitulo = new JLabel();
        lblCodigo = new JLabel();
        lblNombre = new JLabel();
        lblPrecio = new JLabel();

        btnBuscar = new JButton();
        btnEliminar = new JButton();
        btnListar = new JButton();

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("producto.eliminar.titulo"));
        lblTitulo.setText(mensaje.get("producto.eliminar.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
        btnListar.setText(mensaje.get("listar"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
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

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
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

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
