package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOBinario implements ProductoDAO {
    private final String ruta;
    private final List<Producto> productos = new ArrayList<>();

    public ProductoDAOBinario(String ruta) {
        this.ruta = ruta;
        cargarProductos();
    }

    private void cargarProductos() {
        File archivo = new File(ruta + "/productos.bin");
        if (!archivo.exists()) {
            return;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                Producto producto = (Producto) inputStream.readObject();
                productos.add(producto);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void guardarProductos() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ruta + "/productos.bin"))) {
            for (Producto producto : productos) {
                outputStream.writeObject(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream()
                .filter(producto -> producto.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        cargarProductos();
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre() != null && producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardarProductos();
                return;
            }
        }

    }

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(producto -> producto.getCodigo() == codigo);
        guardarProductos();
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
