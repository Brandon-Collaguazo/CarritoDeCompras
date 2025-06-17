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

    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoEliminarView productoEliminarView;
    private ProductoModificarView productoModificarView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public ProductoAnadirView getProductoAnadirView() {
        return productoAnadirView;
    }

    public void setProductoAnadirView(ProductoAnadirView productoAnadirView) {
        this.productoAnadirView = productoAnadirView;
    }

    public ProductoListaView getProductoListaView() {
        return productoListaView;
    }

    public void setProductoListaView(ProductoListaView productoListaView) {
        this.productoListaView = productoListaView;
    }

    public ProductoEliminarView getProductoEliminarView() {
        return productoEliminarView;
    }

    public void setProductoEliminarView(ProductoEliminarView productoEliminarView) {
        this.productoEliminarView = productoEliminarView;
    }

    public ProductoModificarView getProductoModificarView() {
        return productoModificarView;
    }

    public void setProductoModificarView(ProductoModificarView productoModificarView) {
        this.productoModificarView = productoModificarView;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public void configurarEventosAnadir() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    public void configurarEventosLista() {
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoListaView.cargarDatos(productoDAO.listarTodos());
            }
        });
    }

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

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());
        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
    }

    private void buscarProductoEliminar() {
        int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            productoEliminarView.getBtnEliminar().setEnabled(true);
        } else {
            productoEliminarView.mostrarMensaje("Producto no encontrado");
            productoEliminarView.getTxtNombre().setText("");
            productoEliminarView.getTxtPrecio().setText("");
            productoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    private void buscarProductoModificar() {
        // Corrección: Usar productoModificarView en lugar de productoEliminarView
        int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoModificarView.getTxtNombre().setText(producto.getNombre());
            productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            productoModificarView.getBtnActualizar().setEnabled(true);
        } else {
            productoModificarView.mostrarMensaje("Producto no encontrado");
            productoModificarView.getTxtNombre().setText("");
            productoModificarView.getTxtPrecio().setText("");
            productoModificarView.getBtnActualizar().setEnabled(false);
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

        int respuesta = JOptionPane.showConfirmDialog(
                productoModificarView,
                "¿Confirmas la modificación del producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        Producto productoActualizado = new Producto(codigo, nombre, precio);
        productoDAO.actualizar(productoActualizado);
        productoModificarView.mostrarMensaje("Producto actualizado correctamente");
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
            productoEliminarView.getTxtNombre().setText("");
            productoEliminarView.getTxtPrecio().setText("");
            productoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    private void listarProductos() {
        actualizarVistas();
    }

    private void actualizarVistas() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }
}