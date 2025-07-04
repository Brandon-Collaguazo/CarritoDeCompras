package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.utils.FormateadorUtils;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.carrito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoBuscarView carritoBuscarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoActualizarView carritoActualizarView;
    private final CarritoListaView carritoListaView;
    private final CarritoDetalleView carritoDetalleView;
    private Carrito carrito;
    private Usuario usuarioAutenticado;
    private MensajeInternacionalizacionHandler mensaje;
    private int productoSeleccionado = -1;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoBuscarView carritoBuscarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoActualizarView carritoActualizarView,
                             CarritoListaView carritoListaView,
                             CarritoDetalleView carritoDetalleView,
                             Usuario usuarioAutenticado,
                             MensajeInternacionalizacionHandler mensaje) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoBuscarView = carritoBuscarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoActualizarView = carritoActualizarView;
        this.carritoListaView = carritoListaView;
        this.carritoDetalleView = carritoDetalleView;
        this.carrito = new Carrito(usuarioAutenticado);
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensaje = mensaje;
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
        carritoActualizarView.getBtnBuscarC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoModificar();
            }
        });

        carritoActualizarView.getBtnBuscarP().addActionListener(new ActionListener() {
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

        carritoActualizarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        carritoActualizarView.setModificarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                modificarProductoEnCarrito(fila);
            }
        });

        carritoActualizarView.setEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                eliminarProductoDelCarrito(fila);
            }
        });

        //Evento en "CarritoListaView"
        carritoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritos();
            }
        });

        carritoListaView.getBtnDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(carritoListaView.getTblCarrito().getRowCount() == 0) {
                    carritoListaView.mostrarMensaje("primero.busque.carritos");
                    return;
                }
                mostrarDetallesCarrito();
            }
        });
    }

    //Métodos para la ventana "CarritoAnadirView"
    private void guardarCarrito() {
        if(this.carrito.getUsuario() == null) {
            this.carrito.setUsuario(this.usuarioAutenticado);
        }
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
        Locale locale = carritoAnadirView.getMensaje().getLocale();
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
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(subtotal, locale),
                    FormateadorUtils.formatearMoneda(iva, locale),
                    FormateadorUtils.formatearMoneda(total, locale)
            });
        }
    }

    private void mostrarTotales(){
        Locale locale = carritoAnadirView.getMensaje().getLocale();
        String subtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale);
        String iva = FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale);
        String total = FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale);

        System.out.println(FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale));

        carritoAnadirView.getTxtSubtotal().setText(subtotal);
        carritoAnadirView.getTxtIva().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);
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
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoC().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);

        if (carrito == null) {
            carritoActualizarView.mostrarMensaje("Carrito no encontrado");
            carritoActualizarView.limpiarTabla();
        } else {
            cargarProductosEnTabla();
            actualizarTotales();
        }
    }

    private void buscarProductoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoP().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            carritoActualizarView.mostrarMensaje("Producto no encontrado");
        } else {
            productoSeleccionado = codigo;
        }
    }

    private void agregarProductoAlCarrito() {
        Producto producto = productoDAO.buscarPorCodigo(productoSeleccionado);
        carrito.agregarProducto(producto, 1);
        cargarProductosEnTabla();
        actualizarTotales();
        productoSeleccionado = -1;
        carritoActualizarView.getTxtCodigoP().setText("");
    }

    private void modificarProductoEnCarrito(int fila) {
        int nuevaCantidad = carritoActualizarView.getCantidadEnFila(fila);
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.modificarCantidadProducto(codigo, nuevaCantidad);
        actualizarTotales();
    }

    private void eliminarProductoDelCarrito(int fila) {
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.eliminarProducto(codigo);
        cargarProductosEnTabla();
        actualizarTotales();
    }

    private void guardarCambios() {
        carritoDAO.actualizar(carrito);
        carritoActualizarView.mostrarMensaje("Carrito actualizado correctamente");
        carritoActualizarView.limpiarTabla();
        carritoActualizarView.getTxtCodigoC().setText("");
        carritoActualizarView.getTxtCodigoP().setText("");
        productoSeleccionado = -1;
    }

    private void cargarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) carritoActualizarView.getTblProducto().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad(),
                    ""
            });
        }
    }

    private void actualizarTotales() {
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();

        carritoActualizarView.actualizarTotales(subtotal, iva, total);
    }

    //Métodos para la ventana "CarritoListaView"
    private void buscarCarritos() {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);

        if(usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            String username = carritoListaView.getTxtCodigo().getText().trim();
            if(!username.isEmpty()) {
                cargarCarritosUsuario(username);
            } else {
                cargarCarritos();
            }
        } else {
            cargarCarritosUsuario(usuarioAutenticado.getUsername());
        }
    }

    private void cargarCarritosUsuario(String username) {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);

        List<Carrito> carritos = carritoDAO.listarPorUsuario(username);
        Locale locale = carritoListaView.getMensaje().getLocale();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(carritos.isEmpty()) {
            carritoListaView.mostrarMensaje("usuario.sin.carritos");
            return;
        }

        for(Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    dateFormat.format(carrito.getFechaCreacion()),
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    carrito.getItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
        }
    }

    private void cargarCarritos() {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);

        List<Carrito> carritos = carritoDAO.listarTodos();
        Locale locale = carritoListaView.getMensaje().getLocale();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(carritos.isEmpty()) {
            carritoListaView.mostrarMensaje("no.hay.carritos");
            return;
        }

        for(Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    dateFormat.format(carrito.getFechaCreacion()),
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    carrito.getItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
        }
    }

    private void mostrarDetallesCarrito() {
        int filaSeleccionada = carritoListaView.getTblCarrito().getSelectedRow();

        if(filaSeleccionada == -1) {
            carritoListaView.mostrarMensaje("seleccionar.fila");
            return;
        }

        int codigo = (int) carritoListaView.getModelo().getValueAt(filaSeleccionada, 1);

        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
        if(carrito == null) {
            carritoListaView.mostrarMensaje("carrito.no.encontrado");
            return;
        }

        mostrarVentanaDetalla(carrito);
    }

    private void mostrarVentanaDetalla(Carrito carrito) {
        CarritoDetalleView carritoDetalleView = new CarritoDetalleView(carritoListaView.getMensaje());

        //Llenar campos básicos
        Locale locale = carritoDetalleView.getMensaje().getLocale();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        carritoDetalleView.getTxtCodigo().setText(String.valueOf(carrito.getCodigo()));
        carritoDetalleView.getTxtUsuario().setText(carrito.getUsuario().getUsername());
        carritoDetalleView.getTxtFecha().setText(dateFormat.format(carrito.getFechaCreacion()));

        //Calcular valores y mostrarlos
        double subtotal = carrito.calcularSubtotal();
        double iva = subtotal * carrito.getIVA();
        double total = subtotal + iva;

        carritoDetalleView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        carritoDetalleView.getTxtIva().setText(FormateadorUtils.formatearMoneda(iva, locale));
        carritoDetalleView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(total, locale));

        //Cargar productos (items en la tabla)
        DefaultTableModel modelo = carritoDetalleView.getModelo();
        modelo.setRowCount(0);
        for(ItemCarrito itemCarrito : carrito.getItems()) {
            modelo.addRow(new Object[]{
                    itemCarrito.getProducto().getCodigo(),
                    itemCarrito.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(itemCarrito.getProducto().getPrecio(), locale),
                    itemCarrito.getCantidad(),
                    FormateadorUtils.formatearMoneda(itemCarrito.getSubtotal(), locale)
            });
        }
        carritoDetalleView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        carritoDetalleView.pack();
        carritoDetalleView.setLocationRelativeTo(carritoListaView);
        cargarCarritos();
        carritoDetalleView.setVisible(true);

    }
}