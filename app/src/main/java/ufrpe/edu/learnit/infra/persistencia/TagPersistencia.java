package ufrpe.edu.learnit.infra.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;

/**
 * Created by joel_ on 28/10/2016.
 */

public class TagPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;


    public TagPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public ArrayList<Tag> retornarTodasTags(){
        db=dbHelper.getReadableDatabase();
        ArrayList<Tag> tags = new ArrayList<>();
        Cursor cursor=db.query("TAGS",new String[]{"*"}, null, null,null ,null, null);
        while (cursor.moveToNext()){
            Tag tag = new Tag();
            tag.setID(cursor.getInt(cursor.getColumnIndex("Id")));
            tag.setTitulo(cursor.getString(cursor.getColumnIndex("Tag")));
            tags.add(tag);
        }
        db.close();
        return tags;
    }

    public Tag retornarTag(int id){
        db=dbHelper.getReadableDatabase();
        Tag tag = new Tag();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        Cursor cursor=db.query("TAGS",new String[]{"*"},"Id = ?",new String[]{sb.toString()},null ,null, null);
        cursor.moveToFirst();
        db.close();
        return tag;
    }
}
