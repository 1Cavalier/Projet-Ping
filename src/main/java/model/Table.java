package model;

import java.io.Serializable;

public class Table implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private boolean occupee;
    private boolean active;
    private Rencontre rencontreActuelle;

    public Table(int numero) {
        this.numero = numero;
        this.occupee = false;
        this.active = true;
        this.rencontreActuelle = null;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOccupee() {
        return occupee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void occuper(Rencontre rencontre) {
        this.occupee = true;
        this.rencontreActuelle = rencontre;
    }

    public void liberer() {
        this.occupee = false;
        this.rencontreActuelle = null;
    }

    public Rencontre getRencontreActuelle() {
        return rencontreActuelle;
    }

    @Override
    public String toString() {
        if (!active)
            return "Table " + numero + " (désactivée)";
        return "Table " + numero + (occupee ? " (occupée)" : " (libre)");
    }
}
