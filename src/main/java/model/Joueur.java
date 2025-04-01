package model;

import java.io.Serializable;

public class Joueur implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nom;
    private String prenom;
    private Categorie categorie;
    private Integer numeroEquipe;

    public Joueur(String nom, String prenom, Categorie categorie) {
        this.nom = nom;
        this.prenom = prenom;
        this.categorie = categorie;
        this.numeroEquipe = null; // par défaut
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Integer getNumeroEquipe() {
        return numeroEquipe;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setNumeroEquipe(Integer numeroEquipe) {
        this.numeroEquipe = numeroEquipe;
    }

    public boolean isPlaceholder() {
        return this.getNom().equals("—") && this.getPrenom().equals("Vide");
    }    
    
    @Override
    public String toString() {
        return nom + " " + prenom + " (" + categorie + ") - Équipe: " + 
               (numeroEquipe != null ? numeroEquipe : "Non assigné");
    }
}
