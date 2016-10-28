package ufrpe.edu.learnit.aula.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;


public class AulaPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public AulaPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public Aula cadastrarAula(String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        db = dbHelper.getWritableDatabase();
        int tag1ID = tag1.getID();
        int tag2ID = tag2.getID();
        int id = Session.getUsuario().getID();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        String idString = sb.toString();
        ContentValues newValues = new ContentValues();
        newValues.put("Titulo", titulo);
        newValues.put("Descricao",descricao);
        newValues.put("Horas",duracao);
        newValues.put("Valor",valor);
        newValues.put("Tag1", tag1ID);
        newValues.put("Tag2", tag2ID);
        newValues.put("IdPerfil",Session.getUsuario().getID());
        db.insert("AULAS", null, newValues);
        Aula aula=preencherDadosAula(titulo,descricao,duracao,valor,tag1,tag2,Session.getUsuario().getID());
        Cursor cursor=db.query("AULAS",new String[]{"*"}, "IdPerfil=? and Titulo=? and Descricao = ?", new String[]{idString,titulo,descricao},null ,null, null);
        cursor.moveToFirst();
        int idInsert = cursor.getInt(cursor.getColumnIndex("Id"));
        aula.setId(idInsert);
        db.close();
        return aula;

    }
    public Aula preencherDadosAula(String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2,int IdPerfil){
        Aula aula = new Aula();
        aula.setTitulo(titulo);
        aula.setDescricao(descricao);
        aula.setDuracaoHorasAula(duracao);
        aula.setValor(valor);
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        aula.setPerfil(perfilPersistencia.retornarPerfil(IdPerfil));
        ArrayList<Tag> listaTags = new ArrayList<Tag>(Arrays.asList(tag1,tag2));
        aula.setTags(listaTags);
        return aula;
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

    public int retornarQuantidadeDeAulas(int id){
        db=dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        Cursor cursor=db.query("AULAS",new String[]{"*"},"IdPerfil = ?",new String[]{sb.toString()},null ,null, null);
        return cursor.getCount();
    }
}
