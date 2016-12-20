package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.infra.dominio.Confirmacao;
import ufrpe.edu.learnit.infra.persistencia.ConfirmacaoPersistencia;

public class GerenciadorAulasAlunos {
    public ArrayList<Aula> getAulasPorTexto(String texto){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return  aulaPersistencia.getAulasPorTexto(texto);
    }

    public void inscreverAlunoEmAula(int idAluno, int idAula,String date,int horas,int valorPago){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.inscreverAlunoEmAula(idAluno, idAula, date, horas, valorPago);
    }

    public ArrayList<AlunoAula> retornarAulasCompradas(int idAluno){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAulasCompradas(idAluno);
    }
    public boolean existeConfirmacaoRecebida(){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.existeConfirmacaoRecebida();
    }
    public Confirmacao retornarConfirmacaoRecebida(){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.retornarConfirmacaoRecebida();
    }
    public void aceitarConfirmacao(int idConfirmacao){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        confirmacaoPersistencia.aceitarConfirmacao(idConfirmacao);
    }
    public void cancelarConfirmacao(int idConfirmacao){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        confirmacaoPersistencia.cancelarConfirmacao(idConfirmacao);
    }
    }
