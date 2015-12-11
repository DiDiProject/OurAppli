package com.example.didi.ourapplicavin.modeles;

import java.util.ArrayList;

/**
 * Classe d'une liste de vins
 * Created by didi on 05/12/2015.
 */
public class ListeVin {
    //Attributs
    private ArrayList<Vin> listeVins; //liste de vins
    private int nombreVins; //nombre de vin dans la liste

    //Constructeur d'initialisation d'une liste de vins (vide)
    public ListeVin (){
        this.listeVins = new ArrayList<Vin>();
        this.nombreVins = 0;
    }

    //Constructeur d'initialisation d'une liste de vins en ayant déjà une liste
    public ListeVin(ArrayList<Vin> listeVin){
        this.listeVins = listeVin;
        this.nombreVins = listeVin.size();
    }

    //Méthode pour avoir la liste des vins
    public ArrayList<Vin> getListeVins() {
        return listeVins;
    }

    //Méthode pour avoir un vin en ayant le nom du vin
    public Vin getVin(String nom){
        Vin vin = new Vin();
        //on parcourt la liste
        for(int i =0; i< listeVins.size(); i++){
            //si le nom du vin est le même, on retourne ce vin
            if(nom == listeVins.get(i).getNom()){
                vin = listeVins.get(i);
                break ;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
        return vin;
    }

    //Méthode pour ajouter un vin dans la liste
    public void ajoutVin(Vin vin){
        nombreVins +=1; //le nombre de vin augemente de 1
        listeVins.add(vin); //on ajoute ce vin dans la liste
    }

    //Méthode pour supprimer un vin de la liste
    public int supprVin(Vin vin){
        //on parcourt la liste des vins
        for(int i=0; i<listeVins.size(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(vin == listeVins.get(i)){
                listeVins.remove(i);
                nombreVins -=1; //le nombre de vin diminue de 1
                return i;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
        return -1;
    }

    //Méthode pour avoir le nombre de vins dans la liste
    public int getNombreVins() {
        return nombreVins;
    }

    //Méthode pour rechercher un vin avec le nom dans une liste de vin
    public Vin rechercheVinParNom(String nom){
        Vin vin = new Vin();
        for(int i=0; i<listeVins.size(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(nom == listeVins.get(i).getNom()){
                vin = listeVins.get(i);
                return vin;
            }
        }
        // TODO
        // mettre autre chose qu'un vin vide
        return vin;
    }

    //Méthode pour recherche des vins par critère dans une liste de vin
    public ListeVin rechercheVinParCritere(){
        ListeVin liste = new ListeVin();
        // TODO
        return liste;
    }
}
