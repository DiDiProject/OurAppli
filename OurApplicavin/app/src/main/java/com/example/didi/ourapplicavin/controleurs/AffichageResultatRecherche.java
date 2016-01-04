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

//Classe qui affiche la liste des vins de la recherche
public class AffichageResultatRecherche extends AppCompatActivity {
    //Attributs associés au layout
    private GridView tabNomCol = null; //tab pour afficher le nom des colonnes
    private GridView tabResultatVin = null; //tab pour afficher la liste des vins de la bdd
    private Button ajoutCave = null; //bouton pour ajout un vin dans la cave
    private Button ajoutSouhait = null; //dans la liste de souhait
    private Button annuler = null; //annuler le vin sélectionné
    private TextView texte = null; //texte ajout
    private TextView texteOu = null; //texte entre les deux boutons
    //Attributs pour cette classe
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    private int posi; //pour avoir la position dans le tab du vin sélectionné
    final String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private int nbColParLigne = 4; // TODO définit le nb de col par ligne pour la liste
    private String[] listeVins = new String[4]; // TODO liste des vin de la recherche à récupérer
    public final static String ENDROIT = "endroit"; // TODO pour dire qu'on ait dans la bdd pr recherche
    private int endroit = 0;

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_resultat_recherche); //on affiche le layout associé
//        TextView nomVin = (TextView)findViewById(R.id.name);
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            nomVin.setText(intent.getStringExtra(NOM_VIN));
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interresse dans le layout
        tabNomCol = (GridView) findViewById(R.id.tabNomColResultatRecherche);
        tabResultatVin = (GridView) findViewById(R.id.tabResultatVin);
        ajoutCave = (Button) findViewById(R.id.ajoutCaveResultat);
        ajoutSouhait = (Button) findViewById(R.id.ajoutSouhaitResultat);
        annuler = (Button) findViewById(R.id.annulerRecherche);
        texte = (TextView) findViewById(R.id.textView8);
        texteOu = (TextView) findViewById(R.id.textOuResultat);
        // on rend les boutons inutiles au départ invisible ainsi que le tab actif
        boutonsInvisible();
        tabNomCol.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        // TODO
        //il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom du vin", "Type", "Cépage", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNomCol.setAdapter(adapterTitle);
        tabNomCol.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne

        // TODO
        //il faudra mettre la liste des vins provenant de la recherche

        Intent i = getIntent();
        endroit = i.getIntExtra(AffichageBdd.ENDROIT, 2);
        nomVinSel = i.getStringExtra(AffichageBdd.NOM_VIN);
        if(endroit != 2) {
            endroit = i.getIntExtra(AffichageCave.ENDROIT, 1);
            nomVinSel = i.getStringExtra(AffichageCave.NOM_VIN);
            if(endroit != 1){
                endroit = i.getIntExtra(AffichagePref.ENDROIT, 3);
                nomVinSel = i.getStringExtra(AffichagePref.NOM_VIN);
            }
        } else {
            endroit = 2;
        }
        Log.i("AffichageResultat", nomVinSel + " vin recherché !!!");
        int type_recherche = i.getIntExtra(AffichageRechercheVin.TYPE_RECHERCHE, 0);
        if (type_recherche == 0) {
            rechercheParNom();
        } else {
            rechercheParCritere();
        }

        /*listeVins = new String[]{
                "Bordeaux", "rouge", "8", "rr1",
                "Cadillac", "blanc", "1", "rr1",
                "Riesling", "blanc", "5", "rr1",
                "Whispering Angel", "rosé", "3", "rr1",
                "MonBazillac", "blanc", "10", "rr1",
                "Champagne dom pérignon", "blanc", "10", "rr1",
                "Gewurztraminer d'Alsace", "blanc", "1", "rr1",
                "Monbazillac", "balnc", "4", "rr1"
        };*/
        // on va mettre ce tab de la liste des vins dans le tab associé
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tabResultatVin.setAdapter(adapter);
        tabResultatVin.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNomCol

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tabResultatVin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i)).getText();
                    }
                }
                //Affichage court
                Toast.makeText(getApplicationContext(), "La description de " + nomVinSel + " va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                //on va à l'activité détailVin
                Intent n = new Intent(AffichageResultatRecherche.this, AffichageDetailVin.class);
                n.putExtra(NOM_VIN, nomVinSel);
                //en passant des données (nom du vin ici)
                startActivity(n);
            }
        });

        // quand on fait un clic long sur un des vins, on veut soit l'ajouter
        // dans la cave ou dans la liste de souhait
        tabResultatVin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                //on rend visible les boutons ajout dans cave, pref et annuler
                //et on rend le tab inactif
                boutonsVisible();
                //on va chercher la position nomVin ainsi que sa position dans le tab
                posi = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i)).getText();
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
                // TODO

                boutonsInvisible();
                rechangeCouleurLigneVin(posi);
                Intent n = new Intent(AffichageResultatRecherche.this, AffichageAjoutVinCave.class);
                n.putExtra(NOM_VIN, nomVinSel);
                n.putExtra(ENDROIT,2);
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });

        // on ajoute le vin dans la liste de préférence
        ajoutSouhait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Log.i("AffichageBdd", "on veut ajouter le vin à la liste de souhait !");
                // on récupère la liste de souhait et la bdd
                ListePref pref = GestionSauvegarde.getPref();
                Bdd bdd = GestionSauvegarde.getBdd();
                // on va chercher le vin
                // TODO faire la recherche avec tous les critères pas juste le nom
                Vin vin = bdd.rechercheVinParNom(nomVinSel);
                pref.ajoutVin(vin); // on ajoute le vin à la pref
                GestionSauvegarde.enregistrementPref(pref); // on sauvegarde la liste de souhait sur  le tél
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

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_rechercher_vin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_resultat_recherche, menu);
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
        //aller au menu principal
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageMenuPrincipal.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourBdd) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //retour à la recherche
        else if (id == R.id.retourRecherche) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageRechercheVin.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend invisible des boutons (ajout)
    private void boutonsVisible() {
        ajoutCave.setVisibility(View.VISIBLE);
        ajoutSouhait.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        texteOu.setVisibility(View.VISIBLE);
        tabResultatVin.setEnabled(false);
    }

    //Méthode qui rend visible des boutons (pour effectuer l'ajout)
    private void boutonsInvisible() {
        ajoutCave.setVisibility(View.INVISIBLE);
        ajoutSouhait.setVisibility(View.INVISIBLE);
        annuler.setVisibility(View.INVISIBLE);
        texte.setVisibility(View.INVISIBLE);
        texteOu.setVisibility(View.INVISIBLE);
        tabResultatVin.setEnabled(true);
    }

    //Méthode qui surligne la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tabResultatVin.getChildAt(position + i).setBackgroundColor(Color.rgb(253, 220, 216)); //rouge/rose clair
        }
    }

    //Méthode qui désurligne la ligne
    private void rechangeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tabResultatVin.getChildAt(position + i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void rechercheParNom(){
        listeVins = new String[nbColParLigne];
        Vin vin = new Vin();
        if(endroit == 1){
            Cave cave = GestionSauvegarde.getCave();
            vin = cave.rechercheVinParNom(nomVinSel);
            Log.i("AffichageResultat", "recherche ds la cave");
        } else if(endroit == 2){
            Bdd bdd = GestionSauvegarde.getBdd();
            vin = bdd.rechercheVinParNom(nomVinSel);
            Log.i("AffichageResultat", "recherche ds la bdd");
        } else {
            ListePref pref = GestionSauvegarde.getPref();
            vin = pref.rechercheVinParNom(nomVinSel);
            Log.i("AffichageResultat", "recherche ds la pref");
        }
        Log.i("AffichageResultat", vin.getNom() + " vin recherché");
        listeVins[0] = vin.getNom();
        listeVins[1] = vin.getCouleur();
        listeVins[2] = vin.getCepage().get(0);
        listeVins[3] = vin.getRegion();
    }

    public void rechercheParCritere(){
        // TODO
    }
}