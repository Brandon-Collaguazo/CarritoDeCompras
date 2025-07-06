package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        // Constructor simplificado sin animaciones
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // ====== FONDO DEGRADADO AZUL CLARO ======
        GradientPaint background = new GradientPaint(
                0, 0, new Color(173, 216, 230),  // Azul claro (Light Blue)
                getWidth(), getHeight(), new Color(135, 206, 235)  // Azul cielo (Sky Blue)
        );
        g2.setPaint(background);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // ====== ELEMENTOS DE FONDO ======
        drawFloatingIcons(g2);

        // ====== CONTENEDOR PRINCIPAL CON GLASSMORPHISM ======
        int containerWidth = 500;
        int containerHeight = 400;
        int containerX = cx - containerWidth / 2;
        int containerY = cy - containerHeight / 2;

        // Sombra del contenedor
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(containerX + 5, containerY + 5, containerWidth, containerHeight, 25, 25);

        // Contenedor principal con efecto de cristal
        g2.setColor(new Color(255, 255, 255, 20));
        g2.fillRoundRect(containerX, containerY, containerWidth, containerHeight, 25, 25);

        // Borde del contenedor
        g2.setColor(new Color(255, 255, 255, 40));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(containerX, containerY, containerWidth, containerHeight, 25, 25);

        // ====== LOGOTIPO MEJORADO ======
        int logoSize = 100;
        int logoX = cx - logoSize / 2;
        int logoY = containerY + 40;

        // Sombra del logo
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval(logoX + 3, logoY + 3, logoSize, logoSize);

        // Degradado para el círculo del logo (ajustado para el fondo azul)
        GradientPaint logoGradient = new GradientPaint(
                logoX, logoY, new Color(70, 130, 180),
                logoX + logoSize, logoY + logoSize, new Color(30, 80, 140)
        );
        g2.setPaint(logoGradient);
        g2.fillOval(logoX, logoY, logoSize, logoSize);

        // Icono de carrito mejorado
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Cuerpo del carrito
        g2.drawArc(logoX + 25, logoY + 30, 50, 35, 0, -180);
        g2.drawLine(logoX + 35, logoY + 50, logoX + 65, logoY + 50);

        // Ruedas con efecto 3D
        g2.setColor(new Color(220, 220, 220));
        g2.fillOval(logoX + 32, logoY + 60, 10, 10);
        g2.fillOval(logoX + 58, logoY + 60, 10, 10);
        g2.setColor(new Color(180, 180, 180));
        g2.fillOval(logoX + 34, logoY + 62, 6, 6);
        g2.fillOval(logoX + 60, logoY + 62, 6, 6);

        // Mango
        g2.setColor(Color.WHITE);
        g2.drawLine(logoX + 65, logoY + 40, logoX + 75, logoY + 25);
        g2.drawLine(logoX + 75, logoY + 25, logoX + 85, logoY + 25);

        // ====== TÍTULO PRINCIPAL CON EFECTOS ======
        String title = "OnlineMarket";
        Font titleFont = new Font("Segoe UI", Font.BOLD, 42);
        g2.setFont(titleFont);

        // Sombra del título
        g2.setColor(new Color(0, 0, 0, 100));
        FontMetrics fm = g2.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2;
        int titleY = logoY + logoSize + 60;
        g2.drawString(title, titleX + 2, titleY + 2);

        // Título principal con brillo
        g2.setColor(Color.WHITE);
        g2.drawString(title, titleX, titleY);

        // ====== TAGLINE MEJORADO ======
        String tagline = "Tu mercado en línea favorito";
        Font taglineFont = new Font("Segoe UI", Font.PLAIN, 18);
        g2.setFont(taglineFont);

        g2.setColor(new Color(220, 220, 220));
        int taglineWidth = g2.getFontMetrics().stringWidth(tagline);
        int taglineX = (getWidth() - taglineWidth) / 2;
        int taglineY = titleY + 40;
        g2.drawString(tagline, taglineX, taglineY);

        // ====== LÍNEA DIVISORIA ANIMADA ======
        g2.setStroke(new BasicStroke(2f));
        int lineY = taglineY + 30;

        // Línea con gradiente
        GradientPaint lineGradient = new GradientPaint(
                cx - 150, lineY, new Color(255, 255, 255, 0),
                cx, lineY, new Color(255, 255, 255, 150),
                true
        );
        g2.setPaint(lineGradient);
        g2.drawLine(cx - 150, lineY, cx + 150, lineY);

        // Punto central brillante
        g2.setColor(Color.WHITE);
        g2.fillOval(cx - 3, lineY - 3, 6, 6);

        // ====== ICONOS DE PRODUCTOS MEJORADOS ======
        drawProductIcons(g2, cx, lineY + 40);
    }

    private void drawFloatingIcons(Graphics2D g2) {
        // Iconos estáticos de tecnología en el fondo
        g2.setColor(new Color(255, 255, 255, 20));
        Font iconFont = new Font("Segoe UI Emoji", Font.PLAIN, 24);
        g2.setFont(iconFont);

        String[] techIcons = {"💻", "📱", "🖥️", "⌚", "🎧", "📷"};
        int[] xPositions = {50, getWidth() - 80, 100, getWidth() - 120, 80, getWidth() - 100};
        int[] yPositions = {100, 150, getHeight() - 150, getHeight() - 100, getHeight() - 200, 80};

        for (int i = 0; i < techIcons.length && i < xPositions.length; i++) {
            if (xPositions[i] < getWidth() && yPositions[i] < getHeight()) {
                g2.drawString(techIcons[i], xPositions[i], yPositions[i]);
            }
        }
    }

    private void drawProductIcons(Graphics2D g2, int cx, int y) {
        Color[] colors = {
                new Color(74, 144, 226),  // Laptop - Azul
                new Color(156, 39, 176),  // Smartphone - Morado
                new Color(76, 175, 80),   // Tablet - Verde
                new Color(255, 152, 0),   // Auriculares - Naranja
                new Color(244, 67, 54)    // Cámara - Rojo
        };

        String[] techIcons = {"💻", "📱", "📳", "🎧", "📷"};
        String[] techNames = {"Laptops", "Smartphones", "Tablets", "Audio", "Cámaras"};

        int iconSize = 50;
        int spacing = 70;
        int startX = cx - (spacing * (techIcons.length - 1)) / 2;

        for (int i = 0; i < techIcons.length; i++) {
            int iconX = startX + i * spacing - iconSize / 2;
            int iconY = y - iconSize / 2;

            // Sombra
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillOval(iconX + 2, iconY + 2, iconSize, iconSize);

            // Círculo de fondo
            GradientPaint iconGradient = new GradientPaint(
                    iconX, iconY, colors[i],
                    iconX + iconSize, iconY + iconSize, colors[i].darker()
            );
            g2.setPaint(iconGradient);
            g2.fillOval(iconX, iconY, iconSize, iconSize);

            // Emoji
            g2.setColor(Color.WHITE);
            Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 28);
            g2.setFont(emojiFont);
            FontMetrics emojiMetrics = g2.getFontMetrics();
            int emojiWidth = emojiMetrics.stringWidth(techIcons[i]);
            int emojiHeight = emojiMetrics.getHeight();
            g2.drawString(techIcons[i],
                    iconX + (iconSize - emojiWidth) / 2,
                    iconY + (iconSize + emojiHeight / 2) / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("OnlineMarket - Mercado en Línea");
        MiJDesktopPane desktopPane = new MiJDesktopPane();

        frame.setContentPane(desktopPane);
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}