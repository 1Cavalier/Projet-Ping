package save;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SauvegardeManager {

    private static final String DOSSIER_BASES = "ancien_tournoi";

    public static void initialiserDossier() {
        File dossier = new File(DOSSIER_BASES);
        if (!dossier.exists()) {
            dossier.mkdir();
        }
    }

    public static List<File> listerBases() {
        File dossier = new File(DOSSIER_BASES);
        File[] fichiers = dossier.listFiles((dir, name) -> name.endsWith(".ser"));
        if (fichiers == null) return new ArrayList<>();
        List<File> liste = new ArrayList<>();
        for (File f : fichiers) {
            if (f.isFile()) liste.add(f);
        }
        return liste;
    }

    public static void sauvegarder(BaseTournoi base, File fichier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier))) {
            out.writeObject(base);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur de sauvegarde !");
        }
    }

    public static BaseTournoi charger(File fichier) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichier))) {
            return (BaseTournoi) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de chargement !");
            return null;
        }
    }

    public static String getDossierBases() {
        return DOSSIER_BASES;
    }
}
