package view;

import model.Categorie;
import model.Joueur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ModifierJoueur;
import controller.SupprimerJoueur;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PanelJoueur extends JPanel {

    private PanelEquipe panelEquipe;
    private JTextField nomField;
    private JTextField prenomField;
    private JComboBox<Categorie> categorieCombo;
    private JButton ajouterButton;
    private JButton annulerButton;
    private JTable tableJoueurs;
    private DefaultTableModel tableModel;
    private List<Joueur> listeJoueurs;

    public PanelJoueur() {
        this.listeJoueurs = new ArrayList<>();
        setLayout(new GridLayout(1, 2));

        // === PANEL FORMULAIRE GAUCHE ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Ajouter un joueur"));
        formPanel.setPreferredSize(new Dimension(400, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomField = new JTextField(15);
        prenomField = new JTextField(15);
        categorieCombo = new JComboBox<>(Categorie.values());

        ajouterButton = new JButton("Ajouter");
        annulerButton = new JButton("Annuler");

        // Ligne 1 : nom
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomField, gbc);

        // Ligne 2 : prénom
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        formPanel.add(prenomField, gbc);

        // Ligne 3 : catégorie
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Catégorie :"), gbc);
        gbc.gridx = 1;
        formPanel.add(categorieCombo, gbc);

        // Ligne 4 : boutons
        JPanel boutonsPanel = new JPanel();
        boutonsPanel.add(ajouterButton);
        boutonsPanel.add(annulerButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(boutonsPanel, gbc);

        // === PANEL TABLEAU DROIT ===
        tableModel = new DefaultTableModel(new Object[] { "Nom", "Prénom", "Catégorie", "Équipe" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // empêche modification directe
            }
        };

        tableJoueurs = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableJoueurs);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des joueurs"));

        // Ajouter panels
        add(formPanel);
        add(scrollPane);

        // Bouton "Annuler"
        annulerButton.addActionListener(e -> {
            nomField.setText("");
            prenomField.setText("");
            categorieCombo.setSelectedIndex(0);
        });

        // Double-clic pour modifier/supprimer
        tableJoueurs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && tableJoueurs.getSelectedRow() != -1) {
                    int selectedIndex = tableJoueurs.getSelectedRow();
                    afficherPopupModification(selectedIndex);
                }
            }
        });
    }

    // === Méthode publique pour relier le contrôleur d'ajout ===
    public JButton getAjouterButton() {
        return ajouterButton;
    }

    // === Rafraîchit le tableau après modif/suppression ===
    public void rafraichirTableau() {
        tableModel.setRowCount(0);
        for (Joueur j : listeJoueurs) {
            tableModel.addRow(new Object[] {
                    j.getNom(), j.getPrenom(), j.getCategorie(),
                    j.getNumeroEquipe() != null ? j.getNumeroEquipe() : "Non assigné"
            });
        }
    }

    // === Getters/Setters ===
    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JComboBox<Categorie> getCategorieCombo() {
        return categorieCombo;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public List<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public void setListeJoueurs(List<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }

    public void setPanelEquipe(PanelEquipe panelEquipe) {
        this.panelEquipe = panelEquipe;
    }

    // === Popup interne ===
    private void afficherPopupModification(int index) {
        Joueur joueur = listeJoueurs.get(index);

        JTextField nomFieldPop = new JTextField(joueur.getNom(), 15);
        JTextField prenomFieldPop = new JTextField(joueur.getPrenom(), 15);
        JComboBox<Categorie> categoriePop = new JComboBox<>(Categorie.values());
        categoriePop.setSelectedItem(joueur.getCategorie());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Nom :"));
        panel.add(nomFieldPop);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomFieldPop);
        panel.add(new JLabel("Catégorie :"));
        panel.add(categoriePop);

        JButton modifierButton = new JButton("Modifier");
        JButton supprimerButton = new JButton("Supprimer");

        JPanel btnPanel = new JPanel();
        btnPanel.add(modifierButton);
        btnPanel.add(supprimerButton);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier Joueur", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        modifierButton.addActionListener(new ModifierJoueur(
                this,
                panelEquipe,
                index,
                nomFieldPop,
                prenomFieldPop,
                categoriePop,
                dialog));

        supprimerButton.addActionListener(new SupprimerJoueur(
                this,
                panelEquipe,
                this.getListeJoueurs(),
                index,
                dialog));

        dialog.setVisible(true);
    }
}
