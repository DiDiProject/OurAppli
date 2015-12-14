package com.example.didi.ourapplicavin.controleurs;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.ListePref;
import com.example.didi.ourapplicavin.modeles.Vin;

//Classe qui permet d'afficher la liste de préférence de l'utilisateur
public class AffichagePref extends AppCompatActivity {
    //Attributs associés au layout
    private GridView tab = null; //tab pour afficher la liste des vins de la bdd
    private GridView tabNom = null; //tab pour afficher le nom des colonnes
    private Button ajoutCave = null; //bouton pour ajout un vin dans la cave
    private Button supprVin = null; //bouton pour supprimer un vin de la liste de souhait
    private Button annuler = null; //annuler le vin sélectionné
    //Attributs pour cette classe
    public final static String ENDROIT = "endroit"; // TODO pour dire qu'on ait dans liste de souhait
    public final static String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private int nbColParLigne = 4; // TODO définit le nb de col par ligne pour la liste
    private int posiNom = 0; //pour avoir la position dans le tab du vin sélectionné
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    private String[] listeVins; //liste de souhait ds un tab
    private ListePref pref = new ListePref(); // liste de souhait

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_pref); //on affiche le layout associé
        //on va cherche tous les élements qui nous interressent dans le layout
        tabNom = (GridView) findViewById(R.id.tabNomColPref);
        tab = (GridView) findViewById(R.id.tabResultatVinPref);
        ajoutCave = (Button) findViewById(R.id.ajouterCaveViaPref);
        supprVin = (Button) findViewById(R.id.supprVinPref);
        annuler = (Button) findViewById(R.id.annulerPref);
        // on rend les boutons inutiles au départ invisible ainsi que le tab actif
        boutonsInvisible();
        tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom du vin", "Type", "Cépage", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne

        //on récupère la liste de souhait enregistrer
        pref = GestionSauvegarde.getPref();
        affichage(); //on préparer l'affichage de la cave à l'écran
        Log.i("AffichagePref", "on récupère la liste de vin pour l'affichage");

        // on va mettre ce tab de la liste des vins dans le tab associé
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNom
        Log.i("AffichagePref", "on affiche la liste de souhait de l'utilisateur !");

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.i("AffichagePref", "on veut le détail d'un vin !");
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                    }
                }
                //on va à l'activité détailVin
                Intent n = new Intent(AffichagePref.this, AffichageDetailVin.class);
                n.putExtra(NOM_VIN, nomVinSel);
                //en passant des données (nom du vin ici)
                // TODO
                // passer le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                startActivity(n);
            }
        });

        //quand on fait un clic long sur un des vins, on veut supprimer ce vin ou  l'ajouter à la cave
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AffichagePref", "on veut ajouter un vin à la cave ou supprimer ce vin !");
                //on rend visible les boutons ajout dans cave, supprimer et annuler
                //et on rend le tab inactif
                boutonsVisible();
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                //ainsi que sa position dans le tab
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        posiNom = position - i;
                    }
                }
                changeCouleurLigneVin(posiNom); //on surligne le vin sélectionné
                return true;
            }
        });

        // on ajout le vin dans la cave
        ajoutCave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ajouter le vin dans la cave
                // on récupère la cave
                /*Cave maCave = GestionSauvegarde.getCave();
                // TODO
                // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                // on récupère le vin à ajouter
                Vin vin = pref.rechercheVinParNom(nomVinSel);
                maCave.ajoutVin(vin, 1); //on ajoute ce vin à la cave (par défaut 1 bouteille)
                GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave*/

                /*Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();*/
                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posiNom); // on enlève la couleur du vin sélectionné
                Log.i("AffichagePref", "on ajoute le vin : " + nomVinSel + " : à la cave !");
                Intent n = new Intent(AffichagePref.this, AffichageAjoutVinCave.class);
                n.putExtra(NOM_VIN, nomVinSel);
                n.putExtra(ENDROIT, 3);
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });

        // on supprime le vin de la liste de préférence
        supprVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on affiche une boite de dialogue pour confirmation de la suppression
                AlertDialog.Builder boite;
                boite = new AlertDialog.Builder(AffichagePref.this);
                boite.setTitle("Suppresion ?");
                boite.setIcon(R.drawable.photovin);
                boite.setMessage("Voulez-vous supprimer le " + nomVinSel + " de la liste de souhait ?");
                boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // on va chercher la liste de souhait
                                pref = GestionSauvegarde.getPref();
                                // TODO
                                // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                                // on récupère le vin à supprimer
                                Vin vin = pref.rechercheVinParNom(nomVinSel);
                                pref.supprVin(vin); // on supprime le vin de la liste de souhait
                                affichage(); //on réactualise la cave pour l'affichage
                                GestionSauvegarde.enregistrementPref(pref); //on sauvegarde la liste de souhait
                                //on affichage l'actulisation de la liste de souhait
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichagePref.this,
                                        android.R.layout.simple_list_item_1, listeVins);
                                tab.setAdapter(adapter);
                                //rechangeCouleurLigneVin(posiNom); // on enlève la couleur du vin sélectionné
                                boutonsInvisible(); // on remet invisible les boutons
                                Toast.makeText(getApplicationContext(), "Ce vin va être supprimer !!!",
                                        Toast.LENGTH_SHORT).show();
                                Log.i("AfichagePref", "suppression Vin : " + nomVinSel);
                            }
                        }
                );
                boite.setNegativeButton("Conserver", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // on fait rien, revient état précédent avec les boutons
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
                boutonsInvisible(); // on remet invisible les boutons
                rechangeCouleurLigneVin(posiNom); // on enlève la couleur du vin sélectionné
            }
        });
    }

    //Méthode qui permet de mettre un menu à l'écran
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
            n.addCategory( Intent.CATEGORY_HOME );
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichagePref.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend visble les boutons ajout dans cave, supprmier et annuler
    private void boutonsVisible() {
        ajoutCave.setVisibility(View.VISIBLE);
        supprVin.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //Méthode qui rend invisble les boutons ajout dans cave, supprmier et annuler
    private void boutonsInvisible() {
        ajoutCave.setVisibility(View.INVISIBLE);
        supprVin.setVisibility(View.INVISIBLE);
        annuler.setVisibility(View.INVISIBLE);
        tab.setEnabled(true);
    }

    //Méthode pour surligner la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.rgb(255, 228, 196)); //orange clair
        }
    }

    //Méthode pour désurligner la ligne
    private void rechangeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    //Méthode pour enregistrer la liste de souhait dans un tableau pour après l'afficher
    public void affichage(){
        //on initialise le tableau avec le nb de case approprié (nb de vins * nb de col)
        listeVins = new String[pref.getPref().getNombreVins()*nbColParLigne];
        for(int i=0; i<pref.getPref().getNombreVins(); i++){
            //pour chaque vin, on affiche le nom (sur la 1ère col), la couleur (la 2ème col),
            //le cépage (la 3ème col) et la région (sur la 4ème)
            Vin vin = pref.getPref().getListeVins().get(i);
            listeVins[0+i*nbColParLigne] = vin.getNom();
            listeVins[1+i*nbColParLigne] = vin.getCouleur();
            listeVins[2+i*nbColParLigne] = vin.getCepage();
            listeVins[3+i*nbColParLigne] = vin.getRegion();
        }
    }
}
