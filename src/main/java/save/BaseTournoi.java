package save;

import model.Equipe;
import model.Joueur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseTournoi implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Joueur> joueurs;
    private List<Equipe> equipes;

    public BaseTournoi() {
        this.joueurs = new ArrayList<>();
        this.equipes = new ArrayList<>();
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
}
