package ec.edu.ups.modelo;

/**
 * Enum Rol
 * Enumeración que representa los diferentes roles de usuario en el sistema.
 * Define los tipos de permisos y accesos que tendrán los usuarios.
 */
public enum Rol {
    /**
     * Rol con privilegios administrativos completos.
     * Permite el acceso a todas las funcionalidades del sistema.
     * - Gestión de usuarios
     * - Gestión de productos
     * - Gestión de carritos
     */
    ADMINISTRADOR,

    /**
     * Rol estándar para usuarios normales del sistema.
     * Permite el acceso limitado a funcionalidades básicas:
     * - Realizar compras (generar carrito)
     * - Gestionar su propia información
     */
    USUARIO,
}
