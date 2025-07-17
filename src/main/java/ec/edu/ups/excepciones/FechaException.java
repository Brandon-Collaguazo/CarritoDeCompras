package ec.edu.ups.excepciones;

public class FechaException extends Exception {
    public FechaException() {
        super("Formato de fecha incorrecto (dene ser dd/MM/yyyy)");
    }
}
