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

    public void cadastrarPerfil(int id, String bio, String nome){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", id);
        newValues.put("Bio", bio);
        newValues.put("Nome", nome);
        newValues.put("Moedas", 500);
        newValues.put("Avaliadores", 0);
        newValues.put("Avaliacao", 0);
        newValues.put("Horas", 0);
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
            result.setAvaliacao(avaliacao);
            result.setBio(bio);
            result.setMoedas(moedas);
            result.setNome(nome);
            result.setHoras(horas);
            result.setAvaliadores(avaliadores);
            db.close();

        }
        return result;
    }

    public void editarPerfil(int id, String bio, String nome){
        db = dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        String idString = sb.toString();
        ContentValues newValues = new ContentValues();
        newValues.put("Bio",bio);
        newValues.put("Nome",nome);
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }

    public void removerMoedas(int id,int moedas){
        Perfil perfil = retornarPerfil(id);
        moedas = perfil.getMoedas()-moedas;
        String idString = String.valueOf(id);
        ContentValues newValues = new ContentValues();
        newValues.put("Moedas",String.valueOf(moedas));
        db = dbHelper.getReadableDatabase();
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }

    public void addMoeda(int moedas) {
        db = dbHelper.getReadableDatabase();
        String idString = String.valueOf(Session.getUsuario().getID());
        moedas = Session.getUsuario().getPerfil().getMoedas()+moedas;
        ContentValues newValues = new ContentValues();
        newValues.put("Moedas",moedas);
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }
}
