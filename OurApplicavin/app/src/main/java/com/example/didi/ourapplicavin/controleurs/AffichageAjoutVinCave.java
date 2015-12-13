package com.example.didi.ourapplicavin.controleurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.didi.ourapplicavin.R;
import com.example.didi.ourapplicavin.modeles.Bdd;
import com.example.didi.ourapplicavin.modeles.Cave;
import com.example.didi.ourapplicavin.modeles.GestionSauvegarde;
import com.example.didi.ourapplicavin.modeles.Vin;

//Classe qui permet à l'utilisateur d'ajouter un vin dans la cave en rensignant le nb de bouteille ainsi que le millesime
public class AffichageAjoutVinCave extends AppCompatActivity {
    // Attributs associé au layout
    private EditText nbBouteille = null; //pour récupérer le nom
    private EditText millesime = null; //pour récupérer la couleur
    private Button ajout = null;

    private String nomVinSel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_ajout_vin_cave);
        //on va cherche tous les élements qui nous interressent dans le layout
        nbBouteille = (EditText) findViewById(R.id.nbBouteilleAjoutCave);
        millesime = (EditText) findViewById(R.id.millesimeAjoutCave);
        ajout = (Button) findViewById(R.id.boutonAjoutCave);

        Intent i = getIntent();
        nomVinSel = i.getStringExtra(AffichageBdd.NOM_VIN);

        //pour ajouter le vin avec les info de l'utilisateur dans la bdd
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cave maCave = GestionSauvegarde.getCave();
                Bdd bdd = GestionSauvegarde.getBdd();
                // on va chercher le vin
                // TODO faire la recherche avec tous les critères pas juste le nom
                Log.i("AffichageAjoutVinCave", nomVinSel + " ajouter à la cave !");
                Vin vin = bdd.rechercheVinParNom(nomVinSel);
                int nb = Integer.parseInt(nbBouteille.getText().toString());
                maCave.ajoutVin(vin, nb); // on ajoute ce vin à la cave
                GestionSauvegarde.enregistrementCave(maCave); //on sauvegarde la cave sur le tel

                //Affichage court
                Toast.makeText(getApplicationContext(), nomVinSel + " a bien été ajouté à la cave !",
                        Toast.LENGTH_SHORT).show();
                Intent n = new Intent(AffichageAjoutVinCave.this, AffichageBdd.class);
                n.addCategory( Intent.CATEGORY_HOME );
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
            }
        });
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
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller dans la liste de souhait)
        // on va dans l'activité AffichagePref
        else if (id == R.id.allerPref) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichagePref.class);
            startActivity(n);
            return true;
        }
        // si on clique sur le sous menu (aller à la bdd)
        // on va dans l'activité AffichageBdd
        else if (id == R.id.allerBdd) {
            Intent n = new Intent(AffichageAjoutVinCave.this, AffichageBdd.class);
            startActivity(n);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
