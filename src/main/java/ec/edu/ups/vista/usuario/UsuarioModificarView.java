package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * La clase `UsuarioModificarView` representa la vista para que un usuario modifique
 * su propia información.
 * Extiende de `JInternalFrame` para ser utilizada dentro de una aplicación de escritorio.
 * Permite al usuario ver sus datos en una tabla y modificar su contraseña.
 * Soporta la internacionalización de textos a través de `MensajeInternacionalizacionHandler`.
 *
 * @author [Tu Nombre/El nombre del equipo]
 * @version 1.0
 * @since 2025-07-18
 */
public class UsuarioModificarView extends JInternalFrame {

    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JTextField txtUsuario; // Campo para el nombre de usuario (posiblemente de solo lectura o para mostrar el actual)
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JButton btnMostrar; // Botón para mostrar/ocultar contraseña
    private JTable tblDatos;
    private JComboBox cbxModificar; // ComboBox para seleccionar qué modificar (nombre o contraseña, aunque solo se permite contraseña en esta vista)
    private JButton btnGuardar;
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la clase `UsuarioModificarView`.
     * Inicializa la vista y sus componentes, y actualiza los textos
     * según el idioma configurado.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public UsuarioModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponent();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el panel principal, las propiedades del frame (cerrable, redimensionable, tamaño),
     * y carga los íconos de los botones. También llama a `configurarTabla()` y `configurarComboBox()`.
     */
    private void initComponent() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        // Íconos para los botones
        URL mostrarURL = UsuarioModificarView.class.getClassLoader().getResource("imagenes/mostrar.png");
        if (mostrarURL != null) {
            ImageIcon iconBtnMostrar = new ImageIcon(mostrarURL);
            btnMostrar.setIcon(iconBtnMostrar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Mostrar en UsuarioModificarView.");
        }

        URL guardarURL = UsuarioModificarView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(guardarURL);
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.err.println("Error: No se pudo cargar la imagen para el botón Guardar.");
        }

        configurarTabla();
        configurarComboBox();
    }

    /**
     * Configura el modelo de la tabla de datos (`tblDatos`).
     * Establece las columnas de la tabla utilizando textos internacionalizados.
     */
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

    /**
     * Configura el `JComboBox` de opciones de modificación (`cbxModificar`).
     * Añade opciones para modificar el nombre o la contraseña, utilizando textos internacionalizados.
     * En esta vista, el enfoque principal es la contraseña, pero las opciones se mantienen consistentes.
     */
    private void configurarComboBox() {
        cbxModificar.removeAllItems();
        cbxModificar.addItem(mensaje.get("modificar.nombre")); // Aunque el nombre no sea directamente modificable en esta vista
        cbxModificar.addItem(mensaje.get("modificar.contrasenia"));
    }

    /**
     * Actualiza todos los textos de la interfaz gráfica (título del frame, etiquetas, y textos de botones)
     * utilizando los valores obtenidos del objeto `MensajeInternacionalizacionHandler`.
     * También reconfigura el `JComboBox` para asegurar que las opciones estén internacionalizadas.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.modificar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.modificar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar.password"));

        btnGuardar.setText(mensaje.get("guardar"));
        btnMostrar.setText(mensaje.get("mostrar"));
        configurarComboBox(); // Reconfigura el combo box para actualizar los textos de sus ítems
    }

    /**
     * Carga los datos de un objeto `Usuario` en la tabla (`tblDatos`) y en el campo de usuario.
     * Si el usuario es nulo, la tabla se vacía.
     *
     * @param usuario El objeto `Usuario` cuyos datos se cargarán en la vista.
     */
    public void cargarDatosUsuario(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) tblDatos.getModel();
        modelo.setRowCount(0); // Limpiar filas existentes

        if (usuario != null) {
            modelo.addRow(new Object[]{
                    usuario.getNombreCompleto(),
                    usuario.getFechaNacimiento(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    usuario.getUsername()
            });
            txtUsuario.setText(usuario.getUsername()); // Establece el nombre de usuario en el campo
        }
    }

    /**
     * Cambia el idioma de la interfaz gráfica actualizando la configuración del
     * `MensajeInternacionalizacionHandler` y luego volviendo a cargar los textos.
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
     * Limpia los campos de texto del usuario, contraseña y confirmar contraseña.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtConfirmar.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tblDatos.getModel();
        modelo.setRowCount(0); // Limpiar la tabla de datos
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
     * Obtiene el campo de texto para el nombre de usuario.
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
     * Obtiene el campo de contraseña.
     *
     * @return El `JPasswordField` de la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de contraseña.
     *
     * @param txtContrasenia El `JPasswordField` de la contraseña.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el campo para confirmar la contraseña.
     *
     * @return El `JPasswordField` para confirmar la contraseña.
     */
    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    /**
     * Establece el campo para confirmar la contraseña.
     *
     * @param txtConfirmar El `JPasswordField` para confirmar la contraseña.
     */
    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    /**
     * Obtiene el botón para mostrar/ocultar la contraseña.
     *
     * @return El `JButton` de mostrar.
     */
    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    /**
     * Establece el botón para mostrar/ocultar la contraseña.
     *
     * @param btnMostrar El `JButton` de mostrar.
     */
    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    /**
     * Obtiene el botón para guardar los cambios.
     *
     * @return El `JButton` de guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar los cambios.
     *
     * @param btnGuardar El `JButton` de guardar.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene la tabla donde se muestran los datos del usuario.
     *
     * @return El `JTable` de datos.
     */
    public JTable getTblDatos() {
        return tblDatos;
    }

    /**
     * Establece la tabla donde se muestran los datos del usuario.
     *
     * @param tblDatos El `JTable` de datos.
     */
    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    /**
     * Obtiene el combo box de opciones de modificación.
     *
     * @return El `JComboBox` de opciones de modificación.
     */
    public JComboBox getCbxModificar() {
        return cbxModificar;
    }

    /**
     * Establece el combo box de opciones de modificación.
     *
     * @param cbxModificar El `JComboBox` de opciones de modificación.
     */
    public void setCbxModificar(JComboBox cbxModificar) {
        this.cbxModificar = cbxModificar;
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
     * Obtiene la etiqueta para la contraseña.
     *
     * @return El `JLabel` de la contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    /**
     * Establece la etiqueta para la contraseña.
     *
     * @param lblContrasenia El `JLabel` de la contraseña.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Obtiene la etiqueta para confirmar la contraseña.
     *
     * @return El `JLabel` de confirmación de contraseña.
     */
    public JLabel getLblConfirmar() {
        return lblConfirmar;
    }

    /**
     * Establece la etiqueta para confirmar la contraseña.
     *
     * @param lblConfirmar El `JLabel` de confirmación de contraseña.
     */
    public void setLblConfirmar(JLabel lblConfirmar) {
        this.lblConfirmar = lblConfirmar;
    }
}