package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class PanelSalle extends JPanel {

    private JTextField nbTablesGaucheField;
    private JTextField nbTablesDroiteField;
    private JPanel tablePanel;
    private BufferedImage tableImage;

    public PanelSalle() {
        setLayout(new BorderLayout());

        // === NORTH ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Gauche
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Tables à gauche : "));
        nbTablesGaucheField = new JTextField(5);
        leftPanel.add(nbTablesGaucheField);

        // Droite
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JLabel("Tables à droite : "));
        nbTablesDroiteField = new JTextField(5);
        rightPanel.add(nbTablesDroiteField);

        // Ajout
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // === CENTRE ===
        tablePanel = new JPanel(new GridLayout(0, 1, 20, 20));
        tablePanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tablePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // Écouteurs pour champs
        nbTablesGaucheField.addActionListener(e -> genererTables());
        nbTablesDroiteField.addActionListener(e -> genererTables());

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void genererTables() {
        tablePanel.removeAll();

        int nbGauche = parseIntSafe(nbTablesGaucheField.getText());
        int nbDroite = parseIntSafe(nbTablesDroiteField.getText());

        JPanel line = new JPanel(new GridLayout(1, nbGauche + nbDroite, 20, 20));
        int tableNumber = 1;

        // Droite (tables à droite : numérotation en bas à droite -> haut)
        for (int i = nbDroite - 1; i >= 0; i--) {
            JPanel tableWrapper = createTablePanel(tableNumber++);
            line.add(tableWrapper);
        }

        // Ligne verte
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(4, 100));
        separator.setBackground(Color.GREEN);
        line.add(separator);

        // Gauche (tables à gauche : numérotation continue en bas à gauche -> haut)
        for (int i = 0; i < nbGauche; i++) {
            JPanel tableWrapper = createTablePanel(tableNumber++);
            line.add(tableWrapper);
        }

        tablePanel.setLayout(new GridLayout(1, 1));
        tablePanel.add(line);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private JPanel createTablePanel(int numero) {
        JPanel wrapper = new JPanel(new BorderLayout());

        JLabel imageLabel = new JLabel();
        if (tableImage != null) {
            ImageIcon icon = new ImageIcon(tableImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setText("Table " + numero);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        JLabel label = new JLabel("Table " + numero, SwingConstants.CENTER);
        wrapper.add(label, BorderLayout.NORTH);
        wrapper.add(imageLabel, BorderLayout.CENTER);
        return wrapper;
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public JTextField getNbTablesGaucheField() {
        return nbTablesGaucheField;
    }

    public JTextField getNbTablesDroiteField() {
        return nbTablesDroiteField;
    }
}
