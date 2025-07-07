package ec.edu.ups.vista;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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

    private JDesktopPane jDesktopPane;
    private MiJDesktopPane miJDesktopPane;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        agregarListeners();
    }

    public void initComponents() {
        jDesktopPane = new JDesktopPane();
        miJDesktopPane = new MiJDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu(mensaje.get("menu.producto"));
        menuCarrito = new JMenu(mensaje.get("menu.carrito"));
        menuUsuario = new JMenu(mensaje.get("menu.usuario"));
        menuIdioma = new JMenu(mensaje.get("menu.idioma"));
        menuSalir = new JMenu(mensaje.get("menu.salir"));

        menuItemCrear = new JMenuItem(mensaje.get("menu.producto.crear"));
        menuItemBuscar = new JMenuItem(mensaje.get("menu.producto.buscar"));
        menuItemEliminar = new JMenuItem(mensaje.get("menu.producto.eliminar"));
        menuItemActualizar = new JMenuItem(mensaje.get("menu.producto.actualizar"));

        menuItemCrearCarrito = new JMenuItem(mensaje.get("menu.carrito.crear"));
        menuItemBuscarCarrito = new JMenuItem(mensaje.get("menu.carrito.buscar"));
        menuItemEliminarCarrito = new JMenuItem(mensaje.get("menu.carrito.eliminar"));
        menuItemActualizarCarrito = new JMenuItem(mensaje.get("menu.carrito.actualizar"));
        menuItemListarCarrito = new JMenuItem(mensaje.get("menu.carrito.listar"));

        menuItemListarUsuario = new JMenuItem(mensaje.get("menu.usuario.listar"));
        menuItemEliminarUsuario = new JMenuItem(mensaje.get("menu.usuario.eliminar"));
        menuItemModificarUsuario = new JMenuItem(mensaje.get("menu.usuario.modificar"));

        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        menuItemSalir = new JMenuItem(mensaje.get("menu.salir.salir"));
        menuItemCerrarSesion = new JMenuItem(mensaje.get("menu.salir.cerrar"));

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

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

        setJMenuBar(menuBar);
        setContentPane(miJDesktopPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(mensaje.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        //Íconos para los items

        //MENÚ DE PRODUCTOS
        URL crearProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/crear_producto.png");
        if(crearProductoURL != null) {
            ImageIcon iconCrearProducto = new ImageIcon(crearProductoURL);
            menuItemCrear.setIcon(iconCrearProducto);
        } else {
            System.out.println("No se cargo el anadir_producto");
        }

        URL buscarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/buscar_producto.png");
        if(buscarProductoURL != null) {
            ImageIcon iconBuscarProducto = new ImageIcon(buscarProductoURL);
            menuItemBuscar.setIcon(iconBuscarProducto);
        } else {
            System.out.println("No se cargó el buscar producto");
        }

        URL eliminarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/eliminar_producto.png");
        if(eliminarProductoURL != null) {
            ImageIcon iconEliminarProducto = new ImageIcon(eliminarProductoURL);
            menuItemEliminar.setIcon(iconEliminarProducto);
        } else {
            System.out.println("No se cargó eliminarProducto");
        }

        URL actualizarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/modificar.png");
        if(actualizarProductoURL != null) {
            ImageIcon iconActualizarProducto = new ImageIcon(actualizarProductoURL);
            menuItemActualizar.setIcon(iconActualizarProducto);
        } else {
            System.err.println("No se cargó el modificar en menuprincipal");
        }

        //MENÚ CARRITO
        URL crearCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/crear_carrito.png");
        if(crearCarritoURL != null) {
            ImageIcon iconCrearCarrito = new ImageIcon(crearCarritoURL);
            menuItemCrearCarrito.setIcon(iconCrearCarrito);
        } else {
            System.err.println("No se cargó el crear carrito");
        }

        URL buscarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/buscar_carrito.png");
        if(buscarCarritoURL != null) {
            ImageIcon iconBuscarCarrito = new ImageIcon(buscarCarritoURL);
            menuItemBuscarCarrito.setIcon(iconBuscarCarrito);
        } else {
            System.err.println("No se cargó el buscar carrito");
        }

        URL eliminarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/eliminar_carrito.png");
        if(eliminarCarritoURL != null) {
            ImageIcon iconEliminarCaarito = new ImageIcon(eliminarCarritoURL);
            menuItemEliminarCarrito.setIcon(iconEliminarCaarito);
        } else {
            System.err.println("No se cargó el eliminar carrito");
        }

        URL actualizarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/modificar.png");
        if(actualizarCarritoURL != null) {
            ImageIcon iconActualizarCarrito = new ImageIcon(actualizarCarritoURL);
            menuItemActualizarCarrito.setIcon(iconActualizarCarrito);
        } else {
            System.err.println("No se cargó el actualizar carrito");
        }

        URL listarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/listar_carrito.png");
        if(listarCarritoURL != null) {
            ImageIcon iconListarCarrito = new ImageIcon(listarCarritoURL);
            menuItemListarCarrito.setIcon(iconListarCarrito);
        } else {
            System.err.println("No se cargó el listar carrito");
        }

        //MENÚ USUARIO
        URL listarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/listar_usuario.png");
        if(listarUsuarioURL != null) {
            ImageIcon iconListarUsuarios = new ImageIcon(listarUsuarioURL);
            menuItemListarUsuario.setIcon(iconListarUsuarios);
        } else {
            System.err.println("No se cargó el listar usuario");
        }

        URL eliminarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/eliminar_usuario.png");
        if(eliminarUsuarioURL != null) {
            ImageIcon iconEliminarUsuarios = new ImageIcon(eliminarUsuarioURL);
            menuItemEliminarUsuario.setIcon(iconEliminarUsuarios);
        } else {
            System.err.println("No se cargó el eliminar usuario en menu principal");
        }

        URL modificarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/modificar.png");
        if(modificarUsuarioURL != null) {
            ImageIcon iconModificarUsuario = new ImageIcon(modificarUsuarioURL);
            menuItemModificarUsuario.setIcon(iconModificarUsuario);
        } else {
            System.err.println("No se cargó el modificar usuario");
        }

        //MENÚ SALIR
        URL salirURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/salir_sistema.png");
        if(salirURL != null) {
            ImageIcon iconSalir = new ImageIcon(salirURL);
            menuItemSalir.setIcon(iconSalir);
        } else {
            System.err.println("No se cargó el salir del sistema");
        }

        URL cerrarSesionURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/cerrar_sesion.png");
        if(cerrarSesionURL != null) {
            ImageIcon iconCerrarSesion = new ImageIcon(cerrarSesionURL);
            menuItemCerrarSesion.setIcon(iconCerrarSesion);
        } else {
            System.err.println("No se cargó el cerrar sesión");
        }

        //MENÚ IDIOMAS
        URL espaniolURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/espana.png");
        if(espaniolURL != null) {
            ImageIcon iconItemEsp = new ImageIcon(espaniolURL);
            menuItemEspaniol.setIcon(iconItemEsp);
        } else {
            System.out.println("Error, no se cargó la bandera de españa");
        }

        URL inglesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/reino-unido.png");
        if(inglesURL != null) {
            ImageIcon iconItemIng = new ImageIcon(inglesURL);
            menuItemIngles.setIcon(iconItemIng);
        } else {
            System.out.println("Error, no se cargó la bandra inglesa");
        }

        URL francesURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/francia.png");
        if(francesURL != null) {
            ImageIcon iconItemFrn = new ImageIcon(francesURL);
            menuItemFrances.setIcon(iconItemFrn);
        } else {
            System.out.println("Error, no se cargó la bandera de francia");
        }
    }

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

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this,mensaje);
    }

    public void deshabilitarMenusAdministrador() {
        getMenuProducto().setVisible(false);
        getMenuItemListarUsuario().setVisible(false);
        getMenuItemEliminarUsuario().setVisible(false);
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        // Actualizar el manejador de internacionalización
        mensaje.setLenguaje(lenguaje, pais);

        // Actualizar título de la ventana
        setTitle(mensaje.get("app.titulo"));

        // Actualizar textos de los menús principales
        menuProducto.setText(mensaje.get("menu.producto"));
        menuCarrito.setText(mensaje.get("menu.carrito"));
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
        if (menuItemFrances != null) {
            menuItemFrances.setText(mensaje.get("menu.idioma.fr"));
        }

        // Actualizar opciones de Salir
        menuItemSalir.setText(mensaje.get("menu.salir.salir"));
        menuItemCerrarSesion.setText(mensaje.get("menu.salir.cerrar"));

    }

    private void agregarListeners() {
        // Listener para Cerrar Sesión (solo desde el menú)
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
                    dispose();
                    JOptionPane.showMessageDialog(
                            MenuPrincipalView.this,
                            mensaje.get("mensaje.sesion.cerrada"),
                            mensaje.get("titulo.informacion"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
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
                    System.exit(0);
                }
            }
        });
    }
}
