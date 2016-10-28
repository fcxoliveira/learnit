package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.dominio.Tag;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;

/**
 * Created by silva on 26/10/2016.
 */

public class GerenciadorAulasTutor {
    public void cadastrarAula(String titulo, String descricao, int duracao,double valor,Tag tag1,Tag tag2){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.cadastrarAula(titulo,descricao, duracao,valor,tag1,tag2);
    }

}
