package com.example.didi.ourapplicavin.controleursIHM;

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

//Classe qui affiche la liste des vins de la recherche
public class AffichageResultatRecherche extends AppCompatActivity {
    private GridView tabNomCol = null;
    private GridView tabResultatVin = null;
    private Button ajoutCave = null;
    private Button ajoutSouhait = null;
    private TextView texte = null;
    private TextView texteOu = null;
    private String nomVinSel = "";
    private int posi;

    final String NOM_VIN = "nom du vin";
    private int nbColParLigne = 4; //définit le nb de col par ligne pour la liste

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_resultat_recherche);
//        TextView nomVin = (TextView)findViewById(R.id.name);
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            nomVin.setText(intent.getStringExtra(NOM_VIN));
//        }

        tabNomCol = (GridView) findViewById(R.id.tabNomColResultatRecherche);
        tabResultatVin = (GridView) findViewById(R.id.tabResultatVin);
        ajoutCave = (Button) findViewById(R.id.ajoutCaveResultat);
        ajoutSouhait = (Button) findViewById(R.id.ajoutSouhaitResultat);
        texte = (TextView) findViewById(R.id.textView8);
        texteOu = (TextView) findViewById(R.id.textOuResultat);

        this.boutonInvisible();

        // TODO
        //il faudra définir les noms des colonnes
        String[] title = new String[]{
                "Nom du vin", "Type", "Nb de bouteilles", "Région"};
        ArrayAdapter<String> adapterTitle = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        tabNomCol.setAdapter(adapterTitle);
        tabNomCol.setNumColumns(nbColParLigne); //pour lui dire que sur une ligne du tableau, il doit y avoir que 3 colonnes
        //tabNom.setBackgroundColor(Color.CYAN);

        // TODO
        //il faudra mettre la liste des vins provenant de la recherche
        String[] numbers = new String[]{
                "Bordeaux", "rouge", "8", "rr1",
                "Cadillac", "blanc", "1", "rr1",
                "Riesling", "blanc", "5", "rr1",
                "Whispering Angel", "rosé", "3", "rr1",
                "MonBazillac", "blanc", "10", "rr1",
                "Champagne dom pérignon", "blanc", "10", "rr1",
                "Gewurztraminer d'Alsace", "blanc", "1", "rr1",
                "Monbazillac", "balnc", "4", "rr1"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);
        tabResultatVin.setAdapter(adapter);
        tabResultatVin.setNumColumns(nbColParLigne);

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

        tabResultatVin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                // ajouter le vin en question à la cave ou à la liste de souhait
                boutonVisible();

                //on va chercher la position nomVin
                posi = position;
                for (int i = 0; i < nbColParLigne; i++) {
                    if (position % nbColParLigne == i) {
                        nomVinSel = (String) ((TextView) tabResultatVin.getChildAt(position - i)).getText();
                        posi = position - i;
                    }
                }
                changeCouleurLigneVin(posi);
                return true;
            }
        });

        // on ajout le vin dans la cave
        ajoutCave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO

                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();
                boutonInvisible();
                rechangeCouleurLigneVin(posi);
            }
        });

        // on ajoute le vin dans la liste de préférence
        ajoutSouhait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO

                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la liste de souhait !",
                        Toast.LENGTH_SHORT).show();
                boutonInvisible();
                rechangeCouleurLigneVin(posi);
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
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        //retour à la recherche
        else if (id == R.id.retourRecherche) {
            Intent n = new Intent(AffichageResultatRecherche.this, AffichageRechercheVin.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void boutonVisible() {
        ajoutCave.setVisibility(View.VISIBLE);
        ajoutSouhait.setVisibility(View.VISIBLE);
        texte.setVisibility(View.VISIBLE);
        texteOu.setVisibility(View.VISIBLE);
        tabResultatVin.setEnabled(false);
    }

    private void boutonInvisible() {
        ajoutCave.setVisibility(View.INVISIBLE);
        ajoutSouhait.setVisibility(View.INVISIBLE);
        texte.setVisibility(View.INVISIBLE);
        texteOu.setVisibility(View.INVISIBLE);
        tabResultatVin.setEnabled(true);
    }

    //on surligne la ligne (vin sélectionné)
    private void changeCouleurLigneVin(int position) {
        for(int i = 0 ; i<nbColParLigne; i++){
            tabResultatVin.getChildAt(position+i).setBackgroundColor(Color.rgb(253, 190, 195));
        }
    }

    //on désurligne la ligne
    private void rechangeCouleurLigneVin(int position) {
        for(int i = 0 ; i<nbColParLigne; i++){
            tabResultatVin.getChildAt(position+i).setBackgroundColor(Color.TRANSPARENT);
        }
    }
}