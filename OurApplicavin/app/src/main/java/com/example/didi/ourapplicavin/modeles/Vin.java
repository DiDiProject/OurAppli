package com.example.didi.ourapplicavin.modeles;

/**
 * //Classe Object vin
 * Created by didi on 05/12/2015.
 */
public class Vin {
    //Attributs
    private String nom; //nom du vin
    private String couleur; //couleur du vin
    private String cepage; //cépage du vin
    private String region; //région du vin

    //Constructeur d'inialisation vin (vide)
    public Vin(){
        // TODO
        nom = "";
        couleur ="";
        cepage = "";
        region ="";
    }

    //Constructeur d'inialisation vin (avec le nom)
    public Vin(String nom) {
        this.nom = nom;
    }

    //Constructeur d'inialiation d'un vin (avec le nom, la couleur, le cépage et la région du vin)
    public Vin(String nom, String couleur, String cepage, String region) {
        this.nom = nom;
        this.couleur = couleur;
        this.cepage = cepage;
        this.region = region;
    }

    //Méthode pour avoir le nom du vin
    public String getNom() {
        return nom;
    }

    //Méthode pour avoir la couleur du vin
    public String getCouleur() {
        return couleur;
    }

    //Méthode pour avoir le cépage du vin
    public String getCepage() {
        return cepage;
    }

    //Méthode pour avoir la région du vin
    public String getRegion() {
        return region;
    }
}
