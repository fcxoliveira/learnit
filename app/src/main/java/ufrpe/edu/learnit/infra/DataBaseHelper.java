package ufrpe.edu.learnit.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static android.R.attr.version;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "learnit.db";
    private static final int DATABASE_VERSION = 39;
    private static final String TABLE_USER_CREATE = "create table IF NOT EXISTS USER (Id integer primary key autoincrement, Username  text,Password text, Email text);";
    private static final String TABLE_SESSION = "create table IF NOT EXISTS SESSION (LogedUserId integer);";
    private static final String TABLE_PERFIL = "create table IF NOT EXISTS PERFIL (IdPerfil int, Bio text, Nome text, Moedas integer, Avaliacao real, Avaliadores integer, Horas integer);";
    private static final String TABLE_TAGS = "create table IF NOT EXISTS TAGS (Id integer primary key autoincrement, Tag text);";
    private static final String TABLE_AULAS = "create table if not exists AULAS   (Id integer primary key autoincrement, Titulo text, Descricao text, Valor integer, IdPerfil integer, HorasDisponiveis integer, Avaliacao real, Avaliadores integer);";
    private static final ArrayList<String> TAGS = new ArrayList<>(Arrays.asList("Informatica", "Musica", "Portugues", "Matematica", "Biologia", "Fisica", "Quimica", "Ed. Fisica"));
    private static final String TABLE_ALUNO_AULA = "create table if not exists ALUNO_AULA(Id integer primary key autoincrement, IdPerfilAluno integer, IdAula integer, Date text, HorasCompradas integer, ValorPago integer, HorasConfirmadas integer);";
    private static final String TABLE_CONFIRMACAO = "create table if not exists CONFIRMACAO(Id integer primary key autoincrement,IdAula integer,IdAluno integer, HorasParaConfirmar integer, Status integer);";
    private static final String TABLE_AULA_TAG = "create table if not exists AULA_TAG(IdAula integer,IdTag integer);";
    private static final String TABLE_PERFIL_TAG="create table if not exists PERFIL_TAG(IdPerfil integer,IdTag integer);";
    private static final String TABLE_ALUNO_TAG_RECOMENDACAO="create table if not exists ALUNO_TAG_RECOMENDACAO(IdPerfil integer, IdTag integer, Valor integer);";
    private static final String TABLE_RATE_PERFIL="create table if not exists RATE_PERFIL(IdPerfil integer, IdItemPerfil integer, Avaliacao real);";
    private static final String TABLE_RATE_AULA="create table if not exists RATE_AULA(IdPerfil integer, IdItemAula integer, Avaliacao real);";

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
        db.execSQL(TABLE_ALUNO_TAG_RECOMENDACAO);
        db.execSQL(TABLE_RATE_PERFIL);
        db.execSQL(TABLE_RATE_AULA);
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
        db.execSQL("DROP TABLE IF EXISTS TABLE_ALUNO_TAG_RECOMENDACAO");
        db.execSQL("DROP TABLE IF EXISTS RATE_PERFIL");
        db.execSQL("DROP TABLE IF EXISTS RATE_AULA");
        onCreate(db);

    }

    private void populateTags(String tag, SQLiteDatabase db){
        String PopulateTags = "INSERT INTO TAGS (Tag) values ('"+ tag +"');";
        db.execSQL(PopulateTags);
    }
    private void populateUser(String usuario, String password, String email,SQLiteDatabase db){
        String PopulateUser="INSERT INTO USER (Username, Password, Email) VALUES ('"+usuario+"','"+password+"','"+email+"');";
        db.execSQL(PopulateUser);
    }
    private void populatePerfil(int IdPerfil, String Bio, String Nome, int  Moedas, float Avaliacao, int Avaliadores, int Horas,SQLiteDatabase db){
        String PopulatePerfil ="INSERT INTO PERFIL (IdPerfil, Bio, Nome, Moedas, Avaliacao, Avaliadores, Horas) VALUES ('"+IdPerfil+"','"+Bio+"','"+Nome+"','"+Moedas+"','"+Avaliacao+"','"+Avaliadores+"','"+Horas+"');";
        db.execSQL(PopulatePerfil);
    }
    private void populateAulas(String Titulo, String Descricao, int Valor, int IdPerfil, int HorasDisponiveis, float Avaliacao, int Avaliadores){
        String PopulateAulas ="INSERT INTO AULAS (Titulo, Descricao, Valor, IdPerfil, HorasDisponiveis, Avaliacao, Avaliadores) VALUES('"+Titulo+"','"+Descricao+"','"+Valor+"','"+IdPerfil+"','"+HorasDisponiveis+"','"+Avaliacao+"','"+Avaliadores+"');";
    }
    private void populateAlunoAula(int IdPerfilAluno, int IdAula,String Date,int HorasCompradas,int ValorPago,int HorasConfirmadas){
        String PopulateAlunoAula="INSERT INTO ALUNO_AULA (IdPerfilAluno, IdAula, Date, HorasCompradas, ValorPago, HorasConfirmadas) VALUES('"+IdPerfilAluno+"','"+IdAula+"','"+Date+"','"+HorasCompradas+"','"+ValorPago+"','"+HorasConfirmadas+"');";

    }
    private void populateAulaTag(){

    }
}

