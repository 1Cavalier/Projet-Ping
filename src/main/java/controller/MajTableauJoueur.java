package controller;

import model.Joueur;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MajTableauJoueur {

    public static void mettreAJour(DefaultTableModel model, List<Joueur> listeJoueurs) {
        // Vider le tableau actuel
        model.setRowCount(0);

        // Réinsérer les données
        for (Joueur joueur : listeJoueurs) {
            model.addRow(new Object[]{
                joueur.getNom(),
                joueur.getPrenom(),
                joueur.getCategorie(),
                joueur.getNumeroEquipe() != null ? joueur.getNumeroEquipe() : "Non assigné"
            });
        }
    }
}
