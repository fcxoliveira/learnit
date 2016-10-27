package ufrpe.edu.learnit.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ufrpe.edu.learnit.usuario.persistencia.UsuarioPersistencia;



public class DataBaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "learnit.db";
    static final int DATABASE_VERSION = 2;
    private static final String TABLE_USER_CREATE = "create table IF NOT EXISTS USER (Id integer primary key autoincrement, Username  text,Password text, Email text);";
    private static final String TABLE_SESSION = "create table IF NOT EXISTS SESSION (LogedUserId integer);";
    private static final String TABLE_PERFIL = "create table IF NOT EXISTS PERFIL (IdPerfil int, Bio text, Nome text, Moedas "+
    "integer, Interesse1 text, Interesse2 text, Avaliacao real, Avaliadores integer, Horas integer);";
    private static final String TABLE_TAGS = "create table IF NOT EXISTS TAGS (Id integer primary key autoincrement, Tag text);";
    private static final String TABLE_AULAS = "create table if not exists AULAS(Id integer primary key autoincrement, Titulo text, Descricao text, Valor int, Tag1 int, Tag2 int,IdPerfil int, Horas int);";

    public DataBaseHelper(Context context , SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_SESSION);
        db.execSQL(TABLE_PERFIL);
        db.execSQL(TABLE_TAGS);
        db.execSQL(TABLE_AULAS);
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

