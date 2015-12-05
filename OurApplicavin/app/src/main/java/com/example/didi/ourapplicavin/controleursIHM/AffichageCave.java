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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;

//Classe qui affiche la liste des vins de la cave "virtuelle" de l'utilisateur
//elle est appelée via le menu principal
public class AffichageCave extends AppCompatActivity {
    //Attributs (boutons pour + ou - nbBouteille,
    // les tableaux (pour nom des col et la liste de vins ...)
    private Button augmenter = null;
    private Button diminuer = null;
    private EditText nb = null;
    private TextView texte = null;
    private Button ok = null;
    private GridView tab = null;
    private GridView tabNom = null;
    private int nbBouteilles;
    private String[] listeVins;
    private int nbBouteilleActualiser;
    private int positionTabNb = 0;
    private String nomVinSel = "";

    public final static String cave = "cave";
    final String NOM_VIN = "nom du vin";
    private int nbColParLigne = 4; //définit le nb de col par ligne pour la liste

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cave);

        //on va cherche le bouton retour et les deux tableaux qu'on a créer sur le layout
        //retour = (Button)findViewById(R.id.retourButton);
        tabNom = (GridView) findViewById(R.id.tabNomColCave);
        tab = (GridView) findViewById(R.id.tabResultatVinCave);
        augmenter = (Button) findViewById(R.id.augmenter);
        diminuer = (Button) findViewById(R.id.diminuer);
        nb = (EditText) findViewById(R.id.nbBouteille);
        texte = (TextView) findViewById(R.id.textView3);
        ok = (Button) findViewById(R.id.ok);

        //on met les boutons + et - invisible car pas besoin maintenant
        boutonsInvisible();

        // TODO
        // il faudra définir les noms des colonnes
        String[] title = new String[]{
                "Nom du vin", "Type", "Nb de bouteilles", "Région"};
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNom.setAdapter(adapterTitle);
        //on veut qu'il y ait que nbColParLigne colonnes sur une ligne
        tabNom.setNumColumns(nbColParLigne);
        //tabNom.setBackgroundColor(Color.CYAN);

        // TODO
        // il faudra mettre la liste des vins provenant de la cave à vin de l'utilisateur
        listeVins = new String[]{
                "Bordeaux", "rouge", "7", "rr1",
                "Cadillac", "blanc", "0", "rr2",
                "Riesling", "blanc", "5", "rr3",
                "Whispering Angel", "rosé", "3", "rr4",
                "MonBazillac", "blanc", "10", "rr5"
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
                //[nomVinSel = (String) ((TextView) v).getText();]
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

        //clique long => +/- nb bouteille
        tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //pour que l'utilisateur augmente ou diminu le nb de bouteille de ce vin, on rend visible les deux boutons
                //et on désactive aussi la liste des vins
                boutonVisible();

                //on va chercher la position nomVin
                String nbBouteilleavant = "0"; //si on arrive pas à récupérer le nb bouteille
                positionTabNb = 2;
                // TODO
                // à changer si plus de 3 col
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

                nb.setText(nbBouteilleavant); //met le nombre de bouteille du vin
                //on surligne la ligne du vin
                changeCouleurLigneVin(positionTabNb - 2);
                return true;
            }
        });

        //pour augmenter le nombre de bouteille (juste un clique)
        augmenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbavant = Integer.parseInt(nb.getText().toString());
                nbBouteilleActualiser = nbavant + 1; //on rajouter une bouteille
                nb.setText(Integer.toString(nbBouteilleActualiser));
            }
        });

        diminuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nbavant = Integer.parseInt(nb.getText().toString());
                if (nbavant <= 1) {
                    //affiche une boite de dialogue pour confirmation suppression vin ou conserver ce vin
                    AlertDialog.Builder boite;
                    boite = new AlertDialog.Builder(AffichageCave.this);
                    boite.setTitle("Suppresion ?");
                    boite.setIcon(R.drawable.photovin);
                    boite.setMessage("Voulez-vous supprimer le "+ nomVinSel+ " ou le conserver dans votre cave avec 0 bouteille ?");
                    boite.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //on remet les boutons invisibles
                                    boutonsInvisible();
                                    rechangeCouleurLigneVin(positionTabNb - 2);
                                    // TODO
                                    //réactualiser la liste des vins (recharger la liste mais avant supp le vin)
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                                            android.R.layout.simple_list_item_1, listeVins);
                                    tab.setAdapter(adapter);
                                }
                            }
                    );
                    boite.setNegativeButton("Conserver", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // on revient dans l'état précédent
                                    nb.setText("0");
                                    boutonsInvisible();
                                    rechangeCouleurLigneVin(positionTabNb - 2);
                                    // TODO
                                    // reactuliser dans la cave
                                    // faire plutot : modifier le nb bouteille dans la liste des vins de la cave
                                    // puis recharger la liste
                                    //mettre méthode supprVin
                                    listeVins[positionTabNb] = "0"; // à supprimer quand mofif liste des vins
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                                            android.R.layout.simple_list_item_1, listeVins);
                                    tab.setAdapter(adapter);
                                }
                            }
                    );
                    boite.show();
                    return;
                }
                nbBouteilleActualiser = nbavant - 1; //on enlève une bouteille
                nb.setText(Integer.toString(nbBouteilleActualiser));
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on récupère le nombre de bouteilles actualisé
                nbBouteilles = Integer.parseInt(nb.getText().toString());

                //on remet les boutons invisibles
                boutonsInvisible();
                rechangeCouleurLigneVin(positionTabNb - 2);
                // TODO
                // remettre à jour la liste de vin (nb bouteille à changer)
                // faire plutot : modifier le nb bouteille dans la liste des vins de la cave
                // puis recharger la liste
                // faire une méthode pour ça supprVin
                listeVins[positionTabNb] = Integer.toString(nbBouteilles); // à supp quand on a mofif la liste
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AffichageCave.this,
                        android.R.layout.simple_list_item_1, listeVins);
                tab.setAdapter(adapter);
            }
        });

    }

    //Méthode qui perme de mettre un menu à l'écran
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
            startActivity(n);
            return true;
        } else if (id == R.id.rechercheCave) {
            Toast.makeText(AffichageCave.this, "Vous aller effectuer une recherche dans votre cave !",
                    Toast.LENGTH_SHORT).show();
            Intent n = new Intent(AffichageCave.this, AffichageRechercheVin.class);
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
    private void boutonVisible() {
        augmenter.setVisibility(View.VISIBLE);
        diminuer.setVisibility(View.VISIBLE);
        nb.setVisibility(View.VISIBLE);
        ok.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        tab.setEnabled(false);
    }

    //on surligne la ligne (vin sélectionné)
    //position doit être celui du nom
    private void changeCouleurLigneVin(int position) {
        for(int i = 0 ; i<nbColParLigne; i++){
            tab.getChildAt(position+i).setBackgroundColor(Color.rgb(204, 204, 255));
        }
    }

    //on désurligne la ligne
    //position doit être celui du nom
    private void rechangeCouleurLigneVin(int position) {
        for(int i = 0 ; i<nbColParLigne; i++){
            tab.getChildAt(position+i).setBackgroundColor(Color.TRANSPARENT);
        }
    }
}