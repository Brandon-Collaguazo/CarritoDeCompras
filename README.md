# 🛒 Proyecto de Ejemplo - Carrito de Compras

**Estudiante:** Brandon Collaguazo  
**Universidad Politécnica Salesiana - POO Periodo 66**  

---

## 📚 Contexto Académico

Este proyecto fue desarrollado inicialmente en clases con el profesor y finalizado por el estudiante, demostrando la aplicación de:

- ✅ Patrones de diseño avanzados.
- ✅ Principios SOLID.
- ✅ Buenas prácticas de programación en Java.

## 🌍 Características Destacadas

### 🔤 Internacionalización
- Soporte para 3 idiomas: Español (default), Inglés y Francés.
- Cambio dinámico de idioma durante la ejecución.
- Todos los textos cargados desde archivos `.properties`.

### 🎨 Elementos Visuales Mejorados
- Iconos profesionales en botones principales.
- Gráficos de tablas personalizados (JTable).
- Renderizado especial para botones en celdas.
- Diseño responsive para diferentes resoluciones.

### 🛠️ Manejo de Excepciones
- Implementación de excepciones personalizadas para validar entradas de usuario (ej. `CedulaException`, `ContraseniaException`, `CorreoException`, `FechaException`).
- Mecanismos de manejo de errores que mejoran la robustez del sistema y la experiencia del usuario.

### 📁 Manejo de Archivos
- Persistencia de datos utilizando archivos de texto plano y binarios.
- Implementación de DAOs para gestionar la lectura y escritura de usuarios, productos y carritos en diferentes formatos de archivo.

## 🛠️ Tecnologías Utilizadas

- 💻 **Java 21**
- 🧰 **IntelliJ IDEA** (recomendado con el plugin de diseñador gráfico de interfaces Swing)
- ☕ **Swing** para la interfaz gráfica.
- 📦 **Estructura modular** basada en paquetes: modelo, dao, controlador, vista y servicio.

## 🧱 Patrones de Diseño Aplicados

- **MVC (Modelo - Vista - Controlador):** Para separar la lógica de negocio de la interfaz gráfica.
- **DAO (Data Access Object):** Para desacoplar el acceso a los datos, facilitando la migración a diferentes fuentes (archivos, base de datos, etc.).
- **SRP y DIP de los principios SOLID:** Para asegurar una arquitectura mantenible, extensible y fácil de testear.

## 💡 Aprendizajes Obtenidos

- Implementación práctica de MVC y DAO.
- Uso de `ResourceBundle` para internacionalización.
- Personalización avanzada de componentes Swing.
- Aplicación de principios SOLID en un proyecto real.
- Desarrollo de un manejo efectivo de excepciones y archivos para mejorar la integridad y persistencia de datos.

## ⚠️ Áreas de Mejora

Aunque el proyecto ha alcanzado sus objetivos, hay áreas que se pueden mejorar, como la optimización de la interfaz de usuario y la exploración de soluciones de persistencia más avanzadas para futuros escalados.

---
