package ufrpe.edu.learnit.confirmacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
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
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "IdAluno = ? AND IdAula = ? ", new String[]{idAluno + "", idAula + ""}, null, null, null);
        ContentValues newValues = new ContentValues();
        Confirmacao confirmacao;
        if (cursor.moveToFirst()){
            int idConfirmacao = cursor.getColumnIndex("Id");
            newValues.put("HorasParaConfirmar", horasConfirmadas);
            newValues.put("Status", status);
            db.update("CONFIRMACAO",newValues,"Id='"+idConfirmacao +"'",null);
            confirmacao = preencherConfirmacao(idAula, idAluno, horasConfirmadas, status);
            confirmacao.setId(idConfirmacao);

        }else {
            newValues.put("IdAula", idAula);
            newValues.put("IdAluno", idAluno);
            newValues.put("HorasParaConfirmar", horasConfirmadas);
            newValues.put("Status", status);
            db.insert("CONFIRMACAO", null, newValues);
            Cursor neWcursor = db.query("CONFIRMACAO", new String[]{"*"}, "IdAluno = ? AND IdAula = ? ", new String[]{idAluno + "", idAula + ""}, null, null, null);
            int idConfirmacao = neWcursor.getColumnIndex("Id");
            confirmacao = preencherConfirmacao(idAula, idAluno, horasConfirmadas, status);
            confirmacao.setId(idConfirmacao);

        }
        cursor.moveToLast()
        return confirmacao;
    }

    public Confirmacao retornarConfirmacao(int idConfirmacao) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Id = ?", new String[]{idConfirmacao + ""}, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("Id"));
        int idAula = cursor.getInt(cursor.getColumnIndex("IdAula"));
        int horasConfirmadas = cursor.getInt(cursor.getColumnIndex("HorasParaConfirmar"));
        int status = cursor.getInt(cursor.getColumnIndex("Status"));
        int idAluno = cursor.getInt(cursor.getColumnIndex("IdAluno"));
        Confirmacao confirmacao = preencherConfirmacao(idAula, idAluno, horasConfirmadas, status);
        confirmacao.setId(id);
        return confirmacao;
    }

    public ArrayList<Confirmacao> retornarConfimacoesCanceladas(int idAula){
        ArrayList<Confirmacao> confirmacoes = null;
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdAula=? AND IdAula=?", new String[]{"2",idAula+"",idAula+""}, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("Id"));
            int horasConfirmadas = cursor.getInt(cursor.getColumnIndex("HorasParaConfirmar"));
            int status = cursor.getInt(cursor.getColumnIndex("Status"));
            int idAluno = cursor.getInt(cursor.getColumnIndex("IdAluno"));
            Confirmacao confirmacao = preencherConfirmacao(idAula, idAluno,horasConfirmadas, status);
            confirmacoes.add(confirmacao);
        }
        return confirmacoes;
    }

    public ArrayList<Confirmacao> retornarTodasConfirmacoes(int idPerfil) {//retorna todas as confirmações do banco relacionadas ao perfil
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
    public boolean confirmacaoPendente(int idAluno){ //checar se o professor ja enviou uma confirmação
        int idAula=Session.getAula().getId();
        db = dbHelper.getReadableDatabase();
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
        Cursor cursor = db.query("CONFIRMACAO", new String[]{"*"}, "Status = ? AND IdAula=? AND IdAluno=?", new String[]{"0",idAula+"",idAluno+""}, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("Id"));
        return retornarConfirmacao(id);
    }

    public void aceitarConfirmacao(Confirmacao confirmacao){//aluno aceitar confirmação recebida
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        ContentValues newValuesAlunoAula= new ContentValues();
        newValues.put("Status", 1);
        int idAula=confirmacao.getIdAula();
        int idAluno=confirmacao.getIdAluno();
        Cursor cursor = db.query("ALUNO_AULA", new String[]{"*"}, "IdAula=? AND IdPerfilAluno=?", new String[]{idAula+"",idAluno+""}, null, null, null);
        cursor.moveToFirst();
        int horasConfirmadasTotais = cursor.getInt(cursor.getColumnIndex("HorasConfirmadas"));
        int horasConfirmadas = horasConfirmadasTotais+confirmacao.getHorasConfirmadas();
        newValuesAlunoAula.put("HorasConfirmadas",horasConfirmadas+"");
        db.update("ALUNO_AULA",newValuesAlunoAula,"IdAula='"+confirmacao.getIdAula()+"' AND "+"IdPerfilAluno='"+confirmacao.getIdAluno()+"'",null);
        db.update("CONFIRMACAO",newValues,"Id='"+confirmacao.getId()+"'",null);
        db.close();
        cursor.close();
    }

    public void cancelarConfirmacao(int idConfirmacao){ //aluno cancelar confirmação recebida
        db=dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Status", 2);
        db.update("CONFIRMACAO",newValues,"Id='"+idConfirmacao+"'",null);
    }


}
//0-enviado 1-confirmado 2-cancelado