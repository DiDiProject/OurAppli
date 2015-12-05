package com.example.didi.ourapplicavin.controleursIHM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.didi.ourapplicavin.R;

//Classe qui affiche la description d'un vin
// (de la cave, de la liste de pref, de la bdd ou d'une recherche)
public class AffichageDetailVin extends AppCompatActivity {
    //Attributs associé au layout
    private TextView nomVin = null; //pour afficher le nom du vin
    private TextView detailVin = null; //pour afficher le détail du vin
    private EditText remarquesVin = null; //pour afficher les remarques sur ce vin
    private Button enregistrer = null; //pour enregistrer les remarques
    //Attributs associé à cette classe
    final String NOM_VIN = "nom du vin"; //pour passer le nom du vin à une autre activité
    private String string_nomVin = ""; //pour avoir le nom du vin en string
    private String string_detailVin = ""; //pour avoir le détail du vin en string
    private String string_remarquesVin = ""; //pour avoir les remarques du vin en string

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_vin); //on affiche le layout associé

        //on va cherche tous les élements qui nous interresse dans le layout
        nomVin = (TextView) findViewById(R.id.nomVin);
        detailVin = (TextView) findViewById(R.id.detailVin);
        remarquesVin = (EditText) findViewById(R.id.remarquesVin);
        enregistrer = (Button) findViewById(R.id.enregistrer);

        //On récupère le nom du vin passé en paramètre lors de la transition (depuis autre activité)
        Intent intent = getIntent();
        if (intent != null) {
            string_nomVin = intent.getStringExtra(NOM_VIN);
            nomVin.setText(string_nomVin); //on affiche le nom du vin
        }

        // TODO
        // récupérer la desccription du vin en question
        string_detailVin = "Détail du vin -type de vin \n-cépage ... \n-Ffndojhd dfndhif fvndzvh kndk";
        //on affiche le détail
        detailVin.setText(string_detailVin);

        // récupérer les remarques
        string_remarquesVin = "Vos commentaires sur ce vin \nDate des bouteilles \n-accords avec plats ...";
        //on affiche les remarques
        remarquesVin.setText(string_remarquesVin);

        // on enregistre les remarques personnelles
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                String nvRemarques = remarquesVin.getText().toString(); //on récupère les nouvelles remarques
                // il faut maintenant les enregistrer
            }
        });

    }

    //Méthode qui perme de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_detail_vin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_detail_vin, menu);
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
            Intent n = new Intent(AffichageDetailVin.this, AffichageMenuPrincipal.class);
            startActivity(n);
            return true;
        } else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichageDetailVin.this, AffichageCave.class);
            startActivity(n);
            return true;
        } else if (id == R.id.allerPref) {
            Intent n = new Intent(AffichageDetailVin.this, AffichagePref.class);
            startActivity(n);
            return true;
        } else if (id == R.id.allerBdd) {
            Intent n = new Intent(AffichageDetailVin.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
