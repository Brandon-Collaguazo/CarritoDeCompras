package ec.edu.ups.excepciones;

/**
 * Excepción lanzada cuando hay problemas con el formato de una fecha.
 * Esta excepción es de tipo checked (heredada de Exception).
 */
public class FechaException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje estándar indicando que el formato de fecha es incorrecto.
     */
    public FechaException() {
        super("formato.fecha.incorrecto");
    }
}
