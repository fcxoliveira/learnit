package ufrpe.edu.learnit.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Confirmacao;
import ufrpe.edu.learnit.infra.dominio.Session;

public class ConfirmacaoPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public ConfirmacaoPersistencia() {
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);

    }

    public Confirmacao preencherConfirmacao(int idAula, int idAluno, int horasConfirmadas, int status) {
        Confirmacao confirmacao = new Confirmacao();
        confirmacao.setIdAula(idAula);
        confirmacao.setIdAluno(idAluno);
        confirmacao.setHorasConfirmadas(horasConfirmadas);
        confirmacao.setStatus(status);
        return confirmacao;
    }

    public Confirmacao enviarConfirmacao(int idAula, int idAluno, int horasConfirmadas, int status) {
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdAula", idAula);
        newValues.put("IdAluno", idAluno);
        newValues.put("HorasParaConfirmar", horasConfirmadas);
        newValues.put("Status", status);
        db.insert("CONFIRMACAO", null, newValues);
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "IdAluno = ? AND IdAula = ? ", new String[]{idAluno + "", idAula + ""}, null, null, null);
        cursor.moveToLast();
        Confirmacao confirmacao = preencherConfirmacao(idAula, idAluno, horasConfirmadas, status);
        confirmacao.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        return confirmacao;
    }

    public Confirmacao retornarConfirmacao(int idConfirmacao) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Id = ?", new String[]{idConfirmacao + ""}, null, null, null);
        int id = cursor.getInt(cursor.getColumnIndex("Id"));
        int idAula = cursor.getInt(cursor.getColumnIndex("IdAula"));
        int horasConfirmadas = cursor.getInt(cursor.getColumnIndex("HorasConfirmadas"));
        int status = cursor.getInt(cursor.getColumnIndex("Status"));
        int idAluno = cursor.getInt(cursor.getColumnIndex("IdAluno"));
        Confirmacao confirmacao = preencherConfirmacao(idAula, idAluno, horasConfirmadas, status);
        confirmacao.setId(id);
        return confirmacao;
    }

    public ArrayList<Confirmacao> retornarTodasConfirmacoes(int idPerfil) {//retorna todas as confirmações do banco
        ArrayList<Confirmacao> confirmacoes = new ArrayList<Confirmacao>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdPerfil=?", new String[]{"0",idPerfil+""}, null, null, null);
        while (cursor.moveToNext()){
            confirmacoes.add(retornarConfirmacao(cursor.getInt(cursor.getColumnIndex("Id"))));
        }
        return confirmacoes;
    }


    public boolean existeConfirmacaoRecebida(){ //checar se a aula do aluno tem confirmação a ser aceita
        db = dbHelper.getReadableDatabase();
        int idAula = Session.getAlunoAula().getAula().getId();
        int idAluno = Session.getUsuario().getID();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdAula=? AND IdAluno=?", new String[]{"0",idAula+"",idAluno+""}, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public Confirmacao retornarConfirmacaoRecebida(){//recuperar a confirmação do banco
        db = dbHelper.getReadableDatabase();
        int idAula = Session.getAlunoAula().getAula().getId();
        int idAluno = Session.getUsuario().getID();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdAAula=? AND IdAluno=?", new String[]{"0",idAula+"",idAluno+""}, null, null, null);
        int id = cursor.getInt(cursor.getColumnIndex("Id"));
        return retornarConfirmacao(id);
    }

    public void aceitarConfirmacao(int idConfirmacao){//aluno aceitar confirmação recebida
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Status", 1);
        db.update("CONFIRMACAO",newValues,"Id='"+idConfirmacao+"'",null);
    }

    public void cancelarConfirmacao(int idConfirmacao){ //aluno cancelar confirmação recebida
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Status", 2);
        db.update("CONFIRMACAO",newValues,"Id='"+idConfirmacao+"'",null);
    }

    public boolean confirmacaoPendente(int idAluno){ //checar se o professor ja enviou uma confirmação
        int idAula=Session.getAula().getId();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdAAula=? AND IdAluno=?", new String[]{"0",idAula+"",idAluno+""}, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
}
//0-enviado 1-confirmado 2-cancelado