package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.didi.ourapplicavin.R;

public class AffichageAjoutVinBdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_ajout_vin_bdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_ajout_vin_bdd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        } else if( id == R.id.allerCave){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageCave.class);
            startActivity(n);
            return true;
        } else if( id == R.id.allerPref){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichagePref.class);
            startActivity(n);
            return true;
        } else if( id == R.id.allerBdd){
            Intent n = new Intent(AffichageAjoutVinBdd.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
