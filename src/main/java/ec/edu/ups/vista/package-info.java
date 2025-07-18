/**
 * Provee las clases relacionadas con la interfaz de usuario (UI)
 * de la aplicación "OnlineMarket".
 *
 * <p>Este paquete contiene todas las ventanas (vistas) que el usuario final
 * interactúa, organizadas por módulos funcionales como autenticación,
 * gestión de carritos de compra, gestión de productos y gestión de usuarios.
 * Cada subpaquete y clase dentro de 'vista' es responsable de presentar
 * la información al usuario y capturar sus entradas.</p>
 *
 * <h2>Estructura del Paquete:</h2>
 * <ul>
 * <li><b>{@link ec.edu.ups.vista.autenticacion autenticacion}</b>: Contiene vistas para el inicio de sesión,
 * recuperación de contraseña y registro de nuevos usuarios.</li>
 * <li><b>{@link ec.edu.ups.vista.carrito carrito}</b>: Incluye vistas para la creación, búsqueda,
 * eliminación, actualización y listado de carritos de compra, así como la vista de detalle del carrito.</li>
 * <li><b>{@link ec.edu.ups.vista.producto producto}</b>: Contiene las vistas para añadir, eliminar,
 * listar y modificar productos disponibles en el sistema.</li>
 * <li><b>{@link ec.edu.ups.vista.usuario usuario}</b>: Provee vistas para listar, eliminar y modificar
 * información de usuarios, incluyendo una vista específica para administradores.</li>
 * </ul>
 *
 * <h3>Clases Principales en el Nivel Superior:</h3>
 * <ul>
 * <li>{@link ec.edu.ups.vista.MenuPrincipalView}: La ventana principal de la aplicación
 * que actúa como contenedor para las vistas internas (JInternalFrame) y gestiona la navegación.</li>
 * <li>{@link ec.edu.ups.vista.MiJDesktopPane}: Un JDesktopPane personalizado utilizado
 * como fondo de la aplicación, proporcionando un diseño visual único.</li>
 * <li>Main: La clase de punto de entrada principal para iniciar la aplicación.</li>
 * </ul>
 *
 * <p>El diseño de estas vistas busca proporcionar una experiencia de usuario intuitiva
 * y está preparado para la internacionalización a través del uso de
 * {@link ec.edu.ups.utils.MensajeInternacionalizacionHandler MensajeInternacionalizacionHandler}.</p>
 *
 * @since 1.0
 * @see ec.edu.ups.vista.autenticacion
 * @see ec.edu.ups.vista.carrito
 * @see ec.edu.ups.vista.producto
 * @see ec.edu.ups.vista.usuario
 * @see ec.edu.ups.vista.MenuPrincipalView
 * @see ec.edu.ups.vista.MiJDesktopPane
 */
package ec.edu.ups.vista;