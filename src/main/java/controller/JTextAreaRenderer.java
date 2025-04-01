package controller;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class JTextAreaRenderer extends JTextArea implements TableCellRenderer {

    public JTextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        // Ajuste dynamiquement la hauteur de la ligne
        table.setRowHeight(row, getPreferredSize().height);
        return this;
    }
}
