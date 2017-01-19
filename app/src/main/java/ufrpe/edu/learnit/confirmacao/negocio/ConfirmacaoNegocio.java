package ufrpe.edu.learnit.confirmacao.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.confirmacao.dominio.Confirmacao;
import ufrpe.edu.learnit.confirmacao.persistencia.ConfirmacaoPersistencia;

/**
 * Created by joel_ on 16/12/2016.
 */

public class ConfirmacaoNegocio {

    public void enviarConfirmacao(int idAula, int idAluno, int HorasConfirmadas, int status) {
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        confirmacaoPersistencia.enviarConfirmacao(idAula, idAluno, HorasConfirmadas, status);
    }

    public ArrayList<Confirmacao> retornarTodasConfirmacoes(int idPerfil) {
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.retornarTodasConfirmacoes(idPerfil);
    }

    public boolean ConfirmacaoRecebida(){
        boolean result=false;
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        if(confirmacaoPersistencia.existeConfirmacaoRecebida()){
            result=true;
        }
        return result;
    }
    public boolean confirmacaoPendente(int idAluno){
        ConfirmacaoPersistencia confirmacaoPersistencia = new ConfirmacaoPersistencia();
        return confirmacaoPersistencia.confirmacaoPendente(idAluno);
    }

}