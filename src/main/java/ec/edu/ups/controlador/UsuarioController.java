
package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.FormateadorUtils;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsuarioController {

    private Usuario usuario;
    private MensajeInternacionalizacionHandler mensaje;
    private final UsuarioDAO usuarioDAO;
    private final CarritoDAO carritoDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioListaView usuarioListaView;
    private final UsuarioModificarView usuarioModificarView;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             CarritoDAO carritoDAO,
                             LoginView loginView,
                             UsuarioRegistroView usuarioRegistroView,
                             UsuarioEliminarView usuarioEliminarView,
                             UsuarioListaView usuarioListaView,
                             UsuarioModificarView usuarioModificarView) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.mensaje = mensaje;
        this.usuarioRegistroView = usuarioRegistroView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioListaView = usuarioListaView;
        this.usuarioModificarView = usuarioModificarView;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas(){
        //EVENTOS EN "LOGINVIEW"
        loginView.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioRegistroView.limpiarCampos();
                usuarioRegistroView.setVisible(true);
            }
        });
        //EVENTOS DE REGISTRAR
        usuarioRegistroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }


    //Métodos en "LOGINVIEW"
    private void autenticar(){
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        }else{
            loginView.dispose();
        }

        loginView.limpiarCampos();
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    //Métodos en "USUARIOREGISTROVIEW"
    public void registrarUsuario() {
        String nombre = usuarioRegistroView.getTxtNombre().getText();
        String fechaStr = usuarioRegistroView.getTxtFechaNacimiento().getText();
        String telefono = usuarioRegistroView.getTxtTelefono().getText();
        String correo = usuarioRegistroView.getTxtCorreo().getText();
        String username = usuarioRegistroView.getTxtUsername().getText();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());
        String confirmPassword = new String(usuarioRegistroView.getTxtConfirmarPassword().getPassword());

        if (nombre.isEmpty() || fechaStr.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || username.isEmpty() || password.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("Complete todos los campos");
            return;
        }

        if (!password.equals(confirmPassword)) {
            usuarioRegistroView.mostrarMensaje("Las contraseñas no coinciden");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioRegistroView.mostrarMensaje("Usuario ya registrado");
            return;
        }

        Date fechaNacimiento = null;
        try {
            fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Usuario nuevoUsuario = new Usuario(
                nombre,
                fechaNacimiento,
                telefono,
                correo,
                username,
                password,
                Rol.USUARIO
        );

        usuarioDAO.crear(nuevoUsuario);
        String fechaFormateada = FormateadorUtils.formatearFecha(fechaNacimiento, Locale.getDefault());
        usuarioRegistroView.mostrarMensaje("Usuario registrado (" + fechaFormateada + ")");
        usuarioRegistroView.limpiarCampos();
    }


    //Métodos de la ventana "USUARIOELIMINARVIEW"
    private void buscarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();

        if(username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("ingrese.username");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if(usuario != null) {
            int numCarritos = contarCarristosUsuario(usuario);
            usuarioEliminarView.getTxtAsociado().setText(String.valueOf(numCarritos));

            usuarioEliminarView.getBtnEliminar().setEnabled(numCarritos == 0);
        } else {
            limpiarCamposEliminar();
            usuarioEliminarView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private int contarCarristosUsuario(Usuario usuario) {
        if(usuario == null) {
            return  0;
        }
        List<Carrito> carritos = carritoDAO.listarTodos();
        int contador = 0;

        for(Carrito carrito : carritos) {
            if(carrito != null &&
                    carrito.getUsuario() != null &&
                    carrito.getUsuario().getUsername() != null &&
                    carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                contador++;
            }
        }
        return contador;
    }

    private void limpiarCamposEliminar() {
        usuarioEliminarView.getTxtUsuario().setText("");
        usuarioEliminarView.getTxtAsociado().setText("0");
        usuarioEliminarView.getBtnEliminar().setEnabled(false);
    }

    //Métodos de la ventana "USUARIOLISTAVIEW"
    private void buscarUsuarioLista() {
        String username = usuarioListaView.getTxtUsuario().getText().trim();
        List<Usuario> usuarios;

        if(username.isEmpty()) {
            usuarios = usuarioDAO.listarTodos();
        }
        else {
            usuarios = new ArrayList<>();
            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if(usuario != null) {
                usuarios.add(usuario);
            }
        }

        cargarUsuariosEnLaTabla(usuarios);
        if(usuarios.isEmpty()) {
            usuarioListaView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private void cargarUsuariosEnLaTabla(List<Usuario> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) usuarioListaView.getTblDetalle().getModel();
        modelo.setRowCount(0);
        Locale locale = usuarioListaView.getMensaje().getLocale();

        for (Usuario usuario : usuarios) {
            List<Carrito> carritos = carritoDAO.listarPorUsuario(usuario.getUsername());
            int numCarritos = carritos.size();
            double total = calcularTotalCarritos(carritos);

            modelo.addRow(new Object[]{
                    usuario.getUsername(),
                    numCarritos,
                    FormateadorUtils.formatearMoneda(total, locale)
            });
        }
    }

    private double calcularTotalCarritos(List<Carrito> carritos) {
        double total = 0.0;
        for(Carrito carrito : carritos) {
            if(carrito != null) {
                total += carrito.calcularTotal();
            }
        }
        return total;
    }

    private void limpiarCamposLista() {
        usuarioListaView.getTxtUsuario().setText("");
        DefaultTableModel modelo = (DefaultTableModel) usuarioListaView.getTblDetalle().getModel();
        modelo.setRowCount(0);
    }

    //Métodos en la ventana "USUARIOMODIFICARVIEW"
    private void cargarDatosModificar() {
        this.usuario = obtenerUsuarioActual();

        if(usuario != null) {
            usuarioModificarView.getTxtUsuario().setText(usuario.getUsername());
        }
    }

    private void mostrarContrasenia() {
        JPasswordField contrasenia = usuarioModificarView.getTxtContrasenia();
        JPasswordField confirmacion = usuarioModificarView.getTxtConfirmar();

        if(contrasenia.getEchoChar() == '*') {
            contrasenia.setEchoChar((char) 0);
            confirmacion.setEchoChar((char) 0);
            usuarioModificarView.getBtnMostrar().setText(mensaje.get("ocultar"));
        } else {
            contrasenia.setEchoChar('*');
            confirmacion.setEchoChar('*');
            usuarioModificarView.getBtnMostrar().setText(mensaje.get("mostrar"));
        }
    }

    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();
        String contrasenia = new String(usuarioModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(usuarioModificarView.getTxtConfirmar().getPassword());

        if(username.isEmpty()) {
            usuarioModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        if(!contrasenia.equals(confirmacion)) {
            usuarioModificarView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if(contrasenia.isEmpty()) {
            contrasenia = usuario.getContrasenia();
        } else if(!validarContrasenia(contrasenia)) {
            usuarioModificarView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        usuario.setUsername(username);
        usuario.setContrasenia(contrasenia);

        if(usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("usuario.actualizado");
            usuarioModificarView.dispose();
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }

    private boolean validarContrasenia(String contrasenia) {
        return contrasenia.length() >= 5;
    }
}
