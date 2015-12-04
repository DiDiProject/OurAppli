package com.example.didi.ourapplicavin.controleursIHM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;

//Classe du menu principal
//pour ensuite accéder à la cave, liste de preférence, la bdd, faire une recherche ds la bdd
//et ajouter un vin ds la bdd
public class AffichageMenuPrincipal extends Activity {
    //Attributs
    private Button cave = null;
    private Button pref = null;
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
        pref = (Button) findViewById(R.id.voirPref);
        bdd = (Button) findViewById(R.id.voirBdd);
        recherche = (Button) findViewById(R.id.voidRecherche);
        ajoutVinbdd = (Button) findViewById(R.id.ajoutVinbdd);

        cave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Réagir au clic
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de votre cave à vin virtuelle va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageCave.class);
                // Puis on lance l'intent !
                startActivity(secondeActivite);
            }
        });

        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de votre liste de préférence va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichagePref.class);
                startActivity(secondeActivite);
            }
        });

        bdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de la base de données va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageBdd.class);
                startActivity(secondeActivite);
            }
        });

    }

}
