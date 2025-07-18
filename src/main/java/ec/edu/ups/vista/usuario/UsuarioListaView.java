package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * Vista para listar y buscar usuarios en el sistema.
 * Permite visualizar información de usuarios y realizar búsquedas por nombre de usuario.
 */
public class UsuarioListaView extends JInternalFrame {
    // Componentes de la interfaz
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JTable tblDetalle;

    // Manejador de internacionalización
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con un manejador de internacionalización.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos
     */
    public UsuarioListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, comportamiento y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        // Carga de íconos para los botones
        URL buscarURL = UsuarioListaView.class.getClassLoader().getResource("imagenes/buscarUsuario.png");
        if(buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        }

        URL limpiarURL = UsuarioListaView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if(limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(limpiarURL);
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.out.println("Error al cargar ícono de limpiar");
        }

        configurarTabla();
    }

    /**
     * Configura la tabla de usuarios con las columnas necesarias.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.usuario"),
                mensaje.get("columna.asociado"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblDetalle.setModel(modelo);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.lista.titulo"));

        lblTitulo.setText(mensaje.get("usuario.lista.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnLimpiar.setText(mensaje.get("limpiar"));
    }

    /**
     * Cambia el idioma de la interfaz.
     *
     * @param lenguaje Código del lenguaje (ej. "es", "en")
     * @param pais Código del país (ej. "EC", "US")
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Muestra un mensaje al usuario en un diálogo.
     *
     * @param keyMensaje Clave del mensaje a mostrar (según el archivo de internacionalización)
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    // Métodos getters y setters

    /**
     * Obtiene el panel principal de la vista.
     * @return Panel principal
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal Panel principal a establecer
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista.
     * @return Panel superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     * @param pnlSuperior Panel superior a establecer
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior de la vista.
     * @return Panel inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior de la vista.
     * @param pnlInferior Panel inferior a establecer
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    /**
     * Obtiene el campo de texto para buscar usuario.
     * @return Campo de búsqueda
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto para buscar usuario.
     * @param txtUsuario Campo de búsqueda a establecer
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el botón para buscar usuarios.
     * @return Botón de búsqueda
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar usuarios.
     * @param btnBuscar Botón de búsqueda a establecer
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para limpiar campos.
     * @return Botón de limpiar
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón para limpiar campos.
     * @param btnLimpiar Botón de limpiar a establecer
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene la etiqueta de título.
     * @return Etiqueta de título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta de título.
     * @param lblTitulo Etiqueta de título a establecer
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta de usuario.
     * @return Etiqueta de usuario
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta de usuario.
     * @param lblUsuario Etiqueta de usuario a establecer
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene la tabla de detalles de usuarios.
     * @return Tabla de usuarios
     */
    public JTable getTblDetalle() {
        return tblDetalle;
    }

    /**
     * Establece la tabla de detalles de usuarios.
     * @param tblDetalle Tabla de usuarios a establecer
     */
    public void setTblDetalle(JTable tblDetalle) {
        this.tblDetalle = tblDetalle;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return Manejador de mensajes internacionalizados
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje Manejador de mensajes a establecer
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }
}