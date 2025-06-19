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
                //Instanciamos DAO
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                //Instancia Vista
                MenuPrincipalView principalView = new MenuPrincipalView();
                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoModificarView productoModificarView = new ProductoModificarView();
                ProductoEliminarView  productoEliminarView = new ProductoEliminarView();
                CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                //Instancia Controlador
                ProductoController productoController = new ProductoController(productoDAO,
                        productoAnadirView,
                        productoListaView,
                        productoEliminarView,
                        productoModificarView,
                        carritoAnadirView);

                productoController.configurarEventosAnadir();
                productoController.configurarEventosModificar();
                productoController.configurarEventosEliminar();
                productoController.configurarEventosLista();
                productoController.configurarEventosCarrito();

                principalView.getMenuItemCrear().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoAnadirView.isVisible()) {
                            productoAnadirView.setVisible(true);
                            principalView.getjDesktopPane().add(productoAnadirView);
                        }
                    }
                });

                principalView.getMenuItemBuscar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoListaView.isVisible()) {
                            productoListaView.setVisible(true);
                            principalView.getjDesktopPane().add(productoListaView);
                        }
                    }
                });

                principalView.getMenuItemEliminar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoEliminarView.isVisible()) {
                            productoEliminarView.setVisible(true);
                            principalView.getjDesktopPane().add(productoEliminarView);
                        }
                    }
                });

                principalView.getMenuItemActualizar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoModificarView.isVisible()) {
                            productoModificarView.setVisible(true);
                            principalView.getjDesktopPane().add(productoModificarView);
                        }
                    }
                });

                principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!carritoAnadirView.isVisible()) {
                            carritoAnadirView.setVisible(true);
                            principalView.getjDesktopPane().add(carritoAnadirView);
                        }
                    }
                });
            }
        });
    }
}
