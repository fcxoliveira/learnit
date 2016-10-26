package ufrpe.edu.learnit.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
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
        db.close();
    }

    public Perfil retornarPerfil(int id){
        Perfil result = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("PERFIL", null, "ID_PERFIL=?", new String[]{""+id+""}, null, null, null);
        if (!cursor.moveToFirst()){
            result = null;
            db.close();
        }else{
            String bio = cursor.getString(cursor.getColumnIndex("BIO"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            int moedas = cursor.getInt(cursor.getColumnIndex("MOEDAS"));
            int avaliadores = cursor.getInt(cursor.getColumnIndex("AVALIADORES"));
            float avaliacao = cursor.getFloat(cursor.getColumnIndex("AVALIACAO"));
            int horas = cursor.getInt(cursor.getColumnIndex("HORAS"));
            String interesse1= cursor.getString(cursor.getColumnIndex("INTERESSE1"));
            String interesse2= cursor.getString(cursor.getColumnIndex("INTERESSE2"));
            String interesse3= cursor.getString(cursor.getColumnIndex("INTERESSE3"));
            String interesse4= cursor.getString(cursor.getColumnIndex("INTERESSE4"));
            String interesse5= cursor.getString(cursor.getColumnIndex("INTERESSE5"));
            prepararPerfil(result, bio, nome, moedas, avaliacao, interesse1, interesse2, interesse3, interesse4, interesse5,horas,avaliadores);
            db.close();
        }
        return result;
    }

    private void prepararPerfil(Perfil result, String bio, String nome, int moedas, float avaliacao, String interesse1, String interesse2, String interesse3, String interesse4, String interesse5,int horas, int avaliadores) {
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
    }


}
