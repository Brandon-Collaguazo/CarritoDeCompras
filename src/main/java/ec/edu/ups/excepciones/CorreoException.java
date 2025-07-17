package ec.edu.ups.excepciones;

public class CorreoException extends RuntimeException {
  public CorreoException() {
    super("El correo electrónico no tiene formato válido");
  }
}
