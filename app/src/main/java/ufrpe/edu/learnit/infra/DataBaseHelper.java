package ufrpe.edu.learnit.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "learnit.db";
    private static final int DATABASE_VERSION = 24;
    private static final String TABLE_USER_CREATE = "create table IF NOT EXISTS USER (Id integer primary key autoincrement, Username  text,Password text, Email text);";
    private static final String TABLE_SESSION = "create table IF NOT EXISTS SESSION (LogedUserId integer);";
    private static final String TABLE_PERFIL = "create table IF NOT EXISTS PERFIL (IdPerfil int, Bio text, Nome text, Moedas "+
    "integer, Interesse1 text, Interesse2 text, Avaliacao real, Avaliadores integer, Horas integer);";
    private static final String TABLE_TAGS = "create table IF NOT EXISTS TAGS (Id integer primary key autoincrement, Tag text);";
    private static final String TABLE_AULAS = "create table if not exists AULAS(Id integer primary key autoincrement, Titulo text, Descricao text, Valor int, Tag1 int, Tag2 int,IdPerfil int, Horas int);";
    private static final ArrayList<String> TAGS = new ArrayList<String>(Arrays.asList("--Selecione--","Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"));
    private static final String TABLE_ALUNO_AULA = "create table if not exists ALUNO_AULA(IdPerfil integer, IdAula integer, date text, horas integer, moedas integer);";


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
        db.execSQL(TABLE_ALUNO_AULA);
        for(String tag:TAGS) {
            populateTags(tag,db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + "USER");
        db.execSQL("DROP TABLE IF EXISTS " + "SESSION");
        db.execSQL("DROP TABLE IF EXISTS " + "PERFIL");
        db.execSQL("DROP TABLE IF EXISTS " + "TAGS");
        db.execSQL("DROP TABLE IF EXISTS " + "AULAS");
        db.execSQL("DROP TABLE IF EXISTS " + "ALUNO_AULA");
        onCreate(db);
    }

    public void populateTags(String tag,SQLiteDatabase db){
        String PopulateTags = "INSERT INTO TAGS (Tag) values ('"+ tag +"');";
        db.execSQL(PopulateTags);
    }

}

