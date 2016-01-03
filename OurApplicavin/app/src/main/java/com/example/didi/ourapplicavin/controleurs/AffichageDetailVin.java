package com.example.didi.ourapplicavin.controleurs;

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
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;

//Classe qui affiche la description d'un vin
// (de la cave, de la liste de pref, de la bdd ou d'une recherche)
public class AffichageDetailVin extends AppCompatActivity {
    //Attributs associé au layout
    private TextView nomVin = null; //pour afficher le nom du vin
    private TextView detailVin = null; //pour afficher le détail du vin
    private TextView texteRemarques = null;
    private EditText remarquesVin = null; //pour afficher les remarques sur ce vin
    private Button enregistrer = null; //pour enregistrer les remarques
    //Attributs associé à cette classe
    final String NOM_VIN = "vin cave"; //pour passer le nom du vin à une autre activité
    final String VIN_BDD = "vin bdd";
    private String string_nomVin = ""; //pour avoir le nom du vin en string
    private String string_detailVin = ""; //pour avoir le détail du vin en string
    private String string_remarquesVin = ""; //pour avoir les remarques du vin en string
    private Cave maCave = new Cave();
    private Bdd bdd = new Bdd();
    private boolean cave = false;

    //Méthode qui se lance quand on est dans cette activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_vin); //on affiche le layout associé
    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interresse dans le layout
        nomVin = (TextView) findViewById(R.id.nomVin);
        detailVin = (TextView) findViewById(R.id.detailVin);
        remarquesVin = (EditText) findViewById(R.id.remarquesVin);
        enregistrer = (Button) findViewById(R.id.enregistrer);
        texteRemarques = (TextView)findViewById(R.id.textView6);

        //On récupère le nom du vin passé en paramètre lors de la transition (depuis autre activité)
        Intent intent = getIntent();
        int posiCave = -1;
        int posiBdd = -1;

        if (intent != null) {
            posiCave = intent.getIntExtra(NOM_VIN, -1);
            posiBdd = intent.getIntExtra(VIN_BDD, -1);
        }

        if (posiCave != -1) {
            maCave = GestionSauvegarde.getCave();
            cave = true;
        }
        if (posiBdd != -1) {
            bdd = GestionSauvegarde.getBdd();
            string_nomVin = bdd.getVin(posiBdd).getNom();
        }
        else {
            string_nomVin = "erreur";
        }
        nomVin.setText(string_nomVin); //on affiche le nom du vin
        // TODO
        // récupérer la desccription du vin en question
        //string_detailVin = "Détail du vin \n-type de vin : ... \n-cépage : ... \n-région : ...";
        //on affiche le détail
        string_detailVin = bdd.getVin(posiBdd).toString();
        detailVin.setText(string_detailVin);

        if (cave) {
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
        } else {
            remarquesInvisible();
        }
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
            n.addCategory( Intent.CATEGORY_HOME );
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        } else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichageDetailVin.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        } else if (id == R.id.allerPref) {
            Intent n = new Intent(AffichageDetailVin.this, AffichagePref.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        } else if (id == R.id.allerBdd) {
            Intent n = new Intent(AffichageDetailVin.this, AffichageBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void remarquesVisible(){
        texteRemarques.setVisibility(View.VISIBLE);
        remarquesVin.setVisibility(View.VISIBLE);
        enregistrer.setVisibility(View.VISIBLE);
    }

    private void remarquesInvisible(){
        texteRemarques.setVisibility(View.INVISIBLE);
        remarquesVin.setVisibility(View.INVISIBLE);
        enregistrer.setVisibility(View.INVISIBLE);
    }
}
