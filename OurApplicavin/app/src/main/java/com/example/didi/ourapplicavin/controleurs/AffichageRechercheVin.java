package com.example.didi.ourapplicavin.controleurs;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;

import java.util.ArrayList;
import java.util.List;

//classe pour faire une recherche (par nom ou par critère) soit dans la cave ou dans la bdd
public class AffichageRechercheVin extends AppCompatActivity {
    //Attributs associés au layout
    private EditText nom = null;
    private Button rechercheNom = null; //bouton pour faire le recherche par le nom
    private Button faireRechercheAvancee = null; //pour accéder aux différents critères
    private TextView texteRobe = null;
    private TextView texteCepage = null;
    private TextView texteRegion = null;
    //private TextView texteMillesime = null;
    //private TextView texteTerroir = null;
    private Spinner robe = null;
    private EditText cepage = null;
    private EditText region = null;
    //private EditText mellisesime = null;
    //private EditText terroir = null;
    private Button rechercheAvancee = null; //bouton pour faire la recherche par critère
    //Attributs pour cette classe
    private boolean avanceeOuPas = false; //si on ait dans recherche par critère ou pas (non de base)
    final static String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    public final static String ENDROIT = "endroit";
    public final static String TYPE_RECHERCHE = "type de recherche";
    private String string_couleur = "";
    private int endroit = 0;

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_recherche_vin); //on affiche le layout associé


    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interresse dans le layout
        nom = (EditText) findViewById(R.id.nom);
        rechercheNom = (Button) findViewById(R.id.rechercheParNom);
        faireRechercheAvancee = (Button) findViewById(R.id.faireRechercheAvancee);
        rechercheAvancee = (Button) findViewById(R.id.rechercheAvancee);
        texteRobe = (TextView) findViewById(R.id.textRobe);
        texteCepage = (TextView) findViewById(R.id.textCepage);
        texteRegion = (TextView) findViewById(R.id.textRegion);
        //texteMillesime = (TextView) findViewById(R.id.textMillesime);
        //texteTerroir = (TextView) findViewById(R.id.textTerroir);
        robe = (Spinner) findViewById(R.id.listeCouleurRecherche);
        cepage = (EditText) findViewById(R.id.cepage);
        region = (EditText) findViewById(R.id.region);
        //mellisesime = (EditText) findViewById(R.id.millesime);
        //terroir = (EditText) findViewById(R.id.terroir);
        // on rend les différents champs (sauf nom) invisible car de base recherche par nom et pas par critère
        /*if (savedInstanceState != null) {
            // Restore value of members from saved state
            if (avanceeOuPas) {
                visibleRechercheAvancee();
            } else {
                this.invisibleRechercheAvancee();
            }
        } else {
            this.invisibleRechercheAvancee();
        }*/

        this.invisibleRechercheAvancee();

        List liste = new ArrayList();
        liste.add("");
        liste.add("Rouge");
        liste.add("Blanc");
        liste.add("Rosé");
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                liste
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        robe.setAdapter(adapter);

        Intent i = getIntent();
        endroit = i.getIntExtra(AffichageBdd.ENDROIT, 2);
        if (endroit != 2) {
            endroit = i.getIntExtra(AffichageCave.ENDROIT, 1);
            if (endroit != 1) {
                endroit = i.getIntExtra(AffichagePref.ENDROIT, 3);
            }
        } else {
            endroit = 2;
        }

        //recherche par le nom
        rechercheNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n;
                if (endroit != 1) {
                    n = new Intent(AffichageRechercheVin.this, AffichageResultatRecherche.class);

                } else {
                    n = new Intent(AffichageRechercheVin.this, AffichageResultatRechercheCave.class);
                }
                n.putExtra(NOM_VIN, nom.getText().toString()); //on passe le nom à chercher à l'activé résultat
                Log.i("AffichageRecherche", nom.getText().toString() + " vin recherché  :::");
                n.putExtra(ENDROIT, endroit);
                n.putExtra(TYPE_RECHERCHE, 0);
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });

        //pour avoir les différents champs de critères (rechercher avancée)
        faireRechercheAvancee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avanceeOuPas = true;
                visibleRechercheAvancee();
            }
        });

        //recherche par critères
        rechercheAvancee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Intent n;
                if (endroit != 1) {
                    n = new Intent(AffichageRechercheVin.this, AffichageResultatRecherche.class);

                } else {
                    n = new Intent(AffichageRechercheVin.this, AffichageResultatRechercheCave.class);
                }

                n.putExtra("couleur", string_couleur);
                String string_cepage = cepage.getText().toString();
                n.putExtra("cépage", string_cepage);
                String string_region = region.getText().toString();
                n.putExtra("région", string_region);
                String string_nom = nom.getText().toString();
                n.putExtra("nom", string_nom);

                //mettre en para les différents champs
                n.putExtra(ENDROIT, endroit);
                n.putExtra(TYPE_RECHERCHE, 1);
                n.addCategory(Intent.CATEGORY_HOME);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });

        robe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                string_couleur = String.valueOf(robe.getSelectedItem());
                Toast.makeText(AffichageRechercheVin.this, "couleur du vin : " + string_couleur,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string_couleur = "";
            }
        });

    }

    //Méthode qui permet de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_rechercher_vin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_recherche_vin, menu);
        return true;
    }

    //Méthode qui permet d'éxécuter une action quand on clique sur un sous menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageMenuPrincipal.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la cave
        else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        //aller à la bdd
        else if (id == R.id.retourBdd) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        if(avanceeOuPas){
            visibleRechercheAvancee();
        }
        savedInstanceState.putInt(NOM_VIN, 0);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }*/

    //Méthode qui rend visible des boutons (recherche par critère)
    private void visibleRechercheAvancee() {
        texteRobe.setVisibility(View.VISIBLE);
        texteCepage.setVisibility(View.VISIBLE);
        texteRegion.setVisibility(View.VISIBLE);
        //texteMillesime.setVisibility(View.VISIBLE);
        //texteTerroir.setVisibility(View.VISIBLE);
        robe.setVisibility(View.VISIBLE);
        cepage.setVisibility(View.VISIBLE);
        region.setVisibility(View.VISIBLE);
        //mellisesime.setVisibility(View.VISIBLE);
        //terroir.setVisibility(View.VISIBLE);
        rechercheAvancee.setVisibility(View.VISIBLE);
    }

    //Méthode qui rend invisible des boutons (recherche par critère)
    private void invisibleRechercheAvancee() {
        texteRobe.setVisibility(View.INVISIBLE);
        texteCepage.setVisibility(View.INVISIBLE);
        texteRegion.setVisibility(View.INVISIBLE);
        //texteMillesime.setVisibility(View.INVISIBLE);
        //texteTerroir.setVisibility(View.INVISIBLE);
        robe.setVisibility(View.INVISIBLE);
        cepage.setVisibility(View.INVISIBLE);
        region.setVisibility(View.INVISIBLE);
        //mellisesime.setVisibility(View.INVISIBLE);
        //terroir.setVisibility(View.INVISIBLE);
        rechercheAvancee.setVisibility(View.INVISIBLE);
    }

}