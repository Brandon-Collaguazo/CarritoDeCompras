package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.autenticacion.RecuperarContraseniaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * Controlador para el proceso de recuperación de contraseñas.
 * Gestiona la autenticación mediante preguntas de seguridad y
 * el proceso de restablecimiento de contraseñas para usuarios.
 */
public class RecuperarContraseniaController {
    /**
     * Vista para la recuperación de contraseña.
     */
    private final RecuperarContraseniaView recuperarContraseniaView;

    /**
     * DAO para operaciones con usuarios.
     */
    private final UsuarioDAO usuarioDAO;

    /**
     * DAO para operaciones con preguntas de seguridad.
     */
    private final PreguntaSeguridadDAO preguntaDAO;

    /**
     * Pregunta de seguridad actual para el usuario.
     */
    private PreguntaSeguridad preguntaActual;

    /**
     * Manejador de mensajes internacionalizados.
     */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Manejador de mensajes internacionalizados.
     */
    private boolean respuestaVerificada = false; // Flag para controlar el flujo

    /**
     * Constructor principal del controlador.
     *
     * @param recuperarContraseniaView Vista de recuperación de contraseña
     * @param usuarioDAO DAO para operaciones de usuario
     * @param preguntaDAO DAO para preguntas de seguridad
     * @param mensaje Manejador de mensajes internacionalizados
     */
    public RecuperarContraseniaController(RecuperarContraseniaView recuperarContraseniaView,
                                          UsuarioDAO usuarioDAO,
                                          PreguntaSeguridadDAO preguntaDAO,
                                          MensajeInternacionalizacionHandler mensaje) {
        this.recuperarContraseniaView = recuperarContraseniaView;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.mensaje = mensaje;
        configurarEventos();
    }

    /**
     * Configura los eventos de la vista de recuperación de contraseña.
     */
    public void configurarEventos() {
        recuperarContraseniaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });

        recuperarContraseniaView.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!respuestaVerificada) {
                    verificarRespuesta();
                } else {
                    recuperarContrasenia();
                }
            }
        });

        recuperarContraseniaView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                recuperarContraseniaView.dispose();
            }
        });
    }

    /**
     * Busca un usuario para iniciar el proceso de recuperación.
     * Verifica la existencia del usuario y selecciona una pregunta de seguridad aleatoria.
     */
    private void buscarUsuario() {
        String username = recuperarContraseniaView.getTxtUsuario().getText().trim();
        if(username.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("ingrese.usuario");
            return;
        }

        // Verificar que el usuario existe
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if(usuario == null) {
            recuperarContraseniaView.mostrarMensaje("usuario.no.encontrado");
            return;
        }

        // Obtener las preguntas de seguridad del usuario
        List<PreguntaSeguridad> preguntas = preguntaDAO.obtenerPreguntasPorUsuario(username);
        if(preguntas == null || preguntas.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("usuario.sin.preguntas");
            limpiarCamposPregunta();
            return;
        }

        // Seleccionar una pregunta aleatoria
        Random random = new Random();
        preguntaActual = preguntas.get(random.nextInt(preguntas.size()));

        // Mostrar la pregunta en el campo txtPregunta y habilitar el campo de respuesta
        recuperarContraseniaView.getTxtPregunta().setText(preguntaActual.getTextoPregunta());
        recuperarContraseniaView.getTxtRespuesta().setEnabled(true);
        recuperarContraseniaView.getTxtRespuesta().setText(""); // Limpiar respuesta anterior

        // Deshabilitar campos de contraseña hasta verificar respuesta
        recuperarContraseniaView.getTxtContrasenia().setEnabled(false);
        recuperarContraseniaView.getTxtConfirmar().setEnabled(false);

        // Cambiar el texto del botón para verificar respuesta
        recuperarContraseniaView.getBtnRecuperar().setText(mensaje.get("verificar.respuesta"));
        recuperarContraseniaView.getBtnRecuperar().setEnabled(true);

        // Resetear el flag
        respuestaVerificada = false;
    }

    /**
     * Verifica la respuesta del usuario a la pregunta de seguridad.
     * Si es correcta, habilita los campos para ingresar una nueva contraseña.
     */
    private void verificarRespuesta() {
        String respuesta = recuperarContraseniaView.getTxtRespuesta().getText().trim();
        String username = recuperarContraseniaView.getTxtUsuario().getText().trim();

        if(respuesta.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("ingrese.respuesta");
            return;
        }

        if(preguntaActual == null) {
            recuperarContraseniaView.mostrarMensaje("primero.busque.usuario");
            return;
        }

        // Verificar la respuesta
        if(preguntaDAO.verificarRespuesta(username, preguntaActual.getId(), respuesta)) {
            // Respuesta correcta - habilitar campos de contraseña
            recuperarContraseniaView.getTxtContrasenia().setEnabled(true);
            recuperarContraseniaView.getTxtConfirmar().setEnabled(true);
            recuperarContraseniaView.getTxtRespuesta().setEnabled(false); // Deshabilitar respuesta

            // Cambiar el texto del botón para recuperar contraseña
            recuperarContraseniaView.getBtnRecuperar().setText(mensaje.get("recuperar.contrasenia"));
            respuestaVerificada = true;

            recuperarContraseniaView.mostrarMensaje("respuesta.correcta.ingrese.nueva.contrasenia");
        } else {
            // Respuesta incorrecta
            recuperarContraseniaView.mostrarMensaje("respuesta.incorrecta");
            recuperarContraseniaView.getTxtRespuesta().setText("");
            recuperarContraseniaView.getTxtRespuesta().requestFocus();
        }
    }

    /**
     * Procesa el cambio de contraseña del usuario.
     * Valida que las contraseñas coincidan y cumplan con los requisitos mínimos.
     */
    private void recuperarContrasenia() {
        String nuevaContrasenia = new String(recuperarContraseniaView.getTxtContrasenia().getPassword());
        String confirmacion = new String(recuperarContraseniaView.getTxtConfirmar().getPassword());
        String username = recuperarContraseniaView.getTxtUsuario().getText().trim();

        // Validaciones de contraseña
        if(nuevaContrasenia.isEmpty() || confirmacion.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("contrasenias.vacias");
            return;
        }

        if(!nuevaContrasenia.equals(confirmacion)) {
            recuperarContraseniaView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if(nuevaContrasenia.length() < 5) {
            recuperarContraseniaView.mostrarMensaje("contrasenia.invalida.longitud");
            return;
        }

        // Actualizar la contraseña del usuario
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null) {
            usuario.setContrasenia(nuevaContrasenia);

            try {
                usuarioDAO.actualizar(usuario); // Llamada directa sin evaluación
                recuperarContraseniaView.mostrarMensaje("contrasenia.actualizada");
                limpiarFormulario();
                recuperarContraseniaView.dispose();
            } catch (Exception e) {
                recuperarContraseniaView.mostrarMensaje("error.actualizar.contrasenia");
                e.printStackTrace(); // Para debug
            }
        } else {
            recuperarContraseniaView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    /**
     * Limpia los campos relacionados con la pregunta de seguridad.
     */
    private void limpiarCamposPregunta() {
        recuperarContraseniaView.getTxtPregunta().setText("");
        recuperarContraseniaView.getTxtRespuesta().setText("");
        recuperarContraseniaView.getTxtRespuesta().setEnabled(false);
        recuperarContraseniaView.getTxtContrasenia().setEnabled(false);
        recuperarContraseniaView.getTxtConfirmar().setEnabled(false);
        recuperarContraseniaView.getBtnRecuperar().setEnabled(false);
    }

    /**
     * Limpia completamente el formulario de recuperación.
     * Restablece todos los campos y estados a sus valores iniciales.
     */    private void limpiarFormulario() {
        recuperarContraseniaView.getTxtUsuario().setText("");
        recuperarContraseniaView.getTxtPregunta().setText("");
        recuperarContraseniaView.getTxtRespuesta().setText("");
        recuperarContraseniaView.getTxtContrasenia().setText("");
        recuperarContraseniaView.getTxtConfirmar().setText("");

        // Resetear estados
        recuperarContraseniaView.getTxtRespuesta().setEnabled(false);
        recuperarContraseniaView.getTxtContrasenia().setEnabled(false);
        recuperarContraseniaView.getTxtConfirmar().setEnabled(false);
        recuperarContraseniaView.getBtnRecuperar().setEnabled(false);
        recuperarContraseniaView.getBtnRecuperar().setText(mensaje.get("recuperar"));

        // Resetear variables
        preguntaActual = null;
        respuestaVerificada = false;
    }
}