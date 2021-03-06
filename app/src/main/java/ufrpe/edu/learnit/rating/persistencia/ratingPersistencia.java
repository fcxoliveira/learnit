package ufrpe.edu.learnit.rating.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;

public class RatingPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public RatingPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }
    public void novaAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil , float avaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", idPerfilAvaliador);
        newValues.put("IdItemPerfil",idItemPerfil);
        newValues.put("Avaliacao", avaliacao);
        db.insert("RATE_PERFIL", null, newValues);
        atualizarAvaliacaoPerfil(idItemPerfil, 0, avaliacao);
        inserirAvaliadorPerfil(idItemPerfil);

    }

    public void updateAvaliacaoPerfil(int idPerfilAvaliador , int idItemPerfil , float novaAvaliacao, float antigaAvaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Avaliacao", novaAvaliacao);
        db.update("RATE_PERFIL",newValues,"IdPerfil = ? AND IdItemPerfil = ?",new String[]{idPerfilAvaliador+"" ,idItemPerfil+""});
        atualizarAvaliacaoPerfil(idItemPerfil, antigaAvaliacao, novaAvaliacao);

    }

    public float retornarAvaliacaoPerfil(int idPerfilAvaliador ,int idItemPerfil){//retorna -1 caso não exista um rating dado para o item
        db=dbHelper.getReadableDatabase();
        String idItemPerfilString = idItemPerfil+"";
        String idPerfilString = idPerfilAvaliador+"";
        Cursor cursor=db.query("RATE_PERFIL", new String[]{"*"}, "IdPerfil='"+idPerfilString+"' AND IdItemPerfil='"+idItemPerfilString+"'",null, null, null, null);
        float result = 0;
        if (cursor.moveToFirst()){
            result = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        }
        return result;
    }


    public void atualizarAvaliacaoPerfil(int idPerfil,float oldAvaliacao, float newAvaliacao){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        if(cursor.moveToFirst()){
            float avaliacao = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
            float totalAvalicao= (avaliacao-oldAvaliacao)+newAvaliacao;
            newValues.put("Avaliacao", totalAvalicao);
            db.update("PERFIL",newValues,"IdPerfil=?",new String[]{idPerfil+""});
        }

    }

    public void inserirAvaliadorPerfil(int idPerfil){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        cursor.moveToFirst();
        int avaliadores =cursor.getInt(cursor.getColumnIndex("Avaliadores"));
        avaliadores+=1;
        newValues.put("Avaliadores", avaliadores);
        db.update("PERFIL",newValues,"IdPerfil=?",new String[]{idPerfil+""});
    }

    public ArrayList<Integer> retornarTodasAvaliacoesPerfil(int idPerfil){
        db = dbHelper.getWritableDatabase();
        ArrayList<Integer> perfis = new ArrayList<>();
        Cursor cursor=db.query("RATE_PERFIL", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        while (cursor.moveToNext()){
            int idColumItemAula = cursor.getColumnIndex("IdItemPerfil");
            int idItemAula = cursor.getInt(idColumItemAula);
            perfis.add(idItemAula);
        }
        return perfis;
    }


}
