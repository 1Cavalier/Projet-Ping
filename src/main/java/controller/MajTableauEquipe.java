package controller;

import model.Equipe;
import view.PanelEquipe;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class MajTableauEquipe {

    public static void mettreAJour(PanelEquipe panel, List<Equipe> equipes) {
        DefaultTableModel model = panel.getModelEquipes();
        model.setRowCount(0); // reset

        for (Equipe eq : equipes) {
            String joueursStr = eq.getJoueurs().stream()
                    .filter(j -> j != null)
                    .map(j -> j.getNom() + " " + j.getPrenom())
                    .collect(Collectors.joining("\n"));

            model.addRow(new Object[]{
                    eq.getNumeroEquipe(),
                    joueursStr.isEmpty() ? "(Aucun joueur)" : joueursStr
            });
        }

        // 🔁 Forcer rendu multi-ligne
        panel.getTableEquipes().getColumnModel().getColumn(1)
                .setCellRenderer(new JTextAreaRenderer());
    }
}
