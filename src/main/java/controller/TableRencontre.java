package controller;

import model.Table;
import model.Rencontre;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableRencontre extends MouseAdapter {

    private final Table table;

    public TableRencontre(Table table) {
        this.table = table;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (table.isOccupee() && table.getRencontreActuelle() != null) {
                Rencontre r = table.getRencontreActuelle();
                JOptionPane.showMessageDialog(null,
                        "Rencontre en cours : Équipe " + r.getEquipeA().getNumeroEquipe() +
                                " vs Équipe " + r.getEquipeB().getNumeroEquipe(),
                        "Rencontre", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Aucune rencontre en cours sur cette table.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
