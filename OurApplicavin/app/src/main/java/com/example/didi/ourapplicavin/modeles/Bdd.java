package com.example.didi.ourapplicavin.modeles;

import java.io.Serializable;

/**
 * Classe pour avoir la liste des vins de la base de données (du serveur externe)
 * Created by didi on 05/12/2015.
 */
public class Bdd extends ListeVin implements Serializable {
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
    @Override
    public void ajoutVin(Vin vin){
        bdd.ajoutVin(vin);
    }

    //Méthode pour supprimer un vin à la bdd
    public int supprVin(Vin vin){
        return bdd.supprVin(vin);
    }


    //Méthode pour rechercher un vin avec le nom dans la bdd
    public Vin rechercheVinParNom(String nom){
        return bdd.rechercheVinParNom(nom);
    }

    //Méthode pour recherche des vins par critère dans la bdd
    public ListeVin rechercheVinParCritere(){
        return bdd.rechercheVinParCritere();
    }

}
