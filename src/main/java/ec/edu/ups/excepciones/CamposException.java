package ec.edu.ups.excepciones;

public class CamposException extends Exception {
    public CamposException(String campo) {
        super("El campo " + campo + " es obligatorio");
    }
}
