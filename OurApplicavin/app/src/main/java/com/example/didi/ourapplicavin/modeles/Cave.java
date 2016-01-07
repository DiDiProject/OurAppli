package com.example.didi.ourapplicavin.modeles;

import android.util.Log;

import java.io.Serializable;

/**
 * Classe pour avoir la liste des vins de la cave de l'utilisateur
 * Created by didi on 05/12/2015.
 */
public class Cave implements Serializable {
    //Attributs
    private ListeVin maCave; //la liste de vins

    //Constructeur d''initialisation
    public Cave(){
        maCave = new ListeVin();
    }

    //Méthode pour ajouter un vin dans la cave
    public void ajoutVin(final Vin vin){
        maCave.ajoutVin(vin); //on ajoute le vin dans la liste
    }

    //Méthode pour supprimer un vin de la cave
    public int supprVin(final Vin vin){
        return maCave.supprVin(vin); //on supprime ce vin de la liste
    }

    //Méthode pour avoir la cave
    public Cave getMaCave() {
        return this;
    }

    //Méthode pour avoir juste la liste des vins (ss nb et rq)
    public ListeVin getVinsCave(){
        return maCave;
    }

    public void setMaCave(final ListeVin maCave) {
        this.maCave = maCave;
    }

    public Vin getVin(final int posi){
        return maCave.getVin(posi);
    }

    //Méthode pour rechercher un vin avec le nom dans la cave
    public ListeVin rechercheVinParNom(String nom){
        return maCave.rechercheVinParNom(nom);
    }

    //Méthode pour rechercher un vin avec le nom dans la cave
    public int rechercheVin(final Vin vin){
        Log.i("ListeVin", "liste vin size = " + maCave.getListeVins().size());
        for (int i = 0; i < maCave.getListeVins().size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin.getNom().equals(maCave.getListeVins().get(i).getNom())&& vin.getCouleur().equals(maCave.getListeVins().get(i).getCouleur()) &&
                    vin.getCepage().get(0).equals(maCave.getListeVins().get(i).getCepage().get(0))
                    && vin.getMillesime().equals(maCave.getListeVins().get(i).getMillesime())){
                return i;
            }
        }
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
