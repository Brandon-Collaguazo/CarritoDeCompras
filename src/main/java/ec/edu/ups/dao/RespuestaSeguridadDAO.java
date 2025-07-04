package ec.edu.ups.dao;

import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface RespuestaSeguridadDAO {
    List<RespuestaSeguridad> buscarPorUsuario(Usuario usuario);
}
