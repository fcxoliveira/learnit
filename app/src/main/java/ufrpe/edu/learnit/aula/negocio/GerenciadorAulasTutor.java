package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.infra.dominio.Tag;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.perfil.dominio.Perfil;


public class GerenciadorAulasTutor {
    public void cadastrarAula(String titulo, String descricao, int duracao, int valor){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.cadastrarAula(titulo,descricao, duracao,valor);
    }


    public int retornarQuantidadeDeAulas(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarQuantidadeDeAulas(id);
    }

    public void editarAula(int id,String titulo, String descricao, int duracao,int valor,Tag tag1,Tag tag2){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.editarAula(id,titulo,descricao,duracao,valor);

    }

    public Aula retornarAula(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAula(id);
    }

    public ArrayList<Aula> retornarAulasOfertadas(){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAulasOfertadas();
    }

    public ArrayList<Perfil> retornarAlunosCadastrados(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAlunosCadastrados(id);
    }
}
