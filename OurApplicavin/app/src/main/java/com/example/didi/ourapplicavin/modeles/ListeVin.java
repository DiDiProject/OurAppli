package com.example.didi.ourapplicavin.modeles;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe d'une liste de vins
 * Created by didi on 05/12/2015.
 */
public class ListeVin implements Serializable {
    //Attributs
    private ArrayList<Vin> listeVins; //liste de vins
    private int nombreVins; //nombre de vin dans la liste

    //Constructeur d'initialisation d'une liste de vins (vide)
    public ListeVin() {
        this.listeVins = new ArrayList<>();
        this.nombreVins = 0;
    }

    //Constructeur d'initialisation d'une liste de vins en ayant déjà une liste
    public ListeVin(ArrayList<Vin> listeVin) {
        this.listeVins = listeVin;
        this.nombreVins = listeVin.size();
    }

    //Méthode pour avoir la liste des vins
    public ArrayList<Vin> getListeVins() {
        return listeVins;
    }

    public Vin getVin(int position) {
        return listeVins.get(position);
    }

    //Méthode pour ajouter un vin dans la liste
    public void ajoutVin(Vin vin) {
        nombreVins += 1; //le nombre de vin augemente de 1
        listeVins.add(vin); //on ajoute ce vin dans la liste
    }

    //Méthode pour supprimer un vin de la liste
    public int supprVin(Vin vin) {
        //on parcourt la liste des vins
        for (int i = 0; i < listeVins.size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin == listeVins.get(i)) {
                listeVins.remove(i);
                nombreVins -= 1; //le nombre de vin diminue de 1
                return i;
            }
        }
        // exception si on n'a pas trouvé le vin
        return -1;
    }

    //Méthode pour avoir le nombre de vins dans la liste
    public int getNombreVins() {
        return nombreVins;
    }

    //Méthode pour rechercher un vin avec le nom dans une liste de vin
    public ListeVin rechercheVinParNom(String nom) {
        ListeVin listeVin = new ListeVin();
        Log.i("ListeVin", "liste vin size = " + listeVins.size());
        for (int i = 0; i < listeVins.size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (nom.equals(listeVins.get(i).getNom())) {
                listeVin.ajoutVin(listeVins.get(i));
            }
        }
        // mettre autre chose qu'un vin vide
        return listeVin;
    }

    //Méthode pour rechercher un vin avec le nom dans une liste de vin
    public int rechercheVin(Vin vin) {
        Log.i("ListeVin", "liste vin size = " + listeVins.size());
        for (int i = 0; i < listeVins.size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin.getNom().equals(listeVins.get(i).getNom()) && vin.getCouleur().equals(listeVins.get(i).getCouleur()) &&
                    vin.getCepage().get(0).equals(listeVins.get(i).getCepage().get(0)) && vin.getRegion().equals(listeVins.get(i).getRegion())) {
                return i;
            }
        }
        // mettre autre chose qu'un vin vide
        return -1;
    }

    //Méthode pour rechercher un vin avec le nom dans une liste de vin
    public int rechercheVinCave(Vin vin) {
        Log.i("ListeVin", "liste vin size = " + listeVins.size());
        for (int i = 0; i < listeVins.size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            if (vin.getNom().equals(listeVins.get(i).getNom()) && vin.getCouleur().equals(listeVins.get(i).getCouleur()) &&
                    vin.getCepage().get(0).equals(listeVins.get(i).getCepage().get(0))
                    && vin.getMillesime().equals(listeVins.get(i).getMillesime())) {
                return i;
            }
        }
        // mettre autre chose qu'un vin vide
        return -1;
    }

    //Méthode pour recherche des vi{
    // ns par critère dans une liste de vin
    public ListeVin rechercheVinParCritere(String nom, String robe, String cepage, String region) {
        ListeVin liste = new ListeVin();
        // TODO
        Log.i("ListeVin", "commencement rechercher par critère" + nom + robe + cepage + region);
        boolean nom_b = false;
        boolean nom_b2 = false;
        boolean robe_b = false;
        boolean robe_b2 = false;
        boolean cepage_b = false;
        boolean cepage_b2 = false;
        boolean region_b = false;
        boolean region_b2 = false;
        Log.i("ListeVin", " taille  " + listeVins.size());
        for (int i = 0; i < listeVins.size(); i++) {
            //si le vin est le même que celui à supprimer on l'enlève dans la liste
            Log.i("ListeVin", " nom " + listeVins.get(i).getNom());
            if (!nom.equals("")) {
                Log.i("ListeVin", " nom " + listeVins.get(i).getNom());
                if (nom.equals(listeVins.get(i).getNom())) {
                    nom_b = true;
                    Log.i("ListeVin", "nom ok");
                }
            } else {
                nom_b2 = true;
            }
            if (!robe.equals("")) {
                if (robe.equals(listeVins.get(i).getCouleur())) {
                    robe_b = true;
                    Log.i("ListeVin", "robe ok");
                }
            } else {
                robe_b2 = true;
            }
            if (!cepage.equals("")) {
                if (cepage.equals(listeVins.get(i).getCepage().get(0))) {
                    cepage_b = true;
                    Log.i("ListeVin", "cépage ok");
                }
            } else {
                cepage_b2 = true;
            }
            if (!region.equals("")) {
                if (region.equals(listeVins.get(i).getRegion())) {
                    region_b = true;
                    Log.i("ListeVin", "région ok");
                }
            } else {
                region_b2 = true;
            }
            if((nom_b2)){
                if(robe_b2) {
                    if(cepage_b2) {
                        if (region_b2) {

                        } else {
                            if(region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    } else {
                        if (region_b2) {
                            if(cepage_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(cepage_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    }
                } else {
                    if(cepage_b2){
                        if (region_b2) {
                            if(robe_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(robe_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    } else {
                        if (region_b2) {
                            if(robe_b && cepage_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(robe_b && cepage_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    }
                }
            } else {
                if(robe_b2) {
                    if(cepage_b2) {
                        if (region_b2) {
                            if(nom_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(nom_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    } else {
                        if (region_b2) {
                            if(nom_b && cepage_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(nom_b && cepage_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    }
                } else {
                    if(cepage_b2){
                        if (region_b2) {
                            if(nom_b && robe_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(nom_b && robe_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    } else {
                        if (region_b2) {
                            if(nom_b && robe_b && cepage_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        } else {
                            if(nom_b && robe_b && cepage_b && region_b) {
                                Log.i("ListeVin", i + " vin " + listeVins.get(i));
                                liste.ajoutVin(listeVins.get(i));
                            }
                        }
                    }
                }
            }
            nom_b = false;
            nom_b2 = false;
            robe_b = false;
            robe_b2 = false;
            cepage_b2 = false;
            cepage_b = false;
            region_b = false;
            region_b2 = false;
        }
        return liste;
    }

    public String toString() {
        String affichage = "Liste des vins :";
        for (int i = 0; i < listeVins.size(); i++) {
            affichage += "\n- Nom = " + listeVins.get(i).getNom();
            affichage += "\n- Robe = " + listeVins.get(i).getCouleur();
            affichage += "\n- Cépage = " + listeVins.get(i).getCepage();
            affichage += "\n- Région = " + listeVins.get(i).getRegion() + "\n";
        }
        affichage = "Fin de la liste des vins.";
        return affichage;
    }
}
