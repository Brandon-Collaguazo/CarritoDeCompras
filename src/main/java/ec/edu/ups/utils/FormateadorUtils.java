package ec.edu.ups.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria para formateo de valores monetarios y fechas segun localización.
 * Proporciona métodos estáticos para formatear cantidades monetarias y fechas
 * según las convenciones de un Local específico
 */
public class FormateadorUtils {

    /**
     * Formatea la cantidad monetaria según las convenciones del locale especificado.
     *
     * @param cantidad Valor numérico a formatear
     * @param locale Localización que determina el formato (símbolo, separadores, etc)
     * @return Cadena formateada con la moneda
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return  formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha según el estilo MEDIUM del locale especificado.
     *
     * @param fecha Objeto Date a formatear
     * @param locale Localización que determina el formato de fecha
     * @return Cadena con la fecha formateada
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}
