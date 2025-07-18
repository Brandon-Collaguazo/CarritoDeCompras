package ec.edu.ups.vista.usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * La clase `UsuarioEliminarView` representa la vista para eliminar un usuario existente.
 * Extiende de `JInternalFrame` para ser utilizada dentro de una aplicación de escritorio.
 * Permite buscar un usuario por su nombre de usuario, mostrar sus datos en una tabla,
 * y proceder con la eliminación. Soporta la internacionalización de textos a través
 * de `MensajeInternacionalizacionHandler`.
 */
public class UsuarioEliminarView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblAsociado; // Etiqueta no utilizada directamente en actualizarTextos, revisar si se necesita
    private JLabel lblUsuario;
    private JTextField txtUsuario; // Campo para buscar el usuario por username
    private JTextField txtRegistro; // Campo para la fecha de registro del usuario (posiblemente de solo lectura)
    private JTextField txtAcceso;   // Campo para la fecha de último acceso (posiblemente de solo lectura)
    private JTextField txtAsociado; // Campo para el usuario asociado (posiblemente de solo lectura)
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JTable tblUsuario;
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la clase `UsuarioEliminarView`.
     * Inicializa la vista y sus componentes, y actualiza los textos
     * según el idioma configurado.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el panel principal, las propiedades del frame (cerrable, redimensionable, tamaño),
     * y carga los íconos de los botones. También llama a `configurarTabla()`.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(600, 500);

        // Íconos para los botones
        URL buscarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/buscarUsuario.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(buscarURL);
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Buscar en UsuarioEliminarView.");
        }

        URL eliminarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/eliminar_usuario.png");
        if (eliminarURL != null) {
            ImageIcon iconBtnEliminar = new ImageIcon(eliminarURL);
            btnEliminar.setIcon(iconBtnEliminar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Eliminar en UsuarioEliminarView.");
        }

        URL cancelarURL = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/cancelar.png");
        if (cancelarURL != null) {
            ImageIcon iconBtnCancelar = new ImageIcon(cancelarURL);
            btnCancelar.setIcon(iconBtnCancelar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Cancelar en UsuarioEliminarView.");
        }

        configurarTabla();
    }

    /**
     * Configura el modelo de la tabla de usuarios (`tblUsuario`).
     * Establece las columnas de la tabla utilizando textos internacionalizados
     * y asegura que las celdas no sean editables. También configura la tabla
     * para permitir solo una selección de fila a la vez.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas de la tabla no son editables
            }
        };
        Object[] columnas = {
                mensaje.get("columna.nombre.completo"),
                mensaje.get("columna.fecha.nacimiento"),
                mensaje.get("columna.telefono"),
                mensaje.get("columna.correo"),
                mensaje.get("columna.usuario"),
                mensaje.get("columna.asociado") // Posiblemente se refiera a un rol o a otro usuario
        };

        modelo.setColumnIdentifiers(columnas);
        tblUsuario.setModel(modelo);

        tblUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solo una fila
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.eliminar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.eliminar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        // lblAsociado no se actualiza aquí, revisar si es necesario o si es un remanente.

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }

    /**
     * Cambia el idioma de la interfaz gráfica actualizando la configuración del
     * `MensajeInternacionalizacionHandler` y luego volviendo a cargar los textos y la configuración de la tabla.
     *
     * @param lenguaje El código de lenguaje (ej. "es", "en").
     * @param pais     El código de país (ej. "EC", "US").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla(); // Reconfigura la tabla para actualizar los nombres de las columnas
    }

    /**
     * Muestra un mensaje de advertencia o información en un `JOptionPane`.
     * El mensaje se obtiene a través del objeto `MensajeInternacionalizacionHandler` usando la clave proporcionada.
     *
     * @param keyMensaje La clave del mensaje a mostrar, definida en los recursos de internacionalización.
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    /**
     * Limpia los campos de texto de la vista.
     * Los campos de registro, acceso y asociado se establecen como vacíos.
     * También limpia el campo de búsqueda de usuario y vacía la tabla.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtRegistro.setText("");
        txtAcceso.setText("");
        txtAsociado.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tblUsuario.getModel();
        modelo.setRowCount(0); // Limpiar todas las filas de la tabla
    }

    // --- Getters y Setters para los componentes de la interfaz ---

    /**
     * Obtiene el panel principal de la vista.
     *
     * @return El `JPanel` principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     *
     * @param pnlPrincipal El `JPanel` a establecer como principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el campo de texto para el nombre de usuario a buscar/eliminar.
     *
     * @return El `JTextField` del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     *
     * @param txtUsuario El `JTextField` del usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el campo de texto para la fecha de registro del usuario.
     *
     * @return El `JTextField` de registro.
     */
    public JTextField getTxtRegistro() {
        return txtRegistro;
    }

    /**
     * Establece el campo de texto para la fecha de registro del usuario.
     *
     * @param txtRegistro El `JTextField` de registro.
     */
    public void setTxtRegistro(JTextField txtRegistro) {
        this.txtRegistro = txtRegistro;
    }

    /**
     * Obtiene el campo de texto para la fecha de último acceso del usuario.
     *
     * @return El `JTextField` de acceso.
     */
    public JTextField getTxtAcceso() {
        return txtAcceso;
    }

    /**
     * Establece el campo de texto para la fecha de último acceso del usuario.
     *
     * @param txtAcceso El `JTextField` de acceso.
     */
    public void setTxtAcceso(JTextField txtAcceso) {
        this.txtAcceso = txtAcceso;
    }

    /**
     * Obtiene el campo de texto para el usuario asociado (si aplica).
     *
     * @return El `JTextField` asociado.
     */
    public JTextField getTxtAsociado() {
        return txtAsociado;
    }

    /**
     * Establece el campo de texto para el usuario asociado.
     *
     * @param txtAsociado El `JTextField` asociado.
     */
    public void setTxtAsociado(JTextField txtAsociado) {
        this.txtAsociado = txtAsociado;
    }

    /**
     * Obtiene el botón para buscar un usuario.
     *
     * @return El `JButton` de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar un usuario.
     *
     * @param btnBuscar El `JButton` de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para eliminar el usuario.
     *
     * @return El `JButton` de eliminación.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar el usuario.
     *
     * @param btnEliminar El `JButton` de eliminación.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene el botón para cancelar la operación.
     *
     * @return El `JButton` de cancelar.
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Establece el botón para cancelar la operación.
     *
     * @param btnCancelar El `JButton` de cancelar.
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * Obtiene la tabla donde se muestran los datos del usuario.
     *
     * @return El `JTable` de usuario.
     */
    public JTable getTblUsuario() {
        return tblUsuario;
    }

    /**
     * Establece la tabla donde se muestran los datos del usuario.
     *
     * @param tblUsuario El `JTable` de usuario.
     */
    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
    }

    /**
     * Obtiene el manejador de internacionalización de mensajes.
     *
     * @return El `MensajeInternacionalizacionHandler` utilizado en la vista.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización de mensajes.
     *
     * @param mensaje El `MensajeInternacionalizacionHandler` a establecer.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene la etiqueta del título de la vista.
     *
     * @return El `JLabel` del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título de la vista.
     *
     * @param lblTitulo El `JLabel` del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta para el nombre de usuario.
     *
     * @return El `JLabel` de usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta para el nombre de usuario.
     *
     * @param lblUsuario El `JLabel` de usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene la etiqueta para el usuario asociado.
     *
     * @return El `JLabel` asociado.
     */
    public JLabel getLblAsociado() {
        return lblAsociado;
    }

    /**
     * Establece la etiqueta para el usuario asociado.
     *
     * @param lblAsociado El `JLabel` asociado.
     */
    public void setLblAsociado(JLabel lblAsociado) {
        this.lblAsociado = lblAsociado;
    }
}