package TestModel;

import model.Categorie;
import model.Joueur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JoueurTest {

    @Test
    public void testCreationJoueur() {
        Joueur joueur = new Joueur("Durand", "Lucie", Categorie.Jeune_Compétition);

        assertEquals("Durand", joueur.getNom());
        assertEquals("Lucie", joueur.getPrenom());
        assertEquals(Categorie.Jeune_Compétition, joueur.getCategorie());
        assertNull(joueur.getNumeroEquipe());
    }

    @Test
    public void testSetters() {
        Joueur joueur = new Joueur("Martin", "Paul", Categorie.Adulte_Competition);

        joueur.setNom("Dupont");
        joueur.setPrenom("Claire");
        joueur.setCategorie(Categorie.Inviter_Débutant);
        joueur.setNumeroEquipe(2);

        assertEquals("Dupont", joueur.getNom());
        assertEquals("Claire", joueur.getPrenom());
        assertEquals(Categorie.Inviter_Débutant, joueur.getCategorie());
        assertEquals(2, joueur.getNumeroEquipe());
    }

    @Test
    public void testToString() {
        Joueur joueur = new Joueur("Leclerc", "Anne", Categorie.Adulte_Loisir);
        assertTrue(joueur.toString().contains("Leclerc Anne (Adulte_Loisir)"));
        assertTrue(joueur.toString().contains("Non assigné"));

        joueur.setNumeroEquipe(5);
        assertTrue(joueur.toString().contains("Équipe: 5"));
    }
}
