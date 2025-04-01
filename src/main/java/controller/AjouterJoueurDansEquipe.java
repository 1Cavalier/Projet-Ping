package controller;

import model.Equipe;
import model.Joueur;
import view.PanelEquipe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjouterJoueurDansEquipe implements ActionListener {

    private final PanelEquipe panel;

    public AjouterJoueurDansEquipe(PanelEquipe panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = panel.getTableJoueursSansEquipe().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un joueur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = panel.getNomField().getText().trim();
        String prenom = panel.getPrenomField().getText().trim();
        String numEquipeText = panel.getNumeroEquipeFieldJoueur().getText().trim();

        if (numEquipeText.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Veuillez saisir un numéro d'équipe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroEquipe;
        try {
            numeroEquipe = Integer.parseInt(numEquipeText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Le numéro d'équipe doit être un entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer joueur non assigné depuis la liste principale
        Joueur joueurTrouve = null;
        for (Joueur j : panel.getListeJoueurs()) {
            if (j.getNom().equals(nom) && j.getPrenom().equals(prenom) && j.getNumeroEquipe() == null) {
                joueurTrouve = j;
                break;
            }
        }

        if (joueurTrouve == null) {
            JOptionPane.showMessageDialog(panel, "Le joueur est déjà assigné ou introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer l'équipe
        Equipe equipeTrouvee = null;
        for (Equipe eq : panel.getListeEquipes()) {
            if (eq.getNumeroEquipe() == numeroEquipe) {
                equipeTrouvee = eq;
                break;
            }
        }

        if (equipeTrouvee == null) {
            JOptionPane.showMessageDialog(panel, "Aucune équipe avec ce numéro.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si l'équipe est complète
        if (equipeTrouvee.estCompletMax()) {
            JOptionPane.showMessageDialog(panel, "Cette équipe contient déjà 4 joueurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ajouter le joueur
        boolean ajout = equipeTrouvee.ajouterJoueur(joueurTrouve);
        if (!ajout) {
            JOptionPane.showMessageDialog(panel, "Échec de l'ajout du joueur à l'équipe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        joueurTrouve.setNumeroEquipe(numeroEquipe);

        // 🔄 Mise à jour des tableaux
        MajTableauJoueurSansEquipe.mettreAJour(panel, panel.getListeJoueurs());
        MajTableauEquipe.mettreAJour(panel, panel.getListeEquipes());

        // 🔄 Mettre à jour aussi PanelJoueur si disponible
        if (panel.getPanelJoueur() != null) {
            MajTableauJoueur.mettreAJour(panel.getPanelJoueur().getTableModel(), panel.getListeJoueurs());
        }

        // ✅ Reset des champs
        panel.getNomField().setText("");
        panel.getPrenomField().setText("");
        panel.getCategorieCombo().setSelectedIndex(0);
        panel.getNumeroEquipeFieldJoueur().setText("");

        JOptionPane.showMessageDialog(panel, "Joueur ajouté à l'équipe avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
