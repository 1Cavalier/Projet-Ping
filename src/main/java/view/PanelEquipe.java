package view;

import model.Categorie;
import model.Equipe;
import model.Joueur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelEquipe extends JPanel {

    private PanelJoueur panelJoueur;

    private List<Joueur> listeJoueurs;
    private List<Equipe> listeEquipes;

    private JTable tableJoueursSansEquipe;
    private DefaultTableModel modelJoueurs;

    private JTable tableEquipes;
    private DefaultTableModel modelEquipes;

    private JTextField numeroEquipeField;
    private JButton validerEquipeButton;

    private JTextField nomField;
    private JTextField prenomField;
    private JComboBox<Categorie> categorieCombo;
    private JTextField numeroEquipeFieldJoueur;
    private JButton validerJoueurButton;

    public PanelEquipe() {
        setLayout(new BorderLayout());

        // === COLONNE GAUCHE : joueurs sans équipe ===
        modelJoueurs = new DefaultTableModel(new Object[] { "Nom", "Prénom", "Catégorie" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableJoueursSansEquipe = new JTable(modelJoueurs);
        JScrollPane scrollJoueurs = new JScrollPane(tableJoueursSansEquipe);
        scrollJoueurs.setBorder(BorderFactory.createTitledBorder("Joueurs sans équipe"));

        // === COLONNE DROITE : équipes ===
        modelEquipes = new DefaultTableModel(new Object[] { "N° Équipe", "Joueurs" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEquipes = new JTable(modelEquipes);
        JScrollPane scrollEquipes = new JScrollPane(tableEquipes);
        scrollEquipes.setBorder(BorderFactory.createTitledBorder("Équipes"));

        tableEquipes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tableEquipes.getSelectedRow() != -1) {
                    int selectedRow = tableEquipes.getSelectedRow();
                    int numEquipe = (int) modelEquipes.getValueAt(selectedRow, 0);
                    Equipe equipe = listeEquipes.stream()
                            .filter(eq -> eq.getNumeroEquipe() == numEquipe)
                            .findFirst()
                            .orElse(null);

                    if (equipe != null) {
                        afficherPopupEquipe(equipe);
                    }
                }
            }
        });

        // === PANELS CENTRE ===
        JPanel centrePanel = new JPanel(new GridLayout(2, 1));
        centrePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel hautPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcHaut = new GridBagConstraints();
        gbcHaut.insets = new Insets(10, 10, 10, 10);
        gbcHaut.fill = GridBagConstraints.HORIZONTAL;

        numeroEquipeField = new JTextField();
        numeroEquipeField.setPreferredSize(new Dimension(150, 25));

        gbcHaut.gridx = 0;
        gbcHaut.gridy = 0;
        hautPanel.add(new JLabel("N° Équipe :"), gbcHaut);

        gbcHaut.gridx = 1;
        hautPanel.add(numeroEquipeField, gbcHaut);

        gbcHaut.gridx = 0;
        gbcHaut.gridy = 1;
        gbcHaut.gridwidth = 2;
        gbcHaut.anchor = GridBagConstraints.CENTER;
        validerEquipeButton = new JButton("Valider l'équipe");
        hautPanel.add(validerEquipeButton, gbcHaut);

        JPanel basPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBas = new GridBagConstraints();
        gbcBas.insets = new Insets(10, 10, 10, 10);
        gbcBas.fill = GridBagConstraints.HORIZONTAL;

        nomField = new JTextField();
        prenomField = new JTextField();
        categorieCombo = new JComboBox<>(Categorie.values());
        numeroEquipeFieldJoueur = new JTextField();

        Dimension champSize = new Dimension(150, 25);
        nomField.setPreferredSize(champSize);
        nomField.setEditable(false);
        prenomField.setPreferredSize(champSize);
        prenomField.setEditable(false);
        categorieCombo.setPreferredSize(champSize);
        categorieCombo.setEnabled(false);
        numeroEquipeFieldJoueur.setPreferredSize(champSize);

        gbcBas.gridx = 0;
        gbcBas.gridy = 0;
        basPanel.add(new JLabel("Nom :"), gbcBas);
        gbcBas.gridx = 1;
        basPanel.add(nomField, gbcBas);

        gbcBas.gridx = 0;
        gbcBas.gridy = 1;
        basPanel.add(new JLabel("Prénom :"), gbcBas);
        gbcBas.gridx = 1;
        basPanel.add(prenomField, gbcBas);

        gbcBas.gridx = 0;
        gbcBas.gridy = 2;
        basPanel.add(new JLabel("Catégorie :"), gbcBas);
        gbcBas.gridx = 1;
        basPanel.add(categorieCombo, gbcBas);

        gbcBas.gridx = 0;
        gbcBas.gridy = 3;
        basPanel.add(new JLabel("N° Équipe :"), gbcBas);
        gbcBas.gridx = 1;
        basPanel.add(numeroEquipeFieldJoueur, gbcBas);

        gbcBas.gridx = 0;
        gbcBas.gridy = 4;
        gbcBas.gridwidth = 2;
        gbcBas.anchor = GridBagConstraints.CENTER;
        validerJoueurButton = new JButton("Valider joueur");
        basPanel.add(validerJoueurButton, gbcBas);

        centrePanel.add(hautPanel);
        centrePanel.add(basPanel);

        setLayout(new GridLayout(1, 3));
        add(scrollJoueurs);
        add(centrePanel);
        add(scrollEquipes);

        tableJoueursSansEquipe.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableJoueursSansEquipe.getSelectedRow();
            if (selectedRow != -1) {
                String nom = (String) modelJoueurs.getValueAt(selectedRow, 0);
                String prenom = (String) modelJoueurs.getValueAt(selectedRow, 1);
                Object cat = modelJoueurs.getValueAt(selectedRow, 2);

                nomField.setText(nom);
                prenomField.setText(prenom);
                categorieCombo.setSelectedItem(cat);
                numeroEquipeFieldJoueur.setText("");
            }
        });
    }

    private void afficherPopupEquipe(Equipe equipe) {
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame frame = (window instanceof Frame) ? (Frame) window : null;
        JDialog dialog = new JDialog(frame, "Équipe n°" + equipe.getNumeroEquipe(), true);

        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new Object[] { "Nom", "Prénom", "Catégorie" }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Joueur j : equipe.getJoueurs()) {
            if (j != null)
                model.addRow(new Object[] { j.getNom(), j.getPrenom(), j.getCategorie() });
        }

        JTable tableJoueurs = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableJoueurs);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton monter = new JButton("Monter");
        JButton descendre = new JButton("Descendre");
        JButton retirer = new JButton("Retirer");
        JButton supprimer = new JButton("Supprimer l'équipe");
        JButton fermer = new JButton("Fermer");
        JButton ajouterVide = new JButton("Ajouter une position vide");

        buttonPanel.add(ajouterVide);
        buttonPanel.add(monter);
        buttonPanel.add(descendre);
        buttonPanel.add(retirer);
        buttonPanel.add(supprimer);
        buttonPanel.add(fermer);

        ajouterVide.addActionListener(e -> {
            if (equipe.ajouterPlaceholder()) {
                model.setRowCount(0);
                for (Joueur j : equipe.getJoueurs()) {
                    model.addRow(new Object[] { j.getNom(), j.getPrenom(), j.getCategorie() });
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "L'équipe a déjà 4 positions.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        monter.addActionListener(e -> {
            int selectedRow = tableJoueurs.getSelectedRow();
            if (selectedRow <= 0)
                return; // Impossible de monter

            if (equipe.echangerJoueurs(selectedRow, selectedRow - 1)) {
                controller.MajTableauEquipe.mettreAJour(this, listeEquipes);
                model.setRowCount(0);
                for (Joueur j : equipe.getJoueurs()) {
                    model.addRow(new Object[] { j.getNom(), j.getPrenom(), j.getCategorie() });
                }
                tableJoueurs.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
            }
        });

        descendre.addActionListener(e -> {
            int selectedRow = tableJoueurs.getSelectedRow();
            if (selectedRow == -1 || selectedRow >= equipe.getJoueurs().size() - 1)
                return;

            if (equipe.echangerJoueurs(selectedRow, selectedRow + 1)) {
                controller.MajTableauEquipe.mettreAJour(this, listeEquipes);
                model.setRowCount(0);
                for (Joueur j : equipe.getJoueurs()) {
                    model.addRow(new Object[] { j.getNom(), j.getPrenom(), j.getCategorie() });
                }
                tableJoueurs.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
            }
        });

        supprimer.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Voulez-vous vraiment supprimer l'équipe n°" + equipe.getNumeroEquipe() + " ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                for (Joueur joueur : equipe.getJoueurs()) {
                    joueur.setNumeroEquipe(null);
                }

                listeEquipes.remove(equipe);

                controller.MajTableauEquipe.mettreAJour(this, listeEquipes);
                controller.MajTableauJoueurSansEquipe.mettreAJour(this, listeJoueurs);
                if (panelJoueur != null) {
                    controller.MajTableauJoueur.mettreAJour(panelJoueur.getTableModel(), listeJoueurs);
                }

                dialog.dispose();
            }
        });

        retirer.addActionListener(e -> {
            int selectedIndex = tableJoueurs.getSelectedRow();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un joueur à retirer.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Joueur joueurARetirer = equipe.getJoueurs().get(selectedIndex);

            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Retirer " + joueurARetirer.getNom() + " " + joueurARetirer.getPrenom() + " de l'équipe ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                equipe.retirerJoueur(joueurARetirer);

                // Mise à jour des tableaux
                model.removeRow(selectedIndex); // mise à jour du tableau local
                joueurARetirer.setNumeroEquipe(null); // sécurité

                // 🔁 Vue équipe
                controller.MajTableauEquipe.mettreAJour(this, listeEquipes);
                controller.MajTableauJoueurSansEquipe.mettreAJour(this, listeJoueurs);

                // 🔁 Vue joueur
                if (panelJoueur != null) {
                    controller.MajTableauJoueur.mettreAJour(panelJoueur.getTableModel(), listeJoueurs);
                }
            }
        });

        fermer.addActionListener(e -> dialog.dispose());

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.EAST);
        dialog.setVisible(true);
    }

    // === Getters/Setters ===
    public JTable getTableJoueursSansEquipe() {
        return tableJoueursSansEquipe;
    }

    public JTable getTableEquipes() {
        return tableEquipes;
    }

    public DefaultTableModel getModelJoueurs() {
        return modelJoueurs;
    }

    public DefaultTableModel getModelEquipes() {
        return modelEquipes;
    }

    public JTextField getNumeroEquipeField() {
        return numeroEquipeField;
    }

    public JButton getValiderEquipeButton() {
        return validerEquipeButton;
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JComboBox<Categorie> getCategorieCombo() {
        return categorieCombo;
    }

    public JTextField getNumeroEquipeFieldJoueur() {
        return numeroEquipeFieldJoueur;
    }

    public JButton getValiderJoueurButton() {
        return validerJoueurButton;
    }

    public void setListeJoueurs(List<Joueur> joueurs) {
        this.listeJoueurs = joueurs;
    }

    public List<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public void setListeEquipes(List<Equipe> listeEquipes) {
        this.listeEquipes = listeEquipes;
    }

    public List<Equipe> getListeEquipes() {
        return listeEquipes;
    }

    public void setPanelJoueur(PanelJoueur panelJoueur) {
        this.panelJoueur = panelJoueur;
    }

    public PanelJoueur getPanelJoueur() {
        return panelJoueur;
    }
}