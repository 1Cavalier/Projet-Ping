package view;

import controller.TableDispo;
import controller.TableRencontre;
import model.Table;
import model.Rencontre;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PanelSalle extends JPanel {

    private JTextField nbTablesGaucheField;
    private JTextField nbTablesDroiteField;
    private JPanel tablePanel;
    private BufferedImage tableImage;
    private List<Table> tables;

    public PanelSalle() {
        setLayout(new BorderLayout());
        tables = new ArrayList<>();

        try (InputStream stream = getClass().getResourceAsStream("/images/table.png")) {
            if (stream != null) {
                tableImage = ImageIO.read(stream);
            } else {
                JOptionPane.showMessageDialog(this, "Image non trouvée : /images/table.png");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement image table : " + e.getMessage());
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Tables à gauche : "));
        nbTablesGaucheField = new JTextField(5);
        leftPanel.add(nbTablesGaucheField);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JLabel("Tables à droite : "));
        nbTablesDroiteField = new JTextField(5);
        rightPanel.add(nbTablesDroiteField);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.setBackground(new Color(224, 224, 224));

        JScrollPane scroll = new JScrollPane(tablePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        nbTablesGaucheField.addActionListener(e -> genererTables());
        nbTablesDroiteField.addActionListener(e -> genererTables());

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void genererTables() {
        tablePanel.removeAll();
        tables.clear();

        int nbGauche = parseIntSafe(nbTablesGaucheField.getText());
        int nbDroite = parseIntSafe(nbTablesDroiteField.getText());
        int separatorWidth = Math.max(2, 10);
        int tableNumber = 1;

        int imageWidth = 150;
        int imageHeight = 100;

        JPanel gauchePanel = new JPanel();
        gauchePanel.setLayout(new BoxLayout(gauchePanel, BoxLayout.Y_AXIS));
        gauchePanel.setBackground(new Color(224, 224, 224));

        for (int i = nbGauche - 1; i >= 0; i--) {
            Table table = new Table(tableNumber++);
            tables.add(table);
            JPanel wrapper = createTablePanel(table, imageWidth, imageHeight);
            gauchePanel.add(wrapper);
            gauchePanel.add(Box.createVerticalStrut(15));
        }

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(separatorWidth, 400));
        separator.setMinimumSize(new Dimension(separatorWidth, 0));
        separator.setMaximumSize(new Dimension(separatorWidth, Integer.MAX_VALUE));
        separator.setBackground(Color.GREEN);

        JPanel droitePanel = new JPanel();
        droitePanel.setLayout(new BoxLayout(droitePanel, BoxLayout.Y_AXIS));
        droitePanel.setBackground(new Color(224, 224, 224));

        for (int i = 0; i < nbDroite; i++) {
            Table table = new Table(tableNumber++);
            tables.add(table);
            JPanel wrapper = createTablePanel(table, imageWidth, imageHeight);
            droitePanel.add(wrapper);
            droitePanel.add(Box.createVerticalStrut(15));
        }

        tablePanel.add(Box.createHorizontalStrut(20));
        tablePanel.add(gauchePanel);
        tablePanel.add(Box.createHorizontalStrut(20));
        tablePanel.add(separator);
        tablePanel.add(Box.createHorizontalStrut(20));
        tablePanel.add(droitePanel);
        tablePanel.add(Box.createHorizontalStrut(20));

        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private JPanel createTablePanel(Table table, int width, int height) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setBackground(new Color(224, 224, 224));

        JLabel label = new JLabel("Table " + table.getNumero(), SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mettreAJourTableGraphique(table, imageLabel, width, height);

        imageLabel.addMouseListener(new TableDispo(table, imageLabel, this));
        imageLabel.addMouseListener(new TableRencontre(table));

        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(imageLabel);
        return wrapper;
    }

    public void mettreAJourTableGraphique(Table table, JLabel imageLabel, int width, int height) {
        if (tableImage != null) {
            Image img = tableImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            if (!table.isActive()) {
                img = GrayFilter.createDisabledImage(img);
            }
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setText("Table " + table.getNumero());
        }

        String tooltip;
        if (!table.isActive()) {
            tooltip = "Table désactivée";
        } else if (table.isOccupee()) {
            Rencontre r = table.getRencontreActuelle();
            tooltip = (r != null)
                    ? "Occupée - Équipe " + r.getEquipeA().getNumeroEquipe() + " vs " + r.getEquipeB().getNumeroEquipe()
                    : "Occupée";
        } else {
            tooltip = "Libre";
        }

        imageLabel.setToolTipText(tooltip);
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}
