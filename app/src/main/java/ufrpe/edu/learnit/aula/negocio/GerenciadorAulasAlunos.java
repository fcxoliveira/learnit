package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.AlunoAula;
import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;
import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.persistencia.ConfirmacaoPersistencia;

public class GerenciadorAulasAlunos {
    public ArrayList<Aula> getAulasPorTexto(String texto,int idUser){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return  aulaPersistencia.getAulasPorTexto(texto,idUser);
    }

    public void inscreverAlunoEmAula(int idAluno, int idAula,String date,int horas,int valorPago){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.inscreverAlunoEmAula(idAluno, idAula, date, horas, valorPago);
    }

    public ArrayList<AlunoAula> retornarAulasCompradas(int idAluno){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAulasCompradas(idAluno);
    }
    public AlunoAula retornarAlunoAula(int idAlunoAula){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAlunoAula(idAlunoAula);
    }

    public boolean existeConfirmacaoRecebida(){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.existeConfirmacaoRecebida();
    }
    public Confirmacao retornarConfirmacaoRecebida(){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.retornarConfirmacaoRecebida();
    }
    public void aceitarConfirmacao(Confirmacao confirmacao){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        confirmacaoPersistencia.aceitarConfirmacao(confirmacao);
    }
    public void cancelarConfirmacao(int idConfirmacao){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        confirmacaoPersistencia.cancelarConfirmacao(idConfirmacao);
    }
    }
