package com.example.didi.ourapplicavin.modeles;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe pour avoir la liste des vins de la cave de l'utilisateur
 * Created by didi on 05/12/2015.
 */
public class Cave implements Serializable {
    //Attributs
    private ListeVin maCave; //la liste de vins
    //private ArrayList<Integer> nbBouteille; //on associe à chaque vin un nb de bouteille
    //private ArrayList<String> remarques; //et des remarques personnelles

    //Constructeur d''initialisation
    public Cave(){
        maCave = new ListeVin();
        //nbBouteille = new ArrayList<Integer>();
        //remarques = new ArrayList<String>();
    }

    public Cave(ListeVin liste, ArrayList<Integer> nb, ArrayList<String> rq){
        maCave = new ListeVin();
        //nbBouteille = new ArrayList<Integer>();
        //remarques = new ArrayList<String>();
    }

    //Méthode pour ajouter un vin dans la cave
    public void ajoutVin(Vin vin){
        maCave.ajoutVin(vin); //on ajoute le vin dans la liste
        //nbBouteille.add(nb);
        // TODO
        //vérifier quand on ajoute le vin à la liste, le nb est bien ajouter
        // à la même position de la liste de nb Bouteille
        //remarques.add("à compléter"); //on initialise le remarques (pout l'instant l'utilisateur n'a rien mis)
    }

    //Méthode pour supprimer un vin de la cave
    public int supprVin(Vin vin){
        int posi = maCave.supprVin(vin); //on supprime ce vin de la liste
        /*if(posi >= 0){
            nbBouteille.remove(posi); //on enlève le nb de bouteille
            remarques.remove(posi); //ainsi que les remarques
        }*/
        return  posi;
    }

    //Méthode pour avoir la cave
    public Cave getMaCave() {
        return this;
    }

    //Méthode pour avoir juste la liste des vins (ss nb et rq)
    public ListeVin getVinsCave(){
        return maCave;
    }

    public Vin getVin(int posi){
        return maCave.getVin(posi);
    }

    /*//Méthode pour avoir les remarques d'un vin de la cave
    public String getRemarquesVin(Vin vin) {
        String rq = "";
        for(int i=0; i<maCave.getNombreVins(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(vin == maCave.getListeVins().get(i)){
                rq = remarques.get(i);
                return rq;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
        return rq;
    }*/

    /*//Méthode pour avoir le nb de bouteille d'un vin de la cave
    public int getNbBouteilleVin(Vin vin) {
        int nb = 0;
        for(int i=0; i<maCave.getNombreVins(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(vin == maCave.getListeVins().get(i)){
                nb = nbBouteille.get(i);
                return nb;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
        return nb;
    }*/

    /*//Méthode pour changer le nombre de bouteille d'un vin de la cave
    public void setNbBouteilleVin(Vin vin, int nb) {
        for(int i=0; i<maCave.getNombreVins(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(vin == maCave.getListeVins().get(i)){
                nbBouteille.set(i, nb);
                break;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
    }

    //Méthode pour changer le remarques d'un vin de la cave
    public void setRemarquesVin(Vin vin, String rq) {
        for(int i=0; i<maCave.getNombreVins(); i++){
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if(vin == maCave.getListeVins().get(i)){
                remarques.set(i, rq);
                break;
            }
        }
        // TODO
        // exception si on n'a pas trouvé le vin
    }*/

    //Méthode pour rechercher un vin avec le nom dans la cave
    public ListeVin rechercheVinParNom(String nom){
        return maCave.rechercheVinParNom(nom);
    }

    //Méthode pour rechercher un vin avec le nom dans la cave
    public int rechercheVin(Vin vin){
        Vin v = new Vin();
        Log.i("ListeVin", "liste vin size = " + maCave.getListeVins().size());
        for (int i = 0; i < maCave.getListeVins().size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin.getNom().equals(maCave.getListeVins().get(i).getNom())&& vin.getCouleur().equals(maCave.getListeVins().get(i).getCouleur()) &&
                    vin.getCepage().get(0).equals(maCave.getListeVins().get(i).getCepage().get(0))
                    && vin.getMillesime().equals(maCave.getListeVins().get(i).getMillesime())){
                v = maCave.getListeVins().get(i);
                return i;
            }
        }
        // TODO
        // mettre autre chose qu'un vin vide
        return -1;
    }

    //Méthode pour rechercher un vin avec le nom dans la cave
    public int rechercheVinBdd(Vin vin){
        Vin v = new Vin();
        Log.i("ListeVin", "liste vin size = " + maCave.getListeVins().size());
        for (int i = 0; i < maCave.getListeVins().size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin.getNom().equals(maCave.getListeVins().get(i).getNom())&& vin.getCouleur().equals(maCave.getListeVins().get(i).getCouleur()) &&
                    vin.getCepage().get(0).equals(maCave.getListeVins().get(i).getCepage().get(0))
                    && vin.getRegion().equals(maCave.getListeVins().get(i).getRegion())){
                v = maCave.getListeVins().get(i);
                return i;
            }
        }
        // TODO
        // mettre autre chose qu'un vin vide
        return -1;
    }


    //Méthode pour recherche des vins par critère dans la cave
    public ListeVin rechercheVinParCritere(){
        return maCave.rechercheVinParCritere();
    }

    public String toString(){
        String affichage = "Liste des vins :";
        for (int i = 0; i < maCave.getListeVins().size(); i++) {
            affichage += "\n Nom : " + maCave.getListeVins().get(i).getNom();
            affichage += "\n Robe : " + maCave.getListeVins().get(i).getCouleur();
            affichage += "\n Cépage(s) : " + maCave.getListeVins().get(i).getCepage();
            affichage += "\n Région : " + maCave.getListeVins().get(i).getRegion();
            affichage += "\n Nombre de bouteille : " + maCave.getListeVins().get(i).getNbBouteille();
            affichage += "\n Millésime : " + maCave.getListeVins().get(i).getMillesime() + "\n";
        }
        affichage = "Fin de la liste des vins.";
        return affichage;
    }
}
