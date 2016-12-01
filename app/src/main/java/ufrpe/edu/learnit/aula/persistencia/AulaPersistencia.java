package ufrpe.edu.learnit.aula.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
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


    public int retornarQuantidadeDeAulas(int id){
        db=dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        Cursor cursor=db.query("AULAS",new String[]{"*"},"IdPerfil = ?",new String[]{sb.toString()},null ,null, null);
        return cursor.getCount();
    }

    public void editarAula(int id,String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        db = dbHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Descricao",descricao);
        newValues.put("Titulo",titulo);
        newValues.put("Tag1",tag1.getID());
        newValues.put("Tag2",tag2.getID());
        newValues.put("Horas",duracao);
        newValues.put("Valor",valor);
        StringBuilder sb = new StringBuilder();
        sb.append(Session.getUsuario().getID());
        String idPerfilString=sb.toString();
        newValues.put("IdPerfil",idPerfilString);
        StringBuilder sbid = new StringBuilder();
        sbid.append(Session.getUsuario().getID());
        String idString=sbid.toString();
        newValues.put("Id",idString);
        db.update("",newValues,"Id='"+idString+"'",null);
    }

    public ArrayList<Aula> getTodasAulasOfertadas(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("AULAS",new String[]{"*"},null,null,null ,null, null);
        ArrayList<Aula> aulas = new ArrayList<>();
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        int i = 0;
        while (cursor.moveToNext()){
            Aula aula = new Aula();
            aula.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            aula.setPerfil(perfilPersistencia.retornarPerfil(cursor.getInt(cursor.getColumnIndex("IdPerfil"))));
            aula.setTitulo(cursor.getString(cursor.getColumnIndex("Titulo")));
            aula.setAtiva(true);
            aula.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));
            aula.setDuracaoHorasAula(cursor.getInt(cursor.getColumnIndex("Horas")));
            ArrayList<Tag> tags = new ArrayList<Tag>();
            Tag tag1 = new Tag();
            Tag tag2 = new Tag();
            tag1.setID(cursor.getInt(cursor.getColumnIndex("Tag1")));
            tag2.setID(cursor.getInt(cursor.getColumnIndex("Tag2")));
            tags.add(tag1);
            tags.add(tag2);
            aula.setTags(tags);
            aula.setValor(cursor.getDouble(cursor.getColumnIndex("Valor")));
            aulas.add(aula);
        }
        return aulas;
    }

    public ArrayList<Aula> getAulasPorTexto(String texto){
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("AULAS",new String[]{"*"},"Titulo LIKE ? OR Descricao LIKE ?",new String[] { "%"+texto+"%" },null ,null, null);
        ArrayList<Aula> aulas = new ArrayList<>();
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        int i = 0;
        while (cursor.moveToNext()){
            Aula aula = new Aula();
            aula.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            aula.setPerfil(perfilPersistencia.retornarPerfil(cursor.getInt(cursor.getColumnIndex("IdPerfil"))));
            aula.setTitulo(cursor.getString(cursor.getColumnIndex("Titulo")));
            aula.setAtiva(true);
            aula.setDescricao(cursor.getString(cursor.getColumnIndex("Descricao")));
            aula.setDuracaoHorasAula(cursor.getInt(cursor.getColumnIndex("Horas")));
            ArrayList<Tag> tags = new ArrayList<Tag>();
            Tag tag1 = new Tag();
            Tag tag2 = new Tag();
            tag1.setID(cursor.getInt(cursor.getColumnIndex("Tag1")));
            tag2.setID(cursor.getInt(cursor.getColumnIndex("Tag2")));
            tags.add(tag1);
            tags.add(tag2);
            aula.setTags(tags);
            aula.setValor(cursor.getDouble(cursor.getColumnIndex("Valor")));
            aulas.add(aula);
        }
        return aulas;
    }
}
