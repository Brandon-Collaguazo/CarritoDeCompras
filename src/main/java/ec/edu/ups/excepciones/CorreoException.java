package ec.edu.ups.excepciones;

/**
 * Excepción lanzada cuando hay problemas con el formato o validación de un correo electrónico.
 * Esta excepción es de tipo unchecked (heredada de RuntimeException).
 */
public class CorreoException extends RuntimeException {

  /**
   * Construye una nueva excepción con un mensaje estándar indicando que el correo es inválido.
   */
  public CorreoException() {
    super("correo.invalido");
  }
}
