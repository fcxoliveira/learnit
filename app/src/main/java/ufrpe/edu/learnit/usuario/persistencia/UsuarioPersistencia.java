package ufrpe.edu.learnit.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;
import ufrpe.edu.learnit.usuario.dominio.Usuario;
import ufrpe.edu.learnit.infra.DataBaseHelper;

public class UsuarioPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public UsuarioPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public Usuario retornarUsuarioPorEmail(String email){
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("USER",new String[]{"*"}, "Email=?", new String[]{email},null ,null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("Id"));
            String login = cursor.getString(cursor.getColumnIndex("Username"));
            String senha = cursor.getString(cursor.getColumnIndex("Password"));
            Usuario usuario = preencherDadosUsuario(login, senha, email);
            usuario.setID(id);
            return usuario;
        }
        return null;
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Username", login);
        newValues.put("Password",senha);
        newValues.put("Email", email);
        db.insert("USER", null, newValues);
        Usuario usuario = preencherDadosUsuario(login, senha, email);
        Cursor cursor=db.query("USER",new String[]{"*"}, "Username=?", new String[]{login},null ,null, null);
        cursor.moveToFirst();
        int ID = cursor.getInt(cursor.getColumnIndex("Id"));
        usuario.setID(ID);
        cursor.close();
        db.close();
        cursor.close();
        return usuario;
    }

    public int excluirUsuario(String login){
        db = dbHelper.getWritableDatabase();
        int numeroDeEntradasDeletadas= db.delete("USER", "Username=?", new String[]{login});
        db.close();
        return numeroDeEntradasDeletadas;
    }


    public Usuario retornarUsuario(String userName, String password){
        db = dbHelper.getReadableDatabase();
        Usuario usuario;
        Cursor cursor=db.query("USER",new String[]{"*"}, "Username=? and Password=?", new String[]{userName,password},null ,null, null);
        if(cursor.moveToFirst()) {
            int ID = cursor.getInt(cursor.getColumnIndex("Id"));
            String email = cursor.getString(cursor.getColumnIndex("Email"));
            cursor.close();
            usuario = preencherDadosUsuario(userName, password, email);
            usuario.setID(ID);
            } else {
                usuario = null;
            }
            db.close();
            return usuario;
        }


    private Usuario preencherDadosUsuario(String userName, String password, String email){
        Usuario usuario = new Usuario();
        usuario.setLogin(userName);
        usuario.setSenha(password);
        usuario.setEmail(email);
        return usuario;
    }

    public boolean existeUsuario(String usuario, String email){
        boolean result = true;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("USER", null, "Username=? or Email=?", new String[]{usuario,email}, null, null, null);
        if(!cursor.moveToFirst()){
            cursor.close();
            result = false;
        }
        db.close();
        return result;
    }

    public Usuario retornarUsuario(int ID){
        db = dbHelper.getReadableDatabase();
        Usuario usuario;
        Cursor cursor=db.query("USER",new String[]{"*"}, "Id=?", new String[]{""+ID},null ,null, null);
        if(cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndex("Email"));
            String userName = cursor.getString(cursor.getColumnIndex("Username"));
            String password = cursor.getString(cursor.getColumnIndex("Password"));
            cursor.close();
            usuario = preencherDadosUsuario(userName, password, email);
            PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
            usuario.setPerfil(perfilPersistencia.retornarPerfil(ID));
            usuario.setID(ID);
        } else {
            usuario = null;
        }
        db.close();
        return usuario;
    }
}