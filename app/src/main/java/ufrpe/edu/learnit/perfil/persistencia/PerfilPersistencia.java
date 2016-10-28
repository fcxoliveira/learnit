package ufrpe.edu.learnit.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class PerfilPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public PerfilPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);
    }

    public void cadastrarPerfil(int id, String bio, String nome, String interesse1, String interesse2, String interesse3, String interesse4, String interesse5){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", id);
        newValues.put("Bio", bio);
        newValues.put("Nome", nome);
        newValues.put("Moedas", 500);
        newValues.put("Avaliadores", 0);
        newValues.put("Avaliacao", 0);
        newValues.put("Horas", 0);
        newValues.put("Interesse1",interesse1);
        newValues.put("Interesse2",interesse2);
        newValues.put("Interesse3",interesse3);
        newValues.put("Interesse4",interesse4);
        newValues.put("Interesse5",interesse5);
        db.insert("PERFIL", null, newValues);
        db.close();
    }

    public Perfil retornarPerfil(int id){
        Perfil result = new Perfil();
        db = dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        String idString = sb.toString();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{idString}, null, null, null);
        if (!cursor.moveToFirst()){
            result = null;
            db.close();
        }else{
            String bio = cursor.getString(cursor.getColumnIndex("Bio"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            int moedas = cursor.getInt(cursor.getColumnIndex("Moedas"));
            int avaliadores = cursor.getInt(cursor.getColumnIndex("Avaliadores"));
            float avaliacao = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
            int horas = cursor.getInt(cursor.getColumnIndex("Horas"));
            String interesse1= cursor.getString(cursor.getColumnIndex("Interesse1"));
            String interesse2= cursor.getString(cursor.getColumnIndex("Interesse2"));
            String interesse3= cursor.getString(cursor.getColumnIndex("Interesse3"));
            String interesse4= cursor.getString(cursor.getColumnIndex("Interesse4"));
            String interesse5= cursor.getString(cursor.getColumnIndex("Interesse5"));
            result.setAvaliacao(avaliacao);
            result.setBio(bio);
            result.setMoedas(moedas);
            result.setNome(nome);
            ArrayList<String> interesses = new ArrayList<String>();
            interesses.add(interesse1);
            interesses.add(interesse2);
            interesses.add(interesse3);
            interesses.add(interesse4);
            interesses.add(interesse5);
            result.setInteresses(interesses);
            result.setHoras(horas);
            result.setAvaliadores(avaliadores);
            db.close();
        }
        return result;
    }

}
