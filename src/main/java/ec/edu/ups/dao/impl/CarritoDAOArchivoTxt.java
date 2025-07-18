package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoDAOArchivoTxt implements CarritoDAO {
    private final String ruta;
    private final List<Carrito> carritos = new ArrayList<>();

    public CarritoDAOArchivoTxt(String ruta) {
        this.ruta = ruta;
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta + "/carritos.txt");
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
        File archivo = new File(ruta + "/carritos.txt");
        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Carrito carrito = csvToCarrito(linea);
                if (carrito != null) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar carritos desde archivo de texto: " + e.getMessage());
        }
    }

    private void guardarCarritos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta + "/carritos.txt"))) {
            for (Carrito carrito : carritos) {
                writer.println(carritoToCSV(carrito));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String carritoToCSV(Carrito carrito) {
        StringBuilder sb = new StringBuilder();
        sb.append(carrito.getCodigo()).append(",");
        sb.append(carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "").append(",");
        sb.append(carrito.getFechaCreacion().getTimeInMillis()).append(",");
        for (ItemCarrito item : carrito.obtenerItems()) {
            sb.append(item.getProducto().getCodigo()).append(":").append(item.getCantidad()).append(";");
        }
        return sb.toString();
    }

    private Carrito csvToCarrito(String csv) {
        String[] partes = csv.split(",", 4);
        if (partes.length < 4) return null;
        int codigo = Integer.parseInt(partes[0]);
        String username = partes[1];
        long fechaMillis = Long.parseLong(partes[2]);
        Carrito carrito = new Carrito();
        carrito.setCodigo(codigo);
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new GregorianCalendar());
        carrito.getFechaCreacion().setTimeInMillis(fechaMillis);
        String itemsStr = partes[3];
        String[] itemsArr = itemsStr.split(";");
        for (String itemStr : itemsArr) {
            if (itemStr.isEmpty()) continue;
            String[] itemPartes = itemStr.split(":");
            int prodCodigo = Integer.parseInt(itemPartes[0]);
            int cantidad = Integer.parseInt(itemPartes[1]);
            // Producto placeholder con código
            Producto producto = new Producto(prodCodigo, "Desconocido", 0.0);
            carrito.agregarProducto(producto, cantidad);
        }
        return carrito;
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
        return List.of();
    }
}
