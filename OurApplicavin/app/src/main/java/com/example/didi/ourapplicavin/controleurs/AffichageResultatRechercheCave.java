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

//Classe qui affiche la liste des vins de la recherche
public class AffichageResultatRechercheCave extends AppCompatActivity {
    //Attributs associés au layout
    private GridView tabNomCol = null; //tab pour afficher le nom des colonnes
    private GridView tabResultatVin = null; //tab pour afficher la liste des vins de la bdd
    private Button augmenter = null; //bouton pour ajout un vin dans la cave
    private Button diminuer = null; //dans la liste de souhait
    private Button ok = null; //annuler le vin sélectionné
    private TextView texte = null; //texte ajout
    private TextView nb = null;
    //Attributs pour cette classe
    private Cave liste;
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    private String couleurVinSel = "";
    private String nbVinSel = "";
    private String cepageVinSel = "";
    private String millesimeVinSel = "";
    private int nbBouteilles; //pour avoir le nb de bouteille (en entier)
    private int nbBouteilleActualiser; //pour aovir le nb de bouteille actualisé
    private int positionTabNom; //pour avoir la position dans le tab du vin sélectionné
    final String NOM_VIN = "vin cave"; //pour passer le nom du vin à une autre activité
    final static String VIN_BDD = "vin bdd";
    private int nbColParLigne = 5; //définit le nb de col par ligne pour la liste
    private String[] listeVins = new String[nbColParLigne]; //liste des vin de la recherche à récupérer
    public final static String ENDROIT = "endroit"; //pour dire qu'on ait dans la bdd pr recherche
    private int endroit = 0;
    private Cave maCave = new Cave();
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

        Log.i("ResulatRechercheCave", "début de l'activité");
        //on va cherche tous les élements qui nous interresse dans le layout
        tabNomCol = (GridView) findViewById(R.id.tabNomColCave);
        tabResultatVin = (GridView) findViewById(R.id.tabResultatVinCave);
        augmenter = (Button) findViewById(R.id.augmenter);
        diminuer = (Button) findViewById(R.id.diminuer);
        nb = (EditText) findViewById(R.id.nbBouteille);
        texte = (TextView) findViewById(R.id.textView3);
        ok = (Button) findViewById(R.id.ok);
        // on rend les boutons inutiles au départ invisible ainsi que le tab actif
        boutonsInvisible();
        tabNomCol.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        // TODO
        //il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom", "Type", "Nb", "Cépage", "Millésime"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, title);
        tabNomCol.setAdapter(adapterTitle);
        tabNomCol.setNumColumns(nbColParLigne); //définit le nombre de colonne par ligne

        //il faudra mettre la liste des vins provenant de la recherche
        Intent i = getIntent();
        if (i != null) {
            endroit = i.getIntExtra(AffichageCave.ENDROIT, 2);
            nomVinSel = i.getStringExtra(AffichageRechercheVin.NOM_VIN);
            Log.i("AffichageResultat", nomVinSel + " vin recherché !!!");
            if (endroit != 1) {
                Toast.makeText(getApplicationContext(), "erreur !!!",
                        Toast.LENGTH_SHORT).show();
            }

            int type_recherche = i.getIntExtra(AffichageRechercheVin.TYPE_RECHERCHE, 0);
            if (type_recherche == 0) {
                rechercheParNom();
            } else {
                rechercheParCritere();
            }
        }
        // on va mettre ce tab de la liste des vins dans le tab associé
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
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
                        couleurVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 1)).getText();
                        nbVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 2)).getText();
                        cepageVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 3)).getText();
                        millesimeVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 4)).getText();
                    }
                }
                ArrayList<String> ce = new ArrayList<>();
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
                        Intent n = new Intent(AffichageResultatRechercheCave.this, AffichageDetailVin.class);
                        //en passant des données (nom du vin ici)
                        n.putExtra(NOM_VIN, positionCave);
                        n.putExtra(VIN_BDD, positionBdd);
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

        // quand on fait un clic long sur un des vins, on veut soit l'ajouter
        // dans la cave ou dans la liste de souhait
        tabResultatVin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //on rend visible les boutons ajout dans cave, pref et annuler
                //et on rend le tab inactif
                boutonsVisible();
                //on va chercher la position nomVin ainsi que sa position dans le tab
                positionTabNom = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i)).getText();
                        couleurVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 1)).getText();
                        nbVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 2)).getText();
                        cepageVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 3)).getText();
                        millesimeVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i + 4)).getText();
                        positionTabNom = position - i;
                    }
                }
                nb.setText(nbVinSel);
                changeCouleurLigneVin(positionTabNom); //on surligne la ligne du vin sélectionné
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
            public void onClick(final View v) {
                int nbavant = Integer.parseInt(nb.getText().toString()); //on récupère le nb de bouteille
                //si on arrive à 1 bouteille ou à 0
                if (nbavant <= 1) {
                    //affiche une boite de dialogue pour confirmation suppression vin ou conserver ce vin
                    AlertDialog.Builder boite;
                    boite = new AlertDialog.Builder(AffichageResultatRechercheCave.this);
                    boite.setTitle("Suppresion ?");
                    boite.setIcon(R.drawable.photovin); //image
                    boite.setMessage("Voulez-vous supprimer le " + nomVinSel + " ou le conserver dans votre cave avec 0 bouteille ?");
                    boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //on remet les boutons invisibles et remet le tab actif
                                    boutonsInvisible();
                                    rechangeCouleurLigneVin(positionTabNom); //on enlève la couleur du vin sélectionné
                                    // on va chercher la cave qu'on a enregistré
                                    maCave = GestionSauvegarde.getCave();
                                    // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                                    // on récupère le vin à supprimer
                                    ArrayList<String> ce = new ArrayList<>();
                                    ce.add(cepageVinSel);
                                    Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", 0, millesimeVinSel);
                                    int posi = liste.rechercheVin(vin);
                                    int posiCave = maCave.rechercheVin(vin);
                                    if (posi != -1 || posiCave != -1) {
                                        vin = maCave.getVin(posiCave);
                                        maCave.supprVin(vin);
                                        vin = liste.getVin(posi);
                                        liste.supprVin(vin);
                                        affichage(); //on réactualise la cave pour l'affichage
                                        GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave
                                        //on affichage l'actulisation de la cave
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageResultatRechercheCave.this,
                                                android.R.layout.simple_list_item_1, listeVins);
                                        tabResultatVin.setAdapter(adapter);
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
                                    rechangeCouleurLigneVin(positionTabNom); //enlève couleur du vin sélectionné
                                    // on va chercher la cave qu'on a enregistré
                                    maCave = GestionSauvegarde.getCave();
                                    // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                                    // on va chercher la vin pour changer le nb de bouteille => 0
                                    ArrayList<String> ce = new ArrayList<>();
                                    ce.add(cepageVinSel);
                                    Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", 0, millesimeVinSel);
                                    int posi = liste.rechercheVin(vin);
                                    int posiCave = maCave.rechercheVin(vin);
                                    if (posi != -1 || posiCave != -1) {
                                        maCave.getVin(posiCave).setNbBouteille(0); // on modifie le nb de bouteille du vin
                                        liste.getVin(posi).setNbBouteille(0);
                                        affichage(); //on réactualise la cave pour l'affichage
                                        GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave
                                        //on affichage l'actulisation de la cave
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageResultatRechercheCave.this,
                                                android.R.layout.simple_list_item_1, listeVins);
                                        tabResultatVin.setAdapter(adapter);
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
                rechangeCouleurLigneVin(positionTabNom); //enlève couleur vin sélectionné
                // on va chercher la cave qu'on a enregistré
                // prendre le vin en entier pas juste le nom (car peut avoir même nom avec deux vin différents
                // on va chercher la vin pour changer le nb de bouteille
                ArrayList<String> ce = new ArrayList<>();
                ce.add(cepageVinSel);
                Vin vin = new Vin(nomVinSel, couleurVinSel, ce, "", 0, millesimeVinSel);
                maCave = GestionSauvegarde.getCave();
                int posiCave = maCave.rechercheVin(vin);
                int posi = liste.rechercheVin(vin);
                if (posi != -1 || posiCave != -1) {
                    liste.getVin(posi).setNbBouteille(nbBouteilles); //onchage le nb de bouteille du vin ds la cave
                    maCave.getVin(posiCave).setNbBouteille(nbBouteilles);
                    Log.i("AffichageCave", "on actualise le nb de bouteille de " + nomVinSel);
                    affichage(); //on réactualise la cave pour l'affichage
                    GestionSauvegarde.enregistrementCave(maCave);
                    //on affichage l'actulisation de la cave
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AffichageResultatRechercheCave.this,
                            android.R.layout.simple_list_item_1, listeVins);
                    tabResultatVin.setAdapter(adapter);
                    Log.i("AffichageCave", "on actualise le nb de bouteille de " + nomVinSel);
                } else {
                    Toast.makeText(getApplicationContext(), "le vin que vous avez sélectionné n'a pas été trouvé dans la liste de recherche !!!",
                            Toast.LENGTH_SHORT).show();
                }
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
            Intent n = new Intent(AffichageResultatRechercheCave.this, AffichageMenuPrincipal.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageResultatRechercheCave.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourBdd) {
            Intent n = new Intent(AffichageResultatRechercheCave.this, AffichageBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //retour à la recherche
        else if (id == R.id.retourRecherche) {
            Intent n = new Intent(AffichageResultatRechercheCave.this, AffichageRechercheVin.class);
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
        tabResultatVin.setEnabled(true);
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
        tabResultatVin.setEnabled(false);
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

    private void rechercheParNom() {
        listeVins = new String[nbColParLigne];
        liste = new Cave();
        Cave cave = GestionSauvegarde.getCave();
        Log.i("AffichageResultat", "recherche ds la cave de " + nomVinSel);
        liste.setMaCave(cave.rechercheVinParNom(nomVinSel));
        Log.i("AffichageResultat", "recherche ds la cave");
        Log.i("AffichageResultat", liste.toString() + "");
        listeVins = new String[liste.getVinsCave().getNombreVins() * nbColParLigne];
        for (int i = 0; i < liste.getVinsCave().getNombreVins(); i++) {
            //pour chaque vin, on affiche le nom (sur la 1ère col), la couleur (la 2ème col),
            //le nb de bouteille (la 3ème col) et la région (sur la 4ème)
            Vin vin = liste.getVinsCave().getListeVins().get(i);
            listeVins[    i * nbColParLigne] = vin.getNom();
            listeVins[1 + i * nbColParLigne] = vin.getCouleur();
            listeVins[2 + i * nbColParLigne] = "" + vin.getNbBouteille();
            listeVins[3 + i * nbColParLigne] = vin.getCepage().get(0);
            listeVins[4 + i * nbColParLigne] = vin.getMillesime();
        }
    }

    public void rechercheParCritere() {
        // TODO
    }

    //Méthode pour enregistrer la cave dans un tableau pour après l'afficher
    public void affichage() {
        //on initialise le tableau avec le nb de case approprié (nb de vins * nb de col)
        listeVins = new String[liste.getVinsCave().getNombreVins() * nbColParLigne];
        for (int i = 0; i < liste.getVinsCave().getNombreVins(); i++) {
            //pour chaque vin, on affiche le nom (sur la 1ère col), la couleur (la 2ème col),
            //le nb de bouteille (la 3ème col) et la région (sur la 4ème)
            Vin vin = liste.getVinsCave().getListeVins().get(i);
            listeVins[    i * nbColParLigne] = vin.getNom();
            listeVins[1 + i * nbColParLigne] = vin.getCouleur();
            listeVins[2 + i * nbColParLigne] = "" + vin.getNbBouteille();
            listeVins[3 + i * nbColParLigne] = vin.getCepage().get(0);
            listeVins[4 + i * nbColParLigne] = vin.getMillesime();
        }
    }
}