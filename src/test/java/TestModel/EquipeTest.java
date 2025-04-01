package TestModel;

import model.Categorie;
import model.Equipe;
import model.Etat;
import model.Joueur;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EquipeTest {

    @Test
    public void testCreationEquipe() {
        Equipe equipe = new Equipe(1);
        assertEquals(1, equipe.getNumeroEquipe());
        assertEquals(Etat.EN_ATTENTE, equipe.getEtat());
        assertEquals(0, equipe.getJoueurs().size());
    }

    @Test
    public void testAjoutEtRetraitJoueur() {
        Equipe equipe = new Equipe(2);
        Joueur j1 = new Joueur("Dupont", "Jean", Categorie.Adulte_Competition);
        Joueur j2 = new Joueur("Durand", "Paul", Categorie.Jeune_Compétition);
        Joueur j3 = new Joueur("Martin", "Lucie", Categorie.Adulte_Loisir);
        Joueur j4 = new Joueur("Moreau", "Claire", Categorie.Jeune_loisir);
        Joueur j5 = new Joueur("Trop", "Joueur", Categorie.Inviter_Débutant);

        assertTrue(equipe.ajouterJoueur(j1));
        assertTrue(equipe.ajouterJoueur(j2));
        assertTrue(equipe.ajouterJoueur(j3));
        assertTrue(equipe.ajouterJoueur(j4));
        assertFalse(equipe.ajouterJoueur(j5)); // Ne doit pas passer

        assertEquals(4, equipe.getJoueurs().size());

        assertTrue(equipe.retirerJoueur(j3));
        assertEquals(3, equipe.getJoueurs().size());
        assertFalse(equipe.getJoueurs().contains(j3));
        assertNull(j3.getNumeroEquipe());
    }

    @Test
    public void testOrdreDesJoueurs() {
        Equipe equipe = new Equipe(3);
        Joueur a = new Joueur("A", "Alpha", Categorie.Adulte_Loisir);
        Joueur b = new Joueur("B", "Beta", Categorie.Adulte_Loisir);
        Joueur c = new Joueur("C", "Gamma", Categorie.Adulte_Loisir);

        equipe.ajouterJoueur(a);
        equipe.ajouterJoueur(b);
        equipe.ajouterJoueur(c);

        List<Joueur> liste = equipe.getJoueurs();
        assertEquals("A", liste.get(0).getNom());
        assertEquals("B", liste.get(1).getNom());
        assertEquals("C", liste.get(2).getNom());
    }

    @Test
    public void testEquipeEstComplete() {
        Equipe equipe = new Equipe(4);
        Joueur j1 = new Joueur("A", "a", Categorie.Adulte_Loisir);
        Joueur j2 = new Joueur("B", "b", Categorie.Adulte_Loisir);
        Joueur j3 = new Joueur("C", "c", Categorie.Adulte_Loisir);
        Joueur j4 = new Joueur("D", "d", Categorie.Adulte_Loisir);

        assertFalse(equipe.estCompletMax());
        equipe.ajouterJoueur(j1);
        equipe.ajouterJoueur(j2);
        equipe.ajouterJoueur(j3);
        assertFalse(equipe.estCompletMax());

        equipe.ajouterJoueur(j4);
        assertTrue(equipe.estCompletMax());
    }

    @Test
    public void testRatiosNormaux() {
        Equipe equipe = new Equipe(5);
        equipe.setNombreRencontres(10);
        equipe.setNombreRencontresGagnees(6);

        equipe.setNombreMatchs(20);
        equipe.setNombreMatchsGagnes(12);

        equipe.setPointsGagnes(80);
        equipe.setPointsPerdus(20);

        assertEquals(0.6, equipe.getRatioRencontre(), 0.0001);
        assertEquals(0.6, equipe.getRatioMatch(), 0.0001);
        assertEquals(0.8, equipe.getRatioPoint(), 0.0001);
    }

    @Test
    public void testRatiosZeros() {
        Equipe equipe = new Equipe(6);
        // Aucune donnée
        assertEquals(0, equipe.getRatioRencontre(), 0.0001);
        assertEquals(0, equipe.getRatioMatch(), 0.0001);
        assertEquals(0, equipe.getRatioPoint(), 0.0001);

        equipe.setPointsGagnes(0);
        equipe.setPointsPerdus(0);
        assertEquals(0, equipe.getRatioPoint(), 0.0001);
    }
}
