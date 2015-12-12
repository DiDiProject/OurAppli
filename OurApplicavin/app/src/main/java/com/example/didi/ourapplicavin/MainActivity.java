package com.example.didi.ourapplicavin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.didi.ourapplicavin.controleurs.AffichageMenuPrincipal;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.ListeVin;
import com.example.didi.ourapplicavin.modeles.Vin;

import java.util.ArrayList;

//classe principale, celle qui va se lancer lors de l'ouverture de l'application
//juste un écran d'accueil avec un bouton pour ensuite accéder au menu principal
public class MainActivity extends Activity {
    //attribut du layout
    private Button menuPrincipal = null;
    private  Button quitter = null;
    private Button test = null;
    private Cave maCave = new Cave(new ListeVin(), new ArrayList<Integer>(), new ArrayList<String>());

    //méthode qui se fait en premier quand on lance l'application
    //car nous sommes dans la classe principale
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //on affiche l'écran d'accueil

        //on va chercher le bouton (grâce à son identifiant qu'on a choisi dans le layout)
        //pour accéder ensuite au menu principal
        menuPrincipal = (Button)findViewById(R.id.allerMenuPrincipal);
        quitter = (Button)findViewById(R.id.quitterAppliAccueil);
        test = (Button)findViewById(R.id.testBouton);
        //ainsi lorsqu'on appuie sur ce bouton il va dans une autre activité (le menu principale de l'application))
        menuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pour dire à l'utilisateur que le menu principal va s'afficher (texte qui s'affiche pas longtemps en bas)
                Toast.makeText(MainActivity.this, "Le contenu du menu principal va s'afficher !", Toast.LENGTH_SHORT).show();
                //on va chercher l'activité
                Intent n = new Intent(MainActivity.this, AffichageMenuPrincipal.class);
                startActivity(n); //on commence une nouvelle activité
            }
        });
        // pour quitter l'application
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on ferme complètement l'application (que depuis le main)
                finish();
                System.exit(0);
            }
        });

        //init();
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MainActivity.this, TestModele.class);
                //n.putExtra("cave", maCave);
                startActivity(n); //on commence une nouvelle activité
            }
        });
    }

    public void init() {
        maCave.ajoutVin(new Vin("Bordeaux", "rouge", "Merlot", "Gironde"), 3);
        maCave.ajoutVin(new Vin("Cadillac", "blanc", "rr", "Gironde"), 10);
        maCave.ajoutVin(new Vin("Riesling", "blanc", "fgg", "Gironde"), 1);
        maCave.ajoutVin(new Vin("Riesling2", "blanc", "fgg", "Gironde"), 2);
        maCave.ajoutVin(new Vin("Riesling3", "blanc", "fgg", "Gironde"), 4);
        // TODO
    }

    //pas besoin de mettre un menu pour cette écran
}
