package model;

import java.io.Serializable;

public class MatchDouble implements Serializable {
    private static final long serialVersionUID = 1L;

    private Joueur joueurA1, joueurA2;
    private Joueur joueurB1, joueurB2;
    private Equipe equipeA, equipeB;
    private int scoreA, scoreB;
    private boolean termine;

    public MatchDouble(Joueur joueurA1, Joueur joueurA2, Equipe equipeA,
            Joueur joueurB1, Joueur joueurB2, Equipe equipeB) {
        this.joueurA1 = joueurA1;
        this.joueurA2 = joueurA2;
        this.joueurB1 = joueurB1;
        this.joueurB2 = joueurB2;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.scoreA = 0;
        this.scoreB = 0;
        this.termine = false;
    }

    public void enregistrerScores(int scoreA, int scoreB) {
        if (termine)
            return;
        if (scoreA < 0 || scoreB < 0 || scoreA > 11 || scoreB > 11 || (scoreA + scoreB) > 21) {
            throw new IllegalArgumentException("Scores invalides (max total = 21, chaque score ≤ 11).");
        }

        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.termine = true;

        equipeA.setNombreMatchs(equipeA.getNombreMatchs() + 1);
        equipeB.setNombreMatchs(equipeB.getNombreMatchs() + 1);
        equipeA.setPointsGagnes(equipeA.getPointsGagnes() + scoreA);
        equipeA.setPointsPerdus(equipeA.getPointsPerdus() + scoreB);
        equipeB.setPointsGagnes(equipeB.getPointsGagnes() + scoreB);
        equipeB.setPointsPerdus(equipeB.getPointsPerdus() + scoreA);

        if (scoreA > scoreB) {
            equipeA.setNombreMatchsGagnes(equipeA.getNombreMatchsGagnes() + 1);
            equipeB.setNombreMatchsPerdus(equipeB.getNombreMatchsPerdus() + 1);
        } else {
            equipeB.setNombreMatchsGagnes(equipeB.getNombreMatchsGagnes() + 1);
            equipeA.setNombreMatchsPerdus(equipeA.getNombreMatchsPerdus() + 1);
        }
    }

    public boolean estTermine() {
        return termine;
    }

    public Equipe getGagnante() {
        if (!termine)
            return null;
        return (scoreA > scoreB) ? equipeA : equipeB;
    }

    // Getters
    public Joueur getJoueurA1() {
        return joueurA1;
    }

    public Joueur getJoueurA2() {
        return joueurA2;
    }

    public Joueur getJoueurB1() {
        return joueurB1;
    }

    public Joueur getJoueurB2() {
        return joueurB2;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }
}