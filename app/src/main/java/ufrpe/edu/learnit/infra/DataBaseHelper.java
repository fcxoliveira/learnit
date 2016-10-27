package ufrpe.edu.learnit.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;



public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_USER_CREATE = "create table IF NOT EXISTS USER (ID integer primary key autoincrement, USERNAME  text,PASSWORD text, EMAIL text);";
    public static final String TABLE_SESSION = "create table IF NOT EXISTS SESSION (LOGED_USER_ID integer);";
    public static final String TABLE_PERFIL = "create table IF NOT EXISTS PERFIL (ID_PERFIL int, BIO text, NOME text, MOEDAS "+
    "integer, INTERESSE1 text, INTERESSE2 text, AVALIACAO real, AVALIADORES integer, HORAS integer);";

    public DataBaseHelper(Context context , String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_SESSION);
        db.execSQL(TABLE_PERFIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(db);
    }

}

