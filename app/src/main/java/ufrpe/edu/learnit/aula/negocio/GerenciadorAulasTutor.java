package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;


public class GerenciadorAulasTutor {
    public void cadastrarAula(String titulo, String descricao, int duracao,Tag tag1,Tag tag2 ,double valor){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.cadastrarAula(titulo,descricao, duracao,valor,tag1,tag2);
    }


    public int retornarQuantidadeDeAulas(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarQuantidadeDeAulas(id);
    }

    public void editarAula(int id,String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.editarAula(id,titulo,descricao,duracao,valor,tag1,tag2);

    }

    public Aula retornarAula(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAula(id);
    }
}
