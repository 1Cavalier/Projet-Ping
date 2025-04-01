package controller;

import model.Categorie;
import model.Joueur;
import view.PanelJoueur;
import view.PanelEquipe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AjouterJoueur implements ActionListener {

    private final PanelJoueur panelJoueur;
    private final PanelEquipe panelEquipe;
    private final List<Joueur> listeJoueurs;

    public AjouterJoueur(PanelJoueur panelJoueur, PanelEquipe panelEquipe, List<Joueur> listeJoueurs) {
        this.panelJoueur = panelJoueur;
        this.panelEquipe = panelEquipe;
        this.listeJoueurs = listeJoueurs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nom = panelJoueur.getNomField().getText().trim();
        String prenom = panelJoueur.getPrenomField().getText().trim();
        Categorie categorie = (Categorie) panelJoueur.getCategorieCombo().getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(panelJoueur, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Joueur nouveauJoueur = new Joueur(nom, prenom, categorie);
        listeJoueurs.add(nouveauJoueur);

        // Mettre à jour les 2 panels
        MajTableauJoueur.mettreAJour(panelJoueur.getTableModel(), listeJoueurs);
        MajTableauJoueurSansEquipe.mettreAJour(panelEquipe, listeJoueurs);

        // Réinitialiser les champs
        panelJoueur.getNomField().setText("");
        panelJoueur.getPrenomField().setText("");
        panelJoueur.getCategorieCombo().setSelectedIndex(0);
    }
}
