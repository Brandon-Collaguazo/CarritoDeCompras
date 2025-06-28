package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;

import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.carrito.*;
import ec.edu.ups.vista.producto.ProductoAnadirView;
import ec.edu.ups.vista.producto.ProductoEliminarView;
import ec.edu.ups.vista.producto.ProductoListaView;
import ec.edu.ups.vista.producto.ProductoModificarView;
import ec.edu.ups.vista.usuario.LoginView;
import ec.edu.ups.vista.usuario.UsuarioRegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Iniciar sesión
                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                LoginView loginView = new LoginView();
                UsuarioRegistroView usuarioRegistroView = new UsuarioRegistroView();
                loginView.setVisible(true);

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, usuarioRegistroView);

                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                        if(usuarioAutenticado != null) {
                            //Instanciamos MensajeHandler (SINGLETON)
                            MensajeInternacionalizacionHandler mensaje = new MensajeInternacionalizacionHandler("es", "EC");

                            // Instanciamos DAO
                            ProductoDAO productoDAO = new ProductoDAOMemoria();
                            CarritoDAO carritoDAO = new CarritoDAOMemoria();

                            // Instancia Vistas, pasando el objeto 'mensaje' a cada una
                            MenuPrincipalView principalView = new MenuPrincipalView(mensaje);

                            ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
                            ProductoListaView productoListaView = new ProductoListaView(mensaje);
                            ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);

                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
                            CarritoBuscarView carritoBuscarView = new CarritoBuscarView(mensaje);
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
                            CarritoActualizarView carritoActualizarView = new CarritoActualizarView(mensaje);
                            CarritoListaView carritoListaView = new CarritoListaView(mensaje);
                            CarritoDetalleView carritoDetalleView = new CarritoDetalleView(mensaje);

                            // Instancia Controlador
                            ProductoController productoController = new ProductoController(productoDAO,
                                    productoAnadirView,
                                    productoListaView,
                                    productoEliminarView,
                                    productoModificarView,
                                    carritoAnadirView);

                            CarritoController carritoController = new CarritoController(carritoDAO,
                                    productoDAO,
                                    carritoAnadirView,
                                    carritoBuscarView,
                                    carritoEliminarView,
                                    carritoActualizarView,
                                    carritoListaView,
                                    usuarioAutenticado
                            );

                            productoController.configurarEventosAnadir();
                            productoController.configurarEventosModificar();
                            productoController.configurarEventosEliminar();
                            productoController.configurarEventosLista();
                            productoController.configurarEventosCarrito();

                            principalView.mostrarMensaje("Bienvenido " + usuarioAutenticado.getUsername());
                            if(usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            // Configuración de ActionListeners para abrir las vistas
                            principalView.getMenuItemCrear().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoAnadirView.isVisible()) {
                                        principalView.getjDesktopPane().add(productoAnadirView);
                                        productoAnadirView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoListaView.isVisible()) {
                                        principalView.getjDesktopPane().add(productoListaView);
                                        productoListaView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoEliminarView.isVisible()) {
                                        principalView.getjDesktopPane().add(productoEliminarView);
                                        productoEliminarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoModificarView.isVisible()) {
                                        principalView.getjDesktopPane().add(productoModificarView);
                                        productoModificarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoAnadirView.isVisible()){
                                        principalView.getjDesktopPane().add(carritoAnadirView);
                                        carritoAnadirView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoBuscarView.isVisible()){
                                        principalView.getjDesktopPane().add(carritoBuscarView);
                                        carritoBuscarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoEliminarView.isVisible()){
                                        principalView.getjDesktopPane().add(carritoEliminarView );
                                        carritoEliminarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoActualizarView.isVisible()){
                                        principalView.getjDesktopPane().add(carritoActualizarView );
                                        carritoActualizarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoListaView.isVisible()) {
                                        principalView.getjDesktopPane().add(carritoListaView);
                                        carritoListaView.setVisible(true);
                                    }
                                }
                            });

                            // Listeners para cambiar el idioma desde el MenuPrincipalView
                            principalView.getMenuItemEspaniol().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("es", "EC");
                                }
                            });

                            principalView.getMenuItemIngles().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("en", "US");
                                }
                            });

                            principalView.getMenuItemFrances().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    principalView.cambiarIdioma("fr", "FR");
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
