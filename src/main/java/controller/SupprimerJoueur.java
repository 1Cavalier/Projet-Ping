package controller;

import model.Joueur;
import view.PanelEquipe;
import view.PanelJoueur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupprimerJoueur implements ActionListener {

    private final PanelJoueur panelJoueur;
    private final PanelEquipe panelEquipe;
    private final int index;
    private final List<Joueur> listeJoueurs;
    private final JDialog dialog;

    public SupprimerJoueur(PanelJoueur panelJoueur, PanelEquipe panelEquipe,
            List<Joueur> listeJoueurs, int index, JDialog dialog) {
        this.panelJoueur = panelJoueur;
        this.panelEquipe = panelEquipe;
        this.listeJoueurs = listeJoueurs;
        this.index = index;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Joueur joueur = listeJoueurs.get(index);

        if (joueur.getNumeroEquipe() != null) {
            JOptionPane.showMessageDialog(panelJoueur,
                    "Impossible de supprimer ce joueur : il appartient à l'équipe n°" + joueur.getNumeroEquipe() +
                            ".\nVeuillez d'abord le retirer de son équipe.",
                    "Suppression interdite", JOptionPane.WARNING_MESSAGE);
            return;
        }

        listeJoueurs.remove(index);

        // 🔁 Mise à jour des tableaux
        panelJoueur.rafraichirTableau();
        MajTableauJoueurSansEquipe.mettreAJour(panelEquipe, listeJoueurs);

        dialog.dispose();
    }
}
