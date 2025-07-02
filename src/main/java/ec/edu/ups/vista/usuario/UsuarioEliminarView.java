package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class UsuarioEliminarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblAsociado;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JTextField txtRegistro;
    private JTextField txtAcceso;
    private JTextField txtAsociado;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        URL buscarURL = UsuarioEliminarView.class.getResource("imagenes/buscarUsario.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error");
        }

        URL eliminarURL = UsuarioEliminarView.class.getResource("imagenes/eliminar_usuario.png");
        if(eliminarURL != null) {
            ImageIcon iconBtnEliminar = new ImageIcon(eliminarURL);
            btnEliminar.setIcon(iconBtnEliminar);
        } else {
            System.out.println("Error");
        }

        URL cancelarURL = UsuarioEliminarView.class.getResource("imagenes/cancelar.png");
        if(cancelarURL != null) {
            ImageIcon iconBtnCancelar = new ImageIcon(cancelarURL);
            btnCancelar.setIcon(iconBtnCancelar);
        } else {
            System.out.println("Error");
        }
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.eliminar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.eliminar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblAsociado.setText(mensaje.get("asociado"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
        btnCancelar.setText(mensaje.get("cancelar"));
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

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblAsociado() {
        return lblAsociado;
    }

    public void setLblAsociado(JLabel lblAsociado) {
        this.lblAsociado = lblAsociado;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtRegistro() {
        return txtRegistro;
    }

    public void setTxtRegistro(JTextField txtRegistro) {
        this.txtRegistro = txtRegistro;
    }

    public JTextField getTxtAcceso() {
        return txtAcceso;
    }

    public void setTxtAcceso(JTextField txtAcceso) {
        this.txtAcceso = txtAcceso;
    }

    public JTextField getTxtAsociado() {
        return txtAsociado;
    }

    public void setTxtAsociado(JTextField txtAsociado) {
        this.txtAsociado = txtAsociado;
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

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
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
