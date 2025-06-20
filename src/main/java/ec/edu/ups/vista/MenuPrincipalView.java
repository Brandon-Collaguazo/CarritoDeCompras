package ec.edu.ups.vista;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;

    private JMenuItem menuItemCrear;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem menuItemBuscar;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemBuscarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemActualizarCarrito;

    private JDesktopPane jDesktopPane;

    public MenuPrincipalView() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");

        menuItemCrear = new JMenuItem("Crear Producto");
        menuItemBuscar = new JMenuItem("Buscar Producto");
        menuItemEliminar = new JMenuItem("Eliminar Producto");
        menuItemActualizar = new JMenuItem("Modificar Producto");

        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemBuscarCarrito = new JMenuItem("Buscar Carrito");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemActualizarCarrito = new JMenuItem("Actualizar carrito");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);

        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemBuscar);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemActualizarCarrito);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema De Carrito de Compras");
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

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }
}
