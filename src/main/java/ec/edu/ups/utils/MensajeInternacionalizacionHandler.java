package ec.edu.ups.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manejador para la intercionalización de mensajes en la aplicación.
 * Esta classe proporciona funcionalidad para cargar y acceder a mensajes traducidos
 * según el idioma y país especificados, utilizando archivos de propiedades (ResourceBundle)
 * con el nombre base a "mensajes"
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Construye un nuevo manejador de intercionalización para el idioma y país especificados.
     * @param lenguaje Código de 2 letras para el idioma (e.j: "es", "en")
     * @param pais Código de 2 letras para el país (e.j: "EC", "US")
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje intercionalizado correspondiente a la clave especificada.
     * @param key Clave del mensaje en el archivo de propiedades
     * @return String con el mensaje traducido según el locale actual
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia dinámicamente el idioma y país para la intercionalización
     * Recarga el bundle de recursos con la nueva configuración regional
     * @param lenguaje Nuevo código de idioma
     * @param pais Nuevo código de país
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene la configuración regional (locale) actualmente en uso
     * @return Objeto Locale con la configuración actual de idiomas/país
     */
    public Locale getLocale() {
        return locale;
    }
}