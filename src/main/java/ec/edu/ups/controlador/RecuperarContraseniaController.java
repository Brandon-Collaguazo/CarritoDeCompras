package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.RespuestaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.RecuperarContraseniaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecuperarContraseniaController {
    private final RecuperarContraseniaView recuperarContraseniaView;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaSeguridadDAO preguntaDAO;
    private PreguntaSeguridad preguntaActual;
    private MensajeInternacionalizacionHandler mensaje;

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

    private void configurarEventos() {
        recuperarContraseniaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });

        recuperarContraseniaView.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarContrasenia();
            }
        });
    }

    private void buscarUsuario() {
        String username = recuperarContraseniaView.getTxtUsuario().getText();
        if(username.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("ingrese.usuario");
            return;
        }

        List<PreguntaSeguridad> preguntas = preguntaDAO.obtenerPreguntasPorUsuario(username);
        if(preguntas == null || preguntas.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("usuario.sin.preguntas");
        }

        Random random = new Random();
        preguntaActual = preguntas.get(random.nextInt(preguntas.size()));
        recuperarContraseniaView.mostrarPregunta(preguntaActual);
    }

    private void recuperarContrasenia() {
        String respuesta = recuperarContraseniaView.getTxtRespuesta().getText();
        String nuevaContrasenia = new String(recuperarContraseniaView.getTxtContrasenia().getPassword());
        String confirmacion = new String(recuperarContraseniaView.getTxtConfirmar().getPassword());
        if(respuesta.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("ingrese.respuesta");
            return;
        }

        if(!nuevaContrasenia.equals(confirmacion)) {
            recuperarContraseniaView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if(preguntaDAO.verificarRespuesta(
                recuperarContraseniaView.getTxtUsuario().getText(),
                preguntaActual.getId(),
                respuesta
        )) {
            Usuario usuario = usuarioDAO.buscarPorUsername(recuperarContraseniaView.getTxtUsuario().getText());
            usuario.setContrasenia(nuevaContrasenia);
            if(usuarioDAO.actualizar(usuario)) {
                recuperarContraseniaView.mostrarMensaje("contrasenia.actualizada");
                recuperarContraseniaView.dispose();
            } else {
                recuperarContraseniaView.mostrarMensaje("error.actualizar.contrasenia");
            }
        } else {
            recuperarContraseniaView.mostrarMensaje("respuesta.incorrecta");
        }
    }
}
