package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;

//Classe qui affiche la base de données des vins
public class AffichageBdd extends AppCompatActivity {
    private GridView tab = null;
    private GridView tabNom = null;
    private String[] listeVins;
    private Button ajoutCave = null;
    private Button ajoutPref = null;
    private TextView texte = null;
    private TextView texteOu = null;
    private String nomVinSel = "";
    private int posi;

    public final static String cave = "bdd";
    final String NOM_VIN = "nom du vin";
    private int nbColParLigne = 4; //définit le nb de col par ligne pour la liste

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_bdd);

        //on va cherche le bouton retour et les deux tableaux qu'on a créer sur le layout
        //retour = (Button)findViewById(R.id.retourButton);
        tabNom = (GridView)findViewById(R.id.tabNomColBdd);
        tab = (GridView)findViewById(R.id.tabResultatVinBdd);
        ajoutCave = (Button)findViewById(R.id.ajouterCave);
        ajoutPref = (Button)findViewById(R.id.ajouterPref);
        texte = (TextView)findViewById(R.id.textView4);
        texteOu = (TextView)findViewById(R.id.textOu);

        boutonsInvisible();

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[] {
                "Nom du vin", "Type", "Nb de bouteilles", "Région"};
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        //on veut qu'il y ait que 3 colonnes sur une ligne
        tabNom.setNumColumns(nbColParLigne);
        //tabNom.setBackgroundColor(Color.CYAN);

        // TODO
        // il faudra mettre la liste des vins provenant de la cave à vin de l'utilisateur
        listeVins = new String[] {
                "Bordeaux", "rouge", "8", "rr1",
                "Cadillac", "blanc", "0", "rr1",
                "Riesling", "blanc", "5", "rr1",
                "Whispering Angel", "rosé", "3", "rr1",
                "MonBazillac", "blanc", "10", "rr1"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNom

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                    }
                }
                Toast.makeText(getApplicationContext(), "La description de " + nomVinSel + " va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                //on va à l'activité détailVin
                Intent n = new Intent(AffichageBdd.this, AffichageDetailVin.class);
                n.putExtra(NOM_VIN, nomVinSel);
                //en passant des données (nom du vin ici)
                startActivity(n);
            }
        });

        //
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //on rend visible les boutons ajout dans cave ou pref
                boutonsVisible();
                //on va chercher la position nomVin
                posi = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        posi = position - i;
                    }
                }
                //on surligne la ligne du vin
                changeCouleurLigneVin(posi);
                return true;
            }
        });

        // on ajout le vin dans la cave
        ajoutCave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO

                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();
                boutonsInvisible();
                rechangeCouleurLigneVin(posi);
            }
        });

        // on ajoute le vin dans la liste de préférence
        ajoutPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO

                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la liste de souhait !",
                        Toast.LENGTH_SHORT).show();
                boutonsInvisible();
                rechangeCouleurLigneVin(posi);
            }
        });

    }

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_bdd
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_bdd, menu);
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
            Intent n = new Intent(AffichageBdd.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave){
            Intent n = new Intent(AffichageBdd.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        else if (id == R.id.rechercheBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer une recherche dans la base de données !",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageRechercheVin.class);
            startActivity(n);
            return true;
        }
        else if (id == R.id.ajoutVinBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer un ajout de vin dans la base de données",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageAjoutVinBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void boutonsInvisible(){
        ajoutCave.setVisibility(View.INVISIBLE);
        ajoutPref.setVisibility(View.INVISIBLE);
        texte.setVisibility(View.INVISIBLE);
        texteOu.setVisibility(View.INVISIBLE);
        tab.setEnabled(true);
    }

    private void boutonsVisible(){
        ajoutCave.setVisibility(View.VISIBLE);
        ajoutPref.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        texteOu.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //on surligne la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position){
        for(int i = 0 ; i<nbColParLigne; i++){
            tab.getChildAt(position+i).setBackgroundColor(Color.rgb(176, 242, 182));
        }
    }

    //on désurligne la ligne
    private void rechangeCouleurLigneVin(int position){
        for(int i = 0 ; i<nbColParLigne; i++){
            tab.getChildAt(position+i).setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
