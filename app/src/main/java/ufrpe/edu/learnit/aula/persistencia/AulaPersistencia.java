package ufrpe.edu.learnit.aula.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;

/**
 * Created by silva on 26/10/2016.
 */

public class AulaPersistencia {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public AulaPersistencia(){
        context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public void cadastrarAula(String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        db = dbHelper.getWritableDatabase();
        int tag1ID = tag1.getID();
        int tag2ID = tag2.getID();
        ContentValues newValues = new ContentValues();
        newValues.put("Titulo", titulo);
        newValues.put("Descricao",descricao);
        newValues.put("Horas",duracao);
        newValues.put("Valor",valor);
        newValues.put("Tag1", tag1ID);
        newValues.put("Tag", tag2ID);
        db.insert("AULAS", null, newValues);
        Aula aula=preencherDadosAula(titulo,descricao,duracao,valor,tag1,tag2);

    }
    public Aula preencherDadosAula(String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        Aula aula = new Aula();
        aula.setTitulo(titulo);
        aula.setDescricao(descricao);
        aula.setDuracaoHorasAula(duracao);
        aula.setValor(valor);
        ArrayList<Tag> listaTags = new ArrayList<Tag>(Arrays.asList(tag1,tag2));
        aula.setTags(listaTags);
        return aula;
    }
}
