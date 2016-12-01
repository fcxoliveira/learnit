package ufrpe.edu.learnit.aula.negocio;

import java.util.ArrayList;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;

/**
 * Created by silva on 26/10/2016.
 */

public class GerenciadorAulasAlunos {
    public ArrayList<Aula> getTodasAulasOfertadas(){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        ArrayList<Aula> aulas =  aulaPersistencia.getTodasAulasOfertadas();
        return aulas;
    }

    public ArrayList<Aula> getAulasPorTexto(String texto){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        ArrayList<Aula> aulas =  aulaPersistencia.getAulasPorTexto(texto);
        return aulas;
    }

}
