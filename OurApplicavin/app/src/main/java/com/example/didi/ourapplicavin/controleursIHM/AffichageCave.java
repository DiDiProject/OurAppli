package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.didi.ourapplicavin.R;

//Classe qui affiche la liste des vins de la cave "virtuelle" de l'utilisateur
//elle est appelée via le menu principal
public class AffichageCave extends AppCompatActivity {
    //Attributs (boutons pour + ou - nbBouteille,
    // les tableaux (pour nom des col et la liste de vins ...)
    private Button retour = null;
    private Button augmenter = null;
    private Button diminuer = null;
    private EditText nb = null;
    private TextView texte = null;
    private Button ok = null;
    private GridView tab = null;
    private GridView tabNom = null;
    private int nbBouteilles;
    private String[] listeVins;
    private int nbBouteilleActualiser = 0;
    private int positionTabNb = 0;

    public final static String cave = "cave";

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cave);

        //on va cherche le bouton retour et les deux tableaux qu'on a créer sur le layout
        //retour = (Button)findViewById(R.id.retourButton);
        tabNom = (GridView)findViewById(R.id.tabNomCol);
        tab = (GridView)findViewById(R.id.tabResultatVin);
        augmenter = (Button)findViewById(R.id.ajoutCave);
        diminuer = (Button)findViewById(R.id.ajoutSouhait);
        nb = (EditText)findViewById(R.id.nbBouteille);
        texte = (TextView)findViewById(R.id.textView3);
        ok = (Button)findViewById(R.id.ok);

        //on met les boutons + et - invisible car pas besoin maintenant
        boutonsInvisible();

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
    // ce menu est définit dans menu_affichage_cave
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_cave, menu); //on affiche le menu de la cave
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
            Intent n = new Intent(AffichageCave.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend invisble les boutons + et -
    //et le tableau de la liste de vins devient actif
    private void boutonsInvisible(){
        augmenter.setVisibility(View.INVISIBLE);
        diminuer.setVisibility(View.INVISIBLE);
        nb.setVisibility(View.INVISIBLE);
        ok.setVisibility(View.INVISIBLE);
        texte.setVisibility(View.INVISIBLE);
        tab.setEnabled(true);
    }

    //Méthode qui rend visble les boutons + et - pour que l'utilisateur augmente ou diminue le nb de
    //bouteilles d'un vin et le tableau de la liste de vins devient inactif
    // (pour pas que l'utilisateur puisse cliquer sur un autre vns)
    private void boutonVisible(){
        augmenter.setVisibility(View.VISIBLE);
        diminuer.setVisibility(View.VISIBLE);
        nb.setVisibility(View.VISIBLE);
        ok.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }
}
