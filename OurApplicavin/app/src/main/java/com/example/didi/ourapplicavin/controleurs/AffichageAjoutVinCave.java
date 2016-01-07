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
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.Vin;

import java.util.ArrayList;
import java.util.List;

//Classe qui permet à l'utilisateur d'ajouter un vin dans la cave en rensignant le nb de bouteille ainsi que le millesime
public class AffichageAjoutVinCave extends AppCompatActivity {
    // Attributs associé au layout
    private EditText nbBouteille = null; //pour récupérer le nom
    private Spinner listeMillesime = null;

    private Cave maCave;
    private Bdd bdd;
    private String nomVinSel = "";
    private String string_millesime = "2016";
    private int endroit = 0;
    private Vin vinSel = new Vin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_ajout_vin_cave);

        maCave = new Cave();
        bdd = new Bdd();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //on va cherche tous les élements qui nous interressent dans le layout
        nbBouteille = (EditText) findViewById(R.id.nbBouteilleAjoutCave);
        //millesime = (EditText) findViewById(R.id.millesimeAjoutCave);
        Button ajout = (Button) findViewById(R.id.boutonAjoutCave);
        listeMillesime = (Spinner)findViewById(R.id.listeMillesime);
        TextView detailVin = (TextView) findViewById(R.id.detailVinAjoutCave);

        List liste = new ArrayList();
        for(int i=1990 ; i<2016; i++) {
            liste.add(i);
        }

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                liste
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        listeMillesime.setAdapter(adapter);

        listeMillesime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                string_millesime = String.valueOf(listeMillesime.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string_millesime = "2016";
            }
        });

        maCave = GestionSauvegarde.getCave();

        int posiBdd;
        Intent i = getIntent();
        endroit = i.getIntExtra(AffichageBdd.ENDROIT, 2);
        posiBdd = i.getIntExtra(AffichageBdd.VIN_BDD, -1);
        Log.i("AffichageAjoutVinCave", "posi " + posiBdd + " et endroit "+endroit);

        if (posiBdd != -1) {
            bdd = GestionSauvegarde.getBdd();
            vinSel = bdd.getVin(posiBdd);
            nomVinSel = bdd.getVin(posiBdd).getNom();
            Log.i("AffichageAjoutVinCave", "dd");

            String string_detail = bdd.getVin(posiBdd).toString();
            detailVin.setText("Détail du vin que vous allez ajouter à la cave : \n- Nom : " + nomVinSel + string_detail);

            //pour ajouter le vin avec les info de l'utilisateur dans la bdd
            ajout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maCave = GestionSauvegarde.getCave();
                    bdd = GestionSauvegarde.getBdd();
                    // on va chercher le vin
                    Log.i("AffichageAjoutVinCave", nomVinSel + " ajouter à la cave !");
                    int nb = Integer.parseInt(nbBouteille.getText().toString());
                    vinSel = new Vin(vinSel, nb, string_millesime);
                    if (maCave.rechercheVin(vinSel) != -1) {
                        Toast.makeText(getApplicationContext(), "Ce vin avec ce millésime a déjà été ajouté à votre cave !!! ",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        maCave.ajoutVin(vinSel); // on ajoute ce vin à la cave
                        GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave sur le tel
                        //Affichage court
                        Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave ! \n" + vinSel.toString(),
                                Toast.LENGTH_SHORT).show();
                        Intent n;
                        if (endroit == 1) {
                            n = new Intent(AffichageAjoutVinCave.this, AffichageCave.class);
                        } else if (endroit == 3) {
                            n = new Intent(AffichageAjoutVinCave.this, AffichagePref.class);
                        } else {
                            n = new Intent(AffichageAjoutVinCave.this, AffichageBdd.class);
                        }
                        n.addCategory(Intent.CATEGORY_HOME);
                        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(n);
                    }
                }
            });
        }
    }

    //Méthode qui permet de mettre un menu à l'écran
    // ce menu est définit dans menu_affichage_ajout_vin_bdd
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_ajout_vin_cave, menu);
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
        // on va dans l'activité AffichageMenuPrincipal
        if (id == R.id.retourMenu) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichageMenuPrincipal.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la cave)
        // on va dans l'activité AffichageCave
        else if (id == R.id.allerCave) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichageCave.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la liste de souhait)
        // on va dans l'activité AffichagePref
        else if (id == R.id.allerPref) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichagePref.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller à la bdd)
        // on va dans l'activité AffichageBdd
        else if (id == R.id.allerBdd) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichageBdd.class);
            n.addCategory(Intent.CATEGORY_HOME);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
