package ufrpe.edu.learnit.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;

public class SessionPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public SessionPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);
        db = dbHelper.getWritableDatabase();
    }

    public int idLogado(){
        int id=0;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SESSION", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
        id = cursor.getInt(cursor.getColumnIndex("LogedUserId"));
        }
        db.close();
        return id;
    }

    public void cadastrarIdLogado(int id){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("LogedUserId", id);
        db.insert("SESSION", null, newValues);
        db.close();
    }

    public int excluirUsuarioLogado(int Id){
        db = dbHelper.getWritableDatabase();
        int numeroDeEntradasDeletadas = db.delete("SESSION", null, null);
        db.close();
        return numeroDeEntradasDeletadas;
    }

    public void newRating(int idPerfil ,int idItem ,float rate){
        db=dbHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", idPerfil);
        newValues.put("IdItem",idItem);
        newValues.put("Rating", rate);
        db.insert("RATE", null, newValues);
    }

    public void updateRating(int idPerfil ,int idItem ,float rate){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", idPerfil);
        newValues.put("IdItem",idItem);
        newValues.put("Rating", rate);
        db.update("RATE",newValues,"IdPerfil = ? AND IdItem = ?",new String[]{idPerfil+"" ,idItem+""});
    }
    public float retornarRating(int idPerfil ,int idItem ,float rate){//retorna -1 caso não exista um rating dado para o item
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("RATE", null, "IdPerfil=? AND IdItem = ?",new String[]{idPerfil+"",idItem+""}, null, null, null);
        float result = -1;
        if (cursor.moveToFirst()){
            result = cursor.getFloat(cursor.getColumnIndex("Rating"));
        }
        return result;
    }
}
