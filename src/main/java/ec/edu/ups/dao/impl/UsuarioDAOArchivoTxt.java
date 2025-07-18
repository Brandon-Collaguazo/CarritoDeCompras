package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.excepciones.FechaException;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAOArchivoTxt implements UsuarioDAO {
    private String rutaArchivo;
    private List<Usuario> usuarios;

    public UsuarioDAOArchivoTxt(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void crearSiNoExiste() {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + rutaArchivo);
            e.printStackTrace();
        }
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 8) {
                    Usuario usuario = new Usuario();
                    usuario.setCedula(datos[0]);
                    usuario.setNombreCompleto(datos[1]);

                    try {
                        usuario.validarFecha(datos[2]);
                    } catch (FechaException e) {
                        System.err.println("Error al validar la fecha: " + e.getMessage());
                        continue; // O maneja el error como prefieras
                    }

                    usuario.setTelefono(datos[3]);
                    usuario.setCorreo(datos[4]);
                    usuario.setUsername(datos[5]);
                    usuario.setContrasenia(datos[6]);
                    usuario.setRol(Rol.valueOf(datos[7]));
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            // Archivo no existe, se creará al guardar
        }
    }

    private void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                pw.println(usuario.getCedula() + "|" +
                        usuario.getNombreCompleto() + "|" +
                        usuario.getFechaNacimiento() + "|" +
                        usuario.getTelefono() + "|" +
                        usuario.getCorreo() + "|" +
                        usuario.getUsername() + "|" +
                        usuario.getContrasenia() + "|" +
                        usuario.getRol());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
