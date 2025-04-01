package controller;

import model.Joueur;
import view.PanelEquipe;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MajTableauJoueurSansEquipe {

    public static void mettreAJour(PanelEquipe panel, List<Joueur> joueurs) {
        DefaultTableModel model = panel.getModelJoueurs();
        model.setRowCount(0); // vide le tableau

        for (Joueur j : joueurs) {
            if (j.getNumeroEquipe() == null) {
                model.addRow(new Object[]{
                    j.getNom(),
                    j.getPrenom(),
                    j.getCategorie()
                });
            }
        }
    }
}
