package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.RecuperarContraseniaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class RecuperarContraseniaController {
    private final RecuperarContraseniaView recuperarContraseniaView;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaSeguridadDAO preguntaDAO;
    private PreguntaSeguridad preguntaActual;
    private MensajeInternacionalizacionHandler mensaje;
    private boolean respuestaVerificada = false; // Flag para controlar el flujo

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
        if(usuario != null) {
            usuario.setContrasenia(nuevaContrasenia);
            if(usuarioDAO.actualizar(usuario)) {
                recuperarContraseniaView.mostrarMensaje("contrasenia.actualizada");
                limpiarFormulario();
                recuperarContraseniaView.dispose();
            } else {
                recuperarContraseniaView.mostrarMensaje("error.actualizar.contrasenia");
            }
        } else {
            recuperarContraseniaView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private void limpiarCamposPregunta() {
        recuperarContraseniaView.getTxtPregunta().setText("");
        recuperarContraseniaView.getTxtRespuesta().setText("");
        recuperarContraseniaView.getTxtRespuesta().setEnabled(false);
        recuperarContraseniaView.getTxtContrasenia().setEnabled(false);
        recuperarContraseniaView.getTxtConfirmar().setEnabled(false);
        recuperarContraseniaView.getBtnRecuperar().setEnabled(false);
    }

    private void limpiarFormulario() {
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