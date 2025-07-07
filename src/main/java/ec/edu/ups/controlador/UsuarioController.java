package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
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
    private boolean administrador;
    private MensajeInternacionalizacionHandler mensaje;
    private List<PreguntaSeguridad> preguntasSeleccionadas;
    private int pasoActual = 0;
    private String usernameEnRegistro;
    private String passwordEnRegistro;
    private final UsuarioDAO usuarioDAO;
    private final CarritoDAO carritoDAO;
    private final PreguntaSeguridadDAO preguntaDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;
    private final RecuperarContraseniaView recuperarContraseniaView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioListaView usuarioListaView;
    private final UsuarioModificarView usuarioModificarView;
    private final AdminModificarView adminModificarView;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             CarritoDAO carritoDAO,
                             LoginView loginView,
                             PreguntaSeguridadDAO preguntaDAO,
                             UsuarioRegistroView usuarioRegistroView,
                             RecuperarContraseniaView recuperarContraseniaView,
                             UsuarioEliminarView usuarioEliminarView,
                             UsuarioListaView usuarioListaView,
                             UsuarioModificarView usuarioModificarView,
                             AdminModificarView adminModificarView,
                             MensajeInternacionalizacionHandler mensaje) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.preguntaDAO = preguntaDAO;
        this.loginView = loginView;
        this.usuario = usuario;
        this.administrador = false;
        this.mensaje = mensaje;
        this.usuarioRegistroView = usuarioRegistroView;
        this.recuperarContraseniaView = recuperarContraseniaView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioListaView = usuarioListaView;
        this.usuarioModificarView = usuarioModificarView;
        this.adminModificarView = adminModificarView;

        configurarEventosEnVistas();

        if (this.usuarioModificarView != null) {
            inicializarCampos();
            seleccionCombo();
        }
        if (this.adminModificarView != null) {
            if (this.administrador) {
                inicializarCamposAdmin();
                seleccionComboAdmin();
            }
        }
    }

    private void configurarEventosEnVistas(){
        // Eventos en "LOGINVIEW"
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

        loginView.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarContraseniaView.setVisible(true);
            }
        });

        // Eventos de registro
        usuarioRegistroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completarRegistro();
            }
        });

        usuarioRegistroView.getBtnSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pasoActual == 0 && preguntasSeleccionadas == null) {
                    iniciarRegistro();
                } else {
                    procesarRegistro();
                }
            }
        });

        // Eventos en "USUARIOELIMINARVIEW"
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

        // Eventos en "USUARIOLISTAVIEW"
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

        configurarModificacionUsuarios();
    }

    private void configurarModificacionUsuarios() {
        if(usuarioModificarView != null) {
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
            // Añadir ActionListener para el botón Guardar del usuario normal
            usuarioModificarView.getBtnGuardar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guardarCambios();
                }
            });

            if(!administrador) {
                usuarioModificarView.cargarDatosUsuario(usuario);
            }
        }

        // Configuración para ADMIN
        if(adminModificarView != null && administrador) {
            adminModificarView.getBtnBuscar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buscarUsuarioAdmin();
                }
            });

            adminModificarView.getBtnMostrar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarContraseniaAdmin();
                }
            });

            adminModificarView.getCbxModificar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionComboAdmin();
                }
            });
            // Añadir ActionListener para el botón Guardar del administrador
            adminModificarView.getBtnGuardar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guardarCambiosAdmin();
                }
            });
        }
    }

    // Métodos en "LOGINVIEW"
    private void autenticar(){
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtPassword().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("datos.usuario.incorrectos");
        }else{
            this.administrador = (usuario.getRol() == Rol.ADMINISTRADOR);

            loginView.dispose();
            if (this.administrador && this.adminModificarView != null) {
                inicializarCamposAdmin();
            } else if (!this.administrador && this.usuarioModificarView != null) {
                usuarioModificarView.cargarDatosUsuario(usuario);
                seleccionCombo();
            }
        }

        loginView.limpiarCampos();
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    // Métodos en "USUARIOREGISTROVIEW"
    private void iniciarRegistro() {
        pasoActual = 0;
        preguntasSeleccionadas = preguntaDAO.obtenerPreguntasAleatorias(3);
        usuarioRegistroView.limpiarCampos();
        mostrarPreguntaSeguridad();
        usuarioRegistroView.setVisible(true);
    }

    private void procesarRegistro() {
        if(pasoActual < 3) {
            if(procesarRespuestaSeguridad()) {
                pasoActual++;

                if(pasoActual < 3) {
                    mostrarPreguntaSeguridad();
                } else {
                    usuarioRegistroView.habilitarCampos();
                }
            }
        }
    }

    private boolean validarDatos() {
        String nombre = usuarioRegistroView.getTxtNombre().getText();
        String fecha = usuarioRegistroView.getTxtFechaNacimiento().getText();
        String telefono = usuarioRegistroView.getTxtTelefono().getText();
        String correo = usuarioRegistroView.getTxtCorreo().getText();
        String username = usuarioRegistroView.getTxtUsername().getText();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());
        String confirmarPassword = new String(usuarioRegistroView.getTxtConfirmarPassword().getPassword());

        if (nombre.isEmpty() || fecha.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || username.isEmpty() || password.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("campo.usuario.obligatorio");
            return false;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioRegistroView.mostrarMensaje("usuario.existente");
            return false;
        }

        Date fechaNacimiento;
        try {
            fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException e) {
            usuarioRegistroView.mostrarMensaje("formato.fecha.incorrecto");
            return false;
        }

        if (!password.equals(confirmarPassword)) {
            usuarioRegistroView.mostrarMensaje("contrasenia.no.coinciden");
            return false;
        }

        return true;
    }

    private void mostrarPreguntaSeguridad() {
        if(pasoActual >= 0 && pasoActual < 3) {
            PreguntaSeguridad pregunta = preguntasSeleccionadas.get(pasoActual);
            usuarioRegistroView.configurarPreguntaSeguridad(pregunta, pasoActual + 1);
        }
    }

    private boolean procesarRespuestaSeguridad() {
        String respuesta = usuarioRegistroView.obtenerRespuestaSeguridad();
        if (respuesta.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("respuestas.vacias");
            return false;
        }

        PreguntaSeguridad pregunta = preguntasSeleccionadas.get(pasoActual);
        RespuestaSeguridad respuestaSeguridad = new RespuestaSeguridad(
                usernameEnRegistro,
                pregunta.getId(),
                respuesta
        );

        preguntaDAO.guardarRespuesta(respuestaSeguridad);
        return true;
    }

    private void completarRegistro() {
        String nombre = usuarioRegistroView.getTxtNombre().getText();
        String fecha = usuarioRegistroView.getTxtFechaNacimiento().getText();
        String telefono = usuarioRegistroView.getTxtTelefono().getText();
        String correo = usuarioRegistroView.getTxtCorreo().getText();
        String username = usuarioRegistroView.getTxtUsername().getText();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());

        Date fechaNacimiento;
        try {
            fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException exception) {
            usuarioRegistroView.mostrarMensaje("formato.fecha.incorrecto");
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

        try {
            usuarioDAO.crear(nuevoUsuario);
            usuarioRegistroView.mostrarMensaje("registro.exitoso");
            usuarioRegistroView.dispose();

            pasoActual = 0;
            preguntasSeleccionadas = null;
            usernameEnRegistro = null;
            passwordEnRegistro = null;
        } catch (Exception e) {
            usuarioRegistroView.mostrarMensaje("error.registro");
        }
    }

    // Métodos de la ventana "USUARIOELIMINARVIEW"
    private void buscarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("ingrese.username");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            List<Carrito> carritos = carritoDAO.listarPorUsuario(username);
            cargarDatosTabla(usuario, carritos.size());
        } else {
            limpiarCamposEliminar();
            usuarioEliminarView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private int contarCarritosUsuario(Usuario usuario) {
        if (usuario == null || usuario.getUsername() == null) {
            return 0;
        }
        return carritoDAO.listarPorUsuario(usuario.getUsername()).size();
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
                mensaje.get("pregunta.eliminar.usuario"),
                mensaje.get("confirmar.eliminacion.usuario"),
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
            modelo.removeRow(filaSeleccionada);
            usuarioEliminarView.mostrarMensaje("usuario.eliminar.exito");
        }
    }

    private void cargarDatosTabla(Usuario usuario, int numCarritos) {
        DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
        modelo.setRowCount(0);
        Locale locale = usuarioEliminarView.getMensaje().getLocale();

        modelo.addRow(new Object[]{
                usuario.getNombreCompleto(),
                FormateadorUtils.formatearFecha(usuario.getFechaNacimiento(), locale),
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

    // Métodos de la ventana "USUARIOLISTAVIEW"
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

    // Métodos en la ventana "USUARIOMODIFICARVIEW"
    private void cargarUsuario(String username, Usuario usuarioAutenticado) {
        if(usuarioAutenticado.getRol() == Rol.ADMINISTRADOR && username != null) {
            this.usuario = usuarioDAO.buscarPorUsername(username);
        } else {
            this.usuario = usuarioAutenticado;
        }

        if(this.usuario != null) {
            usuarioModificarView.cargarDatosUsuario(this.usuario);
            usuarioModificarView.getTxtUsuario().setEnabled(
                    usuarioAutenticado.getRol() == Rol.ADMINISTRADOR
            );
            seleccionCombo();
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
        if(mensaje == null) {
            JOptionPane.showMessageDialog(null, "Error");
            return;
        }
        String opcion = (String) usuarioModificarView.getCbxModificar().getSelectedItem();
        if(opcion == null) return;

        boolean esContrasenia = opcion.equals(mensaje.get("modificar.contrasenia"));

        usuarioModificarView.getTxtContrasenia().setEnabled(esContrasenia);
        usuarioModificarView.getTxtConfirmar().setEnabled(esContrasenia);
        usuarioModificarView.getTxtUsuario().setEnabled(!esContrasenia);

        usuarioModificarView.revalidate();
        usuarioModificarView.repaint();
    }

    public void guardarCambios() {
        if(usuario == null) return;

        String opcion = (String) usuarioModificarView.getCbxModificar().getSelectedItem();
        if(opcion == null) return;

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
        if(nuevoUsername.isEmpty()) {
            usuarioModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        if(!nuevoUsername.equals(usuario.getUsername()) &&
                usuarioDAO.buscarPorUsername(nuevoUsername) != null) {
            usuarioModificarView.mostrarMensaje("usuario.ya.existe");
            return;
        }

        usuario.setUsername(nuevoUsername);
        if(usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("usuario.actualizado");
            usuarioModificarView.cargarDatosUsuario(usuario);
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }

    // Métodos para "ADMIN"
    private void buscarUsuarioAdmin() {
        String username = adminModificarView.getTxtUsuario().getText();

        if (username.isEmpty()) {
            adminModificarView.mostrarMensaje(mensaje.get("ingrese.username"));
            return;
        }

        this.usuario = usuarioDAO.buscarPorUsername(username);

        if(this.usuario != null) {
            adminModificarView.cargarDatosUsuario(this.usuario);
            seleccionComboAdmin();
        } else {
            adminModificarView.mostrarMensaje(mensaje.get("usuario.no.encontrado"));
            adminModificarView.limpiar();
            inicializarCamposAdmin();
        }
    }

    private void mostrarContraseniaAdmin() {
        JPasswordField contrasenia = adminModificarView.getTxtContrasenia();
        JPasswordField confirmacion = adminModificarView.getTxtConfirmar();

        if(contrasenia.getEchoChar() == '*') {
            contrasenia.setEchoChar((char) 0);
            confirmacion.setEchoChar((char) 0);
            adminModificarView.getBtnMostrar().setText(mensaje.get("ocultar"));
        } else {
            contrasenia.setEchoChar('*');
            confirmacion.setEchoChar('*');
            adminModificarView.getBtnMostrar().setText(mensaje.get("mostrar"));
        }
    }

    private void seleccionComboAdmin() {
        if(mensaje == null) {
            JOptionPane.showMessageDialog(null, "Error");
            return;
        }
        String opcion = (String) adminModificarView.getCbxModificar().getSelectedItem();
        if(opcion == null) return;

        boolean esContrasenia = opcion.equals(mensaje.get("modificar.contrasenia"));

        adminModificarView.getTxtContrasenia().setEnabled(esContrasenia);
        adminModificarView.getTxtConfirmar().setEnabled(esContrasenia);
        adminModificarView.getTxtUsuario1().setEnabled(!esContrasenia); // Asumiendo que !esContrasenia significa "modificar usuario"

        adminModificarView.revalidate();
        adminModificarView.repaint();
    }

    public void guardarCambiosAdmin() {
        if(usuario == null) {
            adminModificarView.mostrarMensaje("primero.buscar.usuario");
            return;
        }

        String opcion = (String) adminModificarView.getCbxModificar().getSelectedItem();
        if(opcion == null) return;

        if(opcion.equals(mensaje.get("modificar.contrasenia"))) {
            actualizarContraseniaAdmin();
        } else {
            actualizarUsernameAdmin();
        }
    }

    private void actualizarContraseniaAdmin() {
        String nuevaContra = new String(adminModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(adminModificarView.getTxtConfirmar().getPassword());

        if(!nuevaContra.equals(confirmacion)) {
            adminModificarView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if(nuevaContra.length() < 5) {
            adminModificarView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        usuario.setContrasenia(nuevaContra);
        if(usuarioDAO.actualizar(usuario)) {
            adminModificarView.mostrarMensaje("contrasenia.actualizada");
            adminModificarView.limpiar();
        } else {
            adminModificarView.mostrarMensaje("error.actualizar");
        }
    }

    private void actualizarUsernameAdmin() {
        String nuevoUsername = adminModificarView.getTxtUsuario().getText().trim();
        if(nuevoUsername.isEmpty()) {
            adminModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        if(!nuevoUsername.equals(usuario.getUsername()) &&
                usuarioDAO.buscarPorUsername(nuevoUsername) != null) {
            adminModificarView.mostrarMensaje("usuario.ya.existe");
            return;
        }

        usuario.setUsername(nuevoUsername);
        if(usuarioDAO.actualizar(usuario)) {
            adminModificarView.mostrarMensaje("usuario.actualizado");
            adminModificarView.cargarDatosUsuario(usuario);
        } else {
            adminModificarView.mostrarMensaje("error.actualizar");
        }
    }

    private void inicializarCampos() {
        usuarioModificarView.getTxtContrasenia().setEnabled(false);
        usuarioModificarView.getTxtConfirmar().setEnabled(false);
        usuarioModificarView.getTxtUsuario().setEnabled(false);
    }
    private void inicializarCamposAdmin() {
        adminModificarView.getTxtContrasenia().setEnabled(false);
        adminModificarView.getTxtConfirmar().setEnabled(false);
        adminModificarView.getTxtUsuario1().setEnabled(false);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}