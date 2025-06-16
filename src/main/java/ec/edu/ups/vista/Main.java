package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                ProductoModificarView productoModificar = new ProductoModificarView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                new ProductoController(productoDAO, productoListaView, productoAnadirView, productoEliminarView, productoModificar);
            }
        });
    }
}
