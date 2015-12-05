package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.didi.ourapplicavin.R;

//classe pour faire une recherche (par nom ou par critère) soit dans la cave ou dans la bdd
public class AffichageRechercheVin extends AppCompatActivity {
    //Attributs
    private EditText nom = null;
    private Button rechercheNom = null;
    private Button faireRechercheAvancee = null;
    private TextView texteRobe = null;
    private TextView texteCepage = null;
    private TextView texteRegion = null;
    private TextView texteMillesime = null;
    private TextView texteTerroir = null;
    private EditText robe = null;
    private EditText cepage = null;
    private EditText region = null;
    private EditText mellisesime = null;
    private EditText terroir = null;
    private Button rechercheAvancee = null;
    private boolean avanceeOuPas = false;

    final String NOM_VIN = "nom du vin";

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_recherche_vin);

        nom = (EditText) findViewById(R.id.nom);
        rechercheNom = (Button) findViewById(R.id.rechercheParNom);
        faireRechercheAvancee = (Button) findViewById(R.id.faireRechercheAvancee);
        rechercheAvancee = (Button) findViewById(R.id.rechercheAvancee);
        texteRobe = (TextView) findViewById(R.id.textRobe);
        texteCepage = (TextView) findViewById(R.id.textCepage);
        texteRegion = (TextView) findViewById(R.id.textRegion);
        texteMillesime = (TextView) findViewById(R.id.textMillesime);
        texteTerroir = (TextView) findViewById(R.id.textTerroir);
        robe = (EditText) findViewById(R.id.robe);
        cepage = (EditText) findViewById(R.id.cepage);
        region = (EditText) findViewById(R.id.region);
        mellisesime = (EditText) findViewById(R.id.millesime);
        terroir = (EditText) findViewById(R.id.terroir);

        this.invisibleRechercheAvancee();

        rechercheNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Intent n = new Intent(AffichageRechercheVin.this, AffichageResultatRecherche.class);
                n.putExtra(NOM_VIN, nom.getText().toString());
                startActivity(n);
            }
        });

        faireRechercheAvancee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                avanceeOuPas = true;
                visibleRechercheAvancee();

            }
        });

        rechercheAvancee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Intent n = new Intent(AffichageRechercheVin.this, AffichageResultatRecherche.class);
                startActivity(n);
            }
        });
    }

    //Méthode qui perme de mettre un menu à l'écran
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        } else if (id == R.id.retourCave) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageCave.class);
            startActivity(n);
            return true;
        }
        else if (id == R.id.retourBdd) {
            Intent n = new Intent(AffichageRechercheVin.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void visibleRechercheAvancee() {
        texteRobe.setVisibility(View.VISIBLE);
        texteCepage.setVisibility(View.VISIBLE);
        texteRegion.setVisibility(View.VISIBLE);
        texteMillesime.setVisibility(View.VISIBLE);
        texteTerroir.setVisibility(View.VISIBLE);
        robe.setVisibility(View.VISIBLE);
        cepage.setVisibility(View.VISIBLE);
        region.setVisibility(View.VISIBLE);
        mellisesime.setVisibility(View.VISIBLE);
        terroir.setVisibility(View.VISIBLE);
        rechercheAvancee.setVisibility(View.VISIBLE);
    }

    private void invisibleRechercheAvancee() {
        texteRobe.setVisibility(View.INVISIBLE);
        texteCepage.setVisibility(View.INVISIBLE);
        texteRegion.setVisibility(View.INVISIBLE);
        texteMillesime.setVisibility(View.INVISIBLE);
        texteTerroir.setVisibility(View.INVISIBLE);
        robe.setVisibility(View.INVISIBLE);
        cepage.setVisibility(View.INVISIBLE);
        region.setVisibility(View.INVISIBLE);
        mellisesime.setVisibility(View.INVISIBLE);
        terroir.setVisibility(View.INVISIBLE);
        rechercheAvancee.setVisibility(View.INVISIBLE);
    }

}