package com.example.didi.ourapplicavin.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * //Classe Object vin
 * Created by didi on 05/12/2015.
 */
public class Vin implements Serializable {
    //Attributs
    private String nom; //nom du vin
    private String couleur; //couleur du vin
    private ArrayList<String> cepage; //cépage du vin
    private String region; //région du vin
    private int nbBouteille;
    private String millesime;
    private String remarques;
    private FicheVin detail;

    //Constructeur d'inialisation vin (vide)
    public Vin() {
        // TODO
        nom = "";
        couleur = "";
        cepage = new ArrayList<String>();
        cepage.add("");
        region = "";
        nbBouteille = 0;
        millesime = "";
        remarques = "";
        detail = new FicheVin(this, "");
    }

    //Constructeur d'inialisation vin (avec le nom)
    public Vin(String nom) {
        this.nom = nom;
    }

    //Constructeur d'inialiation d'un vin pr bdd et pref (avec le nom, la couleur, le cépage et la région du vin)
    public Vin(String nom, String couleur, ArrayList<String> cepage, String region) {
        this.nom = nom;
        this.couleur = couleur;
        this.cepage = cepage;
        this.region = region;
        this.detail = new FicheVin(this, "");
    }

    //Constructeur d'inialiation d'un vin pr cave(avec le nom, la couleur, le cépage, la région du vin ...)
    public Vin(String nom, String couleur, ArrayList<String> cepage, String region, int nbBouteille, String millesime) {
        this.nom = nom;
        this.couleur = couleur;
        this.cepage = cepage;
        this.region = region;
        this.nbBouteille = nbBouteille;
        this.millesime = millesime;
        this.detail = new FicheVin(this, "");
    }

    //Constructeur d'inialiation d'un vin pr cave(avec le nom, la couleur, le cépage, la région du vin ...)
    public Vin(Vin vin, int nbBouteille, String millesime) {
        this.nom = vin.getNom();
        this.couleur = vin.getCouleur();
        this.cepage = vin.getCepage();
        this.region = vin.getRegion();
        this.nbBouteille = nbBouteille;
        this.millesime = millesime;
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
    public ArrayList<String> getCepage() {
        return cepage;
    }

    //Méthode pour avoir la région du vin
    public String getRegion() {
        return region;
    }

    public String getRemarques() {
        return remarques;
    }

    public String getMillesime() {
        return millesime;
    }

    public int getNbBouteille() {
        return nbBouteille;
    }

    public void setNbBouteille(int nbBouteille) {
        this.nbBouteille = nbBouteille;
    }

    public FicheVin getDetail() {
        return detail;
    }

    public boolean compareVin(Objects v) {
        // TODO
        return true;
    }

    public String toString() {
        String affichage = "";
        affichage += "\n- Robe : " + couleur;
        affichage += "\n- Cépage(s) : " + cepage;
        affichage += "\n- Région : " + region; ;
        return affichage;
    }

    public String toStringCave() {
        String affichage = "";
        affichage += "\n- Robe : " + couleur;
        affichage += "\n- Cépage(s) : " + cepage;
        affichage += "\n- Région : " + region;
        affichage += "\n- Nb : " + nbBouteille;
        affichage += "\n- Millésime : " + millesime ;
        return affichage;
    }
}
