package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

public class AdminModificarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTable tblDatos;
    private JTextField txtUsuario1;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblUsuario1;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JComboBox cbxModificar;
    private JButton btnMostrar;
    private JButton btnGuardar;
    private MensajeInternacionalizacionHandler mensaje;

    public AdminModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponent();
        actualizarTextos();
    }

    private void initComponent() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        //Íconos para los botones
        URL buscarURL = AdminModificarView.class.getClassLoader().getResource("imagenes/buscarUsuario.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.out.println("Error, no se mostró el buscar en admin modificar");
        }

        URL mostrarURL = AdminModificarView.class.getClassLoader().getResource("imagenes/mostrar.png");
        if(mostrarURL != null) {
            ImageIcon iconBtnMostrar = new ImageIcon(mostrarURL);
            btnMostrar.setIcon(iconBtnMostrar);
        } else {
            System.out.println("Error");
        }

        URL guardarURL = AdminModificarView.class.getClassLoader().getResource("imagenes/guardar.png");
        if(guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(guardarURL);
            btnMostrar.setIcon(iconBtnGuardar);
        } else {
            System.out.println("Error");
        }

        configurarTabla();
        configurarComboBox();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.nombre.completo"),
                mensaje.get("columna.fecha.nacimiento"),
                mensaje.get("columna.telefono"),
                mensaje.get("columna.correo"),
                mensaje.get("columna.usuario")
        };
        modelo.setColumnIdentifiers(columnas);
        tblDatos.setModel(modelo);
    }

    private void configurarComboBox() {
        cbxModificar.removeAllItems();
        cbxModificar.addItem(mensaje.get("modificar.nombre"));
        cbxModificar.addItem(mensaje.get("modificar.contrasenia"));
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.modificar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.modificar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblUsuario1.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.password"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnGuardar.setText(mensaje.get("guardar"));
        btnMostrar.setText(mensaje.get("mostrar"));
        configurarComboBox();
    }

    public void cargarDatosUsuario(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) tblDatos.getModel();
        modelo.setRowCount(0);

        if(usuario != null) {
            modelo.addRow(new Object[]{
                    usuario.getNombreCompleto(),
                    usuario.getFechaNacimiento(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    usuario.getUsername()
            });
            txtUsuario.setText(usuario.getUsername());
        }
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

    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    public JTextField getTxtUsuario1() {
        return txtUsuario1;
    }

    public void setTxtUsuario1(JTextField txtUsuario1) {
        this.txtUsuario1 = txtUsuario1;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
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

    public JLabel getLblUsuario1() {
        return lblUsuario1;
    }

    public void setLblUsuario1(JLabel lblUsuario1) {
        this.lblUsuario1 = lblUsuario1;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JLabel getLblConfirmar() {
        return lblConfirmar;
    }

    public void setLblConfirmar(JLabel lblConfirmar) {
        this.lblConfirmar = lblConfirmar;
    }

    public JComboBox getCbxModificar() {
        return cbxModificar;
    }

    public void setCbxModificar(JComboBox cbxModificar) {
        this.cbxModificar = cbxModificar;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
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

    public void limpiar() {
        txtUsuario.setText("");
    }
}
