
package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<Usuario>();
        crear(new Usuario("Pepe", null, "1234556789","", "admin", "12345", Rol.ADMINISTRADOR));
        crear(new Usuario("Brandon", null, "0969557675", "", "user", "12345", Rol.USUARIO));
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuarioAux = usuarios.get(i);
            if(usuarioAux.getUsername().equals(usuario.getUsername())){
                usuarios.set(i, usuario);
                break;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }

        return usuariosEncontrados;
    }
}
