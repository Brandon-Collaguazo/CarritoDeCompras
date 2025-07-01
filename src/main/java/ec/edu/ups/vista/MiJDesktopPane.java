package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MiJDesktopPane extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // Definir colores
        Color cartBodyColor = new Color(192, 192, 192); // Gris plata
        Color cartHighlightColor = new Color(220, 220, 220); // Gris claro para brillos
        Color cartShadowColor = new Color(120, 120, 120); // Gris oscuro para sombras
        Color handleColor = new Color(150, 0, 0); // Rojo oscuro para el mango
        Color wheelColor = new Color(50, 50, 50); // Gris muy oscuro para las ruedas
        Color wheelRimColor = new Color(100, 100, 100); // Gris medio para el borde de las ruedas
        Color contentsColor = new Color(180, 120, 70); // Marrón para el contenido

        // ====== TÍTULO ======
        String title = "Sistema de Carrito de Compras Online";
        g2.setColor(Color.BLACK); // Color del título
        g2.setFont(new Font("Roboto", Font.BOLD, 30)); // Fuente y tamaño del título

        // Calcular las dimensiones del texto para centrarlo
        FontMetrics fm = g2.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2;
        int titleY = 50; // Posición Y para el título (más arriba)

        g2.drawString(title, titleX, titleY);

        // ====== CARRITO DE COMPRAS ======
        // Ajustar la posición Y del carrito para dejar espacio al título
        int cartWidth = 280;
        int cartHeight = 150;
        int cartX = cx - cartWidth / 2;
        int cartY = cy - cartHeight / 2 + 50; // Ajustar posición Y para bajarlo un poco

        // Fondo del carrito (para dar sensación de profundidad)
        g2.setColor(cartShadowColor);
        g2.fillRoundRect(cartX + 5, cartY + 10, cartWidth - 10, cartHeight - 20, 20, 20);

        // Cuerpo principal del carrito - usando un gradiente para volumen
        GradientPaint gp = new GradientPaint(cartX, cartY, cartHighlightColor,
                cartX + cartWidth, cartY + cartHeight, cartBodyColor);
        g2.setPaint(gp);
        g2.fillRoundRect(cartX, cartY, cartWidth, cartHeight, 25, 25);

        // Borde superior del carrito (más grueso)
        g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(cartShadowColor.darker());
        g2.drawRoundRect(cartX, cartY, cartWidth, cartHeight, 25, 25);

        // Rejilla interna (verticales) - más finas y con un ligero efecto 3D
        g2.setStroke(new BasicStroke(2));
        g2.setColor(cartHighlightColor); // Color más claro para las rejillas
        for (int i = 1; i < 7; i++) {
            int x = cartX + i * cartWidth / 7;
            g2.drawLine(x, cartY + 15, x, cartY + cartHeight - 15);
        }

        // Rejilla interna (horizontales)
        for (int i = 1; i < 4; i++) {
            int y = cartY + i * cartHeight / 4;
            g2.drawLine(cartX + 15, y, cartX + cartWidth - 15, y);
        }

        // Mango del carrito
        g2.setColor(handleColor);
        g2.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cartX + cartWidth - 10, cartY + 30, cartX + cartWidth + 40, cartY - 10);
        g2.draw(new Arc2D.Double(cartX + cartWidth + 20, cartY - 30, 40, 40, 90, 90, Arc2D.OPEN));

        // Ruedas
        int wheelSize = 40;
        int wheelOffsetY = cartHeight + 10;

        // Rueda trasera izquierda
        g2.setColor(wheelColor);
        g2.fillOval(cartX + 20, cartY + wheelOffsetY, wheelSize, wheelSize);
        g2.setColor(wheelRimColor);
        g2.drawOval(cartX + 20, cartY + wheelOffsetY, wheelSize, wheelSize); // Borde
        g2.setColor(Color.BLACK);
        g2.fillOval(cartX + 20 + wheelSize/4, cartY + wheelOffsetY + wheelSize/4, wheelSize/2, wheelSize/2); // Centro

        // Rueda delantera derecha (ligeramente más adelante y abajo para perspectiva)
        g2.setColor(wheelColor);
        g2.fillOval(cartX + cartWidth - wheelSize - 20, cartY + wheelOffsetY + 5, wheelSize, wheelSize);
        g2.setColor(wheelRimColor);
        g2.drawOval(cartX + cartWidth - wheelSize - 20, cartY + wheelOffsetY + 5, wheelSize, wheelSize); // Borde
        g2.setColor(Color.BLACK);
        g2.fillOval(cartX + cartWidth - wheelSize - 20 + wheelSize/4, cartY + wheelOffsetY + 5 + wheelSize/4, wheelSize/2, wheelSize/2); // Centro

        // Simular contenido en el carrito (ej. cajas)
        g2.setColor(contentsColor);
        int contentX = cartX + 40;
        int contentY = cartY + 40;
        g2.fillRoundRect(contentX, contentY, 80, 40, 10, 10);
        g2.fillRoundRect(contentX + 70, contentY + 20, 60, 30, 8, 8);
        g2.fillRoundRect(contentX + 130, contentY - 10, 50, 50, 12, 12);

        // Algunas líneas de detalle en las cajas
        g2.setColor(contentsColor.darker());
        g2.drawRect(contentX + 5, contentY + 5, 70, 30);
        g2.drawRect(contentX + 75, contentY + 25, 50, 20);
        g2.drawRect(contentX + 135, contentY - 5, 40, 40);

        // Sombra general del carrito (debajo)
        g2.setColor(new Color(0, 0, 0, 50)); // Negro semitransparente
        g2.fillOval(cartX + 20, cartY + cartHeight + wheelSize, cartWidth - 40, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo de Carrito de Compras"); // Cambiar el título de la ventana
        MiJDesktopPane desktopPane = new MiJDesktopPane();
        frame.setContentPane(desktopPane);
        frame.setSize(600, 500); // Tamaño adecuado para visualizar el carrito y el título
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }
}