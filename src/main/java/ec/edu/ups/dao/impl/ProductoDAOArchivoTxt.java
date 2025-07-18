package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTxt implements ProductoDAO {
    private final String ruta;
    private final List<Producto> productos = new ArrayList<>();

    public ProductoDAOArchivoTxt(String ruta) {
        this.ruta = ruta;
        File archivo = new File(ruta + "/productos.txt");
        crearArchivoInexistente(archivo);
        cargaProductos();
    }

    private void crearArchivoInexistente(File archivo) {
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargaProductos() {
        productos.clear();
        File archivo = new File(ruta + "/productos.txt");
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Producto producto = parseProducto(linea);
                if (producto != null) {
                    productos.add(producto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Producto parseProducto(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 3) {
            return new Producto(
                    Integer.parseInt(partes[0]),
                    partes[1],
                    Double.parseDouble(partes[2])
            );
        }
        return null;
    }

    private String productoToCSV(Producto producto) {
        return String.join("|",
                String.valueOf(producto.getCodigo()),
                producto.getNombre(),
                String.valueOf(producto.getPrecio())
        );
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
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
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

    private void guardarProductos() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(ruta + "/productos.txt"))) {
            for (Producto producto : productos) {
                printWriter.println(productoToCSV(producto));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
