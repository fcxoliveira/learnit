package ufrpe.edu.learnit.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;

public class SessionPersistencia {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public SessionPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int idLogado(){
        int id=0;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("SESSION", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
        id = cursor.getInt(cursor.getColumnIndex("LOGED_USER_ID"));
        }
        db.close();
        return id;
    }

    public void cadastrarIdLogado(int id){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("LOGED_USER_ID", id);
        db.insert("SESSION", null, newValues);
        db.close();
    }

    public int excluirUsuarioLogado(int Id){
        db = dbHelper.getWritableDatabase();
        int numeroDeEntradasDeletadas = db.delete("SESSION", null, null);
        db.close();
        return numeroDeEntradasDeletadas;
    }
}
