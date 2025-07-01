package ec.edu.ups.vista;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalView extends JFrame {
    private MensajeInternacionalizacionHandler mensaje;

    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
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

        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        menuItemSalir = new JMenuItem(mensaje.get("menu.salir.salir"));
        menuItemCerrarSesion = new JMenuItem(mensaje.get("menu.salir.cerrar"));

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
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

        menuIdioma.add(menuItemEspaniol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        setJMenuBar(menuBar);
        setContentPane(miJDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(mensaje.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
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

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }



    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void deshabilitarMenusAdministrador() {
        getMenuItemCrear().setEnabled(false);
        getMenuItemBuscar().setEnabled(false);
        getMenuItemActualizar().setEnabled(false);
        getMenuItemEliminar().setEnabled(false);
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
                    new LoginView().setVisible(true);

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
