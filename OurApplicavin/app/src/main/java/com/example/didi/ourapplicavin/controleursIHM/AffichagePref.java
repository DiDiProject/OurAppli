package com.example.didi.ourapplicavin.controleursIHM;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

//Classe qui permet d'afficher la liste de préférence de l'utilisateur
public class AffichagePref extends AppCompatActivity {
    private GridView tab = null;
    private GridView tabNom = null;
    private Button ajoutCave = null;
    private Button supprVin = null;
    private Button annuler = null;
    private String[] listeVins;
    private String nomVinSel = "";

    public final static String cave = "pref";
    final String NOM_VIN = "nom du vin";
    private int nbColParLigne = 4; //définit le nb de col par ligne pour la liste
    private int posiNom = 0;

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_pref);

        //on va cherche le bouton retour et les deux tableaux qu'on a créer sur le layout
        //retour = (Button)findViewById(R.id.retourButton);
        tabNom = (GridView) findViewById(R.id.tabNomColPref);
        tab = (GridView) findViewById(R.id.tabResultatVinPref);
        ajoutCave = (Button) findViewById(R.id.ajouterCaveViaPref);
        supprVin = (Button) findViewById(R.id.supprVinPref);
        annuler = (Button) findViewById(R.id.annulerPref);

        boutonInvisible();

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{
                "Nom du vin", "Type", "Nb de bouteilles", "Région"};
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        //on veut qu'il y ait que 3 colonnes sur une ligne
        tabNom.setNumColumns(nbColParLigne);
        //tabNom.setBackgroundColor(Color.CYAN);

        // TODO
        // il faudra mettre la liste des vins provenant de la cave à vin de l'utilisateur
        listeVins = new String[]{
                "Bordeaux", "rouge", "8", "rr1",
                "Cadillac", "blanc", "5", "rr1",
                "Riesling", "blanc", "5", "rr1",
                "Whispering Angel", "rosé", "3", "rr1",
                "MonBazillac", "blanc", "10", "rr1",
        };
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
                Intent n = new Intent(AffichagePref.this, AffichageDetailVin.class);
                n.putExtra(NOM_VIN, nomVinSel);
                //en passant des données (nom du vin ici)
                startActivity(n);
            }
        });

        //clique long => suppresion vin => boite de dialogue pour confirmation
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                boutonVisible();
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        posiNom = position - i;
                    }
                }
                changeCouleurLigneVin(posiNom);
                return true;
            }
        });

        // on ajout le vin dans la cave
        ajoutCave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                //ajouter le vin dans la cave
                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();
                boutonInvisible();
                rechangeCouleurLigneVin(posiNom);
            }
        });

        // on supprime le vin de la liste de préférence
        supprVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                AlertDialog.Builder boite;
                boite = new AlertDialog.Builder(AffichagePref.this);
                boite.setTitle("Suppresion ?");
                boite.setIcon(R.drawable.photovin);
                boite.setMessage("Voulez-vous supprimer le " + nomVinSel + " de la liste de souhait ?");
                boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Ce vin va être supprimer !!!",
                                        Toast.LENGTH_SHORT).show();
                                // TODO
                                // réactualiser la liste des vins (recharger la liste mais avant supp le vin)
                                // faire une méthode
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichagePref.this,
                                        android.R.layout.simple_list_item_1, listeVins);
                                tab.setAdapter(adapter);
                                //rechangeCouleurLigneVin(posiNom);
                                boutonInvisible();
                            }
                        }
                );
                boite.setNegativeButton("Conserver", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // on fait rien
                            }
                        }
                );
                boite.show();
            }
        });

        // on annule ce que l'utilisateur vient de faire (cad enlever la sélection du vin)
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonInvisible();
                rechangeCouleurLigneVin(posiNom);
            }
        });
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
        else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichagePref.this, AffichageCave.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend visble les boutons ajout dans cave, supprmier et annuler
    private void boutonVisible() {
        ajoutCave.setVisibility(View.VISIBLE);
        supprVin.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //Méthode qui rend invisble les boutons ajout dans cave, supprmier et annuler
    private void boutonInvisible() {
        ajoutCave.setVisibility(View.INVISIBLE);
        supprVin.setVisibility(View.INVISIBLE);
        annuler.setVisibility(View.INVISIBLE);
        tab.setEnabled(true);
    }

    //on surligne la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.rgb(255, 228, 196));
        }
    }

    //on désurligne la ligne
    private void rechangeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
