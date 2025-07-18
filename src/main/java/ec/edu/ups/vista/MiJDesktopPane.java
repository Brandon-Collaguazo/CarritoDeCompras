package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*; // Se importa si se usa para Path2D, Ellipse2D, etc. (actualmente no directamente en este código)

/**
 * La clase `MiJDesktopPane` es una extensión de `JDesktopPane` que proporciona
 * un fondo personalizado y elementos gráficos dibujados directamente sobre el panel.
 * Incluye un fondo degradado, un contenedor central con efecto de "glassmorphism",
 * un logotipo y un título con efectos visuales, y una serie de iconos flotantes
 * y de productos para dar una estética de "mercado en línea".
 */
public class MiJDesktopPane extends JDesktopPane {

    /**
     * Constructor de la clase `MiJDesktopPane`.
     * Se mantiene simplificado, ya que la mayor parte de la lógica de dibujo
     * se encuentra en el método `paintComponent`.
     */
    public MiJDesktopPane() {
        // Constructor simplificado sin animaciones
        // Puedes añadir aquí configuraciones iniciales si son necesarias.
    }

    /**
     * Sobrescribe el método `paintComponent` para realizar el dibujo personalizado
     * del fondo y los elementos gráficos en el `JDesktopPane`.
     *
     * @param g El contexto gráfico en el que se va a dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Asegura que los componentes hijos (JInternalFrames) se dibujen correctamente.

        Graphics2D g2 = (Graphics2D) g; // Convierte a Graphics2D para usar características avanzadas.
        // Configura las sugerencias de renderizado para mejorar la calidad visual (antialiasing, calidad de renderizado, antialiasing de texto).
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = getWidth() / 2; // Coordenada X central del panel.
        int cy = getHeight() / 2; // Coordenada Y central del panel.

        // ====== FONDO DEGRADADO AZUL CLARO ======
        // Crea un degradado de color para el fondo, desde un azul claro a un azul cielo.
        GradientPaint background = new GradientPaint(
                0, 0, new Color(173, 216, 230),  // Azul claro (Light Blue) - Color de inicio
                getWidth(), getHeight(), new Color(135, 206, 235)  // Azul cielo (Sky Blue) - Color final
        );
        g2.setPaint(background); // Establece el degradado como la pintura actual.
        g2.fillRect(0, 0, getWidth(), getHeight()); // Rellena todo el panel con el degradado.

        // ====== ELEMENTOS DE FONDO ======
        // Llama a un método auxiliar para dibujar iconos estáticos flotantes en el fondo.
        drawFloatingIcons(g2);

        // ====== CONTENEDOR PRINCIPAL CON GLASSMORPHISM ======
        int containerWidth = 500;
        int containerHeight = 400;
        int containerX = cx - containerWidth / 2; // Calcula la posición X para centrar el contenedor.
        int containerY = cy - containerHeight / 2; // Calcula la posición Y para centrar el contenedor.

        // Sombra del contenedor
        g2.setColor(new Color(0, 0, 0, 30)); // Color de sombra semi-transparente.
        g2.fillRoundRect(containerX + 5, containerY + 5, containerWidth, containerHeight, 25, 25); // Dibuja la sombra desplazada.

        // Contenedor principal con efecto de cristal (glassmorphism)
        g2.setColor(new Color(255, 255, 255, 20)); // Color blanco muy transparente.
        g2.fillRoundRect(containerX, containerY, containerWidth, containerHeight, 25, 25); // Rellena el contenedor.

        // Borde del contenedor
        g2.setColor(new Color(255, 255, 255, 40)); // Color blanco semi-transparente para el borde.
        g2.setStroke(new BasicStroke(1.5f)); // Grosor del borde.
        g2.drawRoundRect(containerX, containerY, containerWidth, containerHeight, 25, 25); // Dibuja el borde.

        // ====== LOGOTIPO MEJORADO ======
        int logoSize = 100;
        int logoX = cx - logoSize / 2; // Calcula la posición X para centrar el logo.
        int logoY = containerY + 40; // Posiciona el logo dentro del contenedor.

        // Sombra del logo
        g2.setColor(new Color(0, 0, 0, 50)); // Sombra más pronunciada para el logo.
        g2.fillOval(logoX + 3, logoY + 3, logoSize, logoSize); // Dibuja la sombra del círculo del logo.

        // Degradado para el círculo del logo
        GradientPaint logoGradient = new GradientPaint(
                logoX, logoY, new Color(70, 130, 180), // Color de inicio (azul más oscuro)
                logoX + logoSize, logoY + logoSize, new Color(30, 80, 140) // Color final (azul aún más oscuro)
        );
        g2.setPaint(logoGradient); // Establece el degradado.
        g2.fillOval(logoX, logoY, logoSize, logoSize); // Rellena el círculo del logo.

        // Icono de carrito mejorado
        g2.setColor(Color.WHITE); // Color blanco para el icono.
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Grosor y estilo de línea.

        // Cuerpo del carrito
        g2.drawArc(logoX + 25, logoY + 30, 50, 35, 0, -180); // Dibuja la parte superior del carrito.
        g2.drawLine(logoX + 35, logoY + 50, logoX + 65, logoY + 50); // Dibuja la base del carrito.

        // Ruedas con efecto 3D
        g2.setColor(new Color(220, 220, 220)); // Color claro para la rueda base.
        g2.fillOval(logoX + 32, logoY + 60, 10, 10); // Rueda izquierda.
        g2.fillOval(logoX + 58, logoY + 60, 10, 10); // Rueda derecha.
        g2.setColor(new Color(180, 180, 180)); // Color más oscuro para el centro de la rueda.
        g2.fillOval(logoX + 34, logoY + 62, 6, 6); // Centro rueda izquierda.
        g2.fillOval(logoX + 60, logoY + 62, 6, 6); // Centro rueda derecha.

        // Mango del carrito
        g2.setColor(Color.WHITE);
        g2.drawLine(logoX + 65, logoY + 40, logoX + 75, logoY + 25);
        g2.drawLine(logoX + 75, logoY + 25, logoX + 85, logoY + 25);

        // ====== TÍTULO PRINCIPAL CON EFECTOS ======
        String title = "OnlineMarket";
        Font titleFont = new Font("Segoe UI", Font.BOLD, 42); // Fuente y tamaño del título.
        g2.setFont(titleFont); // Establece la fuente.

        // Sombra del título
        g2.setColor(new Color(0, 0, 0, 100)); // Color de sombra semi-transparente.
        FontMetrics fm = g2.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2; // Centra el título horizontalmente.
        int titleY = logoY + logoSize + 60; // Posiciona el título debajo del logo.
        g2.drawString(title, titleX + 2, titleY + 2); // Dibuja la sombra del título.

        // Título principal con brillo
        g2.setColor(Color.WHITE); // Color blanco para el título principal.
        g2.drawString(title, titleX, titleY); // Dibuja el título principal.

        // ====== TAGLINE MEJORADO ======
        String tagline = "Tu mercado en línea favorito";
        Font taglineFont = new Font("Segoe UI", Font.PLAIN, 18); // Fuente y tamaño del tagline.
        g2.setFont(taglineFont); // Establece la fuente.

        g2.setColor(new Color(220, 220, 220)); // Color gris claro para el tagline.
        int taglineWidth = g2.getFontMetrics().stringWidth(tagline);
        int taglineX = (getWidth() - taglineWidth) / 2; // Centra el tagline horizontalmente.
        int taglineY = titleY + 40; // Posiciona el tagline debajo del título.
        g2.drawString(tagline, taglineX, taglineY); // Dibuja el tagline.

        // ====== LÍNEA DIVISORIA ANIMADA ====== (Aunque sin animación, se dibuja estáticamente)
        g2.setStroke(new BasicStroke(2f)); // Grosor de la línea.
        int lineY = taglineY + 30; // Posición Y de la línea.

        // Línea con gradiente
        GradientPaint lineGradient = new GradientPaint(
                cx - 150, lineY, new Color(255, 255, 255, 0),    // Inicio transparente
                cx, lineY, new Color(255, 255, 255, 150), // Centro semi-transparente
                true // Repetir el gradiente
        );
        g2.setPaint(lineGradient); // Establece el degradado.
        g2.drawLine(cx - 150, lineY, cx + 150, lineY); // Dibuja la línea.

        // Punto central brillante
        g2.setColor(Color.WHITE); // Color blanco para el punto.
        g2.fillOval(cx - 3, lineY - 3, 6, 6); // Dibuja un pequeño óvalo en el centro de la línea.

        // ====== ICONOS DE PRODUCTOS MEJORADOS ======
        drawProductIcons(g2, cx, lineY + 40); // Llama a un método auxiliar para dibujar iconos de productos.
    }

    /**
     * Dibuja iconos estáticos de tecnología flotando en el fondo del panel.
     * Estos iconos son semi-transparentes para un efecto sutil.
     *
     * @param g2 El contexto gráfico 2D.
     */
    private void drawFloatingIcons(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 20)); // Color blanco muy transparente.
        Font iconFont = new Font("Segoe UI Emoji", Font.PLAIN, 24); // Fuente para emojis.
        g2.setFont(iconFont);

        String[] techIcons = {"💻", "📱", "🖥️", "⌚", "🎧", "📷"}; // Array de emojis de tecnología.
        int[] xPositions = {50, getWidth() - 80, 100, getWidth() - 120, 80, getWidth() - 100}; // Posiciones X predefinidas.
        int[] yPositions = {100, 150, getHeight() - 150, getHeight() - 100, getHeight() - 200, 80}; // Posiciones Y predefinidas.

        // Dibuja cada icono en su posición.
        for (int i = 0; i < techIcons.length && i < xPositions.length; i++) {
            if (xPositions[i] < getWidth() && yPositions[i] < getHeight()) {
                g2.drawString(techIcons[i], xPositions[i], yPositions[i]);
            }
        }
    }

    /**
     * Dibuja una serie de iconos de productos con un fondo circular degradado.
     *
     * @param g2 El contexto gráfico 2D.
     * @param cx La coordenada X central de referencia para posicionar los iconos.
     * @param y  La coordenada Y de referencia para posicionar los iconos.
     */
    private void drawProductIcons(Graphics2D g2, int cx, int y) {
        Color[] colors = {
                new Color(74, 144, 226),  // Laptop - Azul
                new Color(156, 39, 176),  // Smartphone - Morado
                new Color(76, 175, 80),   // Tablet - Verde
                new Color(255, 152, 0),   // Auriculares - Naranja
                new Color(244, 67, 54)    // Cámara - Rojo
        };

        String[] techIcons = {"💻", "📱", "📳", "🎧", "📷"}; // Emojis de productos.
        String[] techNames = {"Laptops", "Smartphones", "Tablets", "Audio", "Cámaras"}; // Nombres (no usados en el dibujo, pero útiles para referencia).

        int iconSize = 50;
        int spacing = 70; // Espacio entre iconos.
        int startX = cx - (spacing * (techIcons.length - 1)) / 2; // Calcula la posición inicial para centrar el grupo de iconos.

        for (int i = 0; i < techIcons.length; i++) {
            int iconX = startX + i * spacing - iconSize / 2; // Posición X para el icono actual.
            int iconY = y - iconSize / 2; // Posición Y para el icono actual.

            // Sombra del círculo de fondo
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillOval(iconX + 2, iconY + 2, iconSize, iconSize);

            // Círculo de fondo con degradado
            GradientPaint iconGradient = new GradientPaint(
                    iconX, iconY, colors[i], // Color de inicio del degradado.
                    iconX + iconSize, iconY + iconSize, colors[i].darker() // Color final del degradado.
            );
            g2.setPaint(iconGradient); // Establece el degradado.
            g2.fillOval(iconX, iconY, iconSize, iconSize); // Rellena el círculo.

            // Emoji del producto
            g2.setColor(Color.WHITE); // Color blanco para el emoji.
            Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 28); // Fuente para el emoji.
            g2.setFont(emojiFont);
            FontMetrics emojiMetrics = g2.getFontMetrics();
            int emojiWidth = emojiMetrics.stringWidth(techIcons[i]);
            int emojiHeight = emojiMetrics.getHeight();
            // Dibuja el emoji centrado dentro del círculo.
            g2.drawString(techIcons[i],
                    iconX + (iconSize - emojiWidth) / 2,
                    iconY + (iconSize + emojiHeight / 2) / 2);
        }
    }

    /**
     * Método `main` para probar la visualización de `MiJDesktopPane` en un `JFrame`.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("OnlineMarket - Mercado en Línea"); // Crea un nuevo JFrame.
        MiJDesktopPane desktopPane = new MiJDesktopPane(); // Crea una instancia de MiJDesktopPane.

        frame.setContentPane(desktopPane); // Establece el MiJDesktopPane como contenido del frame.
        frame.setSize(900, 700); // Define el tamaño inicial de la ventana.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura el comportamiento al cerrar la ventana.
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla.
        frame.setResizable(true); // Permite que la ventana sea redimensionable.
        frame.setVisible(true); // Hace visible la ventana.
    }
}