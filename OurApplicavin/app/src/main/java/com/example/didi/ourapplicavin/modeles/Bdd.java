package com.example.didi.ourapplicavin.modeles;

import java.io.Serializable;

/**
 * Classe pour avoir la liste des vins de la base de données (du serveur externe)
 * Created by didi on 05/12/2015.
 */
public class Bdd implements Serializable {
    //Attributs
    private ListeVin bdd;

    //Constructeur d'initialisation de la bdd
    public Bdd(){
        bdd = new ListeVin();
    }

    //Méthode pour avoir la liste de vin de la bdd
    public ListeVin getBdd(){
        return bdd;
    }

   //Méthode pour ajouter un vin à la bdd
    public void ajoutVin(Vin vin){
        bdd.ajoutVin(vin);
    }

    //Méthode pour supprimer un vin à la bdd
    public int supprVin(Vin vin){
        return bdd.supprVin(vin);
    }

    public Vin getVin(int posi){
        return bdd.getVin(posi);
    }


    //Méthode pour rechercher un vin avec le nom dans la bdd
    public Vin rechercheVinParNom(String nom){
        return bdd.rechercheVinParNom(nom);
    }

    //Méthode pour rechercher un vin avec le nom dans la bdd
    public int rechercheVin(Vin vin){
        return bdd.rechercheVin(vin);
    }

    //Méthode pour recherche des vins par critère dans la bdd
    public ListeVin rechercheVinParCritere(){
        return bdd.rechercheVinParCritere();
    }

}
