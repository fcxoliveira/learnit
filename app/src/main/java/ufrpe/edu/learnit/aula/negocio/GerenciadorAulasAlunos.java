package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;

public class GerenciadorAulasAlunos {
    public ArrayList<Aula> getAulasPorTexto(String texto){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return  aulaPersistencia.getAulasPorTexto(texto);
    }

    public void inscreverAlunoEmAula(int idAluno, int idAula,String date,int horas,int moedas){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.inscreverAlunoEmAula(idAluno, idAula, date, horas, moedas);
    }

    public ArrayList<AlunoAula> retornarAulasCompradas(int idAluno){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAulasCompradas(idAluno);
    }


    }
