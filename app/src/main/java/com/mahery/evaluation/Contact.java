package com.mahery.evaluation;

public class Contact {
    private String nom;
    private String numero;
    private int image;

    public Contact(String nom, String numero, int image) {
        this.nom = nom;
        this.numero = numero;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
