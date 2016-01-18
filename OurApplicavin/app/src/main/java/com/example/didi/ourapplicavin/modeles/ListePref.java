package com.example.didi.ourapplicavin.modeles;

import java.io.Serializable;

/**
 * Created by didi on 05/12/2015.
 */
public class ListePref implements Serializable {
    private ListeVin pref;

    public ListePref(){
        pref = new ListeVin();
    }

    public void ajoutVin(Vin vin){
        pref.ajoutVin(vin);
    }

    public int supprVin(Vin vin){
        return pref.supprVin(vin);
    }

    public ListeVin getPref(){
        return pref;
    }

    public Vin getVin(int posi){
        return pref.getVin(posi);
    }

    //Méthode pour rechercher un vin avec le nom dans la bdd
    public ListeVin rechercheVinParNom(String nom){
        return pref.rechercheVinParNom(nom);
    }

    //Méthode pour rechercher un vin avec le nom dans la bdd
    public int rechercheVin(Vin vin){
        return pref.rechercheVin(vin);
    }

    //Méthode pour recherche des vins par critère dans la bdd
    public ListeVin rechercheVinParCritere(String nom, String robe, String cepage, String region){
        return pref.rechercheVinParCritere(nom, robe, cepage, region);
    }

    public String toString(){
        return pref.toString();
    }
}
