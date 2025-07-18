package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * Vista para añadir nuevos productos al sistema.
 * Permite ingresar información básica de un producto como código, nombre y precio.
 */
public class ProductoAnadirView extends JInternalFrame {

    // Componentes de la interfaz
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

    // Manejador de internacionalización
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con un manejador de internacionalización.
     *
     * @param mensaje Manejador de internacionalización para cargar los textos
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        configurarListeners();
        actualizarTextos();
    }

    /**
     * Inicializa los componentes gráficos de la vista.
     * Configura el tamaño, comportamiento y carga los íconos de los botones.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Carga de íconos para los botones
        URL aceptarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if(aceptarURL != null) {
            ImageIcon iconBtnAceptar = new ImageIcon(aceptarURL);
            btnAceptar.setIcon(iconBtnAceptar);
        } else {
            System.out.println("Error al cargar ícono de aceptar");
        }

        URL limpiarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if(limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(limpiarURL);
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.out.println("Error al cargar ícono de limpiar");
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma configurado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.anadir.titulo"));
        lblTitulo.setText(mensaje.get("producto.anadir.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblNombre.setText(mensaje.get("nombre"));
        lblPrecio.setText(mensaje.get("precio"));

        btnAceptar.setText(mensaje.get("aceptar"));
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
    }

    /**
     * Configura los listeners para los botones de la interfaz.
     */
    private void configurarListeners() {
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    /**
     * Muestra un mensaje al usuario en un diálogo.
     *
     * @param keyMensaje Clave del mensaje a mostrar (según el archivo de internacionalización)
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    /**
     * Limpia todos los campos de entrada de datos.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    // Métodos getters y setters

    /**
     * Obtiene el panel principal de la vista.
     * @return Panel principal
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param panelPrincipal Panel principal a establecer
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el campo de texto para el precio.
     * @return Campo de precio
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio.
     * @param txtPrecio Campo de precio a establecer
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene el campo de texto para el nombre.
     * @return Campo de nombre
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre.
     * @param txtNombre Campo de nombre a establecer
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el código.
     * @return Campo de código
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código.
     * @param txtCodigo Campo de código a establecer
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón de aceptar.
     * @return Botón de aceptar
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón de aceptar.
     * @param btnAceptar Botón de aceptar a establecer
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón de limpiar.
     * @return Botón de limpiar
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón de limpiar.
     * @param btnLimpiar Botón de limpiar a establecer
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
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