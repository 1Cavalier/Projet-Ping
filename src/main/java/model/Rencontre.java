package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rencontre implements Serializable {
    private static final long serialVersionUID = 1L;

    private Equipe equipeA;
    private Equipe equipeB;
    private Table table;
    private List<Object> matchs; // 4 simples (Match) + 1 double (MatchDouble)
    private boolean terminee;
    private Equipe gagnante;

    public Rencontre(Equipe equipeA, Equipe equipeB, Table table) {
        if (equipeA.getEtat() != Etat.EN_ATTENTE || equipeB.getEtat() != Etat.EN_ATTENTE) {
            throw new IllegalStateException(
                    "Les deux équipes doivent être en état EN_ATTENTE pour commencer une rencontre.");
        }

        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.table = table;
        this.matchs = new ArrayList<>();
        this.terminee = false;
        this.gagnante = null;

        // On marque les équipes comme en match
        equipeA.setEtat(Etat.EN_MATCH);
        equipeB.setEtat(Etat.EN_MATCH);

        // On occupe la table
        table.occuper();

        // Initialisation des matchs simples
        for (int i = 0; i < 4; i++) {
            matchs.add(new Match(equipeA.getJoueurs().get(i), equipeA, equipeB.getJoueurs().get(i), equipeB));
        }

        // Match double — joueurs à définir plus tard par l'utilisateur ou logique future
        matchs.add(new MatchDouble(
            equipeA.getJoueurs().get(0), equipeA.getJoueurs().get(1), equipeA,
            equipeB.getJoueurs().get(0), equipeB.getJoueurs().get(1), equipeB
        ));
    }

    public void terminerRencontre() {
        if (terminee)
            return;

        int scoreA = 0;
        int scoreB = 0;

        for (Object matchObj : matchs) {
            if (matchObj instanceof Match) {
                Match match = (Match) matchObj;
                if (!match.estTermine()) return;
                if (match.getGagnant() == equipeA) scoreA++;
                else if (match.getGagnant() == equipeB) scoreB++;
            } else if (matchObj instanceof MatchDouble) {
                MatchDouble matchDouble = (MatchDouble) matchObj;
                if (!matchDouble.estTermine()) return;
                if (matchDouble.getGagnante() == equipeA) scoreA++;
                else if (matchDouble.getGagnante() == equipeB) scoreB++;
            }
        }

        this.gagnante = (scoreA > scoreB) ? equipeA : equipeB;
        this.terminee = true;

        // Libérer la table
        table.liberer();

        // Mettre les équipes à jour
        equipeA.setEtat(Etat.EN_ATTENTE);
        equipeB.setEtat(Etat.EN_ATTENTE);

        equipeA.setNombreRencontres(equipeA.getNombreRencontres() + 1);
        equipeB.setNombreRencontres(equipeB.getNombreRencontres() + 1);

        equipeA.getRencontresEffectuees().add(equipeB.getNumeroEquipe() + "");
        equipeB.getRencontresEffectuees().add(equipeA.getNumeroEquipe() + "");

        if (gagnante == equipeA) {
            equipeA.setNombreRencontresGagnees(equipeA.getNombreRencontresGagnees() + 1);
            equipeB.setNombreRencontresPerdues(equipeB.getNombreRencontresPerdues() + 1);
        } else {
            equipeB.setNombreRencontresGagnees(equipeB.getNombreRencontresGagnees() + 1);
            equipeA.setNombreRencontresPerdues(equipeA.getNombreRencontresPerdues() + 1);
        }
    }

    // Getters utiles
    public Equipe getEquipeA() {
        return equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public Table getTable() {
        return table;
    }

    public List<Object> getMatchs() {
        return matchs;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public Equipe getGagnante() {
        return gagnante;
    }
}
