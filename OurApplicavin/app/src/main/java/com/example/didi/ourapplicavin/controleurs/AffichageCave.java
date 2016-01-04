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
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.Vin;

import java.util.ArrayList;

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
    private int nbBouteilleActualiser; //pour aovir le nb de bouteille actualisé
    private int positionTabNb = 0; //pour avoir la position dans le tab (liste vins) du nb de bouteille
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    private String couleurVinSel = "";
    private String nbVinSel = "";
    private String cepageVinSel = "";
    private String millesimeVinSel = "";
    public final static String ENDROIT = "endroit"; // TODO pour dire qu'on ait dans la cave pr recherche
    final static String NOM_VIN = "vin cave"; //pour passer le nom du vin à une autre activité
    final static String VIN_BDD = "vin bdd";
    private int nbColParLigne = 5; // TODO définit le nb de col par ligne pour la liste (pas oublier de modif affichage)
    private boolean ajoutOuPas = false; //savoir si on est ds ajout nb d'un vin
    private String[] listeVins; //cave ds un tab
    private Cave maCave = new Cave(); //cave qu'on va ensuite récupérer ds fichier .ser
    private Bdd bdd = new Bdd();

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cave); //on affiche le layout associé


    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interressent dans le layout
        tabNom = (GridView) findViewById(R.id.tabNomColCave);
        tab = (GridView) findViewById(R.id.tabResultatVinCave);
        augmenter = (Button) findViewById(R.id.augmenter);
        diminuer = (Button) findViewById(R.id.diminuer);
        nb = (EditText) findViewById(R.id.nbBouteille);
        texte = (TextView) findViewById(R.id.textView3);
        ok = (Button) findViewById(R.id.ok);
        //on rend les boutons inutiles au départ invisible ainsi que le tab actif

        //pour l'horientation du tel (mode paysage ou portait) ms ne marche pas
        /*if (savedInstanceState != null) {
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
        }*/

        boutonsInvisible();
        tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes
        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom du vin", "Robe", "Nb", "Cépage", "Millésime"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne);  //définit le nombre de colonne par ligne

        //on récupère la cave (enregistrer dans fichier .ser sur le tel
        maCave = GestionSauvegarde.getCave();
        /*if (maCave == null) {
            init(); //si n'a pas de cave on l'initialise
        }*/
        //on préparer l'affichage de la cave à l'écran
        affichage();
        Log.i("AffichageCave", "on récupère la cave pour l'affichage");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listeVins);
        tab.setAdapter(adapter);
        tab.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne comme tabNom
        Log.i("AffichageCave", "on affiche la cave de l'utilisateur !");

        //quand on fait un clic court sur un des vins (n'importe quelle colonne)
        //on va afficher le détail de ce vin
        tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.i("AffichageCave", "on veut le détail d'un vin !");
                //selon la colonne où l'utilisateur clique, il faudra récupérer le nom du vin
                //[nomVinSel = (String) ((TextView) v).getText(); //directement mais que pour le vin cliqué]
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tab.getChildAt(position - i)).getText();
                        couleurVinSel = (String) ((TextView) tab.getChildAt(position - i + 1)).getText();
                        nbVinSel = (String) ((TextView) tab.getChildAt(position - i + 2)).getText();
                        cepageVinSel = (String) ((TextView) tab.getChildAt(position - i + 3)).getText();
                        millesimeVinSel = (String) ((TextView) tab.getChildAt(position - i + 4)).getText();
                    }
                }
                ArrayList<String> ce = new ArrayList<String>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", Integer.parseInt(nbVinSel), millesimeVinSel);
                maCave = GestionSauvegarde.getCave();
                int positionCave = maCave.rechercheVin(vin);
                vin = maCave.getVin(positionCave);
                //on va à l'activité détailVin
                if (positionCave != -1) {
                    Log.i("AffichageCaveé", "couleur " + couleurVinSel + " méllisime " + millesimeVinSel + " nb " + nbVinSel + " posi ds cave " + positionCave);
                    bdd = GestionSauvegarde.getBdd();
                    int positionBdd = bdd.rechercheVin(vin);
                    if (positionBdd != -1) {
                        Intent n = new Intent(AffichageCave.this, AffichageDetailVin.class);
                        //en passant des données (nom du vin ici)
                        n.putExtra(NOM_VIN, positionCave);
                        n.putExtra(VIN_BDD, positionBdd);
                        // TODO
                        // passer le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                        startActivity(n);
                    } else {
                        Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans la bdd !!!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans votre cave !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // quand on fait un clic long sur un des vins,
        // on veut augmenter ou diminuer son nb de bouteille
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AffichageCave", "on veut ajouter des bouteilles ou en supprimer ou supprimer directement un vin !");
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
                    couleurVinSel = (String) ((TextView) tab.getChildAt(position + 1)).getText();
                    nbVinSel = (String) ((TextView) tab.getChildAt(position + 2)).getText();
                    cepageVinSel = (String) ((TextView) tab.getChildAt(position + 3)).getText();
                    millesimeVinSel = (String) ((TextView) tab.getChildAt(position + 4)).getText();
                } else if (position % nbColParLigne == 1) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position + 1)).getText();
                    positionTabNb = position + 1;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 1)).getText();
                    couleurVinSel = (String) ((TextView) tab.getChildAt(position - 1 + 1)).getText();
                    nbVinSel = (String) ((TextView) tab.getChildAt(position - 1 + 2)).getText();
                    cepageVinSel = (String) ((TextView) tab.getChildAt(position - 1 + 3)).getText();
                    millesimeVinSel = (String) ((TextView) tab.getChildAt(position - 1 + 4)).getText();
                } else if (position % nbColParLigne == 2) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position)).getText();
                    positionTabNb = position;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 2)).getText();
                    couleurVinSel = (String) ((TextView) tab.getChildAt(position - 2 + 1)).getText();
                    nbVinSel = (String) ((TextView) tab.getChildAt(position - 2 + 2)).getText();
                    cepageVinSel = (String) ((TextView) tab.getChildAt(position - 2 + 3)).getText();
                    millesimeVinSel = (String) ((TextView) tab.getChildAt(position - 2 + 4)).getText();
                } else if (position % nbColParLigne == 3) {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position - 1)).getText();
                    positionTabNb = position - 1;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 3)).getText();
                    couleurVinSel = (String) ((TextView) tab.getChildAt(position - 3 + 1)).getText();
                    nbVinSel = (String) ((TextView) tab.getChildAt(position - 3 + 2)).getText();
                    cepageVinSel = (String) ((TextView) tab.getChildAt(position - 3 + 3)).getText();
                    millesimeVinSel = (String) ((TextView) tab.getChildAt(position - 3 + 4)).getText();
                } else {
                    nbBouteilleavant = (String) ((TextView) tab.getChildAt(position - 2)).getText();
                    positionTabNb = position - 2;
                    nomVinSel = (String) ((TextView) tab.getChildAt(position - 4)).getText();
                    couleurVinSel = (String) ((TextView) tab.getChildAt(position - 4 + 1)).getText();
                    nbVinSel = (String) ((TextView) tab.getChildAt(position - 4 + 2)).getText();
                    cepageVinSel = (String) ((TextView) tab.getChildAt(position - 4 + 3)).getText();
                    millesimeVinSel = (String) ((TextView) tab.getChildAt(position - 4 + 4)).getText();
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
                                    // on va chercher la cave qu'on a enregistré
                                    maCave = GestionSauvegarde.getCave();
                                    // TODO
                                    // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                                    // on récupère le vin à supprimer
                                    ArrayList<String> ce = new ArrayList<String>();
                                    ce.add(cepageVinSel);
                                    Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", 0, millesimeVinSel);
                                    int posi = maCave.rechercheVin(vin);
                                    if (posi != -1) {
                                        vin = maCave.getVin(posi);
                                        maCave.supprVin(vin); //on supprime ce vin de la cave
                                        affichage(); //on réactualise la cave pour l'affichage
                                        GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave
                                        //on affichage l'actulisation de la cave
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageCave.this,
                                                android.R.layout.simple_list_item_1, listeVins);
                                        tab.setAdapter(adapter);
                                        Log.i("AfichageCave", "suppression du vin : " + nomVinSel);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans votre cave !!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                    boite.setNegativeButton("Conserver", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // on revient dans l'état précédent
                                    nb.setText("0"); //on met à 0 le nb de bouteille
                                    boutonsInvisible(); //boutons invisible  + tab actif
                                    rechangeCouleurLigneVin(positionTabNb - 2); //enlève couleur du vin sélectionné
                                    // on va chercher la cave qu'on a enregistré
                                    maCave = GestionSauvegarde.getCave();
                                    // TODO
                                    // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                                    // on va chercher la vin pour changer le nb de bouteille => 0
                                    ArrayList<String> ce = new ArrayList<String>();
                                    ce.add(cepageVinSel);
                                    Vin vin = new Vin(nomVinSel, couleurVinSel, ce, millesimeVinSel);
                                    int posi = maCave.rechercheVin(vin);
                                    if (posi != -1) {
                                        vin = maCave.getVin(posi);
                                        maCave.getVin(posi).setNbBouteille(0); // on modifie le nb de bouteille du vin
                                        affichage(); //on réactualise la cave pour l'affichage
                                        GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave
                                        //on affichage l'actulisation de la cave
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageCave.this,
                                                android.R.layout.simple_list_item_1, listeVins);
                                        tab.setAdapter(adapter);
                                        Log.i("AfichageCave", "conservation du vin : " + nomVinSel + ": avec 0 bouteille !");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans votre cave !!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
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
                // on va chercher la cave qu'on a enregistré
                maCave = GestionSauvegarde.getCave();
                // TODO
                // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                // on va chercher la vin pour changer le nb de bouteille
                ArrayList<String> ce = new ArrayList<String>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", 0, millesimeVinSel);
                int posi = maCave.rechercheVin(vin);
                if (posi != -1) {
                    vin = maCave.getVin(posi);
                    maCave.getVin(posi).setNbBouteille(nbBouteilles); //onchage le nb de bouteille du vin ds la cave
                    Log.i("AffichageCave", "on actualise le nb de bouteille de " + nomVinSel);
                    affichage(); //on réactualise la cave pour l'affichage
                    GestionSauvegarde.enregistrementCave(maCave); //on enregistre la nouvelle liste de vin dans la cave (fichier .ser)
                    //on affichage l'actulisation de la cave
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageCave.this,
                            android.R.layout.simple_list_item_1, listeVins);
                    tab.setAdapter(adapter);
                    Log.i("AffichageCave", "on actualise le nb de bouteille de " + nomVinSel);
                } else {
                    Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans votre cave !!!",
                            Toast.LENGTH_SHORT).show();
                }
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
            n.putExtra(ENDROIT, 1);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    //Méthode pour initialiser la cave (donc avec 0 vin)
    public void init() {
        maCave = new Cave();
        affichage();
        Log.i("AffichageCave", "on a initialisé la liste de vin de la cave et on va enegistrer cette liste dans un fichier .ser");
        GestionSauvegarde.enregistrementCave(maCave); //enregistrement de la cave (vide pour l'instant)
    }

    //Méthode pour enregistrer la cave dans un tableau pour après l'afficher
    public void affichage() {
        //on initialise le tableau avec le nb de case approprié (nb de vins * nb de col)
        listeVins = new String[maCave.getVinsCave().getNombreVins() * nbColParLigne];
        for (int i = 0; i < maCave.getVinsCave().getNombreVins(); i++) {
            //pour chaque vin, on affiche le nom (sur la 1ère col), la couleur (la 2ème col),
            //le nb de bouteille (la 3ème col) et la région (sur la 4ème)
            Vin vin = maCave.getVinsCave().getListeVins().get(i);
            listeVins[0 + i * nbColParLigne] = vin.getNom();
            listeVins[1 + i * nbColParLigne] = vin.getCouleur();
            listeVins[2 + i * nbColParLigne] = "" + vin.getNbBouteille();
            listeVins[3 + i * nbColParLigne] = vin.getCepage().get(0);
            listeVins[4 + i * nbColParLigne] = vin.getMillesime();
        }
    }
}