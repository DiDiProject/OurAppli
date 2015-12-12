package com.example.didi.ourapplicavin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didi.ourapplicavin.controleurs.AffichageDetailVin;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.Vin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// activité test pour tester mes classes modèles (avec leur méthode; ajout, supp, recherche ..)
// et pour tester la serialization ("enregistrement d'u object" ici la cave
public class TestModele extends Activity {
    private Button augmenter = null; //bouton pour augmenter le nb de bouteille
    private Button diminuer = null; //bouton pour diminuer le nb de bouteille
    private EditText nb = null; //pour afficher le nb de bouteille en temps réel
    private TextView texte = null; //texte pour dire qu'on parle de bouteille
    private Button ok = null; //pour enregistrer ce nouveau nb de bouteille
    private GridView tab = null; //tab pour afficher la liste des vins de la cave
    private GridView tabNom = null; //tab pour afficher le nom des colonnes

    // Attributs associé au layout
    private EditText nom = null; //pour récupérer le nom
    private EditText robe = null; //pour récupérer la couleur
    private EditText cepage = null; //pour récupérer le cépage
    private EditText region = null; //pour récupérer la région
    private EditText nbBouteilleAjout = null;
    private Button ajoutVin = null; //bouton pour ajouter ce vin dans la bdd

    //Attributs pour cette classe
    private int nbBouteilles; //pour avoir le nb de bouteille (en entier)
    private String[] listeVins; // TODO liste des vins de la cave à récupérer
    private int nbBouteilleActualiser; //pour aovir le nb de bouteille actualisé
    private int positionTabNb = 0; //pour avoir la position dans le tab (liste vins) du nb de bouteille
    private String nomVinSel = ""; //pour avoir le nom du vin sélectionné
    public final static String cave = "cave"; // TODO pour dire qu'on ait dans la cave pr recherche
    final String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private int nbColParLigne = 4; // TODO définit le nb de col par ligne pour la liste
    private boolean ajoutOuPas = false;

    //Attributs assicié à cette classe
    private String stringNom = ""; //pour avoir le nom en string
    private String stringRobe = ""; //la couleur en string
    private String stringCepage = ""; //le cépage en string
    private String stringRegion = ""; //et la région en string
    private Cave caveTest = new Cave();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_modele);

        tabNom = (GridView) findViewById(R.id.tabNomColCaveTest);
        tab = (GridView) findViewById(R.id.tabResultatVinCaveTest);
        augmenter = (Button) findViewById(R.id.augmenterTest);
        diminuer = (Button) findViewById(R.id.diminuerTest);
        nb = (EditText) findViewById(R.id.nbBouteilleTest);
        texte = (TextView) findViewById(R.id.textView3Test);
        ok = (Button) findViewById(R.id.okTest);

        nom = (EditText) findViewById(R.id.nomAjoutTest);
        robe = (EditText) findViewById(R.id.robeAjoutTest);
        cepage = (EditText) findViewById(R.id.cepageAjoutTest);
        region = (EditText) findViewById(R.id.regionAjoutTest);
        nbBouteilleAjout = (EditText) findViewById(R.id.nbAjoutTest);
        ajoutVin = (Button) findViewById(R.id.ajoutVinTest);

        boutonsInvisible();
        tabNom.setEnabled(false); //pas besion de cliquer sur le tab des noms des colonnes

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{"Nom", "Couleur", "Nb de bouteilles", "Région"};
        // on va mettre ce tab des noms des colonnes dans le tab associé
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        tabNom.setNumColumns(nbColParLigne);  //définit le nombre de colonne par ligne

        Log.i("i", "init1");
        caveTest = getCaveTest();
        if (caveTest == null){
            init();
        }
        //Intent n = getIntent();
        //caveTest = (Cave) n.getSerializableExtra("cave");
        affichage();
        Log.i("ii", "fin init par putExtra");

        //caveTest = getCaveTest();
        // TODO
        // il faudra mettre la liste des vins provenant de la cave à vin de l'utilisateur
        /*listeVins = new String[]{
                "Bordeaux", "rouge", "7", "rr1",
                "Cadillac", "blanc", "0", "rr2",
                "Riesling", "blanc", "5", "rr3",
                "Whispering Angel", "rosé", "3", "rr4",
                "MonBazillac", "blanc", "10", "rr5",
                "MonBazillac2", "blanc", "10", "rr5"
        };*/
        // on va mettre ce tab de la liste des vins dans le tab associé
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
                Intent n = new Intent(TestModele.this, AffichageDetailVin.class);
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
                    boite = new AlertDialog.Builder(TestModele.this);
                    boite.setTitle("Suppresion ?");
                    boite.setIcon(R.drawable.photovin); //image
                    boite.setMessage("Voulez-vous supprimer le " + nomVinSel + " ou le conserver dans votre cave avec 0 bouteille ?");
                    boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //on remet les boutons invisibles et remet le tab actif
                                    boutonsInvisible();
                                    rechangeCouleurLigneVin(positionTabNb - 2); //on enlève la couleur du vin sélectionné
                                    // TODO
                                    //réactualiser la liste des vins (recharger la liste mais avant supp le vin)

                                    Log.i( "ss", "suup Vin : "+nomVinSel);
                                    Vin vin = caveTest.rechercheVinParNom(nomVinSel);
                                    caveTest.supprVin(vin);
                                    affichage();
                                    enregistrementCave();

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestModele.this,
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
                                    listeVins[positionTabNb] = "0"; // à supprimer quand mofif liste des vins
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestModele.this,
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
                // TODO
                // remettre à jour la liste de vin (nb bouteille à changer)
                // faire plutot : modifier le nb bouteille dans la liste des vins de la cave
                // puis recharger la liste
                // faire une méthode pour ça supprVin
                Log.i( "dd", "actualise le nb de bouteille de "+nomVinSel);
                Vin vin = caveTest.rechercheVinParNom(nomVinSel);
                caveTest.setNbBouteilleVin(vin, nbBouteilles);
                affichage();
                enregistrementCave();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestModele.this,
                        android.R.layout.simple_list_item_1, listeVins);
                tab.setAdapter(adapter);
            }
        });

        //pour ajouter le vin avec les info de l'utilisateur dans la bdd
        ajoutVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on récupère les informations que l'utilisateur a rempli
                stringNom = nom.getText().toString();
                stringRobe = robe.getText().toString();
                stringCepage = cepage.getText().toString();
                stringRegion = region.getText().toString();

                int nbBB;
                nbBB = Integer.valueOf(nbBouteilleAjout.getText().toString());

                // TODO

                caveTest.ajoutVin(new Vin(stringNom, stringRobe, stringCepage, stringRegion), nbBB);
                affichage();
                enregistrementCave();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestModele.this,
                        android.R.layout.simple_list_item_1, listeVins);
                tab.setAdapter(adapter);
                // on initialise les champs pour un nouveau ajout
                nom.setText("");
                robe.setText("");
                cepage.setText("");
                region.setText("");

            }
        });


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
        caveTest = new Cave();
        caveTest.ajoutVin(new Vin("Bordeaux", "rouge", "Merlot", "Gironde"), 3);
        caveTest.ajoutVin(new Vin("Cadillac", "blanc", "rr", "Gironde"), 10);
        caveTest.ajoutVin(new Vin("Riesling", "blanc", "fgg", "Gironde"), 1);
        affichage();
        // TODO
        // enregistrer la bdd
        Log.i("e", "va ds enregistrement caveTest");
        enregistrementCave();
    }

    public void affichage() {
        listeVins = new String[caveTest.getVinsCave().getNombreVins() * 4];
        for (int i = 0; i < caveTest.getVinsCave().getNombreVins(); i++) {
            Vin vin = caveTest.getVinsCave().getListeVins().get(i);
            listeVins[0 + i * 4] = vin.getNom();
            listeVins[1 + i * 4] = vin.getCouleur();
            listeVins[2 + i * 4] = "" + caveTest.getNbBouteilleVin(vin);
            listeVins[3 + i * 4] = vin.getRegion();
        }
    }

    public void enregistrementCave() {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichier = new FileOutputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/caveTest.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(caveTest);
            Log.i("ee", "enregistrement caveTest");
            oos.flush();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // pour ravoir la cave (qu'on a enregistrer avant)
    public Cave getCaveTest() {

        Cave cave = null;
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) +"/caveTest.ser");
            ois = new ObjectInputStream(fichier);
            final Cave cave2 = (Cave) ois.readObject();
            Log.i( "gg", "getCaveTest");
            cave = cave2;
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }

        return cave;
    }
}
