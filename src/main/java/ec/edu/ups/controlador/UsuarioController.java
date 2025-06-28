
package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.usuario.LoginView;
import ec.edu.ups.vista.usuario.UsuarioRegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, UsuarioRegistroView usuarioRegistroView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.usuarioRegistroView = usuarioRegistroView;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas(){
        //EVENTOS EN "LOGINVIEW"
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
        usuarioRegistroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }


    //Métodos en "LOGINVIEW"
    private void autenticar(){
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        }else{
            loginView.dispose();
        }

        loginView.limpiarCampos();
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    //Métodos en "USUARIOREGISTROVIEW"
    private void registrarUsuario() {
        String username = usuarioRegistroView.getTxtUsername().getText();
        String password = new String(usuarioRegistroView.getTxtPassword().getPassword());
        String confirmarPassword = new String(usuarioRegistroView.getTxtConfirmarPassword().getPassword());

        if(username.isEmpty() || password.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("Por favor complete todos los campos");
            return;
        }

        if(usuarioDAO.buscarPorUsername(username) != null) {
            usuarioRegistroView.mostrarMensaje("Nombre de usuarios ya existente");
            return;
        }

        if(!password.equals(confirmarPassword)) {
            usuarioRegistroView.mostrarMensaje("Las contraseñas no coinciden");
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, password, Rol.USUARIO);
        usuarioDAO.crear(nuevoUsuario);
        usuarioRegistroView.mostrarMensaje("Usuario registrado exitosamente");
        usuarioRegistroView.limpiarCampos();
    }
}
