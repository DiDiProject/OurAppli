package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.didi.ourapplicavin.R;

//Classe qui affiche la description d'un vin
// (de la cave, de la liste de pref, de la bdd ou d'une recherche)
public class AffichageDetailVin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_vin);
    }

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_detail_vin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_detail_vin, menu);
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
            Intent n = new Intent(AffichageDetailVin.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
