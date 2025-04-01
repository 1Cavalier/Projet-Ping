package model;

import java.io.Serializable;

public class Match implements Serializable {
    private Joueur joueurA, joueurB;
    private Equipe equipeA, equipeB;
    private int scoreA, scoreB;
    private boolean termine;

    public Match(Joueur joueurA, Equipe equipeA, Joueur joueurB, Equipe equipeB) {
        this.joueurA = joueurA;
        this.equipeA = equipeA;
        this.joueurB = joueurB;
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

        // Statistiques
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

    public Equipe getGagnant() {
        if (!termine)
            return null;
        return (scoreA > scoreB) ? equipeA : equipeB;
    }

    // Getters
    public Joueur getJoueurA() {
        return joueurA;
    }

    public Joueur getJoueurB() {
        return joueurB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }
}