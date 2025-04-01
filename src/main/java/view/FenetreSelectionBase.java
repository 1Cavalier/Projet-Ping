package view;

import save.SauvegardeManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class FenetreSelectionBase extends JDialog {

    private JComboBox<String> comboBox;
    private File fichierSelectionne;

    public FenetreSelectionBase(Frame parent) {
        super(parent, "Sélection d'une base", true);
        setLayout(new BorderLayout());
        setSize(400, 150);
        setLocationRelativeTo(parent);

        SauvegardeManager.initialiserDossier();
        List<File> fichiers = SauvegardeManager.listerBases();

        String[] noms = fichiers.stream()
                .map(File::getName)
                .toArray(String[]::new);

        comboBox = new JComboBox<>(noms);

        JButton chargerButton = new JButton("Charger la base");
        JButton nouvelleButton = new JButton("Nouvelle base");

        JPanel center = new JPanel();
        center.add(new JLabel("Sélectionnez une base :"));
        center.add(comboBox);

        JPanel bottom = new JPanel();
        bottom.add(chargerButton);
        bottom.add(nouvelleButton);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        chargerButton.addActionListener(e -> {
            int index = comboBox.getSelectedIndex();
            if (index != -1 && index < fichiers.size()) {
                fichierSelectionne = fichiers.get(index);
                dispose();
            }
        });

        nouvelleButton.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom de la nouvelle base :");
            if (nom != null && !nom.trim().isEmpty()) {
                fichierSelectionne = new File(SauvegardeManager.getDossierBases(), nom + ".ser");
                dispose();
            }
        });
    }

    public File getFichierSelectionne() {
        return fichierSelectionne;
    }
}
