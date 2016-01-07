package com.example.didi.ourapplicavin.modeles;

import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by didi on 12/12/2015.
 */
public class GestionSauvegarde {

    public static void enregistrementCave(Cave cave) {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichier = new FileOutputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/AppliCavin/maCave.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(cave);
            Log.i("GestionSauvegarde", "enregistrement de la cave; serialization");
            oos.flush(); //vider le tampon
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

    public static Cave getCave() {
        Cave cave = null;
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) +"/AppliCavin/maCave.ser");
            ois = new ObjectInputStream(fichier);
            final Cave cave2 = (Cave) ois.readObject();
            Log.i("AffichageCave", "récupération de la cave avec getCave; deserialization");
            cave = cave2;
        } catch (final IOException | ClassNotFoundException e) {
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

    public static void enregistrementBdd(Bdd bdd) {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichier = new FileOutputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/AppliCavin/bdd.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(bdd);
            Log.i("GestionSauvegarde", "enregistrement de la bdd; serialization");
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

    public static Bdd getBdd() {
        Bdd bdd = null;
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) +"/AppliCavin/bdd.ser");
            ois = new ObjectInputStream(fichier);
            final Bdd bdd2 = (Bdd) ois.readObject();
            Log.i("GestionSauvegarde", "récupération de la bdd; deserialization");
            bdd = bdd2;
        } catch (final IOException | ClassNotFoundException e) {
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
        return bdd;
    }

    public static void enregistrementPref(ListePref pref) {
        ObjectOutputStream oos = null;
        try {
            final FileOutputStream fichier = new FileOutputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/AppliCavin/pref.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(pref);
            Log.i("GestionSauvegarde", "enregistrement de la pref; serialization");
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

    public static ListePref getPref() {
        ListePref pref = null;
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) +"/AppliCavin/pref.ser");
            ois = new ObjectInputStream(fichier);
            final ListePref pref2 = (ListePref) ois.readObject();
            Log.i("GestionSauvegarde", "récupération de la pref; deserialization");
            pref = pref2;
        } catch (final IOException | ClassNotFoundException e) {
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
        return pref;
    }

}
