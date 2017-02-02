package ufrpe.edu.learnit.rating.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;

/**
 * Created by joel_ on 02/02/2017.
 */

public class ratingPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public ratingPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }
    public void novaAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil , float avaliacao){
        db=dbHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", idPerfilAvaliador);
        newValues.put("IdItemPerfil",idItemPerfil);
        newValues.put("Avaliacao", avaliacao);
        db.insert("RATE_PERFIL", null, newValues);
        atualizarAvaliacaoPerfil(idItemPerfil, 0, avaliacao);
        inserirAvaliadorPerfil(idItemPerfil);

    }

    public void novaAvaliacaoAula(int idPerfilAvaliador , int idItemAula , float avaliacao){
        db=dbHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", idPerfilAvaliador);
        newValues.put("IdItemAula",idItemAula);
        newValues.put("Avaliacao", avaliacao);
        db.insert("RATE_AULA", null, newValues);
        atualizarAvaliacaoAula(idItemAula, 0, avaliacao);
        inserirAvaliadorAula(idItemAula);
    }

    public void updateAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil , float novaAvaliacao, float antigaAvaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Avaliacao", novaAvaliacao);
        db.update("RATE_PERFIL",newValues,"IdPerfil = ? AND IdItemPerfil = ?",new String[]{idPerfilAvaliador+"" ,idItemPerfil+""});
        atualizarAvaliacaoPerfil(idItemPerfil, antigaAvaliacao, novaAvaliacao);
    }
    public void updateAvaliacaoAula(int idPerfilAvaliador , int idItemAula , float novaAvaliacao, float antigaAvaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Avaliacao", novaAvaliacao);
        db.update("RATE_AULA",newValues,"IdPerfil = ? AND IdItemAula = ?",new String[]{idPerfilAvaliador+"" ,idItemAula+""});
        atualizarAvaliacaoAula(idItemAula, antigaAvaliacao, novaAvaliacao);
    }


    public float retornarAvaliacaoPerfil(int idPerfilAvaliador ,int idItemPerfil){//retorna -1 caso não exista um rating dado para o item
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("RATE_PERFIL", null, "IdPerfil=? AND IdItemPerfil = ?",new String[]{idPerfilAvaliador+"",idItemPerfil+""}, null, null, null);
        float result = -1;
        if (cursor.moveToFirst()){
            result = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        }
        return result;
    }

    public float retornarAvaliacaoAula(int idPerfilAvaliador ,int idItemAula){//retorna -1 caso não exista um rating dado para o item
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("RATE_AULA", null, "IdPerfil=? AND IdItemAula = ?",new String[]{idPerfilAvaliador+"",idItemAula+""}, null, null, null);
        float result = -1;
        if (cursor.moveToFirst()){
            result = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        }
        return result;
    }

    public void atualizarAvaliacaoPerfil(int idPerfil,float oldAvaliacao, float newAvaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        float avaliacao = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        float totalAvalicao= (avaliacao-oldAvaliacao)+newAvaliacao;
        newValues.put("Avaliacao", totalAvalicao);
        db.update("PERFIL",newValues,"IdPerfil=",new String[]{idPerfil+""});
    }


    public void atualizarAvaliacaoAula(int idAula,float oldAvaliacao, float newAvaliacao){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Cursor cursor=db.query("AULAS", null, "Id=?",new String[]{idAula+""}, null, null, null);
        float avaliacao = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        int avaliadores =cursor.getInt(cursor.getColumnIndex("Avaliadores"));
        float totalAvalicao=(avaliacao-oldAvaliacao)+newAvaliacao;
        avaliadores+=1;
        newValues.put("Avaliacao", totalAvalicao);
        newValues.put("Avalidores", avaliadores);
        db.update("AULAS",newValues,"Id=",new String[]{idAula+""});
    }
     public void inserirAvaliadorAula(int idAula){
         db = dbHelper.getWritableDatabase();
         ContentValues newValues = new ContentValues();
         Cursor cursor=db.query("AULAS", null, "Id=?",new String[]{idAula+""}, null, null, null);
         int avaliadores =cursor.getInt(cursor.getColumnIndex("Avaliadores"));
         avaliadores+=1;
         newValues.put("Avalidores", avaliadores);
         db.update("AULAS",newValues,"Id=",new String[]{idAula+""});
     }
    public void inserirAvaliadorPerfil(int idPerfil){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Cursor cursor=db.query("AULAS", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        int avaliadores =cursor.getInt(cursor.getColumnIndex("Avaliadores"));
        avaliadores+=1;
        newValues.put("Avalidores", avaliadores);
        db.update("AULAS",newValues,"Id=",new String[]{idPerfil+""});
    }


}
