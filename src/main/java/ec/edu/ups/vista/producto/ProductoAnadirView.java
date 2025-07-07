package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        configurarListeners();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        //Íconos para los botones
        URL aceptarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if(aceptarURL != null) {
            ImageIcon iconBtnAceptar = new ImageIcon(aceptarURL);
            btnAceptar.setIcon(iconBtnAceptar);
        } else {
            System.out.println("Error");
        }

        URL limpiarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if(limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(limpiarURL);
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.out.println("Error");
        }

        //setResizable(false);
        //setLocationRelativeTo(null);
        //pack();
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("producto.anadir.titulo"));
        lblTitulo.setText(mensaje.get("producto.anadir.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));

        btnAceptar.setText(mensaje.get("aceptar"));
        btnLimpiar.setText(mensaje.get("limpiar"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    private void configurarListeners() {
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
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
