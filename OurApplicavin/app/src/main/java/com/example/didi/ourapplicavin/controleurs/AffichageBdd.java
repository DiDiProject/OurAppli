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

import java.util.ArrayList;

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
    private String couleurVinSel = "";
    private String cepageVinSel = "";
    private String regionVinSel = "";
    private int posi; //pour avoir la position dans le tab du vin sélectionné
    public final static String ENDROIT = "endroit"; //pour dire qu'on ait dans la bdd pr recherche
    public final static String VIN_BDD = "vin bdd";
    private int nbColParLigne = 4; //définit le nb de col par ligne pour la liste (pas oublier de modif affichage)
    private String[] listeVins; //bdd dans un tab
    private Bdd bdd = new Bdd(); //bdd qu'on va chercher ds fichier .ser
    private Cave maCave;

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_bdd); //on affiche le layout associé

        maCave = new Cave();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interressent dans le layout
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
        String[] nomsCol = new String[]{"Nom", "Robe", "Cépage", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nomsCol);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne
        //tabNom.setBackgroundColor(Color.CYAN); //change la couleur du tab

        //on va récupérer la bdd enregistrer sur le tel/tablette (.ser)
        bdd = GestionSauvegarde.getBdd();
        affichage(); //on prépare l'affichage de cette bdd à l'écran
        Log.i("AffichageBdd", "on a récupèré la liste de vin pour l'affichage.");
        GestionSauvegarde.enregistrementBdd(bdd);

        //on affiche la bdd à l'écran
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNom
        Log.i("AffichageBdd", "on affiche la bdd !");

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.i("AffichageBdd", "on veut le détail d'un vin !");
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        couleurVinSel = (String) ((TextView) tab.getChildAt(position - i + 1)).getText();
                        cepageVinSel = (String) ((TextView) tab.getChildAt(position - i + 2)).getText();
                        regionVinSel = (String) ((TextView) tab.getChildAt(position - i + 3)).getText();
                    }
                }

                ArrayList<String> ce = new ArrayList<>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, regionVinSel);
                bdd = GestionSauvegarde.getBdd();
                int positionBdd = bdd.rechercheVin(vin);
                if (positionBdd != -1) {
                    //on va à l'activité détailVin
                    Log.i("AffichageBdd", "couleur " + couleurVinSel + " region " + regionVinSel + " posi ds cave " + positionBdd);

                    //on va à l'activité détailVin
                    Intent n = new Intent(AffichageBdd.this, AffichageDetailVin.class);
                    n.putExtra(VIN_BDD, positionBdd); //en passant des données (position du vin dans la bdd ici)
                    startActivity(n);
                } else {
                    Toast.makeText(getApplicationContext(), "ce vin n'a pas été trouvé ds la bdd!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // quand on fait un clic long sur un des vins, on veut soit l'ajouter
        // dans la cave ou dans la liste de souhait
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AffichageBdd", "on veut ajouter un vin à la cave ou la liste de souhait !");
                //on rend visible les boutons ajout dans cave, pref et annuler
                //et on rend le tab inactif
                boutonsVisible();
                //on va chercher la position nomVin ainsi que le nom du vin
                posi = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        couleurVinSel = (String) ((TextView) tab.getChildAt(position - i + 1)).getText();
                        cepageVinSel = (String) ((TextView) tab.getChildAt(position - i + 2)).getText();
                        regionVinSel = (String) ((TextView) tab.getChildAt(position - i + 3)).getText();
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
                Log.i("AffichageBdd", "on veut ajouter le vin à la cave !");

                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posi); // on enlève la couleur du vin sélectionné

                ArrayList<String> ce = new ArrayList<>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, regionVinSel);
                maCave = GestionSauvegarde.getCave();
                bdd = GestionSauvegarde.getBdd();
                int positionBdd = bdd.rechercheVin(vin);
                Log.i("AffichageBdd", "couleur " + couleurVinSel + " region " + regionVinSel + " posi ds bdd " + positionBdd);

                Intent n = new Intent(AffichageBdd.this, AffichageAjoutVinCave.class);
                n.putExtra(VIN_BDD, positionBdd);
                n.putExtra(ENDROIT, 2);
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);

            }
        });

        // on ajoute le vin dans la liste de préférence
        ajoutPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AffichageBdd", "on veut ajouter le vin à la liste de souhait !");
                // on récupère la liste de souhait et la bdd
                ListePref pref = GestionSauvegarde.getPref();
                bdd = GestionSauvegarde.getBdd();
                // on va chercher le vin
                ArrayList<String> ce = new ArrayList<>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, regionVinSel);
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
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //faire une recherche dans la bdd
        else if (id == R.id.rechercheBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer une recherche dans la base de données !",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageRechercheVin.class);
            //on dit qu'on ait dans la base de données pour la recherche
            n.putExtra(ENDROIT, 2);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //ajouter un vin dans la bdd
        else if (id == R.id.ajoutVinBdd) {
            Toast.makeText(AffichageBdd.this, "Vous aller effectuer un ajout de vin dans la base de données",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageBdd.this, AffichageAjoutVinBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    //Méthode pour enregistrer la bdd dans un tableau pour après l'afficher
    public void affichage() {
        //on initialise le tableau avec le nb de case approprié (nb de vins * nb de col)
        listeVins = new String[bdd.getBdd().getNombreVins() * nbColParLigne];
        for (int i = 0; i < bdd.getBdd().getNombreVins(); i++) {
            //pour chaque vin, on affiche le nom (sur la 1ère col), la couleur (la 2ème col),
            //le cépage (la 3ème col) et la région (sur la 4ème)
            Vin vin = bdd.getBdd().getListeVins().get(i);
            listeVins[    i * nbColParLigne] = vin.getNom();
            listeVins[1 + i * nbColParLigne] = vin.getCouleur();
            listeVins[2 + i * nbColParLigne] = vin.getCepage().get(0);
            listeVins[3 + i * nbColParLigne] = vin.getRegion();
        }
    }
}
