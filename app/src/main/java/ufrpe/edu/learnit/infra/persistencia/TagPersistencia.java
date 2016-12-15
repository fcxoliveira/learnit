package ufrpe.edu.learnit.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.infra.dominio.Tag;

public class TagPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;


    public TagPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);
    }

    public ArrayList<Tag> retornarTagsAula(int idAula){
        db=dbHelper.getReadableDatabase();
        ArrayList<Tag> tagsAula= new ArrayList<>();
        Cursor cursor=db.query("AULA_TAG",new String[]{"*"}, "IdAula=?",new String[] {idAula+""},null ,null, null);
        while (cursor.moveToNext()){
            tagsAula.add(retornarTag(cursor.getInt(cursor.getColumnIndex("IdTag"))));
        }
        cursor.close();
        db.close();
        return tagsAula;

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
        cursor.close();
        db.close();
        return tags;
    }

    private Tag retornarTag(int id){
        db=dbHelper.getReadableDatabase();
        Tag tag = new Tag();
        Cursor cursor=db.query("TAGS",new String[]{"*"},"Id=?",new String[]{id+""},null ,null, null);
        tag.setID(cursor.getInt(cursor.getColumnIndex("Id")));
        tag.setTitulo(cursor.getString(cursor.getColumnIndex("Tag")));
        cursor.close();
        db.close();
        return tag;
    }

    public ArrayList<Tag> retornarTagPorTexto(String texto){
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("TAGS",new String[]{"*"},"Tag LIKE ?",new String[] {"%"+texto+"%"},null ,null, null);
        ArrayList<Tag> tags = new ArrayList<>();
        while (cursor.moveToNext()){
            tags.add(preencherTag(cursor.getInt(cursor.getColumnIndex("Id")),cursor.getString(cursor.getColumnIndex("Tag"))));
        }
        cursor.close();
        db.close();
        return tags;
    }

    private Tag preencherTag(int id,String nome){
        Tag tag = new Tag();
        tag.setID(id);
        tag.setTitulo(nome);
        return tag;
    }

    public Tag retornarTagTexto(String texto){
        db=dbHelper.getReadableDatabase();
        Tag tag = new Tag();
        Cursor cursor=db.query("TAGS",new String[]{"*"},"Tag=?",new String[]{texto},null ,null, null);
        if (cursor.moveToFirst()) {
            tag.setID(cursor.getInt(cursor.getColumnIndex("Id")));
            tag.setTitulo(cursor.getString(cursor.getColumnIndex("Tag")));
            cursor.close();
            db.close();
        }
        return tag;
    }

    public void inserirTag(String texto){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Tag",texto);
        db.insert("TAGS",null,newValues);
        db.close();
    }

    public boolean existeRealcaoTagAula(int idAula, int idTag){
        db=dbHelper.getReadableDatabase();
        boolean result = false;
        Cursor cursor=db.query("AULA_TAG",new String[]{"*"},"IdAula=? AND IdTag=?",new String[]{idAula+"",idTag+""},null ,null, null);
        if(cursor.moveToFirst()){
            result=true;
        }
        db.close();
        cursor.close();
        return result;
    }

    public void inserirRelacaoTagAula(int idTag,int idAula){
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdTag",idTag);
        newValues.put("IdAula",idAula);
        db.insert("AULA_TAG",null,newValues);
        db.close();
    }
}
