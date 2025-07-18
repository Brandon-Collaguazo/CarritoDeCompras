package ec.edu.ups.excepciones;

/**
 * Excepción lanzada cuando un campo obligatorio no ha sido proporcionado.
 * Esta excepción es de tipo checked (heredada de Exception).
 */
public class CamposException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje estándar indicando que un campo es obligatorio.
     * @param campo El nombre del campo que generó la excepción (actualmente no utilizado en el mensaje)
     */
    public CamposException(String campo) {
        super("campo.obligatorio");
    }
}
