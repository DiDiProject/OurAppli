package com.example.didi.ourapplicavin.controleurs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.ListePref;
import com.example.didi.ourapplicavin.modeles.Vin;

//Classe qui affiche la base de données des vins
public class AffichageBdd extends AppCompatActivity {
    //Attributs associés au layout
    private GridView tab = null; //tab pour afficher la liste des vins de la bdd
    private GridView tabNom = null; //tab pour afficher le nom des colonnes
    private Button ajoutCave = null; //bouton pour ajout un vin dans la cave
    private Button ajoutPref = null; //dans la liste de souhait
    private Button annuler = null; //annuler le vin sélectionné
    private TextView texte = null; //texte ajout
    private TextView texteOu = null; //texte entre les deux boutons
    //Attributs pour cette classe
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    private int posi; //pour avoir la position dans le tab du vin sélectionné
    public final static String cave = "bdd"; // TODO pour dire qu'on ait dans la bdd pr recherche
    final String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private int nbColParLigne = 4; // TODO définit le nb de col par ligne pour la liste
    private String[] listeVins;
    private Bdd bdd = new Bdd();

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_bdd); //on affiche le layout associé

        //on va cherche tous les élements qui nous interresse dans le layout
        tabNom = (GridView) findViewById(R.id.tabNomColBdd);
        tab = (GridView) findViewById(R.id.tabResultatVinBdd);
        ajoutCave = (Button) findViewById(R.id.ajouterCave);
        ajoutPref = (Button) findViewById(R.id.ajouterPref);
        annuler = (Button) findViewById(R.id.annulerBdd);
        texte = (TextView) findViewById(R.id.textView4);
        texteOu = (TextView) findViewById(R.id.textOu);
        // on rend les boutons inutiles au départ invisible ainsi que le tab actif
        boutonsInvisible();
        tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        // TODO
        // il faudra définir les noms des colonnes
        String[] nomsCol = new String[]{"Nom", "Couleur", "Cépage", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nomsCol);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne
        //tabNom.setBackgroundColor(Color.CYAN); //change la couleur du tab

        bdd = GestionSauvegarde.getBdd();
        if (bdd == null) {
            init();
        }
        affichage();
        Log.i("AffichageBdd", "on récupère la liste de vin pour l'affichage");

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
                //Affichage court
                Toast.makeText(getApplicationContext(), "La description de " + nomVinSel + " va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                //on va à l'activité détailVin
                Intent n = new Intent(AffichageBdd.this, AffichageDetailVin.class);
                n.putExtra(NOM_VIN, nomVinSel);
                //en passant des données (nom du vin ici)
                startActivity(n);
            }
        });

        // quand on fait un clic long sur un des vins, on veut soit l'ajouter
        // dans la cave ou dans la liste de souhait
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //on rend visible les boutons ajout dans cave, pref et annuler
                //et on rend le tab inactif
                boutonsVisible();
                //on va chercher la position nomVin ainsi que le nom du vin
                posi = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        posi = position - i;
                    }
                }
                changeCouleurLigneVin(posi); //on surligne la ligne du vin sélectionné
                return true;
            }
        });

        // on ajout le vin dans la cave
        ajoutCave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cave maCave = GestionSauvegarde.getCave();
                Vin vin = bdd.rechercheVinParNom(nomVinSel);
                maCave.ajoutVin(vin, 1);
                GestionSauvegarde.enregistrementCave(maCave);

                //Affichage court
                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();
                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posi); // on enlève la couleur du vin sélectionné
            }
        });

        // on ajoute le vin dans la liste de préférence
        ajoutPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListePref pref = GestionSauvegarde.getPref();
                Vin vin = bdd.rechercheVinParNom(nomVinSel);
                pref.ajoutVin(vin);
                GestionSauvegarde.enregistrementPref(pref);
                //Affichage court
                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la liste de souhait !",
                        Toast.LENGTH_SHORT).show();
                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posi); // on enlève la couleur du vin sélectionné
            }
        });

        // on annule ce que l'utilisateur vient de faire (cad enlever la sélection du vin)
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posi); // on enlève la couleur du vin sélectionné
            }
        });

    }

    //Méthode qui permet de mettre un menu à l'écran
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
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichageBdd.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        //faire une recherche dans la bdd
        else if (id == R.id.rechercheBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer une recherche dans la base de données !",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageRechercheVin.class);
            // TODO
            //dire qu'on ait dans la base de données pour la recherche
            startActivity(n);
            return true;
        }
        //ajouter un vin dans la bdd
        else if (id == R.id.ajoutVinBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer un ajout de vin dans la base de données",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageAjoutVinBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend invisible des boutons (ajout)
    private void boutonsInvisible() {
        ajoutCave.setVisibility(View.INVISIBLE);
        ajoutPref.setVisibility(View.INVISIBLE);
        annuler.setVisibility(View.INVISIBLE);
        texte.setVisibility(View.INVISIBLE);
        texteOu.setVisibility(View.INVISIBLE);
        tab.setEnabled(true);
    }

    //Méthode qui rend visible des boutons (pour effectuer l'ajout)
    private void boutonsVisible() {
        ajoutCave.setVisibility(View.VISIBLE);
        ajoutPref.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        texteOu.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //Méthode qui surligne la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.rgb(190, 253, 185)); //vert clair
        }
    }

    //Méthode qui désurligne la ligne
    private void rechangeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void init() {
        bdd = new Bdd();
        bdd.ajoutVin(new Vin("Bordeaux", "rouge", "Merlot", "Aquitaine"));
        bdd.ajoutVin(new Vin("Bordeaux", "blanc", "Saivignon", "Aquitaine"));
        bdd.ajoutVin(new Vin("Cadillac", "blanc", "Muscadelle", "Aquitaine"));
        bdd.ajoutVin(new Vin("Riesling", "blanc", "Semillon", "Alsace"));
        bdd.ajoutVin(new Vin("Whispering Angel", "rosé", "Grenache", "Provence"));
        Log.i("AffichageBdd", "on a initialiser la liste de vin de la bdd et on va enegistrer cette liste dans un fichier .ser");
        affichage();
        GestionSauvegarde.enregistrementBdd(bdd);

    }

    public void affichage() {
        listeVins = new String[bdd.getBdd().getNombreVins() * 4];
        for (int i = 0; i < bdd.getBdd().getNombreVins(); i++) {
            Vin vin = bdd.getBdd().getListeVins().get(i);
            listeVins[0 + i * 4] = vin.getNom();
            listeVins[1 + i * 4] = vin.getCouleur();
            listeVins[2 + i * 4] = vin.getCepage();
            listeVins[3 + i * 4] = vin.getRegion();
        }
    }


    public void setBdd(Vin vin) {
        bdd.ajoutVin(vin);
    }
}
