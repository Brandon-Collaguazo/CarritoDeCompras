package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import java.util.List;

public interface CarritoDAO {

    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    List<Carrito> buscarPorUsuario(String username);

    void actualizar(Carrito carrito);

    void eliminar(int codigo);

    List<Carrito> listarTodos();

}
