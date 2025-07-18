/**
 * Paquete para el acceso a datos y persistencia de la aplicación.
 *
 * <p><b>Arquitectura:</b></p>
 * <ul>
 *   <li><b>Interfaces DAO</b>: Definiciones de contrato para operaciones CRUD</li>
 *   <li><b>Implementaciones</b>:
 *     <ul>
 *       <li><code>memory</code>: Implementación en memoria (volátil)</li>
 *       <li><code>txt</code>: Persistencia en archivos de texto plano</li>
 *       <li><code>bin</code>: Persistencia en archivos binarios</li>
 *     </ul>
 *   </li>
 * </ul>
 */
package ec.edu.ups.dao;