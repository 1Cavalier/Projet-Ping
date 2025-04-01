import model.Equipe;
import model.Joueur;
import save.BaseTournoi;
import save.SauvegardeManager;
import view.FenetreSelectionBase;
import view.PanelAdmin;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final BaseTournoi[] baseHolder = new BaseTournoi[1];
            final File[] fichierHolder = new File[1];

            FenetreSelectionBase fenetre = new FenetreSelectionBase(null);
            fenetre.setVisible(true);

            File fichierChoisi = fenetre.getFichierSelectionne();
            if (fichierChoisi == null) {
                JOptionPane.showMessageDialog(null, "Aucune base sélectionnée. Fermeture.");
                System.exit(0);
            }

            fichierHolder[0] = fichierChoisi;

            if (fichierChoisi.exists()) {
                baseHolder[0] = SauvegardeManager.charger(fichierChoisi);
                if (baseHolder[0] == null) {
                    JOptionPane.showMessageDialog(null, "Erreur de chargement. Nouvelle base créée.");
                    baseHolder[0] = new BaseTournoi();
                }
            } else {
                baseHolder[0] = new BaseTournoi(); // Nouvelle base
            }

            if (baseHolder[0].getEquipes() == null) {
                baseHolder[0].setEquipes(new ArrayList<>());
            }
            List<Equipe> listeEquipes = baseHolder[0].getEquipes();
            List<Joueur> listeJoueurs = baseHolder[0].getJoueurs();

            new PanelAdmin(listeJoueurs, listeEquipes);

            // Enregistrement automatique à la fermeture
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                baseHolder[0].setJoueurs(listeJoueurs);
                baseHolder[0].setEquipes(listeEquipes);
                SauvegardeManager.sauvegarder(baseHolder[0], fichierHolder[0]);
                System.out.println("Base sauvegardée dans : " + fichierHolder[0].getName());
            }));
        });
    }
}
