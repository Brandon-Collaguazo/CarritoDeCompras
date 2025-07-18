package ec.edu.ups.vista;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * La clase `MenuPrincipalView` representa la ventana principal de la aplicación.
 * Extiende de `JFrame` y contiene un menú bar con opciones para gestionar productos,
 * carritos y usuarios, así como opciones de idioma y salida.
 * La vista está diseñada para ser internacionalizada utilizando `MensajeInternacionalizacionHandler`.
 */
public class MenuPrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensaje;

    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuario;
    private JMenu menuIdioma;
    private JMenu menuSalir;

    private JMenuItem menuItemCrear;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem menuItemBuscar;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemBuscarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemActualizarCarrito;
    private JMenuItem menuItemListarCarrito;

    private JMenuItem menuItemListarUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemModificarUsuario;

    private JMenuItem menuItemEspaniol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    private JMenuItem menuItemSalir;
    private JMenuItem menuItemCerrarSesion;

    private JDesktopPane jDesktopPane; // Declaración inicial
    private MiJDesktopPane miJDesktopPane; // Uso de una clase personalizada si extiende JDesktopPane

    /**
     * Constructor de la clase `MenuPrincipalView`.
     * Inicializa la vista y sus componentes, y agrega los listeners para las acciones de los menús.
     *
     * @param mensaje Un objeto `MensajeInternacionalizacionHandler` para manejar la internacionalización de los textos.
     */
    public MenuPrincipalView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        agregarListeners();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     * Configura el `JDesktopPane`, la barra de menú (`JMenuBar`), los menús principales (`JMenu`)
     * y los ítems de menú (`JMenuItem`). Asigna textos internacionalizados y carga los íconos
     * para cada ítem de menú. Finalmente, establece las propiedades del `JFrame`.
     */
    public void initComponents() {
        // Inicialización de componentes principales
        jDesktopPane = new JDesktopPane(); // Se mantiene esta línea si `MiJDesktopPane` no lo reemplaza completamente
        miJDesktopPane = new MiJDesktopPane(); // Asume que MiJDesktopPane es una subclase de JDesktopPane

        menuBar = new JMenuBar();

        // Inicialización de menús principales con textos internacionalizados
        menuProducto = new JMenu(mensaje.get("menu.producto"));
        menuCarrito = new JMenu(mensaje.get("menu.carrito"));
        menuUsuario = new JMenu(mensaje.get("menu.usuario"));
        menuIdioma = new JMenu(mensaje.get("menu.idioma"));
        menuSalir = new JMenu(mensaje.get("menu.salir"));

        // Inicialización de ítems de menú Producto
        menuItemCrear = new JMenuItem(mensaje.get("menu.producto.crear"));
        menuItemBuscar = new JMenuItem(mensaje.get("menu.producto.buscar"));
        menuItemEliminar = new JMenuItem(mensaje.get("menu.producto.eliminar"));
        menuItemActualizar = new JMenuItem(mensaje.get("menu.producto.actualizar"));

        // Inicialización de ítems de menú Carrito
        menuItemCrearCarrito = new JMenuItem(mensaje.get("menu.carrito.crear"));
        menuItemBuscarCarrito = new JMenuItem(mensaje.get("menu.carrito.buscar"));
        menuItemEliminarCarrito = new JMenuItem(mensaje.get("menu.carrito.eliminar"));
        menuItemActualizarCarrito = new JMenuItem(mensaje.get("menu.carrito.actualizar"));
        menuItemListarCarrito = new JMenuItem(mensaje.get("menu.carrito.listar"));

        // Inicialización de ítems de menú Usuario
        menuItemListarUsuario = new JMenuItem(mensaje.get("menu.usuario.listar"));
        menuItemEliminarUsuario = new JMenuItem(mensaje.get("menu.usuario.eliminar"));
        menuItemModificarUsuario = new JMenuItem(mensaje.get("menu.usuario.modificar"));

        // Inicialización de ítems de menú Idioma
        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        // Inicialización de ítems de menú Salir
        menuItemSalir = new JMenuItem(mensaje.get("menu.salir.salir"));
        menuItemCerrarSesion = new JMenuItem(mensaje.get("menu.salir.cerrar"));

        // Añadir menús a la barra de menú
        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        // Añadir ítems a los menús
        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemBuscar);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemActualizarCarrito);
        menuCarrito.add(menuItemListarCarrito);

        menuUsuario.add(menuItemListarUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemModificarUsuario);

        menuIdioma.add(menuItemEspaniol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        // Configuración del JFrame
        setJMenuBar(menuBar);
        setContentPane(miJDesktopPane); // Establece el JDesktopPane personalizado como contenido
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no la aplicación
        setTitle(mensaje.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana al inicio
        setVisible(true);

        // --- Carga de íconos para los ítems de menú ---
        // MENÚ DE PRODUCTOS
        cargarIcono(menuItemCrear, "imagenes/crear_producto.png", "No se cargó el anadir_producto");
        cargarIcono(menuItemBuscar, "imagenes/buscar_producto.png", "No se cargó el buscar producto");
        cargarIcono(menuItemEliminar, "imagenes/eliminar_producto.png", "No se cargó eliminarProducto");
        cargarIcono(menuItemActualizar, "imagenes/modificar.png", "No se cargó el modificar en menuprincipal");

        // MENÚ CARRITO
        cargarIcono(menuItemCrearCarrito, "imagenes/crear_carrito.png", "No se cargó el crear carrito");
        cargarIcono(menuItemBuscarCarrito, "imagenes/buscar_carrito.png", "No se cargó el buscar carrito");
        cargarIcono(menuItemEliminarCarrito, "imagenes/eliminar_carrito.png", "No se cargó el eliminar carrito");
        cargarIcono(menuItemActualizarCarrito, "imagenes/modificar.png", "No se cargó el actualizar carrito");
        cargarIcono(menuItemListarCarrito, "imagenes/listar_carrito.png", "No se cargó el listar carrito");

        // MENÚ USUARIO
        cargarIcono(menuItemListarUsuario, "imagenes/listar_usuario.png", "No se cargó el listar usuario");
        cargarIcono(menuItemEliminarUsuario, "imagenes/eliminar_usuario.png", "No se cargó el eliminar usuario en menu principal");
        cargarIcono(menuItemModificarUsuario, "imagenes/modificar.png", "No se cargó el modificar usuario");

        // MENÚ SALIR
        cargarIcono(menuItemSalir, "imagenes/salir_sistema.png", "No se cargó el salir del sistema");
        cargarIcono(menuItemCerrarSesion, "imagenes/cerrar_sesion.png", "No se cargó el cerrar sesión");

        // MENÚ IDIOMAS
        cargarIcono(menuItemEspaniol, "imagenes/espana.png", "Error, no se cargó la bandera de españa");
        cargarIcono(menuItemIngles, "imagenes/reino-unido.png", "Error, no se cargó la bandera inglesa");
        cargarIcono(menuItemFrances, "imagenes/francia.png", "Error, no se cargó la bandera de francia");
    }

    /**
     * Helper method to load icons and set them on JMenuItems.
     *
     * @param menuItem The JMenuItem to set the icon on.
     * @param imagePath The path to the image resource.
     * @param errorMessage The error message to print if the image fails to load.
     */
    private void cargarIcono(JMenuItem menuItem, String imagePath, String errorMessage) {
        URL imageUrl = MenuPrincipalView.class.getClassLoader().getResource(imagePath);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            menuItem.setIcon(icon);
        } else {
            System.err.println(errorMessage);
        }
    }

    /**
     * Agrega los `ActionListener` a los ítems de menú que requieren manejo de eventos.
     * Actualmente, maneja las acciones de "Cerrar Sesión" y "Salir del Sistema".
     */
    private void agregarListeners() {
        // Listener para Cerrar Sesión
        menuItemCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        MenuPrincipalView.this,
                        mensaje.get("mensaje.confirmar.cerrar.sesion"),
                        mensaje.get("titulo.confirmacion"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    dispose(); // Cierra la ventana actual
                    JOptionPane.showMessageDialog(
                            MenuPrincipalView.this,
                            mensaje.get("mensaje.sesion.cerrada"),
                            mensaje.get("titulo.informacion"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // Aquí se podría añadir lógica para regresar a la ventana de inicio de sesión
                    // o realizar cualquier otra acción post-cierre de sesión.
                }
            }
        });

        // Listener para Salir del Sistema
        menuItemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        MenuPrincipalView.this,
                        mensaje.get("mensaje.confirmar.salir.sistema"),
                        mensaje.get("titulo.confirmacion"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0); // Termina la aplicación
                }
            }
        });
    }

    /**
     * Muestra un mensaje en un `JOptionPane`.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Deshabilita o hace invisible los menús y ítems de menú que corresponden
     * a funcionalidades de administrador (Producto, Listar Usuario, Eliminar Usuario).
     * Esto se usaría, por ejemplo, cuando un usuario con rol normal inicia sesión.
     */
    public void deshabilitarMenusAdministrador() {
        menuProducto.setVisible(false);
        menuUsuario.remove(menuItemListarUsuario); // Remover el ítem directamente
        menuUsuario.remove(menuItemEliminarUsuario); // Remover el ítem directamente
        // Alternativamente, puedes simplemente deshabilitarlos:
        // menuItemListarUsuario.setEnabled(false);
        // menuItemEliminarUsuario.setEnabled(false);
    }

    /**
     * Habilita los menús y ítems de menú que corresponden a funcionalidades de administrador.
     * Esto se usaría cuando un administrador inicia sesión o para revertir la deshabilitación.
     */
    public void habilitarMenusAdministrador() {
        menuProducto.setVisible(true);
        // Asegúrate de añadirlos de nuevo si los removiste previamente
        if (!menuUsuario.isAncestorOf(menuItemListarUsuario)) {
            menuUsuario.add(menuItemListarUsuario, 0); // Añadir en una posición específica si es necesario
        }
        if (!menuUsuario.isAncestorOf(menuItemEliminarUsuario)) {
            menuUsuario.add(menuItemEliminarUsuario, 1); // Añadir en una posición específica
        }
        // O si solo los deshabilitaste:
        // menuItemListarUsuario.setEnabled(true);
        // menuItemEliminarUsuario.setEnabled(true);
    }


    /**
     * Cambia el idioma de la interfaz gráfica actualizando la configuración del
     * `MensajeInternacionalizacionHandler` y luego volviendo a cargar los textos
     * de todos los componentes de menú.
     *
     * @param lenguaje El código de lenguaje (ej. "es", "en", "fr").
     * @param pais     El código de país (ej. "EC", "US", "FR").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        // Actualizar el manejador de internacionalización
        mensaje.setLenguaje(lenguaje, pais);

        // Actualizar título de la ventana
        setTitle(mensaje.get("app.titulo"));

        // Actualizar textos de los menús principales
        menuProducto.setText(mensaje.get("menu.producto"));
        menuCarrito.setText(mensaje.get("menu.carrito"));
        menuUsuario.setText(mensaje.get("menu.usuario"));
        menuIdioma.setText(mensaje.get("menu.idioma"));
        menuSalir.setText(mensaje.get("menu.salir"));

        // Actualizar opciones de menú Producto
        menuItemCrear.setText(mensaje.get("menu.producto.crear"));
        menuItemEliminar.setText(mensaje.get("menu.producto.eliminar"));
        menuItemActualizar.setText(mensaje.get("menu.producto.actualizar"));
        menuItemBuscar.setText(mensaje.get("menu.producto.buscar"));

        // Actualizar opciones de menú Carrito
        menuItemCrearCarrito.setText(mensaje.get("menu.carrito.crear"));
        menuItemBuscarCarrito.setText(mensaje.get("menu.carrito.buscar"));
        menuItemEliminarCarrito.setText(mensaje.get("menu.carrito.eliminar"));
        menuItemActualizarCarrito.setText(mensaje.get("menu.carrito.actualizar"));
        menuItemListarCarrito.setText(mensaje.get("menu.carrito.listar"));

        // Actualizar opciones de menú Usuario
        menuItemListarUsuario.setText(mensaje.get("menu.usuario.listar"));
        menuItemEliminarUsuario.setText(mensaje.get("menu.usuario.eliminar"));
        menuItemModificarUsuario.setText(mensaje.get("menu.usuario.modificar"));

        // Actualizar opciones de idioma
        menuItemEspaniol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        // Asegurarse de que menuItemFrances no sea nulo antes de intentar acceder a él
        if (menuItemFrances != null) {
            menuItemFrances.setText(mensaje.get("menu.idioma.fr"));
        }

        // Actualizar opciones de Salir
        menuItemSalir.setText(mensaje.get("menu.salir.salir"));
        menuItemCerrarSesion.setText(mensaje.get("menu.salir.cerrar"));

        // Si MiJDesktopPane también tiene textos que necesitan actualización, se llamarían aquí
        // miJDesktopPane.actualizarTextos();
    }

    // --- Getters para los ítems de menú ---
    public JMenuItem getMenuItemCrear() {
        return menuItemCrear;
    }

    public JMenuItem getMenuItemEliminar() {
        return menuItemEliminar;
    }

    public JMenuItem getMenuItemActualizar() {
        return menuItemActualizar;
    }

    public JMenuItem getMenuItemBuscar() {
        return menuItemBuscar;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JMenuItem getMenuItemBuscarCarrito() {
        return menuItemBuscarCarrito;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemActualizarCarrito() {
        return menuItemActualizarCarrito;
    }

    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    public JMenuItem getMenuItemListarUsuario() {
        return menuItemListarUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemModificarUsuario() {
        return menuItemModificarUsuario;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public MiJDesktopPane getMiJDesktopPane() {
        return miJDesktopPane;
    }

    public void setMiJDesktopPane(MiJDesktopPane miJDesktopPane) {
        this.miJDesktopPane = miJDesktopPane;
    }

    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    public JMenu getMenuSalir() {
        return menuSalir;
    }

    public JMenuItem getMenuItemEspaniol() {
        return menuItemEspaniol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }
}