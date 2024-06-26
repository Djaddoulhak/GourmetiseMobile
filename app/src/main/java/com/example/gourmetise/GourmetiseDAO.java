package com.example.gourmetise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class GourmetiseDAO<Boulangerie> {
    private SQLiteDatabase maBase;
    private GourmetiseHelper monHelper;
    public GourmetiseDAO(Context context){
        monHelper = new GourmetiseHelper(context);
        maBase = monHelper.getWritableDatabase();
    }



    public Cursor LesBoulangeries() {
        Cursor curseurContact = maBase.rawQuery("SELECT siren, nom, rue, ville, code_postal,descriptif from Boulangerie " , new String[] {});
        return curseurContact;
    }


    public void ajouterBoulangerie(com.example.gourmetise.Boulangerie uneBoulangerie) {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("siren", uneBoulangerie.getSiren());
        v.put("nom", uneBoulangerie.getNom());
        v.put("rue", uneBoulangerie.getRue());
        v.put("ville", uneBoulangerie.getVille());
        v.put("code_postal", uneBoulangerie.getCode_postal());
        v.put("descriptif", uneBoulangerie.getDescriptif());
        maBase.insert("Boulangerie", null, v);

    }


    public boolean ajouterEvaluation (Evaluation uneEvaluation ) {

        // Vérifier si le code unique est déjà utilisé
        if (isCodeUniqueUsed(uneEvaluation.getCodeUnique())) {
            return false;
        }

        // Création d'un ContentValues pour ajouter les valeurs
        ContentValues v = new ContentValues();

        v.put("code_unique", (String) uneEvaluation.getCodeUnique());
        v.put("date_evaluation", (String) uneEvaluation.getDateEvaluation());
        v.put("note_critere1",  uneEvaluation.getNoteCritere1());
        v.put("note_critere2",  uneEvaluation.getNoteCritere2());
        v.put("note_critere3",  uneEvaluation.getNoteCritere3());

        long resultat = maBase.insert("Evaluation", null, v);

        return resultat != -1;
    }

    //Vérifie si le code unique est deja utilisée
    public boolean isCodeUniqueUsed(String codeUnique) {
        Cursor cursor = maBase.rawQuery("SELECT 1 FROM Evaluation WHERE code_unique = ?", new String[]{codeUnique});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    public void supprimerTous() {
        maBase.delete("Boulangerie",null,null);
    }

    public void supprimerToutesEvaluations() {
        maBase.delete("Evaluation", null, null);
    }


}

