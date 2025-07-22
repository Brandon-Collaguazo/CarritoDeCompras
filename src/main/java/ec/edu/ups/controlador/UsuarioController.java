package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.archBin.CarritoDAOBinario;
import ec.edu.ups.dao.impl.archBin.ProductoDAOBinario;
import ec.edu.ups.dao.impl.archBin.UsuarioDAOBinario;
import ec.edu.ups.dao.impl.archTxt.CarritoDAOArchivoTxt;
import ec.edu.ups.dao.impl.archTxt.ProductoDAOArchivoTxt;
import ec.edu.ups.dao.impl.archTxt.UsuarioDAOArchivoTxt;
import ec.edu.ups.dao.impl.memoria.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.memoria.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.memoria.UsuarioDAOMemoria;
import ec.edu.ups.excepciones.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.utils.FormateadorUtils;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.autenticacion.LoginView;
import ec.edu.ups.vista.autenticacion.RecuperarContraseniaView;
import ec.edu.ups.vista.autenticacion.UsuarioRegistroView;
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Controlador principal para la gestión de usuarios en el sistema.
 * Maneja la lógica de negocio relacionada con autenticación, registro,
 * modificación y eliminación de usuarios, así como la interacción entre
 * las vistas y los DAOs correspondientes.
 */
public class UsuarioController {

    private Usuario usuario;
    private boolean administrador;
    private MensajeInternacionalizacionHandler mensaje;
    private List<PreguntaSeguridad> preguntasSeleccionadas;
    private int pasoActual = 0;
    private String usernameEnRegistro;
    private String passwordEnRegistro;
    private UsuarioDAO usuarioDAO;
    private CarritoDAO carritoDAO;
    private ProductoDAO productoDAO;
    private final PreguntaSeguridadDAO preguntaDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;
    private final RecuperarContraseniaView recuperarContraseniaView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioListaView usuarioListaView;
    private final UsuarioModificarView usuarioModificarView;
    private final AdminModificarView adminModificarView;
    private final ManagerDAO managerDAO;

    /**
     * Constructor principal del controlador de usuarios.
     *
     * @param usuarioDAO DAO para operaciones con usuarios
     * @param carritoDAO DAO para operaciones con carritos
     * @param productoDAO DAO para operaciones con productos
     * @param loginView Vista de login
     * @param preguntaDAO DAO para preguntas de seguridad
     * @param usuarioRegistroView Vista de registro de usuarios
     * @param recuperarContraseniaView Vista de recuperación de contraseña
     * @param usuarioEliminarView Vista de eliminación de usuarios
     * @param usuarioListaView Vista de listado de usuarios
     * @param usuarioModificarView Vista de modificación de usuarios (usuario normal)
     * @param adminModificarView Vista de modificación de usuarios (admin)
     * @param mensaje Manejador de internacionalización
     */
    public UsuarioController(UsuarioDAO usuarioDAO,
                             CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             LoginView loginView,
                             PreguntaSeguridadDAO preguntaDAO,
                             UsuarioRegistroView usuarioRegistroView,
                             RecuperarContraseniaView recuperarContraseniaView,
                             UsuarioEliminarView usuarioEliminarView,
                             UsuarioListaView usuarioListaView,
                             UsuarioModificarView usuarioModificarView,
                             AdminModificarView adminModificarView,
                             MensajeInternacionalizacionHandler mensaje,
                             ManagerDAO managerDAO) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
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
        this.managerDAO = managerDAO;


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

    /**
     * Configura los eventos y listeners para todas las vistas asociadas.
     */
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

    /**
     * Configura los eventos específicos para la modificación de usuarios.
     */
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

    /**
     * Autentica a un usuario en el sistema.
     * Verifica las credenciales y establece el estado de administrador.
     */
    public void autenticar() {
        String username = loginView.getTxtUsuario().getText().trim();
        String contrasenia = loginView.getTxtPassword().getText().trim();
        String almacenamientoKey = loginView.getSelectedStorageTypeKey();
        String filePath = loginView.getRutaArchivo();

        // Inicializar el DAO según el tipo de almacenamiento seleccionado
        managerDAO.inicializarDAOS(almacenamientoKey, filePath);
        this.usuarioDAO = managerDAO.getUsuarioDAO();

        // Autenticación del usuario
        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("datos.usuario.incorrectos");
        } else {
            this.administrador = (usuario.getRol() == Rol.ADMINISTRADOR);
            loginView.dispose(); // Cerrar la vista de login

            // Redirigir a la vista correspondiente según el rol
            if (this.administrador) {
                if (this.adminModificarView != null) {
                    inicializarCamposAdmin();
                } else {
                    System.err.println("Vista de administrador no inicializada.");
                }
            } else {
                if (this.usuarioModificarView != null) {
                    usuarioModificarView.cargarDatosUsuario(usuario);
                    seleccionCombo();
                } else {
                    System.err.println("Vista de usuario no inicializada.");
                }
            }
        }
        // Limpiar campos después de intentar autenticar
        loginView.limpiarCampos();
    }


    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return Usuario autenticado o null si no hay sesión activa
     */
    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    // Métodos en "USUARIOREGISTROVIEW"

    /**
     * Inicia el proceso de registro de un nuevo usuario.
     * Limpia los campos y selecciona preguntas de seguridad aleatorias.
     */
    private void iniciarRegistro() {
        pasoActual = 0;
        preguntasSeleccionadas = preguntaDAO.obtenerPreguntasAleatorias(3);
        usuarioRegistroView.limpiarCampos();
        mostrarPreguntaSeguridad();
        usuarioRegistroView.setVisible(true);
    }

    /**
     * Procesa cada paso del registro de usuario.
     * Maneja las respuestas de seguridad y avanza en el proceso.
     */
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

    /**
     * Valida los datos ingresados en el formulario de registro.
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos() {
        String cedula = usuarioRegistroView.getTxtCedula().getText().trim();
        String nombre = usuarioRegistroView.getTxtNombre().getText().trim();
        String fecha = usuarioRegistroView.getTxtFechaNacimiento().getText().trim();
        String telefono = usuarioRegistroView.getTxtTelefono().getText().trim();
        String correo = usuarioRegistroView.getTxtCorreo().getText().trim();
        String username = usuarioRegistroView.getTxtUsername().getText().trim();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());
        String confirmarPassword = new String(usuarioRegistroView.getTxtConfirmarPassword().getPassword());
        // Validación básica de campos obligatorios
        if (cedula.isEmpty() || nombre.isEmpty() || fecha.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || username.isEmpty() || password.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("campo.usuario.obligatorio");
            return false;
        }
        // Verificación de username único
        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioRegistroView.mostrarMensaje("usuario.existente");
            return false;
        }
        // Verificación de coincidencia de contraseñas
        if (!password.equals(confirmarPassword)) {
            usuarioRegistroView.mostrarMensaje("contrasenias.no.coinciden");
            return false;
        }
        return true;
    }

    /**
     * Muestra la pregunta de seguridad actual en el proceso de registro.
     */
    private void mostrarPreguntaSeguridad() {
        if(pasoActual >= 0 && pasoActual < 3) {
            PreguntaSeguridad pregunta = preguntasSeleccionadas.get(pasoActual);
            usuarioRegistroView.configurarPreguntaSeguridad(pregunta, pasoActual + 1);
        }
    }

    /**
     * Procesa la respuesta de seguridad del usuario.
     *
     * @return true si la respuesta fue procesada correctamente, false en caso contrario
     */
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

    /**
     * Completa el registro de un nuevo usuario con todos sus datos.
     * Valida y guarda la información en la base de datos.
     */
    private void completarRegistro() {
        if (!validarDatos()) {
            return;
        }

        String cedula = usuarioRegistroView.getTxtCedula().getText().trim();
        String nombre = usuarioRegistroView.getTxtNombre().getText().trim();
        String fechaStr = usuarioRegistroView.getTxtFechaNacimiento().getText().trim();
        String telefono = usuarioRegistroView.getTxtTelefono().getText().trim();
        String correo = usuarioRegistroView.getTxtCorreo().getText().trim();
        String username = usuarioRegistroView.getTxtUsername().getText().trim();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());

        // Limpiar el número de teléfono (remover espacios, guiones, etc.)
        String telefonoLimpio = telefono.replaceAll("[^0-9]", "");

        Usuario nuevoUsuario = new Usuario(
                cedula,
                nombre,
                null,
                telefonoLimpio, // Usar el teléfono limpio
                correo,
                username,
                password,
                Rol.USUARIO
        );

        try {
            nuevoUsuario.validar(fechaStr);

            usuarioDAO.crear(nuevoUsuario);
            usuarioRegistroView.mostrarMensaje("registro.exitoso");
            usuarioRegistroView.dispose();

            // Limpiar estado
            pasoActual = 0;
            preguntasSeleccionadas = null;
            usernameEnRegistro = null;
            passwordEnRegistro = null;
            usuarioRegistroView.limpiarCampos();

        } catch (CedulaException e) {
            usuarioRegistroView.mostrarMensaje("cedula.invalida");
        } catch (ContraseniaException e) {
            usuarioRegistroView.mostrarMensaje(e.getMessage());
        } catch (CorreoException e) {
            usuarioRegistroView.mostrarMensaje("correo.invalido");
        } catch (FechaException e) {
            usuarioRegistroView.mostrarMensaje("formato.fecha.incorrecto");
        } catch (TelefonoException e) {
            usuarioRegistroView.mostrarMensaje("telefono.invalido");
        } catch (Exception e) {
            usuarioRegistroView.mostrarMensaje("error.registro");
            e.printStackTrace();
        }
    }


    // Métodos de la ventana "USUARIOELIMINARVIEW"

    /**
     * Busca un usuario para eliminación.
     * Muestra los datos del usuario y sus carritos asociados.
     */
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

    /**
     * Cuenta los carritos asociados a un usuario.
     *
     * @param usuario Usuario a verificar
     * @return Número de carritos asociados al usuario
     */
    private int contarCarritosUsuario(Usuario usuario) {
        if (usuario == null || usuario.getUsername() == null) {
            return 0;
        }
        return carritoDAO.listarPorUsuario(usuario.getUsername()).size();
    }

    /**
     * Elimina un usuario del sistema.
     * Realiza validaciones previas y solicita confirmación.
     */
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

    /**
     * Carga los datos de un usuario en la tabla de eliminación.
     *
     * @param usuario Usuario a mostrar
     * @param numCarritos Número de carritos asociados
     */
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

    /**
     * Limpia los campos de la vista de eliminación de usuarios.
     */
    private void limpiarCamposEliminar() {
        usuarioEliminarView.getTxtUsuario().setText("");
        usuarioEliminarView.getBtnEliminar().setEnabled(false);
        DefaultTableModel modelo = (DefaultTableModel) usuarioEliminarView.getTblUsuario().getModel();
        modelo.setRowCount(0);
    }

    // Métodos de la ventana "USUARIOLISTAVIEW"
    /**
     * Busca usuarios para mostrar en el listado.
     * Puede buscar un usuario específico o listar todos.
     */
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

    /**
     * Carga los datos de usuarios en la tabla de listado.
     *
     * @param usuarios Lista de usuarios a mostrar
     */
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

    /**
     * Calcula el total de compras de una lista de carritos.
     *
     * @param carritos Lista de carritos a procesar
     * @return Suma total de los carritos
     */
    private double calcularTotalCarritos(List<Carrito> carritos) {
        double total = 0.0;
        for(Carrito carrito : carritos) {
            if(carrito != null) {
                total += carrito.calcularTotal();
            }
        }
        return total;
    }

    /**
     * Limpia los campos de la vista de listado de usuarios.
     */
    private void limpiarCamposLista() {
        usuarioListaView.getTxtUsuario().setText("");
    }

    // Métodos en la ventana "USUARIOMODIFICARVIEW"

    /**
     * Carga los datos de un usuario en la vista de modificación.
     *
     * @param username Nombre de usuario a cargar
     * @param usuarioAutenticado Usuario con permisos para la operación
     */
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

    /**
     * Alterna entre mostrar/ocultar la contraseña en la vista de modificación.
     */
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

    /**
     * Maneja la selección en el combo box de opciones de modificación.
     */
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

    /**
     * Guarda los cambios realizados en la modificación de usuario.
     */
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

    /**
     * Actualiza la contraseña de un usuario.
     */
    private void actualizarContrasenia() {
        String nuevaContra = new String(usuarioModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(usuarioModificarView.getTxtConfirmar().getPassword());

        try {
            if (!nuevaContra.equals(confirmacion)) {
                throw new ContraseniaException("contrasenias.no.coinciden");
            }

            if (nuevaContra.length() < 8) {
                throw new ContraseniaException("contrasenia.corta");
            }

            usuario.setContrasenia(nuevaContra);
            usuarioDAO.actualizar(usuario);

            usuarioModificarView.mostrarMensaje("contrasenia.actualizada");
            usuarioModificarView.limpiarCampos();

        } catch (ContraseniaException e) {
            usuarioModificarView.mostrarMensaje(e.getMessage());
        } catch (Exception e) {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }

    /**
     * Actualiza el nombre de usuario.
     */
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
        usuarioDAO.actualizar(usuario);
        usuarioModificarView.mostrarMensaje("usuario.actualizado");
        usuarioModificarView.cargarDatosUsuario(usuario);
    }

    // Métodos para "ADMIN"

    /**
     * Busca un usuario para modificación (vista de administrador).
     */
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

    /**
     * Alterna entre mostrar/ocultar la contraseña en la vista de administrador.
     */
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

    /**
     * Maneja la selección en el combo box de opciones de modificación (admin).
     */
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

    /**
     * Guarda los cambios realizados en la modificación de usuario (admin).
     */
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

    /**
     * Actualiza la contraseña de un usuario (vista de administrador).
     */
    private void actualizarContraseniaAdmin() {
        String nuevaContra = new String(adminModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(adminModificarView.getTxtConfirmar().getPassword());

        try {
            if (!nuevaContra.equals(confirmacion)) {
                throw new ContraseniaException("contrasenias.no.coinciden");
            }

            if (nuevaContra.length() < 8) {
                throw new ContraseniaException("contrasenia.corta");
            }

            usuario.setContrasenia(nuevaContra);
            usuarioDAO.actualizar(usuario);

            adminModificarView.mostrarMensaje("contrasenia.actualizada");
            adminModificarView.limpiar();

        } catch (ContraseniaException e) {
            adminModificarView.mostrarMensaje(e.getMessage());
        } catch (Exception e) {
            adminModificarView.mostrarMensaje("error.actualizar");
        }
    }

    /**
     * Actualiza el nombre de usuario (vista de administrador).
     */
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
        usuarioDAO.actualizar(usuario);
        adminModificarView.mostrarMensaje("usuario.actualizado");
        adminModificarView.cargarDatosUsuario(usuario);
    }

    /**
     * Inicializa los campos de la vista de modificación de usuario normal.
     */
    private void inicializarCampos() {
        usuarioModificarView.getTxtContrasenia().setEnabled(false);
        usuarioModificarView.getTxtConfirmar().setEnabled(false);
        usuarioModificarView.getTxtUsuario().setEnabled(false);
    }

    /**
     * Inicializa los campos de la vista de modificación de administrador.
     */
    private void inicializarCamposAdmin() {
        adminModificarView.getTxtContrasenia().setEnabled(false);
        adminModificarView.getTxtConfirmar().setEnabled(false);
        adminModificarView.getTxtUsuario1().setEnabled(false);
    }

    /**
     * Obtiene el usuario actual.
     * @return Usuario actual
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario actual.
     * @param usuario Usuario a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}