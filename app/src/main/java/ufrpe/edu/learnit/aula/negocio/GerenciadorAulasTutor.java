package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;


public class GerenciadorAulasTutor {
    public void cadastrarAula(String titulo, String descricao, int duracao,int idTag1,int idTag2 ,double valor){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        Tag tag1 = aulaPersistencia.retornarTag(idTag1);
        Tag tag2 = aulaPersistencia.retornarTag(idTag2);
        aulaPersistencia.cadastrarAula(titulo,descricao, duracao,valor,tag1,tag2);
    }

    public ArrayList<Tag> retornarTodasTags(){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarTodasTags();
    }

    public int retornarQuantidadeDeAulas(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarQuantidadeDeAulas(id);
    }
}
