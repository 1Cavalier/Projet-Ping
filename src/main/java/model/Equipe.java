package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numeroEquipe;
    private List<Joueur> joueurs;
    private Etat etat;
    private List<String> rencontresEffectuees;
    private int nombreRencontres;
    private int nombreRencontresGagnees;
    private int nombreRencontresPerdues;
    private int nombreMatchs;
    private int nombreMatchsGagnes;
    private int nombreMatchsPerdus;
    private int pointsGagnes;
    private int pointsPerdus;

    public Equipe(int numeroEquipe) {
        this.numeroEquipe = numeroEquipe;
        this.joueurs = new ArrayList<>();
        this.etat = Etat.EN_ATTENTE;
        this.rencontresEffectuees = new ArrayList<>();
        this.nombreRencontres = 0;
        this.nombreRencontresGagnees = 0;
        this.nombreRencontresPerdues = 0;
        this.nombreMatchs = 0;
        this.nombreMatchsGagnes = 0;
        this.nombreMatchsPerdus = 0;
        this.pointsGagnes = 0;
        this.pointsPerdus = 0;
    }

    // === Getters & Setters ===

    public int getNumeroEquipe() {
        return numeroEquipe;
    }

    public void setNumeroEquipe(int numeroEquipe) {
        this.numeroEquipe = numeroEquipe;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<String> getRencontresEffectuees() {
        return rencontresEffectuees;
    }

    public void setRencontresEffectuees(List<String> rencontresEffectuees) {
        this.rencontresEffectuees = rencontresEffectuees;
    }

    public int getNombreRencontres() {
        return nombreRencontres;
    }

    public void setNombreRencontres(int nombreRencontres) {
        this.nombreRencontres = nombreRencontres;
    }

    public int getNombreRencontresGagnees() {
        return nombreRencontresGagnees;
    }

    public void setNombreRencontresGagnees(int nombreRencontresGagnees) {
        this.nombreRencontresGagnees = nombreRencontresGagnees;
    }

    public int getNombreRencontresPerdues() {
        return nombreRencontresPerdues;
    }

    public void setNombreRencontresPerdues(int nombreRencontresPerdues) {
        this.nombreRencontresPerdues = nombreRencontresPerdues;
    }

    public int getNombreMatchs() {
        return nombreMatchs;
    }

    public void setNombreMatchs(int nombreMatchs) {
        this.nombreMatchs = nombreMatchs;
    }

    public int getNombreMatchsGagnes() {
        return nombreMatchsGagnes;
    }

    public void setNombreMatchsGagnes(int nombreMatchsGagnes) {
        this.nombreMatchsGagnes = nombreMatchsGagnes;
    }

    public int getNombreMatchsPerdus() {
        return nombreMatchsPerdus;
    }

    public void setNombreMatchsPerdus(int nombreMatchsPerdus) {
        this.nombreMatchsPerdus = nombreMatchsPerdus;
    }

    public int getPointsGagnes() {
        return pointsGagnes;
    }

    public void setPointsGagnes(int pointsGagnes) {
        this.pointsGagnes = pointsGagnes;
    }

    public int getPointsPerdus() {
        return pointsPerdus;
    }

    public void setPointsPerdus(int pointsPerdus) {
        this.pointsPerdus = pointsPerdus;
    }

    // Ratio victoire par rencontre
    public double getRatioRencontre() {
        return nombreRencontres > 0 ? (double) nombreRencontresGagnees / nombreRencontres : 0;
    }

    // Ratio victoire par match
    public double getRatioMatch() {
        return nombreMatchs > 0 ? (double) nombreMatchsGagnes / nombreMatchs : 0;
    }

    // Ratio de points marqués
    public double getRatioPoint() {
        int totalPoints = pointsGagnes + pointsPerdus;
        return totalPoints > 0 ? (double) pointsGagnes / totalPoints : 0;
    }

    // === Méthodes utilitaires ===

    public boolean ajouterJoueur(Joueur joueur) {
        if (joueurs.size() >= 4) {
            return false;
        }
        joueurs.add(joueur);
        joueur.setNumeroEquipe(numeroEquipe);
        return true;
    }

    public boolean retirerJoueur(Joueur joueur) {
        if (joueurs.remove(joueur)) {
            joueur.setNumeroEquipe(null);
            return true;
        }
        return false;
    }

    public boolean estCompletMax() {
        return joueurs.stream().filter(j -> j != null).count() >= 4;
    }

    public void changerOrdre(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= joueurs.size() || index2 >= joueurs.size())
            return;
        Joueur temp = joueurs.get(index1);
        joueurs.set(index1, joueurs.get(index2));
        joueurs.set(index2, temp);
    }

    public boolean echangerJoueurs(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= joueurs.size() || index2 >= joueurs.size()) {
            return false; // échange impossible
        }

        Joueur temp = joueurs.get(index1);
        joueurs.set(index1, joueurs.get(index2));
        joueurs.set(index2, temp);
        return true;
    }

    public boolean ajouterPlaceholder() {
        if (joueurs.size() >= 4) return false;
        joueurs.add(new Joueur("—", "Vide", Categorie.Inviter_Débutant));
        return true;
    }
}