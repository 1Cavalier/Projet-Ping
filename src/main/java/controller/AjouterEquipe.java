package controller;

import model.Equipe;
import view.PanelEquipe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AjouterEquipe implements ActionListener {

    private final PanelEquipe panel;
    private final List<Equipe> listeEquipes;

    public AjouterEquipe(PanelEquipe panel, List<Equipe> listeEquipes) {
        this.panel = panel;
        this.listeEquipes = listeEquipes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String numeroText = panel.getNumeroEquipeField().getText().trim();

        if (numeroText.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Veuillez saisir un numéro d'équipe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroEquipe;
        try {
            numeroEquipe = Integer.parseInt(numeroText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Le numéro d'équipe doit être un entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifie si ce numéro existe déjà
        for (Equipe eq : listeEquipes) {
            if (eq.getNumeroEquipe() == numeroEquipe) {
                JOptionPane.showMessageDialog(panel, "Ce numéro d'équipe est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Créer l’équipe
        Equipe nouvelle = new Equipe(numeroEquipe);
        listeEquipes.add(nouvelle);

        // Rafraîchir le tableau
        MajTableauEquipe.mettreAJour(panel, listeEquipes);

        // Réinitialiser le champ
        panel.getNumeroEquipeField().setText("");
    }
}
