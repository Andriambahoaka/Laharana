package com.mahery.evaluation;

import android.graphics.Bitmap;

public class Contact {
    private String nom;
    private String numero;
    private Bitmap image;

    public Contact(String nom, String numero, Bitmap image) {
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
