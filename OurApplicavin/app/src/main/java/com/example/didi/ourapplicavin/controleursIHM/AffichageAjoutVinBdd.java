package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.didi.ourapplicavin.R;

//Classe qui permet à l'utilisateur d'ajouter un vin dans la bdd
public class AffichageAjoutVinBdd extends AppCompatActivity {
    // TODO

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_ajout_vin_bdd);
        // TODO
    }

    //Méthode qui perme de mettre un menu à l'écran
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
        else if( id == R.id.allerCave){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la liste de souhait)
        // on va dans l'activité AffichagePref
        else if( id == R.id.allerPref){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichagePref.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller à la bdd)
        // on va dans l'activité AffichageBdd
        else if( id == R.id.allerBdd){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
