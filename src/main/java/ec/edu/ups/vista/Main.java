package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProductoDAO productoDAO = new ProductoDAOMemoria();

                PrincipalView principal = new PrincipalView();
                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoModificarView productoModificarView = new ProductoModificarView();
                ProductoEliminarView  productoEliminarView = new ProductoEliminarView();

                ProductoController productoController = new ProductoController(productoDAO);

                productoController.setProductoAnadirView(productoAnadirView);
                productoController.setProductoListaView(productoListaView);
                productoController.setProductoModificarView(productoModificarView);
                productoController.setProductoEliminarView(productoEliminarView);

                productoController.configurarEventosAnadir();
                productoController.configurarEventosModificar();
                productoController.configurarEventosEliminar();
                productoController.configurarEventosLista();

                principal.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoAnadirView.isVisible()){
                            productoAnadirView.setVisible(true);
                            principal.getjDesktopPane().add(productoAnadirView);
                        }
                    }
                });

                principal.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoListaView.isVisible()) {
                            productoListaView.setVisible(true);
                            principal.getjDesktopPane().add(productoListaView);
                        }
                    }
                });

                principal.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoEliminarView.isVisible()) {
                            productoEliminarView.setVisible(true);
                            principal.getjDesktopPane().add(productoEliminarView);
                        }
                    }
                });

                principal.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoModificarView.isVisible()) {
                            productoModificarView.setVisible(true);
                            principal.getjDesktopPane().add(productoModificarView);
                        }
                    }
                });
            }
        });
    }
}
