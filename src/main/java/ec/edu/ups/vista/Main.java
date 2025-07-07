package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.RecuperarContraseniaController;
import ec.edu.ups.controlador.UsuarioController;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;

import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria;
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
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                LoginView loginView = new LoginView();
                loginView.setVisible(true);

                CarritoDAO carritoDAO = new CarritoDAOMemoria();

                PreguntaSeguridadDAO preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria();

                UsuarioRegistroView usuarioRegistroView = new UsuarioRegistroView();
                RecuperarContraseniaView recuperarContraseniaView = new RecuperarContraseniaView();

                UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(loginView.getMensaje());
                UsuarioListaView usuarioListaView = new UsuarioListaView(loginView.getMensaje());
                UsuarioModificarView usuarioModificarView = new UsuarioModificarView(loginView.getMensaje());
                AdminModificarView adminModificarView = new AdminModificarView(loginView.getMensaje());

                loginView.setUsuarioRegistroView(usuarioRegistroView);
                loginView.setRecuperarContraseniaView(recuperarContraseniaView);

                UsuarioController usuarioController = new UsuarioController(
                        usuarioDAO,
                        carritoDAO,
                        loginView,
                        preguntaSeguridadDAO,
                        usuarioRegistroView,
                        recuperarContraseniaView,
                        usuarioEliminarView,
                        usuarioListaView,
                        usuarioModificarView,
                        adminModificarView,
                        loginView.getMensaje()
                );

                RecuperarContraseniaController recuperarController = new RecuperarContraseniaController(
                        recuperarContraseniaView,
                        usuarioDAO,
                        preguntaSeguridadDAO,
                        loginView.getMensaje()
                );

                recuperarController.configurarEventos();

                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                        if(usuarioAutenticado != null) {
                            MensajeInternacionalizacionHandler mensaje = loginView.getMensaje();

                            ProductoDAO productoDAO = new ProductoDAOMemoria();

                            MenuPrincipalView principalView = new MenuPrincipalView(mensaje);

                            ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
                            ProductoListaView productoListaView = new ProductoListaView(mensaje);
                            ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);

                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
                            CarritoBuscarView carritoBuscarView = new CarritoBuscarView(mensaje);
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
                            CarritoActualizarView carritoActualizarView = new CarritoActualizarView(mensaje);
                            CarritoListaView carritoListaView = new CarritoListaView(mensaje, usuarioAutenticado.getRol());
                            CarritoDetalleView carritoDetalleView = new CarritoDetalleView(mensaje);

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
                                    carritoDetalleView,
                                    usuarioAutenticado
                            );



                            productoController.configurarEventosAnadir();
                            productoController.configurarEventosModificar();
                            productoController.configurarEventosEliminar();
                            productoController.configurarEventosLista();
                            productoController.configurarEventosCarrito();

                            carritoController.configurarEventosEnVistas();

                            principalView.mostrarMensaje(
                                    MessageFormat.format(mensaje.get("mensaje.bienvenida"),
                                            usuarioAutenticado.getNombreCompleto()
                                    )
                            );

                            if(usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            principalView.getMenuItemCrear().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoAnadirView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(productoAnadirView);
                                        productoAnadirView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoListaView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(productoListaView);
                                        productoListaView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoEliminarView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(productoEliminarView);
                                        productoEliminarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizar().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoModificarView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(productoModificarView);
                                        productoModificarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoAnadirView.isVisible()){
                                        principalView.getMiJDesktopPane().add(carritoAnadirView);
                                        carritoAnadirView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoBuscarView.isVisible()){
                                        principalView.getMiJDesktopPane().add(carritoBuscarView);
                                        carritoBuscarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoEliminarView.isVisible()){
                                        principalView.getMiJDesktopPane().add(carritoEliminarView );
                                        carritoEliminarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemActualizarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoActualizarView.isVisible()){
                                        principalView.getMiJDesktopPane().add(carritoActualizarView );
                                        carritoActualizarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!carritoListaView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(carritoListaView);
                                        carritoListaView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemListarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!usuarioListaView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(usuarioListaView);
                                        usuarioListaView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemEliminarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!usuarioEliminarView.isVisible()) {
                                        principalView.getMiJDesktopPane().add(usuarioEliminarView);
                                        usuarioEliminarView.setVisible(true);
                                    }
                                }
                            });

                            principalView.getMenuItemModificarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                                    if(usuarioAutenticado == null) {
                                        JOptionPane.showMessageDialog(principalView, "Primero debe autenticarse");
                                        return;
                                    }

                                    if(usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                                        // Comportamiento para ADMIN
                                        if(!adminModificarView.isVisible()) {
                                            principalView.getMiJDesktopPane().add(adminModificarView);
                                            adminModificarView.setVisible(true);
                                            adminModificarView.limpiar();
                                        }
                                    } else {

                                        if(!usuarioModificarView.isVisible()) {
                                            principalView.getMiJDesktopPane().add(usuarioModificarView);
                                            usuarioModificarView.setVisible(true);
                                            // Cargar datos automáticamente
                                            usuarioModificarView.cargarDatosUsuario(usuarioAutenticado);
                                        }
                                    }
                                }
                            });

                            principalView.getMenuItemEspaniol().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    mensaje.setLenguaje("es", "EC");
                                    principalView.cambiarIdioma("es", "EC");

                                    productoAnadirView.cambiarIdioma("es", "EC");
                                    productoListaView.cambiarIdioma("es", "EC");
                                    productoModificarView.cambiarIdioma("es", "EC");
                                    productoEliminarView.cambiarIdioma("es", "EC");

                                    carritoAnadirView.cambiarIdioma("es", "EC");
                                    carritoBuscarView.cambiarIdioma("es", "EC");
                                    carritoEliminarView.cambiarIdioma("es", "EC");
                                    carritoActualizarView.cambiarIdioma("es", "EC");
                                    carritoListaView.cambiarIdioma("es", "EC");
                                    carritoDetalleView.cambiarIdioma("es", "EC");

                                    usuarioRegistroView.cambiarIdioma("es", "EC");
                                    usuarioListaView.cambiarIdioma("es", "EC");
                                    usuarioEliminarView.cambiarIdioma("es","EC");
                                    usuarioModificarView.cambiarIdioma("es", "EC");
                                }
                            });

                            principalView.getMenuItemIngles().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    mensaje.setLenguaje("en", "US");
                                    principalView.cambiarIdioma("en", "US");

                                    productoAnadirView.cambiarIdioma("en", "US");
                                    productoListaView.cambiarIdioma("en", "US");
                                    productoModificarView.cambiarIdioma("en", "US");
                                    productoEliminarView.cambiarIdioma("en", "US");

                                    carritoAnadirView.cambiarIdioma("en", "US");
                                    carritoBuscarView.cambiarIdioma("en", "US");
                                    carritoEliminarView.cambiarIdioma("en", "US");
                                    carritoActualizarView.cambiarIdioma("en", "US");
                                    carritoListaView.cambiarIdioma("en", "US");
                                    carritoDetalleView.cambiarIdioma("en", "US");

                                    usuarioRegistroView.cambiarIdioma("en", "US");
                                    usuarioListaView.cambiarIdioma("en", "US");
                                    usuarioEliminarView.cambiarIdioma("en","US");
                                    usuarioModificarView.cambiarIdioma("en", "US");
                                }
                            });

                            principalView.getMenuItemFrances().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    mensaje.setLenguaje("fr", "FR");
                                    principalView.cambiarIdioma("fr", "FR");

                                    productoAnadirView.cambiarIdioma("fr", "FR");
                                    productoListaView.cambiarIdioma("fr", "FR");
                                    productoModificarView.cambiarIdioma("fr", "FR");
                                    productoEliminarView.cambiarIdioma("fr", "FR");

                                    carritoAnadirView.cambiarIdioma("fr", "FR");
                                    carritoBuscarView.cambiarIdioma("fr", "FR");
                                    carritoEliminarView.cambiarIdioma("fr", "FR");
                                    carritoActualizarView.cambiarIdioma("fr", "FR");
                                    carritoListaView.cambiarIdioma("fr", "FR");
                                    carritoDetalleView.cambiarIdioma("fr", "FR");

                                    usuarioRegistroView.cambiarIdioma("fr", "FR");
                                    usuarioListaView.cambiarIdioma("fr", "FR");
                                    usuarioEliminarView.cambiarIdioma("fr","FR");
                                    usuarioModificarView.cambiarIdioma("fr", "FR");
                                }
                            });

                            principalView.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    usuarioController.setUsuario(null);
                                    loginView.limpiarCampos();
                                    loginView.setVisible(true);
                                    loginView.setState(JFrame.NORMAL);
                                    loginView.toFront();
                                    loginView.requestFocus();
                                }
                            });

                            principalView.setVisible(true);
                        } else {
                            System.exit(0);
                        }
                    }
                });
            }
        });
    }
}