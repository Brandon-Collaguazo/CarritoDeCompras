package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOBinario implements UsuarioDAO {
    private String rutaArchivo;
    private List<Usuario> usuarios;

    public UsuarioDAOBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) &&
                    usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;

    }

    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsername(usuario.getUsername()) == null) {
            usuarios.add(usuario);
            guardarUsuarios();
        }
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
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null) {
            usuarios.remove(usuario);
            guardarUsuarios();
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        int index = usuarios.indexOf(buscarPorUsername(usuario.getUsername()));
        if (index != -1) {
            usuarios.set(index, usuario);
            guardarUsuarios();
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return List.of();
    }

    private void cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(rutaArchivo))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Archivo no existe, se creará al guardar
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
