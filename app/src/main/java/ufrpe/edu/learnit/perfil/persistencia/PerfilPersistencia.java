package ufrpe.edu.learnit.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.usuario.dominio.Usuario;

public class PerfilPersistencia {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public PerfilPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void cadastrarPerfil(int id, String bio, String nome, String interesse1, String interesse2, String interesse3, String interesse4, String interesse5){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("ID_PERFIL", id);
        newValues.put("BIO", bio);
        newValues.put("NOME", nome);
        newValues.put("MOEDAS", 500);
        newValues.put("AVALIADORES", 0);
        newValues.put("AVALIACAO", 0);
        newValues.put("HORAS", 0);
        newValues.put("INTERESSE1",interesse1);
        newValues.put("INTERESSE2",interesse2);
        newValues.put("INTERESSE3",interesse3);
        newValues.put("INTERESSE4",interesse4);
        newValues.put("INTERESSE5",interesse5);
        db.insert("PERFIL", null, newValues);
    }


}
