package com.example.didi.ourapplicavin.controleurs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.didi.ourapplicavin.MainActivity;
import com.example.didi.ourapplicavin.R;
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.ListePref;
import com.example.didi.ourapplicavin.modeles.Vin;

import java.io.File;

//Classe du menu principal
//pour ensuite accéder à la cave, liste de preférence, la bdd, faire une recherche ds la bdd
//et ajouter un vin ds la bdd
public class AffichageMenuPrincipal extends AppCompatActivity {
    //Attributs
    public final static String bddd = "bdd"; // TODO pour dire qu'on ait dans la bdd pr recherche
    private Cave maCave = new Cave();
    private ListePref pref = new ListePref();
    private Bdd bdd = new Bdd();

    //méthode qui se lance lors de cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_menu_principal); //on affiche le menu principal

        //on va chercher tous les boutons afin de leur assigner une action (ici changement d'activité)
        Button cave = (Button) findViewById(R.id.voirCave); //bouton pour accéder à la cave
        Button pref = (Button) findViewById(R.id.voirPref); //bouton pour accéder à sa liste de souhait
        Button bdd = (Button) findViewById(R.id.voirBdd); //bouton pour accéder à la bdd
        Button recherche = (Button) findViewById(R.id.faireRechercheBdd); //bouton pour faire une recherche dans la bdd
        Button ajoutVinbdd = (Button) findViewById(R.id.ajoutVinbdd); //bouton pour ajouter un vin dans la bdd
        Button quitter = (Button) findViewById(R.id.quitterAppliPrincipal); //bouton pour quitter le menu principal

        init();

        //clique sur bouton voir sa cave à vin
        cave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Réagir au clic
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                //Affchage court
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de votre cave à vin virtuelle va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageCave.class);
                // Puis on lance l'intent !
                startActivity(secondeActivite);
            }
        });

        //clique sur bouton voir sa liste de souhait
        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de votre liste de souhait va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichagePref.class);
                startActivity(secondeActivite);
            }
        });

        //clique sur bouton voir la bdd
        bdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Le contenu de la base de données va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageBdd.class);
                startActivity(secondeActivite);
            }
        });

        //clique sur bouton recherche dans bdd
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Vous aller effectuer une recherche dans la base de données !",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageRechercheVin.class);
                // TODO
                //dire qu'on ait dans la bdd pour la recherche
                startActivity(secondeActivite);
            }
        });

        //clique sur bouton ajout vin dans la bdd
        ajoutVinbdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AffichageMenuPrincipal.this, "Vous aller effectuer un ajout de vin dans la base de données",
                        Toast.LENGTH_SHORT).show();
                Intent secondeActivite = new Intent(AffichageMenuPrincipal.this, AffichageAjoutVinBdd.class);
                startActivity(secondeActivite);
            }
        });

        //pour quitter l'application
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on retourne à la page d'accueil
                Intent n = new Intent(AffichageMenuPrincipal.this, MainActivity.class);
                // on enlève l'activité précédente
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });

        //création du dossier AppliCavin sur le téléphone pour enregistrer la cave et la liste de souhait (et pour l'instant la bdd)
        File dir = new File (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) +"/AppliCavin");
        if(!dir.exists()) {
            dir.mkdirs(); //s'il n'exite pas on le créer
        }
    }

    //Méthode qui permet de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_cave
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_menu_principal, menu); //on affiche le menu de la cave
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
        // on va dans l'activité menu principal
        if (id == R.id.renitialiserCave) {
            // on supprime le fichier associé la cave et à la pref
            // (on va le récréer après si l'utilisateur va dans sa cave ou ds pref)
            final File fichier = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/AppliCavin/maCave.ser");
            fichier.delete();
            maCave = new Cave();
            GestionSauvegarde.enregistrementCave(maCave); //enregistrement de la cave (vide pour l'instant)
            return true;
        } else if (id == R.id.renitialiserPref){
            final File fichier = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/AppliCavin/pref.ser");
            fichier.delete();
            pref = new ListePref();
            GestionSauvegarde.enregistrementPref(pref); //enregistrement de la cave (vide pour l'instant)
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Méthode pour initialiser la cave et la pref(donc avec 0 vin)
    public void init() {
        maCave = GestionSauvegarde.getCave();
        pref = GestionSauvegarde.getPref();
        bdd = GestionSauvegarde.getBdd();
        if (maCave == null) {
            maCave = new Cave();
            GestionSauvegarde.enregistrementCave(maCave); //enregistrement de la cave (vide pour l'instant)
        }
        if (pref == null) {
            pref = new ListePref();
            GestionSauvegarde.enregistrementPref(pref); //enregistrement de la cave (vide pour l'instant)
        }
        if (bdd == null) {
            bdd = new Bdd();
            bdd.ajoutVin(new Vin("Bordeaux", "rouge", "Merlot", "Aquitaine"));
            bdd.ajoutVin(new Vin("Bordeaux", "blanc", "Saivignon", "Aquitaine"));
            bdd.ajoutVin(new Vin("Cadillac", "blanc", "Muscadelle", "Aquitaine"));
            bdd.ajoutVin(new Vin("Riesling", "blanc", "Semillon", "Alsace"));
            bdd.ajoutVin(new Vin("Whispering Angel", "rosé", "Grenache", "Provence"));
            GestionSauvegarde.enregistrementBdd(bdd); //enregistrement de la cave (vide pour l'instant)
        }

    }

}
