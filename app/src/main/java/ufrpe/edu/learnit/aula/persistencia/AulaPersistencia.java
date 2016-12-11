package ufrpe.edu.learnit.aula.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;
import ufrpe.edu.learnit.perfil.persistencia.PerfilPersistencia;
import ufrpe.edu.learnit.aula.dominio.AlunoAula;


public class AulaPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public AulaPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public void cadastrarAula(String titulo, String descricao, int duracao, int valor, Tag tag1, Tag tag2){
        db = dbHelper.getWritableDatabase();
        int tag1ID = tag1.getID();
        int tag2ID = tag2.getID();
        ContentValues newValues = new ContentValues();
        newValues.put("Titulo", titulo);
        newValues.put("Descricao",descricao);
        newValues.put("Horas",duracao);
        newValues.put("Valor",valor);
        newValues.put("Tag1", tag1ID);
        newValues.put("Tag2", tag2ID);
        newValues.put("IdPerfil",Session.getUsuario().getID());
        db.insert("AULAS", null, newValues);
        db.close();
    }

    private Aula preencherDadosAula(int id, String titulo, String descricao, int duracao, int valor, int IdPerfil){
        Aula aula = new Aula();
        aula.setId(id);
        aula.setTitulo(titulo);
        aula.setDescricao(descricao);
        aula.setDuracaoHorasAula(duracao);
        aula.setValor(valor);
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        aula.setPerfil(perfilPersistencia.retornarPerfil(IdPerfil));
        return aula;
    }


    public int retornarQuantidadeDeAulas(int id){
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("AULAS",new String[]{"*"},"IdPerfil = ?",new String[]{id+""},null ,null, null);
        cursor.close();
        db.close();
        return cursor.getCount();
        }

    public void editarAula(int id,String titulo, String descricao, int duracao,int valor){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Descricao",descricao);
        newValues.put("Titulo",titulo);
        newValues.put("Horas",duracao);
        newValues.put("Valor",valor);
        newValues.put("IdPerfil",id+"");
        newValues.put("Id",id+"");
        db.update("AULAS",newValues,"Id='"+id+"'",null);
    }

    public ArrayList<Aula> getAulasPorTexto(String texto){
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("AULAS",new String[]{"*"},"Titulo LIKE ? OR Descricao LIKE ? AND IdPerfil!=?",new String[] { "%"+texto+"%","%"+texto+"%",Session.getUsuario().getID()+"" },null ,null, null);
        ArrayList<Aula> aulas = new ArrayList<>();
        while (cursor.moveToNext()){
            Aula aula = retornarAula(cursor.getInt(cursor.getColumnIndex("Id")));
            aulas.add(aula);
        }
        cursor.close();
        db.close();
        return aulas;
    }

    public void inscreverAlunoEmAula(int idAluno, int idAula, String date,int horas,int moedas){
        db = dbHelper.getWritableDatabase();
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        String idAlunoString = String.valueOf(idAluno);
        String idAulaString = String.valueOf(idAula);
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfilAluno",idAlunoString);
        newValues.put("IdAula", idAulaString);
        newValues.put("date",date);
        newValues.put("horas",horas);
        newValues.put("moedas",moedas);
        db.insert("ALUNO_AULA", null, newValues);
        removerHorasDisponiveis(idAula,horas);
        perfilPersistencia.removerMoedas(idAluno, moedas);
        db.close();


    }

    private void removerHorasDisponiveis(int idAula, int horas){
        ContentValues newValues = new ContentValues();
        Aula aula =  retornarAula(idAula);
        int horasTotal = aula.getHoras()-horas;
        String idAulaString = String.valueOf(idAula);
        String horasString = String.valueOf(horasTotal);
        newValues.put("Horas", horasString);
        db = dbHelper.getWritableDatabase();
        db.update("AULAS",newValues,"Id = ?",new String[]{idAulaString});
        db.close();
    }
    public Aula retornarAula(int id){
        Aula result;
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("AULAS", null, "Id=?",new String[]{id+""}, null, null, null);
        if (!cursor.moveToFirst()){
            result = null;
            db.close();
        }else{
            String descricao = cursor.getString(cursor.getColumnIndex("Descricao"));
            String nome = cursor.getString(cursor.getColumnIndex("Titulo"));
            int valor = cursor.getInt(cursor.getColumnIndex("Valor"));
            int horas = cursor.getInt(cursor.getColumnIndex("Horas"));
            int idPerfil = cursor.getInt(cursor.getColumnIndex("IdPerfil"));
            result = preencherDadosAula(id,nome,descricao,horas,valor,idPerfil);
            db.close();
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Aula> retornarAulasOfertadas(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Aula> aulas = new ArrayList<>();
        String idPerfilString = String.valueOf(Session.getUsuario().getID());
        Cursor cursor=db.query("AULAS",new String[]{"*"},"IdPerfil = ?",new String[] {idPerfilString},null ,null, null);
        while (cursor.moveToNext()){
            aulas.add(retornarAula(cursor.getInt(cursor.getColumnIndex("Id"))));
        }
        cursor.close();
        db.close();
        return aulas;
    }

    public ArrayList<Perfil> retornarAlunosCadastrados(int id){
        db = dbHelper.getReadableDatabase();
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        ArrayList<Perfil> alunos = new ArrayList<>();
        String idAulaString = String.valueOf(id);
        Cursor cursor=db.query("ALUNO_AULA",new String[]{"*"},"IdAula = ?",new String[] {idAulaString},null ,null, null);
        while (cursor.moveToNext()){
            alunos.add(perfilPersistencia.retornarPerfil(cursor.getInt(cursor.getColumnIndex("IdPerfilAluno"))));
        }
        cursor.close();
        db.close();
        return alunos;

    }

    public ArrayList<AlunoAula> retornarAulasCompradas(int idAluno){
        db = dbHelper.getReadableDatabase();
        ArrayList<AlunoAula> result = new ArrayList<>();
        PerfilPersistencia perfilPersistencia = new PerfilPersistencia();
        Cursor cursor=db.query("ALUNO_AULA",new String[]{"*"},"IdPerfilAluno = ?",new String[] {idAluno+""},null ,null, null);
        while (cursor.moveToNext()){
            AlunoAula alunoAula = new AlunoAula();
            alunoAula.setPerfil(perfilPersistencia.retornarPerfil(idAluno));
            alunoAula.setAula(retornarAula(cursor.getInt(cursor.getColumnIndex("IdAula"))));
            alunoAula.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            alunoAula.setHorasTotal(cursor.getInt(cursor.getColumnIndex("HorasCompradas")));
            alunoAula.setValorTotal(cursor.getInt(cursor.getColumnIndex("ValorPago")));
            result.add(alunoAula);
        }
        cursor.close();
        db.close();
        return result;
    }



}
