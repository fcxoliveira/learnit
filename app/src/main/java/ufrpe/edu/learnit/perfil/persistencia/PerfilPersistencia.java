package ufrpe.edu.learnit.perfil.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ufrpe.edu.learnit.infra.DataBaseHelper;
import ufrpe.edu.learnit.infra.dominio.Session;
import ufrpe.edu.learnit.perfil.dominio.Perfil;

public class PerfilPersistencia {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public PerfilPersistencia(){
        Context context = Session.getContext();
        dbHelper = new DataBaseHelper(context, null);
    }

    public void cadastrarPerfil(int id, String bio, String nome){
        db = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("IdPerfil", id);
        newValues.put("Bio", bio);
        newValues.put("Nome", nome);
        newValues.put("Moedas", 500);
        newValues.put("Avaliadores", 0);
        newValues.put("Avaliacao", 0);
        newValues.put("Horas", 0);
        db.insert("PERFIL", null, newValues);
        db.close();
    }

    public void setNota(int idPerfil,float avaliacao){
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{idPerfil+""}, null, null, null);
        int avaliadores = cursor.getInt(cursor.getColumnIndex("Avaliadores"));
        float avaliacaoTotal = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
        avaliacaoTotal= avaliacao+avaliacaoTotal;
        ContentValues newValues = new ContentValues();
        newValues.put("Avaliacao", avaliacaoTotal);
        newValues.put("Avaliadores", avaliadores+1);
    }
    public Perfil retornarPerfil(int id){
        Perfil result = new Perfil();
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("PERFIL", null, "IdPerfil=?",new String[]{id+""}, null, null, null);
        if (!cursor.moveToFirst()){
            result = null;
        }else{
            String bio = cursor.getString(cursor.getColumnIndex("Bio"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            int moedas = cursor.getInt(cursor.getColumnIndex("Moedas"));
            int avaliadores = cursor.getInt(cursor.getColumnIndex("Avaliadores"));
            float avaliacao = cursor.getFloat(cursor.getColumnIndex("Avaliacao"));
            int horas = cursor.getInt(cursor.getColumnIndex("Horas"));
            result.setId(id);
            result.setAvaliacao(avaliacao);
            result.setBio(bio);
            result.setMoedas(moedas);
            result.setNome(nome);
            result.setHoras(horas);
            result.setAvaliadores(avaliadores);


        }
        cursor.close();
        db.close();
        return result;
    }

    public void editarPerfil(int id, String bio, String nome){
        db = dbHelper.getReadableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("Bio",bio);
        newValues.put("Nome",nome);
        db.update("PERFIL",newValues,"IdPerfil='"+id+"'",null);
        db.close();
    }

    public void removerMoedas(int id,int moedas){
        Perfil perfil = retornarPerfil(id);
        moedas = perfil.getMoedas()-moedas;
        String idString = String.valueOf(id);
        ContentValues newValues = new ContentValues();
        newValues.put("Moedas",String.valueOf(moedas));
        db = dbHelper.getReadableDatabase();
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }

    public void addMoeda(int moedas) {
        db = dbHelper.getReadableDatabase();
        String idString = String.valueOf(Session.getUsuario().getID());
        moedas = Session.getUsuario().getPerfil().getMoedas()+moedas;
        ContentValues newValues = new ContentValues();
        newValues.put("Moedas",moedas);
        db.update("PERFIL",newValues,"IdPerfil='"+idString+"'",null);
        db.close();
    }
}
