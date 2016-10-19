package ufrpe.edu.learnit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import ufrpe.edu.learnit.DataBaseHelper;

/**
 * Created by Filipe on 19/10/2016.
 */
public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String DATABASE_CREATE = "create table LOGIN (ID integer primary key autoincrement, USERNAME  text,PASSWORD text, EMAIL text);";


    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context iContext){
        context = iContext;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  LoginDataBaseAdapter open() throws SQLException
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

    public void insertEntry(String userName,String password, String email)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=? or EMAIL=?", new String[]{userName,email}, null, null, null);
        if(cursor.getCount()==1){
            cursor.close();
            Toast.makeText(context, "Usuario ou email não disponiveis", Toast.LENGTH_LONG).show();
            return;
        }
        cursor.moveToFirst();
        cursor.close();
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);
        newValues.put("EMAIL", email);
        db.insert("LOGIN", null, newValues);
        Toast.makeText(context, "Bem vindo "+userName+" voce foi registrado com sucesso", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        Toast.makeText(context, "Sua conta foi deletada com sucesso", Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public ArrayList<String> getUser(String userName, String password)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=? and PASSWORD=?", new String[]{userName,password}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            Toast.makeText(context, "Usuario ou senha incorretos", Toast.LENGTH_LONG).show();
            return null;
        }
        cursor.moveToFirst();
        String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        ArrayList<String> userData = new ArrayList<String>();
        userData.add(userName);
        userData.add(password);
        userData.add(email);
        return userData;
    }
}
