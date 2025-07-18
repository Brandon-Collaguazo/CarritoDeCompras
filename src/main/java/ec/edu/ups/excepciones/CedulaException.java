package ec.edu.ups.excepciones;

/**
 * Excepción lanzada cuando hay problemas con el formato o validación de una cédula.
 * Esta excepción es de tipo checked (heredada de Exception).
 */
public class CedulaException extends Exception {

    /**
     * Construye una nueva excepción con el mensaje especificado.
     * @param message El mensaje detallado que describe el error de validación de la cédula
     */
    public CedulaException(String message) {
        super(message);
    }
}
