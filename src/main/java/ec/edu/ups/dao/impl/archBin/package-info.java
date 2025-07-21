/**
 * <p>Este paquete contiene las implementaciones concretas de los objetos de acceso a datos (DAO)
 * que gestionan la persistencia de la información en archivos binarios.</p>
 *
 * <p>Las clases en este paquete son responsables de leer y escribir objetos Java directamente
 * desde y hacia el disco utilizando la serialización de objetos de Java ({@link java.io.ObjectOutputStream}
 * y {@link java.io.ObjectInputStream}). Esto permite un almacenamiento eficiente de datos
 * en un formato binario.</p>
 *
 * <p>Para que la serialización funcione correctamente, todas las clases de modelo (entidades)
 * que son almacenadas por estos DAOs deben implementar la interfaz {@link java.io.Serializable}.</p>
 */
package ec.edu.ups.dao.impl.archBin;