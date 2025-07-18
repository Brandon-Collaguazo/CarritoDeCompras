package ec.edu.ups.utils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Renderizador personalizado para celdas de JTable que muestra botones de acción
 * <p>
 *     Muestra botones "Modificar" y "Eliminar" en una celda de tabla. A diferencia del ButtonEditor,
 *     esta clase solo maneja la representación visual.
 * </p>
 */
public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton modifyButton;
    private final JButton deleteButton;

    /**
     * Crea un nuevo renderizador de botones con configuración predeterminada.
     * <p>
     *     Inicializa los botones con textos "Modificar" y "Eliminar" en un layout horizontal centrado
     * </p>
     */
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        modifyButton = new JButton("Modificar");
        deleteButton = new JButton("Eliminar");
        add(modifyButton);
        add(deleteButton);
    }

    /**
     * {@inheritDoc}
     * @return Componente JPanel que contiene los botones de acción
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}