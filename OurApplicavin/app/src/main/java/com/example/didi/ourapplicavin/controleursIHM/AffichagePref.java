package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.didi.ourapplicavin.R;

//Classe qui permet d'afficher la liste de préférence de l'utilisateur
public class AffichagePref extends AppCompatActivity {
    private GridView tab = null;
    private GridView tabNom = null;
    private String[] listeVins;

    public final static String cave = "pref";

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_pref);

        //on va cherche le bouton retour et les deux tableaux qu'on a créer sur le layout
        //retour = (Button)findViewById(R.id.retourButton);
        tabNom = (GridView)findViewById(R.id.tabNomColPref);
        tab = (GridView)findViewById(R.id.tabResultatVinPref);

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[] {
                "Nom du vin", "Type", "Nb de bouteilles"};
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        //on veut qu'il y ait que 3 colonnes sur une ligne
        tabNom.setNumColumns(3);
        //tabNom.setBackgroundColor(Color.CYAN);

        // TODO
        // il faudra mettre la liste des vins provenant de la cave à vin de l'utilisateur
        listeVins = new String[] {
                "Bordeaux", "rouge", "8",
                "Cadillac", "blanc", "0",
                "Riesling", "blanc", "5",
                "Whispering Angel", "rosé", "3",
                "MonBazillac", "blanc", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(3); //définit le nombre de colonne par ligne comme tabNom
    }

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_pref
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_pref, menu);
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
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichagePref.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave){
            Intent n = new Intent(AffichagePref.this, AffichageCave.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
