package com.example.didi.ourapplicavin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.didi.ourapplicavin.controleursIHM.AffichageMenuPrincipal;

//classe principale, celle qui va se lancer lors de l'ouverture de l'application
//juste un écran d'accueil avec un bouton pour ensuite accéder au menu principal
public class MainActivity extends Activity {
    //attribut du layout
    private Button menuPrincipal = null;
    private  Button quitter = null;

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

    }

    //pas besoin de mettre un menu pour cette écran
}
