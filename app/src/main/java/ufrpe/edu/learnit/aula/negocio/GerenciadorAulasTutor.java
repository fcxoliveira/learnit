package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
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

    public void editarAula(int id, int duracao,int valor){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        aulaPersistencia.editarAula(id,duracao,valor);

    }

    public boolean verificarHorasDisponiveis(int idAula, int idAluno,int horasParaConfirmar){
        boolean result = true;
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        int horasDisponoveis= aulaPersistencia.retornarHorasRestantes(idAula, idAluno);
        if (horasDisponoveis< horasParaConfirmar){
            result=false;
        }
        return result;
    }

    public Aula retornarAula(int id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAula(id);
    }

    public ArrayList<Aula> retornarAulasOfertadas(int idPerfil){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAulasOfertadas(idPerfil);
    }

    public ArrayList<Perfil> retornarAlunosCadastrados(int idAula){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAlunosCadastrados(idAula);
    }
}
