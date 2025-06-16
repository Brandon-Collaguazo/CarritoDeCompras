package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;
import ec.edu.ups.vista.ProductoModificarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO, ProductoListaView productoListaView,
                              ProductoAnadirView productoView, ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView) {
        this.productoDAO = productoDAO;
        this.productoListaView = productoListaView;
        this.productoAnadirView = productoView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoListaView.cargarDatos(productoDAO.listarTodos());
            }
        });
        productoEliminarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoEliminarView.mostrarProducto(productoDAO.listarTodos());
            }
        });
        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        productoModificarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoModificarView.cargarProductos(productoDAO.listarTodos());
            }
        });
        productoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        productoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());
        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
    }

    private void buscarProducto() {
        int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if(producto != null) {
            productoModificarView.getTxtNombre().setText(producto.getNombre());
            productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            productoModificarView.getTxtNombre().setEnabled(true);
            productoModificarView.getTxtPrecio().setEnabled(true);
            productoModificarView.getBtnActualizar().setEnabled(true);
        } else {
            productoModificarView.mostrarMensaje("Producto no encontrado");
            productoModificarView.limpiarCampos();
        }
    }

    private void modificarProducto() {
        int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
        String nombre = productoModificarView.getTxtNombre().getText().trim();
        double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText());
        if (nombre.isEmpty()) {
            productoModificarView.mostrarMensaje("El nombre no puede estar vacío");
            return;
        }
        if (precio <= 0) {
            productoModificarView.mostrarMensaje("El precio debe ser mayor a 0");
            return;
        }
        Producto productoActualizado = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(productoActualizado);
        productoModificarView.mostrarMensaje("Producto actualizado correctamente");
        productoModificarView.cargarProductos(productoDAO.listarTodos());
        productoModificarView.limpiarCampos();
        productoModificarView.getTxtNombre().setEnabled(false);
        productoModificarView.getTxtPrecio().setEnabled(false);
        productoModificarView.getBtnActualizar().setEnabled(false);
    }

    private void eliminarProducto() {
        int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEliminarView.mostrarMensaje("No existe un producto con código " + codigo);
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                productoEliminarView,
                "¿Seguro que quieres eliminar '" + producto.getNombre() + "'?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje("Producto eliminado");
            productoEliminarView.limpiarCampos();
            productoEliminarView.mostrarProducto(productoDAO.listarTodos());
        }
    }

    private void listarProductos() {
        actualizarVistas();
    }

    private void actualizarVistas() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
        productoEliminarView.mostrarProducto(productos);
        productoModificarView.cargarProductos(productos);
    }

}
