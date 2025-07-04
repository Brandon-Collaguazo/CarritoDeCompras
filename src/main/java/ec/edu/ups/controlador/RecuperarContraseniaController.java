package ec.edu.ups.controlador;

import ec.edu.ups.dao.RespuestaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utils.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.usuario.RecuperarContraseniaView;

import java.util.ArrayList;
import java.util.List;

public class RecuperarContraseniaController {
    private final RecuperarContraseniaView recuperarContraseniaView;
    private final UsuarioDAO usuarioDAO;
    private final RespuestaSeguridadDAO respuestaDAO;
    private MensajeInternacionalizacionHandler mensaje;

    public RecuperarContraseniaController(RecuperarContraseniaView recuperarContraseniaView,
                                          UsuarioDAO usuarioDAO,
                                          RespuestaSeguridadDAO respuestaDAO,
                                          MensajeInternacionalizacionHandler mensaje) {
        this.recuperarContraseniaView = recuperarContraseniaView;
        this.usuarioDAO = usuarioDAO;
        this.respuestaDAO = respuestaDAO;
        this.mensaje = mensaje;
        configurarEventos();
    }

    private void configurarEventos() {

    }

    private void recuperarContrasenia() {
        String username = recuperarContraseniaView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            recuperarContraseniaView.mostrarMensaje("ingrese.usuario");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            recuperarContraseniaView.mostrarMensaje("usuario.no.encontrado");
            return;
        }

        List<RespuestaSeguridad> respuestasUsuario = respuestaDAO.buscarPorUsuario(usuario);
        if (respuestasUsuario == null || respuestasUsuario.size() < 3) {
            recuperarContraseniaView.mostrarMensaje("preguntas.no.configuradas");
            return;
        }

        if (!validarRespuestasSeguridad(respuestasUsuario)) {
            recuperarContraseniaView.mostrarMensaje("respuestas.incorrectas");
            return;
        }

        actualizarContrasenia(usuario);
    }

    private boolean validarRespuestasSeguridad(List<RespuestaSeguridad> respuestasUsuario) {
        List<PreguntaSeguridad> preguntas = new ArrayList<>();
        List<String> respuestasCorrectas = new ArrayList<>();
        for (RespuestaSeguridad rs : respuestasUsuario) {
            preguntas.add(rs.getPregunta());
            respuestasCorrectas.add(rs.getRespuesta());
        }

        recuperarContraseniaView.cargarPreguntas(preguntas);
        List<String> respuestas = recuperarContraseniaView.getRespuestas();

        if (respuestas == null) {
            return false;
        }

        for (int i = 0; i < respuestasCorrectas.size(); i++) {
            if (!respuestasCorrectas.get(i).equalsIgnoreCase(respuestas.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void actualizarContrasenia(Usuario usuario) {
        String nuevaContra = new String(recuperarContraseniaView.getTxtContrasenia().getPassword());
        String confirmacion = new String(recuperarContraseniaView.getTxtConfirmar().getPassword());

        if (!nuevaContra.equals(confirmacion)) {
            recuperarContraseniaView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if (nuevaContra.length() < 5) {
            recuperarContraseniaView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        usuario.setContrasenia(nuevaContra);
        if (usuarioDAO.actualizar(usuario)) {
            recuperarContraseniaView.mostrarMensaje("contrasenia.actualizada");
            recuperarContraseniaView.dispose();
        } else {
            recuperarContraseniaView.mostrarMensaje("error.actualizar");
        }
    }
}
