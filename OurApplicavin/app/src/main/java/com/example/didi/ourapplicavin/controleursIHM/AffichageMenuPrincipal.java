package com.example.didi.ourapplicavin.controleursIHM;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.didi.ourapplicavin.R;

//Classe du menu principal
//pour ensuite accéder à la cave, liste de preférence, la bdd, faire une recherche ds la bdd
//et ajouter un vin ds la bdd
public class AffichageMenuPrincipal extends Activity {
    //Attributs
    private Button cave = null;
    private Button liste = null;
    private Button bdd = null;
    private Button recherche = null;
    private Button ajoutVinbdd = null;

    public final static String bddd = "bdd";

    //méthode qui se lance lors de cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_menu_principal); //on affiche le menu principal

        //on va chercher tous les boutons afin de leur assigner une action (ici changement d'activité)
        cave = (Button) findViewById(R.id.voirCave);
        liste = (Button) findViewById(R.id.voirListe);
        bdd = (Button) findViewById(R.id.voirBdd);
        recherche = (Button) findViewById(R.id.voidRecherche);
        ajoutVinbdd = (Button) findViewById(R.id.ajoutVinbdd);

    }

}
