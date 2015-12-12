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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.Vin;

//Classe qui affiche la liste des vins de la cave "virtuelle" de l'utilisateur
//elle est appelée via le menu principal
public class AffichageCave extends AppCompatActivity {
    //Attributs associés au layout
    private Button augmenter = null; //bouton pour augmenter le nb de bouteille
    private Button diminuer = null; //bouton pour diminuer le nb de bouteille
    private EditText nb = null; //pour afficher le nb de bouteille en temps réel
    private TextView texte = null; //texte pour dire qu'on parle de bouteille
    private Button ok = null; //pour enregistrer ce nouveau nb de bouteille
    private GridView tab = null; //tab pour afficher la liste des vins de la cave
    private GridView tabNom = null; //tab pour afficher le nom des colonnes
    //Attributs pour cette classe
    private int nbBouteilles; //pour avoir le nb de bouteille (en entier)
    private String[] listeVins;
    private int nbBouteilleActualiser; //pour aovir le nb de bouteille actualisé
    private int positionTabNb = 0; //pour avoir la position dans le tab (liste vins) du nb de bouteille
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    public final static String nomCave = "cave"; // TODO pour dire qu'on ait dans la cave pr recherche
    final String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private int nbColParLigne = 4; // TODO définit le nb de col par ligne pour la liste
    private boolean ajoutOuPas = false;
    private Cave maCave = new Cave();

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cave); //on affiche le layout associé

        //on va cherche tous les élements qui nous interresse dans le layout
        tabNom = (GridView) findViewById(R.id.tabNomColCave);
        tab = (GridView) findViewById(R.id.tabResultatVinCave);
        augmenter = (Button) findViewById(R.id.augmenter);
        diminuer = (Button) findViewById(R.id.diminuer);
        nb = (EditText) findViewById(R.id.nbBouteille);
        texte = (TextView) findViewById(R.id.textView3);
        ok = (Button) findViewById(R.id.ok);
        //on rend les boutons inutiles au départ invisible ainsi que le tab actif

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            if (ajoutOuPas) {
                boutonsVisible();
                tabNom.setEnabled(true);
            } else {
                boutonsInvisible();
                tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes
            }

        } else {
            boutonsInvisible();
            tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        }

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom du vin", "Type", "Nb de bouteilles", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne);  //définit le nombre de colonne par ligne

        maCave = GestionSauvegarde.getCave();
        if (maCave == null) {
            init();
        }

        affichage();
        Log.i("AffichageCave", "on récupère la liste de vin pour l'affichage");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNom

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                //[nomVinSel = (String) ((TextView) v).getText(); //directement mais que pour le vin cliqué]
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                    }
                }
                //Affichage court
                Toast.makeText(getApplicationContext(), "La description de " + nomVinSel + " va s'afficher !",
                        Toast.LENGTH_SHORT).show();
                //on va à l'activité détailVin
                Intent n = new Intent(AffichageCave.this, AffichageDetailVin.class);
                //en passant des données (nom du vin ici)
                n.putExtra(NOM_VIN, nomVinSel);
                startActivity(n); // on l'activité détailVin
            }
        });

        // quand on fait un clic long sur un des vins,
        // on veut augmenter ou diminuer son nb de bouteille
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //on rend visible les boutons +, - et ok et rend le tab inactif
                boutonsVisible();
                ajoutOuPas = true;
                //on va chercher la position nomVin ainsi que son nom et le nb bouteille
                String nbBouteilleavant = "0"; //si on arrive pas à récupérer le nb bouteille
                positionTabNb = 2; //si on arrive pas à récupérer le nb bouteille
                // TODO
                // à changer si plus de 4 col
                if (position % nbColParLigne == 0) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position + 2)).getText();
                    positionTabNb = position + 2;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position)).getText();
                } else if (position % nbColParLigne == 1) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position + 1)).getText();
                    positionTabNb = position + 1;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 1)).getText();
                } else if (position % nbColParLigne == 2) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position)).getText();
                    positionTabNb = position;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 2)).getText();
                } else {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position - 1)).getText();
                    positionTabNb = position - 1;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 3)).getText();
                }
                nb.setText(nbBouteilleavant); //met le nombre de bouteille du vin en affichage
                //on surligne la ligne du vin sélectionné
                changeCouleurLigneVin(positionTabNb - 2);
                return true;
            }
        });

        //pour augmenter le nombre de bouteille (juste un clique sur +)
        augmenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbavant = Integer.parseInt(nb.getText().toString()); //on récupère le nb de bouteille
                nbBouteilleActualiser = nbavant + 1; //on rajouter une bouteille
                nb.setText(Integer.toString(nbBouteilleActualiser)); //affichage du nouveau nb
            }
        });

        //pour diminuer le nombre de bouteille (juste un clique sur -)
        diminuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbavant = Integer.parseInt(nb.getText().toString()); //on récupère le nb de bouteille
                //si on arrive à 1 bouteille ou à 0
                if (nbavant <= 1) {
                    //affiche une boite de dialogue pour confirmation suppression vin ou conserver ce vin
                    AlertDialog.Builder boite;
                    boite = new AlertDialog.Builder(AffichageCave.this);
                    boite.setTitle("Suppresion ?");
                    boite.setIcon(R.drawable.photovin); //image
                    boite.setMessage("Voulez-vous supprimer le " + nomVinSel + " ou le conserver dans votre cave avec 0 bouteille ?");
                    boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //on remet les boutons invisibles et remet le tab actif
                                    boutonsInvisible();
                                    rechangeCouleurLigneVin(positionTabNb - 2); //on enlève la couleur du vin sélectionné
                                    //réactualiser la liste des vins (recharger la liste mais avant supp le vin)

                                    Log.i("AfichageCave", "suppression Vin : " + nomVinSel);
                                    Vin vin = maCave.rechercheVinParNom(nomVinSel);
                                    maCave.supprVin(vin);
                                    affichage();
                                    GestionSauvegarde.enregistrementCave(maCave);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                                            android.R.layout.simple_list_item_1, listeVins);
                                    tab.setAdapter(adapter);
                                }
                            }
                    );
                    boite.setNegativeButton("Conserver", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // on revient dans l'état précédent
                                    nb.setText("0"); //on met à 0 le nb de bouteille
                                    boutonsInvisible(); //boutons invisible  + tab actif
                                    rechangeCouleurLigneVin(positionTabNb - 2); //enlève couleur du vin sélectionné
                                    // TODO
                                    // reactuliser dans la cave
                                    // faire plutot : modifier le nb bouteille dans la liste des vins de la cave
                                    // puis recharger la liste
                                    //mettre méthode supprVin

                                    Vin vin = maCave.rechercheVinParNom(nomVinSel);
                                    maCave.setNbBouteilleVin(vin, 0);
                                    affichage();
                                    GestionSauvegarde.enregistrementCave(maCave);

                                    //listeVins[positionTabNb] = "0"; // à supprimer quand mofif liste des vins
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                                            android.R.layout.simple_list_item_1, listeVins);
                                    tab.setAdapter(adapter);
                                }
                            }
                    );
                    boite.show();
                    return;
                }
                nbBouteilleActualiser = nbavant - 1; //on enlève une bouteille (si nbBouteilleAvant>1
                nb.setText(Integer.toString(nbBouteilleActualiser)); //affichage du nouveau nombre
            }
        });

        //quand l'utilisateur à fini de changer le nb de bouteille, on enregistre ce nouveau nb
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on récupère le nombre de bouteilles actualisé
                nbBouteilles = Integer.parseInt(nb.getText().toString());
                //on remet les boutons invisibles et le tab actif
                boutonsInvisible();
                ajoutOuPas = false;
                rechangeCouleurLigneVin(positionTabNb - 2); //enlève couleur vin sélectionné
                // remettre à jour la liste de vin (nb bouteille à changer)
                // faire plutot : modifier le nb bouteille dans la liste des vins de la cave
                // puis recharger la liste
                Log.i("AffichageCave", "actualise le nb de bouteille de " + nomVinSel);
                Vin vin = maCave.rechercheVinParNom(nomVinSel);
                maCave.setNbBouteilleVin(vin, nbBouteilles);
                affichage();
                GestionSauvegarde.enregistrementCave(maCave); //on enrgistre la nouvelle liste de vin dans la cave (fichier .ser)

                //listeVins[positionTabNb] = Integer.toString(nbBouteilles); // à supp quand on a mofif la liste
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                        android.R.layout.simple_list_item_1, listeVins);
                tab.setAdapter(adapter);
            }
        });

    }

    //Méthode qui permet de mettre un menu à l'écran
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
            // on enlève l'activité précédente (celle de voir sa cave)
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (rechercher des vins)
        // on va dans l'activité recherche vin
        else if (id == R.id.rechercheCave) {
            Toast.makeText(AffichageCave.this, "Vous aller effectuer une recherche dans votre cave !",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageCave.this, AffichageRechercheVin.class);
            // TODO
            //dire qu'on ait dans la cave pour la recherche
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Méthode qui rend invisble les boutons + et -
    //et le tableau de la liste de vins devient actif
    private void boutonsInvisible() {
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
    private void boutonsVisible() {
        augmenter.setVisibility(View.VISIBLE);
        diminuer.setVisibility(View.VISIBLE);
        nb.setVisibility(View.VISIBLE);
        ok.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //Méthode pour surligner la ligne (vin sélectionné)
    //position doit être celui du nom
    private void changeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.rgb(176, 222, 253)); //bleu clair
        }
    }

    //Méthode qui désurligne la ligne
    //position doit être celui du nom
    private void rechangeCouleurLigneVin(int position) {
        for (int i = 0; i < nbColParLigne; i++) {
            tab.getChildAt(position + i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void init() {
        maCave = new Cave();
        affichage();
        Log.i("AffichageCave", "on a initialiser la liste de vin de la cave et on va enegistrer cette liste dans un fichier .ser");
        GestionSauvegarde.enregistrementCave(maCave);
    }

    public void affichage() {
        listeVins = new String[maCave.getVinsCave().getNombreVins() * 4];
        for (int i = 0; i < maCave.getVinsCave().getNombreVins(); i++) {
            Vin vin = maCave.getVinsCave().getListeVins().get(i);
            listeVins[0 + i * 4] = vin.getNom();
            listeVins[1 + i * 4] = vin.getCouleur();
            listeVins[2 + i * 4] = "" + maCave.getNbBouteilleVin(vin);
            listeVins[3 + i * 4] = vin.getRegion();
        }
    }

}