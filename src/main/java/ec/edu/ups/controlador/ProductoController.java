package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.utils.FormateadorUtils;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.producto.ProductoAnadirView;
import ec.edu.ups.vista.producto.ProductoEliminarView;
import ec.edu.ups.vista.producto.ProductoListaView;
import ec.edu.ups.vista.producto.ProductoModificarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Controlador para la gestión de productos en el sistema.
 * Maneja las operaciones CRUD de productos y coordina las interacciones
 * entre las vistas de productos y el modelo de datos.
 */
public class ProductoController {

    /**
     * DAO para operaciones de persistencia de productos.
     */
    private final ProductoDAO productoDAO;

    /**
     * Vista para añadir nuevos productos.
     */
    private final ProductoAnadirView productoAnadirView;

    /**
     * Vista para listar productos existentes.
     */
    private final ProductoListaView productoListaView;

    /**
     * Vista para eliminar productos.
     */
    private final ProductoEliminarView productoEliminarView;

    /**
     * Vista para modificar productos existentes.
     */
    private final ProductoModificarView productoModificarView;

    /**
     * Vista del carrito de compras (para integración).
     */
    private final CarritoAnadirView carritoAnadirView;

    /**
     * Constructor principal del controlador de productos.
     *
     * @param productoDAO DAO para operaciones de productos
     * @param productoAnadirView Vista de añadir productos
     * @param productoListaView Vista de listar productos
     * @param productoEliminarView Vista de eliminar productos
     * @param productoModificarView Vista de modificar productos
     * @param carritoAnadirView Vista del carrito para integración
     */
    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView,
                              CarritoAnadirView carritoAnadirView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        this.carritoAnadirView = carritoAnadirView;
    }

    /**
     * Configura los eventos para la vista de añadir productos.
     */
    public void configurarEventosAnadir() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    /**
     * Configura los eventos para la vista de listar productos.
     */
    public void configurarEventosLista() {
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    /**
     * Configura los eventos para la vista de eliminar productos.
     */
    public void configurarEventosEliminar() {
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    /**
     * Configura los eventos para la vista de modificar productos.
     */
    public void configurarEventosModificar() {
        productoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoModificar();
            }
        });

        productoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });
    }

    /**
     * Configura los eventos relacionados con productos en el carrito.
     */
    public void configurarEventosCarrito() {
        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     */
    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());
        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("guardado.producto.exito");
        productoAnadirView.limpiarCampos();
    }

    /**
     * Busca un producto por su código y lo muestra en la lista.
     */
    private void buscarProducto() {
        String codigoStr = productoListaView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(codigoStr);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if(producto != null) {
            List<Producto> productos = new ArrayList<>();
            productos.add(producto);
            productoListaView.cargarDatos(productos);
            productoListaView.limpiarCampos();
        } else {
            productoListaView.mostrarMensaje("producto.no.encontrado");
            productoListaView.cargarDatos(new ArrayList<>());
        }
    }

    /**
     * Busca un producto para su posterior eliminación.
     */
    private void buscarProductoEliminar() {
        int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            productoEliminarView.getBtnEliminar().setEnabled(true);
        } else {
            productoEliminarView.mostrarMensaje("producto.no.encontrado");
            productoEliminarView.getTxtNombre().setText("");
            productoEliminarView.getTxtPrecio().setText("");
            productoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    /**
     * Busca un producto para su modificación.
     */
    private void buscarProductoModificar() {
        int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        Locale locale = productoModificarView.getMensaje().getLocale();

        if (producto != null) {
            productoModificarView.getTxtNombre().setText(producto.getNombre());
            productoModificarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), locale));
            productoModificarView.getBtnActualizar().setEnabled(true);
        } else {
            productoModificarView.mostrarMensaje("producto.no.encontrado");
            productoModificarView.getTxtNombre().setText("");
            productoModificarView.getTxtPrecio().setText("");
            productoModificarView.getBtnActualizar().setEnabled(false);
        }
    }

    /**
     * Modifica los datos de un producto existente.
     */
    private void modificarProducto() {
        int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
        String nombre = productoModificarView.getTxtNombre().getText().trim();
        double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText());

        if (nombre.isEmpty()) {
            productoModificarView.mostrarMensaje("producto.nombre.vacio");
            return;
        }

        if (precio <= 0) {
            productoModificarView.mostrarMensaje("producto.precio.invalido");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                productoModificarView,
                "pregunta.modificacion.producto",
                "confirmar.modificacion.producto",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        Producto productoActualizado = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(productoActualizado);
        productoModificarView.mostrarMensaje("actualizacion.producto.exito");
        productoModificarView.limpiarCampos();
        productoModificarView.getTxtNombre().setEnabled(false);
        productoModificarView.getTxtPrecio().setEnabled(false);
        productoModificarView.getBtnActualizar().setEnabled(false);
    }

    /**
     * Elimina un producto de la base de datos.
     */
    private void eliminarProducto() {
        int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoEliminarView.mostrarMensaje("producto.no.existe");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                productoEliminarView,
                "pregunta.eliminacion.producto",
                "confirmar.eliminacion.producto",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje("eliminacion.producto.exito");
            productoEliminarView.limpiarCampos();
            productoEliminarView.getTxtNombre().setText("");
            productoEliminarView.getTxtPrecio().setText("");
            productoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    /**
     * Lista todos los productos disponibles.
     */
    private void listarProductos() {
        actualizarVistas();
    }

    /**
     * Actualiza las vistas con la lista completa de productos.
     */
    private void actualizarVistas() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    /**
     * Busca un producto por código para añadirlo al carrito.
     */
    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        Locale locale = carritoAnadirView.getMensaje().getLocale();
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("producto.no.encontrado");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), locale));
        }
    }
}