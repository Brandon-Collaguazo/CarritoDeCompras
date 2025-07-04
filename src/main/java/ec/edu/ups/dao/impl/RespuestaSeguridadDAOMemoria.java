package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.RespuestaSeguridadDAO;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RespuestaSeguridadDAOMemoria implements RespuestaSeguridadDAO {
    private List<RespuestaSeguridad> respuestas;

    public RespuestaSeguridadDAOMemoria() {
        respuestas = new ArrayList<>();
    }

    @Override
    public List<RespuestaSeguridad> buscarPorUsuario(Usuario usuario) {
        List<RespuestaSeguridad> resultado = new ArrayList<>();
        for (RespuestaSeguridad respuesta : respuestas) {
            if (respuesta.getUsuario().equals(usuario)) {
                resultado.add(respuesta);
            }
        }
        return resultado;
    }
}
