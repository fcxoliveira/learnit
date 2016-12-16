package ufrpe.edu.learnit.infra.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.infra.dominio.Confirmacao;
import ufrpe.edu.learnit.infra.persistencia.ConfirmacaoPersistencia;

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
}