package ufrpe.edu.learnit.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "learnit.db";
    private static final int DATABASE_VERSION = 30;
    private static final String TABLE_USER_CREATE = "create table IF NOT EXISTS USER (Id integer primary key autoincrement, Username  text,Password text, Email text);";
    private static final String TABLE_SESSION = "create table IF NOT EXISTS SESSION (LogedUserId integer);";
    private static final String TABLE_PERFIL = "create table IF NOT EXISTS PERFIL (IdPerfil int, Bio text, Nome text, Moedas integer, Avaliacao real, Avaliadores integer, Horas integer);";
    private static final String TABLE_TAGS = "create table IF NOT EXISTS TAGS (Id integer primary key autoincrement, Tag text);";
    private static final String TABLE_AULAS = "create table if not exists AULAS(Id integer primary key autoincrement, Titulo text, Descricao text, Valor integer, IdPerfil integer, HorasDisponiveis integer);";
    private static final ArrayList<String> TAGS = new ArrayList<>(Arrays.asList("Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"));
    private static final String TABLE_ALUNO_AULA = "create table if not exists ALUNO_AULA(IdPerfilAluno integer, IdAula integer, Date text, HorasCompradas integer, ValorPago integer, HorasConfirmadas integer);";
    private static final String TABLE_CONFIRMACAO = "create table if not exists CONFIRMACAO(Id integer primary key autoincrement,IdAula integer,IdAluno integer, HorasParaConfirmar integer, Status integer);";
    private static final String TABLE_AULA_TAG = "create table if not exists AULA_TAG(IdAula integer,IdTag integer);";
    private static final String TABLE_PERFIL_TAG="create table if not exists PERFIL_TAG(IdPerfil integer,IdTag integer);";

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
        db.execSQL(TABLE_AULA_TAG);
        db.execSQL(TABLE_PERFIL_TAG);
        db.execSQL(TABLE_CONFIRMACAO);
        for(String tag:TAGS) {
            populateTags(tag,db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS SESSION");
        db.execSQL("DROP TABLE IF EXISTS PERFIL");
        db.execSQL("DROP TABLE IF EXISTS TAGS");
        db.execSQL("DROP TABLE IF EXISTS AULAS");
        db.execSQL("DROP TABLE IF EXISTS ALUNO_AULA");
        db.execSQL("DROP TABLE IF EXISTS AULA_TAG");
        db.execSQL("DROP TABLE IF EXISTS PERFIL_TAG");
        db.execSQL("DROP TABLE IF EXISTS CONFIRMACAO");
        onCreate(db);
    }

    private void populateTags(String tag, SQLiteDatabase db){
        String PopulateTags = "INSERT INTO TAGS (Tag) values ('"+ tag +"');";
        db.execSQL(PopulateTags);
    }

}

