package ufrpe.edu.learnit.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ufrpe.edu.learnit.dominio.Usuario;
import ufrpe.edu.learnit.infra.DataBaseHelper;

public class UsuarioPersistencia {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String DATABASE_CREATE = "create table USER (ID integer primary key autoincrement, USERNAME  text,PASSWORD text, EMAIL text);";
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;


    public UsuarioPersistencia(Context iContext){
        context = iContext;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public UsuarioPersistencia open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public Usuario inserirUsuario(String login, String senha, String email)
    {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", login);
        newValues.put("PASSWORD",senha);
        newValues.put("EMAIL", email);
        db.insert("USER", null, newValues);
        Usuario usuario = preencherDadosUsuario(login, senha, email);
        return usuario;
    }
    public int excluirUsuario(String UserName)
    {
        String where="USERNAME=?";
        int numeroDeEntradasDeletadas= db.delete("USER", where, new String[]{UserName}) ;
        return numeroDeEntradasDeletadas;
    }

    public Usuario retornarUsuario(String userName, String password)
    {
        Cursor cursor=db.query("USER", null, " USERNAME=? and PASSWORD=?", new String[]{userName,password}, null, null, null);
        int contador = cursor.getCount();
        if(contador==1){
            int idEmail = cursor.getColumnIndex("EMAIL");
            String email = cursor.getString(idEmail);
            cursor.close();
            Usuario usuario = preencherDadosUsuario(userName, password, email);
            return usuario;
        }else{
            return null;
        }

    }

    public Usuario preencherDadosUsuario(String userName, String password, String email) {
        Usuario usuario = new Usuario();
        usuario.setLogin(userName);
        usuario.setSenha(password);
        usuario.setEmail(email);
        return usuario;
    }

    public boolean existeUsuario(String usuario){
        Cursor cursor=db.query("USER", null, " USERNAME=?", new String[]{usuario}, null, null, null);
            int contador = cursor.getCount();
            if(contador==1){
                return true;
            }else{
                return false;
            }
    }

}
