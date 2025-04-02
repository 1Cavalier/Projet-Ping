package controller;

import model.Table;
import view.PanelSalle;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableDispo extends MouseAdapter {

    private final Table table;
    private final JLabel imageLabel;
    private final PanelSalle panel;

    public TableDispo(Table table, JLabel imageLabel, PanelSalle panel) {
        this.table = table;
        this.imageLabel = imageLabel;
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (table.isOccupee()) {
                JOptionPane.showMessageDialog(panel,
                        "Impossible de désactiver cette table : une rencontre est en cours.",
                        "Table occupée", JOptionPane.WARNING_MESSAGE);
                return;
            }

            table.setActive(!table.isActive());
            panel.mettreAJourTableGraphique(table, imageLabel, 150, 100);
        }
    }
}
