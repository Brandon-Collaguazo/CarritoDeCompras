
package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.PreguntaSeguridad;
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
    private final PreguntaSeguridadDAO preguntaDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioListaView usuarioListaView;
    private final UsuarioModificarView usuarioModificarView;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             CarritoDAO carritoDAO,
                             LoginView loginView,
                             PreguntaSeguridadDAO preguntaDAO,
                             UsuarioRegistroView usuarioRegistroView,
                             UsuarioEliminarView usuarioEliminarView,
                             UsuarioListaView usuarioListaView,
                             UsuarioModificarView usuarioModificarView) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.preguntaDAO = preguntaDAO;
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

        //EVENTOS EN "USUARIOELIMINARVIEW"
        usuarioEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });

        usuarioEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        //EVENTOS EN "USUARIOLISTAVIEW"
        usuarioListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioLista();
            }
        });

        usuarioListaView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposLista();
            }
        });

        //EVENTOS EN "USUARIOMODIFICARVIEW"
        usuarioModificarView.getBtnMostrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarContrasenia();
            }
        });

        usuarioModificarView.getCbxModificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionCombo();
            }
        });
    }


    //Métodos en "LOGINVIEW"
    private void autenticar(){
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtPassword().getText();

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
        char[] passwordChars = usuarioRegistroView.getTxtPassword().getPassword();
        char[] confirmPasswordChars = usuarioRegistroView.getTxtConfirmarPassword().getPassword();

        String password = new String(passwordChars);
        String confirmPassword = new String(confirmPasswordChars);

        if (nombre.isEmpty() || fechaStr.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || username.isEmpty() || password.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("Todos los campos son obligatorios");
            return;
        }

        if (!password.equals(confirmPassword)) {
            usuarioRegistroView.mostrarMensaje("Las contraseñas no coinciden");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioRegistroView.mostrarMensaje("El nombre de usuario ya existe");
            return;
        }

        Date fechaNacimiento;
        try {
            fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        } catch (ParseException e) {
            usuarioRegistroView.mostrarMensaje("Formato de fecha inválido. Use dd/MM/yyyy");
            return;
        }

        List<PreguntaSeguridad> preguntasDisponibles = preguntaDAO.listarTodas();
        String[] preguntasRespuestas = usuarioRegistroView.mostrarPreguntasSeguridad(preguntasDisponibles);

        if (preguntasRespuestas == null || preguntasRespuestas.length < 6) {
            usuarioRegistroView.mostrarMensaje("Debe responder 3 preguntas de seguridad");
            return;
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
        for (int i = 0; i < preguntasRespuestas.length; i += 2) {
            nuevoUsuario.addPreguntaSeguridad(preguntasRespuestas[i], preguntasRespuestas[i+1]);
        }

        usuarioDAO.crear(nuevoUsuario);

        usuarioRegistroView.mostrarMensaje("¡Registro exitoso!");
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
            cargarDatosTabla(usuario, numCarritos);
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

    private void eliminarUsuario() {
        int filaSeleccionada = usuarioEliminarView.getTblUsuario().getSelectedRow();
        if (filaSeleccionada < 0) {
            usuarioEliminarView.mostrarMensaje("seleccione.fila");
            return;
        }

        String username = (String) usuarioEliminarView.getTblUsuario().getValueAt(filaSeleccionada, 4);
        int confirmacion = JOptionPane.showConfirmDialog(
                usuarioEliminarView,
                mensaje.get("confirmar.eliminar"),
                mensaje.get("titulo.eliminar"),
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
            modelo.removeRow(filaSeleccionada);
            usuarioEliminarView.mostrarMensaje("usuario.elimina.exito");
        }

    }

    private void cargarDatosTabla(Usuario usuario, int numCarritos) {
        DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
        modelo.setRowCount(0);

        modelo.addRow(new Object[]{
                usuario.getNombreCompleto(),
                usuario.getFechaNacimiento(),
                usuario.getTelefono(),
                usuario.getCorreo(),
                usuario.getUsername(),
                numCarritos
        });

        usuarioEliminarView.getBtnEliminar().setEnabled(numCarritos == 0);
    }

    private void limpiarCamposEliminar() {
        usuarioEliminarView.getTxtUsuario().setText("");
        usuarioEliminarView.getBtnEliminar().setEnabled(false);
        DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
        modelo.setRowCount(0);
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

        cargarDatosTablaLista(usuarios);
        if(usuarios.isEmpty()) {
            usuarioListaView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private void cargarDatosTablaLista(List<Usuario> usuarios) {
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
    }

    //Métodos en la ventana "USUARIOMODIFICARVIEW"
    public void cargarUsuario(String username) {
        usuario = usuarioDAO.buscarPorUsername(username);
        if(usuario != null) {
            usuarioModificarView.cargarDatosUsuario(usuario);
        } else {
            usuarioModificarView.mostrarMensaje("usuario.no.encontrado");
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

    private void seleccionCombo() {
        String opcion = (String) usuarioModificarView.getCbxModificar().getSelectedItem();
        boolean esContrasenia = opcion.equals(mensaje.get("modificar.contrasenia"));
        usuarioModificarView.getTxtContrasenia().setEnabled(esContrasenia);
        usuarioModificarView.getTxtConfirmar().setEnabled(esContrasenia);
        usuarioModificarView.getTxtUsuario().setEnabled(!esContrasenia);
    }

    public void guardarCambios() {
        if(usuario == null)
            return;
        String opcion = (String) usuarioModificarView.getCbxModificar().getSelectedItem();

        if(opcion.equals(mensaje.get("modificar.contrasenia"))) {
            actualizarContrasenia();
        } else {
            actualizarUsername();
        }
    }

    private void actualizarContrasenia() {
        String nuevaContra = new String(usuarioModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(usuarioModificarView.getTxtConfirmar().getPassword());

        if(!nuevaContra.equals(confirmacion)) {
            usuarioModificarView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if(nuevaContra.length() < 5) {
            usuarioModificarView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        usuario.setContrasenia(nuevaContra);
        if(usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("contrasenia.actualizada");
            usuarioModificarView.limpiarCampos();
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }

    private void actualizarUsername() {
        String nuevoUsername = usuarioModificarView.getTxtUsuario().getText().trim();

        if (nuevoUsername.isEmpty()) {
            usuarioModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        if (!nuevoUsername.equals(usuario.getUsername()) &&
                usuarioDAO.buscarPorUsername(nuevoUsername) != null) {
            usuarioModificarView.mostrarMensaje("usuario.ya.existe");
            return;
        }

        usuario.setUsername(nuevoUsername);
        if (usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("usuario.actualizado");
            usuarioModificarView.cargarDatosUsuario(usuario);
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }
}
