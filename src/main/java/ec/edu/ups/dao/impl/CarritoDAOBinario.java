package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOBinario implements CarritoDAO {
    private final String ruta;
    private final List<Carrito> carritos = new ArrayList<>();

    public CarritoDAOBinario(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivo = new File(ruta + "/carritos.bin");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cargarCarritos();
    }

    private void cargarCarritos() {
        carritos.clear();
        File archivo = new File(ruta + "/carritos.bin");
        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                Carrito carrito = (Carrito) objectInputStream.readObject();
                carritos.add(carrito);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar carritos desde un archivo binario: " + e.getMessage());
        }
    }

    private void guardarCarritos() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ruta + "/carritos.bin"))) {
            for (Carrito carrito : carritos) {
                objectOutputStream.writeObject(carrito);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritos();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> encontrados = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                encontrados.add(carrito);
            }
        }
        return encontrados;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                guardarCarritos();
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(carrito -> carrito.getCodigo() == codigo);
        guardarCarritos();
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> encontrados = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getUsername().equals(username)) {
                encontrados.add(carrito);
            }
        }
        return encontrados;
    }
}
