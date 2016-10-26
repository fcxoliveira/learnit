package ufrpe.edu.learnit.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.infra.DataBaseHelper;

public class UsuarioPersistencia {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public UsuarioPersistencia(Context iContext){
        context = iContext;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public Usuario inserirUsuario(String login, String senha, String email){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", login);
        newValues.put("PASSWORD",senha);
        newValues.put("EMAIL", email);
        db.insert("USER", null, newValues);
        Usuario usuario = preencherDadosUsuario(login, senha, email);
        db.close();
        return usuario;
    }

    public int excluirUsuario(String UserName){
        db = dbHelper.getWritableDatabase();
        int numeroDeEntradasDeletadas= db.delete("USER", "USERNAME=?", new String[]{UserName});
        db.close();
        return numeroDeEntradasDeletadas;
    }

    public Usuario retornarUsuario(String userName, String password){
        db = dbHelper.getReadableDatabase();
        Usuario usuario;
        Cursor cursor=db.query("USER",new String[]{"*"}, " USERNAME=? and PASSWORD=?", new String[]{userName,password},null ,null, null);
        if(cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
                cursor.close();
                usuario = preencherDadosUsuario(userName, password, email);
            } else {
                usuario = null;
            }
            db.close();
            return usuario;
        }


    public Usuario preencherDadosUsuario(String userName, String password, String email){
        Usuario usuario = new Usuario();
        usuario.setLogin(userName);
        usuario.setSenha(password);
        usuario.setEmail(email);
        return usuario;
    }

    public boolean existeUsuario(String usuario, String email){
        boolean result = true;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("USER", null, " USERNAME=? or EMAIL=?", new String[]{usuario,email}, null, null, null);
        cursor.moveToFirst();
        int contador = cursor.getCount();
        if (contador != 1){
            cursor.close();
            result = false;
        }
        db.close();
        return result;
    }
}