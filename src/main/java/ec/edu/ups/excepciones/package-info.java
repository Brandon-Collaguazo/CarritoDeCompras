/**
 * Paquete que contiene las clases de excepciones personalizadas para la aplicación.
 *
 * <p>Este paquete agrupa las excepciones específicas del dominio de la aplicación,
 * tanto excepciones verificadas (checked) como no verificadas (unchecked).</p>
 *
 * <p>Las excepciones incluidas son:</p>
 * <ul>
 *   <li>{@link ec.edu.ups.excepciones.CamposException} - Para campos obligatorios faltantes</li>
 *   <li>{@link ec.edu.ups.excepciones.CedulaException} - Para errores en validación de cédulas</li>
 *   <li>{@link ec.edu.ups.excepciones.CorreoException} - Para errores en formato de correo electrónico</li>
 *   <li>{@link ec.edu.ups.excepciones.FechaException} - Para errores en formato de fechas</li>
 * </ul>
 *
 * <p>Estas excepciones permiten un manejo más granular de errores específicos
 * de la lógica de negocio de la aplicación.</p>
 *
 */
package ec.edu.ups.excepciones;