package view;

import controller.*;
import model.Equipe;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelAdmin extends JFrame {

    private PanelJoueur panelJoueur;
    private PanelEquipe panelEquipe;
    private PanelSalle panelSalle;

    public PanelAdmin(List<Joueur> listeJoueurs, List<Equipe> listeEquipes) {
        setTitle("Interface Administrateur - Tournoi Tennis de Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // === Onglets principaux
        JTabbedPane tabbedPane = new JTabbedPane();

        // Étape 1 : Créer Panel Joueur
        panelJoueur = new PanelJoueur();
        panelJoueur.setListeJoueurs(listeJoueurs);

        // === Panel Salle
        panelSalle = new PanelSalle();

        // Étape 2 : Créer Panel Équipe (après panelJoueur pour pouvoir le lui injecter)
        panelEquipe = new PanelEquipe();
        panelEquipe.setListeJoueurs(listeJoueurs);
        panelEquipe.setListeEquipes(listeEquipes);
        panelEquipe.setPanelJoueur(panelJoueur);

        // Lien inverse (PanelJoueur connaît PanelEquipe aussi)
        panelJoueur.setPanelEquipe(panelEquipe);

        // Initialisation des tableaux
        MajTableauJoueur.mettreAJour(panelJoueur.getTableModel(), listeJoueurs);
        MajTableauJoueurSansEquipe.mettreAJour(panelEquipe, listeJoueurs);
        MajTableauEquipe.mettreAJour(panelEquipe, listeEquipes);

        // Liaisons avec contrôleurs
        panelJoueur.getAjouterButton().addActionListener(
                new AjouterJoueur(panelJoueur, panelEquipe, listeJoueurs));

        panelEquipe.getValiderEquipeButton().addActionListener(
                new AjouterEquipe(panelEquipe, listeEquipes));

        panelEquipe.getValiderJoueurButton().addActionListener(
                new AjouterJoueurDansEquipe(panelEquipe));

        // Ajout des panneaux dans les onglets
        tabbedPane.addTab("Création Joueur", panelJoueur);
        tabbedPane.addTab("Création Équipe", panelEquipe);
        tabbedPane.addTab("Salle", panelSalle);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }
}