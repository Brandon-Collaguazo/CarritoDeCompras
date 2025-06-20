package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoActualizarView;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.CarritoBuscarView;
import ec.edu.ups.vista.CarritoEliminarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoBuscarView carritoBuscarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoActualizarView carritoActualizarView;
    private Carrito carrito;
    private int productoSeleccionado = -1;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoBuscarView carritoBuscarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoActualizarView carritoActualizarView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoBuscarView = carritoBuscarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoActualizarView = carritoActualizarView;
        this.carrito = new Carrito();
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {

        //Eventos para la ventana "CarritoAnadirView"
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarDatos();
            }
        });

        //Eventos en "CarritoBuscarView"
        carritoBuscarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoPorCodigo();
            }
        });
        carritoBuscarView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        //Eventos en "CarritoEliminarView"
        carritoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoParaEliminar();
            }
        });
        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarrito();
            }
        });

        //Evento en "CarritoActualizarView"
        carritoActualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoModificar();
            }
        });
        carritoActualizarView.getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoModificar();
            }
        });
        carritoActualizarView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAlCarrito();
            }
        });
        carritoActualizarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoDelCarrito();
            }
        });
        carritoActualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProductoEnCarrito();
            }
        });
        carritoActualizarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
    }

    //Métodos para la ventana "CarritoAnadirView"
    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente");
        System.out.println(carritoDAO.listarTodos());
    }

    private void anadirProducto() {

        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();

    }

    private void cargarProductos(){
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProducto().getModel();
        modelo.setNumRows(0);

        for (ItemCarrito item : items) {
            double subtotal = item.getProducto().getPrecio() * item.getCantidad();
            double iva = subtotal * carrito.getIVA();
            double total = subtotal + iva;

            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    String.format("$%,.2f", item.getProducto().getPrecio()),
                    item.getCantidad(),
                    String.format("$%,.2f", subtotal),
                    String.format("$%,.2f", iva),
                    String.format("$%,.2f", total)
            });
        }
    }

    private void mostrarTotales(){
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();

        carritoAnadirView.getTxtSubtotal().setText(String.format("$%,.2f", subtotal));
        carritoAnadirView.getTxtIva().setText(String.format("$%,.2f", iva));
        carritoAnadirView.getTxtTotal().setText(String.format("$%,.2f", total));
    }

    private void limpiarDatos() {
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("$0.00");
        carritoAnadirView.getCbxCantidad().setSelectedIndex(0);
        carritoAnadirView.getTxtSubtotal().setText("$0.00");
        carritoAnadirView.getTxtIva().setText("$0.00");
        carritoAnadirView.getTxtTotal().setText("$0.00");
    }

    //Métodos para la ventana "CarritoBuscarView"
    private void buscarCarritoPorCodigo() {
        int codigo = Integer.parseInt(carritoBuscarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if(carrito == null) {
            carritoBuscarView.mostrarMensaje("No se encontró el carrito");
            limpiarCampos();
        } else {
            mostrarProductosEnCarrito(carrito);
            carritoBuscarView.getTxtSubtotal().setText(String.format("$%,.2f", carrito.calcularSubtotal()));
            carritoBuscarView.getTxtIva().setText(String.format("$%,.2f", carrito.calcularIVA()));
            carritoBuscarView.getTxtTotal().setText(String.format("$%,.2f", carrito.calcularTotal()));
        }
    }

    private void mostrarProductosEnCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);

        for(ItemCarrito item : carrito.obtenerItems()) {
            Producto producto = item.getProducto();
            double subtotal = producto.getPrecio() * item.getCantidad();
            double iva = subtotal * carrito.calcularIVA();
            double total = subtotal + iva;

            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    String.format("$%,.2f", producto.getPrecio()),
                    item.getCantidad(),
                    String.format("$%,.2f", subtotal),
                    String.format("$%,.2f", iva),
                    String.format("$%,.2f", total)
            });

        }
    }

    private void limpiarCampos() {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoBuscarView.getTxtCodigo().setText("");
        carritoBuscarView.getTxtSubtotal().setText("");
        carritoBuscarView.getTxtIva().setText("");
        carritoBuscarView.getTxtTotal().setText("");
    }

    //Métodos para la ventana "CarritoEliminarView"
    private void buscarCarritoParaEliminar() {
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if(carrito == null) {
            carritoEliminarView.mostrarMensaje("Carrito no encontrado");
            limpiarCamposEliminar();
        } else {
            mostrarDetallesCarrito(carrito);
            carritoEliminarView.getBtnEliminar().setEnabled(true);
        }
    }
    private void eliminarCarrito() {
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if(carrito == null) {
            carritoEliminarView.mostrarMensaje("El carrito ya fue eliminado o no existe");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(carritoEliminarView,
                "¿Está seguro de eliminar el carrito #" + codigo + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );
        if(confirmacion == JOptionPane.YES_NO_OPTION) {
            carritoDAO.eliminar(codigo);
            carritoEliminarView.mostrarMensaje("Carrito eliminado correctamente");
            limpiarCamposEliminar();
        }
    }

    private void mostrarDetallesCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        for(ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    String.format("$%,.2f", item.getProducto().getPrecio()),
                    item.getCantidad(),
                    String.format("$%,.2f", item.getProducto().getPrecio() * item.getCantidad())
            });
        }
        carritoEliminarView.getTxtSubtotal().setText(String.format("$%,.2f", carrito.calcularSubtotal()));
        carritoEliminarView.getTxtIva().setText(String.format("$%,.2f", carrito.calcularIVA()));
        carritoEliminarView.getTxtTotal().setText(String.format("$%,.2f", carrito.calcularTotal()));
    }

    private void limpiarCamposEliminar() {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoEliminarView.getTxtCodigo().setText("");
        carritoEliminarView.getTxtSubtotal().setText("");
        carritoEliminarView.getTxtIva().setText("");
        carritoEliminarView.getTxtTotal().setText("");
        carritoEliminarView.getBtnEliminar().setEnabled(false);
    }

    //Métodos para la ventana "CarritoActualizarView"
    private void buscarCarritoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);

        if (carrito != null) {
            carritoActualizarView.getBtnBuscarProducto().setEnabled(true);
            carritoActualizarView.getBtnGuardar().setEnabled(true);
            mostrarProductosEnTabla();
            actualizarTotales();
            carritoActualizarView.mostrarMensaje("Carrito encontrado");
        } else {
            carritoActualizarView.getBtnBuscarProducto().setEnabled(false);
            carritoActualizarView.getBtnGuardar().setEnabled(false);
            limpiarEstadoProducto();
            carritoActualizarView.mostrarMensaje("Carrito no existe");
        }
    }

    private void buscarProductoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoProducto().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            carritoActualizarView.getTxtNombre().setText(producto.getNombre());
            carritoActualizarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));

            boolean existeEnCarrito = carrito.contieneProducto(codigo);
            carritoActualizarView.getBtnAnadir().setEnabled(!existeEnCarrito);
            carritoActualizarView.getBtnEliminar().setEnabled(existeEnCarrito);
            carritoActualizarView.getBtnActualizar().setEnabled(existeEnCarrito);

            if (existeEnCarrito) {
                ItemCarrito item = carrito.buscarItemPorCodigo(codigo);
                carritoActualizarView.getCbxCantidad().setSelectedItem(item.getCantidad());
            } else {
                carritoActualizarView.getCbxCantidad().setSelectedItem(1);
            }
        } else {
            carritoActualizarView.mostrarMensaje("Producto no encontrado");
            limpiarEstadoProducto();
        }
    }

    private void agregarProductoAlCarrito() {
        int codigoProducto = Integer.parseInt(carritoActualizarView.getTxtCodigoProducto().getText());
        int cantidad = (Integer) carritoActualizarView.getCbxCantidad().getSelectedItem();

        Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
        carrito.agregarProducto(producto, cantidad);
        actualizarVista();
    }

    private void eliminarProductoDelCarrito() {
        int codigoProducto = Integer.parseInt(carritoActualizarView.getTxtCodigoProducto().getText());
        carrito.eliminarProducto(codigoProducto);
        actualizarVista();
    }

    private void actualizarProductoEnCarrito() {
        String codigoTexto = carritoActualizarView.getTxtCodigoProducto().getText();
        int codigoProducto = Integer.parseInt(codigoTexto);

        String nuevoNombre = carritoActualizarView.getTxtNombre().getText();
        String precioTexto = carritoActualizarView.getTxtPrecio().getText().replace("$", "").trim();
        double nuevoPrecio = Double.parseDouble(precioTexto);

        Object cantidadSeleccionada = carritoActualizarView.getCbxCantidad().getSelectedItem();
        int nuevaCantidad = Integer.parseInt(cantidadSeleccionada.toString());

        ItemCarrito item = carrito.buscarItemPorCodigo(codigoProducto);
        if (item != null) {
            Producto producto = item.getProducto();
            producto.setNombre(nuevoNombre);
            producto.setPrecio(nuevoPrecio);
            item.setCantidad(nuevaCantidad);

            mostrarProductosEnTabla();
            actualizarTotales();
            carritoActualizarView.mostrarMensaje("Producto actualizado");
        }
    }

    private void actualizarVista() {
        mostrarProductosEnTabla();
        actualizarTotales();
        limpiarEstadoProducto();
    }

    private void guardarCambios() {
        carritoDAO.actualizar(carrito);
        carritoActualizarView.mostrarMensaje("Cambios guardados");
    }

    private void limpiarEstadoProducto() {
        carritoActualizarView.getTxtCodigoProducto().setText("");
        carritoActualizarView.getTxtNombre().setText("");
        carritoActualizarView.getTxtPrecio().setText("");
        carritoActualizarView.getBtnAnadir().setEnabled(false);
        carritoActualizarView.getBtnEliminar().setEnabled(false);
        carritoActualizarView.getBtnActualizar().setEnabled(false);
    }

    private void mostrarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) carritoActualizarView.getTblProducto().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void actualizarTotales() {
        carritoActualizarView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoActualizarView.getTxtIva().setText(String.valueOf(carrito.calcularIVA()));
        carritoActualizarView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }
}