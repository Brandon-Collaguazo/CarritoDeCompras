package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.utils.FormateadorUtils;
import ec.edu.ups.vista.carrito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
    private int productoSeleccionado = -1;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoBuscarView carritoBuscarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoActualizarView carritoActualizarView,
                             CarritoListaView carritoListaView,
                             CarritoDetalleView carritoDetalleView,
                             Usuario usuarioAutenticado) {
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
        configurarEventosEnVistas();
    }

    public void configurarEventosEnVistas() {

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
                    carritoListaView.mostrarMensaje("busque.carritos");
                    return;
                }
                mostrarDetallesCarrito();
            }
        });
    }

    //Métodos para la ventana "CarritoAnadirView"
    private void guardarCarrito() {
        if (this.carrito.getUsuario() == null) {
            this.carrito.setUsuario(this.usuarioAutenticado);
        }
        if (carritoDAO.buscarPorCodigo(carrito.getCodigo()) != null) {
            carritoAnadirView.mostrarMensaje("carrito.ya.existe");
            return;
        }
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("creacion.carrito.exito");
    }

    private void anadirProducto() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if(producto == null) {
            carritoAnadirView.mostrarMensaje("producto.no.encontrado");
            return;
        }

        int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        boolean productoExiste = false;
        for(ItemCarrito item : carrito.obtenerItems()) {
            if(item.getProducto().getCodigo() == codigo) {
                item.setCantidad(item.getCantidad() + cantidad);
                productoExiste = true;
                break;
            }
        }

        if(!productoExiste) {
            carrito.agregarProducto(producto, cantidad);
        }

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
        Locale locale = carritoAnadirView.getMensaje().getLocale();
        String valorCero = FormateadorUtils.formatearMoneda(0.0, locale);

        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText(valorCero);
        carritoAnadirView.getCbxCantidad().setSelectedIndex(0);
        carritoAnadirView.getTxtSubtotal().setText(valorCero);
        carritoAnadirView.getTxtIva().setText(valorCero);
        carritoAnadirView.getTxtTotal().setText(valorCero);
    }

    //Métodos para la ventana "CarritoBuscarView"
    private void buscarCarritoPorCodigo() {
        int codigo = Integer.parseInt(carritoBuscarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        Locale locale = carritoBuscarView.getMensaje().getLocale();

        if(carrito == null) {
            carritoBuscarView.mostrarMensaje("carrito.no.encontrado");
            limpiarCampos();
        } else {
            mostrarProductosEnCarrito(carrito);
            carritoBuscarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
            carritoBuscarView.getTxtIva().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
            carritoBuscarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
        }
    }

    private void mostrarProductosEnCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        Locale locale = carritoBuscarView.getMensaje().getLocale();
        Set<Integer> productosAgregados = new HashSet<>();

        for(ItemCarrito item : carrito.obtenerItems()) {
            if(!productosAgregados.contains(item.getProducto().getCodigo())) {
                Producto producto = item.getProducto();
                double subtotal = producto.getPrecio() * item.getCantidad();
                double iva = carrito.calcularIVA();
                double total = subtotal + iva;

                modelo.addRow(new Object[]{
                        producto.getCodigo(),
                        producto.getNombre(),
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(subtotal, locale),
                        FormateadorUtils.formatearMoneda(iva, locale),
                        FormateadorUtils.formatearMoneda(total, locale)
                });
                productosAgregados.add(item.getProducto().getCodigo());
            }
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
            carritoEliminarView.mostrarMensaje("carrito.no.encontrado");
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
            carritoEliminarView.mostrarMensaje("carrito.inexistente");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(carritoEliminarView,
                "pregunta.eliminar.carrito",
                "confirmar.eliminacion.carrito",
                JOptionPane.YES_NO_OPTION
        );
        if(confirmacion == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(codigo);
            carritoEliminarView.mostrarMensaje("eliminacion.carrito.exito");
            limpiarCamposEliminar();
        }
    }

    private void mostrarDetallesCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        Locale locale = carritoEliminarView.getMensaje().getLocale();

        for(ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale)
            });
        }
        carritoEliminarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
        carritoEliminarView.getTxtIva().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoEliminarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
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
            carritoActualizarView.mostrarMensaje("carrito.no.encontrado");
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
            carritoActualizarView.mostrarMensaje("producto.no.encontrado");
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
        carritoActualizarView.mostrarMensaje("actualizacion.carrito.exito");
        carritoActualizarView.limpiarTabla();
        carritoActualizarView.getTxtCodigoC().setText("");
        carritoActualizarView.getTxtCodigoP().setText("");
        productoSeleccionado = -1;
    }

    private void cargarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) carritoActualizarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        Locale locale = carritoActualizarView.getMensaje().getLocale();

        for (ItemCarrito item : carrito.obtenerItems()) {
            boolean productoYaExiste = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int codigoEnTabla = (int) modelo.getValueAt(i, 0);
                if (codigoEnTabla == item.getProducto().getCodigo()) {
                    productoYaExiste = true;
                    int cantidadActual = (int) modelo.getValueAt(i, 3);
                    modelo.setValueAt(cantidadActual + item.getCantidad(), i, 3);
                    break;
                }
            }

            if (!productoYaExiste) {
                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale),
                        ""
                });
            }
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
        List<Carrito> carritos;

        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            String usernameFiltro = carritoListaView.getTxtCodigo().getText().trim();
            if (usernameFiltro.isEmpty()) {
                carritos = carritoDAO.listarTodos();
            } else {
                carritos = carritoDAO.listarPorUsuario(usernameFiltro);
            }
        } else {
            carritos = carritoDAO.listarPorUsuario(usuarioAutenticado.getUsername());
        }

        if (carritos.isEmpty()) {
            if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                carritoListaView.mostrarMensaje("no.hay.carritos");
            } else {
                carritoListaView.mostrarMensaje("usuario.sin.carritos");
            }
            return;
        }

        Locale locale = carritoListaView.getMensaje().getLocale();
        for (Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale),
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
        // Llenar campos básicos
        Locale locale = carritoDetalleView.getMensaje().getLocale();

        carritoDetalleView.getTxtCodigo().setText(String.valueOf(carrito.getCodigo()));
        carritoDetalleView.getTxtUsuario().setText(carrito.getUsuario().getUsername());

        Date fecha = carrito.getFechaCreacion().getTime();
        carritoDetalleView.getTxtFecha().setText(FormateadorUtils.formatearFecha(fecha, locale));

        // Calcular valores y mostrarlos
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = subtotal + iva;

        carritoDetalleView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        carritoDetalleView.getTxtIva().setText(FormateadorUtils.formatearMoneda(iva, locale));
        carritoDetalleView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(total, locale));

        // Cargar productos (items en la tabla)
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
        carritoDetalleView.setVisible(true);
    }
}