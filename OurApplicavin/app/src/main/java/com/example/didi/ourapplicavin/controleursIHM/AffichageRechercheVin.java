package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.didi.ourapplicavin.R;

//classe pour faire une recherche (par nom ou par critère) soit dans la cave ou dans la bdd
public class AffichageRechercheVin extends AppCompatActivity {

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_recherche_vin);
    }

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_rechercher_vin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_recherche_vin, menu);
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
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        } else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        else if (id == R.id.retourBdd) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
