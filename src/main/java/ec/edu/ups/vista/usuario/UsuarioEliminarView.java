package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable tblUsuario;
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

        //Íconos para los botones
        URL buscarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/buscarUsario.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error");
        }

        URL eliminarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/eliminar_usuario.png");
        if(eliminarURL != null) {
            ImageIcon iconBtnEliminar = new ImageIcon(eliminarURL);
            btnEliminar.setIcon(iconBtnEliminar);
        } else {
            System.out.println("Error");
        }

        URL cancelarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/cancelar.png");
        if(cancelarURL != null) {
            ImageIcon iconBtnCancelar = new ImageIcon(cancelarURL);
            btnCancelar.setIcon(iconBtnCancelar);
        } else {
            System.out.println("Error");
        }

        configurarTabla();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] columnas = {
                mensaje.get("columna.nombre.completo"),
                mensaje.get("columna.fecha.nacimiento"),
                mensaje.get("columna.telefono"),
                mensaje.get("columna.correo"),
                mensaje.get("columna.usuario"),
                mensaje.get("columna.asociado")
        };

        modelo.setColumnIdentifiers(columnas);
        tblUsuario.setModel(modelo);

        tblUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.eliminar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.eliminar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
        btnCancelar.setText(mensaje.get("cancelar"));
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

    public JTable getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
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
