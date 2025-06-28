package ec.edu.ups.vista.producto;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class ProductoModificarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JTextField txtCodigo;
    private JButton btnListar;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        //setLocationRelativeTo(null);

        lblTitulo = new JLabel();
        lblCodigo = new JLabel();
        lblNombre = new JLabel();
        lblPrecio = new JLabel();

        btnBuscar = new JButton();
        btnListar = new JButton();
        btnActualizar = new JButton();

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("producto.modificar.titulo"));
        lblTitulo.setText(mensaje.get("producto.modificar.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnListar.setText(mensaje.get("listar"));
        btnActualizar.setText(mensaje.get("actualizar"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
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

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
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

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
