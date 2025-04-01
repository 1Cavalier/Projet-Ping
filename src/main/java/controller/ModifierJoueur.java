package controller;

import model.Categorie;
import model.Joueur;
import view.PanelEquipe;
import view.PanelJoueur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierJoueur implements ActionListener {

    private final PanelJoueur panelJoueur;
    private final PanelEquipe panelEquipe;
    private final int index;
    private final JTextField nomField;
    private final JTextField prenomField;
    private final JComboBox<Categorie> categorieBox;
    private final JDialog dialog;

    public ModifierJoueur(PanelJoueur panelJoueur, PanelEquipe panelEquipe,
                          int index, JTextField nomField, JTextField prenomField,
                          JComboBox<Categorie> categorieBox, JDialog dialog) {
        this.panelJoueur = panelJoueur;
        this.panelEquipe = panelEquipe;
        this.index = index;
        this.nomField = nomField;
        this.prenomField = prenomField;
        this.categorieBox = categorieBox;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Joueur joueur = panelJoueur.getListeJoueurs().get(index);

        joueur.setNom(nomField.getText().trim());
        joueur.setPrenom(prenomField.getText().trim());
        joueur.setCategorie((Categorie) categorieBox.getSelectedItem());

        // 🔁 Met à jour les tableaux
        panelJoueur.rafraichirTableau();
        controller.MajTableauJoueurSansEquipe.mettreAJour(panelEquipe, panelJoueur.getListeJoueurs());
        controller.MajTableauEquipe.mettreAJour(panelEquipe, panelEquipe.getListeEquipes()); // 🔥 ICI

        dialog.dispose();
    }
}
