package model;

import java.io.Serializable;

public class Table implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private boolean occupee;

    public Table(int numero) {
        this.numero = numero;
        this.occupee = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOccupee() {
        return occupee;
    }

    public void occuper() {
        this.occupee = true;
    }

    public void liberer() {
        this.occupee = false;
    }

    @Override
    public String toString() {
        return "Table " + numero + (occupee ? " (occupée)" : " (libre)");
    }
}
