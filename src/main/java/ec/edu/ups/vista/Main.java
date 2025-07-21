package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.RecuperarContraseniaController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.ManagerDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO; // Se mantiene, aunque el IDE lo marque como no usado si no hay getters/setters explícitos.
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.autenticacion.LoginView;
import ec.edu.ups.vista.autenticacion.RecuperarContraseniaView;
import ec.edu.ups.vista.autenticacion.UsuarioRegistroView;
import ec.edu.ups.vista.carrito.*;
import ec.edu.ups.vista.producto.*;
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.MessageFormat;

/**
 * <p>La clase {@code Main} es el punto de entrada principal de la aplicación.</p>
 * <p>Se encarga de inicializar todos los componentes necesarios de la interfaz de usuario,
 * los controladores y los objetos de acceso a datos (DAOs). También gestiona el flujo
 * inicial de la aplicación, como la visualización de la ventana de login y la
 * posterior transición al menú principal una vez que el usuario se ha autenticado.</p>
 */
public class Main {
    /**
     * <p>Método principal que inicia la aplicación.</p>
     * <p>Realiza la inicialización de:</p>
     * <ul>
     * <li>El manejador de internacionalización.</li>
     * <li>La ruta por defecto para el almacenamiento de datos.</li>
     * <li>El {@link ManagerDAO} y sus implementaciones de DAOs.</li>
     * <li>Todas las vistas de la aplicación (login, registro, recuperación, gestión de usuarios, productos, carritos).</li>
     * <li>Los controladores que orquestan la interacción entre vistas y modelos/DAOs.</li>
     * <li>La configuración de eventos para la interacción entre componentes, incluyendo el manejo del cierre de ventanas.</li>
     * </ul>
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        // Asegura que la interfaz de usuario se ejecute en el Event Dispatch Thread (EDT)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // 1. Inicializar el manejador de mensajes para la internacionalización.
                // Se configura inicialmente en español de Ecuador.
                MensajeInternacionalizacionHandler mensaje = new MensajeInternacionalizacionHandler("es", "EC");

                // 2. Configurar la ruta base para el almacenamiento de archivos.
                // Crea la carpeta 'data' si no existe.
                String rutaDefault = "./data/";
                new File(rutaDefault).mkdirs();

                // 3. Crear el ManagerDAO y las implementaciones específicas de los DAOs
                // (UsuarioDAO, CarritoDAO, ProductoDAO, PreguntaSeguridadDAO)
                // Se inicializan con el tipo de almacenamiento predeterminado (Memoria)
                ManagerDAO managerDAO = new ManagerDAO(mensaje);
                managerDAO.inicializarDAOS(mensaje.get("almacenamiento.memoria.login"), rutaDefault);

                // 4. Crear la vista de login y configurarla.
                // Es la primera ventana que se muestra al usuario.
                LoginView loginView = new LoginView(mensaje);
                loginView.setMensaje(mensaje); // Asegura que el LoginView tenga el manejador de mensajes
                loginView.setRutaArchivo(rutaDefault); // Establece la ruta por defecto en el campo de la vista
                loginView.setVisible(true); // Hace visible la ventana de login

                // 5. Crear instancias de las vistas secundarias.
                // Estas vistas se gestionarán a través de los controladores.
                UsuarioRegistroView usuarioRegistroView = new UsuarioRegistroView();
                RecuperarContraseniaView recuperarContraseniaView = new RecuperarContraseniaView();
                UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensaje);
                UsuarioListaView usuarioListaView = new UsuarioListaView(mensaje);
                UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensaje);
                AdminModificarView adminModificarView = new AdminModificarView(mensaje);

                // 6. Configurar los controladores principales.
                // El UsuarioController maneja la lógica de autenticación y gestión de usuarios.
                UsuarioController usuarioController = new UsuarioController(
                        managerDAO.getUsuarioDAO(),
                        managerDAO.getCarritoDAO(), // Se mantiene por la estructura original del constructor
                        managerDAO.getProductoDAO(), // Se mantiene por la estructura original del constructor
                        loginView,
                        managerDAO.getPreguntaDAO(),
                        usuarioRegistroView,
                        recuperarContraseniaView,
                        usuarioEliminarView,
                        usuarioListaView,
                        usuarioModificarView,
                        adminModificarView,
                        mensaje,
                        managerDAO // Se pasa el ManagerDAO completo
                );

                // El RecuperarContraseniaController maneja el flujo de recuperación de contraseña.
                RecuperarContraseniaController recuperarController = new RecuperarContraseniaController(
                        recuperarContraseniaView,
                        managerDAO.getUsuarioDAO(),
                        managerDAO.getPreguntaDAO(),
                        mensaje
                );

                // 7. Configurar eventos iniciales de los controladores.
                recuperarController.configurarEventos();

                // 8. Configurar el listener para el cierre de la ventana de login.
                // Cuando la ventana de login se cierra (usualmente después de un login exitoso),
                // se verifica si un usuario ha sido autenticado y se procede al menú principal.
                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Obtiene el usuario autenticado desde el controlador de usuario.
                        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                        // Si hay un usuario autenticado, se procede a la vista principal.
                        if(usuarioAutenticado != null) {
                            // Configurar la vista principal (MenuPrincipalView)
                            MenuPrincipalView principalView = new MenuPrincipalView(mensaje);

                            // Crear instancias de las vistas de productos.
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
                            ProductoListaView productoListaView = new ProductoListaView(mensaje);
                            ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);

                            // Crear instancias de las vistas de carritos.
                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
                            CarritoBuscarView carritoBuscarView = new CarritoBuscarView(mensaje);
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
                            CarritoActualizarView carritoActualizarView = new CarritoActualizarView(mensaje);
                            CarritoListaView carritoListaView = new CarritoListaView(mensaje);
                            CarritoDetalleView carritoDetalleView = new CarritoDetalleView(mensaje);

                            // Configurar los controladores de Producto y Carrito.
                            ProductoController productoController = new ProductoController(
                                    managerDAO.getProductoDAO(),
                                    productoAnadirView,
                                    productoListaView,
                                    productoEliminarView,
                                    productoModificarView,
                                    carritoAnadirView
                            );

                            CarritoController carritoController = new CarritoController(
                                    managerDAO.getCarritoDAO(),
                                    managerDAO.getProductoDAO(),
                                    carritoAnadirView,
                                    carritoBuscarView,
                                    carritoEliminarView,
                                    carritoActualizarView,
                                    carritoListaView,
                                    carritoDetalleView,
                                    usuarioAutenticado
                            );

                            // Configurar los eventos para las vistas y controladores de Producto y Carrito.
                            productoController.configurarEventosAnadir();
                            productoController.configurarEventosLista();
                            productoController.configurarEventosEliminar();
                            productoController.configurarEventosModificar();
                            productoController.configurarEventosCarrito();
                            carritoController.configurarEventosEnVistas();

                            // Muestra un mensaje de bienvenida al usuario autenticado.
                            principalView.mostrarMensaje(
                                    MessageFormat.format(
                                            mensaje.get("mensaje.bienvenida"),
                                            usuarioAutenticado.getNombreCompleto()
                                    )
                            );

                            // Configura la visibilidad del menú principal según el rol del usuario (ADMINISTRADOR/USUARIO).
                            if(usuarioAutenticado.getRol() == Rol.USUARIO) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            // Configurar las acciones de los ítems del menú principal para abrir las vistas internas.
                            configurarAccionesMenu(principalView, productoAnadirView, productoListaView,
                                    productoModificarView, productoEliminarView, carritoAnadirView,
                                    carritoBuscarView, carritoEliminarView, carritoActualizarView,
                                    carritoListaView, usuarioListaView, usuarioEliminarView,
                                    adminModificarView, usuarioModificarView, usuarioController);

                            // Configurar un listener para cuando se cierre la ventana principal.
                            // Al cerrar la ventana principal, se vuelve a mostrar la ventana de login.
                            principalView.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    loginView.setVisible(true);  // Muestra el login nuevamente
                                    loginView.limpiarCampos();   // Limpia los campos del login para una nueva sesión
                                }
                            });

                            principalView.setVisible(true); // Hace visible la ventana principal
                        }
                        // Se elimina el System.exit(0) para permitir que la aplicación continúe y
                        // el usuario pueda volver al login o iniciar otra sesión.
                    }
                });
            }
        });
    }

    /**
     * <p>Configura los {@link ActionListener} para los elementos del menú de la {@link MenuPrincipalView}.</p>
     * <p>Asocia cada ítem del menú con la acción de mostrar la vista interna correspondiente
     * dentro del {@link JDesktopPane} de la ventana principal.</p>
     *
     * @param principalView La {@link MenuPrincipalView} a la que se le configuran las acciones.
     * @param productoAnadirView La vista para añadir productos.
     * @param productoListaView La vista para listar productos.
     * @param productoModificarView La vista para modificar productos.
     * @param productoEliminarView La vista para eliminar productos.
     * @param carritoAnadirView La vista para añadir al carrito.
     * @param carritoBuscarView La vista para buscar carritos.
     * @param carritoEliminarView La vista para eliminar carritos.
     * @param carritoActualizarView La vista para actualizar carritos.
     * @param carritoListaView La vista para listar carritos.
     * @param usuarioListaView La vista para listar usuarios.
     * @param usuarioEliminarView La vista para eliminar usuarios.
     * @param adminModificarView La vista de modificación para el administrador.
     * @param usuarioModificarView La vista de modificación para el usuario normal.
     * @param usuarioController El controlador de usuarios para obtener el usuario autenticado.
     */
    private static void configurarAccionesMenu(MenuPrincipalView principalView,
                                               ProductoAnadirView productoAnadirView, ProductoListaView productoListaView,
                                               ProductoModificarView productoModificarView, ProductoEliminarView productoEliminarView,
                                               CarritoAnadirView carritoAnadirView, CarritoBuscarView carritoBuscarView,
                                               CarritoEliminarView carritoEliminarView, CarritoActualizarView carritoActualizarView,
                                               CarritoListaView carritoListaView, UsuarioListaView usuarioListaView,
                                               UsuarioEliminarView usuarioEliminarView, AdminModificarView adminModificarView,
                                               UsuarioModificarView usuarioModificarView, UsuarioController usuarioController) {

        // Acciones para el menú de Productos
        principalView.getMenuItemCrear().addActionListener(e -> mostrarVistaSiNoVisible(principalView, productoAnadirView));
        principalView.getMenuItemBuscar().addActionListener(e -> mostrarVistaSiNoVisible(principalView, productoListaView));
        principalView.getMenuItemActualizar().addActionListener(e -> mostrarVistaSiNoVisible(principalView, productoModificarView));
        principalView.getMenuItemEliminar().addActionListener(e -> mostrarVistaSiNoVisible(principalView, productoEliminarView));

        // Acciones para el menú de Carritos
        principalView.getMenuItemCrearCarrito().addActionListener(e -> mostrarVistaSiNoVisible(principalView, carritoAnadirView));
        principalView.getMenuItemBuscarCarrito().addActionListener(e -> mostrarVistaSiNoVisible(principalView, carritoBuscarView));
        principalView.getMenuItemEliminarCarrito().addActionListener(e -> mostrarVistaSiNoVisible(principalView, carritoEliminarView));
        principalView.getMenuItemActualizarCarrito().addActionListener(e -> mostrarVistaSiNoVisible(principalView, carritoActualizarView));
        principalView.getMenuItemListarCarrito().addActionListener(e -> mostrarVistaSiNoVisible(principalView, carritoListaView));

        // Acciones para el menú de Usuarios (Admin)
        principalView.getMenuItemListarUsuario().addActionListener(e -> mostrarVistaSiNoVisible(principalView, usuarioListaView));
        principalView.getMenuItemEliminarUsuario().addActionListener(e -> mostrarVistaSiNoVisible(principalView, usuarioEliminarView));

        // Acción para modificar usuario, diferenciando entre rol de ADMINISTRADOR y USUARIO
        principalView.getMenuItemModificarUsuario().addActionListener(e -> {
            Usuario usuario = usuarioController.getUsuarioAutenticado();
            if(usuario == null) return; // Si no hay usuario autenticado, no hacer nada

            if(usuario.getRol() == Rol.ADMINISTRADOR) {
                // Si es administrador, mostrar la vista de modificación de administrador y limpiarla
                mostrarVistaSiNoVisible(principalView, adminModificarView);
                adminModificarView.limpiar();
            } else {
                // Si es usuario normal, mostrar la vista de modificación de usuario y cargar sus datos
                mostrarVistaSiNoVisible(principalView, usuarioModificarView);
                usuarioModificarView.cargarDatosUsuario(usuario);
            }
        });
    }

    /**
     * <p>Muestra una {@link JInternalFrame} (vista interna) dentro del {@link JDesktopPane}
     * de la ventana principal si aún no está visible.</p>
     * <p>Este método asegura que no se abran múltiples instancias de la misma vista interna.</p>
     *
     * @param principal La {@link MenuPrincipalView} que contiene el {@link JDesktopPane}.
     * @param vista La {@link JInternalFrame} que se desea mostrar.
     */
    private static void mostrarVistaSiNoVisible(MenuPrincipalView principal, JInternalFrame vista) {
        if(!vista.isVisible()) {
            principal.getMiJDesktopPane().add(vista);
            vista.setVisible(true);
        }
    }
}