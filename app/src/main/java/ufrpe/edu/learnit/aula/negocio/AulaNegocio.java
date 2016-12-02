package ufrpe.edu.learnit.aula.negocio;

import ufrpe.edu.learnit.aula.dominio.Aula;
import ufrpe.edu.learnit.aula.persistencia.AulaPersistencia;

/**
 * Created by Felipe on 01/12/2016.
 */

public class AulaNegocio {
    public Aula retornarAula(long id){
        AulaPersistencia aulaPersistencia = new AulaPersistencia();
        return aulaPersistencia.retornarAula(id);
    }



}
