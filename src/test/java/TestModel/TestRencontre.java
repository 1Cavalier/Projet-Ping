package TestModel;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRencontre {

    private Equipe equipeA;
    private Equipe equipeB;
    private Table table;

    private Joueur a1, a2, a3, a4;
    private Joueur b1, b2, b3, b4;

    @BeforeEach
    public void setup() {
        equipeA = new Equipe(1);
        equipeB = new Equipe(2);
        table = new Table(1);

        a1 = new Joueur("Durant", "Kevin", Categorie.Adulte_Competition);
        a2 = new Joueur("James", "LeBron", Categorie.Adulte_Competition);
        a3 = new Joueur("Curry", "Stephen", Categorie.Adulte_Competition);
        a4 = new Joueur("Tatum", "Jayson", Categorie.Adulte_Competition);

        b1 = new Joueur("Antetokounmpo", "Giannis", Categorie.Adulte_Competition);
        b2 = new Joueur("Jokic", "Nikola", Categorie.Adulte_Competition);
        b3 = new Joueur("Doncic", "Luka", Categorie.Adulte_Competition);
        b4 = new Joueur("Morant", "Ja", Categorie.Adulte_Competition);

        equipeA.ajouterJoueur(a1);
        equipeA.ajouterJoueur(a2);
        equipeA.ajouterJoueur(a3);
        equipeA.ajouterJoueur(a4);

        equipeB.ajouterJoueur(b1);
        equipeB.ajouterJoueur(b2);
        equipeB.ajouterJoueur(b3);
        equipeB.ajouterJoueur(b4);
    }

    @Test
    public void testMatchSimple() {
        Match match = new Match(a1, equipeA, b1, equipeB);
        match.enregistrerScores(11, 5);

        assertTrue(match.estTermine());
        assertEquals(equipeA, match.getGagnant());
        assertEquals(1, equipeA.getNombreMatchs());
        assertEquals(11, equipeA.getPointsGagnes());
        assertEquals(5, equipeA.getPointsPerdus());
        assertEquals(0, equipeA.getNombreMatchsPerdus());
        assertEquals(1, equipeA.getNombreMatchsGagnes());
    }

    @Test
    public void testMatchDouble() {
        MatchDouble match = new MatchDouble(a1, a2, equipeA, b1, b2, equipeB);
        match.enregistrerScores(10, 11);

        assertTrue(match.estTermine());
        assertEquals(equipeB, match.getGagnante());
        assertEquals(11, equipeB.getPointsGagnes());
        assertEquals(10, equipeB.getPointsPerdus());
        assertEquals(1, equipeB.getNombreMatchs());
        assertEquals(1, equipeB.getNombreMatchsGagnes());
        assertEquals(0, equipeB.getNombreMatchsPerdus());
    }

    @Test
    public void testTableEtat() {
        assertFalse(table.isOccupee());
        table.isOccupee();
        assertTrue(table.isOccupee());
        table.liberer();
        assertFalse(table.isOccupee());
    }

    @Test
    public void testRencontreComplete() {
        Rencontre rencontre = new Rencontre(equipeA, equipeB, table);

        // On récupère les 4 matchs simples et le match double
        for (int i = 0; i < 4; i++) {
            Match match = (Match) rencontre.getMatchs().get(i);
            match.enregistrerScores(11, 6); // tous gagnés par A
        }

        MatchDouble matchDouble = (MatchDouble) rencontre.getMatchs().get(4);
        matchDouble.enregistrerScores(5, 11); // gagné par B

        rencontre.terminerRencontre();

        assertTrue(rencontre.isTerminee());
        assertEquals(equipeA, rencontre.getGagnante());
        assertEquals(1, equipeA.getNombreRencontres());
        assertEquals(1, equipeA.getNombreRencontresGagnees());
        assertEquals(0, equipeA.getNombreRencontresPerdues());
        assertEquals(4, equipeA.getNombreMatchsGagnes());
        assertEquals(1, equipeA.getNombreMatchsPerdus());
        assertEquals(5, equipeA.getNombreMatchs());

        // Vérification des points
        int expectedPointsGagnesA = 11 * 4 + 5; // 4 simples gagnés + 5 pts du double perdu
        int expectedPointsPerdusA = 6 * 4 + 11; // 4 simples concédés + 11 pts du double perdu

        assertEquals(expectedPointsGagnesA, equipeA.getPointsGagnes());
        assertEquals(expectedPointsPerdusA, equipeA.getPointsPerdus());
    }
}