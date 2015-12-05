package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.didi.ourapplicavin.R;

//Classe qui permet à l'utilisateur d'ajouter un vin dans la bdd
public class AffichageAjoutVinBdd extends AppCompatActivity {
    // Attributs associé au layout
    private EditText nom = null; //pour récupérer le nom
    private EditText robe = null; //pour récupérer la couleur
    private EditText cepage = null; //pour récupérer le cépage
    private EditText region = null; //pour récupérer la région
    private Button ajoutVin = null; //bouton pour ajouter ce vin dans la bdd
    //Attributs assicié à cette classe
    private String stringNom = ""; //pour avoir le nom en string
    private String stringRobe = ""; //la couleur en string
    private String stringCepage = ""; //le cépage en string
    private String stringRegion = ""; //et la région en strinf

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_ajout_vin_bdd); //on affiche le layout associé
        //on va cherche tous les élements qui nous interresse dans le layout
        nom = (EditText) findViewById(R.id.nomAjout);
        robe = (EditText) findViewById(R.id.robeAjout);
        cepage = (EditText) findViewById(R.id.cepageAjout);
        region = (EditText) findViewById(R.id.regionAjout);
        ajoutVin = (Button)findViewById(R.id.ajoutVinDsBdd);

        //pour ajouter le vin avec les info de l'utilisateur dans la bdd
        ajoutVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on récupère les informations que l'utilisateur a rempli
                stringNom = nom.getText().toString();
                stringRobe = robe.getText().toString();
                stringCepage = cepage.getText().toString();
                stringRegion = region.getText().toString();
                // TODO
                // ajouter le vin dans la bdd

                // on initialise les champs pour un nouveau ajout
                nom.setText("");
                robe.setText("");
                cepage.setText("");
                region.setText("");

                //puis on revient au menu principal
                Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageMenuPrincipal.class);
                startActivity(n);
            }
        });

    }

    //Méthode qui permet de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_ajout_vin_bdd
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_ajout_vin_bdd, menu);
        return true;
    }

    //Méthode qui permet d'éxécuter une action quand on clique sur un sous menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // si on clique sur le sous menu (retour au menu principal)
        // on va dans l'activité AffichageMenuPrincipal
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la liste de souhait)
        // on va dans l'activité AffichagePref
        else if (id == R.id.allerPref) {
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichagePref.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller à la bdd)
        // on va dans l'activité AffichageBdd
        else if (id == R.id.allerBdd) {
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
