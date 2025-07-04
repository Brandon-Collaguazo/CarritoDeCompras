package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

public class UsuarioModificarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JButton btnMostrar;
    private JTable tblDatos;
    private JComboBox cbxModificar;
    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mensaje) {
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
        URL mostrarURL = UsuarioModificarView.class.getClassLoader().getResource("imagenes/mostrar.png");
        if(mostrarURL != null) {
            ImageIcon iconBtnMostrar = new ImageIcon(mostrarURL);
            btnMostrar.setIcon(iconBtnMostrar);
        } else {
            System.out.println("Error");
        }

        configurarTabla();
        configurarComboBox();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.setColumnIdentifiers(new Object[]{
                mensaje.get("columna.nombre.completo"),
                mensaje.get("columna.fecha.nacimiento"),
                mensaje.get("columna.telefono"),
                mensaje.get("columna.correo"),
                mensaje.get("columna.usuario")
        });

        tblDatos.setModel(modelo);
        tblDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        lblContrasenia.setText(mensaje.get("contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.password"));

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

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    public JComboBox getCbxModificar() {
        return cbxModificar;
    }

    public void setCbxModificar(JComboBox cbxModificar) {
        this.cbxModificar = cbxModificar;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this,mensaje.get(keyMensaje));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtConfirmar.setText("");
    }
}
