package ufrpe.edu.learnit.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.persistencia.TagPersistencia;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class PerfilPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public PerfilPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);
    }

    public void cadastrarPerfil(int id, String bio, String nome, Tag interesse1, Tag interesse2){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", id);
        newValues.put("Bio", bio);
        newValues.put("Nome", nome);
        newValues.put("Moedas", 500);
        newValues.put("Avaliadores", 0);
        newValues.put("Avaliacao", 0);
        newValues.put("Horas", 0);
        newValues.put("Interesse1", interesse1.getID());
        newValues.put("Interesse2", interesse2.getID());
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
            int interesse1= cursor.getInt(cursor.getColumnIndex("Interesse1"));
            int interesse2= cursor.getInt(cursor.getColumnIndex("Interesse2"));
            result.setAvaliacao(avaliacao);
            result.setBio(bio);
            result.setMoedas(moedas);
            result.setNome(nome);
            ArrayList<Tag> interesses = new ArrayList<Tag>();
            TagPersistencia tagPersistencia = new TagPersistencia();
            Tag tag1 = tagPersistencia.retornarTag(interesse1);
            Tag tag2 = tagPersistencia.retornarTag(interesse2);
            interesses.add(tag1);
            interesses.add(tag2);
            result.setInteresses(interesses);
            result.setHoras(horas);
            result.setAvaliadores(avaliadores);
            db.close();

        }
        return result;
    }

    public void editarPerfil(int id, String bio, String nome, Tag interesse1, Tag interesse2){
        db = dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        String idString = sb.toString();
        ContentValues newValues = new ContentValues();
        newValues.put("Bio",bio);
        newValues.put("Nome",nome);
        newValues.put("Interesse1",interesse1.getID());
        newValues.put("Interesse2",interesse2.getID());
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }

    public void removerMoedas(int id,int moedas){
        db = dbHelper.getReadableDatabase();
        Perfil perfil = retornarPerfil(id);
        moedas = perfil.getMoedas()-moedas;
        String idString = String.valueOf(id);
        ContentValues newValues = new ContentValues();
        newValues.put("Moedas",moedas);
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }
}
